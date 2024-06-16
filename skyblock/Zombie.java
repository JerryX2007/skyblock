import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Mob{
    public Zombie(){
        super(true, 0, 2, 20);
        defaultImg = new GreenfootImage("mobs/zombie_1.png");
        movingImg = new GreenfootImage("mobs/zombie_2.png");
        hurtImg = new GreenfootImage("mobs/zombie_3.png");
        
        defaultImg.scale(64, 128);
        movingImg.scale(64, 128);
        hurtImg.scale(64, 128);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
