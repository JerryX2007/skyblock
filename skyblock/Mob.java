import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * All mobs are born with its own damage, speed, and health (passive mobs have 0 damage)
 * All mobs have a chance to drop something when defeated
 * 
 * Mobs are born with a key indentifying trait - hostile or not
 * If the mob is hostile, will attempt to attack players in its sight range
 * If the mob is passive and attacked, will attempt to flee from the player
 * If there is nothing to do, mobs will default to wandering aimlessly
 * 
 * Mobs will spawn based on lighting and will only do so on solid blocks
 * 
 * @author Evan Xi
 * @version (a version number or a date)
 */
public abstract class Mob extends SuperSmoothMover{
    protected boolean isHostile;
    protected int damage;
    protected double speed;
    protected int health;
    
    protected int minSpawnLight;
    protected int maxSpawnLight;

    protected boolean isFleeing;
    SimpleTimer fleeTimer;
    
    protected GreenfootImage defaultImg;
    protected GreenfootImage movingImg;
    protected GreenfootImage hurtImg;
    protected GreenfootImage deathImg;
    protected GreenfootImage attackImg;
    public Mob(boolean hostile, int dmg, double spd, int hp, int minLight, int maxLight){
        isHostile = hostile;
        damage = dmg;
        speed = spd;
        health = hp;
        minSpawnLight = minLight;
        maxSpawnLight = maxLight;
    }
    
    public void act(){
        if(fleeTimer.millisElapsed() > 2000){
            isFleeing = false;
        }
        
        if(isHostile){
            if(hasTarget()){
                hunt();
            }
            else{
                wander();
            }
        }
        
        if(!isHostile){
            if(isFleeing){
                flee();
            }
            else{
                wander();
            }
        }
        
        if(health <= 0){
            drop();
            getWorld().removeObject(this);
        }

    }
    
    /**
     * Applies to hostile mobs only
     * Constantly searches for a player within radius
     * If a player is found, begin working its way to it
     */
    protected void hunt(){
        
    }
    
    /**
     * The method to a target in its sight range
     * If a target is found, set 
     */
    protected boolean hasTarget(){
        return false;
    }
    
    /**
     * Applies to passive mobs only
     * If isFleeing = true, will run in the opposite direction of the player
     */
    protected void flee(){
        
    }
    
    /**
     * If there is nothing to do, wander aimlessly around
     */
    protected void wander(){
        
    }
    
    /**
     * All mobs will drop something upon death
     */
    protected abstract void drop();

    /**
     * Allows other entities to damage the mob
     * If the mob is passive, will flee from the player
     */
    public void damageMe(int damage){
        health -= damage;
        if(!isHostile){
            isFleeing = true;
            fleeTimer = new SimpleTimer();
            fleeTimer.mark();
        }
    }
    
    public boolean canSpawn(int light, int gridX, int gridY){
        GameWorld world = (GameWorld) getWorld();
        if((light >= minSpawnLight) && (light <= maxSpawnLight)){ // Checks for light level requirement
            if(world.getGridValue(gridX, gridY).equals(Air.class) && !world.getGridValue(gridX, gridY + 1).equals(Air.class)){ // Checks if floor is solid and space is vacant
                if(world.getGridValue(gridX, gridY - 1).equals(Air.class) && world.getGridValue(gridX, gridY - 2).equals(Air.class)){ // Checks if there is at least two blocks above it
                    return true;
                }
            }
        }
        return false;
    }
}
