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
public class Item extends Actor {
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
    private boolean firstTime = true;
    private ArrayList<Item> touchingItems;
    private boolean gotItems = false;
    private ArrayList<Item> numItems;
    public int sizeOfNumItems = 1;
    private Label counter = new Label(sizeOfNumItems, 20);
    private boolean addedCounter = false;
    private boolean runOnlyFirstTime = true;
    private boolean okToSnap = true;
    private boolean firstAct = true;
    private boolean foundEmptySlot = false;
    private int testCount = 0;

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
    public Item(String file, World world, int X, int Y, int length, int width, String type){
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

    public Item(String file, int length, int width, World world, boolean draggable, int X, int Y, String type){
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

    

    public void act(){
        if(draggable){
            
            // This is used to make sure that the stacks don't go over 64
            if(firstAct){
                
                // I get a list of touching items and if any of them are over 64, I find a new location to put the stack
                ArrayList<Item> test = (ArrayList<Item>) getIntersectingObjects(Item.class);
                for(Item i : test){
                    if(i.getCounterNum() > 64){
                        
                        // get a really big distance
                        int dist = 1000;
                        while(!getObjectsInRange(dist, Empty.class).isEmpty() && dist >= 0 && !foundEmptySlot){
                            ArrayList<Empty> emptyList = (ArrayList<Empty>) getObjectsInRange(dist, Empty.class);
                            for(Empty e : emptyList){
                                if(e.getType().equals("air") || e.getCounterNum() < 64){
                                    testCount++;
                                }
                            }
                            if(testCount == 0){
                                break;
                            }
                            testCount = 0;
                            dist--;
                            
                        }
                        dist++;
                        ArrayList<Empty> emptyList = (ArrayList<Empty>) getObjectsInRange(dist, Empty.class);
                        
                        for(Empty e : emptyList){
                            if(e.getType().equals("air")){
                                X = e.getX();
                                Y = e.getY();
                                setLocation(X, Y);
                                tempX = getX();
                                tempY = getY();
                                X = getX();
                                Y = getY();
                                break;
                            }
                        }
                        emptyList.clear();
                    }
                    
                }
                firstAct = false;
            }
            
            counter.setLineColor(Label.getTransparent());
            counter.setLocation(getX() + 15, getY() + 15);
            
            numItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
            //Chat gpt helped with Iterator
            Iterator<Item> test = numItems.iterator();
            while(test.hasNext()){
                Item i = test.next();
                if(!i.getType().equals(getType()) || i.getItemImage().equals("block/air.png")){
                    test.remove();
                }
            }
            sizeOfNumItems = numItems.size() + 1;
            if(sizeOfNumItems > 1){
                counter.setValue(sizeOfNumItems);
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            } else if(sizeOfNumItems == 1){
                counter.setValue("");
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            }
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(Greenfoot.mousePressed(this) && !dragging){
                dragging = true;
                snapped = false;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
                    //Chat gpt helped with Iterator
                    Iterator<Item> iterator = touchingItems.iterator();
                    while(iterator.hasNext()){
                        Item i = iterator.next();
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
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
                    //Chat gpt helped with Iterator
                    Iterator<Item> iterator = touchingItems.iterator();
                    while(iterator.hasNext()){
                        Item i = iterator.next();
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
                for(Item i: touchingItems){
                    i.setLocation(mouse.getX(), mouse.getY());
                }
                setLocation(mouse.getX(), mouse.getY());
                if(sizeOfNumItems > 1){
                    counter.setLocation(mouse.getX() + 15, mouse.getY() + 15);
                }
            }
            
            // snapping to slot
            if(!dragging && !snapped){
                if(!getObjectsInRange(1000, Empty.class).isEmpty()){
                    int dist = 1000;
                    while(!getObjectsInRange(dist, Empty.class).isEmpty() && dist >= 0){
                        dist--;
                    }
                    dist++;
                    ArrayList<Empty> itemList = (ArrayList<Empty>) getObjectsInRange(dist, Empty.class);
                    Item temp = (Item) itemList.get(0);

                    X = temp.getX();
                    Y = temp.getY();

                    ArrayList<Item> temp1 = (ArrayList<Item>) getIntersectingObjects(Item.class);

                    for(Item i : temp1){
                        if(i.getType().equals("air") || i.getType().equals(getType()) && okToSnap){
                            ;
                        } else  {
                            okToSnap = false;
                            break;
                        }
                    }
                    if(okToSnap){
                        
                        setLocation(X, Y);
                        tempX = getX();
                        tempY = getY();
                        X = getX();
                        Y = getY();
                        
                        snapped = true;
                        
                        if(gotItems){
                            for(Item j: touchingItems){
                                j.setLocation(X, Y);
                                j.setXY(X, Y);
                                j.setTempXY(tempX, tempY);
                                j.setSnapped(true);
                            }
                        }
                    } else {
                        
                        setLocation(tempX, tempY);
                        X = getX();
                        Y = getY();
                        
                        snapped = true;
                        
                        if(gotItems){
                            for(Item j: touchingItems){
                                j.setLocation(tempX, tempY);
                                j.setXY(X, Y);
                                j.setSnapped(true);
                            }
                        }
                    }
                    setLocation(X, Y);
                    temp1.clear();
                    if(gotItems){
                        touchingItems.clear();
                    }
                    okToSnap = true;
                }
            }
        }
        if(!draggable){
            ArrayList<Item> temp2 = (ArrayList<Item>) getIntersectingObjects(Item.class);
            if(temp2.isEmpty()){
                type = "air";
                sizeOfNumItems = 0;
            } else{
                for(Item i : temp2){
                    if(i.isSnapped()){
                        type = i.getType();
                        sizeOfNumItems = i.getCounterNum();
                        break;
                    }
                }
                
            }
            temp2.clear();
        }
    }
    
    public void setSnapped(boolean snapped){
        this.snapped = snapped;
    }
    
    public void setTempXY(int X, int Y){
        tempX = X;
        tempY = Y;
    }
    
    public void setXY(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    
    public void removeNum(){
        world.removeObject(counter);
    }
    
    public int getCounterNum(){
        return sizeOfNumItems;
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
