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
public class CraftingTable extends Block
{
    private static Color lightYellow = new Color(235, 198, 109);
    private GreenfootImage img;
    private GreenfootImage img2;

    public CraftingTable(){
        super(lightYellow,2);
        img = new GreenfootImage("block/crafting table.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isWood = true;
        itemDrop = 9;
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
}
