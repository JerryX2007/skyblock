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
    protected int X;
    protected int Y;
    protected MouseInfo mouse;
    protected boolean dragging  = false;
    protected boolean draggable = true;
    protected boolean snapped = false;
    protected String type;
    protected int tempX;
    protected int tempY;
    protected World world;
    
    /**
     * Create an Items with given file name
     * 
     * @param file Location and name of file.
     * @param world World where items will reside in.
     */
    public Items(String file, World world, String type){
        this.setImage(file);
        getImage().scale(48, 48);
        X = world.getWidth()/2;
        Y = world.getHeight()/2;
        this.type = type;
        this.world = world;
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
        this.world = world;
    
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
    public Items(String file, World world, int X, int Y, int length, int width, String type){
        this.setImage(file);
        getImage().scale(length, width);
        this.X = X;
        this.Y = Y;
        this.type = type;
        tempX = X;
        tempY = Y;
        this.world = world;
    }
    
    public Items(String file, int length, int width, World world, boolean draggable, int X, int Y, String type){
        this.setImage(file);
        getImage().scale(length, width);
        this.draggable = draggable;
        this.X = X;
        this.Y = Y;
        this.type = type;
        tempX = X;
        tempY = Y;
        this.world = world;
    }
    
    private boolean firstTime = true;
    
    public void act(){
        if(draggable){
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(Greenfoot.mousePressed(this) && !dragging){
                dragging = true;
                snapped = false;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                }
            } else if (Greenfoot.mousePressed(this) && dragging){
                dragging = false;
                firstTime = true;
            }
            if(Greenfoot.mouseDragged(this)){
                dragging = true;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                }
                snapped = false;
            } else if(Greenfoot.mouseDragEnded(this)){
                dragging = false;
                firstTime = true;
            }
            
            // mouse dragging
            if (dragging){
                snapped = false;
                X = mouse.getX();
                Y = mouse.getY();
                setLocation(mouse.getX(), mouse.getY());
            }
            
            if(!dragging && !snapped){
                if(!getObjectsInRange(1000, Items.class).isEmpty()){
                    int dist=1000;
                    
                    while(!getObjectsInRange(dist, Items.class).isEmpty()){
                        dist--;
                    }
                    dist++;
                    
                    Items temp = (Items) getObjectsInRange(dist,Items.class).get(0);
                    
                    X = temp.getX();
                    Y = temp.getY();
                    
                    
                    ArrayList<Items> temp1 = (ArrayList<Items>) getIntersectingObjects(Items.class);
                    for(Items i : temp1){
                        if(i.getType().equals("air") || i.getType().equals(getType())){
                            setLocation(X, Y);
                            tempX = getX();
                            tempY = getY();
                            X = getX();
                            Y = getY();
                        } else {
                            setLocation(tempX, tempY);
                            break;
                        }
                    }
                    temp1.clear();
                    snapped = true;
                }
            }
            
        }
        if(!draggable){
            ArrayList<Items> temp2 = (ArrayList<Items>) getIntersectingObjects(Items.class);
            if(temp2.size() == 0){
                type = "air";
            }
            if(temp2.size() > 0){
                Items test = (Items) getOneIntersectingObject(Items.class);
                if(test.isSnapped()){
                    type = test.getType();
                }
            }
            temp2.clear();
        }
    }
    
    public boolean getDraggable(){
        return draggable;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String toString(){
        return "(" + X + ", " + Y + ")";
    }
    
    public int getXPos(){
        return X;
    }
    
    public boolean isSnapped(){
        return snapped;
    }
    
    public int getYPos(){
        return Y;
    }
    
    public String getType(){
        return type;
    }
}
