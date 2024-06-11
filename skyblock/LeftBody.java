import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Leftbody here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LeftBody extends BodyPart
{
    private GreenfootImage img;
    private Steve steve;
    public LeftBody(Steve steve){
        img = new GreenfootImage("steve/body_left.png");
        img.scale(16,48);
        setImage(img);
        this.steve = steve;
    }

    /**
     * Act - do whatever the Leftbody wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        setLocation(steve.getX(), steve.getY());
    }
}

