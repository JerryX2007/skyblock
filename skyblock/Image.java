import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Images here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends Actor
{
    public Image(String file){
        this.setImage(file);
    }
    
    public Image(String file, int scale){
        this.setImage(file);
        getImage().scale((int) (getImage().getWidth() * scale * 0.01), (int) (getImage().getHeight() * scale * 0.01));
    }
}
