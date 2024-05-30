import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

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

    public GameWorld() {    
        // Create a new world with 1280x720 cells with a cell size of 1x1 pixels.
        super(1280, 768, 1);

        // Initialize the grid
        grid = new Block[16][9];

        // Optionally fill the grid with initial values or objects
        initializeGrid();
    }

    private void initializeGrid() {
        // Example of initializing the grid with Dirt blocks
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (j<5) {
                    grid[i][j] = new Air();
                }
                else {
                    grid[i][j] = new CobbleStone();
                }
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
        int worldX = gridX * 64 + 40; // center of the cell
        int worldY = gridY * 64 + 40; // center of the cell
        return new int[]{worldX, worldY};
    }

    public void addActorToGrid(Actor actor, int gridX, int gridY) {
        int[] worldCoordinates = getWorldCoordinates(gridX, gridY);
        addObject(actor, worldCoordinates[0], worldCoordinates[1]);
    }
}
