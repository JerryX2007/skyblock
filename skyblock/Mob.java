import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.List;

/**
 * All mobs are born with its own damage, speed, and health (passive mobs have 0 damage)
 * All mobs have a chance to drop something when defeated
 * 
 * Mobs are born with a key indentifying trait - hostile or not
 * If the mob is hostile, will attempt to attack players in its sight range
 * If the mob is passive and attacked, will attempt to flee from the player
 * If there is nothing to do, mobs will default to wandering aimlessly
 * 
 * Mobs will spawn based on lighting and will only do so on solid blocks
 * 
 * @author Evan Xi
 * @version (a version number or a date)
 */
public abstract class Mob extends SuperSmoothMover{
    protected boolean isHostile;
    protected int damage;
    protected double speed;
    protected int health;
    
    protected double yVelocity;
    protected double acceleration = 0.15;
    
    protected int minSpawnLight;
    protected int maxSpawnLight;
    
    protected boolean huntRight = false;
    protected boolean huntLeft = false;

    SimpleTimer attackCD = new SimpleTimer();
    
    protected boolean isFleeing;
    SimpleTimer fleeTimer = new SimpleTimer();
    
    protected int wanderDirection = 0;
    SimpleTimer wanderTimer = new SimpleTimer();
    
    protected GreenfootImage defaultImg;
    protected GreenfootImage movingImg;
    protected GreenfootImage hurtImg;
    
    protected GreenfootImage defaultImgMirrored;
    protected GreenfootImage movingImgMirrored;
    protected GreenfootImage hurtImgMirrored;
    
    public Mob(boolean hostile, int dmg, double spd, int hp){
        isHostile = hostile;
        damage = dmg;
        speed = spd;
        health = hp;
        wanderTimer.mark();
        attackCD.mark();
    }
    
