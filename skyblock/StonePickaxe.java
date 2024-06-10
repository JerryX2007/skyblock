import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StonePickaxe here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StonePickaxe extends Item
{
    private String image;

    public StonePickaxe(int length, int width, World world, int X, int Y){
        super("items/Stone_Pickaxe.png", 25, 25, world, false, X, Y, "wooden_sword");
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

    public String getItemImage(){
        return image;
    }
}
