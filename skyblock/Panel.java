import greenfoot.*;

/**
 * The Panel is for the pause world, it is used to "cover" the painting
 * on top of the pauseScreen. 
 * 
 * @author Andy Feng
 * @version 0.0.1
 */
public class Panel extends Actor
{
    /**
     * Used for getting the dimensions of the world to determine size of image to create.
     *
     * @param world the world to be visually duplicated
     */
    public void addedToWorld(World world)
    {
        int cs=world.getCellSize();
        int w=world.getWidth()*cs, h=world.getHeight()*cs;
        GreenfootImage image=new GreenfootImage(w, h);
        setImage(image);
        updateImage();
    }

    /**
     * Builds the image to display.
     */
    private void updateImage()
    {
        GreenfootImage image=getImage();
        int w=image.getWidth(), h=image.getHeight();
        image.setColor(new Color(192, 192, 192, 96));
        image.fill();
    }
}