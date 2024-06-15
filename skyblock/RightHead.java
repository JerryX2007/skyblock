import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the right head of the player character.
 * 
 * This class represents the right head of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class RightHead extends BodyPart
{
    private GreenfootImage img; // The image of the right head
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for RightHead class.
     * 
     * @param steve The instance of the Steve class to which this right head belongs.
     */
    public RightHead(Steve steve){
        img = new GreenfootImage("steve/head_right.png"); // Load the image of the right head
        img.scale(32,64); // Scale the image
        setImage(img); // Set the image of the right head
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the RightHead wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the right head part to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()-32);
    }

    /**
     * Reflects the image of the right head.
     * 
     * This method changes the image of the right head to a reflected version.
     */
    public void reflectImage(){
        GreenfootImage img1 = new GreenfootImage("steve/head_right1.png");
        img1.scale(32,64);
        setImage(img1);
    }

    /**
     * Reverts the image of the right head back to its original state.
     * 
     * This method sets the image of the right head back to its original state.
     */
    public void reflectBack(){
        setImage(img);
    }
}
