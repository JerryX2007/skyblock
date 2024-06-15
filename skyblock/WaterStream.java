import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Water here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterStream extends WaterSource
{
    
    public static GreenfootImage[] left = { new GreenfootImage("liquid/Lw1.png"),new GreenfootImage("liquid/Lw2.png"),new GreenfootImage("liquid/Lw3.png"),
                                            new GreenfootImage("liquid/Lw4.png"),new GreenfootImage("liquid/Lw5.png"),new GreenfootImage("liquid/Lw6.png")};
    public static GreenfootImage[] right = { new GreenfootImage("liquid/Rw1.png"),new GreenfootImage("liquid/Rw2.png"),new GreenfootImage("liquid/Rw3.png"),
                                             new GreenfootImage("liquid/Rw4.png"),new GreenfootImage("liquid/Rw5.png"),new GreenfootImage("liquid/Rw6.png")};
    public static GreenfootImage normal = new GreenfootImage("liquid/w0.png");
    
    private WaterSource previousWater;
    private int actNum;
    private int streamNumber;
    private int streamLength;
    private boolean direction;//true is right, false is left
    private int imageType; //0 is w0.png; 1-6 is streams flowing to right and -1 to -6 is streams flowing to left
    /**
     * makes a new water
     * 
     * @param previous      the previous block of water
     */
    public WaterStream(WaterSource previous){
        previousWater = previous;
        if(previous != null){
            resizeImages();
            updateImage();
            setState();
        }
        actNum = 0;
    }
    /**
     * Act - do whatever the Water wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        actNum++;
        if(actNum % 60 == 0){
            if(previousWater.isRemoved()){
                remove();
            }
            else{
                flow();
            }
            
        }
        if(isRemoved){
            getWorld().removeObject(this);
        }
    }
    private void resizeImages(){
        //resize images
        if(normal.getHeight() != 64){
            normal.scale(64,64);
            for(int i = 0; i < left.length; i++){
                left[i].scale(64,64);
                right[i].scale(64,64);
            }
        }
    }
    private void updateImage(){
        //set the image
        if(imageType < 0 && imageType > -7){
            setImage(left[-1*imageType - 1]);
        }
        else if(imageType > 0 && imageType < 7){
            setImage(right[imageType - 1]);
        }
        else{
            setImage(normal);
        }
    }
    public void setState(){
        if(previousWater instanceof WaterStream){

            streamNumber = ((WaterStream) previousWater).getStreamNum();
            streamLength = ((WaterStream) previousWater).getStreamLength() + 1;
        }
        else{
            streamNumber = 0;
        }
    }
    public boolean getDirection(){
        return direction;
    }
    public void setDirection(boolean d){
        direction = d;
    }
    public int getStreamNum(){
        return streamNumber;
    }
    public void setStreamNum(int i){
        streamNumber = i;
    }
    public int getStreamLength(){
        return streamLength;
    }
    public void setStreamLength(int i){
        streamLength = i;
    }
}
