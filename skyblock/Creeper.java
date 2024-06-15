import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Creeper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Creeper extends Mob{
    public Creeper(){
        super(true, 0, 2, 20, 0, 5);
        defaultImg = new GreenfootImage("mob/creeper_1.png");
        movingImg = new GreenfootImage("mob/creeper_2.png");
        hurtImg = new GreenfootImage("mob/creeper_3.png");
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        
    }
}
