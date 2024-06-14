import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>
 * The scale and bar of the slider
 * When creating a bar, a slider will be created together, they are one
 * set. So please keep in mind to create a bar instead of a slider
 * </p>
 * <p>
 * <a href="https://www.vecteezy.com/vector-art/22908990-slider-menu-set-with-different-color-in-pixel-art-style">Link to art</a>
 * </p>
 * Slider and bar image from vecteezy.com and image edited by Benny Wang<br>
 * 
 * Edited by Felix Zhao
 * 
 * @author Andy
 * @version 1.0 (Apr 5th, 2024)
 * 
 */
public class Bar extends Actor
{
    /**
     * Act - do whatever the Bar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    private MouseInfo mouse;
    private GreenfootImage bar = new GreenfootImage("images/bar.png");
    private Slider s;

    /**
     * the constructor of bar class, when a bar is created, a slider
     * will be created with it
     * 
     * @ parameter
     * @ String controlVariable: tells the bar which instance variable
     * in the modifier world it is going to control
     */
    public Bar(ValueBox valueBox){

        setImage(bar);
        bar.scale((int) (getImage().getWidth() * 1.25), (int) (getImage().getHeight() * 1.4));      
        s = new Slider(valueBox, this);
    }

    /**
     * Addes the slider when this gets added to the world
     *
     * @param w The world
     */
    public void addedToWorld(World w) {
        w.addObject(s, getX() - getImage().getWidth() / 2, getY());
    }

    /**
     * Sets the slider location every act
     *
     */
    public void act()
    {
        // Add your action code here.

        mouse = Greenfoot.getMouseInfo();
        setSliderLocation();
    }

    /**
     * set the location of the slider to the position of the mouse when
     * the mouse is clicking on the bar random location on the bar.
     * 
     */
    private void setSliderLocation(){
        if(Greenfoot.mousePressed(this)){
            s.setLocation(mouse.getX(), getY());
            s.setDrag(true);
        }
    }

    /**
     * Setter for the percent
     * 
     * @param percent what percent it is at
     */
    public void updateValue(double percent) {
        //percent = (double)distance / scale.getImage().getWidth();
        //distance = percent * scale.getImage().getWidth();
        //int distance = getX() - originalX;
        //originalX = scale.getX() - scale.getImage().getWidth() / 2;
        int x = (int)(percent * getImage().getWidth() + getX() - getImage().getWidth() / 2);
        s.setLocation(x, getY());
    }
}