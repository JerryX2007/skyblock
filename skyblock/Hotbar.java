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
            hotbarSlots[i] = new Empty(16, 16, world, 401 + xAdjust, 718);
            xAdjust += 60;
        }
    }
    
    public static Item[] getHotbarSlots(){
        return hotbarSlots;
    }
    
    public static void addHotbar(){
        int xAdjust = 0;
        for (Item i : hotbarSlots){
            world.addObject(i, 401 + xAdjust, 718);
            xAdjust += 60;
        }
    }
    
    public static void removeHotbar(){
        for (Item i : hotbarSlots){
            world.removeObject(i);
        }
    }
    
    /**
     * Act - do whatever the Hotbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        
    }
}
