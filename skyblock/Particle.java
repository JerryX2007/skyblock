import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Represents a particle effect in the game.
 * 
 * This class represents a particle effect in the game. It extends SuperSmoothMover
 * to inherit basic properties and behaviors of an actor.
 * 
 * The particle effect is typically used for visual effects such as explosions or
 * dust clouds.
 * 
 * @author Nick Chen
 * @version 1.0.0
 */
public class Particle extends SuperSmoothMover
{
    private Color color; // The color of the particle
    private GreenfootImage img; // The image of the particle
    private double xVel, yVel; // The velocity of the particle in the x and y directions
    private int duration; // The duration for which the particle exists
    
    /**
     * Constructor for Particle class.
     * 
     * @param color The color of the particle.
     * @param xVel The velocity of the particle in the x-direction.
     * @param yVel The velocity of the particle in the y-direction.
     */
    public Particle(Color color, double xVel, double yVel){
        duration = 30; // Set the initial duration
        this.xVel = xVel; // Set the initial x-velocity
        this.yVel = yVel; // Set the initial y-velocity
        img = new GreenfootImage(4,4); // Create a new GreenfootImage for the particle
        if(color != null){
            this.color = color; // Set the color of the particle
            img.setColor(color); // Set the color of the image
            img.fill(); // Fill the image with the specified color
            img.setTransparency(Greenfoot.getRandomNumber(100) + 75); // Set the transparency of the image
        }
        setImage(img); // Set the image of the particle
    }
    
    /**
     * Act - do whatever the ParticleEffect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Remove the particle if it goes out of bounds or if its duration expires
        if(isAtEdge() || duration < 0){
            getWorld().removeObject(this);
        }
        else{
            // Update the position of the particle based on its velocity
            setLocation(getX() + xVel, getY() - yVel);
            
            int rate = 90; // Set the decay rate
            // Decay the x-velocity
            if(xVel > 0){
                xVel -= xVel/rate;
            }
            else if(xVel < 0){
                xVel += xVel/rate;
            }
            
            // Decay the transparency of the image
            int trans = img.getTransparency();
            img.setTransparency((int)(trans-trans/rate));
            setImage(img);
            
            yVel -= 0.5; // Apply a change in velocity
        }
        duration--; // Decrease the duration
    }
}
