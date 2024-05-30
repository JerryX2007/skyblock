import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Block here.
 * - blocks are all squares
 * - blocks stops entities from falling through them
 * - blocks can be broken
 * - blocks drop when they are broken
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class  Block extends Actor
{
   
    private static MouseInfo mouse;
    private BreakingEffect be;
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
    public Block(Color color, double hardness){
        this.color = color;
        this.hardness = hardness;
        isWood = false; isStone = false; isDirt = false;
        breakTime = 60*hardness;
        subBreakTime = breakTime;
        be = new BreakingEffect(this);
        isSelected = false; isHoldingMouse = false;
    }
    /**
     * method to break to block only works if the block is selected (isSelected = true)
     * 
     * @param tooltype      0 for fist, 1 for picaxe, 2 for axe, 3 for shovel
     * @param efficiency    the effeciency bonus of this tool in percent% 
     */
    public void breakMe(int toolType, double efficiency){
        if(isSelected){
            boolean toolsAreMatching = (toolType == 1 && isStone)||(toolType == 2 && isWood)||(toolType == 3 && isDirt);
            if(toolsAreMatching){
                subBreakTime -= 1 *(1 + efficiency/100);
            }
            else{
                subBreakTime--;
            }
            //System.out.println("breaking");
        }
    }
    public void stopBreaking(){
        subBreakTime = breakTime;
    }
    public double getBreakTime(){
        return breakTime;
    }
    public double getSubBreakTime(){
        return subBreakTime;
    }
    /**
     * every block needs to drop something after being broken
     */
    protected abstract void drop();
    /**
     * Act - do whatever the Block wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        /**
        if (Greenfoot.mouseMoved(this)) {
            isSelected = true;
            //System.out.println("hovered");
        }
        else{
            isSelected = false;
        }
        */
        MouseInfo mouse = Greenfoot.getMouseInfo();
        
        if (mouse != null) {
            // Extract the x and y coordinates of the mouse
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            
            isSelected = isIntersectingWithCoordinate(mouseX, mouseY);
            
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
            isSelected = true;
            breakMe(0,0);
        }
        else{
            stopBreaking();
            isSelected = false;
        }
        //add the breaking effect 
        getWorld().addObject(be, getX(),getY());
        //block is broken
        if(subBreakTime < 0){
            drop();
            getWorld().removeObject(be);
            getWorld().removeObject(this);
        }
        //System.out.println(isSelected);
        //System.out.println((int)subBreakTime);
    }
     /**
     * Check if the actor is intersecting with a given coordinate.
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return true if the actor intersects with the coordinate, false otherwise.
     */
    public boolean isIntersectingWithCoordinate(int x, int y) {
        int actorX = getX();
        int actorY = getY();
        GreenfootImage image = getImage();
        int halfWidth = image.getWidth() / 2;
        int halfHeight = image.getHeight() / 2;

        // Check if the coordinate is within the bounding box of the actor
        return (x >= actorX - halfWidth && x <= actorX + halfWidth &&
                y >= actorY - halfHeight && y <= actorY + halfHeight);
    }
}

