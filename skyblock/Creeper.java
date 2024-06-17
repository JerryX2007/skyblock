import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Random;
import java.util.List;

/**
 * Write a description of class Creeper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Creeper extends Mob{
    private boolean exploding = false;
    private boolean exploded = false;
    
    public Creeper(){
        super(true, 15, 2, 20);
        defaultImg = new GreenfootImage("mobs/creeper_1.png");
        movingImg = new GreenfootImage("mobs/creeper_2.png");
        hurtImg = new GreenfootImage("mobs/creeper_3.png");
        
        defaultImgMirrored = new GreenfootImage("mobs/creeper_1 - Copy.png");
        movingImgMirrored = new GreenfootImage("mobs/creeper_2 - Copy.png");
        hurtImgMirrored = new GreenfootImage("mobs/creeper_3 - Copy.png");
        
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
        if(exploding){
            promptExplosion();
        }
        if(exploded){
            getWorld().removeObject(this);
        }
    }
    
    public void drop(){
        Random random = new Random();
        int choice = random.nextInt(5);
        
        if(choice == 1){
            getWorld().addObject(new ItemDrop(17), getX(), getY());
        }
    }
    
    /**
     * Override damage with AOE damage instead of contact damage
     */
    public void attack(){
        List<Player> playersInRange = getObjectsInRange(64, Player.class);
        for(Player player : playersInRange){
            exploding = true;
            sleepFor(1000);
        }
    }
    
    /**
     * Explodes and deals massive damage to a player within radius
     */
    private void promptExplosion(){
        if(exploding){
            List<Player> playersInRange = getObjectsInRange(128, Player.class);
            for(Player player : playersInRange){
                player.doDamage(damage);
                if(this.getX() > player.getX()){
                    player.knockBack(1);
                }
                else{
                    player.knockBack(2);
                }
                exploded = true;
            }
        }
        
    }
}
