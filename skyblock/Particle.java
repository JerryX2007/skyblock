import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ParticleEffect here.
 * modifies the particle effect in minecraft
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Particle extends SuperSmoothMover
{
    private Color color;
    private GreenfootImage img;
    private double xVel, yVel;
    private int duration;
    public Particle(Color color, double xVel, double yVel){
        this.color = color;
        duration = 30;
        this.xVel = xVel;
        this.yVel = yVel;
        img = new GreenfootImage(4,4);
        img.setColor(color);
        img.fill();
        img.setTransparency(Greenfoot.getRandomNumber(100) + 75);
        setImage(img);
    }
    /**
     * Act - do whatever the ParticleEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        if(isAtEdge()||duration < 0){
            getWorld().removeObject(this);
        }
        else{
            setLocation(getX() + xVel, getY() - yVel);
            int rate = 90;//decay rate
            if(xVel > 0){
                xVel -= xVel/rate;
            }
            else if(xVel < 0){
                xVel += xVel/rate;
            }
            int trans = img.getTransparency();
            img.setTransparency((int)(trans-trans/rate));
            setImage(img);
            
            yVel -= 0.5;//change in velocity applied
            
        }
        duration--;
    }
}
