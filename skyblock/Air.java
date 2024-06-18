import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Air here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Air extends Block{
    private GreenfootImage img;

    public Air(){
        super(null,100, "air");
        img = new GreenfootImage("block/air.png");
        img.scale(64, 64);
        setImage(img);
    }

    public void act(){

    }

    public void drop(int itemDrop){

    }
}