    /**
     * Perform tasks based on current condition
     * 
     * If it is hostile, will attempt to find players to attack
     * If it is peaceful, will flee when attacked
     * Both types will wander around with nothing to do
     */
    public void act(){
        if(fleeTimer.millisElapsed() > 2000){
            isFleeing = false;
        }
        
        // Actions to perform for a hostile mob
        if(isHostile){
            if(hasTarget()){
                hunt();
            }
            else{
                wander();
            }
            pursue();
        }
        
        // Actions to perform for a peaceful mob
        if(!isHostile){
            if(isFleeing){
                flee();
            }
            else{
                wander();
            }
        }
        
        // Update world conditions and check for attack/damage
        checkFalling();
        tryDamageMe();
        attack();
        
        // Instantly kill self when touching void
        if(isTouching(Void.class)){
            health -= 20;
        }
        
        // Drop items and remove self upon death
        if(health <= 0){
            drop();
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Applies to hostile mobs only
     * Constantly searches for a player within radius
     * If a player is found, begin working its way to it
     */
    protected void hunt(){
        List<Player> playersInRange = getObjectsInRange(600, Player.class);
        for(Player player : playersInRange){
            if(player.getX() > this.getX()){
                huntRight = true;
                huntLeft = false;
            }
            else{
                huntLeft = true;
                huntRight = false;
            }
        }
    }
    
    /**
     * If the hostile mob has a target, finds the difference in x-location between the player and itself
     * Will walk towards the player, jumping over obstacles along the way
     */
    protected void pursue(){
        if(!hasTarget()){
            huntRight = false;
            huntLeft = false;
            return;
        }
        if(huntRight){
            if(rightClear()){
                setImage(defaultImg);
                moveRight();
            }
            else if(headClear() && onGround()){
                setImage(defaultImg);
                jump();
                moveRight();
            }
        }
        if(huntLeft){
            if(leftClear()){
                setImage(defaultImgMirrored);
                moveLeft();
            }
            else if(headClear() && onGround()){
                setImage(defaultImgMirrored);
                jump();
                moveLeft();
            }
        }
    }
    
    /**
     * The method to a target in its sight range
     * If a target is found, set 
     */
    protected boolean hasTarget(){
        List<Player> playersInRange = getObjectsInRange(600, Player.class);
        if(playersInRange != null){
            return true;
        }
        return false;
    }
    
    protected void attack(){
        if(isHostile && attackCD.millisElapsed() > 2000){
            List<Player> players = getIntersectingObjects(Player.class);
            for(Player player : players){
                player.doDamage(damage);
                if(this.getX() > player.getX()){
                    player.knockBack(1);
                }
                else{
                    player.knockBack(2);
                }
                attackCD.mark();
            }
        }
    }
    
    /**
     * Applies to passive mobs only
     * If isFleeing = true, will run in the opposite direction of the player
     */
    protected void flee(){
        List<Player> playersInRange = getObjectsInRange(600, Player.class);
        for(Player player : playersInRange){
            if(player.getX() > this.getX()){
                if(leftClear()){
                    moveLeft();
                    setImage(defaultImgMirrored);
                }
                else if(headClear() && onGround()){
                    jump();
                    moveLeft();
                    setImage(defaultImgMirrored);
                }
            }
            else{
                if(rightClear()){
                    moveRight();
                    setImage(defaultImg);
                }
                else if(headClear() && onGround()){
                    jump();
                    moveRight();
                    setImage(defaultImg);
                }
            }
        }
    }
    
    /**
     * If there is nothing to do, wander aimlessly around
     */
    protected void wander(){
        if((wanderTimer.millisElapsed() > 3000) && wanderTimer.millisElapsed() < 4000){
            if(wanderDirection == 0){
                if(leftClear()){
                    moveLeft();
                    setImage(defaultImgMirrored);
                }
                else if(headClear() && onGround()){
                    jump();
                    moveLeft();
                    setImage(defaultImgMirrored);
                }
            }
            else if(wanderDirection == 1){
                if(rightClear()){
                    moveRight();
                    setImage(defaultImg);
                }
                else if(headClear() && onGround()){
                    jump();
                    moveRight();
                    setImage(defaultImg);
                }
            }                    
        }
        if(wanderTimer.millisElapsed() > 4000){
            wanderTimer.mark();
            Random random = new Random();
            wanderDirection = random.nextInt(2);
        }
    }
    
    /**
     * Checks if the player is within a certain range of the the mob.
     * 
     * @return True if the player is within range, false otherwise.
     */
    public boolean isPlayerWithinRange() {
        // Get self position
        int selfX = this.getX();
        int selfY = this.getY();
    
        // Get player's position
        int playerX = 640;
        int playerY = 384;
    
        // Calculate the direction vector
        int dirX = playerX - selfX;
        int dirY = playerY - selfY;
    
        // Check if the player is within a certain range
        if (dirX - dirY < 378) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    /**
     * Checks if a block is visible from the player's current position.
     * 
     * @param targetBlock The block to check.
     * @param increment The y-coordinate increment to adjust the player's position.
     * @return True if the block is visible, false otherwise.
     */
    public boolean isPlayerVisible(int increment) {
        // Get self position
        int selfX = this.getX();
        int selfY = this.getY() - increment;
    
        // Get player's position
        int playerX = 640;
        int playerY = 384;
    
        // Calculate the direction vector
        int dirX = playerX - selfX;
        int dirY = playerY - selfY;
        int steps = Math.max(Math.abs(dirX), Math.abs(dirY));
    
        // Normalize the direction vector
        double stepX = dirX / (double) steps;
        double stepY = dirY / (double) steps;
    
        // Cast the ray
        double currentX = selfX;
        double currentY = selfY;
        for (int i = 0; i < steps; i++) {
            // Increment to the position of the block
            currentX += stepX;
            currentY += stepY;
    
            // Check if there is a block at the current position
            Block block = (Block) getOneObjectAtOffset((int) Math.round(currentX - selfX), (int) Math.round(currentY - selfY), Block.class);
            if (block != null && !(block instanceof Air)) {
                return false; // Block is obstructing the view
            }
        }
        return true; // No obstructions
    }
    
    /**
     * All mobs will drop something upon death
     */
    protected abstract void drop();

    /**
     * Allows other entities to damage the mob
     * If the mob is passive, will flee from the player
     */
    public void tryDamageMe(){
        if(Greenfoot.mousePressed(this)){
            if(isPlayerWithinRange() && (isPlayerVisible(40) || isPlayerVisible(0) || isPlayerVisible(40))){
                health -= Player.getDamage();
                setImage(hurtImg);
                
                if(!isHostile){
                    isFleeing = true;
                    fleeTimer = new SimpleTimer();
                    fleeTimer.mark();
                }                
            }

        }
    }
    
    /**
     * Checks if the mob in on solid ground
     * Gets the block directly under the mob.  If there exists a block and it isn't an air block, return true.
     */
    protected boolean onGround() {
        Block under = (Block) getOneObjectAtOffset(getImage().getWidth()/2 - 20, getImage().getHeight()/2, Block.class);
        if(under != null) {
            if(!(under instanceof Air)){
                return true;
            }
        }     
        under = (Block) getOneObjectAtOffset(-(getImage().getWidth()/2), getImage().getHeight()/2, Block.class);
        if(under != null) {
            if(!(under instanceof Air)){
                return true;
            }
        } 
        return false;
    }

    /**
     * Checks if the mob's head is clear and determines if it can jump
     * Gets the block directly above the mob.  If there exists a block that isn't an air block, return false.  Otherwise return true.
     */
    protected boolean headClear(){
        Block above = (Block) getOneObjectAtOffset(0, -135, Block.class);
        if(above != null) {
            if(above instanceof Air) {
                return true;
            }
            else {
                return false;
            }
        }        
        return true;        
    }

    /**
     * Checks if the mob can continue moving right
     * Uses 4 collision points to the right of the mob to detect any non-air blocks
     * If either of the 4 collision points detect a non-air block, immediately return false
     */
    protected boolean rightClear(){ 
        Block right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, getImage().getHeight()/4, Block.class);
        if(right != null) {
            if(!(right instanceof Air)){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, (getImage().getHeight()/4) * -1, Block.class);
        if(right != null) {
            if(!(right instanceof Air)){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, getImage().getHeight()/2 - 5, Block.class);
        if(right != null) {
            if(!(right instanceof Air)){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, (getImage().getHeight()/2) * -1, Block.class);
        if(right != null) {
            if(!(right instanceof Air)){
                return false;
            }
        }
        return true;
    }    
    
    /**
     * Checks if the mob can continue moving left
     * Uses 4 collision points to the left of the mob to detect any non-air blocks
     * If either of the 4 collision points detect a non-air block, immediately return false
     */
    protected boolean leftClear(){
        Block left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, getImage().getHeight()/4, Block.class);
        if(left != null) {
            if(!(left instanceof Air)){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, (getImage().getHeight()/4) * -1, Block.class);
        if(left != null) {
            if(!(left instanceof Air)){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, getImage().getHeight()/2 - 5, Block.class);
        if(left != null) {
            if(!(left instanceof Air)){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, (getImage().getHeight()/2) * -1, Block.class);
        if(left != null) {
            if(!(left instanceof Air)){
                return false;
            }
        }

        return true;
    }
    
    /**
     * Moves left by a pixel for each move speed
     * If its path is blocked by a single block, will jump over it.  Otherwise break early.
     */
    protected void moveLeft(){
        for(int i = 0; i < speed; i++){
            setLocation(getX() - 1, getY());
            if(!leftClear()){
                if(headClear()){
                    jump();
                }
            }
        }
    }
        
    /**
     * Moves right by a pixel for each move speed
     * If its path is blocked by a single block, will jump over it.  Otherwise break early.
     */
    protected void moveRight(){
        for(int i = 0; i < speed; i++){
            setLocation(getX() + 1, getY());
            if(!rightClear()){
                if(headClear()){
                    jump();
                }
            }
        }
    }
    
    /**
     * If it isn't on the ground, start falling
     */
    protected void checkFalling() {
        if(onGround()) {
            yVelocity = 0;
        }
        else {
            fall();
        }
    }

    /**
     * Accelerate downwards to fall
     */
    protected void fall() {
        yVelocity = yVelocity + acceleration;
        setLocation(getX(), getY() + yVelocity);
    }

    /**
     * Gains a small amount of momentum upwards to jump
     */
    protected void jump() {
        yVelocity -= 4.5;
        setLocation(getX(), getY() + yVelocity);
    }
    
    /**
     * Gets the damage the mob does
     * 
     * @return the amount of damage
     */
    protected int getDamage(){
        return damage;
    }
}
