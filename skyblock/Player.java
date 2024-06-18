import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * The Player superclass represents a player in the game. 
 * It provides methods for movement, interaction with objects, and other player-related functionalities.
 * 
 * Uses A/D to move left and right
 * Uses W or Space to jump
 * Uses E to open inventory or close related interfaces
 * 
 * Will move the world instead of self
 * 
 * The player has 20hp initially indicated by a following health bar
 * The player will slowly regenerate hp over time
 * 
 * @author Jerry Xing, Evan Xi, Benny Wang, Nick Chen
 */
public abstract class Player extends SuperSmoothMover{
    protected static double moveSpeed;
    protected static int damage = 3;
    protected static int jumpHeight;
    protected static int reach;
    protected static boolean canDrop;
    protected static int pickUpRange;
    protected static boolean jumping;
    protected Inventory inventory;
    protected static GreenfootSound[] walking;
    protected boolean isPlaying;

    protected final int gravity = 2;
    protected double yVelocity;
    protected double xVelocity;
    protected double acceleration = 0.15;
    protected boolean direction; //true for facing right, false for left
    protected boolean isMoving;
    protected boolean isSprinting = false;
    protected int sprintToggleCD = 50;
    protected static boolean activated;
    protected static boolean activated1;
    
    protected Block block;
    protected Chest chest;
    protected CraftingTable craftingTable;

    protected int moveLeftCounter;
    protected int moveRightCounter;

    protected int hp; 
    
    protected static double totalXOffset;
    protected static double totalYOffset;
    
    SimpleTimer healTimer = new SimpleTimer();


    /**
     * Constructor for Player class.
     * 
     * @param moveSpeed Speed of the player's movement
     * @param jumpHeight Height of the player's jump
     * @param reach Reach distance of the player
     * @param canDrop Boolean indicating if the player can drop items
     * @param pickUpRange Range within which the player can pick up items
     * @param jumping Boolean indicating if the player is currently jumping
     * @param inventory The player's inventory
     */

    public Player(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange, boolean jumping, Inventory inventory) {
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.reach = reach;
        this.canDrop = canDrop;
        this.pickUpRange = pickUpRange;
        this.jumping = jumping;
        direction = false;
        isMoving = false;
        this.hp = 20;
        activated = false;
        this.inventory = inventory;
        walking = new GreenfootSound[3];
        walking[0] = new GreenfootSound("walking_dirt.mp3");
        walking[1] = new GreenfootSound("walking_stone.mp3");
        walking[2] = new GreenfootSound("walking_wood.mp3");
        isPlaying = false;
        healTimer.mark();
        totalXOffset = 0;
        totalYOffset = 0;
    }

    /**
     * Constantly checks for movement input and possible pickups around it
     */
    public void act(){
        GameWorld world = (GameWorld) getWorld();
        if(!GameWorld.getGUIOpened()){
            checkKeys();
            checkPickup();
            checkFalling();
        }
        
        if (isMoving) {
            Block blockBelow = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
            if(blockBelow != null && !(blockBelow instanceof Air)) {
                if(blockBelow.isDirt() && !isPlaying) {
                    walking[0].playLoop();
                    isPlaying = true;
                }
                else if (blockBelow.isStone() && !isPlaying) {
                    walking[1].playLoop();
                    isPlaying = true;
                }
                else if (blockBelow.isWood() && !isPlaying) {
                    walking[2].playLoop();
                    isPlaying = true;
                }
            }
        }
        else {
            for(int i=0; i<walking.length; i++) {
                walking[i].pause();
            }
            isPlaying = false;
        }
        
        // Instantly kill self if touching void
        if(isTouching(Void.class)){
            hp -= 20;
        }
        
        // If the player is alive, will attempt to heal 1hp every 2.5 seconds.  If it is dead, pause the world.
        if(hp > 0){
            if((healTimer.millisElapsed() > 2500) && (hp < 20)){
                hp++;
                healTimer.mark();
            }
        }
        else{
            world.pause();
        }
    }

