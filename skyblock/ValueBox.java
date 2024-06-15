import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * <p>
 * An actor that stores an integer in a range. The number can be set by typing it in.
 * It goes with the slider
 * </p>
 * 
 * @author Felix Zhao
 * @version 0.01 April 11th, 2024
 */
public class ValueBox extends Actor
{
    private final static Color BASIC_FILL = new Color(255, 255, 255);

    private Bar bar;
    private int minVal;
    private int maxVal;
    private int currentVal;
    private Label textLabel;
    private int yOffset;

    // Pressing variables
    private boolean mouseDown = false;
    private boolean hover = false;
    private boolean isTyping = false;
    private String currentlyTyping = "";
    private int typeCooldown = 10;
    private boolean showBlink = false;

    private int blinkCooldown = 30;

    /**
     * Creates an instance of valueBox
     *
     * @param min The min value for the valueBox
     * @param max The max value for the valueBox
     * @param yOffset Offsets the slider by the value
     */
    public ValueBox(int min, int max, int yOffset) {
        minVal = min;
        maxVal = max;
        currentVal = min;
        textLabel = new Label(min, 70);
        setImage(textLabel.getImage());
        this.yOffset = yOffset + 5;
        bar = new Bar(this);
    }

    /**
     * Adds the bar/slider into the world with it
     *
     * @param w The world the object was placed into
     */
    public void addedToWorld(World w) {
        w.addObject(bar, getX(), getY() + yOffset+3);
    }

    /**
     * The act method checks if the user clicks on it and types on to it
     * 
     */
    public void act()
    {
        textLabel.setFillColor(BASIC_FILL);
        if (Greenfoot.mouseMoved(this)) {
            hover = true;

        } else if (Greenfoot.mouseMoved(null)) {
            hover = false;
            mouseDown = false;
        }

        if (Greenfoot.mousePressed(this) && !mouseDown) {
            mouseDown = true;
        }

        if (Greenfoot.mouseClicked(this) && mouseDown) {
            mouseDown = false;
            isTyping = true;
        } else if ((!Greenfoot.mousePressed(this) && Greenfoot.mousePressed(null) && isTyping)) {
            mouseDown = false;
            isTyping = false;
            currentlyTyping = "";
            textLabel.setValue(currentVal);
            bar.updateValue((double)(currentVal-minVal)/(maxVal-minVal));
            setImage(textLabel.getImage());
        }
        typeCooldown -= 1;

        if (isTyping && typeCooldown <= 0) {
            blinkCooldown -= 1;
            if (blinkCooldown <= 0) {
                showBlink = !showBlink;

                blinkCooldown = 30;
            }
            String key = Greenfoot.getKey();
            if (currentlyTyping.length() < 3) {
                if (key != null && key.matches("\\d+")) {
                    currentlyTyping += key;
                    typeCooldown = 10;
                }
            } 
            if (Greenfoot.isKeyDown("Enter")) {
                isTyping = false;
                int value = Integer.parseInt(currentlyTyping);
                value = Math.max(minVal, value);
                value = Math.min(maxVal, value);
                textLabel.setValue(value);
                currentVal = value;
                currentlyTyping = "";
                typeCooldown = 10;
                bar.updateValue((double)(currentVal-minVal)/(maxVal-minVal));
            } else if (Greenfoot.isKeyDown("Escape")) {
                isTyping = false;
                currentlyTyping = "";
                textLabel.setValue(currentVal);
                typeCooldown = 10;
                bar.updateValue((double)(currentVal-minVal)/(maxVal-minVal));
            } else if (Greenfoot.isKeyDown("Backspace") && currentlyTyping.length() > 0) {
                currentlyTyping = currentlyTyping.substring(0, currentlyTyping.length()-1);
                typeCooldown = 10;
            }
            // has to check again because it could be changed

            if (isTyping) {
                if (showBlink && currentlyTyping.length() < 3) {
                    textLabel.setValue(currentlyTyping + "I");
                } else {
                    textLabel.setValue(currentlyTyping);
                }
                if (!currentlyTyping.equals("")) {
                    int typingValue = Integer.parseInt(currentlyTyping);
                    typingValue = Math.max(minVal, typingValue);
                    typingValue = Math.min(maxVal, typingValue);
                    bar.updateValue((double)(typingValue-minVal)/(maxVal-minVal));
                }

            }
            setImage(textLabel.getImage());
        }

    }

    /**
     * Updates the value of the valueBox given a percent
     *
     * @param percent The percent that determines what the value (0% - minVal to 100% - maxVal)
     */
    public void update(double percent) {
        //System.out.println(percent);

        currentVal = (int)(minVal*(1-percent)+maxVal*percent);
        textLabel.setValue(currentVal);
        setImage(textLabel.getImage());
        bar.updateValue(percent);
    }

    /**
     * Returns the current value of the instance
     *
     * @return Returns the current value
     */
    public int getValue() {
        return currentVal;
    }
}