import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the left leg of the player character.
 * 
 * This class represents the left leg of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class LeftLeg extends BodyPart
{
    private GreenfootImage img; // The image of the left leg
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for LeftLeg class.
     * 
     * @param steve The instance of the Steve class to which this left leg belongs.
     */
    public LeftLeg(Steve steve){
        img = new GreenfootImage("steve/leg_left.png"); // Load the image of the left leg
        img.scale(16,96); // Scale the image
        setImage(img); // Set the image of the left leg
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the LeftLeg wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the left leg to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()+16);
    }
}
