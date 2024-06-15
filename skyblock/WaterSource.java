import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Write a description of class WaterSource here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaterSource extends Block
{
    public static GreenfootImage water = new GreenfootImage("liquid/w0.png");
    private int actNum;
    protected boolean isRemoved;
    public WaterSource(){
        super(null,99999, "WaterSource");
        if(water.getHeight() != 64)
            water.scale(64,64);
            setImage(water);
        actNum = 0;
    }
    /**
     * Act - do whatever the WaterSource wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        actNum++;
        if(actNum % 60 == 0){
            flow();
        }
        if(isRemoved){
            getWorld().removeObject(this);
        }
    }
    //makes to water flow downwards
    protected void flow(){
        //check if there is block to the side of the water
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);
        if(below == null){
            //add a water stream below
            getWorld().addObject(new WaterStream(this), getX(), getY() + 64);
        }
        else if(!(below instanceof WaterSource || below instanceof WaterStream)){
            if(left == null){
                //add a water stream to left if cant flow down
                getWorld().addObject(new WaterStream(this), getX() - 64, getY());
            }
            if(right == null){
                //add a water stream to right if cant flow down
                getWorld().addObject(new WaterStream(this), getX() + 64, getY());
            }
        }
    }
    protected void remove(){
        isRemoved = true;
    }
    protected boolean isRemoved(){
        return isRemoved;
    }
}
