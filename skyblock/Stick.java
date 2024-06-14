import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StonePickaxe here.
 * 
 * Dylan Dinesh
 * @version (a version number or a date)
 */
public class Stick extends Item
{
    private String image;

    public Stick(int length, int width, World world, int X, int Y){
        super("items/stick.png", 25, 25, world, true, X, Y, "stick");
        image  = "items/stick.png";
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
