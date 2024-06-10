import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Adds a grid of blocks to the screen. 
 * Grid coded by Dylan with the aid of ChatGPT
 * 
 * Dylan Dinesh, Jerry, Benny
 * @version (a version number or a date)
 */

public class GameWorld extends World {
    private Block[][] grid;
    
    private boolean openInventory = false;
    private Inventory inventory;
    
    private ChestGUI chest;
    private boolean openChest = false;
 
    private boolean GUIOpened = false;
    
    private Steve player = new Steve(3, 3, 3, true, 3);

    public GameWorld() {    
        // Create a new world with 1280x768 cells with a cell size of 1x1 pixels.
        super(1280, 768, 1);

        // Initialize the grid
        grid = new Block[20][12];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
        prepareWorld();
        // Inventory stuff
        inventory = new Inventory(300, this);
        chest = new ChestGUI(300, this);
        
        addObject(player, 512, 384);
    }

    private boolean keyPreviouslyDown = false;
    private boolean keyPreviouslyDown1 = false;
    
    
    public void act() {
        setPaintOrder(Label.class, Item.class, GUI.class, SuperSmoothMover.class);
        
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory && !GUIOpened) {
                openInventory = true;
                inventory.addInventory();
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                GUIOpened = true;
            } 
            else if(openInventory && GUIOpened){
                openInventory = false;
                inventory.removeInventory();
                removeObject(inventory);
                GUIOpened = false;
            } 
        }

        keyPreviouslyDown = keyCurrentlyDown;
        
        boolean keyCurrentlyDown1 = Greenfoot.isKeyDown("f");
        if (keyCurrentlyDown1 && !keyPreviouslyDown1) {
            if (!openChest && !GUIOpened) {
                openChest = true;
                chest.addChest();
                addObject(chest, getWidth() / 2, getHeight() / 2);
                GUIOpened = true;
            } 
            else if(openChest && GUIOpened){
                openChest = false;
                chest.removeChest();
                removeObject(chest);
                GUIOpened = false;
            }
        }

        keyPreviouslyDown1 = keyCurrentlyDown1;
        
    }

    private void initializeGrid() {
        // Initializing the grid with Air blocks
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

    private void prepareWorld(){
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
        TitleScreen.mainMenu.playLoop();
    }
    public void stopped() {
        TitleScreen.mainMenu.pause();
    }
    
    public Player getPlayer() {
        return player;
    }
}

