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
    protected int vSpeed;
    protected int acceleration = 1;
    public Player(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange, boolean jumping) {
        this.moveSpeed = moveSpeed;
        this.jumpHeight = jumpHeight;
        this.reach = reach;
        this.canDrop = canDrop;
        this.pickUpRange = pickUpRange;
        this.jumping = jumping;
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
    }
    
    public void checkKeys() {
        if(Greenfoot.isKeyDown("d")) {
            setLocation(getX()+moveSpeed, getY());
        }
        if(Greenfoot.isKeyDown("a")) {
            setLocation(getX()-moveSpeed, getY());
        }
        if(Greenfoot.isKeyDown("shift")) {
            //Do shift animation type shit
            jumpStrength = 15;
        }
    }
    
    protected void fall() {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }
    
    protected boolean onGround() {
        Actor under = getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
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
    protected void checkFalling() {
        if(!onGround()) {
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
}
