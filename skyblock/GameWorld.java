import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Keeps track of everything that happens inside the world.
 * All block information is stored in a massive 2D array system.
 * The user is able to see a portion of the world using a "camera" system centered around the player.
 * 
 * @author 
 * @version 1.0.0
 */
public class GameWorld extends World {
    private CraftingSystem craftingSystem;
    private boolean isCraftingVisible = false;
    private Block[][] grid;
    private static boolean openInventory = false;
    private static Inventory inventory;
    private static boolean openChest = false;
    private static boolean GUIOpened = false;
    private Steve player;
    private HealthBar hpBar;
    private boolean keyPreviouslyDown = false;
    private boolean keyPreviouslyDown1 = false;

    /**
     * Constructor for objects of class GameWorld.
     * Initializes the world, grid, player, inventory, and other components.
     */
    public GameWorld() {    
        // Create a new world with 1280x768 cells with a cell size of 64x64 pixels.
        super(1280, 768, 1, false);

        // Initialize the grid
        grid = new Block[20][12];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
        prepareWorld();

        // Inventory initialization
        inventory = new Inventory(300, this);
        craftingSystem = new CraftingSystem(300, this);

        // Player and health bar initialization
        player = new Steve(3, 3, 3, true, 3, inventory);
        hpBar = new HealthBar(player);
        addObject(hpBar, 0, 0);
        addObject(player, 640, 384);
    }

