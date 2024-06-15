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
public class WaterSource extends Block {
    public static GreenfootImage water = new GreenfootImage("liquid/w0.png");
    private int actNum;
    protected boolean isRemoved;

    public WaterSource() {
        super(null, 99999, "WaterSource");
        if (water.getHeight() != 64) {
            water.scale(64, 64);
        }
        setImage(water);
        actNum = 0;
        isRemoved = false;
    }

    public void act() {
        actNum++;
        if (actNum % 45 == 0) {
            flow();
        }
        if(isRemoved){
            getWorld().removeObject(this);
        }
        
    }

    protected void flow() {
        Block left = (Block) getOneObjectAtOffset(-64, 0, Block.class);
        Block right = (Block) getOneObjectAtOffset(64, 0, Block.class);
        Block below = (Block) getOneObjectAtOffset(0, 64, Block.class);

        if (below == null) {
            getWorld().addObject(new WaterStream(this, 0), getX(), getY() + 64);
        } else if (!(below instanceof WaterSource || below instanceof WaterStream)) {
            if (left == null) {
                getWorld().addObject(new WaterStream(this, 1), getX() - 64, getY());
            }
            if (right == null) {
                getWorld().addObject(new WaterStream(this, 1), getX() + 64, getY());
            }
        }
    }

    
    public void remove() {
        isRemoved = true;
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}
