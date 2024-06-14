import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CraftingSystem here.
 * 
 * @author Jerry Xing
 * @version (a version number or a date)
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
            outputItem = new Stick(25, 25, world, outputSlot.getX(), outputSlot.getY()); // Example output: 4 sticks
            increaseItemAmount(outputItem, 3);
            outputSlot.setItem(outputItem);
            outputItem = null;
            
        } else {
            outputItem = null;
        }
        
    }
    
    private boolean isEmpty(int x, int y) {
        return itemArray[y][x] == null;
    }
    
    private CraftingSlot getSlot(int x, int y) {
        return itemArray[y][x];
    }
    
    /** Item functions */
    
    private void setItem(Item item, int x, int y) {
        itemArray[y][x].setItem(item);
    }
    
    private void increaseItemAmount(int x, int y) {
        itemArray[y][x].getItem().addSizeOfNumItems(1);
    }
    
    private void increaseItemAmount(Item item, int increment) {
        item.addSizeOfNumItems(increment);
    }
    
    private void decreaseItemAmount(int x, int y) {
        itemArray[y][x].getItem().addSizeOfNumItems(-1);
    }
    
    private void decreaseItemAmount(Item item, int increment) {
        item.addSizeOfNumItems(-increment);
    }
    
    //Overload the methods
    private void increaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].getItem().addSizeOfNumItems(increment);
    }
    
    private void decreaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].getItem().addSizeOfNumItems(-increment);
    }
    
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
    
    public void showCrafting() {
        isVisible = true;
    }

    public void hideCrafting() {
        isVisible = false;
    }

    public boolean isVisible() {
        return isVisible;
    }
    
    public Item getOutputItem() {
        return outputItem;
    }
    
    private boolean isCraftingPlanks() {
        // Recipe for planks: a single wood block in the top-left corner
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

    private boolean isCraftingSticks() {
        // Recipe for sticks: two planks vertically aligned
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
    
    private boolean isCraftingWoodSword() {
        //Recipe for wooden sword: two planks and a stick vertically aligned
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
}
