import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodenSword here.
 * 
 * Dylan Dinesh
 * @version (a version number or a date)
 */
public class WoodenSword extends Item
{
    private String image;

    public WoodenSword(World world, int X, int Y){
        super("items/Wooden_Sword.png", 32, 32, world, true, X, Y, "wooden_sword");
        image  = "items/Wooden_Sword.png";
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
