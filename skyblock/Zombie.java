import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Write a description of class Zombie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Zombie extends Mob{
    public Zombie(){
        super(true, 4, 2, 20);
        defaultImg = new GreenfootImage("mobs/zombie_1.png");
        movingImg = new GreenfootImage("mobs/zombie_2.png");
        hurtImg = new GreenfootImage("mobs/zombie_3.png");
        
        defaultImgMirrored = new GreenfootImage("mobs/zombie_1 - Copy.png");
        movingImgMirrored = new GreenfootImage("mobs/zombie_2 - Copy.png");
        hurtImgMirrored = new GreenfootImage("mobs/zombie_3 - Copy.png");
        
        defaultImg.scale(64, 128);
        movingImg.scale(64, 128);
        hurtImg.scale(64, 128);
        
        defaultImgMirrored.scale(64, 128);
        movingImgMirrored.scale(64, 128);
        hurtImgMirrored.scale(64, 128);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        Random random = new Random();
        int choice = random.nextInt(10);
        
        if(choice == 1){
            getWorld().addObject(new ItemDrop(19), getX(), getY());
        }
        else if(choice == 2){
            getWorld().addObject(new ItemDrop(20), getX(), getY());
        }
    }
}
