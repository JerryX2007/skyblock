import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CraftingSystem here.
 * 
 * @author Jerry Xing
 * @version (a version number or a date)
 * Help from https://www.youtube.com/watch?v=LmQ6U3YkHHk
 */
public class CraftingSystem extends GUI
{
    private boolean isVisible;
    private final int GRID_SIZE = 3;
    private CraftingSlot[][] itemArray;
    private Item outputItem;
    private Block outputBlock;
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
        
        // Check for plank recipe (wood block anywhere in the grid)
        if (isCraftingPlanks()) {
            outputBlock = new WoodenPlank(); // Example output: 4 planks
            //outputBlock.increaseBlockAmount();
            outputItem = null;
            
        } 
        // Check for stick recipe (two planks vertically aligned)
        else if (isCraftingSticks()) {
            //outputItem = new Item("Sticks", 4); // Example output: 4 sticks
        } else {
            outputItem = null;
            outputBlock = null;
        }
        
    }
    
    private boolean isEmpty(int x, int y) {
        return itemArray[y][x] == null;
    }
    
    private CraftingSlot getSlot(int x, int y) {
        return itemArray[y][x];
    }
    
    private void setItem(Item item, int x, int y) {
        itemArray[y][x].setItem(item);
    }
    
    private void increaseItemAmount(int x, int y) {
        itemArray[y][x].getItem().addSizeOfNumItems(1);
    }
    
    private void decreaseBlockAmount(int x, int y) {
        //itemArray[y][x].getBlock().addSizeOfNumItems(-1);
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
                if (!isEmpty(x, y) && getSlot(x, y).getItem().getType().equals("Log")) {
                    if(satisfied) {
                        return false;
                    }
                    satisfied = true;
                }
            }
        }
        return satisfied;
    }

    private boolean isCraftingSticks() {
        // Recipe for sticks: two planks vertically aligned in the middle column
        // Fix later
        return !isEmpty(1, 0) && !isEmpty(1, 1) && isEmpty(1, 2) &&
               getSlot(1, 0).getItem().getType().equals("Planks") &&
               getSlot(1, 1).getItem().getType().equals("Planks");
    }
}
