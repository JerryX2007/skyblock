import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * <p>
 * The pause screen main which pauses the simulation. If the user wants to pause
 * the simulation, just clicked the screen.
 * <br>
 * <br>
 * This menu has two buttons and one slider:
 * <ul>
 * <li> Resume button <code>resume</code> resumes the simulation
 * <li> Menu button <code>menu</code> redirect the user to the titleScreen
 * <li> Slider <code>VolumeSlider</code> allows the user to adjust the volume of the music of the simulation
 * </ul>
 * </p>
 * 
 * Edited By: Felix Zhao, Benny Wang & Dylan Dinesh
 * 
 * @author Andy Feng
 * @version 0.0.1
 */
public class PauseScreen extends World
{
    private Button menu = new Button("menu", 3, ".png");
    private TitleScreen titleScreen;
    private GameWorld world;
    private Button resume = new Button("resume", 3, ".png");
    private ArrayList<Actor> pauseLocation;
    private GreenfootImage overlay = new GreenfootImage(1260, 720);
    private ValueBox volumeSlider;
    private Image soundOnImg = new Image("sound_on.png"); 
    private Image soundOffImg = new Image("sound_off.png");
    protected static int volume = 100;
    private boolean soundOn = true;
    private int previousVolume;

    /**
     * Constructor for objects of class PauseScreen.
     * 
     * @param TitleScreen titleScreen: make sure the pause world has a titleScreen to go back to
     * @param GameWorld world: the world world which is acting before it is paused, used for resume button
     * @param ArrayList<Actor> actors: used for painting actors on the pauseScreen based under these actors' position in the simulation before
     */
    public PauseScreen(TitleScreen titleScreen, GameWorld world, ArrayList<Actor> actors)
    {    
        // Create a new world with 1260x720 cells with a cell size of 1x1 pixels.
        super(1260, 720, 1);

        // Capture the background of the current game world
        GreenfootImage background = new GreenfootImage(world.getBackground());

        // Apply a mostly transparent gray overlay
        overlay.setColor(new Color(0, 0, 0, 50)); // 50 is the alpha for more transparency
        overlay.fill();
        background.drawImage(overlay, 0, 0);
        setBackground(background);

        // Add buttons
        addObject(menu, getWidth() / 2, getHeight() / 2);
        addObject(resume, getWidth() / 2, getHeight() / 2 + 150);

        // Save parameters
        this.titleScreen = titleScreen;
        this.world = world;
        this.pauseLocation = actors;

        // Add volume slider
        volumeSlider = new ValueBox(0, 100, 35);
        addObject(volumeSlider, getWidth() / 2, getHeight() - 70);
        volumeSlider.update((double) volume / 100);

        // Add sound images
        if (soundOn) {
            addObject(soundOnImg, getWidth() / 2 - 110, getHeight() - 25);
            soundOnImg.getImage().scale(40, 40);
        } else {
            addObject(soundOffImg, getWidth() / 2 - 110, getHeight() - 25);
            soundOffImg.getImage().scale(40, 40);
        }
    }

    /**
     * This act method updates the volume of music based on the volume slider.
     * It also checks whether or not buttons are clicked.
     */
    public void act() {
        if (menu.isPressed()) {
            menu.setPressedCondition(false);
            TitleScreen.setMusicVolume(25);
            TitleScreen.playMusic();
            Greenfoot.setWorld(titleScreen);
        }
        if (resume.isPressed()) {
            Greenfoot.setWorld(world);
            resume.setPressedCondition(false);
        }

        volume = volumeSlider.getValue();
        if (volume > 0) {
            soundOn = true;
            if (soundOffImg.getWorld() != null) {
                removeObject(soundOffImg);
            }
            if (soundOnImg.getWorld() == null) {
                addObject(soundOnImg, getWidth() / 2 - 110, getHeight() - 25);
            }
        } else {
            soundOn = false;
            if (soundOnImg.getWorld() != null) {
                removeObject(soundOnImg);
            }
            if (soundOffImg.getWorld() == null) {
                addObject(soundOffImg, getWidth() / 2 - 110, getHeight() - 25);
                soundOffImg.getImage().scale(40, 40);
            }
        }

        if (Greenfoot.mouseClicked(soundOnImg)) {
            previousVolume = volume;
            volume = 0;
            volumeSlider.update((double) volume / 100);
        }
        if (Greenfoot.mouseClicked(soundOffImg)) {
            volume = previousVolume; 
            volumeSlider.update((double) volume / 100.0);
        }
    }

    /**
     * method which gets the image of actors in the simulation, and draw their 
     * picture at their location before the simulation is paused
     */
    private void getActorImage(Fader blackScreen){
        for(Actor actor : pauseLocation){
            if (actor == blackScreen) {
                continue;
            }
            //   GreenfootImage image = new GreenfootImage(actor.getImage());
            //   if (!(actor instanceof Mouse)) {
            //image.rotate(actor.getRotation());
            //  }

            //  image.setTransparency(actor.getImage().getTransparency());
            //drawImage(image, actor.getX() - image.getWidth()/2, actor.getY() - image.getHeight()/2);
        }
        if(blackScreen.getWorld() == null) {
            return;
        }
        GreenfootImage fader = new GreenfootImage(blackScreen.getImage());
        fader.setTransparency(blackScreen.getImage().getTransparency());
        //  drawImage(fader, blackScreen.getX() - fader.getWidth()/2, blackScreen.getY() - fader.getHeight()/2);
    }

    /**
     * Returns the volume
     * 
     * @return volume
     */
    public static int getVolume() {
        return volume;
    }
}