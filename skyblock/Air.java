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
    
    public Air(){
        super(Color.WHITE,100);
        GreenfootImage img = new GreenfootImage("air.png");
        img.scale(80,80);
        setImage(img);
    }
    
    public void act()
    {
        if(isHoldingMouse){
            isHoldingMouse = false;
        }
        super.act();
    }
     
    public void drop()
    {
        
    }
}
