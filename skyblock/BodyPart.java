import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a body part in the game.
 * 
 * This class represents a body part in the game. It extends SuperSmoothMover
 * to provide smooth movement.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class BodyPart extends SuperSmoothMover
{
    protected int xPos; // The x-coordinate of the body part
    protected int yPos; // The y-coordinate of the body part
    
    /**
     * Act - do whatever the BodyPart wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    
    /**
     * Sets the x and y coordinates of the body part.
     * 
     * @param x The x-coordinate to set.
     * @param y The y-coordinate to set.
     */
    public void setXY(int x, int y){
        xPos = x;
        yPos = y;
    }
    
    /**
     * Sets the location of the body part based on its x and y coordinates.
     */
    public void setPlace(){
        setLocation(xPos, yPos);
    }
}
