import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Items here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Items extends Actor
{
    private int X;
    private int Y;
    
    /**
     * Create an Items with given file name
     * 
     * @param file Location and name of file.
     */
    public Items(String file, World world){
        this.setImage(file);
        getImage().scale(48, 48);
        X = world.getWidth()/2;
        Y = world.getHeight()/2;
    }
    
    public void act(){
        // mouse dragging
        if (Greenfoot.mouseDragged(this))
        {
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            X = mouse.getX();
            Y = mouse.getX();
            setLocation(mouse.getX(), mouse.getY());
        }
    }
    
    public int getXPos(){
        return X;
    }
    
    public int getYPos(){
        return Y;
    }
}
