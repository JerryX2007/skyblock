import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.time.LocalTime;

/**
 * Write a description of class TitleScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TitleScreen extends World
{
    private GreenfootImage background;
    
    /**
     * Constructor for objects of class TitleScreen.
     * 
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1); 
        LocalTime now = LocalTime.now();
        int currentHour = now.getHour();
        Image logo = new Image("logo.png", 75);
        if (currentHour >= 6 && currentHour < 10) {
            background = new GreenfootImage("dawn.png");
        } else if (currentHour >= 10 && currentHour < 18) {
            background = new GreenfootImage("titlescreen.png");
        } else if (currentHour >= 18 && currentHour < 20) {
            background = new GreenfootImage("dawn.png");
        } else {
            background = new GreenfootImage("midnight.png");
        }
        background.scale(1280, 720);
        setBackground(background);
        addObject(logo, getWidth() / 2, 100);
    }
}
