import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CraftingSlot here.
 * 
 * @author Jerry Xing 
 * @version (a version number or a date)
 */
public class OutputSlot extends Actor
{
    private Item item;
    
    public OutputSlot(Item item) {
        this.item = item;
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

    public boolean hasItem() {
        return item != null;
    }
    
    public void setItem(Item item ) {
        this.item = item;
    }
}
