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
        }
        setImage(water);
        actNum = 0;
        isRemoved = false;
    }

    public void act() {
        actNum++;
        //set the flowrate
        if (actNum % 40 == 0) {
            flow();
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
        if (below == null) {
            getWorld().addObject(new WaterStream(this, 0, 0), getX(), getY() + 64);
        } 
        if (left == null) {
            if(left instanceof LavaSource){
                //make a cobble stone if in contact with lava
                getWorld().addObject(new CobbleStone(), getX() + 64, getY());
            }
            else{
                getWorld().addObject(new WaterStream(this,1, -1), getX() - 64, getY());
            }
        }
        if (right == null) {
            if(right instanceof LavaSource){
                //make a cobble stone if in contact with lava
                getWorld().addObject(new CobbleStone(), getX() - 64, getY());
            }
            else{
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
}
