import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
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
    
    protected final int gravity = 2;
    protected double yVelocity;
    protected double xVelocity;
    protected double acceleration = 0.15;
    protected boolean direction; //true for facing right, false for left
    protected boolean isMoving;
    
    protected int moveLeftCounter;
    protected int moveRightCounter;
    
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
        isMoving = false;
        if(((Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) && onGround()) && headClear()) {
            jump();  
        }
        if((Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) && rightClear()) {
            moveRight();
            direction = true;
            isMoving = true;
        }
        if((Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) && leftClear()) {
            moveLeft();
            direction = false;
            isMoving = true;
        }
        if(Greenfoot.isKeyDown("shift")) {
            isMoving = false;
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
    
    protected boolean headClear(){
        Block above = (Block) getOneObjectAtOffset(0, -(getImage().getHeight()/2+2), Block.class);
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
            yVelocity = 0;
        }
        else {
            fall();
        }
    }
    
    protected void fall() {
        setLocation(getX(), getY() + yVelocity);
        yVelocity = yVelocity + acceleration;
    }
    
    protected void jump() {
        yVelocity -= 4.5;
        setLocation(getX(), getY() + yVelocity);
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
