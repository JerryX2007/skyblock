import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the left arm of the player.
 * 
 * This class represents the left arm of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class LeftArm extends BodyPart
{
    private GreenfootImage img; // The image of the left arm
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for LeftArm class.
     * 
     * @param steve The instance of the Steve class to which this left arm belongs.
     */
    public LeftArm(Steve steve){
        img = new GreenfootImage("steve/arm_left.png"); // Load the image of the left arm
        img.scale(16,96); // Scale the image
        setImage(img); // Set the image of the left arm
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the LeftArm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the left arm to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()-32);
    }
}
