import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Description to be added
 * 
 * @author Jerry Xing, Evan Xi
 * 
 * With help from Benny Wang
 */
public abstract class Player extends SuperSmoothMover{
    protected static int moveSpeed;
    protected static int jumpHeight;
    protected static int reach;
    protected static boolean canDrop;
    protected static int pickUpRange;
    protected static boolean jumping;
    protected Inventory inventory;

    protected final int gravity = 2;
    protected double yVelocity;
    protected double xVelocity;
    protected double acceleration = 0.15;
    protected boolean direction; //true for facing right, false for left
    protected boolean isMoving;
    protected boolean isSprinting = false;
    protected int sprintToggleCD = 50;
    protected static boolean activated;
    
    protected Chest block;
    //protected Chest chest;

    protected int moveLeftCounter;
    protected int moveRightCounter;
    protected int hp;
    
    
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
    }

    /**
     * Constantly checks for movement input and possible pickups around it
     */
    public void act(){
        checkKeys();
        checkFalling();
        checkPickup();
    }

    /**
     * Check movement input 
     * Uses a WASD system and checks respective conditions to see if they can be executed
     * Can toggle sneaking and sprinting
     */
    public void checkKeys() {
        
        GameWorld world = (GameWorld) getWorld();
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        isMoving = false;
        
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
        if(Greenfoot.isKeyDown("shift")) {
            isMoving = false;
        }
        if(Greenfoot.isKeyDown("control") && sprintToggleCD < 0){
            if(isSprinting){
                moveSpeed -= 1;
                isSprinting = false;
            }
            else{
                moveSpeed += 1;
                isSprinting = true;
            }
            sprintToggleCD = 50;
        }
        sprintToggleCD--;

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
                try{
                    block = (Chest) getBlockUnderCursor();
                    if(block != null && !activated && !GameWorld.getGUIOpened()) {
                        //System.out.println("test");
                        activated = true;
                        
                        block.addChest();
                        getWorld().addObject(block.getChestGUI(), getWorld().getWidth() / 2, getWorld().getHeight() / 2);
                        inventory.act();
                        GameWorld.setGUIOpened(true);
                        GameWorld.setOpenChest(true);
                    }
                } catch (ClassCastException e){
                         
                }
                
            }
        }
        
    }
    
    public static void setActivated(boolean activated){
        activated = activated;
    }

    /**
     * Checks for blocks being mined by the user
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
     * Checks if the player in on solid ground
     * Gets the block directly under the user.  If there exists a block and it isn't an air block, return true.
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
     * Checks if the player's head is clear and determines if it can jump
     * Gets the block directly above the user.  If there exists a block that isn't an air block, return false.  Otherwise return true.
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
     * Checks if the user can continue moving right
     * Uses 4 collision points to the right of the user to detect any non-air blocks
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
     * Checks if the user can continue moving left
     * Uses 4 collision points to the left of the user to detect any non-air blocks
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
     * The loop is broken early if the left side is no longer clear for movement
     */
    protected void moveLeft(){
        GameWorld world = (GameWorld) getWorld();
        for(int i = 0; i < moveSpeed; i++){
            world.shiftWorld(1, 0);
            world.reverseShiftPlayer(1, 0);
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
            if(!rightClear()){
                return;
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
        GameWorld world = (GameWorld) getWorld();
        world.shiftWorld(0, - yVelocity);
        world.reverseShiftPlayer(0, -yVelocity);
        yVelocity = yVelocity + acceleration;
    }

    /**
     * Gains a small amount of momentum upwards to jump
     */
    protected void jump() {
        GameWorld world = (GameWorld) getWorld();
        yVelocity -= 4.9;
        world.shiftWorld(0, -yVelocity);
        world.reverseShiftPlayer(0, -yVelocity);
    }

    /**
     * Gets a list of all items in a radius for pick up 
     * If there is space in the inventory, pick it up
     */
    protected void checkPickup(){
        ArrayList<ItemDrop> dropsInRange = (ArrayList)getObjectsInRange(60, ItemDrop.class);
        for(ItemDrop item : dropsInRange){
            if(Inventory.hasSpaceFor(item.getName())){
                Inventory.addItem(item.getName());
                getWorld().removeObject(item);
            }
        }
    }

    public boolean isBlockWithinRange(Block targetBlock) {
        // Get player's position
        int playerX = this.getX();
        int playerY = this.getY();

        // Get block's position
        int blockX = targetBlock.getX();
        int blockY = targetBlock.getY();

        //Calculate the direction vector
        int dirX = blockX - playerX;
        int dirY = blockY - playerY;
        //System.out.println(dirX);
        //System.out.println(dirY);
        if(dirX - dirY < 378) {
            return true;
        }
        else {
            return false;
        }
    }

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
            //Increment to the position of the block
            currentX += stepX;
            currentY += stepY;

            // Check if there is a block at the current position
            Block block = (Block) getOneObjectAtOffset((int) Math.round(currentX - playerX), (int) Math.round(currentY - playerY), Block.class);
            if (block != null && block != targetBlock && !(block instanceof Air)) {
                return false; // Block is obstructing the view

            }
        }
        return true; // No obstructions
    }
    
    //this is just a testing class
    public void doDamage(int damage){
        this.hp -= damage;
        if(hp <= 0) {
            getWorld().removeObject(this);
        }
    }
    
    
    public int getMoveSpeed() {
        return this.moveSpeed;
    }
    public int getJumpHeight() {
        return this.jumpHeight;
    }
    public int getReach() {
        return this.reach;
    }
    public boolean getCanDrop() {
        return this.canDrop;
    }
    public int getPickUpRange() {
        return this.pickUpRange;
    }
    public boolean isJumping() {
        return this.jumping;
    }
    public boolean getDirection(){
        return this.direction;
    }
    public int getHp(){
        return this.hp;
    }
    public void deactivate() {
        activated = false;
    }
}
