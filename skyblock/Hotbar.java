import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Hotbar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hotbar extends GUI
{
    private int xAdjust = 0;
    private int yAdjust = 0;

    
    public Hotbar(int scale, World world){
        super("hotbar.png", scale, world);
        this.world = world;
        
        for (int i = 0; i < 9; i++) {
            world.addObject(slots[i][0], 401 + xAdjust, world.getHeight() - 50);
            xAdjust += 60;
        }
        xAdjust = 0;
        yAdjust = 0;
    }
    
    /**
     * Act - do whatever the Hotbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
