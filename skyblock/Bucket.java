import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StonePickaxe here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class Bucket extends Item
{
    private String image;

    public Bucket(int length, int width, World world, int X, int Y){
        super("items/Bucket.png", 32, 32, world, true, X, Y, "bucket", false);
        image  = "items/Bucket.png";
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
