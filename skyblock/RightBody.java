import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the right body part of the player character.
 * 
 * This class represents the right body part of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class RightBody extends BodyPart
{
    private GreenfootImage img; // The image of the right body
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for RightBody class.
     * 
     * @param steve The instance of the Steve class to which this right body belongs.
     */
    public RightBody(Steve steve){
        img = new GreenfootImage("steve/body_right.png"); // Load the image of the right body
        img.scale(16,48); // Scale the image
        setImage(img); // Set the image of the right body
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the RightBody wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the right body part to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY());
    }
}
