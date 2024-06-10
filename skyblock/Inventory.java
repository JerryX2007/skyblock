import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Inventory here.
 * 
 * @author Benny Wang, Jerry Xing
 * @version (a version number or a date)
 */
public class Inventory extends GUI
{
    private int xAdjust = 0;
    private int yAdjust = 0;
    private static World world;
    private static ArrayList<Item> itemsList = new ArrayList<>();
    private static Item[][] slots = new Item[9][3];
    private static Item[][] crafting = new Item[2][2];
    private boolean prevState = false;
    private boolean prevState1 = false;
    private int tempX;
    private int tempY;
    private boolean foundLocation = false;
    
    public static ArrayList<Item> getItemsList(){
        return itemsList;
    }
    
    public Inventory (int scale, World world){
        super("inventory.png", scale, world);
        this.world = world;
        clearInv(); 
    }
    
    public static void addItem(Item item) {
        itemsList.add(item);
    }

    public static void removeItem(Item item) {
        itemsList.remove(item);
    }
    
    public void addInventory(){
        //Actual inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, world.getHeight()/2 + 27 + yAdjust);
                world.addObject(temp, 424 + xAdjust, world.getHeight()/2 + 27 + yAdjust);
                slots[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        //System.out.println(world.getHeight()/2 + 27);
        
        //Crafting section in inventory
        for (int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++) {
                Empty temp = new Empty(16, 16, world, 694 + xAdjust, 212 + yAdjust);
                world.addObject(temp, 694 + xAdjust, 212 + yAdjust);
                crafting[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        
        
        for(Item i : itemsList) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        xAdjust = 0;
        yAdjust = 0;
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
    }
    
    private void clearInv(){
        itemsList.clear();
    }
    
    
    
    /**
     * Act - do whatever the Inventory wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        boolean currentDown = Greenfoot.isKeyDown("p");
        boolean currentDown1 = Greenfoot.isKeyDown("o");
        if(currentDown && !prevState){
            Item temp = new Item("block/wood.png", world, 424, world.getHeight()/2 + 27, 32, 32, "wood");
           
            itemsList.add(temp);
            foundLocation = false;
            for (int i = 2; i >= 0; i--) {
                for (int j = 0; j < 9; j++) {
                    if(slots[j][i].getType().equals(temp.getType()) && slots[j][i].getCounterNum() < 64){
                        tempX = slots[j][i].getX();
                        tempY = slots[j][i].getY();
                        foundLocation = true;
                        break;
                    }
                }
            }
            
            if(!foundLocation){
                for (int i = 2; i >= 0; i--) {
                    for (int j = 0; j < 9; j++) {
                        if(slots[j][i].getType().equals("air")){
                            tempX = slots[j][i].getX();
                            tempY = slots[j][i].getY();
                            break;
                        }
                    }
                }
            }
            
            foundLocation = false;
            temp.setTempXY(tempX, tempY);
            temp.setXY(tempX, tempY);
            world.addObject(temp, tempX, tempY);
        } else if(currentDown1 && !prevState1){
            Item temp = new Item("block/cobblestone.png", world, 424, world.getHeight()/2 + 27, 32, 32, "cobblestone");
            
            itemsList.add(temp);
            foundLocation = false;
            
            for (int i = 2; i >= 0; i--) {
                for (int j = 0; j < 9; j++) {
                    if(slots[j][i].getType().equals(temp.getType()) && slots[j][i].getCounterNum() < 64){
                        tempX = slots[j][i].getX();
                        tempY = slots[j][i].getY();
                        foundLocation = true;
                        break;
                    }
                }
            }
            
            if(!foundLocation){
                for (int i = 2; i >= 0; i--) {
                    for (int j = 0; j < 9; j++) {
                        if(slots[j][i].getType().equals("air")){
                            tempX = slots[j][i].getX();
                            tempY = slots[j][i].getY();
                            break;
                        }
                    }
                }
            }
            
            foundLocation = true;
            temp.setTempXY(tempX, tempY);
            temp.setXY(tempX, tempY);
            world.addObject(temp, tempX, tempY);
        }
        prevState = currentDown;
        prevState1 = currentDown1;
    }
    
    public static void setSlot(int x, int y, String itemName){
        int tempX = slots[x][y].getX();
        int tempY = slots[x][y].getY();
        slots[x][y] = new Item("block/air.png", 16, 16, world, false, tempX, tempY, itemName);
    }
    
    public static void addItem(String item){
        Item temp = new Item("block/" + item + ".png", world, 424, world.getHeight()/2 + 27, 32, 32, item);
        itemsList.add(temp);
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
