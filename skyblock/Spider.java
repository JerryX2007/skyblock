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
        defaultImg = new GreenfootImage("mobs/spider_1.png");
        movingImg = new GreenfootImage("mobs/spider_2.png");
        hurtImg = new GreenfootImage("mobs/spider_3.png");
        
        defaultImg.scale(64, 32);
        movingImg.scale(64, 32);
        hurtImg.scale(64, 32);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
