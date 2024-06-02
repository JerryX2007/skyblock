import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Iterator;
import com.google.common.collect.Iterators;

/**
 * @author Benny
 * @version June 1, 2024
 * 
 * Thank to ChatGPT for helping me write this at 11:46pm
 */
public class Items extends Actor {
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
    private String image;

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
        image = file;
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
        image = file;
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
        image = file;
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
        image = file;
    }

    private boolean firstTime = true;
    private ArrayList<Items> touchingItems;
    private boolean gotItems = false;
    private ArrayList<Items> numItems;
    private Label counter = new Label(1, 20);
    private boolean addedCounter = false;

    public void act(){
        counter.setLineColor(Label.getTransparent());
        counter.setLocation(getX() + 15, getY() + 15);
        if(draggable){
            numItems = (ArrayList<Items>) getIntersectingObjects(Items.class);
            Iterator<Items> test = numItems.iterator();
            while(test.hasNext()){
                Items i = test.next();
                if(!i.getType().equals(getType()) || i.getItemImage().equals("block/air.png")){
                    test.remove();
                }
            }
            int sizeOfNumItems = numItems.size() + 1;
            if(sizeOfNumItems > 1){
                counter.setValue(sizeOfNumItems);
                
                getWorld().addObject(counter, getX() + 15, getY() + 15);
                
            } else if(sizeOfNumItems == 1){
                getWorld().removeObject(counter);
            }
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(Greenfoot.mousePressed(this) && !dragging){
                dragging = true;
                snapped = false;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                    touchingItems = (ArrayList<Items>) getIntersectingObjects(Items.class);
                    Iterator<Items> iterator = touchingItems.iterator();
                    while(iterator.hasNext()){
                        Items i = iterator.next();
                        if(!i.getType().equals(getType()) || i.getItemImage().equals("block/air.png")){
                            iterator.remove();
                        }
                    }
                    gotItems = true;
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
                    touchingItems = (ArrayList<Items>) getIntersectingObjects(Items.class);
                    Iterator<Items> iterator = touchingItems.iterator();
                    while(iterator.hasNext()){
                        Items i = iterator.next();
                        if(!i.getType().equals(getType()) || i.getItemImage().equals("block/air.png")){
                            iterator.remove();
                        }
                    }
                    gotItems = true;
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
                for(Items i: touchingItems){
                    i.setLocation(mouse.getX(), mouse.getY());
                }
                setLocation(mouse.getX(), mouse.getY());
                if(sizeOfNumItems > 1){
                    counter.setLocation(mouse.getX() + 15, mouse.getY() + 15);
                }
            }

            if(!dragging && !snapped){
                if(!getObjectsInRange(1000, Empty.class).isEmpty()){
                    int dist = 1000;
                    while(!getObjectsInRange(dist, Empty.class).isEmpty() && dist >= 0){
                        dist--;
                    }
                    dist++;
                    ArrayList<Empty> itemList = (ArrayList<Empty>) getObjectsInRange(dist, Empty.class);
                    Items temp = (Items) itemList.get(0);

                    X = temp.getX();
                    Y = temp.getY();

                    ArrayList<Items> temp1 = (ArrayList<Items>) getIntersectingObjects(Items.class);

                    for(Items i : temp1){
                        if(i.getType().equals("air") || i.getType().equals(getType())){
                            if(gotItems){
                                for(Items j: touchingItems){
                                    j.setLocation(X, Y);
                                }
                            }
                            if(sizeOfNumItems > 1){
                                counter.setLocation(X + 15, Y + 15);
                            }
                            setLocation(X, Y);
                            tempX = getX();
                            tempY = getY();
                            X = getX();
                            Y = getY();
                            snapped = true;

                        } else {
                            if(gotItems){
                                for(Items j: touchingItems){
                                    j.setLocation(tempX, tempY);
                                }
                            }
                            if(sizeOfNumItems > 1){
                                counter.setLocation(tempX + 15, tempY + 15);
                            }
                            setLocation(tempX, tempY);
                            X = getX();
                            Y = getY();
                            snapped = true;
                            break;
                        }
                    }
                    setLocation(X, Y);
                    temp1.clear();
                    if(gotItems){
                        touchingItems.clear();
                    }
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

    public boolean isThisEmpty(){
        return false;
    }

    public String getItemImage(){
        return image;
    }
}
