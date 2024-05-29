import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>
 * Has the logic to detect the mouse relation to the button
 * </p>
 * 
 * <a href="https://www.youtube.com/watch?v=h8y0JMVwdmM">Link to sound</a>
 * Sounds by Minecraft<br>
 * Editied by Benny Wang, Andy Feng and Evan Xi
 * 
 * @author Felix Zhao
 * @version 1/18/2023
 * 
 */
public class Button extends Actor
{
    private boolean hover = false;
    private boolean mouseDown = false;
    protected GreenfootImage[] imageStates;
    private static GreenfootSound[] click;
    private static int clickIndex = 0;
    
    private boolean pressed = false;
    
    /**
     * The construtor for the Button
     * @param imageStates Where the image is located
     * @param numstates Is how many different states there are of that image (at most 3 are used)
     * @param imageType The file type (.png, .jpeg, etc.) (Include the dot)
     */
    public Button(String imagePath, int numStates, String imageType) {
        GreenfootImage image = new GreenfootImage(imagePath + "_1" + imageType);
        setImage(image);
        imageStates = new GreenfootImage[numStates];
        imageStates[0] = image;
        for (int i = 1; i < numStates; i++) {
            imageStates[i] = new GreenfootImage(imagePath + "_" + (i+1) + ".png");
        }
    }
    
    /**
     * Same as above but with a certain size
     *
     * @param imageStates Where the image is located
     * @param numstates Is how many different states there are of that image (at most 3 are used)
     * @param imageType The file type (.png, .jpeg, etc.) (Include the dot)
     * @param initialSize The size
     */
    public Button(String imagePath, int numStates, String imageType, int initialSize) {
        GreenfootImage image = new GreenfootImage(imagePath + "_1" + imageType);
        image.scale(initialSize, initialSize);
        setImage(image);
        imageStates = new GreenfootImage[numStates];
        imageStates[0] = image;
        for (int i = 1; i < numStates; i++) {
            imageStates[i] = new GreenfootImage(imagePath + "_" + (i+1) + ".png");
            imageStates[i].scale(initialSize, initialSize);
        }
    }
    
    /**
     * Checks the state of the mouse in relation to the button
     * If the mouse is hovering
     * If the mouse is clicked on the button
     * If the mouse let go after clicking on the button
     */
    public void act()
    {
        if (pressed) {
            pressed = false;
        }
        
        if (Greenfoot.mouseMoved(this)) {
            hover = true;
            if (imageStates.length > 1) {
                setImage(imageStates[1]);
            }
        } else if (Greenfoot.mouseMoved(null)) {
            hover = false;
            mouseDown = false;
            
            if (imageStates.length > 2) {
                setImage(imageStates[0]);
            }
        }
        
        if (Greenfoot.mousePressed(this) && !mouseDown) {
            mouseDown = true;
            if (imageStates.length > 2) {
                setImage(imageStates[2]);
            }
        }
                                                                                                                                                                                                                                                                       
        if (Greenfoot.mouseClicked(this) && mouseDown) {
            playClick();
            //action();
            
            pressed = true;
            
            mouseDown = false;
            if (imageStates.length > 2) {
                setImage(imageStates[1]);
            }

        } else if (Greenfoot.mouseClicked(null)) {
            mouseDown = false;
            if (imageStates.length > 2) {
                setImage(imageStates[0]);
            }

        }
    }
    
    /**
     * Sets the scale for the buttons
     * @param width sets the width of the button to width
     * @param height sets the height of the button to height
     */
    public void setScale(int width, int height) {
        for (int i = 0; i < imageStates.length; i++) {
            imageStates[i].scale(width, height);
        }
    }
    
    /**
     * Preload sounds
     */
    public static void init(){
        clickIndex = 0;
        click = new GreenfootSound[100];
        for (int i = 0; i < click.length; i++){
            click[i] = new GreenfootSound("click.mp3");
            click[i].play();
            Greenfoot.delay(1);
            click[i].stop();
        }
    }
    
    /**
     * Play click sound
     */
    public void playClick(){
        click[clickIndex].setVolume(50);
        click[clickIndex].play();
        clickIndex++;
        if (clickIndex >= click.length){
            clickIndex = 0;
        }
    }

    /**
     * Setter for pressed
     * @param trueOrFalse sets press as true or false
     */
    public void setPressedCondition(boolean trueOrFalse){
        pressed = trueOrFalse;
    }
    
    /**
     * Action does nothing in this class but should be in the subclasses
     */
    public void action() {
        // does nothing should be in sub class
    }
    
    /**
     * Getter for pressed
     */
    public boolean isPressed(){
        return pressed;
    }
}
