import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Inventory here.
 * 
 * @author Benny Wang, Jerry Xing
 * edited by Evan Xi
 * @version (a version number or a date)
 */
public class Inventory extends GUI{
    private int xAdjust = 0;
    private int yAdjust = 0;
    private boolean prevState = false;
    private boolean prevState1 = false;
    private int tempX;
    private int tempY;
    private boolean foundLocation = false;
    private static boolean addedSomethingToInventory = false;
    private static boolean addedYet = false;
    
    public Inventory (int scale, World world){
        super("inventory.png", scale, world);
        this.world = world;
        clearInv();
        
        xAdjust = 0;
        yAdjust = 0;
        
        //Actual inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, 414 + yAdjust);
                slots[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        //Crafting section in inventory
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                Empty temp = new Empty(16, 16, world, 694 + xAdjust, 216 + yAdjust);
                crafting[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
    }
    
    /**
     * Act - do whatever the Inventory wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(!tempItemsList.isEmpty() && !addedYet){
            for(int x = 0; x < tempItemsList.size(); x++){
                Item temp = tempItemsList.get(x);
                foundLocation = false;
                outerloop1:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        //System.out.println(j + ", " + i + " Slot type: " + slots[j][i].getType() + " Item type: " + temp.getType());
                        if(slots[j][i].getType().equals(temp.getType()) && slots[j][i].getCounterNum() < 64){
                            tempX = slots[j][i].getX();
                            tempY = slots[j][i].getY();
                            //System.out.println("Found!");
                            foundLocation = true;
                            
                            break outerloop1;
                        }
                    }
                }
                //System.out.println();
                
                if(!foundLocation){
                    outerloop2:
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            if(slots[j][i].getType().equals("air")){
                                tempX = slots[j][i].getX();
                                tempY = slots[j][i].getY();
                                slots[j][i].setType(temp.getType());
                                //System.out.println(j + ", " + i + " Slot type: " + slots[j][i].getType() + " DID NOT FIND");
                                break outerloop2;
                            }
                        }
                    }
                }
            
                temp.setTempXY(tempX, tempY);
                temp.setXY(tempX, tempY);
                itemsList.add(temp);
                world.addObject(temp, tempX, tempY);
            }
            tempItemsList.clear();
            addedYet = true;
        }
    }
    
    public void addInventory(){
        xAdjust = 0;
        yAdjust = 0;
        
        //Actual inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.addObject(slots[j][i], 424 + xAdjust, 414 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        //Crafting section in inventory
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                world.addObject(crafting[j][i], 694 + xAdjust, 216 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        for(Item i : itemsList) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
    }
    
    public static ArrayList<Item> getTempItemsList(){
        return tempItemsList;
    }
    
    public void removeInventory(){
        for(Item i: itemsList){
            world.removeObject(i);
            i.removeNum();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                world.removeObject(crafting[j][i]);
            }
        }
    }
    
    public static void setAdded(boolean add){
        addedYet = add;
    }
    
    public static boolean getAdded(){
        return addedYet;
    }
    
    public void clearInv(){
        itemsList.clear();
    }

    public static ArrayList<Item> getItemsList(){
        return itemsList;
    }
    
    public static void addItem(Item item) {
        itemsList.add(item);
    }

    public static void removeItem(Item item) {
        itemsList.remove(item);
    }
        
    public static void setSlot(int x, int y, String itemName){
        int tempX = slots[x][y].getX();
        int tempY = slots[x][y].getY();
        slots[x][y] = new Item("block/air.png", 16, 16, world, false, tempX, tempY, itemName);
    }
    
    public static void addItem(String item){
        Item temp = new Item("block/" + item + ".png", 32, 32, world, true, 424, world.getHeight()/2 + 27, item);
        tempItemsList.add(temp);
        addedYet = false;
    }

    public static boolean hasSpaceFor(String item){
        return true;
    }
    
    public static int numOfEmptySlots(Item[][] arr) {
        int count = 0;
        for(int i = 0; i < arr.length; i++) {
            for(int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] instanceof Empty) {
                    count++;
                }
            }
        }
        return count;
    }
}
