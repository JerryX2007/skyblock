import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Most items will drop something upon being destroyed
 * Most drops will take form of a smaller version of the original that hovers, waiting to be picked up by the user
 * Gravity applies to item drops
 * 
 * @author Evan Xi 
 * @version (a version number or a date)
 */
public class ItemDrop extends SuperSmoothMover{
    private int type;
    private String name;
    private GreenfootImage img;

    private double vSpeed = 0;
    private double acceleration = 0.1;
    private double angle = 0;

    public ItemDrop(int type){
        this.type = type;
        setType();
    }

    /**
     * Simply checks the status of the item drop
     * If it isn't on the ground, fall until it is
     * While on the ground, constantly hover up and down
     */
    public void act(){
        if(!onGround()){
            fall();
        }
        else{
            vSpeed = 0;
            hover();
        }
    }

    /**
     * Using a sinusoidal function, make the item drop hover right above ground
     */
    private void hover(){
        setLocation(getPreciseX(), getPreciseY() + Math.sin(angle) * 0.5);
        angle += Math.PI/60;
    }

    /**
     * Sets the image and drop based on the input received
     */
    private void setType(){
        switch(type){
            case 1:
                img = new GreenfootImage("block/cobblestone.png");
                name = "cobblestone";
                break;
            case 2:
                img = new GreenfootImage("block/dirt.png");
                name = "dirt";
                break;
            case 3:
                img = new GreenfootImage("block/grass_block.png");
                name = "grass_block";
                break;
            case 4: 
                img = new GreenfootImage("block/leaves.png");
                name = "leaves";
                break;
            case 5:
                img = new GreenfootImage("block/wood.png");
                name = "wood";
                break;
            case 6:
                img = new GreenfootImage("block/crafting_table.png");
                name = "crafting_table";
                break;
            case 7:
                img = new GreenfootImage("block/chest.png");
                name = "chest";
                break;
            case 8:
                img = new GreenfootImage("block/sapling.png");
                name = "sapling";
                break;
            case 9:
                img = new GreenfootImage("block/chest.png");
                name = "chest";
                break;
            case 10:
                img = new GreenfootImage("block/wooden_plank.png");
                name = "plank";
                break;
            case 11:
                img = new GreenfootImage("block/coal_ore.jpeg");
                name = "coal";
                break;
            case 12:
                img = new GreenfootImage("block/iron_ore.png");
                name = "iron";
                break;
            case 13:
                img = new GreenfootImage("items/Diamond.png");
                name = "diamond";
                break;
            case 14:
                img = new GreenfootImage("items/torch.png");
                name = "torch";
                break;
            case 15:
                img = new GreenfootImage("block/wool.jpg");
                name = "wool";
                break;
            case 16:
                img = new GreenfootImage("items/leather.png");
                name = "leather";
                break;
            case 17:
                img = new GreenfootImage("items/gunpowder.png");
                name = "gunpowder";
                break;
            case 18:
                img = new GreenfootImage("items/String.png");
                name = "string";
                break;
            case 19:
                img = new GreenfootImage("items/Coal.png");
                name = "coal";
                break;
            case 20:
                img = new GreenfootImage("items/Iron.png");
                name = "iron";
                break;
        }
        img.scale(15, 15); // Scales the item drop to be a mini version of the original 64x64 block
        setImage(img);
    }

    /**
     * Checks if the block is on the ground
     * 
     * @return the status of the block
     */
    private boolean onGround() {
        Block under = (Block) getOneObjectAtOffset(0, getImage().getHeight()/2 + 20, Block.class);
        if(under != null) {
            if(under instanceof Air) {
                return false;
            }
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Accelerate downwards to fall
     */
    private void fall() {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }

    /**
     * If it isn't on the ground, start falling
     */    
    private void checkFalling() {
        if(onGround()) {
            vSpeed = 0;
        }
        else {
            fall();
        }
    }

    /**
     * Gets the integer representation of the item drop
     * 
     * @return the integer representation
     */
    public int getType(){
        return type;
    }
    
    /**
     * Gets the string representation of the item drop
     * 
     * @return the string representation
     */
    public String getName(){
        return name;
    }
}
