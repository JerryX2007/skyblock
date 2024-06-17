import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * CraftingSystem class handles the crafting mechanics in the game.
 * It checks for specific crafting recipes and updates the output slot with the crafted item.
 * 
 * @author Jerry Xing
 * @version 1.0 (June 14, 2024)
 * Help from https://www.youtube.com/watch?v=LmQ6U3YkHHk & ChatGPT
 */
public class CraftingSystem extends GUI
{
    private boolean isVisible;
    private final int GRID_SIZE = 3;
    private CraftingSlot[][] itemArray;
    private OutputSlot outputSlot;
    private static World world;
    private int xAdjust = 0;
    private int yAdjust = 0;
    private boolean key1PreviouslyDown;
    private boolean crafted;
    private ArrayList<CraftingSlot> affectedSlots;
    private ArrayList<int[]> usedCoords;
    private boolean needFour;
    
    /**
     * Constructor for objects of class CraftingSystem.
     * Initializes the crafting grid and visibility state.
     * 
     * @param scale The scale of the crafting system interface.
     * @param world The world in which the crafting system exists.
     */
    public CraftingSystem(int scale, World world) {
        super("craftingTableInterface.png", scale, world);
        this.world = world;
        
        //Initialize itemArray
        itemArray = new CraftingSlot[GRID_SIZE][GRID_SIZE];
        for (int x = 0; x < GRID_SIZE; x++) {
            for(int y = 0; y < GRID_SIZE; y++) {
                Empty temp = new Empty(16, 16, world, 490 + xAdjust, world.getHeight() / 2 - 170 + yAdjust);
                itemArray[x][y] = new CraftingSlot(world, 490 + xAdjust, world.getHeight() / 2 - 170 + yAdjust, temp);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        isVisible = false;
        xAdjust = 0;
        yAdjust = 0;
        crafted = false;
        outputSlot = new OutputSlot(world, 772, world.getHeight()/2 - 116, new Empty(16, 16, world, 772, world.getHeight()/2-116));
        affectedSlots = new ArrayList<CraftingSlot>();
        usedCoords = new ArrayList<int[]>();
        needFour = false;
    }
    
    /**
     * Act - do whatever the CraftingSystem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        boolean key1CurrentlyDown = Greenfoot.isKeyDown("e");
        checkCrafting();
        
        manageItems();
        if(!GameWorld.getOpenCrafting() && GameWorld.getGUIOpened() && key1CurrentlyDown && !key1PreviouslyDown){
            GameWorld.setGUIOpened(false);
            GameWorld.setOpenCrafting(false);
            removeCrafting();
            Player.setActivated(false);
            world.removeObject(this);
        }
        key1PreviouslyDown = key1CurrentlyDown;
    }
    
    public void addCrafting() {
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
        
        // Add crafting slots to the world
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                world.addObject(itemArray[i][j].getItem(), 490 + xAdjust, world.getHeight() / 2 - 170 + yAdjust);
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        
        xAdjust = 0;
        yAdjust = 0;
        
        outputSlot = new OutputSlot(world, 772, world.getHeight()/2-116, new Empty(16, 16, world, 772, world.getHeight()/2-116));
        world.addObject(outputSlot, 772, world.getHeight()/2 - 116);
        GameWorld.setOpenCrafting(true);
    }
    
    public void removeCrafting() {
        // Remove player's items from the world
        for (Item i : Inventory.getItemsList()) {
            world.removeObject(i);
            i.removeNum();
        }
        
        // Remove player's items from the crafting table
        for(CraftingSlot[] arr : itemArray) {
            for(CraftingSlot cs : arr) {
                world.removeObject(cs.getItem());
                cs.getItem().removeNum();
            }
        }
        
        // Remove player's inventory slots from the world
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
        
        for (Item i : craftingSlotItems) {
            if(Inventory.hasSpaceFor(i.getType())) {
                Inventory.addItem(i.getType());
                craftingSlotItems.remove(i);
                i.removeNum();
            }
            else {
                world.removeObject(i);
                i.removeNum();
                craftingSlotItems.remove(i);
            }
            
        }
        
        world.removeObject(outputSlot.getItem());
        outputSlot.removeNum();
        world.removeObject(outputSlot);
    }
    
    
    /**
     * Manages the items between the player's inventory and the chest.
     * Moves items between the player's inventory and the chest based on their position.
     */
    
    private void manageItems() {
        
        // Move items from the player's inventory to the crafting if they are above a certain y-coordinate
        for (int i = 0; i < Inventory.getItemsList().size(); i++) {
            if (Inventory.getItemsList().get(i).getY() <= 366) {
                craftingSlotItems.add(Inventory.getItemsList().get(i));
                Inventory.removeItem(Inventory.getItemsList().get(i));
            }
        }
        
        
        // Move items from the chest to the player's inventory if they are below a certain y-coordinate
        for (int i = 0; i < craftingSlotItems.size(); i++) {
            if (craftingSlotItems.get(i).getY() > 366) {
                Inventory.getItemsList().add(craftingSlotItems.get(i));
                craftingSlotItems.remove(craftingSlotItems.get(i));
            }
        }
    } 
    
    /**
     * Checks the crafting grid for valid crafting recipes and updates the output slot with the crafted item.
     */
    private void checkCrafting() {
        //Hard code recipes
        //Also try to hard code the possible positions of every single combination ;-;
        if(!crafted) {
            if (isCraftingPlanks()) {
                world.addObject(new Item("block/wooden_plank.png", 32, 32, world, true, 772, 268, "plank", true), 772, 268);
                needFour = true;
                increaseItemAmount(outputSlot, 3);
                crafted = true;
                
            }
        }
        
        // Check for stick recipe (two planks vertically aligned)
        else if (isCraftingSticks()) {
            //outputSlot = new Stick(world, outputSlot.getX(), outputSlot.getY()); // Example output: 4 sticks
            increaseItemAmount(outputSlot, 3);
            crafted = true;
        }
        if(crafted) {
            update(outputSlot);
            if(needFour) {
                world.addObject(new Item("block/wooden_plank.png", 32, 32, world, true, 772, 268, "plank", true), 772, 268);
                world.addObject(new Item("block/wooden_plank.png", 32, 32, world, true, 772, 268, "plank", true), 772, 268);
                world.addObject(new Item("block/wooden_plank.png", 32, 32, world, true, 772, 268, "plank", true), 772, 268);
            }
            needFour = false;
        }
        /*
        else if (isCraftingSword("plank")) {
            outputItem = new WoodenSword(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingSword("cobblestone")) {
            outputItem = new StoneSword(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingSword("iron")) {
            //outputItem = new IronSword(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingSword("diamond")) {
            //outputItem = new DiamondSword(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingPickaxe("plank")) {
            outputItem = new WoodenPickaxe(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingPickaxe("cobblestone")) {
            outputItem = new StonePickaxe(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingPickaxe("iron")) {
            //outputItem = new IronPickaxe(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingPickaxe("diamond")) {
            //outputItem = new DiamondPickaxe(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingShovel("plank")) {
            outputItem = new WoodenShovel(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingShovel("cobblestone")) {
            outputItem = new StoneShovel(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingShovel("iron")) {
            //outputItem = new IronShovel(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingShovel("diamond")) {
            //outputItem = new DiamondShovel(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingHelmet("leather")) {
            //outputItem = new LeatherHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingHelmet("iron")) {
            //outputItem = new IronHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingHelmet("diamond")) {
            //outputItem = new DiamondHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingChestplate("leather")) {
            //outputItem = new LeatherHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingChestplate("iron")) {
            //outputItem = new IronHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingChestplate("diamond")) {
            //outputItem = new DiamondHelmet(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else {
            outputItem = null;
        } */
        
    }
    
    private void update(Item outputSlot) {
        world.removeObject(outputSlot);
        world.addObject(outputSlot, 772, world.getHeight()/2 - 116);
    }
    
    /**
     * Checks if the slot at the given coordinates is empty.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @return True if the slot is empty, false otherwise.
     */
    private boolean isEmpty(int x, int y) {
        return itemArray[y][x].getItem().getType().equals("air");
    }
    
    /**
     * Gets the crafting slot at the given coordinates.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @return The CraftingSlot object at the specified coordinates.
     */
    private CraftingSlot getSlot(int x, int y) {
        return itemArray[y][x];
    }
    
    /**
     * Sets the item in the specified slot.
     * 
     * @param item The item to be placed in the slot.
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     */
    private void setItem(Item item, int x, int y) {
        itemArray[y][x].setItem(item);
    }
    
    /**
     * Increases the amount of items in the specified slot by 1.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     */
    private void increaseItemAmount(int x, int y) {
        itemArray[y][x].getItem().addSizeOfNumItems(1);
    }
    
    /**
     * Increases the amount of the specified item by a given increment.
     * 
     * @param item The item whose amount is to be increased.
     * @param increment The amount to increase.
     */
    private void increaseItemAmount(Item item, int increment) {
        item.addSizeOfNumItems(increment);
    }
    
    /**
     * Decreases the amount of items in the specified slot by 1.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     */
    private void decreaseItemAmount(int x, int y) {
        itemArray[y][x].getItem().addSizeOfNumItems(-1);
    }
    
    /**
     * Decreases the amount of the specified item by a given decrement.
     * 
     * @param item The item whose amount is to be decreased.
     * @param increment The amount to decrease.
     */
    private void decreaseItemAmount(Item item, int increment) {
        item.addSizeOfNumItems(-increment);
    }
    
    //Overload the methods
    
    /**
     * Increases the amount of items in the specified slot by a given increment.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @param increment The amount to increase.
     */
    private void increaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].getItem().addSizeOfNumItems(increment);
    }
    
