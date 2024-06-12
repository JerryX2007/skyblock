import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class alsjflsa here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Log here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Chest extends Block
{
    private static Color tan = new Color(198, 122, 70);
    private GreenfootImage img;
    private GreenfootImage img2;
    private ChestGUI chestGUI;
    private World world;

    public Chest(World world){
        super(tan,2);
        img = new GreenfootImage("block/chest.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isWood = true;
        this.world = world;
        chestGUI = new ChestGUI(300, world);
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
    
    public void openChestGUI() {
        chestGUI.addChest();
    }
}
