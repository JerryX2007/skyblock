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
    private MouseInfo mouse;
    private boolean dragging  = false;
    
    /**
     * Create an Items with given file name
     * 
     * @param file Location and name of file.
     * @param world World where items will reside in.
     */
    public Items(String file, World world){
        this.setImage(file);
        getImage().scale(48, 48);
        X = world.getWidth()/2;
        Y = world.getHeight()/2;
    }
    
    /**
     * Create an Items with given file name
     * 
     * @param file Location and name of file.
     * @param world World where items will reside in.
     * @param X x pos of the item.
     * @param Y y pos of the item.
     */
    public Items(String file, World world, int X, int Y){
        this.setImage(file);
        getImage().scale(48, 48);
        this.X = X;
        this.Y = Y;
    }
    
    public void act(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(Greenfoot.mousePressed(this) && !dragging){
            dragging = true;
        } else if (Greenfoot.mousePressed(this) && dragging){
            dragging = false;
        }
        if(Greenfoot.mouseDragEnded(this)){
            dragging = false;
        }
        
        // mouse dragging
        if (Greenfoot.mouseDragged(this) || dragging)
        {
            X = mouse.getX();
            Y = mouse.getY();
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
