import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CraftingSlot here.
 * 
 * @author Jerry Xing 
 * @version (a version number or a date)
 */
public class CraftingSlot extends Actor
{
    private Item item;
    private Block block;
    
    public CraftingSlot(Item item) {
        this.item = item;
    }

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

    public Item getItem() {
        return item;
    }

    public Block getBlock() {
        return block;
    }

    public boolean isItem() {
        return item != null;
    }

    public boolean isBlock() {
        return block != null;
    }
    
    public void setBlock(Block block) {
        this.block = block;
    }
    
    public void setItem(Item item ) {
        this.item = item;
    }
}
