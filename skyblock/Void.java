import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Everything that comes into contact with the void instantly dies
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Void extends Block
{
    
    private static Color brown = new Color(77, 50, 36);

    public Void(){
        super(brown,1000000000, "void");
        setImage(new GreenfootImage(16, 16));
    }

    /**
     * Act - do whatever the Dirt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
    }
}
