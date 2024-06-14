import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class WoodenPickaxe here.
 * 
 * Dylan Dinesh
 * @version (a version number or a date)
 */
public class WoodenPickaxe extends Item
{
    private String image;

    public WoodenPickaxe(World world, int X, int Y){
        super("items/Wooden_Pickaxe.png", 32, 32, world, true, X, Y, "wooden_pickaxe");
        image  = "items/Wooden_Pickaxe.png";
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
