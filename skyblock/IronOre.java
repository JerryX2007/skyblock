import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class IronOre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class IronOre extends Block
{
    private GreenfootImage img;
    private GreenfootImage img2;

    public IronOre(){
        super(Color.GRAY,5, "iron_ore");
        img = new GreenfootImage("block/iron_ore.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isStone = true;
        itemDrop = 11;
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
