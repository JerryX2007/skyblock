import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.Random;

/**
 * Keeps track of everything that happens inside the world.
 * All block information is stored in a massive 2D array system.
 * The user is able to see a portion of the world using a "camera" system centered around the player.
 * 
 * @author Evan Xi, Benny Wang
 * @version 1.0.0
 */
public class GameWorld extends World {
    private CraftingSystem craftingSystem;
    private boolean isCraftingVisible = false;
    public static Block[][] grid = new Block[100][36];
    private TitleScreen titleScreen;
    private ArrayList<Actor> actorList;
    private Fader blackScreen;
    private ChestGUI chest;
    private static boolean openInventory = false;
    private static Inventory inventory;
    private static boolean openChest = false;
    private static boolean openCrafting = false;
    private static boolean GUIOpened = false;
    private Steve player;
    private HealthBar hpBar;
    private boolean keyPreviouslyDown = false;
    private int worldTime;    
    private boolean firstAct = true;
    SimpleTimer dayNightTimer = new SimpleTimer();
    Scanner s;
    FileWriter fWriter;
    PrintWriter pWriter;

    /**
     * Constructor for objects of class GameWorld.
     * Initializes the world, grid, player, inventory, and other components.
     */
    public GameWorld(TitleScreen titleScreen) {    
        // Create a new world with 1280x768 cells with a cell size of 64x64 pixels.
        super(1280, 768, 1, false);
        
        // Optionally fill the grid with initial values or objects
        loadWorld();
        
        checkSave();

        // Inventory initialization
        inventory = new Inventory(300, this);
        craftingSystem = new CraftingSystem(300, this);
        this.titleScreen = titleScreen;
        // Player and health bar initialization
        player = new Steve(4, 3, 3, true, 3, inventory);
        hpBar = new HealthBar(player);
        addObject(hpBar, 0, 0);
        addObject(player, 640, 384);
        dayNightTimer.mark();
    }

    /**
     * Act method that is called repeatedly to check for actions in the world.
     * Tracks inventory, GUI, and player health bar.
     */
    public void act() {
        
        // Determines what goes on top
        setPaintOrder(Label.class, Item.class, Empty.class, GUI.class, Shader.class, SuperSmoothMover.class);
        
        if(firstAct){
            loadInv();
            loadChest();
            firstAct = false;
        }
        pause();

        // Inventory toggle logic
        boolean keyCurrentlyDown = Greenfoot.isKeyDown("e");

        if (keyCurrentlyDown && !keyPreviouslyDown) {
            if (!openInventory && !GUIOpened) {
                openInventory = true;
                inventory.addInventory();
                addObject(inventory, getWidth() / 2, getHeight() / 2);
                GUIOpened = true;
            } else if (openInventory && GUIOpened && !openChest) {
                GUIOpened = false;
                openInventory = false;
                inventory.removeInventory();
                removeObject(inventory);

            } 
            for(Label l : getObjects(Label.class)){
                removeObject(l);
            }
        }
        keyPreviouslyDown = keyCurrentlyDown;

        // Update health bar position
        hpBar.setLocation(player.getX(), player.getY() - 90);

        checkSave();
        checkReset();
        checkTime();
        checkPause();
        
        if(totalMobs() < 20){
            //attemptSpawn();
        }        
    }

    /**
     * Updates the time of the day every 20 seconds
     */
    private void checkTime(){
        if(dayNightTimer.millisElapsed() < 20000){
            worldTime = 1;
        }
        else if(dayNightTimer.millisElapsed() < 40000){
            worldTime = 2;
        }
        else if(dayNightTimer.millisElapsed() < 60000){
            worldTime = 3;
        }
        else if(dayNightTimer.millisElapsed() < 80000){
            worldTime = 4;
        }
        else if(dayNightTimer.millisElapsed() < 100000){
            worldTime = 3;
        }
        else if(dayNightTimer.millisElapsed() < 120000){
            worldTime = 2;
            dayNightTimer.mark();
        }

    }

