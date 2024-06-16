import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Write a description of class Cow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cow extends Mob{
    public Cow(){
        super(false, 0, 2, 10);
        defaultImg = new GreenfootImage("mobs/cow_1.png");
        movingImg = new GreenfootImage("mobs/cow_2.png");
        hurtImg = new GreenfootImage("mobs/cow_3.png");
        
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