    /**
     * Check movement input 
     * Uses a WAD system and checks respective conditions to see if they can be executed
     */
    public void checkKeys() {
        if (getWorld() instanceof GameWorld) {
            GameWorld world = (GameWorld) getWorld();
            boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
            isMoving = false;
            if(!GameWorld.getGUIOpened()){
                if(((Greenfoot.isKeyDown("space") || Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) && onGround()) && headClear()) {
                    jump();  
                }
                if((Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) && rightClear()) {
                    moveRight();
                    isMoving = true;
                }
                if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) && leftClear()) {
                    moveLeft();
                    isMoving = true;
                }


                MouseInfo mi = Greenfoot.getMouseInfo();

                if(mi != null) {
                    int button = mi.getButton();
                    if(button == 1) {
                        Block block = getBlockUnderCursor();
                        if(block != null) {
                            block.setPlayer(this);
                        }
                    }
                    if(button == 3) {
                        block = getBlockUnderCursor();
                        if(block != null && !activated && !GameWorld.getGUIOpened() && block instanceof Chest) {
                            chest = (Chest) block;
                            activated = true;
                            chest.addChest();
                            getWorld().addObject(chest.getChestGUI(), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
                            inventory.act();
                            GameWorld.setGUIOpened(true);
                            GameWorld.setOpenChest(true);
                        }
                        else if(block !=null && !activated1 && !GameWorld.getGUIOpened() && block instanceof CraftingTable) {
                            craftingTable = (CraftingTable) block;
                            activated = true;
                            craftingTable.openCraftingSystem();
                            getWorld().addObject(craftingTable.getCraftingSystem(), getWorld().getWidth()/2, getWorld().getHeight()/2);
                            inventory.act();
                            GameWorld.setGUIOpened(true);
                            GameWorld.setOpenCrafting(true);
                        }
                    }
                }
            }
        }
    }

    /**
    * Sets the activation state of the player.
    * 
    * @param active The activation state to set
    */
    public static void setActivated(boolean active){
        activated = active;
    }

    /**
     * Gets the block under the cursor.
     * 
     * @return The block under the cursor, or null if no block is found
     */
    protected Block getBlockUnderCursor() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            ArrayList<Block> blocks = (ArrayList<Block>) getWorld().getObjectsAt(mouseX, mouseY, Block.class);
            if (!blocks.isEmpty()) {
                return blocks.get(0); // Assuming only one block can be at this position
            }
        }
        return null;
    }

    /**
     * Checks if the player is on solid ground.
     * 
     * @return True if the player is on solid ground, false otherwise
     */
    protected boolean onGround() {
        Block under = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
        if(under != null) {
            if(under instanceof Air || under.isLiquid()) {
                return false;
            }
            else {
                return true;
            }
        }        
        return false;
    }

    /**
     * Checks if the player's head is clear and determines if it can jump.
     * 
     * @return True if the player's head is clear, false otherwise
     */
    protected boolean headClear(){
        Block above = (Block) getOneObjectAtOffset(0, -(getImage().getHeight()/2+10), Block.class);
        if(above != null) {
            if(above instanceof Air|| above.isLiquid()) {
                return true;
            }
            else {
                return false;
            }
        }        
        return true;        
    }

    /**
     * Checks if the player can continue moving right.
     * 
     * @return True if the player can move right, false otherwise
     */
    protected boolean rightClear(){ 
        Block right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, getImage().getHeight()/4, Block.class);
        if(right != null) {
            if(!(right instanceof Air|| right.isLiquid())){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, (getImage().getHeight()/4) * -1, Block.class);
        if(right != null) {
            if(!(right instanceof Air|| right.isLiquid())){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, getImage().getHeight()/2 - 5, Block.class);
        if(right != null) {
            if(!(right instanceof Air|| right.isLiquid())){
                return false;
            }
        }
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, (getImage().getHeight()/2) * -1, Block.class);
        if(right != null) {
            if(!(right instanceof Air|| right.isLiquid())){
                return false;
            }
        }
        return true;
    }    

    /**
     * Checks if the player can continue moving left.
     * 
     * @return True if the player can move left, false otherwise
     */
    protected boolean leftClear(){
        Block left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, getImage().getHeight()/4, Block.class);
        if(left != null) {
            if(!(left instanceof Air|| left.isLiquid())){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, (getImage().getHeight()/4) * -1, Block.class);
        if(left != null) {
            if(!(left instanceof Air|| left.isLiquid())){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, getImage().getHeight()/2 - 5, Block.class);
        if(left != null) {
            if(!(left instanceof Air|| left.isLiquid())){
                return false;
            }
        }
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, (getImage().getHeight()/2) * -1, Block.class);
        if(left != null) {
            if(!(left instanceof Air|| left.isLiquid())){
                return false;
            }
        }

        return true;
    }

    /**
     * Moves left by a pixel for each move speed
     * The loop is broken early if the left side is no longer clear for movement
     */
    protected void moveLeft(){
        GameWorld world = (GameWorld) getWorld();
        for(int i = 0; i < moveSpeed; i++){
            world.shiftWorld(1, 0);
            world.reverseShiftPlayer(1, 0);
            totalXOffset--;
            if(!leftClear()){
                return;
            }
        }
    }

    /**
     * Moves right by a pixel for each move speed
     * The loop is broken early if the left side is no longer clear for movement
     */
    protected void moveRight(){
        GameWorld world = (GameWorld) getWorld();
        for(int i = 0; i < moveSpeed; i++){
            world.shiftWorld(-1, 0);
            world.reverseShiftPlayer(-1, 0);
            totalXOffset++;
            if(!rightClear()){
                return;
            }
        }
    }

    /**
     * Checks if the player should fall
     */
    protected void checkFalling() {
        if(onGround()) {
            yVelocity = 0;
        } else {
            fall();
        }
    }

    /**
     * Accelerate downwards to fall
     */
    protected void fall() {
        GameWorld world = (GameWorld) getWorld();
        yVelocity = yVelocity + acceleration;
        world.shiftWorld(0, - yVelocity);
        world.reverseShiftPlayer(0, -yVelocity + 0.05);
        totalYOffset += yVelocity;
    }

    /**
     * Makes the player jump by giving it a small amount of velocity in the y-axis
     */
    protected void jump() {
        GameWorld world = (GameWorld) getWorld();
        yVelocity -= 4.4;
        world.shiftWorld(0, -yVelocity);
        world.reverseShiftPlayer(0, -yVelocity);
        totalYOffset += yVelocity;
    }

    /**
     * Gets a list of all items in a radius for pick up 
     */
    protected void checkPickup(){
        ArrayList<ItemDrop> dropsInRange = (ArrayList)getObjectsInRange(60, ItemDrop.class);
        for(ItemDrop item : dropsInRange){
            Inventory.addItem(item.getName());
            getWorld().removeObject(item);
        }
    }

    /**
     * Checks if a block is within a certain range of the player.
     * 
     * @param targetBlock The block to check.
     * @return True if the block is within range, false otherwise.
     */
    public boolean isBlockWithinRange(Block targetBlock) {
        // Get player's position
        int playerX = this.getX();
        int playerY = this.getY();
    
        // Get block's position
        int blockX = targetBlock.getX();
        int blockY = targetBlock.getY();
    
        // Calculate the direction vector
        int dirX = blockX - playerX;
        int dirY = blockY - playerY;
    
        // Check if the block is within a certain range
        if (dirX - dirY < 378) {
            return true;
        } else {
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
    public boolean isBlockVisible(Block targetBlock, int increment) {
        // Get player's position
        int playerX = this.getX();
        int playerY = this.getY() - increment;
    
        // Get block's position
        int blockX = targetBlock.getX();
        int blockY = targetBlock.getY();
    
        // Calculate the direction vector
        int dirX = blockX - playerX;
        int dirY = blockY - playerY;
        int steps = Math.max(Math.abs(dirX), Math.abs(dirY));
    
        // Normalize the direction vector
        double stepX = dirX / (double) steps;
        double stepY = dirY / (double) steps;
    
        // Cast the ray
        double currentX = playerX;
        double currentY = playerY;
        for (int i = 0; i < steps; i++) {
            // Increment to the position of the block
            currentX += stepX;
            currentY += stepY;
    
            // Check if there is a block at the current position
            Block block = (Block) getOneObjectAtOffset((int) Math.round(currentX - playerX), (int) Math.round(currentY - playerY), Block.class);
            if (block != null && block != targetBlock && !(block instanceof Air|| block.isLiquid())) {
                return false; // Block is obstructing the view
            }
        }
        return true; // No obstructions
    }
    
    /**
     * Knockbacks the player in a direction
     * Will be stopped pre-emptively if it hits a wall
     */
    public void knockBack(int direction){
        GameWorld world = (GameWorld) getWorld();
        if(direction == 1){
            for(int i = 0; i < 20; i++){
                if(leftClear()){
                    world.shiftWorld(1, 0);
                    world.reverseShiftPlayer(1, 0);
                    totalXOffset--;
                }
            }
        }
        else if(direction == 2){
            for(int i = 0; i < 20; i++){
                if(rightClear()){
                    world.shiftWorld(-1, 0);
                    world.reverseShiftPlayer(-1, 0);
                    totalXOffset++;
                }
            }    
        }
    }
    
    /**
     * Inflicts damage to the player.
     * 
     * @param damage The amount of damage to inflict.
     */
    public void doDamage(int damage) {
        this.hp -= damage;
    }
    
    /**
     * Returns how much the player has moved since the start of the game on the x-axis
     * 
     * @return the distance moved
     */
    public static int getTotalXOffset(){
        return (int) totalXOffset;
    }
    
    /**
     * Returns how much the player has moved since the start of the game on the y-axis
     * 
     * @return the distance moved
     */
    public static int getTotalYOffset(){
        return (int) totalYOffset;
    }
    
    /**
     * Gets the player's movement speed.
     * 
     * @return The movement speed.
     */
    public double getMoveSpeed() {
        return this.moveSpeed;
    }
    
    /**
     * Gets the amount of damage the player does
     * 
     * @return the damage the stat
     */
    public static int getDamage(){
        return damage;
    }
    
    /**
     * Gets the player's jump height.
     * 
     * @return The jump height.
     */
    public int getJumpHeight() {
        return this.jumpHeight;
    }
    
    /**
     * Gets the player's reach distance.
     * 
     * @return The reach distance.
     */
    public int getReach() {
        return this.reach;
    }
    
    /**
     * Checks if the player can drop items.
     * 
     * @return True if the player can drop items, false otherwise.
     */
    public boolean getCanDrop() {
        return this.canDrop;
    }
    
    /**
     * Gets the range within which the player can pick up items.
     * 
     * @return The pick-up range.
     */
    public int getPickUpRange() {
        return this.pickUpRange;
    }
    
    /**
     * Checks if the player is currently jumping.
     * 
     * @return True if the player is jumping, false otherwise.
     */
    public boolean isJumping() {
        return this.jumping;
    }
    
    /**
     * Gets the direction the player is facing.
     * 
     * @return True if the player is facing right, false if facing left.
     */
    public boolean getDirection() {
        return this.direction;
    }
    
    /**
     * Gets the player's current health points.
     * 
     * @return The current health points.
     */
    public int getHp() {
        return this.hp;
    }
    
    /**
     * Deactivates the player.
     */
    public void deactivate() {
        activated = false;
    }
    
}
