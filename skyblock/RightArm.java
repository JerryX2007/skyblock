import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the right arm of the player character.
 * 
 * This class represents the right arm of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class RightArm extends BodyPart
{
    private GreenfootImage img; // The image of the right arm
    private Steve steve; // The instance of the Steve class
    private int actNum; // A counter for actions
    private boolean isPunching; // Indicates whether the arm is currently punching
    private GreenfootImage img1; // An alternative image for the arm
    
    /**
     * Constructor for RightArm class.
     * 
     * @param steve The instance of the Steve class to which this right arm belongs.
     */
    public RightArm(Steve steve){
        img = new GreenfootImage("steve/arm_right.png"); // Load the image of the right arm
        img.scale(16,96); // Scale the image
        setImage(img); // Set the image of the right arm
        this.steve = steve; // Assign the Steve instance
        isPunching = false; // Initialize punching state
        img1 = new GreenfootImage("steve/side_arm.png"); // Load an alternative image for the arm
        img1.scale(16,96); // Scale the alternative image
        actNum = 0; // Initialize action counter
    }

    /**
     * Act - do whatever the RightArm wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the right arm to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()-32);
    }

}
