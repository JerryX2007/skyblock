import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LeftLeg here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LeftLeg extends BodyPart
{
    private GreenfootImage img;
    private Steve steve;
    public LeftLeg(Steve steve){
        img = new GreenfootImage("steve/leg_left.png");
        img.scale(16,96);
        setImage(img);
        this.steve = steve;
    }
    /**
     * Act - do whatever the LeftLeg wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        setLocation(steve.getX(), steve.getY()+16);
    }
}
