import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StoneShovel here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class StoneShovel extends Item
{
    private String image;

    public StoneShovel(World world, int X, int Y){
        super("items/Stone_Shovel.png", 32, 32, world, true, X, Y, "stone_shovel");
        image  = "items/Stone_Shovel.png";
    }

    /**
     * Act - do whatever the Empty wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
