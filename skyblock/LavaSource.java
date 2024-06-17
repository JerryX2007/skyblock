import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The source of all lava
 * Creates lavastreams that flow and spread
 * 
 * @author Nick
 * @version (a version number or a date)
 */
public class LavaSource extends Block {
    public static GreenfootImage lava = new GreenfootImage("liquid/l0.png");
    private int actNum;
    protected boolean isRemoved;

    public LavaSource() {
        super(null, 99999, "LavaSource");

        if (lava.getHeight() != 64) {
            lava.scale(64, 64);
        }
        setImage(lava);
        actNum = 0;
        isRemoved = false;
    }

    public void act() {
        actNum++;
        //set the flowrate
        if (actNum % 90 == 0) {
            flow();
        }
        // Check if another block is placed on the same location
        if (isBlocked()) {
            remove();
        }
        //remove the lava if isRemoved is true
        if(isRemoved){
            getWorld().removeObject(this);
        }

    }

    /**
     * this method makes the lava flow down and sideways
     */
    protected void flow() {
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);
        //check if blocks beside are filled, if not, flow into them by adding a new lava stream
        if(below instanceof WaterSource || below instanceof WaterStream){
            //make a cobble stone if in contact with water
            getWorld().addObject(new CobbleStone(), getX(), getY() + 64);
        }
        else if(below == null){
            //else if the space is empty add a stream
            getWorld().addObject(new LavaStream(this, 0, 0), getX(), getY() + 64);
        }
        else if (!(below instanceof LavaSource || below instanceof LavaStream)) {
            if(left instanceof WaterSource || left instanceof WaterStream){
                //make a cobble stone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() - 64, getY());
            }
            else if (left == null){
                //else if the space is empty add a stream
                getWorld().addObject(new LavaStream(this, 1, -1), getX() - 64, getY());
            }
            if(right instanceof WaterSource || right instanceof WaterStream){
                //make a cobble stone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() + 64, getY());
            }
            else if(right == null){
                //else if the space is empty add a stream
                getWorld().addObject(new LavaStream(this, 1, 1), getX() + 64, getY());
            }
        }
    }

    //removing methods
    public void remove() {
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }
    
    /**
     * @author chatGPT
     * This method checks if there is a block in the 32x32 area around the water 
     * 
     * @return true if there is a block in the area, false otherwise
     */
    protected boolean isBlocked() {
        int[][] offsets = {{-16, -16}, {16, -16}, {-16, 16}, {16, 16}};
        for (int[] offset : offsets) {
            Block blockAtOffset = (Block) getOneObjectAtOffset(offset[0], offset[1], Block.class);
            if (blockAtOffset != null && blockAtOffset != this) {
                return true;
            }
        }
        return false;
    }
}
