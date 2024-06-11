import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    private static GreenfootImage fullHeart = new GreenfootImage("heart.png");
    private static GreenfootImage halfHeart = new GreenfootImage("halfHeart.png");
    private static GreenfootImage emptyHeart = new GreenfootImage("emptyHeart.png");
    private static final int maxHp = 20;
    private static final int heartSize = 18;//needs to be multiple of 9
    
    private int hp;
    private GreenfootImage image;
    /**
    private Player player;
    public HealthBar(Player player){
        this.player = player;
    }
    */
    public HealthBar(int hp){
        fullHeart.scale(heartSize,heartSize);
        halfHeart.scale(heartSize,heartSize);
        emptyHeart.scale(heartSize,heartSize);
        this.hp = hp;
        image = new GreenfootImage(heartSize*10,heartSize);
        for (int i = 0; i < 10; i++) {
            int xPosition = i * 18;
            if(hp > 1){
                image.drawImage(fullHeart, xPosition, 0);
            }
            else if(hp == 1){
                image.drawImage(halfHeart, xPosition, 0);
            }
            else{
                image.drawImage(emptyHeart, xPosition, 0);
            }
            hp -= 2;
        }

        // Set the big image as the image of this actor
        setImage(image);
    }
    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
    }
}
