    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Adds a grid of blocks to the screen. 
 * Grid coded by Dylan with the aid of ChatGPT
 * 
 * Dylan Dinesh
 * @version (a version number or a date)
 */

public class GameWorld extends World {
    private Block[][] grid;
    private boolean openInventory = false;
    private GUI inventory;
    private ArrayList<Items> itemsList;
    private int xAdjust = 0;
    private int yAdjust = 0;
    private Items[][] slots = new Items[9][3];

    public GameWorld() {    
        // Create a new world with 1280x768 cells with a cell size of 1x1 pixels.
        super(1280, 768, 1);

        // Initialize the grid
        grid = new Block[20][12];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
        prepareWorld();
        // Inventory stuff
        inventory = new GUI("inventory.png", 300, this);
        itemsList = new ArrayList<>();
    }

    private boolean keyPreviouslyDown = false;
    private boolean prevState = false;
    private boolean prevState1 = false;

    public void act() {
        //setPaintOrder(Items.class, Empty.class);
        
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");
        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory) {
                openInventory = true;
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        Empty temp = new Empty(16, 16, this, 424 + xAdjust, getHeight()/2 + 27 + yAdjust);
                        addObject(temp, 424 + xAdjust, getHeight()/2 + 27 + yAdjust);
                        slots[j][i] = temp;
                        xAdjust += 54;
                    }
                    xAdjust = 0;
                    yAdjust += 54;
                }
                for(Items i: itemsList){
                    addObject(i, i.getXPos(), i.getYPos());
                }
                xAdjust = 0;
                yAdjust = 0;
            } else {
                openInventory = false;
                removeObject(inventory);
                for(Items i: itemsList){
                    removeObject(i);
                }
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++) {
                        removeObject(slots[j][i]);
                    }
                }
            }
        }

        keyPreviouslyDown = keyCurrentlyDown;
        boolean currentDown = Greenfoot.isKeyDown("p");
        if(currentDown && !prevState){
            if(openInventory){
                Items temp = new Items("block/wood.png", this, 424, getHeight()/2 + 27, 32, 32, "wood");
                itemsList.add(temp);
                addObject(temp, 424, getHeight()/2 + 27);
            }
        }
        prevState = currentDown;
        
        boolean currentDown1 = Greenfoot.isKeyDown("o");
        if(currentDown1 && !prevState1){
            if(openInventory){
                Items temp = new Items("block/cobblestone.png", this, 424, getHeight()/2 + 27, 32, 32, "cobblestone");
                itemsList.add(temp);
                addObject(temp, 424, getHeight()/2 + 27);
            }
        }
        prevState1 = currentDown1;
    }

    private void initializeGrid() {
        // Example of initializing the grid with Air blocks
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 12; j++) {
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
    
    public void setSlot(int x, int y, String itemName){
        int tempX = slots[x][y].getX();
        int tempY = slots[x][y].getY();
        slots[x][y] = new Items("block/air.png", 16, 16, this, false, tempX, tempY, itemName);
    }

    private void prepareWorld()
    {
        for (int i = 2; i < 18; i++)
        {
            grid[i][8] = new Dirt();
            addActorToGrid(grid[i][8], i, 8);  
        }
        for (int i = 2; i < 18; i++)
        {
            grid[i][7] = new Grass();
            addActorToGrid(grid[i][7], i, 7);  
        }
        for (int i = 3; i < 17; i++)
        {
            grid[i][9] = new CobbleStone();
            addActorToGrid(grid[i][9], i, 9);  
        }
        for (int i = 5; i < 15; i++)
        {
            grid[i][10] = new CobbleStone();
            addActorToGrid(grid[i][10], i, 10);  
        }

        for (int i = 10; i < 15; i++)
        {
            grid[i][4] = new Leaf();
            addActorToGrid(grid[i][4], i, 3);  
        }
        for (int i = 10; i < 15; i++)
        {
            grid[i][5] = new Leaf();
            addActorToGrid(grid[i][5], i, 4);  
        }
        for (int i = 11; i < 14; i++)
        {
            grid[i][3] = new Leaf();
            addActorToGrid(grid[i][3], i, 2);  
        }

        for (int j = 4; j < 7; j++)
        {
            grid[12][j] = new Log();
            addActorToGrid(grid[12][j], 12, j);  
        }

        for (int i = 0; i < 1; i++)
        {
            grid[12][1] = new Leaf();
            addActorToGrid(grid[12][1], 12, 1);  
        }
    }
}

