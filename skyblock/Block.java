import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Block here.
 * - blocks are all squares
 * - blocks stops entities from falling through them
 * - blocks can be broken
 * - blocks drop when they are broken
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class  Block extends Actor
{
    private static int blockSize = 32;//how much pixels is a block
    protected String color;//color of the block
    protected double hardness;//how long will it take to break
    protected boolean isWood;// i am a wood and an axe will break me faster
    protected boolean isStone;// i am a stone and an picaxe will break me faster
    protected boolean isDirt;// i am a dirt and a shovel will break me faster
    public Block(String color, double hardness){
        this.color = color;
        this.hardness = hardness;
    }
    
    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
