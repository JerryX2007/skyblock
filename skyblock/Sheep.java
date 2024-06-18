import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;

/**
 * Write a description of class Sheep here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sheep extends Mob{
    public Sheep(){
        super(false, 0, 2, 10);
        defaultImg = new GreenfootImage("mobs/sheep_1.png");
        movingImg = new GreenfootImage("mobs/sheep_2.png");
        hurtImg = new GreenfootImage("mobs/sheep_3.png");

        defaultImgMirrored = new GreenfootImage("mobs/sheep_1 - Copy.png");
        movingImgMirrored = new GreenfootImage("mobs/sheep_2 - Copy.png");
        hurtImgMirrored = new GreenfootImage("mobs/sheep_3 - Copy.png");
        
        defaultImg.scale(80, 64);
        movingImg.scale(80, 64);
        hurtImg.scale(80, 64);
        
        defaultImgMirrored.scale(80, 64);
        movingImgMirrored.scale(80, 64);
        hurtImgMirrored.scale(80, 64);
        
        setImage(defaultImg);
    }
    
    public void act(){
        super.act();
    }
    
    public void drop(){
        Random random = new Random();
        int choice = random.nextInt(5);
        
        if(choice == 1){
            getWorld().addObject(new ItemDrop(15), getX(), getY());
        }
    }
}
