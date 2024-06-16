import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Abstract class for defining blocks in the game.
 * Blocks are used to create the environment, and can be broken by the player.
 * Different types of blocks have different properties such as hardness and drop items.
 * 
 * @author Nick Chen
 * Edited by: Evan Xi
 * @version 1.0.0
 */
public abstract class  Block extends Actor{

    private static MouseInfo mouse;
    protected BreakingEffect be;
    private static int blockSize = 32;//how much pixels is a block
    protected Color color;//pls enter in "Color.RED"/"Color.LIGHT_GRAY" form
    protected double hardness;//how long will it take to break
    protected double breakTime;//acts it will take for the block to be broken
    protected double subBreakTime;//breaking progress
    protected boolean isWood;// i am a wood and an axe will break me faster
    protected boolean isStone;// i am a stone and an picaxe will break me faster
    protected boolean isDirt;// i am a dirt and a shovel will break me faster
    protected boolean isSelected;//the mouse have hovered over this block
    protected boolean isHoldingMouse;
    protected int brightness = 1;
    protected int itemDrop; // The item drop it will spawn when mined
    protected Color black = new Color(0, 0, 0);
    protected Player player;
    protected String name;

    /**
     * Constructor for Block class.
     * 
     * @param color     The color of the block
     * @param hardness  How long it takes to break the block
     * @param name      The name of the block
     */
    public Block(Color color, double hardness, String name){
        this.color = color;
        this.hardness = hardness;
        isWood = false; isStone = false; isDirt = false;
        breakTime = 60*hardness;
        subBreakTime = breakTime;
        isSelected = false; isHoldingMouse = false;
        this.name = name;
    }

    /**
     * method to break to block only works if the block is selected (isSelected = true)
     * 
     * @param tooltype      0 for fist, 1 for picaxe, 2 for axe, 3 for shovel
     * @param efficiency    the effeciency bonus of this tool in percent% 
     */
    public void breakMe(Player playerf, int toolType, double efficiency){
        if(playerf != null) {
            if(isSelected && (playerf.isBlockVisible(this, 40) || playerf.isBlockVisible(this, 0) || playerf.isBlockVisible(this, -40)) && playerf.isBlockWithinRange(this)) {
                boolean toolsAreMatching = (toolType == 1 && isStone)||(toolType == 2 && isWood)||(toolType == 3 && isDirt);
                if(toolsAreMatching){
                    subBreakTime -= 1 *(1 + efficiency/100);
                }
                else{
                    subBreakTime--;
                }
                if(Greenfoot.getRandomNumber(3) ==0){
                    //particle effect
                    this.particleEffect(this.getX(),this.getY() - 25, 1, this.color);
                }
            }
            else {
                stopBreaking();
            }
        }
    }

