import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dirt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dirt extends Block
{
    private static Color brown = new Color(185, 122, 87);
    public Dirt(){
        super(brown,1);
        GreenfootImage img = new GreenfootImage("dirt.png");
        img.scale(32,32);
        setImage(img);
        isDirt = true;
    }
    /**
     * Act - do whatever the Dirt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
    }
    public void drop(){
        
    }
}
