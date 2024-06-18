import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Sets pictures of lava according to its relative location to previous blocks and the source block
 * 
 * @author Nick 
 * @version (a version number or a date)
 */
public class LavaStream extends LavaSource {
    public static int streamCount = 0;
    public static GreenfootImage[] left = {
        new GreenfootImage("liquid/Ll1.png"), new GreenfootImage("liquid/Ll2.png"),
        new GreenfootImage("liquid/Ll3.png"), new GreenfootImage("liquid/Ll4.png"),
    };
    public static GreenfootImage[] right = {
        new GreenfootImage("liquid/Rl1.png"), new GreenfootImage("liquid/Rl2.png"),
        new GreenfootImage("liquid/Rl3.png"), new GreenfootImage("liquid/Rl4.png"),
    };
    public static GreenfootImage normal = new GreenfootImage("liquid/l0.png");

    private LavaSource previousLava;
    private int actNum;

    private int direction; // 1 is right, 0 is down, -1 is left
    private int sidewaysCount;
    /**
     * makes a block of lava stream
     * 
     * @param previous                  the previous block of lava
     * @param initialSidewaysCount      how long this stream of lava has been flowing sideways
     * @param direction                 the direction of this lavastream
     */
    public LavaStream(LavaSource previous, int initialSidewaysCount, int direction) {
        previousLava = previous;//preivious block of lava
        sidewaysCount = initialSidewaysCount;// howlong the lava has flowed sideways
        this.direction = direction;
        if (previous != null) {
            resizeImages();
            updateImage();
        }
        streamCount++;
        actNum = streamCount;
    }

    public void act() {
        actNum++;
        //set flowrate
        if (actNum % 90 == 0) {
            //remove me if the previous lava is removed
            if (previousLava.isRemoved()) {
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
            for (int i = 0; i < left.length; i++) {
                left[i].scale(64, 64);
                right[i].scale(64, 64);
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
    // Check blocks adjacent to this block
    Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
    Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
    Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);
    Block above = (Block) getOneObjectAtOffset(0, -64, Block.class);

    // If below, left, or right is empty then flow into them by adding a new lava stream
    if (below instanceof WaterSource || below instanceof WaterStream) {
        // Make a cobblestone if in contact with water
        getWorld().addObject(new CobbleStone(), getX(), getY() + 64);
    } else if (below == null) {
        // Else if the space is empty, add a stream
        getWorld().addObject(new LavaStream(this, 0, 0), getX(), getY() + 64);
    } else if (!(below instanceof LavaSource || below instanceof LavaStream)) {
        // Max range the lava can flow sideways, currently 3
        if (sidewaysCount < 3) {
            if (left instanceof WaterSource || left instanceof WaterStream) {
                // Make a cobblestone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() - 64, getY());
            } else if (left == null) {
                // Else if the space is empty, add a stream
                getWorld().addObject(new LavaStream(this, sidewaysCount + 1, -1), getX() - 64, getY());
            }
            if (right instanceof WaterSource || right instanceof WaterStream) {
                // Make a cobblestone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() + 64, getY());
            } else if (right == null) {
                // Else if the space is empty, add a stream
                getWorld().addObject(new LavaStream(this, sidewaysCount + 1, 1), getX() + 64, getY());
            }
        }
    }

    // If the block above me is lava, adjust my picture to a full block
    if (above instanceof LavaSource || above instanceof LavaStream) {
        setImage(normal);
    }
}

}
