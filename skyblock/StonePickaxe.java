import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StonePickaxe here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class StonePickaxe extends Item
{
    private String image;

    public StonePickaxe(World world, int X, int Y){
        super("items/Stone_Pickaxe.png", 32, 32, world, true, X, Y, "stone_pickaxe", false);
        image  = "items/Stone_Pickaxe.png";
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
