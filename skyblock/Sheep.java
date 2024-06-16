import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sheep here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sheep extends Mob{
    public Sheep(){
        super(false, 0, 2, 10);
        defaultImg = new GreenfootImage("mobs/sheep_1.png");
        movingImg = new GreenfootImage("mobs/sheep_2.png");
        hurtImg = new GreenfootImage("mobs/sheep_3.png");
        
        defaultImg.scale(80, 64);
        movingImg.scale(80, 64);
        hurtImg.scale(80, 64);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
