import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodenShovel here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class WoodenShovel extends Item
{
    private String image;

    public WoodenShovel(World world, int X, int Y){
        super("items/Wooden_Shovel.png", 32, 32, world, true, X, Y, "wooden_shovel", false);
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
