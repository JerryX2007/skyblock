import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TorchBlock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TorchBlock extends Block
{
    private static Color brown = new Color(77, 50, 36);
    private GreenfootImage img;
    private GreenfootImage img2;

    public TorchBlock(){
        super(brown, 0.1, "torch");
        img = new GreenfootImage("block/Torch_Block.png");
        img.scale(32,32);
        setImage(img);
        img2 = addBorder(img, black);
        isWood = true;
        itemDrop = 14;
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
