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
        super(false, 0, 2, 10, 10, 15);
        defaultImg = new GreenfootImage("mob/cow_1.png");
        movingImg = new GreenfootImage("mob/cow_2.png");
        hurtImg = new GreenfootImage("mob/cow_3.png");
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
