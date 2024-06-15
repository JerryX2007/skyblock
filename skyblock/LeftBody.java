import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the left part of the body.
 * 
 * This class represents the left part of the body of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class LeftBody extends BodyPart
{
    private GreenfootImage img; // The image of the left body
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for LeftBody class.
     * 
     * @param steve The instance of the Steve class to which this left body belongs.
     */
    public LeftBody(Steve steve){
        img = new GreenfootImage("steve/body_left.png"); // Load the image of the left body
        img.scale(16,48); // Scale the image
        setImage(img); // Set the image of the left body
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the LeftBody wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the left body part to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY());
    }
}
