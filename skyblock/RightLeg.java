import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the right leg of the player character.
 * 
 * This class represents the right leg of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class RightLeg extends BodyPart
{
    private GreenfootImage img; // The image of the right leg
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for RightLeg class.
     * 
     * @param steve The instance of the Steve class to which this right leg belongs.
     */
    public RightLeg(Steve steve){
        img = new GreenfootImage("steve/leg_right.png"); // Load the image of the right leg
        img.scale(16,96); // Scale the image
        setImage(img); // Set the image of the right leg
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the RightLeg wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the right leg part to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()+16);
    }
}
