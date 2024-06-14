import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Water here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Water extends Air
{
    
    public static GreenfootImage[] left = { new GreenfootImage("liquid/Lw1.png"),new GreenfootImage("liquid/Lw2.png"),new GreenfootImage("liquid/Lw3.png"),
                                            new GreenfootImage("liquid/Lw4.png"),new GreenfootImage("liquid/Lw5.png"),new GreenfootImage("liquid/Lw6.png")};
    public static GreenfootImage[] right = { new GreenfootImage("liquid/Rw1.png"),new GreenfootImage("liquid/Rw2.png"),new GreenfootImage("liquid/Rw3.png"),
                                             new GreenfootImage("liquid/Rw4.png"),new GreenfootImage("liquid/Rw5.png"),new GreenfootImage("liquid/Rw6.png")};
    public static GreenfootImage normal = new GreenfootImage("liquid/w0.png");
    
    private boolean isSource;
    private int imageType; //0 is w0.png; 1-6 is streams flowing to right and -1 to -6 is streams flowing to left
    /**
     * makes a new water
     * 
     * @param isSource      check if this block of water is a water source or just a stream
     */
    public Water(boolean isSource, int type){
        this.isSource = isSource;
        this.imageType = type;
        //resize images
        if(normal.getHeight() != 64){
            normal.scale(64,64);
            for(int i = 0; i < left.length; i++){
                left[i].scale(64,64);
                right[i].scale(64,64);
            }
        }
        //set the image
        if(type < 0 && type > -7){
            setImage(left[-1*type - 1]);
        }
        else if(type > 0 && type < 7){
            setImage(right[type - 1]);
        }
        else{
            setImage(normal);
        }
    }
    /**
     * Act - do whatever the Water wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
    public boolean isSource(){
        return isSource;
    }
}
