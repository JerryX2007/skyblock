import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CobbleStone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CobbleStone extends Block
{
    public CobbleStone(){
        super(Color.GRAY,5);
        GreenfootImage img = new GreenfootImage("cobblestone.png");
        img.scale(32,32);
        setImage(img);
        isStone = true;
    }
    /**
     * Act - do whatever the Grass wants to do. This method is called whenever
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