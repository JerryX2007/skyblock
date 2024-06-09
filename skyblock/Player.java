import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @author Jerry Xing
 * @version (a version number or a date)
 */
public abstract class Player extends SuperSmoothMover
{
    protected static int moveSpeed;
    protected static int jumpHeight;
    protected static int reach;
    protected static boolean canDrop;
    protected static int pickUpRange;
    protected static boolean jumping;
    
    protected static double jumpStrength = 1.3;
    protected final int gravity = 2;
    protected double vSpeed;
    protected double acceleration = 0.1;
    protected boolean direction; //true for facing right, false for left
    protected boolean isMoving;
    public Player(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange, boolean jumping) {
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.reach = reach;
        this.canDrop = canDrop;
        this.pickUpRange = pickUpRange;
        this.jumping = jumping;
        direction = false;
        isMoving = false;
    }
    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        checkKeys();
        checkFalling();
        checkPickup();
        //snapOnTop();
        
    }
    
    public void checkKeys() {
        if((Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) && rightClear()) {
            jumpStrength = 1.3;
            moveRight();
            direction = true;
            isMoving = true;
        }
        else if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) && leftClear()) {
            jumpStrength = 1.3;
            moveLeft();
            direction = false;
            isMoving = true;
        }
        else if(Greenfoot.isKeyDown("shift")) {
            //Do shift animation type shit
            jumpStrength = 0.8;
            isMoving = false;
        }
        else if(Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) {
            jump();  
        }
        else{
            isMoving = false;
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
        }
    }
    
    protected Block getBlockUnderCursor() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            ArrayList<Block> blocks = (ArrayList<Block>) getWorld().getObjectsAt(mouseX, mouseY, Block.class);
            if (!blocks.isEmpty()) {
                //System.out.println("Got block");
                return blocks.get(0); // Assuming only one block can be at this position
            }
        }
        return null;
    }
    protected void fall() {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }
    protected void snapOnTop() {
        ArrayList<Block> blocks = (ArrayList<Block>) getIntersectingObjects(Block.class);
        if(!blocks.isEmpty()) {
            for(Block b : blocks) {
                if(!(b instanceof Air)) {
                    Block under = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
                    if(under != null && !(under instanceof Air)) {
                        setLocation(getX(), getY() - under.getImage().getHeight()/2);
                    }
                }
            }
        }
    }
    protected boolean onGround() {
        Block under = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2+1, Block.class);
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
    
    protected void moveLeft(){
        for(int i = 0; i < moveSpeed; i++){
            setLocation(getX() - 1, getY());
            if(!rightClear()){
                return;
            }
        }
    }
    protected void moveRight(){
        for(int i = 0; i < moveSpeed; i++){
            setLocation(getX() + 1, getY());
            if(!leftClear()){
                return;
            }
        }
    }
    
    protected void checkFalling() {
        if(onGround()) {
            vSpeed = 0;
        }
        else {
            fall();
        }
    }
    
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
        if(dirX - dirY < 270) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean isBlockVisible(Block targetBlock) {
        // Get player's position
        int playerX = this.getX();
        int playerY = this.getY();
    
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

    
    protected void jump() {
        vSpeed = vSpeed - jumpStrength;
        jumping = true;
        fall();
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
}
