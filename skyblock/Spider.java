import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider extends Mob{
    public Spider(){
        super(true, 0, 2.5, 16, 0, 5);
        defaultImg = new GreenfootImage("mob/spider_1.png");
        movingImg = new GreenfootImage("mob/spider_2.png");
        hurtImg = new GreenfootImage("mob/spider_3.png");
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
