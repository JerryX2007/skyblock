import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.time.LocalTime;
import greenfoot.GreenfootSound;

/**
 * <a href="https://www.youtube.com/watch?v=5ChvaSe6aK0"> Link to music</a>
 * 
 * @author Benny
 * @version May 29, 2024
 */
public class TitleScreen extends World
{
    private GreenfootImage background;
    private Button play;
    private GameWorld game;
    private static GreenfootSound mainMenu = new GreenfootSound("mainmenu.mp3");
    private boolean musicStarted = false;

    /**
     * Constructor for objects of class TitleScreen.
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 768, 1); 
        LocalTime now = LocalTime.now();
        int currentHour = now.getHour();
        Image logo = new Image("logo.png", 75);
        if (currentHour >= 6 && currentHour < 10) {
            background = new GreenfootImage("dawn.png");
        } else if (currentHour >= 10 && currentHour < 18) {
            background = new GreenfootImage("titlescreen.png");
        } else if (currentHour >= 18 && currentHour < 20) {
            background = new GreenfootImage("dawn.png");
        } else {
            background = new GreenfootImage("midnight.png");
        }
        setBackground(background);
        play = new Button("pfs", 3, ".png");

        Button.init();

        addObject(play, getWidth() / 2, getHeight() / 2 + 150);
        addObject(logo, getWidth() / 2, 100);
        prepare();
    }

    public void act(){
        checkClick();
        startMusic();
    }

    /**
     * Stops music when greenfoot is stopped
     */
    public void stopped() {
        mainMenu.pause();
    }

    /**
     * Starts playing music when greenfoot is started
     *
     */
    public void started() {
        mainMenu.playLoop();
    }

    /**
     * @return the mainMenu music
     */
    public static GreenfootSound getMainMenuMusic(){
        return mainMenu;
    }

    /**
     * Plays music
     */
    public static void playMusic(){
        mainMenu.playLoop();
    }

    /**
     * Pauses music
     */
    public static void pauseMusic(){
        mainMenu.pause();
    }

    /**
     * Setter for mainMenu volume
     * 
     * @param volume New volume
     */
    public static void setMusicVolume(int volume){
        mainMenu.setVolume(volume);
    }

    /**
     * Start background music
     */
    public void startMusic(){
        if (!musicStarted) { // Start music only once
            mainMenu.setVolume(25);
            mainMenu.playLoop();
            musicStarted = true;
        }
    }

    /**
     * Checks if buttons are pressed
     */
    private void checkClick(){
        if(play.isPressed()){
            game = new GameWorld(this);
            Greenfoot.setWorld(game);
            play.setPressedCondition(false);
        }
    }
    
    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