    /**
     * Returns the world time for blocks to use and set their brightness
     * 
     * @return the time of the world
     */
    public int getTime(){
        return worldTime;
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
     * Setter for openCrafting.
     * 
     * @param open New value for openCrafting.
     */
    public static void setOpenCrafting(boolean open) {
        openCrafting = true;
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

    public void pause() {
        // Capture current grid and actors
        Block[][] currentGrid = getGrid();
        ArrayList<Actor> currentActors = getActors();
        Greenfoot.setWorld(new PauseScreen(titleScreen, this, currentActors));   
    }
    
    public void checkPause(){
        if(Greenfoot.isKeyDown("escape")){
            pause();
        }
    }

    /**
     * Initializes the grid with air blocks.
     */
    private void initializeGrid() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 36; j++) {
                updateBlock(i, j, new Air());
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
        int gridX = (x / 64) + 40;
        int gridY = (y / 64) + 12;
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
        int worldX = (gridX - 40) * 64 + 32; // center of the cell
        int worldY = (gridY - 12) * 64 + 32; // center of the cell
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
        ArrayList<Block> removingBlock = (ArrayList<Block>) getObjectsAt((gridX - 40)* 64 + 32, (gridY - 12) * 64 + 32, Block.class);
        for (Block block : removingBlock) {
            removeObject(block);
        }
        grid[gridX][gridY] = newBlock;
        addObject(grid[gridX][gridY], ((gridX - 40) * 64 + 32), ((gridY - 12) * 64 + 32));
    }
    
    private void saveInv(){
        try{
            fWriter = new FileWriter("saves/inv.txt");
            pWriter = new PrintWriter(fWriter);
            for(Item i : GUI.getItemList()){
                pWriter.println(i.toString());
            }

            pWriter.close();
            fWriter.close();
        }
        catch(IOException e){
        
        }
    }
    
    private void loadInv(){
        try{
            s = new Scanner(new File("saves/inv.txt"));
            ArrayList<String> tempString = new ArrayList<>();
            while(s.hasNext()){
                tempString.add(s.nextLine());
            }
            for(String s : tempString){
                String[] a = s.split(" ");
                String file = a[0];
                int lengthWidth = Integer.valueOf(a[1]);
                boolean draggable = Boolean.valueOf(a[3]);
                int X = Integer.valueOf(a[4]);
                int Y = Integer.valueOf(a[5]);
                String type = a[6];
                boolean placeable = Boolean.valueOf(a[7]);
                Item temp = new Item(file, lengthWidth, lengthWidth, this, draggable, X, Y, type, placeable);
 
                GUI.getItemList().add(temp);
            }
            s.close();
        }
        catch(FileNotFoundException e){

        }
    }

    /**
     * Updates the entire world with the correct blocks
     */
    private void refreshWorld(){
        for(int i = 0; i < 100; i++){
            for(int j = 0; j < 36; j++){
                updateBlock(i, j, grid[i][j]);
            }
        }
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
            object.setLocation((int) ((object.getX() + xShift + Math.signum(object.getX()) * 0.5)), (int) ((object.getY() + yShift + Math.signum(object.getY()) * 0.5)));
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
            player.setLocation((int) (player.getX() - xShift + Math.signum(player.getX()) * 0.5), (int) ((player.getY() - yShift + Math.signum(player.getY()) * 0.5)));
        }
    }

    /**
     * Loads the initial island by placing associated blocks.
     * Called when the world is generated.
     */
    private void prepareWorld() {
        updateBlock(42, 18, new Chest(this));
        updateBlock(42, 17, new Chest(this));
        updateBlock(44, 18, new Chest(this));
        updateBlock(45, 18, new CraftingTable(this));
        for (int i = 42; i < 58; i++) {
            updateBlock(i, 20, new Dirt());
        }
        for (int i = 42; i < 58; i++) {
            updateBlock(i, 19, new Grass());
        }
        for (int i = 43; i < 57; i++) {
            updateBlock(i, 21, new CobbleStone());
        }
        for (int i = 45; i < 55; i++) {
            updateBlock(i, 22, new CobbleStone());
        }
        for (int i = 50; i < 55; i++) {
            updateBlock(i, 15, new Leaf());
        }
        for (int i = 50; i < 55; i++) {
            updateBlock(i, 16, new Leaf());
        }
        for (int i = 51; i < 54; i++) {
            updateBlock(i, 14, new Leaf());
        }
        for (int j = 16; j < 19; j++) {
            updateBlock(52, j, new Log());
        }
        for(int i = 0; i < 100; i++){
            updateBlock(i, 35, new Void());
        }
    }

    private void loadWorld(){
        try{
            s = new Scanner(new File("saves/world_info.txt"));
            while(s.hasNext()){
                for(int i = 0; i < 100; i++){
                    for(int j = 0; j < 36; j++){
                        grid[i][j] = toBlock(s.nextLine());
                    }
                }
            }
            refreshWorld();
        }
        catch(FileNotFoundException e){
            prepareWorld();
        } catch(NullPointerException e){
            prepareWorld();
        }

        
    }

    private void checkReset(){
        if(Greenfoot.isKeyDown("l")){
            initializeGrid();
            prepareWorld();
        }
    }

    private void saveWorld(){
        try{
            FileWriter f = new FileWriter("saves/world_info.txt");
            PrintWriter p = new PrintWriter(f);
            for(int i = 0; i < 100; i++){
                for(int j = 0; j < 36; j++){
                    if(grid[i][j] == null){
                        p.println("air");
                    } else{
                        p.println(grid[i][j].getName());
                    }
                    
                }
            }
            p.close();
            f.close();
        }
        catch(IOException e){

        }
    }
    
