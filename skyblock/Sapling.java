import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class alsjflsa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Log here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sapling extends Item
{
    private static Color brown = new Color(77, 50, 36);
    private GreenfootImage img;
    public Sapling(){
        img = new GreenfootImage("block/sapling.png");
        img.scale(64,64);
        setImage(img);
    }
    /**
     * Act - do whatever the Dirt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
    
    public void drop(){
        
    }
}
