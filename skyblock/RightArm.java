import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LeftArm here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RightArm extends BodyPart
{
    private GreenfootImage img;
    private Steve steve;
    private int actNum;
    private boolean isPunching;
    private GreenfootImage img1;
    public RightArm(Steve steve){
        img = new GreenfootImage("steve/arm_right.png");
        img.scale(16,96);
        setImage(img);
        this.steve = steve;
        isPunching = false;
        img1 = new GreenfootImage("steve/side_arm.png");
        img1.scale(16,96);
        actNum = 0;
    }
    /**
     * Act - do whatever the LeftArm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        setLocation(steve.getX(), steve.getY()-32);
    }

}