    private void saveChest(){
        try{
            int x = 0;
            for(int i = 0; i < 100; i++){
                for(int j = 0; j < 36; j++){
                    if(grid[i][j] instanceof Chest){
                        Chest tempBlock = (Chest) grid[i][j];
                        fWriter = new FileWriter("saves" + File.separator + "chest" + x + ".txt");
                        pWriter = new PrintWriter(fWriter);
                        
                        for(Item item : tempBlock.getChestGUI().getContents()){
                            pWriter.println(item.toString());
                        }
                        x++;
                        fWriter.close();
                        pWriter.close();
                    }
                }
            }

            
            
            
        }
        catch(IOException e){

        }
    }
    
    private void loadChest(){
        try{
            int x = 0;
            for(int i = 0; i < 100; i++){
                for(int j = 0; j < 36; j++){
                    if(grid[i][j] instanceof Chest){
                        Chest tempBlock = (Chest) grid[i][j];
                        s = new Scanner(new File("saves" + File.separator + "chest" + x + ".txt"));
                        ArrayList<String> tempString = new ArrayList<>();
                        while(s.hasNext()){
                            tempString.add(s.nextLine());
                        }
                        for(String s : tempString){
                            String[] a = s.split(" ");
                            String file = a[0];
                            int lengthWidth = Integer.valueOf(a[1]);
                            boolean draggable = Boolean.valueOf(a[3]);
                            int X = Integer.valueOf(a[4]);
                            int Y = Integer.valueOf(a[5]);
                            String type = a[6];
                            boolean placeable = Boolean.valueOf(a[7]);
                            Item temp = new Item(file, lengthWidth, lengthWidth, this, draggable, X, Y, type, placeable);

                            tempBlock.getChestGUI().addItem(temp);
                        }
                        s.close();
                        x++;
                    }
                }
            }
            
        }
        catch(IOException e){
            //System.out.println("Error: " + e);
        }
    }

    private void checkSave(){
        if(Greenfoot.isKeyDown("k")){
            saveWorld();
            spoofInventory();
            saveInv();
            saveChest();
        }
    }

    private int totalMobs(){
        ArrayList<Mob> mobList = (ArrayList<Mob>) getObjects(Mob.class);
        return mobList.size();
    }

    private Block toBlock(String name){
        Block block = new Air();

        if(name.equals("air")){
            block = new Air();
        }
        else if(name.equals("chest")){
            block = new Chest(this);
        }
        else if(name.equals("cobblestone")){
            block = new CobbleStone();
        }
        else if(name.equals("craftingtable")){
            block = new CraftingTable(this);
        }
        else if(name.equals("dirt")){
            block = new Dirt();
        }
        else if(name.equals("grass")){
            block = new Grass();
        }
        else if(name.equals("leaf")){
            block = new Leaf();
        }
        else if(name.equals("log")){
            block = new Log();
        }
        else if(name.equals("sapling")){
            block = new Sapling();
        }

        return block;
    }

    private void attemptSpawn(){
        Random random = new Random();        
        for(int i = 1; i < 99; i++){
            for(int j = 1; j < 35; j++){
                Block block1 = grid[i][j];
                Block block2 = grid[i][j + 1];
                Block block3 = grid[i][j - 1];
                Block block4 = grid[i - 1][j];
                Block block5 = grid[i + 1][j];
                if(!(block2.getName().equals("air"))){
                    if((block1.getName().equals("air") && block3.getName().equals("air")) && (block4.getName().equals("air") && block5.getName().equals("air"))){
                        if(block1.getBrightness() <= 2){
                            int choice = random.nextInt(20000);
                            if(choice == 1){
                                addObject(new Sheep(), (((i - 40) * 64) + 32 - Player.getTotalXOffset()), (((j - 12) * 64) + 32 - Player.getTotalYOffset()));
                            }
                            else if(choice == 2){
                                addObject(new Cow(), (((i - 40) * 64) - 32), (((j - 12) * 64) - 32));
                            }
                        }
                        if(getTime() > 2){
                            int choice = random.nextInt(30000);
                            if(choice == 1){
                                addObject(new Zombie(), (((i - 40) * 64) + 32 - Player.getTotalXOffset()), (((j - 12) * 64) + 32 - Player.getTotalYOffset()));
                            }
                            else if(choice == 2){
                                addObject(new Creeper(), (((i - 40) * 64) + 32 - Player.getTotalXOffset()), (((j - 12) * 64) + 32 - Player.getTotalYOffset()));
                            }
                            else if(choice == 3){
                                addObject(new Spider(), (((i - 40) * 64) + 32 - Player.getTotalXOffset()), (((j - 12) * 64) + 32 - Player.getTotalYOffset()));
                            }                           
                        }
                    }
                }
            }
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

    public Block[][] getGrid() {
        return grid;
    }

    public ArrayList<Actor> getActors() {
        return new ArrayList<>(getObjects(Actor.class));
    }
}
