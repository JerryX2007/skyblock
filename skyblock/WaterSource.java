import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The source of all water
 * Creates waterstreams that flow and spread
 * 
 * @author Nick
 * @version (a version number or a date)
 */
public class WaterSource extends Block {
    public static GreenfootImage water = new GreenfootImage("liquid/w0.png");
    private int actNum;
    protected boolean isRemoved;

    public WaterSource() {
        super(null, 99999, "WaterSource");

        if (water.getHeight() != 64) {
            water.scale(64, 64);
            water.setTransparency(220);
        }
        setImage(water);
        actNum = 0;
        isRemoved = false;
    }

    /**
     * Physics for water movement
     */
    public void act() {
        actNum++;
        //set the flowrate
        if (actNum % 40 == 0) {
            flow();
        }
        // Check if another block is placed on the same location
        if (isBlocked()) {
            remove();
        }
        //remove the water if isRemoved is true
        if(isRemoved){
            getWorld().removeObject(this);
        }
    }

    /**
     * this method makes the water flow down and sideways
     */
    protected void flow() {
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);
        //check if blocks beside are filled, if not, flow into them by adding a new water stream
        if(below instanceof LavaSource || below instanceof LavaStream){
            //make a cobble stone if in contact with lava
            getWorld().addObject(new CobbleStone(), getX(), getY() + 64);
        }
        else if(below == null){
            //else if the space is empty add a stream
            getWorld().addObject(new WaterStream(this, 0, 0), getX(), getY() + 64);
        }
        else if (!(below instanceof WaterSource || below instanceof WaterStream)) {
            if(left instanceof LavaSource || left instanceof LavaStream){
                //make a cobble stone if in contact with lava
                getWorld().addObject(new CobbleStone(), getX() - 64, getY());
            }
            else if (left == null){
                //else if the space is empty add a stream
                getWorld().addObject(new WaterStream(this, 1, -1), getX() - 64, getY());
            }
            if(right instanceof LavaSource || right instanceof LavaStream){
                //make a cobble stone if in contact with lava
                getWorld().addObject(new CobbleStone(), getX() + 64, getY());
            }
            else if(right == null){
                //else if the space is empty add a stream
                getWorld().addObject(new WaterStream(this, 1, 1), getX() + 64, getY());
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
