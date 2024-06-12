import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * GUI
 * 
 * @author Benny
 * @version May 30, 2024
 */
public class GUI extends Actor
{
    protected static World world;
    protected Inventory inventory;
    protected static ArrayList<Item> itemsList = new ArrayList<>();
    protected ArrayList<Item> contents = new ArrayList<>();
    protected static ArrayList<Item> tempItemsList = new ArrayList<>();
    protected static Item[][] slots = new Item[9][3];
    protected static Item[][] crafting = new Item[2][2];
    protected Item[][] chestSlots = new Item[9][3];
    
    /**
     * Create an image with given file name and size
     * 
     * @param file Location and name of file.
     * @param scale Percentage size of relative to original size.
     */
    public GUI(String file, int scale, World world){
        this.setImage(file);
        getImage().scale((int) (getImage().getWidth() * scale * 0.01), (int) (getImage().getHeight() * scale * 0.01));
    }
}
