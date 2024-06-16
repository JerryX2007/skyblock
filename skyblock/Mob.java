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

    protected boolean isFleeing;
    SimpleTimer fleeTimer = new SimpleTimer();
    
    protected int wanderDirection = 0;
    SimpleTimer wanderTimer = new SimpleTimer();
    
    protected GreenfootImage defaultImg;
    protected GreenfootImage movingImg;
    protected GreenfootImage hurtImg;
    
    public Mob(boolean hostile, int dmg, double spd, int hp, int minLight, int maxLight){
        isHostile = hostile;
        damage = dmg;
        speed = spd;
        health = hp;
        minSpawnLight = minLight;
        maxSpawnLight = maxLight;
        wanderTimer.mark();
    }
    
    public void act(){
        if(fleeTimer.millisElapsed() > 2000){
            isFleeing = false;
        }
        
        if(isHostile){
            if(hasTarget()){
                hunt();
            }
            else{
                wander();
            }
            pursue();
        }
        
        if(!isHostile){
            if(isFleeing){
                flee();
            }
            else{
                wander();
            }
        }
        
        checkFalling();
        
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
    
    protected void pursue(){
        if(!hasTarget()){
            huntRight = false;
            huntLeft = false;
            return;
        }
        if(huntRight){
            if(rightClear()){
                moveRight();
            }
            else if(headClear() && onGround()){
                jump();
                moveRight();
            }
        }
        if(huntLeft){
            if(leftClear()){
                moveLeft();
            }
            else if(headClear() && onGround()){
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
                }
                else if(headClear() && onGround()){
                    jump();
                    moveLeft();
                }
            }
            else{
                if(rightClear()){
                    moveRight();
                }
                else if(headClear() && onGround()){
                    jump();
                    moveRight();
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
                }
                else if(headClear() && onGround()){
                    jump();
                    moveLeft();
                }
            }
            else if(wanderDirection == 1){
                if(rightClear()){
                    moveRight();
                }
                else if(headClear() && onGround()){
                    jump();
                    moveRight();
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
     * All mobs will drop something upon death
     */
    protected abstract void drop();

    /**
     * Allows other entities to damage the mob
     * If the mob is passive, will flee from the player
     */
    public void damageMe(int damage){
        health -= damage;
        if(!isHostile){
            isFleeing = true;
            fleeTimer = new SimpleTimer();
            fleeTimer.mark();
        }
    }
    
    public boolean canSpawn(int light, int gridX, int gridY){
        GameWorld world = (GameWorld) getWorld();
        if((light >= minSpawnLight) && (light <= maxSpawnLight)){ // Checks for light level requirement
            if(world.getGridValue(gridX, gridY).equals(Air.class) && !world.getGridValue(gridX, gridY + 1).equals(Air.class)){ // Checks if floor is solid and space is vacant
                if(world.getGridValue(gridX, gridY - 1).equals(Air.class) && world.getGridValue(gridX, gridY - 2).equals(Air.class)){ // Checks if there is at least two blocks above it
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the mob in on solid ground
     * Gets the block directly under the mob.  If there exists a block and it isn't an air block, return true.
     */
    protected boolean onGround() {
        Block under = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
        if(under != null) {
            if(under instanceof Air) {
                return false;
            }
            else {
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
        Block above = (Block) getOneObjectAtOffset(0, -(getImage().getHeight()/2+4), Block.class);
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
        setLocation(getX(), getY() + yVelocity);
        yVelocity = yVelocity + acceleration;
    }

    /**
     * Gains a small amount of momentum upwards to jump
     */
    protected void jump() {
        yVelocity -= 4.5;
        setLocation(getX(), getY() + yVelocity);
    }
}
