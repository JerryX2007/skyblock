import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ItemDrop here.
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

    public void act(){
        if(!onGround()){
            fall();
        }
        else{
            vSpeed = 0;
            hover();
        }
    }

    private void hover(){
        setLocation(getPreciseX(), getPreciseY() + Math.sin(angle) * 0.5);
        angle += Math.PI/60;
    }

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
        }
        img.scale(15, 15);
        setImage(img);
    }

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

    private void fall() {
        setLocation(getX(), getY() + vSpeed);
        vSpeed = vSpeed + acceleration;
    }

    private void checkFalling() {
        if(onGround()) {
            vSpeed = 0;
        }
        else {
            fall();
        }
    }

    public int getType(){
        return type;
    }

    public String getName(){
        return name;
    }
}
