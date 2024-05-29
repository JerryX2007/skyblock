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
   
    
    
    private static int blockSize = 32;//how much pixels is a block
    protected Color color;//pls enter in "Color.RED"/"Color.LIGHT_GRAY" form
    protected double hardness;//how long will it take to break
    protected double breakTime;//acts it will take for the block to be broken
    protected double subBreakTime;//breaking progress
    protected boolean isWood;// i am a wood and an axe will break me faster
    protected boolean isStone;// i am a stone and an picaxe will break me faster
    protected boolean isDirt;// i am a dirt and a shovel will break me faster
    protected boolean isSelected;//the mouse have hovered over this block
    public Block(Color color, double hardness){
        this.color = color;
        this.hardness = hardness;
        isWood = false; isStone = false; isDirt = false;
        breakTime = 60*hardness;
        subBreakTime = breakTime;
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
        if(!isSelected){
            stopBreaking();
        }
        //block is broken
        if(subBreakTime < 0){
            drop();
            getWorld().removeObject(this);
        }
    }
}
