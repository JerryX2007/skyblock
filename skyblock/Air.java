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

    public Air(){
        super(null,100, "air");

        img = new GreenfootImage("block/air.png");
        img.setColor(new Color(0, 0, 0));

        setImage(img);
        img2 = addBorder(img, black);

    }

    public void act()
    {
        /*
        //stop air from breaking
        super.act();

        if(isSelected){
            setImage(img2);
        }
        if(!isSelected){
            setImage(img);
        }
        if(subBreakTime < 10){
            subBreakTime = breakTime;
        }
        */
    }

    public void drop(int itemDrop){

    }
}
