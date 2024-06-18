import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A GUI for a chest in game.
 * It manages the chest inventory and the player's inventory when interacting with the chest.
 * 
 * Author: Benny
 * Version: 1.0.0
 */
public class ChestGUI extends GUI {
    private static World world;
    private int xAdjust = 0;
    private int yAdjust = 0;
    private boolean foundLocation = false;
    private int tempX;
    private int tempY;
    private boolean keyPreviouslyDown;

    /**
     * Constructor for the ChestGUI class.
     * Initializes the chest GUI with the specified scale and world.
     * 
     * @param scale The scale of the chest GUI.
     * @param world The world in which the chest GUI exists.
     */
    public ChestGUI(int scale, World world) {
        super("chestGUI.png", scale, world);
        this.world = world;
        
        // Initialize chest inventory slots
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, world.getHeight() / 2 - 174 + yAdjust);
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
     * What ChestGUI when run
     */
    public void act() {
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        
        manageItems();
        if(GameWorld.getGUIOpened() && GameWorld.getOpenChest() && keyCurrentlyDown && !keyPreviouslyDown){
            GameWorld.setGUIOpened(false);
            GameWorld.setOpenChest(false);
            removeChest();
            Player.setActivated(false);
            world.removeObject(this);
        }
        keyPreviouslyDown = keyCurrentlyDown;
    }

    /**
     * Add items to contents
     * 
     * @param item Item to add
     */
    public void addItem(Item item){
        contents.add(item);
    }
    
    /**
     * Adds the chest and inventory slots to the world and positions the items in the chest.
     */
    public void addChest() {
        xAdjust = 0;
        yAdjust = 0;
        
        // Add player's inventory slots to the world
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                if(i == 0){
                    world.addObject(slots[j][i], 424 + xAdjust, 588);
                    xAdjust += 54;
                } else {
                    world.addObject(slots[j][i], 424 + xAdjust, 414 + yAdjust);
                    xAdjust += 54;
                }
            }
            xAdjust = 0;
            if(i != 0){
                yAdjust += 54;
            }
        }
        xAdjust = 0;
        yAdjust = 0;
        
        // Add player's items to the world
        for (Item i : Inventory.getItemsList()) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        
        // Add crafting grid slots to the world
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.addObject(chestSlots[j][i], 424 + xAdjust, world.getHeight() / 2 - 174 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        xAdjust = 0;
        yAdjust = 0;
        
        // Add items in the chest to the world
        for (Item i : contents) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        
    }

    /**
     * Removes the chest and inventory slots from the world and the items they contain.
     */
    public void removeChest() {
        // Remove player's items from the world
        for (Item i : Inventory.getItemsList()) {
            world.removeObject(i);
            i.removeNum();
        }
        
        // Remove items in the chest from the world
        for (Item i : contents) {
            world.removeObject(i);
            i.removeNum();
        }
        
        // Remove player's inventory slots from the world
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
        
        // Remove chest inventory slots from the world
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(chestSlots[j][i]);
            }
        }
    }

    /**
     * Manages the items between the player's inventory and the chest.
     * Moves items between the player's inventory and the chest based on their position.
     */
    private void manageItems() {
        // Move items from the player's inventory to the chest if they are above a certain y-coordinate
        for (int i = 0; i < Inventory.getItemsList().size(); i++) {
            if (Inventory.getItemsList().get(i).getY() <= 366) {
                contents.add(Inventory.getItemsList().get(i));
                Inventory.removeItem(Inventory.getItemsList().get(i));
            }
        }
        
        // Move items from the chest to the player's inventory if they are below a certain y-coordinate
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).getY() > 366) {
                Inventory.getItemsList().add(contents.get(i));
                contents.remove(contents.get(i));
            }
        }
    }
    
    /**
     * Getter for contents
     * 
     * @return returns an ArrayList of items
     */
    public ArrayList<Item> getContents(){
        return contents;
    }
}
