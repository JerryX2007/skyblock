import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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
    private boolean draggable = true;
    private boolean snapped = false;
    
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
    
    /**
     * Create an Items with given file name
     * 
     * @param file Location and name of file.
     * @param world World where items will reside in.
     * @param X x pos of the item.
     * @param Y y pos of the item.
     * @param length Length of the item
     * @param width Width of the item
     */
    public Items(String file, World world, int X, int Y, int length, int width){
        this.setImage(file);
        getImage().scale(length, width);
        this.X = X;
        this.Y = Y;
    }
    
    public Items(String file, int length, int width, World world, boolean draggable, int X, int Y){
        this.setImage(file);
        getImage().scale(length, width);
        this.draggable = draggable;
        this.X = X;
        this.Y = Y;
    }
    
    public void act(){
        if(draggable){
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(Greenfoot.mousePressed(this) && !dragging){
                dragging = true;
                snapped = false;
            } else if (Greenfoot.mousePressed(this) && dragging){
                dragging = false;
            }
            if(Greenfoot.mouseDragged(this)){
                dragging = true;
                snapped = false;
            } else if(Greenfoot.mouseDragEnded(this)){
                dragging = false;
            }
            
            // mouse dragging
            if (dragging){
                snapped = false;
                X = mouse.getX();
                Y = mouse.getY();
                setLocation(mouse.getX(), mouse.getY());
            }
            
            if(!dragging && !snapped){
                System.out.println(toString());
                
                if(!getObjectsInRange(500, Items.class).isEmpty()){
                    int dist=500;
                    
                    while(!getObjectsInRange(dist, Items.class).isEmpty()){
                        dist--;
                    }
                    dist++;
 
                    Actor temp = (Items) getObjectsInRange(dist,Items.class).get(0);
                    X = temp.getX();
                    Y = temp.getY();
                    snapped = true;
                    setLocation(X, Y);
                }
            }
        }
    }
    
    public boolean getDraggable(){
        return draggable;
    }
    
    public String toString(){
        return "(" + X + ", " + Y + ")";
    }
    
    public int getXPos(){
        return X;
    }
    
    public int getYPos(){
        return Y;
    }
}
