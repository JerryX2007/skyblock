import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RightHead here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RightHead extends BodyPart
{
    private GreenfootImage img;
    private Steve steve;
    public RightHead(Steve steve){
        img = new GreenfootImage("steve/head_right.png");
        img.scale(32,64);
        setImage(img);
        this.steve = steve;
    }

    /**
     * Act - do whatever the RightHead wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        setLocation(steve.getX(), steve.getY()-32);
    }

    public void reflectImage(){
        GreenfootImage img1 = new GreenfootImage("steve/head_right1.png");
        img1.scale(32,64);
        setImage(img1);
    }

    public void reflectBack(){
        setImage(img);
    }
}
