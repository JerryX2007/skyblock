import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Steve here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Steve extends Player
{
    private GreenfootImage hitBox;
    private LeftHead leftHead;
    private RightHead rightHead;
    private LeftArm leftArm;
    private RightArm rightArm;
    private LeftBody leftBody;
    private RightBody rightBody;
    private LeftLeg leftLeg;
    private RightLeg rightLeg;
    private int actNum;
    public Steve(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange) {
        super(moveSpeed, jumpHeight, reach, canDrop, pickUpRange, true);
        hitBox = new GreenfootImage("steve/hitBox.png");
        hitBox.scale(40,128);
        setImage(hitBox);
        //add bodyparts
        leftHead = new LeftHead(this); leftArm = new LeftArm(this); leftBody = new LeftBody(this); leftLeg = new LeftLeg(this);
        rightHead = new RightHead(this); rightArm = new RightArm(this); rightBody = new RightBody(this); rightLeg = new RightLeg(this);
        int actNum = 0;
    }

    /**
     * Act - do whatever the Steve wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(actNum == 0){
            getWorld().addObject(leftLeg,0,0);  getWorld().addObject(rightLeg,0,0);
            getWorld().addObject(leftBody,0,0); getWorld().addObject(rightBody,0,0);
            getWorld().addObject(leftArm,0,0);  getWorld().addObject(rightArm,0,0);
            getWorld().addObject(leftHead,0,0); getWorld().addObject(rightHead,0,0);
        }
        super.act();
        updateLayering();
    }

    public void updateLayering() {
        // Clear existing objects
        getWorld().removeObject(leftLeg);
        getWorld().removeObject(rightLeg);
        getWorld().removeObject(leftBody);
        getWorld().removeObject(rightBody);
        getWorld().removeObject(leftArm);
        getWorld().removeObject(rightArm);
        getWorld().removeObject(leftHead);
        getWorld().removeObject(rightHead);

        if (!direction) {
            // Facing Left
            getWorld().addObject(rightLeg, 0, 0);
            getWorld().addObject(rightBody, 0, 0);
            getWorld().addObject(rightArm, 0, 0);
            getWorld().addObject(rightHead, 0, 0);

            getWorld().addObject(leftLeg, 0, 0);
            getWorld().addObject(leftBody, 0, 0);
            getWorld().addObject(leftArm, 0, 0);
            getWorld().addObject(leftHead, 0, 0);
        } else {
            // Facing Right
            getWorld().addObject(leftLeg, 0, 0);
            getWorld().addObject(leftBody, 0, 0);
            getWorld().addObject(leftArm, 0, 0);
            getWorld().addObject(leftHead, 0, 0);

            getWorld().addObject(rightLeg, 0, 0);
            getWorld().addObject(rightBody, 0, 0);
            getWorld().addObject(rightArm, 0, 0);
            getWorld().addObject(rightHead, 0, 0);
        }
    }
}
