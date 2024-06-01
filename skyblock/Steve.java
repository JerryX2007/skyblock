import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Steve here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Steve extends Player
{
    private GreenfootImage hitBox;
    public Steve(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange) {
        super(moveSpeed, jumpHeight, reach, canDrop, pickUpRange, true);
        hitBox = new GreenfootImage("steve/hitBox.png");
        hitBox.scale(40,120);
        setImage(hitBox);
    }
    /**
     * Act - do whatever the Steve wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        super.act();
    }
}
