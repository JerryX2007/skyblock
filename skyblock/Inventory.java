import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class Inventory here.
 * 
 * @author Benny Wang
 * @version (a version number or a date)
 */
public class Inventory extends GUI
{
    private int xAdjust = 0;
    private int yAdjust = 0;
    private static World world;
    private static ArrayList<Items> itemsList = new ArrayList<>();;
    private static Items[][] slots = new Items[9][3];
    private boolean prevState = false;
    private boolean prevState1 = false;
    private int tempX;
    private int tempY;
    
    public Inventory (int scale, World world){
        super("inventory.png", scale, world);
        this.world = world;
        clearInv(); 
    }
    
    public void addInventory(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                Empty temp = new Empty(16, 16, world, 424 + xAdjust, world.getHeight()/2 + 27 + yAdjust);
                world.addObject(temp, 424 + xAdjust, world.getHeight()/2 + 27 + yAdjust);
                slots[j][i] = temp;
                xAdjust += 54;
            }
            xAdjust = 0;
            yAdjust += 54;
        }
        for(Items i: itemsList){
            world.addObject(i, i.getXPos(), i.getYPos());
        }
        xAdjust = 0;
        yAdjust = 0;
    }
    
    public void removeInventory(){
        for(Items i: itemsList){
            world.removeObject(i);
            i.removeNum();
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                world.removeObject(slots[j][i]);
            }
        }
    }
    
    private void clearInv(){
        itemsList.clear();
    }
    
    /**
     * Act - do whatever the Inventory wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        boolean currentDown = Greenfoot.isKeyDown("p");
        if(currentDown && !prevState){
            Items temp = new Items("block/wood.png", world, 424, world.getHeight()/2 + 27, 32, 32, "wood");
           
            itemsList.add(temp);
            for (int i = 2; i >= 0; i--) {
                for (int j = 0; j < 9; j++) {
                    if(slots[j][i].getType().equals("air") || slots[j][i].getType().equals(temp.getType())){
                        tempX = slots[j][i].getX();
                        tempY = slots[j][i].getY();
                        break;
                    }
                }
            }
            world.addObject(temp, tempX, tempY);
        }
        prevState = currentDown;
        
        boolean currentDown1 = Greenfoot.isKeyDown("o");
        if(currentDown1 && !prevState1){
            Items temp = new Items("block/cobblestone.png", world, 424, world.getHeight()/2 + 27, 32, 32, "cobblestone");
            
            itemsList.add(temp);
            for (int i = 2; i >= 0; i--) {
                for (int j = 0; j < 9; j++) {
                    if(slots[j][i].getType().equals("air") || slots[j][i].getType().equals(temp.getType())){
                        tempX = slots[j][i].getX();
                        tempY = slots[j][i].getY();
                        break;
                    }
                }
            }
            world.addObject(temp, tempX, tempY);
        }
        prevState1 = currentDown1;
    }
    
    public static void setSlot(int x, int y, String itemName){
        int tempX = slots[x][y].getX();
        int tempY = slots[x][y].getY();
        slots[x][y] = new Items("block/air.png", 16, 16, world, false, tempX, tempY, itemName);
    }
    
    public static void addItem(String item){
        Items temp = new Items("block/" + item + ".png", world, 424, world.getHeight()/2 + 27, 32, 32, item);
        itemsList.add(temp);
    }

    public static boolean hasSpaceFor(String item){
        
        return true;
    }
}
