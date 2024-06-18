import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TNT here.
 * 
 * At the moment, TNT is more of a cosmetic block and does not explode (due to the lack of flint & steel)
 */
public class TNT extends Block
{
    private static Color brown = new Color(77, 50, 36);
    private GreenfootImage img;
    private GreenfootImage img2;

    public TNT(){
        super(brown,2, "tnt");
        img = new GreenfootImage("block/TNT.png");
        img.scale(64,64);
        setImage(img);
        img2 = addBorder(img, black);
        isWood = true;
        itemDrop = 8;
    }
    /**
     * Act - do whatever the Dirt wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        super.act();
        if(isSelected){
            setImage(img2);
        }
        if(!isSelected){
            setImage(img);
        }
    }
}
