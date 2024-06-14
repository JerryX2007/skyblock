import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HealthBar here.
 * health bar of player
 * @author Nick
 * @version (a version number or a date)
 */
public class HealthBar extends Actor
{
    private static GreenfootImage fullHeart = new GreenfootImage("heart.png");
    private static GreenfootImage halfHeart = new GreenfootImage("halfHeart.png");
    private static GreenfootImage emptyHeart = new GreenfootImage("emptyHeart.png");
    private static final int maxHp = 20;
    private static final int heartSize = 18;//needs to be multiple of 9
    
    private GreenfootImage image;
    private Steve player;
    private int actNum;
    public HealthBar(Steve player){
        fullHeart.scale(heartSize,heartSize);
        halfHeart.scale(heartSize,heartSize);
        emptyHeart.scale(heartSize,heartSize);
        image = new GreenfootImage(heartSize*10,heartSize);
        this.player = player;
        actNum = 0;
    }
    /**
     * Act - do whatever the HealthBar wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        if(actNum >= 20){
            updateHpBar();
            actNum = 0;
        }
        actNum++;
    }
    //updates hp bar
    private void updateHpBar(){
        
        image.clear();
        
        int hp ;
        if(player == null){
            hp = Greenfoot.getRandomNumber(21);
        }
        else{
            hp = player.getHp();
        }
        
        for (int i = 0; i < 10; i++) {
            int xPosition = i * heartSize;
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
}