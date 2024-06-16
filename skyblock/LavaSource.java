import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * =====PLS CALL remove() INSTEAD OF getWorld().removeObject(lava)!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
        if (below == null) {
            getWorld().addObject(new LavaStream(this, 0, 0), getX(), getY() + 64);
        } 
        if (left == null) {
            if(left instanceof WaterSource){
                //make a cobble stone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() + 64, getY());
            }
            else{
                getWorld().addObject(new LavaStream(this,1, -1), getX() - 64, getY());
            }
        }
        if (right == null) {
            if(right instanceof WaterSource){
                //make a cobble stone if in contact with water
                getWorld().addObject(new CobbleStone(), getX() - 64, getY());
            }
            else{
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
}
