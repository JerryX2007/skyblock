import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Grass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grass extends Block
{
    private static Color brown = new Color(185, 122, 87);
    private GreenfootImage img;
    private GreenfootImage img2;

    public Grass(){
        super(brown,1);
        img = new GreenfootImage("block/grass_block.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isDirt = true;
        itemDrop = 2;
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
