import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.time.LocalTime;
import greenfoot.GreenfootSound;

/**
 *  
 * <p>
 * The TitleScreen class of the Skyblock, or "SKIDBLOCK" Game
 * </p>
 * <div>
 * <h2> All Assets from Minecraft </h2>
 * <a href="minecraft.net"> Link to Minecraft's Site</a> <br>
 * </div>
 *  * A 2D recreation of Minecraft Skyblock!
 * 
 * The player moves using WAD and shifts the world instead of moving itself
 * Use left click to mine blocks and attack mobs
 * Use right click to interact with blocks such as inventory or place down blocks
 * E to open inventory and close opened interfaces, such as crafting
 * 
 * Mobs will automatically spawn and drop items
 * Lava/water implemented, but players not yet given buckets on spawn
 * Certain items have no effects 
 * Crafting recipes incomplete and sometimes bugs out
 * 
 * Survive for as long as possible!
 * 
 * <h2>List of Features</h2>
 * <p>
 * There's the Item Superclass, Block Superclass, GUI Superclass and the Player Superclass<br>
 * </li>Within the game itself, the user spawns is as the Player, Steve, and in an open sandbox world, the player must get resources, tools, expand their home, and survive the nights from the mobs! </li>
 * <li>If the player survives for 5 days, then a victory screen will be shown!</li><br>
 * <h3> Items </h3>
 * Items include:
 * <ul>
 * <li> Bones </li>
 * <li>Bucket</li>
 * <li>Coal</li>
 * <li>Diamond</li>
 * <li>Feather</li>
 * <li>Gunpowder</li>
 * <li>Iron</li>
 * <li>Leather</li>
 * <li>Stick</li>
 * <li>Stone Pickaxe/Shovel/Sword</li>
 * <li>Wood Pickaxe/Shovel/Sword</li>
 * <li>String</li>
 * <li>Torch</li>
 * </ul>
 * <h3>GUI</h3>
 * The GUI interfaces in this game include the chest GUI, crafting system GUI and the inventory GUI. 
 * All these GUI's are crucial to the game, player's interactions with the game, and for the ease of user experience. 
 *  <h3>Mobs</h3>
 * Mobs will either attack or not attack the player.
 * Mobs in the game include:
 * <ul>
 * <li>Cow</li>
 * <li>Spider</li>
 * <li>Zombie</li>
 * <li>Sheep</li>
 * <li>Creeper</li>
 * </ul>
 * <h3>Blocks</h3>
 * Blocks are crucial to the game. The skyblock world itself is made out of a 2D Array filled with blocks, whether it be with the cobblestone blocks or the air blocks. 
 * There are many blocks in the game, which include:
 * <ul>
 *  <li>Air Blocks (transparent)</li>
 *  <li>Chest</li>
 *  <li>Cobblestone</li>
 *  <li>Wooden Plank</li>
 *  <li>Crafting Table</li>
 *  <li>Diamond Ore</li>
 *  <li>Dirt</li>
 *  <li>Grass</li>
 *  <li>Iron Ore</li>
 *  <li>Lava Source</li>
 *  <li>Leaf</li>
 *  <li>Log</li>
 *  <li>Sapling</li>
 *  <li>TNT</li>
 *  <li>Water Source</li>
 *  <li>Torch Block</li>
 * </ul>
 * <h2>Credits</h2>
 * Most, if not all assets/sounds come from Minecraft itself! As mentioned at the top. 
 * <h3>Music</h3>
 * <a href="https://www.youtube.com/watch?v=5ChvaSe6aK0"> Link to music</a>
 * <h3>Code</h3>
 * SuperSmoothMover and SuperStatBar taken from Jordan Cohen<br>
 * Bar, PauseScreen, Button, Panel, Slider, ValueBox taekn from the previous simulation assignment, with some of the current group with the addition of the support of Felix Zhao and Andy Feng.<br>
 * GifImage, SimpleTimer, Label taken from Greenfoot<br>
 * ChatGPT was used to assist with some of the coding throughout.<br>
 * <h2>Bugs</h2>
 * <li>Originally our save files, loading and reseting the word worked, but after a change to help deal with memory issues, issues with the save/load/resetting occured. 
 * <a href="https://www.youtube.com/watch?v=xgb6DekApIs"><b>Refer to this video of Benny explaining the bug</b></a></li>
 *  <li>Memory issues may occur</li>
 * <li>As of the present commit, crafting is not always consistent and may be buggy.</li>
 * </p>
 * 
 * 
 * <a href="https://www.youtube.com/watch?v=5ChvaSe6aK0"> Link to music</a>
 * 
 * @author Benny
 * Edited by: Dylan Dinesh
 * @version May 29, 2024
 */
public class TitleScreen extends World
{
    private GreenfootImage background;
    private Button play;
    private Button playFromSave;
    private Button playNewWorld;
    private Button credits;
    private Button instructions;
    private GameWorld game;
    private Image instructionsImage;
    private Image creditsImage;
    private static GreenfootSound mainMenu = new GreenfootSound("mainmenu.mp3");
    private boolean musicStarted = false;

    /**
     * Constructor for objects of class TitleScreen.
     */
    public TitleScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1280, 768, 1); 
        game = new GameWorld(this);
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
        playNewWorld = new Button("NEW_WORLD", 3, ".png");
        playFromSave = new Button("pfs", 3, ".png");
        credits = new Button("credits", 3, ".png");
        instructions = new Button("Instructions", 3, ".png");
        Button.init();

        instructionsImage = new Image("instructions_image.png", 1280, 768);
        creditsImage = new Image("credits_image.png", 1280, 768);
        addObject(playNewWorld, getWidth() / 2, getHeight() / 2 + 150);
        addObject(playFromSave, getWidth() / 2, getHeight() / 2 + 200);
        addObject(instructions, getWidth() / 2, getHeight() / 2 + 250);
        addObject(credits, getWidth() / 2, getHeight() / 2 + 300);

        addObject(logo, getWidth() / 2, 100);
        prepare();
    }

    public void act(){
        checkIfPressed();
        startMusic();

        if (instructionsImage != null)
        {
            if(Greenfoot.isKeyDown("escape")){
                removeObject(instructionsImage);
            }
        }

        if (creditsImage != null)
        {
            if(Greenfoot.isKeyDown("escape")){
                removeObject(creditsImage);
            }
        }
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
    private void checkIfPressed()
    {
        if(playFromSave.isPressed()){ // loads world
            Greenfoot.setWorld(game);
            playFromSave.setPressedCondition(false);
        }
        
        else if(playNewWorld.isPressed()){ // creates new world
            GameWorld.deleteStuff();
            // game.clearWorld();
            game = new GameWorld(this);
            Greenfoot.setWorld(game);
            playNewWorld.setPressedCondition(false);
        }
        
        else if(instructions.isPressed()){
            playNewWorld.setPressedCondition(false);
        }
        
        else if(credits.isPressed()){
            
            playNewWorld.setPressedCondition(false);
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
