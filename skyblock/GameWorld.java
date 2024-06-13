import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Keeps track of everything that happens inside the world
 * All block information is stored in a massive 2d array system
 * The user is able to see a portion of the world using a "camera" system cenetered around the player
 * 
 * @author Evan Xi, Benny Wang, Dylan Dinesh
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
    public GameWorld() {    
        // Create a new world with 1280x768 cells with a cell size of 64x64 pixels.
        super(1280, 768, 1, false);

        // Initialize the grid
        grid = new Block[20][12];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
        prepareWorld();
        // Inventory stuff
        inventory = new Inventory(300, this);
        craftingSystem = new CraftingSystem(300, this);
        //addObject(craftingSystem, getWidth()/2, getHeight()/2);
        
        
        player = new Steve(3, 3, 3, true, 3, inventory);
        hpBar = new HealthBar(player);
        addObject(hpBar, 0, 0);
        addObject(player, 512, 384);
    }

    private boolean keyPreviouslyDown = false;
    private boolean keyPreviouslyDown1 = false;

    /**
     * Checks for things happening in the world
     * Tracks chests, inventory, blocks, and more
     */
    public void act() {
        setPaintOrder(Label.class, Item.class, GUI.class, SuperSmoothMover.class);  // Determines what goes on top

        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        
        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory && !GUIOpened) {
                openInventory = true;
                inventory.addInventory();
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                GUIOpened = true;
            } else if(openInventory && GUIOpened){
                openInventory = false;
                inventory.removeInventory();
                removeObject(inventory);
                GUIOpened = false;
            } 
        }
        keyPreviouslyDown = keyCurrentlyDown;
        
        hpBar.setLocation(player.getX(),player.getY() - 90);
    }
    
    /*
     * Getter for openChest
     * 
     * @return openChest openChest value
     */
    public static boolean getOpenChest(){
        return openChest;
    }
    
    /*
     * Getter for GUIOpened
     * 
     * @return GUIOpened GUIOpened value
     */
    public static boolean getGUIOpened(){
        return GUIOpened;
    }
    
    /*
     * Sets the boolean openChest
     * 
     * @param open New value for openChest
     */
    public static void setOpenChest(boolean open){
        openChest = open;
    }
    
    /*
     * Sets the boolean GUIOpened
     * 
     * @param opened New value for GUIOpened
     */
    public static void setGUIOpened(boolean opened){
        GUIOpened = opened;
    }
    
    public void spoofInventory(){
        inventory.addInventory();
        addObject(inventory, getWidth() / 2, getHeight() / 2);
        inventory.act();
        inventory.removeInventory();
        removeObject(inventory);
    }
    
    /**
     * Fill out the entire world with air blocks
     */
    private void initializeGrid() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
                grid[i][j] = new Air();           
            }
        }
    }

    public Block getGridValue(int x, int y) {
        return grid[x][y];
    }

    public void setGridValue(int x, int y, Block block) {
        grid[x][y] = block;
        addActorToGrid(block, x, y);
    }

    public int[] getGridCoordinates(int x, int y) {
        int gridX = x / 64;
        int gridY = y / 64;
        return new int[]{gridX, gridY};
    }

    public int[] getWorldCoordinates(int gridX, int gridY) {
        int worldX = gridX * 64 + 32; // center of the cell
        int worldY = gridY * 64 + 32; // center of the cell
        return new int[]{worldX, worldY};
    }

    public void addActorToGrid(Actor actor, int gridX, int gridY) {
        int[] worldCoordinates = getWorldCoordinates(gridX, gridY);
        addObject(actor, worldCoordinates[0], worldCoordinates[1]);
    }

    /**
     * Replaces the given block on the 2d grid system with another block
     */
    public void updateBlock(int gridX, int gridY, Block newBlock){
        ArrayList<Block> removingBlock = (ArrayList<Block>)getObjectsAt(gridX * 64 + 32, gridY * 64 + 32, Block.class);
        for(Block blocks : removingBlock){
            removeObject(blocks);
        }

        setGridValue(gridX, gridY, newBlock);
    }

    /**
     * Refreshes the entire screen of blocks 
     * 
     */
    public void updateEntireGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid.length; j++){

            }
        }
    }
    
    /**
     * Load in the initial island by placing associated blocks
     * Called when the world is generated
     */
    private void prepareWorld() {
        updateBlock(2, 6, new Chest(this));
        updateBlock(4, 6, new Chest(this));
        for (int i = 2; i < 18; i++){
            updateBlock(i, 8, new Dirt());  
        }
        for (int i = 2; i < 18; i++){
            updateBlock(i, 7, new Grass()); 
        }
        for (int i = 3; i < 17; i++){
            updateBlock(i, 9, new CobbleStone()); 
        }
        for (int i = 5; i < 15; i++){
            updateBlock(i, 10, new CobbleStone());
        }
        for (int i = 10; i < 15; i++){
            updateBlock(i, 3, new Leaf());  
        }
        for (int i = 10; i < 15; i++){
            updateBlock(i, 4, new Leaf());  
        }
        for (int i = 11; i < 14; i++){
            updateBlock(i, 2, new Leaf()); 
        }
        for (int j = 4; j < 7; j++){
            updateBlock(12, j, new Log());  
        }        
    }

    public void started() {
        TitleScreen.getMainMenuMusic().playLoop();
    }

    public void stopped() {
        TitleScreen.getMainMenuMusic().pause();
    }

    public Player getPlayer() {
        return player;
    }
    
    public void openCraftingInterface() {
        if (!isCraftingVisible) {
            addObject(craftingSystem, 400, 300); // Center of the screen, for example
            craftingSystem.showCrafting();
            isCraftingVisible = true;
        }
    }

    public void closeCraftingInterface() {
        if (isCraftingVisible) {
            removeObject(craftingSystem);
            craftingSystem.hideCrafting();
            isCraftingVisible = false;
        }
    }
}

