import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Write a description of class ChestGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ChestGUI extends GUI
{
    private static World world;
    private int xAdjust = 0;
    private int yAdjust = 0;
    private Item[][] slots = new Item[9][3];
    private Item[][] chestSlots = new Item[9][3];
    private ArrayList<Item> contents = new ArrayList<>();
    private boolean foundLocation = false;
    private int tempX;
    private int tempY;
    private Inventory inventory;
    
    public ChestGUI (int scale, World world, Inventory inventory){
        super("chestGUI.png", scale, world);
        this.world = world;
        this.inventory = inventory;
        //Actual inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, world.getHeight()/2 + 30 + yAdjust);
                slots[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        //Chest inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, world.getHeight()/2 - 174 + yAdjust);
                chestSlots[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
    }
    
    /**
     * Act - do whatever the ChestGUI wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        manageItems();
    }

    
    public void addChest(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.addObject(slots[j][i], 424 + xAdjust, world.getHeight()/2 + 30 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        for(Item i : Inventory.getItemsList()) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        
        //Chest inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.addObject(chestSlots[j][i], 424 + xAdjust, world.getHeight()/2 - 174 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        for(Item i : contents){
            world.addObject(i, i.getXPos(), i.getYPos());
        }
    }
    
    public void removeChest(){
        for(Item i: Inventory.getItemsList()){
            world.removeObject(i);
            i.removeNum();
        }
        
        for(Item i: contents){
            world.removeObject(i);
            i.removeNum();
        }
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(chestSlots[j][i]);
            }
        }
    }
    
    private void manageItems(){
        for(int i = 0; i < Inventory.getItemsList().size(); i++){
            if(Inventory.getItemsList().get(i).getY() <= 366){
                contents.add(Inventory.getItemsList().get(i));
                Inventory.removeItem(Inventory.getItemsList().get(i));
                
            }
        }
        
        for(int i = 0; i < contents.size(); i++){
            if(contents.get(i).getY() > 366){
                Inventory.getItemsList().add(contents.get(i));
                contents.remove(contents.get(i));
            }
        }
        
        
        // for (Item item : Inventory.getItemsList()) {
            // if (item.getY() > 366) {
                // Inventory.removeItem(item);
                // contents.add(item);
            // }
        // }
        
        // for (Item item : contents) {
            // if (item.getY() <= 366) {
                // contents.remove(item);
                // Inventory.addItem(item);
            // }
        // }
    }
}
