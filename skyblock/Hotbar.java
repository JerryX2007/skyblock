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
    private static Item[] temp = new Item[9];
    
    public Hotbar(int scale, World world){
        super("hotbar.png", scale, world);
        this.world = world;
        
        for(int i = 0; i < 9; i++){
            temp[i] = slots[i][0];
        }
        hotbarSlots = temp.clone();
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
            temp[i] = slots[i][0];
        }
        hotbarSlots = temp.clone();
    }
}
