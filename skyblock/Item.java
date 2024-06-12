import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Iterator;
import com.google.common.collect.Iterators;

/**
 * Represents an item in the game that can be dragged, snapped, and stacked.
 * The item can interact with other items and empty slots.
 * 
 * @author Benny
 * @version June 1, 2024
 */
public class Item extends Actor {
    // Basic properties of the item
    protected String name;
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
    private int sizeOfNumItems = 1;
    private Label counter = new Label(sizeOfNumItems, 20);
    private boolean addedCounter = false;
    private boolean runOnlyFirstTime = true;
    private boolean okToSnap = true;
    private boolean firstAct = true;
    private boolean foundEmptySlot = false;
    private int testCount = 0;
    private int invX;
    private int invY;

    /**
     * Constructor for the Item class, given file name, size of scaled image length, size of scaled image width, the world its in, whether its draggable or not, x-coordinate of item, y-coordinate of item and the type of file
     * 
     * @param file The image file for the item.
     * @param length The length to scale the image.
     * @param width The width to scale the image.
     * @param world The world in which the item exists.
     * @param draggable Whether the item is draggable.
     * @param X The x-coordinate of the item.
     * @param Y The y-coordinate of the item.
     * @param type The type of the item.
     */
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
        this.invX = invX;
        this.invY = invY;
    }

    /**
     * What Items do when run
     */
    public void act(){
        if(draggable){
            // This is used to make sure that the stacks don't go over 64
            if(firstAct){
                // Get a list of touching items and if any of them are over 64, find a new location to put the stack
                ArrayList<Item> test = (ArrayList<Item>) getIntersectingObjects(Item.class);
                for(Item i : test){
                    if(i.getCounterNum() > 64){
                        // Get a really big distance to find an empty slot
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
                                break;
                            }
                        }
                        emptyList.clear();
                    }
                }
                firstAct = false;  // Set firstAct to false after initial setup
            }
            
            // Update the counter label position
            counter.setLineColor(Label.getTransparent());
            counter.setLocation(getX() + 15, getY() + 15);
            
            numItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
            // Remove all items that aren't the same as this item or that are air
            Iterator<Item> test = numItems.iterator();
            while(test.hasNext()){
                Item i = test.next();
                if(!i.getType().equals(getType()) || i.getItemImage().equals("block/air.png")){
                    test.remove();
                }
            }
            sizeOfNumItems = numItems.size() + 1;
            // Update the counter value based on the number of items in the stack
            if(sizeOfNumItems > 1){
                counter.setValue(sizeOfNumItems);
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            } else if(sizeOfNumItems == 1){
                counter.setValue("");
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            }
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if(Greenfoot.mousePressed(this) && !dragging){
                // Start dragging the item
                dragging = true;
                snapped = false;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
                    // Remove items that aren't the same type or are air
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
                // Stop dragging the item
                dragging = false;
                firstTime = true;
            }
            if(Greenfoot.mouseDragged(this)){
                // Continue dragging the item
                dragging = true;
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
                    // Remove items that aren't the same type or are air
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
                // Stop dragging the item when mouse drag ends
                dragging = false;
                firstTime = true;
            }

            // Handle mouse dragging
            if (dragging){
                snapped = false;
                try{
                    X = mouse.getX();
                    Y = mouse.getY();
                } catch (NullPointerException e){
                    X = world.getWidth()/2;
                    Y = world.getHeight()/2;
                }
                
                // Move all touching items together with the dragged item
                for(Item i: touchingItems){
                    i.setLocation(X, Y);
                }
                setLocation(X, Y);
                if(sizeOfNumItems > 1){
                    counter.setLocation(X + 15, Y + 15);
                }
            }
            
            // Snapping to slot
            if(!dragging && !snapped){
                // Get a big radius to ensure that a slot can be found
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
        
        // Empty/air items run this instead
        // All they do is check what blocks are on top of them and set their type to the touching type
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
    
    /**
     * Gets the inventory x-coordinate of the item.
     * 
     * @return The inventory x-coordinate.
     */
    public int getInvX(){
        return invX;
    }
    
    /**
     * Gets the inventory y-coordinate of the item.
     * 
     * @return The inventory y-coordinate.
     */
    public int getInvY(){
        return invY;
    }
    
    /**
     * Sets the snapped status of the item.
     * 
     * @param snapped True if the item is snapped, false otherwise.
     */
    public void setSnapped(boolean snapped){
        this.snapped = snapped;
    }
    
    /**
     * Sets the temporary x and y coordinates of the item.
     * 
     * @param X The x-coordinate.
     * @param Y The y-coordinate.
     */
    public void setTempXY(int X, int Y){
        tempX = X;
        tempY = Y;
    }
    
    /**
     * Sets the x and y coordinates of the item.
     * 
     * @param X The x-coordinate.
     * @param Y The y-coordinate.
     */
    public void setXY(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    
    /**
     * Removes the counter label from the world.
     */
    public void removeNum(){
        world.removeObject(counter);
    }
    
    /*
     * Update sizeOfNumItems value
     * 
     * @param value Value to be added
     */
    public void addSizeOfNumItems(int value){
        sizeOfNumItems += value;
    }
    
    /**
     * Gets the number of items in the stack.
     * 
     * @return The number of items in the stack.
     */
    public int getCounterNum(){
        return sizeOfNumItems;
    }

    /**
     * Gets whether the item is draggable.
     * 
     * @return True if the item is draggable, false otherwise.
     */
    public boolean getDraggable(){
        return draggable;
    }

    /**
     * Sets the type of the item.
     * 
     * @param type The type to set.
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Gets a string representation of the item.
     * 
     * @return A string representation of the item.
     */
    public String toString(){
        return "(" + X + ", " + Y + ")";
    }

    /**
     * Gets the x-coordinate of the item.
     * 
     * @return The x-coordinate of the item.
     */
    public int getXPos(){
        return X;
    }

    /**
     * Gets whether the item is snapped.
     * 
     * @return True if the item is snapped, false otherwise.
     */
    public boolean isSnapped(){
        return snapped;
    }

    /**
     * Gets the y-coordinate of the item.
     * 
     * @return The y-coordinate of the item.
     */
    public int getYPos(){
        return Y;
    }

    /**
     * Gets the type of the item.
     * 
     * @return The type of the item.
     */
    public String getType(){
        return type;
    }

    /**
     * Checks if this item is empty.
     * 
     * @return False, as this item is not empty.
     */
    public boolean isThisEmpty(){
        return false;
    }

    /**
     * Gets the image file of the item.
     * 
     * @return The image file of the item.
     */
    public String getItemImage(){
        return image;
    }
    
    /**
     * Checks if this item is equal to another item.
     * 
     * @param item The item to compare with.
     * @return True if the items are equal, false otherwise.
     */
    public boolean equals(Item item) {
        return this.name.equals(item.name);
    }
}
