import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Leave here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Leaf extends Block
{
    private static Color green = new Color(23, 120, 51);
    private GreenfootImage img;
    private GreenfootImage img2;

    public Leaf(){
        super(green,1);
        img = new GreenfootImage("block/leaves.png");
        img.scale(64,64);
        img2 = addBorder(img, black);
        setImage(img);
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
    public void drop(int itemDrop){
        
    }
}