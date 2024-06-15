import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StoneSword here.
 * 
 * Jerry Xing
 * @version (a version number or a date)
 */
public class StoneSword extends Item
{
    private String image;

    public StoneSword(World world, int X, int Y){
        super("items/Stone_Sword.png", 32, 32, world, true, X, Y, "stone_sword");
        image  = "items/Stone_Sword.png";
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
