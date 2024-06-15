import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Mob{
    public Zombie(){
        super(true, 0, 2, 20, 0, 5);
        defaultImg = new GreenfootImage("mob/zombie_1.png");
        movingImg = new GreenfootImage("mob/zombie_2.png");
        hurtImg = new GreenfootImage("mob/zombie_3.png");
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
