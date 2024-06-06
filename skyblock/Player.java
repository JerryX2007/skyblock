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
    
    protected static int jumpStrength = 20;
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
        //checkPickup();
        snapOnTop();
    }
    
    public void checkKeys() {
        if(Greenfoot.isKeyDown("d") && rightClear()) {
            moveRight();
            isMoving = true;
        }
        else if(Greenfoot.isKeyDown("a") && leftClear()) {
            moveLeft();
            isMoving = true;
        }
        else if(Greenfoot.isKeyDown("shift")) {
            //Do shift animation type shit
            jumpStrength = 15;
            isMoving = false;
        }
        else{
            isMoving = false;
        }
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
        right = (Block) getOneObjectAtOffset(getImage().getWidth()/2 + 5, (getImage().getWidth()/4) * -1, Block.class);
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
        left = (Block) getOneObjectAtOffset((getImage().getWidth()/2 + 5) * -1, (getImage().getWidth()/4) * -1, Block.class);
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
            
        }
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
