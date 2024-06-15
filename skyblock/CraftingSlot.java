import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A slot for crafting items or placing blocks.
 * 
 * This class represents a slot in a crafting interface where either an item or a block can be placed.
 * It can hold either an item or a block, but not both simultaneously.
 * 
 * @author Jerry Xing 
 * @version (a version number or a date)
 */
public class CraftingSlot extends Actor
{
    private Item item; // The item held in this crafting slot
    private Block block; // The block held in this crafting slot
    
    /**
     * Constructs a new CraftingSlot object with an item.
     * 
     * @param item The item to be placed in this slot.
     */
    public CraftingSlot(Item item) {
        this.item = item;
    }

    /**
     * Constructs a new CraftingSlot object with a block.
     * 
     * @param block The block to be placed in this slot.
     */
    public CraftingSlot(Block block) {
        this.block = block;
    }
    
    /**
     * Act - do whatever the CraftingSlot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }

    /**
     * Gets the item held in this crafting slot.
     * 
     * @return The item held in this slot, or null if no item is present.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the block held in this crafting slot.
     * 
     * @return The block held in this slot, or null if no block is present.
     */
    public Block getBlock() {
        return block;
    }

    /**
     * Checks if this crafting slot contains an item.
     * 
     * @return true if this slot contains an item, false otherwise.
     */
    public boolean isItem() {
        return item != null;
    }

    /**
     * Checks if this crafting slot contains a block.
     * 
     * @return true if this slot contains a block, false otherwise.
     */
    public boolean isBlock() {
        return block != null;
    }
    
    /**
     * Sets the block held in this crafting slot.
     * 
     * @param block The block to be placed in this slot.
     */
    public void setBlock(Block block) {
        this.block = block;
    }
    
    /**
     * Sets the item held in this crafting slot.
     * 
     * @param item The item to be placed in this slot.
     */
    public void setItem(Item item ) {
        this.item = item;
    }
}