    /**
     * Add the itemDrop object to the current world
     * 
     * @param itemDrop The itemDrop Object to put in the current world
     */
    protected void drop(int itemDrop){
        getWorld().addObject(new ItemDrop(itemDrop), this.getX(), this.getY());
    }

    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act(){
        if (getWorld() instanceof GameWorld) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            GameWorld world = (GameWorld) getWorld();

            updateBrightness();
            
            if (mouse != null) {
                // Extract the x and y coordinates of the mouse
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();

                //borrowed mouseover code from Mr. Cohen
                isSelected = isIntersectingWithCoordinate(this, mouseX, mouseY);

                if (Greenfoot.mousePressed(this)||Greenfoot.mousePressed(be)) {
                    // Mouse button is pressed
                    isHoldingMouse = true;
                }
                if (Greenfoot.mouseClicked(this)||Greenfoot.mouseClicked(be)) {
                    isHoldingMouse = false; // Reset the flag
                }   
            }
            if(!isSelected){
                stopBreaking();
                isHoldingMouse = false;
            }
            //attempt to break the block when mouse is pressed on me
            if(isHoldingMouse){
                if(be == null){
                    be = new BreakingEffect(this);
                }
                isSelected = true;
                breakMe(player, 0,0);
            }

            else{
                stopBreaking();
                isSelected = false;
            }
            //add the breaking effect 
            if(be != null){
                world.addObject(be, getX(),getY());
            }
            //block is broken
            if(subBreakTime < 0){
                drop(itemDrop);
                if(be != null){
                    world.removeObject(be);
                }
                
                GameWorld.grid[getGridNumX()][getGridNumY()] = new Air();
                removeTouching(Shader.class);
                world.removeObject(this);
            }
        }
    }

    /**
     * Adds a transparent block on top depending on the brightness of the block
     * Uses a scale from 1 - 4, with 4 being brightest
     */
    private void updateBrightness(){
        GameWorld world = (GameWorld) getWorld();
        
        brightness = world.getTime();
        
        removeTouching(Shader.class);
        world.addObject(new Shader(brightness), this.getX(), this.getY());
    }
    
    /**
     * Check if the actor is intersecting with a given coordinate.
     * 
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return true if the actor intersects with the coordinate, false otherwise.
     */
    public boolean isIntersectingWithCoordinate(Actor a,int x, int y) {
        int actorX = a.getX();
        int actorY = a.getY();
        GreenfootImage image = getImage();
        int halfWidth = image.getWidth() / 2;
        int halfHeight = image.getHeight() / 2;

        // Check if the coordinate is within the bounding box of the actor
        return (x >= actorX - halfWidth && x <= actorX + halfWidth &&
            y >= actorY - halfHeight && y <= actorY + halfHeight);
    }

    /**
     * add a border to an image, rectangle only
     * 
     * @param image         the orginial image
     * @param borderColor   the color of the border
     * @return              return the new image
     */
    public static GreenfootImage addBorder(GreenfootImage image, Color borderColor) {
        int width = image.getWidth();
        int height = image.getHeight();

        GreenfootImage newImage = new GreenfootImage(width, height);
        // Draw the original image onto the new image
        newImage.drawImage(image, 0, 0);

        newImage.setColor(borderColor);
        // Draw the border around the new image
        newImage.drawRect(0, 0, width - 1, height - 1);

        return newImage;
    }

    /**
     * particle effect, break a block to see what it looks like idk :/
     * 
     * @param x                 x-coord to spawn particle
     * @param y                 y-coord to spawn particle
     * @param numOfParticles    number of particle
     * @param color             color of the particle
     */
    public void particleEffect(int x, int y, int numOfParticles, Color color){
        for(int i = 0; i < numOfParticles; i++){
            double angle = Math.PI*Greenfoot.getRandomNumber(180)/180;
            int speed = 4;
            double xVel = (Math.cos(angle)*speed);
            double yVel = (Math.sin(angle)*speed);
            getWorld().addObject(new Particle(color,xVel,yVel), x, y);
        }
    }

     /**
     * Set the player object interacting with this block.
     * 
     * @param player The player object
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Stop the breaking process of the block.
     */
    public void stopBreaking(){
        subBreakTime = breakTime;
    }

    /**
     * Get the total break time of the block.
     * 
     * @return The total break time
     */
    public double getBreakTime(){
        return breakTime;
    }

    /**
     * Get the current break time progress of the block.
     * 
     * @return The current break time progress
     */
    public double getSubBreakTime(){
        return subBreakTime;
    }

    /**
     * Get the grid number along the x-axis where the block is positioned.
     * 
     * @return The grid number along the x-axis
     */
    private int getGridNumX(){
        return ((this.getX() - 32) / 64) + 40;
    }
    
    /**
     * Get the grid number along the y-axis where the block is positioned.
     * 
     * @return The grid number along the y-axis
     */
    private int getGridNumY(){
        return ((this.getY() - 32) / 64) + 12;
    }

    /**
     * Get the name of the block.
     * 
     * @return The name of the block
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get whether or not the block is a type of dirt
     * 
     * @return if the block is a type of dirt
     */
    public boolean isDirt() {
        return isDirt;
    }
    
    /**
     * Get whether or not the block is a type of stone
     * 
     * @return if the block is a type of stone
     */
    public boolean isStone() {
        return isStone;
    }
    
    /**
     * Get whether or not the block is a type of wood
     * 
     * @return if the block is a type of wood
     */
    public boolean isWood() {
        return isWood;
    }
    
    /**
     * Get the brightness of the block
     * 
     * @return the brightness of the block
     */
    public int getBrightness(){
        return brightness;
    }
}

