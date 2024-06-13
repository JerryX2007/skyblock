import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Represents the inventory GUI in the game.
 * Manages the items in the player's inventory and crafting sections.
 * 
 * Author: Benny Wang, Jerry Xing
 * Edited by: Evan Xi
 * @version (a version number or a date)
 */
public class Inventory extends GUI {
    private int xAdjust = 0;
    private int yAdjust = 0;
    private boolean prevState = false;
    private boolean prevState1 = false;
    private int tempX;
    private int tempY;
    private boolean foundLocation = false;
    private static boolean addedSomethingToInventory = false;
    private static boolean addedYet = false;

    /**
     * Constructor for the Inventory class.
     * Initializes the inventory GUI with the specified scale and world.
     * 
     * @param scale The scale of the inventory GUI.
     * @param world The world in which the inventory GUI exists.
     */
    public Inventory(int scale, World world) {
        super("inventory.png", scale, world);
        this.world = world;
        clearInv();
        
        xAdjust = 0;
        yAdjust = 0;
        
        // Initialize player's inventory slots
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
        
        // Initialize crafting section slots
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                Empty temp = new Empty(16, 16, world, 694 + xAdjust, 216 + yAdjust);
                crafting[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
    }

    /**
     * What is run whenever Inventory is added
     */
    public void act() {
        // Add temporary items to inventory if not already added
        if (!tempItemsList.isEmpty() && !addedYet) {
            for (int x = 0; x < tempItemsList.size(); x++) {
                Item temp = tempItemsList.get(x);
                foundLocation = false;
                
                // Find a slot for the item based on its type
                outerloop1:
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        if (slots[j][i].getType().equals(temp.getType()) && slots[j][i].getCounterNum() < 64) {
                            tempX = slots[j][i].getX();
                            tempY = slots[j][i].getY();
                            foundLocation = true;
                            break outerloop1;
                        }
                    }
                }
                
                // If no suitable slot was found, find an empty slot
                if (!foundLocation) {
                    outerloop2:
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (slots[j][i].getType().equals("air")) {
                                tempX = slots[j][i].getX();
                                tempY = slots[j][i].getY();
                                slots[j][i].setType(temp.getType());
                                break outerloop2;
                            }
                        }
                    }
                }

                // Set the position of the item and add it to the inventory
                temp.setTempXY(tempX, tempY);
                temp.setXY(tempX, tempY);
                itemsList.add(temp);
                world.addObject(temp, tempX, tempY);
            }
            tempItemsList.clear();
            addedYet = true;
        }
    }

    /**
     * Adds the inventory slots and items to the world.
     */
    public void addInventory() {
        xAdjust = 0;
        yAdjust = 0;
        
        // Add player's inventory slots to the world
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
        
        // Add crafting section slots to the world
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                world.addObject(crafting[j][i], 694 + xAdjust, 216 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        // Add items in the inventory to the world
        for (Item i : itemsList) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
    }

    /**
     * Returns the list of temporary items.
     * 
     * @return The list of temporary items.
     */
    public static ArrayList<Item> getTempItemsList() {
        return tempItemsList;
    }

    /**
     * Removes the inventory slots and items from the world.
     */
    public void removeInventory() {
        for (Item i : itemsList) {
            world.removeObject(i);
            i.removeNum();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                world.removeObject(crafting[j][i]);
            }
        }
    }

    /**
     * Sets the addedYet flag.
     * 
     * @param add The value to set the addedYet flag.
     */
    public static void setAdded(boolean add) {
        addedYet = add;
    }

    /**
     * Returns the value of the addedYet flag.
     * 
     * @return The value of the addedYet flag.
     */
    public static boolean getAdded() {
        return addedYet;
    }

    /**
     * Clears the inventory list.
     */
    public void clearInv() {
        itemsList.clear();
    }

    /**
     * Returns the list of items in the inventory.
     * 
     * @return The list of items in the inventory.
     */
    public static ArrayList<Item> getItemsList() {
        return itemsList;
    }

    /**
     * Adds an item to the inventory list.
     * 
     * @param item The item to add to the inventory list.
     */
    public static void addItem(Item item) {
        itemsList.add(item);
    }

    /**
     * Removes an item from the inventory list.
     * 
     * @param item The item to remove from the inventory list.
     */
    public static void removeItem(Item item) {
        itemsList.remove(item);
    }

    /**
     * Sets the slot at the specified coordinates with the given item name.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @param itemName The name of the item to set in the slot.
     */
    public static void setSlot(int x, int y, String itemName) {
        int tempX = slots[x][y].getX();
        int tempY = slots[x][y].getY();
        slots[x][y] = new Item("block/air.png", 16, 16, world, false, tempX, tempY, itemName);
    }

    /**
     * Adds an item to the inventory based on the item name.
     * 
     * @param item The name of the item to add.
     */
    public static void addItem(String item) {
        Item temp = new Item("block/" + item + ".png", 32, 32, world, true, 424, world.getHeight() / 2 + 27, item);
        tempItemsList.add(temp);
        addedYet = false;
    }

    /**
     * Checks if there is space for the specified item in the inventory.
     * 
     * @param item The item to check space for.
     * @return True if there is space for the item, otherwise false.
     */
    public static boolean hasSpaceFor(String item) {
        return true;  // This implementation always returns true, consider implementing actual logic
    }

    /**
     * Returns the number of empty slots in the given 2D array of slots.
     * 
     * @param arr The 2D array of slots to check.
     * @return The number of empty slots.
     */
    public static int numOfEmptySlots(Item[][] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] instanceof Empty) {
                    count++;
                }
            }
        }
        return count;
    }
}
