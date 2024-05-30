import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Adds a grid of blocks to the screen. 
 * Grid coded by Dylan with the aid of ChatGPT
 * 
 * Dylan Dinesh
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    private Block[][] grid;
    private boolean openInventory = false;
    private GUI inventory;
    private Items dirtBlock;
    private ArrayList<Items> itemsList;

    public GameWorld() {    
        // Create a new world with 1280x720 cells with a cell size of 1x1 pixels.
        super(1280, 720, 1);

        // Initialize the grid
        grid = new Block[16][9];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
        
        // Inventory stuff
        inventory = new GUI("inventory.png", 300);
        dirtBlock = new Items("dirtBlock.png", this);
        itemsList = new ArrayList<>();
    }
    
    private boolean keyPreviouslyDown = false;
    private boolean prevState = false;

    public void act() {
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
    
        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory) {
                openInventory = true;
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                for(Items i: itemsList){
                    addObject(i, i.getXPos(), i.getYPos());
                }
            } else {
                openInventory = false;
                removeObject(inventory);
                for(Items i: itemsList){
                    removeObject(i);
                }
            }
        }
    
        keyPreviouslyDown = keyCurrentlyDown;
        boolean currentDown = Greenfoot.isKeyDown("p");
        if(currentDown && !prevState){
            if(openInventory){
                int tempX = getWidth() / 2 - 20 + Greenfoot.getRandomNumber(20);
                int tempY = getHeight() / 2 - 20 + Greenfoot.getRandomNumber(20);
                Items temp = new Items("dirtBlock.png", this, tempX, tempY);
                itemsList.add(temp);
                addObject(temp, tempX, tempY);
            }
        }
        prevState = currentDown;
    }


    private void initializeGrid() {
        // Example of initializing the grid with Dirt blocks
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Air();
                addActorToGrid(grid[i][j], i, j);
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
        int gridX = x / 80;
        int gridY = y / 80;
        return new int[]{gridX, gridY};
    }

    public int[] getWorldCoordinates(int gridX, int gridY) {
        int worldX = gridX * 80 + 40; // center of the cell
        int worldY = gridY * 80 + 40; // center of the cell
        return new int[]{worldX, worldY};
    }

    public void addActorToGrid(Actor actor, int gridX, int gridY) {
        int[] worldCoordinates = getWorldCoordinates(gridX, gridY);
        addObject(actor, worldCoordinates[0], worldCoordinates[1]);
    }
}
