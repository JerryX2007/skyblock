import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents an output slot in a crafting interface where crafted items are placed.
 * 
 * This class represents a slot in a crafting interface where crafted items are placed as output.
 * It can hold one item at a time.
 * 
 * @author Jerry Xing 
 * @version (a version number or a date)
 */
public class OutputSlot extends Actor
{
    private Item item; // The item held in this output slot
    
    /**
     * Constructs a new OutputSlot object with an initial item.
     * 
     * @param item The item to be placed in this output slot initially.
     */
    public OutputSlot(Item item) {
        this.item = item;
    }
    
    /**
     * Act - do whatever the OutputSlot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }

    /**
     * Gets the item held in this output slot.
     * 
     * @return The item held in this output slot, or null if no item is present.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Checks if this output slot contains an item.
     * 
     * @return true if this slot contains an item, false otherwise.
     */
    public boolean hasItem() {
        return item != null;
    }
    
    /**
     * Sets the item held in this output slot.
     * 
     * @param item The item to be placed in this output slot.
     */
    public void setItem(Item item ) {
        this.item = item;
    }
}
