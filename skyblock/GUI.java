import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * GUI
 * 
 * @author Benny
 * @version May 30, 2024
 */
public class GUI extends Actor
{
    /**
     * Create an image with given file name and size
     * 
     * @param file Location and name of file.
     * @param scale Percentage size of relative to original size.
     */
    public GUI(String file, int scale){
        this.setImage(file);
        getImage().scale((int) (getImage().getWidth() * scale * 0.01), (int) (getImage().getHeight() * scale * 0.01));
    }
}
