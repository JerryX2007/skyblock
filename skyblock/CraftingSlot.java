import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A slot for crafting items or placing blocks.
 * 
 * This class represents a slot in a crafting interface where either an item or a block can be placed.
 * 
 * @author Jerry Xing 
 * @version (a version number or a date)
 */
public class CraftingSlot extends Item
{
    private Item item; // The item held in this crafting slot
    private String image;
    
    /**
     * Constructs a new CraftingSlot object with an item.
     * 
     * @param item The item to be placed in this slot.
     */
    public CraftingSlot(World world, int X, int Y, Item item) {
        super("block/leaves.png", 32, 32, world, false, X, Y, "air", false);
        image  = "block/air.png";
        this.item = item;
    }

    /**
     * Act - do whatever the CraftingSlot wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
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
     * Sets the item held in this crafting slot.
     * 
     * @param item The item to be placed in this slot.
     */
    public void setItem(Item item ) {
        this.item = item;
    }
    
    /**
     * Getter for image
     * 
     * @return image Type of image as a String
     */
    public String getItemImage(){
        return image;
    }
}
