import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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
    private Item outputItem;
    private OutputSlot outputSlot;
    private static World world;
    private int xAdjust = 0;
    private int yAdjust = 0;
    
    /**
     * Constructor for objects of class CraftingSystem.
     * Initializes the crafting grid and visibility state.
     * 
     * @param scale The scale of the crafting system interface.
     * @param world The world in which the crafting system exists.
     */
    public CraftingSystem(int scale, World world) {
        super("craftingTableInterface.png", scale, world);
        itemArray = new CraftingSlot[GRID_SIZE][GRID_SIZE];
        isVisible = false;
    }
    
    /**
     * Act - do whatever the CraftingSystem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if(isVisible) {
            checkCrafting();
        }
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
        
        for(int i=0; i < GRID_SIZE; i++) {
            for(int y=0;y<GRID_SIZE;y++) {
                if(i==0) {
                    world.addObject(new Empty(16, 16, world, 200 + xAdjust, 400), 200 + xAdjust, 400);
                }
                
            }
        }
        xAdjust = 0;
        yAdjust = 0;
        
        // Add player's items to the world
        for (Item i : Inventory.getItemsList()) {
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        
        // Add chest inventory slots to the world
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
     * Checks the crafting grid for valid crafting recipes and updates the output slot with the crafted item.
     */
    private void checkCrafting() {
        //Hard code recipes
        //Also try to hard code the possible positions of every single combination ;-;
        
        if (isCraftingPlanks()) {
            outputItem = new Item("block/plank.png", 32, 32, world, true, outputSlot.getX(), outputSlot.getY(), "plank");
            increaseItemAmount(outputItem, 3);
            outputSlot.setItem(outputItem);
            outputItem = null;
            
        } 
        // Check for stick recipe (two planks vertically aligned)
        else if (isCraftingSticks()) {
            outputItem = new Stick(world, outputSlot.getX(), outputSlot.getY()); // Example output: 4 sticks
            increaseItemAmount(outputItem, 3);
            outputSlot.setItem(outputItem);
            outputItem = null;
            
        }
        
        else if (isCraftingWoodSword()) {
            outputItem = new WoodenSword(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
            outputItem = null;
        }
        
        else if (isCraftingWoodPickaxe()) {
            outputItem = new WoodenPickaxe(world, outputSlot.getX(), outputSlot.getY());
            outputSlot.setItem(outputItem);
        }
        
        else {
            outputItem = null;
        }
        
    }
    
    /**
     * Checks if the slot at the given coordinates is empty.
     * 
     * @param x The x-coordinate of the slot.
     * @param y The y-coordinate of the slot.
     * @return True if the slot is empty, false otherwise.
     */
    private boolean isEmpty(int x, int y) {
        return itemArray[y][x] == null;
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
     * Gets the output item from the crafting process.
     * 
     * @return The crafted item.
     */
    public Item getOutputItem() {
        return outputItem;
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
                    if(getSlot(x, y).getBlock().getName().equals("log")) {
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
        boolean foundFirstPlank = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 1; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getItem() != null && getSlot(x, y + 1).getItem() != null &&
                        getSlot(x, y).getItem().getType().equals("planks") &&
                        getSlot(x, y + 1).getItem().getType().equals("planks")) {
                        if (foundFirstPlank) {
                            return false; // More than one pair of planks found
                        }
                        foundFirstPlank = true;
                    } else {
                        return false; // Another item found
                    }
                }
            }
        }
        
        return foundFirstPlank;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a wooden sword.
     * 
     * @return True if the recipe for a wooden sword is satisfied, false otherwise.
     */
    private boolean isCraftingWoodSword() {
        //Recipe for wooden sword: two planks and a stick vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstSword = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getBlock() != null && getSlot(x, y + 1).getBlock() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getBlock().getName().equals("planks") && 
                        getSlot(x, y+1).getBlock().getName().equals("planks") && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        if (foundFirstSword) {
                            return false; //More than one sword recipe found
                        }
                        foundFirstSword = true;
                    } else {
                        return false; //Another item found
                    }
                }
            }
        }
        return foundFirstSword;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a stone sword.
     * 
     * @return True if the recipe for a stone sword is satisfied, false otherwise.
     */
    private boolean isCraftingStoneSword() {
        //Recipe for stone sword: two cobblestones and a stick vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstSword = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getBlock() != null && getSlot(x, y + 1).getBlock() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getBlock().getName().equals("cobblestone") && 
                        getSlot(x, y+1).getBlock().getName().equals("cobblestone") && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        
                        if (foundFirstSword) {
                            return false; //More than one sword recipe found
                        }
                        foundFirstSword = true;
                    } else {
                        return false; //Another item found
                    }
                }
            }
        }
        if(foundFirstSword) {
            for(int x = 0; x < GRID_SIZE; x++) {
                for(int y = 0; y < GRID_SIZE; y++) {
                    if(!isEmpty(x, y)) {
                        foundFirstSword = false;
                    }
                }
            }
        }
        return foundFirstSword;
    }
    
    /**
     * Checks if the current grid configuration matches the recipe for crafting a wooden pickaxe.
     * 
     * @return True if the recipe for a wooden pickaxe is satisfied, false otherwise.
     */
    private boolean isCraftingWoodPickaxe() {
        //Recipe for wooden pickaxe: three planks and 2 sticks in a pickaxe pattern
        /* ###
         * -#-
         * -#-
         */
        boolean foundFirstPickaxe = false;
        if (!isEmpty(0, 0) && !isEmpty(1, 0) && !isEmpty(2, 0) && !isEmpty(1, 1) && !isEmpty(1, 2) &&
            getSlot(0, 0).getItem().getType().equals("plank") && getSlot(1, 0).getItem().getType().equals("plank") &&
            getSlot(2, 0).getItem().getType().equals("plank") && getSlot(1, 1).getItem().getType().equals("stick") && 
            getSlot(1, 2).getItem().getType().equals("stick")) {
            
            if(foundFirstPickaxe) {
                return false; //More than one pickaxe recipe found
            }
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
     * Checks if the current grid configuration matches the recipe for crafting a stone pickaxe.
     * 
     * @return True if the recipe for a stone pickaxe is satisfied, false otherwise.
     */
    private boolean isCraftingStonePickaxe() {
        //Recipe for stone pickaxe: three cobblestone blocks and 2 sticks in a pickaxe pattern
        /* ###
         * -#-
         * -#-
         */
        boolean foundFirstPickaxe = false;
        if (!isEmpty(0, 0) && !isEmpty(1, 0) && !isEmpty(2, 0) && !isEmpty(1, 1) && !isEmpty(1, 2) &&
            getSlot(0, 0).getItem().getType().equals("cobblestone") && getSlot(1, 0).getItem().getType().equals("cobblestone") &&
            getSlot(2, 0).getItem().getType().equals("cobblestone") && getSlot(1, 1).getItem().getType().equals("stick") && 
            getSlot(1, 2).getItem().getType().equals("stick")) {
            
            if(foundFirstPickaxe) {
                return false; //More than one pickaxe recipe found
            }
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
     * Checks if the current grid configuration matches the recipe for crafting a wooden shovel.
     * 
     * @return True if the recipe for a wooden shovel is satisfied, false otherwise.
     */
    private boolean isCraftingWoodShovel() {
        //Recipe for the wooden shovel: one plank and 2 sticks vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstShovel = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getBlock() != null && getSlot(x, y + 1).getBlock() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getBlock().getName().equals("plank") && 
                        getSlot(x, y+1).getBlock().getName().equals("stick") && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        
                        if (foundFirstShovel) {
                            return false; //More than one sword recipe found
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
     * Checks if the current grid configuration matches the recipe for crafting a stone shovel.
     * 
     * @return True if the recipe for a stone shovel is satisfied, false otherwise.
     */
    private boolean isCraftingStoneShovel() {
        //Recipe for the stone shovel: one cobblestone and 2 sticks vertically aligned
        /*
         * #
         * #
         * #
         */
        boolean foundFirstShovel = false;
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                if (!isEmpty(x, y) && !isEmpty(x, y + 1)) {
                    if (getSlot(x, y).getBlock() != null && getSlot(x, y + 1).getBlock() != null && 
                        getSlot(x, y+2).getItem() != null && getSlot(x, y).getBlock().getName().equals("cobblestone") && 
                        getSlot(x, y+1).getBlock().getName().equals("stick") && getSlot(x, y+2).getItem().getType().equals("stick")) {
                        
                        if (foundFirstShovel) {
                            return false; //More than one sword recipe found
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
}
