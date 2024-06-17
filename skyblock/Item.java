import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

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
    protected String file;
    protected int length;
    protected int width;
    protected int X;
    protected int Y;
    protected MouseInfo mouse;
    protected boolean draggable = true;
    protected boolean snapped = false;
    protected String type;
    protected int tempX;
    protected int tempY;
    protected World world;
    private String image;
    private boolean firstTime = true;
    private static ArrayList<Item> touchingItems = new ArrayList<>();
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
    private int mX;
    private int mY;
    protected boolean pressed = false;
    protected static boolean holdingSomething = false;
    protected boolean placeable;
    
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
     * @param placeable Whether the item is placeable or not
     */
    public Item(String file, int length, int width, World world, boolean draggable, int X, int Y, String type, boolean placeable){
        this.setImage(file);
        this.draggable = draggable;
        this.file = file;
        this.X = X;
        this.Y = Y;
        this.type = type;
        this.world = world;
        this.invX = invX;
        this.invY = invY;
        this.length = length;
        this.placeable = placeable;
        image = file;
        tempX = X;
        tempY = Y;
        counter.setLineColor(Label.getTransparent());
        getImage().scale(length, width);
    }

    /**
     * What Items do when run
     */
    public void act(){
        //System.out.println(holdingSomething + " " + Greenfoot.getRandomNumber(10));
        if(draggable){
            // Update the counter label position
            counter.setLocation(getX() + 15, getY() + 15);
            
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
            
            numItems = (ArrayList<Item>) getIntersectingObjects(Item.class);
            // Remove all items that aren't the same as this item or that are air
            for(int i = 0; i < numItems.size(); i++){
                if(numItems.get(i).getItemImage().equals("block/air.png") || !numItems.get(i).getType().equals(getType())){
                    numItems.remove(numItems.get(i));
                }
            }
            sizeOfNumItems = numItems.size();
            
            // Update the counter value based on the number of items in the stack
            if(sizeOfNumItems >= 1){
                counter.setValue(sizeOfNumItems + 1);
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            } else if(sizeOfNumItems < 1){
                counter.setValue("");
                getWorld().addObject(counter, getX() + 15, getY() + 15);
            }
            
            MouseInfo mouse = Greenfoot.getMouseInfo();
            
            if (Greenfoot.mousePressed(this) && holdingSomething){ // pressing releases all items
                // Stop pressed the item
                //System.out.println(3);
                for(Item i : world.getObjects(Item.class)){
                    i.setPressed(false);
                    i.setFirstTime(true);
                }
                holdingSomething = false;
                pressed = false;
                firstTime = true;
            } else if(mouse != null && mouse.getButton() == 1 && Greenfoot.mousePressed(this) && !pressed && !holdingSomething){ // left click gets entire stack
                // Start pressed the item
                //System.out.println(1);
                
                if(firstTime){
                    pressed = true;
                    snapped = false;
                    tempX = getX();
                    tempY = getY();
                    firstTime = false;
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);    
                    for(int i = 0; i < touchingItems.size(); i++){
                        if(!touchingItems.get(i).getType().equals(getType()) || touchingItems.get(i).getItemImage().equals("block/air.png")){
                            touchingItems.remove(touchingItems.get(i));
                        }
                    }
                    gotItems = true;
                }
            } else if(mouse != null && mouse.getButton() == 3 && Greenfoot.mousePressed(this) && !holdingSomething){ // right click gets only 1 item
                // Start pressed the item
                //System.out.println(2);
                
                if(firstTime){
                    tempX = getX();
                    tempY = getY();
                    
                    touchingItems = (ArrayList<Item>) getIntersectingObjects(Item.class);    
                    for(int i = 0; i < touchingItems.size(); i++){
                        if(!touchingItems.get(i).getType().equals(getType()) || touchingItems.get(i).getItemImage().equals("block/air.png")){
                            touchingItems.remove(touchingItems.get(i));
                        }
                    }
                    
                    while(touchingItems.size() > 1){
                        touchingItems.remove(touchingItems.size() - 1);
                    }
                    //System.out.println(touchingItems.size());
                    try{
                        touchingItems.get(0).setPressed(true);
                        touchingItems.get(0).setSnapped(false);
                        touchingItems.get(0).setTempXY(tempX, tempY);
                        touchingItems.get(0).setGotItems(true);
                    } catch (IndexOutOfBoundsException e){
                        
                    }
                    
                }
            }
            
            // Handle mouse pressed
            if (pressed){
                holdingSomething = true;
                snapped = false;
                try{
                    mX = mouse.getX();
                    mY = mouse.getY();
                } catch (NullPointerException e){
                    mX = world.getWidth()/2;
                    mY = world.getHeight()/2;
                }
                
                // Move all touching items together with the dragged item
                for(Item i: touchingItems){
                    i.setLocation(mX, mY);
                }
                setLocation(mX, mY);
                if(sizeOfNumItems > 1){
                    counter.setLocation(mX + 15, mY + 15);
                }
            }
            
            // Snapping to slot
            if(!pressed && !snapped){
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
                    touchingItems.clear();
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
     * Getter for touchingItems
     * 
     * @return Returns touchingItems
     */
    public static ArrayList<Item> getTouchingItems(){
        return touchingItems;
    }
    
    /**
     * Gets a string representation of the item.
     * 
     * @return A string representation of the item.
     */
    public String toString(){
        return file + " " + length + " " + world + " " + draggable + " " + X + " " + Y + " " + type + " " +  placeable;
    }
    
    /**
     * Get item under cursor
     * 
     * @return returns a Item
     */
    protected Item getBlockUnderCursor() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            ArrayList<Item> it = (ArrayList<Item>) getWorld().getObjectsAt(mouseX, mouseY, Item.class);
            for(int i = 0; i < it.size(); i++){
                if(it.get(i).getType().equals("air")){
                    it.remove(it.get(i));
                }
            }
            if (!it.isEmpty()) {
                return it.get(0); // Assuming only one block can be at this position
            }
        }
        return null;
    }
    
    /**
     * Setter for pressed
     * 
     * @param drag New value for pressed
     */
    public void setPressed(boolean drag){
        pressed = drag;
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
     * Setter for holdingSomething
     * 
     * @param hold New value for holdingSomething
     */
    public static void setHoldingSomething(boolean hold){
        holdingSomething = hold;
    }
    
    /**
     * Setter for firstTime
     * 
     * @param first New value for firstTime
     */
    public void setFirstTime(boolean first){
        firstTime = first;
    }
    
    /**
     * Setter for snapped
     * 
     * @param snap New value for snapped
     */
    public void setSnapped(boolean snap){
        snapped = snap;
    }
    
    /**
     * Setter for gotItems
     * 
     * @param got New value for gotItems
     */
    public void setGotItems(boolean got){
        gotItems = got;
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
    
    /**
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
     * Getter for placeable
     * 
     * @return returns the value of placeable for a given item
     */
    public boolean getPlaceable(){ return placeable;}
    
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
