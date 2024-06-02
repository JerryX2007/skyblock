import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    }
    
    public void checkKeys() {
        if(Greenfoot.isKeyDown("d")) {
            setLocation(getX()+moveSpeed, getY());
            direction = true;
            isMoving = true;
        }
        else if(Greenfoot.isKeyDown("a")) {
            setLocation(getX()-moveSpeed, getY());
            direction = false;
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
    
    protected boolean onGround() {
        Actor under = getOneObjectAtOffset(0, getImage().getHeight()/2, Block.class);
        if(under != null) {
            if(under instanceof Air|| under instanceof Sapling) {
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }
    protected void checkFalling() {
        if(onGround()) {
            vSpeed = 0;
        }
        else {
            fall();
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
