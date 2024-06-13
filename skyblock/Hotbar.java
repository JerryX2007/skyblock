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
    private static Item[] hotbarSlots = new Item[9];
    
    public Hotbar(int scale, World world){
        super("hotbar.png", scale, world);
        this.world = world;
        
        for(int i = 0; i < 9; i++){
            hotbarSlots[i] = slots[i][0];
        }
        
        for (int i = 0; i < 9; i++) {
            world.addObject(hotbarSlots[i], 401 + xAdjust, world.getHeight() - 50);
            xAdjust += 60;
        }
        xAdjust = 0;
        yAdjust = 0;
    }
    
    public static Item[] getHotbarSlots(){
        return hotbarSlots;
    }
    
    /**
     * Act - do whatever the Hotbar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        for(int i = 0; i < 9; i++){
            hotbarSlots[i] = slots[i][0];
        }
    }
}
