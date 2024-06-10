import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Empty here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Empty extends Item
{
    private String image;
    
    public Empty(int length, int width, World world, int X, int Y, int invX, int invY){
        super("block/air.png", length, width, world, false, X, Y, "air", invX, invY);
        image  = "block/air.png";
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