    /**
     * Act method that is called repeatedly to check for actions in the world.
     * Tracks inventory, GUI, and player health bar.
     */
    public void act() {
        // Determines what goes on top
        setPaintOrder(Label.class, Item.class, GUI.class, SuperSmoothMover.class);

        // Inventory toggle logic
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        boolean keyCurrentlyDown1 = Greenfoot.isKeyDown("p");
        
        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory && !GUIOpened) {
                openInventory = true;
                inventory.addInventory();
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                GUIOpened = true;
            } else if (openInventory && GUIOpened && !openChest) {
                openInventory = false;
                inventory.removeInventory();
                removeObject(inventory);
                GUIOpened = false;
            } 
        }
        keyPreviouslyDown = keyCurrentlyDown;
        
        // Update health bar position
        hpBar.setLocation(player.getX(), player.getY() - 90);
        
        // Debugging: print player's Y coordinate when 'p' is pressed
        if (keyCurrentlyDown1) {
            System.out.println(player.getY());
        }
    }

    /**
     * Getter for openChest.
     * 
     * @return boolean value of openChest.
     */
    public static boolean getOpenChest() {
        return openChest;
    }

    /**
     * Getter for GUIOpened.
     * 
     * @return boolean value of GUIOpened.
     */
    public static boolean getGUIOpened() {
        return GUIOpened;
    }

    /**
     * Setter for openChest.
     * 
     * @param open New value for openChest.
     */
    public static void setOpenChest(boolean open) {
        openChest = open;
    }

    /**
     * Setter for GUIOpened.
     * 
     * @param opened New value for GUIOpened.
     */
    public static void setGUIOpened(boolean opened) {
        GUIOpened = opened;
    }

    /**
     * Simulates inventory actions.
     */
    public void spoofInventory() {
        inventory.addInventory();
        addObject(inventory, getWidth() / 2, getHeight() / 2);
        inventory.act();
        inventory.removeInventory();
        removeObject(inventory);
    }

    /**
     * Initializes the grid with air blocks.
     */
    private void initializeGrid() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                grid[i][j] = new Air();           
            }
        }
    }

    /**
     * Gets the block at specified grid coordinates.
     * 
     * @param x X coordinate in the grid.
     * @param y Y coordinate in the grid.
     * @return Block at the specified coordinates.
     */
    public Block getGridValue(int x, int y) {
        return grid[x][y];
    }

    /**
     * Sets the block at specified grid coordinates and adds it to the world.
     * 
     * @param x X coordinate in the grid.
     * @param y Y coordinate in the grid.
     * @param block The block to place at the specified coordinates.
     */
    public void setGridValue(int x, int y, Block block) {
        grid[x][y] = block;
        addActorToGrid(block, x, y);
    }

    /**
     * Converts world coordinates to grid coordinates.
     * 
     * @param x X coordinate in the world.
     * @param y Y coordinate in the world.
     * @return Array containing grid coordinates {gridX, gridY}.
     */
    public int[] getGridCoordinates(int x, int y) {
        int gridX = x / 64;
        int gridY = y / 64;
        return new int[]{gridX, gridY};
    }

    /**
     * Converts grid coordinates to world coordinates.
     * 
     * @param gridX X coordinate in the grid.
     * @param gridY Y coordinate in the grid.
     * @return Array containing world coordinates {worldX, worldY}.
     */
    public int[] getWorldCoordinates(int gridX, int gridY) {
        int worldX = gridX * 64 + 32; // center of the cell
        int worldY = gridY * 64 + 32; // center of the cell
        return new int[]{worldX, worldY};
    }

    /**
     * Adds an actor to the grid at the specified coordinates.
     * 
     * @param actor The actor to add.
     * @param gridX X coordinate in the grid.
     * @param gridY Y coordinate in the grid.
     */
    public void addActorToGrid(Actor actor, int gridX, int gridY) {
        int[] worldCoordinates = getWorldCoordinates(gridX, gridY);
        addObject(actor, worldCoordinates[0], worldCoordinates[1]);
    }

    /**
     * Replaces the given block on the 2D grid system with another block.
     * 
     * @param gridX X coordinate in the grid.
     * @param gridY Y coordinate in the grid.
     * @param newBlock The new block to place.
     */
    public void updateBlock(int gridX, int gridY, Block newBlock) {
        ArrayList<Block> removingBlock = (ArrayList<Block>) getObjectsAt(gridX * 64 + 32, gridY * 64 + 32, Block.class);
        for (Block blocks : removingBlock) {
            removeObject(blocks);
        }
        setGridValue(gridX, gridY, newBlock);
    }

    /**
     * Shifts the entire world, creating a scrolling effect.
     * 
     * @param xShift The number of pixels all objects move on the X-axis.
     * @param yShift The number of pixels all objects move on the Y-axis.
     */
    public void shiftWorld(double xShift, double yShift) {
        ArrayList<Actor> allActors = (ArrayList<Actor>) getObjects(Actor.class);
        for (Actor object : allActors) {
            object.setLocation((int) (object.getX() + xShift + 0.5), (int) (object.getY() + yShift + 0.5));
        }
    }

    /**
     * Moves the player in the opposite direction to reverse the shift effect.
     * 
     * @param xShift The number of pixels the player moves on the X-axis.
     * @param yShift The number of pixels the player moves on the Y-axis.
     */
    public void reverseShiftPlayer(double xShift, double yShift) {
        ArrayList<Player> allPlayers = (ArrayList<Player>) getObjects(Player.class);
        for (Actor player : allPlayers) {
            player.setLocation((int) (player.getX() - xShift + 0.5), (int) (player.getY() - yShift + 0.5));
        }
    }

    /**
     * Loads the initial island by placing associated blocks.
     * Called when the world is generated.
     */
    private void prepareWorld() {
        updateBlock(2, 6, new Chest(this));
        updateBlock(4, 6, new Chest(this));
        updateBlock(5, 6, new CraftingTable(this));
        for (int i = 2; i < 18; i++) {
            updateBlock(i, 8, new Dirt());
        }
        for (int i = 2; i < 18; i++) {
            updateBlock(i, 7, new Grass());
        }
        for (int i = 3; i < 17; i++) {
            updateBlock(i, 9, new CobbleStone());
        }
        for (int i = 5; i < 15; i++) {
            updateBlock(i, 10, new CobbleStone());
        }
        for (int i = 10; i < 15; i++) {
            updateBlock(i, 3, new Leaf());
        }
        for (int i = 10; i < 15; i++) {
            updateBlock(i, 4, new Leaf());
        }
        for (int i = 11; i < 14; i++) {
            updateBlock(i, 2, new Leaf());
        }
        for (int j = 4; j < 7; j++) {
            updateBlock(12, j, new Log());
        }
    }

    /**
     * Starts the main menu music when the world starts.
     */
    public void started() {
        TitleScreen.getMainMenuMusic().playLoop();
    }

    /**
     * Pauses the main menu music when the world stops.
     */
    public void stopped() {
        TitleScreen.getMainMenuMusic().pause();
    }

    /**
     * Getter for the player.
     * 
     * @return The player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Opens the crafting interface if it is not already visible.
     */
    public void openCraftingInterface() {
        if (!isCraftingVisible) {
            addObject(craftingSystem, getWidth()/2, getHeight()/2);
            craftingSystem.showCrafting();
            isCraftingVisible = true;
        }
    }

    /**
     * Closes the crafting interface if it is visible.
     */
    public void closeCraftingInterface() {
        if (isCraftingVisible) {
            removeObject(craftingSystem);
            craftingSystem.hideCrafting();
            isCraftingVisible = false;
        }
    }
}
