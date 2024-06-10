import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CraftingSystem here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * Help from https://www.youtube.com/watch?v=LmQ6U3YkHHk
 */
public class CraftingSystem extends Actor
{
    private final int GRID_SIZE = 3;
    private Item[][] itemArray;
    /**
     * Act - do whatever the CraftingSystem wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public CraftingSystem() {
        itemArray = new Item[GRID_SIZE][GRID_SIZE];
    }
    
    private boolean isEmpty(int x, int y) {
        return itemArray[y][x] == null;
    }
    
    private Item getItem(int x, int y) {
        return itemArray[y][x];
    }
    
    private void setItem(Item item, int x, int y) {
        itemArray[y][x] = item;
    }
    
    private void increaseItemAmount(int x, int y) {
        itemArray[y][x].sizeOfNumItems++;
    }
    
    private void decreaseItemAmount(int x, int y) {
        itemArray[y][x].sizeOfNumItems--;
    }
    
    //Overload the methods
    private void increaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].sizeOfNumItems += increment;
    }
    
    private void decreaseItemAmount(int x, int y, int increment) {
        itemArray[y][x].sizeOfNumItems -= increment;
    }
    
    private boolean tryAddItem(Item item, int x, int y) {
        if(isEmpty(x, y)) {
            setItem(item, x, y);
            return true;
        }
        else {
            if (getItem(x, y).equals(item)) {
                increaseItemAmount(x, y);
                return true;
            }
            else {
                return false;
            }
        }
    }
}
