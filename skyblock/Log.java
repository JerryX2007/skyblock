import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Log here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Log extends Block
{
    private static Color brown = new Color(77, 50, 36);
    private GreenfootImage img;
    private GreenfootImage img2;
    private Color gold = new Color(255, 201, 14);
    public Log(){
        super(brown,2);
        img = new GreenfootImage("wood.png");
        img.scale(32,32);
        setImage(img);
        img2 = addBorder(img, gold);
        isWood = true;
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