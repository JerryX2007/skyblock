import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ColeOre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CoalOre extends Block
{
    private GreenfootImage img;
    private GreenfootImage img2;

    public CoalOre(){
        super(Color.GRAY,5, "cole_ore");
        img = new GreenfootImage("block/cole_ore.jpeg");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isStone = true;
        itemDrop = 10;
    }

    /**
     * Act - do whatever the Grass wants to do. This method is called whenever
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
}
