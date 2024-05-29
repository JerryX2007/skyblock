import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Log here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Log extends Block
{
    private static Color brown = new Color(77, 50, 36);
    public Log(){
        super(brown,2);
        GreenfootImage img = new GreenfootImage("wood.png");
        img.scale(32,32);
        setImage(img);
        isWood = true;
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