    /**
     * Decreases the amount of items in the specified slot by a given decrement.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @param increment The amount to decrease.
     */
    private void decreaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].getItem().addSizeOfNumItems(-increment);
    }
    
    /**
     * Tries to add the specified item to the slot at the given coordinates.
     * 
     * @param item The item to be added.
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @return True if the item was successfully added, false otherwise.
     */
    private boolean tryAddItem(Item item, int x, int y) {
        if(isEmpty(x, y)) {
            setItem(item, x, y);
            return true;
        }
        else {
            if (getSlot(x, y).equals(item)) {
                increaseItemAmount(x, y);
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    /**
     * Shows the crafting interface.
     */
    public void showCrafting() {
        isVisible = true;
    }

    /**
     * Hides the crafting interface.
     */
    public void hideCrafting() {
        isVisible = false;
    }
    
    /**
     * Checks if the crafting interface is visible.
     * 
     * @return True if the crafting interface is visible, false otherwise.
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting planks.
     * 
     * @return True if the recipe for planks is satisfied, false otherwise.
     */
    private boolean isCraftingPlanks() {
        // Recipe for planks: a single wood block in the top-left corner
        /*
         * #
         */
        boolean satisfied = false;
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (!isEmpty(x, y)) {
                    if(satisfied) {
                        return false; //If there is another block in the crafting system return false
                    }
                    if(getSlot(x, y).getItem().getType().equals("wood")) {
                        satisfied = true;
                    }
                }
            }
        }
        return satisfied;
    }

    /**
     * Checks if the current grid configuration matches the recipe for crafting sticks.
     * 
     * @return True if the recipe for sticks is satisfied, false otherwise.
     */
    private boolean isCraftingSticks() {
        // Recipe for sticks: two planks vertically aligned
        /*
         * #
         * #
         */
        boolean foundFirstStick = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE-1; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1) && getSlot(x, y).getItem().getType().equals("stick") &&
                    getSlot(x, y + 1).getItem().getType().equals("stick")) {
                    if (foundFirstStick) {
                        usedCoords.clear();
                        return false;
                    }
                    foundFirstStick = true;
                    int[] temp = {x, y};
                    usedCoords.add(temp);
                    
                }
            }
        }
        
        if(foundFirstStick) {
            for (int x = 0; x < GRID_SIZE; x++) {
                for (int y = 0; y < GRID_SIZE; y++) {
                    int[] temp = {x, y};
                    if (!usedCoords.contains(temp) && !isEmpty(x, y)) {
                        foundFirstStick = false;
                    }
                }
            }
        }
        
        usedCoords.clear();
        return foundFirstStick;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a torch.
     * 
     * @return True if the recipe for a torch is satisfied, false otherwise.
     */
    private boolean isCraftingTorch() {
        //Recipe for the torch: one coal and stick vertically aligned
        /*
         * #
         * #
         */
        boolean foundFirstTorch = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE-1; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1) && getSlot(x, y).getItem().getType().equals("coal") &&
                    getSlot(x, y + 1).getItem().getType().equals("stick")) {
                    if (foundFirstTorch) {
                        usedCoords.clear();
                        return false;
                    }
                    foundFirstTorch = true;
                    int[] temp = {x, y};
                    usedCoords.add(temp);
                    
                }
            }
        }
        
        if(foundFirstTorch) {
            for (int x = 0; x < GRID_SIZE; x++) {
                for (int y = 0; y < GRID_SIZE; y++) {
                    int[] temp = {x, y};
                    if (!usedCoords.contains(temp) && !isEmpty(x, y)) {
                        foundFirstTorch = false;
                    }
                }
            }
        }
        
        usedCoords.clear();
        return foundFirstTorch;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a sword.
     * 
     * @param itemName the type of sword
     * @return True if the recipe for a sword is satisfied, false otherwise.
     */
    private boolean isCraftingSword(String itemName) {
        //Recipe for stone sword: two (itemName)s and a stick vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstSword = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getItem() != null && getSlot(x, y + 1).getItem() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getItem().getType().equals(itemName) && 
                        getSlot(x, y+1).getItem().getType().equals(itemName) && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        
                        if (foundFirstSword) {
                            usedCoords.clear();
                            return false; //More than one sword recipe found
                        }
                        int[] temp = {x, y};
                        int[] temp1 = {x, y+1};
                        int[] temp2 = {x, y+2};
                        
                        usedCoords.add(temp);
                        usedCoords.add(temp1);
                        usedCoords.add(temp2);
                        
                        foundFirstSword = true;
                    } else {
                        usedCoords.clear();
                        return false; //Another item found
                    }
                }
            }
        }
        
        if(foundFirstSword) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    int[] temp = {x, y};
                    if(!usedCoords.contains(temp) && !isEmpty(x, y)) {
                        foundFirstSword = false;
                    }
                }
            }
        }
        usedCoords.clear();
        return foundFirstSword;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a wooden pickaxe.
     * 
     * @param itemName the type of pickaxe
     * @return True if the recipe for a pickaxe is satisfied, false otherwise.
     */
    private boolean isCraftingPickaxe(String itemName) {
        //Recipe for wooden pickaxe: three (itemName)s and 2 sticks in a pickaxe pattern
        /* ###
         * -#-
         * -#-
         */
        boolean foundFirstPickaxe = false;
        if (!isEmpty(0, 0) && !isEmpty(1, 0) && !isEmpty(2, 0) && !isEmpty(1, 1) && !isEmpty(1, 2) &&
            getSlot(0, 0).getItem().getType().equals(itemName) && getSlot(1, 0).getItem().getType().equals(itemName) &&
            getSlot(2, 0).getItem().getType().equals(itemName) && getSlot(1, 1).getItem().getType().equals("stick") && 
            getSlot(1, 2).getItem().getType().equals("stick")) {

            foundFirstPickaxe = true;
        }
        else {
            return false;
        }
        if(foundFirstPickaxe) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstPickaxe = false;
                    }
                }
            }
        }
        return foundFirstPickaxe;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a shovel.
     * 
     * @param itemName the type of shovel
     * @return True if the recipe for a shovel is satisfied, false otherwise.
     */
    private boolean isCraftingShovel(String itemName) {
        //Recipe for the shovel: one (itemName) and 2 sticks vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstShovel = false;
        
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getItem() != null && getSlot(x, y + 1).getItem() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getItem().getType().equals(itemName) && 
                        getSlot(x, y+1).getItem().getType().equals("stick") && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        
                        if (foundFirstShovel) {
                            return false; //More than one shovel recipe found
                        }
                        foundFirstShovel = true;
                    } else {
                        return false; //Another item found
                    }
                }
            }
        }
        
        if(foundFirstShovel) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstShovel = false;
                    }
                }
            }
        }
        
        return foundFirstShovel;
        
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a helmet.
     * 
     * @param itemName the type of helmet
     * @return True if the recipe for a helmet is satisfied, false otherwise.
     */
    private boolean isCraftingHelmet(String itemName) {
        // Recipe for the helmet: 5 (itemName)s in an upside down U pattern
        /*
         * ###
         * #-#
         */
        boolean foundFirstHelmet = false;
        for(int x=0; x < GRID_SIZE; x++) {
            for(int y=0; y < GRID_SIZE-2; y++) {
                if(!isEmpty(x, y) && !isEmpty(x + 1, y) && !isEmpty(x + 2, y) && !isEmpty(x, y + 1) && !isEmpty(x + 2, y + 1) &&
                    getSlot(x, y).getItem().getType().equals(itemName) && getSlot(x + 1, y).getItem().getType().equals(itemName) && 
                    getSlot(x + 2, y).getItem().getType().equals(itemName) && getSlot(x, y + 1).getItem().getType().equals(itemName) &&
                    getSlot(x + 2, y + 1).getItem().getType().equals(itemName)) {
                    
                    if(foundFirstHelmet) {
                        return false;
                    }
                    
                    foundFirstHelmet = true;
                }
            }
        }
        
        if(foundFirstHelmet) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstHelmet = false;
                    }
                }
            }
        }
        
        return foundFirstHelmet;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a chestplate.
     * 
     * @param itemName the type of chestplate
     * @return True if the recipe for a chestplate is satisfied, false otherwise.
     */
    private boolean isCraftingChestplate(String itemName) {
        // Recipe for the chestplate: 8 (itemName)s in a "W" pattern
        /*
         * #-#
         * ###
         * ###
         */
        
        boolean foundFirstChestplate = false;
        
        if (!isEmpty(0, 0) && !isEmpty(2, 0) && !isEmpty(0, 1) && !isEmpty(1, 1) && !isEmpty(2, 1) && !isEmpty(0, 2) && 
            !isEmpty(1, 2) && !isEmpty(2, 2) && getSlot(0, 0).getItem().getType().equals(itemName) && 
            getSlot(2, 0).getItem().getType().equals(itemName) && getSlot(0, 1).getItem().getType().equals(itemName) &&
            getSlot(1, 1).getItem().getType().equals(itemName) && getSlot(2, 1).getItem().getType().equals(itemName) &&
            getSlot(0, 2).getItem().getType().equals(itemName) && getSlot(1, 2).getItem().getType().equals(itemName) &&
            getSlot(2, 2).getItem().getType().equals(itemName)) {

            foundFirstChestplate = true;
        }
        else {
            return false;
        }
        
        if(foundFirstChestplate) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstChestplate = false;
                    }
                }
            }
        }
        
        return foundFirstChestplate;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting leggings.
     * 
     * @param itemName the type of leggings
     * @return True if the recipe for leggings is satisfied, false otherwise.
     */
    private boolean isCraftingLeggings(String itemName) {
        // Recipe for the chestplate: 7 (itemName)s in an extended upside down U pattern
        /*
         * ###
         * #-#
         * #-#
         */
        
        boolean foundFirstLeggings = false;
        
        if (!isEmpty(0, 0) && !isEmpty(1, 0) && !isEmpty(2, 0) && !isEmpty(0, 1) && !isEmpty(2, 1) && !isEmpty(0, 2) && 
            !isEmpty(2, 2) && getSlot(0, 0).getItem().getType().equals(itemName) &&
            getSlot(1, 0).getItem().getType().equals(itemName) && getSlot(2, 0).getItem().getType().equals(itemName) &&
            getSlot(0, 1).getItem().getType().equals(itemName) && getSlot(2, 1).getItem().getType().equals(itemName) &&
            getSlot(0, 2).getItem().getType().equals(itemName) && getSlot(2, 2).getItem().getType().equals(itemName)) {

            foundFirstLeggings = true;
        }
        else {
            return false;
        }
        
        if(foundFirstLeggings) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstLeggings = false;
                    }
                }
            }
        }
        
        return foundFirstLeggings;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting boots.
     * 
     * @param itemName the type of boots
     * @return True if the recipe for boots is satisfied, false otherwise.
     */
    private boolean isCraftingBoots(String itemName) {
        // Recipe for the helmet: 4 (itemName)s in a | | pattern
        /*
         * #-#
         * #-#
         */
        boolean foundFirstBoots = false;
        for(int x=0; x < GRID_SIZE; x++) {
            for(int y=0; y < GRID_SIZE-2; y++) {
                if(!isEmpty(x, y) && !isEmpty(x + 2, y) && !isEmpty(x, y + 1) && !isEmpty(x + 2, y + 1) &&
                    getSlot(x, y).getItem().getType().equals(itemName) && getSlot(x + 2, y).getItem().getType().equals(itemName) && 
                    getSlot(x, y + 1).getItem().getType().equals(itemName) && getSlot(x + 2, y + 1).getItem().getType().equals(itemName)) {
                    
                    if(foundFirstBoots) {
                        return false;
                    }
                    
                    foundFirstBoots = true;
                }
            }
        }
        
        if(foundFirstBoots) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstBoots = false;
                    }
                }
            }
        }
        
        return foundFirstBoots;
    }
}
