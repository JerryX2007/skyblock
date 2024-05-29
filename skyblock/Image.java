import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Images here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends Actor
{
    /**
     * Create an image with given file name
     * 
     * @param file Location and name of file.
     */
    public Image(String file){
        this.setImage(file);
    }
    
    /**
     * Create an image with given file name and size
     * 
     * @param file Location and name of file.
     * @param scale Percentage size of relative to original size.
     */
    public Image(String file, int scale){
        this.setImage(file);
        getImage().scale((int) (getImage().getWidth() * scale * 0.01), (int) (getImage().getHeight() * scale * 0.01));
    }
    
    /**
     * Create an image with given file name, horizontal length and vertical length.
     * 
     * @param file Location and name of file.
     * @param x Length of image.
     * @param y Width of image.
     */
    public Image(String file, int x, int y){
        this.setImage(file);
        getImage().scale(x, y);
    }
}
