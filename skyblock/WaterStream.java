import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sets pictures of water according to its relative location to previous blocks and the source block
 * 
 * @author Nick 
 * @version (a version number or a date)
 */
public class WaterStream extends WaterSource {
    public static int streamCount = 0;
    public static GreenfootImage[] left = {
            new GreenfootImage("liquid/Lw1.png"), new GreenfootImage("liquid/Lw2.png"),
            new GreenfootImage("liquid/Lw3.png"), new GreenfootImage("liquid/Lw4.png"),
            new GreenfootImage("liquid/Lw5.png"), new GreenfootImage("liquid/Lw6.png")
            };
    public static GreenfootImage[] right = {
            new GreenfootImage("liquid/Rw1.png"), new GreenfootImage("liquid/Rw2.png"),
            new GreenfootImage("liquid/Rw3.png"), new GreenfootImage("liquid/Rw4.png"),
            new GreenfootImage("liquid/Rw5.png"), new GreenfootImage("liquid/Rw6.png")
            };
    public static GreenfootImage normal = new GreenfootImage("liquid/w0.png");

    private WaterSource previousWater;
    private int actNum;

    private int direction; // 1 is right, 0 is down, -1 is left
    private int sidewaysCount;
    /**
     * makes a block of water stream
     * 
     * @param previous                  the previous block of water
     * @param initialSidewaysCount      how long this stream of water has been flowing sideways
     * @param direction                 the direction of this waterstream
     */
    public WaterStream(WaterSource previous, int initialSidewaysCount, int direction) {
        previousWater = previous;//preivious block of water
        sidewaysCount = initialSidewaysCount;// howlong the water has flowed sideways
        this.direction = direction;
        if (previous != null) {
            resizeImages();
            updateImage();
        }
        streamCount++;
        if(streamCount > 9){
            streamCount = 0;
        }
        actNum = streamCount;
    }

    public void act() {
        actNum++;
        //set flowrate
        if (actNum % 40 == 0) {
            //remove me if the previous water is removed
            if (previousWater.isRemoved()) {
                remove();
            } else {
                flow();
            }

        }
        // Check if another block is placed on the same location
        if (isBlocked()) {
            remove();
        }
        //remove me
        if(isRemoved){
            getWorld().removeObject(this);
        }
        
    }

    /**
     * this method resize the static images
     */
    private void resizeImages() {
        if (normal.getHeight() != 64) {
            normal.scale(64, 64);
            normal.setTransparency(220);
            for (int i = 0; i < left.length; i++) {
                left[i].scale(64, 64);
                left[i].setTransparency(220);
                right[i].scale(64, 64);
                right[i].setTransparency(220);
            }
        }
    }

    /**
     * this method updates the image of this block
     */
    private void updateImage() {
        if (direction == -1) {
            setImage(left[sidewaysCount-1]);
        } else if (direction == 1) {
            setImage(right[sidewaysCount-1]);
        } else {
            setImage(normal);
        }
    }
    //getter setting methods
    public int getDirection() {
        return direction;
    }

    public void setDirection(int d) {
        direction = d;
    }

    public int getSidewaysCount() {
        return sidewaysCount;
    }

    public void setSidewaysCount(int count) {
        sidewaysCount = count;
    }

    @Override
    protected void flow() {
        //checkblocks adjacent to this block
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);
        Block above = (Block) getOneObjectAtOffset(0, -64, Block.class);
        //if below left or right is empty then flow into them by adding a new water stream
        if(below instanceof LavaSource || below instanceof LavaStream){
            //make a cobble stone if in contact with lava
            getWorld().addObject(new CobbleStone(), getX(), getY() + 64);
        }
        else if(below == null){
            //else if the space is empty add a stream
            getWorld().addObject(new WaterStream(this, 0, 0), getX(), getY() + 64);
        }
        else if (!(below instanceof WaterSource || below instanceof WaterStream)) {
            //max range the water can flow sideways, currently 5
            if (sidewaysCount < 5) {
                if(left instanceof LavaSource || left instanceof LavaStream){
                    //make a cobble stone if in contact with lava
                    getWorld().addObject(new CobbleStone(), getX() - 64, getY());
                }
                else if (left == null){
                    //else if the space is empty add a stream
                    getWorld().addObject(new WaterStream(this, sidewaysCount + 1, -1), getX() - 64, getY());
                }
                if(right instanceof LavaSource || right instanceof LavaStream){
                    //make a cobble stone if in contact with lava
                    getWorld().addObject(new CobbleStone(), getX() + 64, getY());
                }
                else if(right == null){
                    //else if the space is empty add a stream
                    getWorld().addObject(new WaterStream(this, sidewaysCount + 1, 1), getX() + 64, getY());
                }
            }
        }
        //if the block above me is water, adjust my picture to a full block
        if(above instanceof WaterSource || above instanceof WaterStream){
            setImage(normal);
        }
    }
}
