import greenfoot.*;

/**
 * Write a description of class alsjflsa here.
 * 
 * @author Jerry Xing
 * @version (a version number or a date)
 */

public class CraftingTable extends Block
{
    private static Color lightYellow = new Color(235, 198, 109);
    private GreenfootImage img;
    private GreenfootImage img2;
    private World world;
    private CraftingSystem craftingSystem;

    public CraftingTable(World world){
        super(lightYellow,2, "craftingtable");
        img = new GreenfootImage("block/crafting_table.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isWood = true;
        this.world = world;
        craftingSystem = new CraftingSystem(300, world);
        itemDrop = 6;
    }

    /**
     * Act - do whatever the Dirt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
        if(isSelected){
            setImage(img2);
        }
        if(!isSelected){
            setImage(img);
        }
    }

    public void drop(){

    }
    
    public void openCraftingSystem() {
        
    }
    
    public void closeCraftingSystem() {
        
    }
    
    public CraftingSystem getCraftingSystem() {
        return craftingSystem;
    }
}
