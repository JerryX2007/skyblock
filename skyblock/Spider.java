import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Write a description of class Spider here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spider extends Mob{
    public Spider(){
        super(true, 4, 2.5, 16);
        defaultImg = new GreenfootImage("mobs/spider_1.png");
        movingImg = new GreenfootImage("mobs/spider_2.png");
        hurtImg = new GreenfootImage("mobs/spider_3.png");
        
        defaultImgMirrored = new GreenfootImage("mobs/spider_1 - Copy.png");
        movingImgMirrored = new GreenfootImage("mobs/spider_2 - Copy.png");
        hurtImgMirrored = new GreenfootImage("mobs/spider_3 - Copy.png");
        
        defaultImg.scale(64, 32);
        movingImg.scale(64, 32);
        hurtImg.scale(64, 32);
        
        defaultImgMirrored.scale(64, 32);
        movingImgMirrored.scale(64, 32);
        hurtImgMirrored.scale(64, 32);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        Random random = new Random();
        int choice = random.nextInt(5);
        
        if(choice == 1){
            getWorld().addObject(new ItemDrop(18), getX(), getY());
        }
    }
}
