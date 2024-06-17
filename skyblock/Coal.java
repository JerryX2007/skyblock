import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StonePickaxe here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class Coal extends Item
{
    private String image;

    public Coal(int length, int width, World world, int X, int Y){
        super("items/Coal.png", 32, 32, world, true, X, Y, "coal", false);
        image = "items/Coal.png";
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
