import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents the left side of the head.
 * 
 * This class represents the left side of the head of the player character. It extends BodyPart
 * to inherit basic properties and behaviors of a body part.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class LeftHead extends BodyPart
{
    private GreenfootImage img; // The image of the left side of the head
    private Steve steve; // The instance of the Steve class
    
    /**
     * Constructor for LeftHead class.
     * 
     * @param steve The instance of the Steve class to which this left head belongs.
     */
    public LeftHead(Steve steve){
        img = new GreenfootImage("steve/head_left.png"); // Load the image of the left side of the head
        img.scale(32,64); // Scale the image
        setImage(img); // Set the image of the left side of the head
        this.steve = steve; // Assign the Steve instance
    }

    /**
     * Act - do whatever the LeftHead wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Move the left side of the head to the position relative to the Steve actor
        setLocation(steve.getX(), steve.getY()-32);
    }

    /**
     * Reflects the image of the left side of the head.
     */
    public void reflectImage(){
        GreenfootImage img1 = new GreenfootImage("steve/head_left1.png"); // Load the reflected image
        img1.scale(32,64); // Scale the image
        setImage(img1); // Set the reflected image
    }

    /**
     * Reflects the image of the left side of the head back to its original state.
     */
    public void reflectBack(){
        setImage(img); // Set the image back to its original state
    }
}
