import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sheep here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sheep extends Mob{
    public Sheep(){
        super(false, 0, 2, 10, 10, 15);
        defaultImg = new GreenfootImage("mob/sheep_1.png");
        movingImg = new GreenfootImage("mob/sheep_2.png");
        hurtImg = new GreenfootImage("mob/sheep_3.png");
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
