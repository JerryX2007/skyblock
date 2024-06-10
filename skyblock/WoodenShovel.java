import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodenShovel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WoodenShovel extends Item
{
    private String image;

    public WoodenShovel(int length, int width, World world, int X, int Y){
        super("items/Wooden_Shovel.png", 25, 25, world, false, X, Y, "wooden_sword");
        image  = "items/Wooden_Shovel.png";
    }

    /**
     * Act - do whatever the Empty wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }

    public String getItemImage(){
        return image;
    }
}
