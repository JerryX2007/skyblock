import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CobbleStone here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CobbleStone extends Block
{
    private GreenfootImage img;
    private GreenfootImage img2;

    public CobbleStone(){
        super(Color.GRAY,5);
        img = new GreenfootImage("block/cobblestone.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isStone = true;
        itemDrop = 1;
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