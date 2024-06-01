import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RightLeg here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RightLeg extends BodyPart
{
    private GreenfootImage img;
    private Steve steve;
    public RightLeg(Steve steve){
        img = new GreenfootImage("steve/leg_right.png");
        img.scale(16,96);
        setImage(img);
        this.steve = steve;
    }
    /**
     * Act - do whatever the RightLeg wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        setLocation(steve.getX(), steve.getY()+16);
    }
}
