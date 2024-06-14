import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Empty slot
 * 
 * @author Benny
 * @version 1.0.0
 */
public class Empty extends Item
{
    private String image;
    
    /*
     * Constructor for an empty item
     * 
     * @param length length of the item
     * @param width width of the item
     * @param world the world that the item resides in
     * @param X X positon of the item
     * @param Y Y position of the item
     */
    public Empty(int length, int width, World world, int X, int Y){
        super("block/air.png", length, width, world, false, X, Y, "air");
        image  = "block/air.png";
    }
    
    /**
     * Act - do whatever the Empty wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    /*
     * Getter for image
     * 
     * @return image Type of image as a String
     */
    public String getItemImage(){
        return image;
    }
    
    
}
