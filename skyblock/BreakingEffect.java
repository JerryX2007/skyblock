import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BreakingEffect extends Actor
{
    private static GreenfootImage[] breakFrames = {new GreenfootImage("breaking/0.png"),new GreenfootImage("breaking/1.png"),new GreenfootImage("breaking/2.png"),new GreenfootImage("breaking/3.png"),
                                                   new GreenfootImage("breaking/4.png"),new GreenfootImage("breaking/5.png"),new GreenfootImage("breaking/6.png"),
                                                   new GreenfootImage("breaking/7.png"),new GreenfootImage("breaking/8.png"),new GreenfootImage("breaking/9.png"),};
    private Block block;
    private boolean disappear;
    public BreakingEffect(Block b){
        this.block = b;
        for(int i = 0; i < breakFrames.length; i++){
            breakFrames[i].setTransparency (200);
            breakFrames[i].scale(80, 80);
        }
        disappear = false;
    }
     /**
     * Act - do whatever the Effect wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
        if(block != null){
            int breakingPercent = (int)(100*block.getSubBreakTime()/block.getBreakTime());
            if(breakingPercent > 90){
                setImage(breakFrames[0]);
            }
            else if(breakingPercent <= 90 && breakingPercent >80){
                setImage(breakFrames[1]);
            }
            else if(breakingPercent <= 80 && breakingPercent >70){
                setImage(breakFrames[2]);
            }
            else if(breakingPercent <= 70 && breakingPercent >60){
                setImage(breakFrames[3]);
            }
            else if(breakingPercent <= 60 && breakingPercent >50){
                setImage(breakFrames[4]);
            }
            else if(breakingPercent <= 50 && breakingPercent >40){
                setImage(breakFrames[5]);
            }
            else if(breakingPercent <= 40 && breakingPercent >30){
                setImage(breakFrames[6]);
            }
            else if(breakingPercent <= 30 && breakingPercent >20){
                setImage(breakFrames[7]);
            }
            else if(breakingPercent <= 20 && breakingPercent >10){
                setImage(breakFrames[8]);
            }
            else if(breakingPercent <= 0){
                setImage(breakFrames[9]);
            }
            else{
                setImage(breakFrames[0]);
            }
        }
        else{
            getWorld().removeObject(this);
        }
    }
}
