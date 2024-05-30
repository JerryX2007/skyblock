import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Air here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Air extends Block
{
    /**
     * Act - do whatever the Air wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private GreenfootImage img;
    private GreenfootImage img2;
    private Color gold = new Color(255, 201, 14);
    public Air(){
        super(Color.WHITE,100);
        GreenfootImage img = new GreenfootImage("block/air.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, gold);
    }
    
    public void act()
    {
        //stop air from breaking
        if(isHoldingMouse){
            isHoldingMouse = false;
        }
        super.act();
        /**
        if(isSelected){
            setImage(img2);
        }
        if(!isSelected){
            setImage(img);
        }
        */
    }
    
    public void drop()
    {
        
    }
}
