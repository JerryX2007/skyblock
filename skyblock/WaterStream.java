import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(water)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * 
 * @author (your name) 
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
    private int streamNumber;
    private int streamLength;
    private boolean direction; // true is right, false is left
    private int imageType; // 0 is w0.png; 1-6 is streams flowing to right and -1 to -6 is streams flowing to left
    private int sidewaysCount;
    
    public WaterStream(WaterSource previous, int initialSidewaysCount) {
        previousWater = previous;
        sidewaysCount = initialSidewaysCount;
        if (previous != null) {
            resizeImages();
            updateImage();
            setState();
        }
        streamCount++;
        actNum = streamCount;
    }

    public void act() {
        actNum++;
        if (actNum % 45 == 0) {
            if (previousWater.isRemoved()) {
                remove();
            } else {
                flow();
            }
        }
        if(isRemoved){
            getWorld().removeObject(this);
        }
    }

    private void resizeImages() {
        if (normal.getHeight() != 64) {
            normal.scale(64, 64);
            for (int i = 0; i < left.length; i++) {
                left[i].scale(64, 64);
                right[i].scale(64, 64);
            }
        }
    }

    private void updateImage() {
        if (imageType < 0 && imageType > -7) {
            setImage(left[-1 * imageType - 1]);
        } else if (imageType > 0 && imageType < 7) {
            setImage(right[imageType - 1]);
        } else {
            setImage(normal);
        }
    }

    public void setState() {
        if (previousWater instanceof WaterStream) {
            direction = ((WaterStream) previousWater).getDirection();
            streamNumber = ((WaterStream) previousWater).getStreamNum();
            streamLength = ((WaterStream) previousWater).getStreamLength() + 1;
            sidewaysCount = ((WaterStream) previousWater).getSidewaysCount();
        } else {
            streamNumber = 0;
        }
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean d) {
        direction = d;
    }

    public int getStreamNum() {
        return streamNumber;
    }

    public void setStreamNum(int i) {
        streamNumber = i;
    }

    public int getStreamLength() {
        return streamLength;
    }

    public void setStreamLength(int i) {
        streamLength = i;
    }

    public int getSidewaysCount() {
        return sidewaysCount;
    }

    public void setSidewaysCount(int count) {
        sidewaysCount = count;
    }

    @Override
    protected void flow() {
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);

        if (below == null) {
            // Reset sidewaysCount when flowing downward
            getWorld().addObject(new WaterStream(this, 0), getX(), getY() + 64);
        } else if (!(below instanceof WaterSource || below instanceof WaterStream)) {
            if (sidewaysCount < 6) {
                if (left == null) {
                    getWorld().addObject(new WaterStream(this, sidewaysCount + 1), getX() - 64, getY());
                }
                if (right == null) {
                    getWorld().addObject(new WaterStream(this, sidewaysCount + 1), getX() + 64, getY());
                }
            }
        }
    }
}
