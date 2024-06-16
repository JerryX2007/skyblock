import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Shader here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shader extends Actor{
    GreenfootImage brightness1 = new GreenfootImage("shader.png");    
    GreenfootImage brightness2 = new GreenfootImage("shader.png");
    GreenfootImage brightness3 = new GreenfootImage("shader.png");
    GreenfootImage brightness4 = new GreenfootImage("shader.png");
      
    public Shader(int brightness){
        switch(brightness){
            case 1:
                setImage(brightness1);
                this.getImage().setTransparency(0);
                break;
            case 2:
                setImage(brightness2);
                this.getImage().setTransparency(50);
                break;
            case 3:
                setImage(brightness3);
                this.getImage().setTransparency(100);
                break;
            case 4:
                setImage(brightness4);
                this.getImage().setTransparency(150);
                break;
            default:
                break;
        }
    }
    
    public void act(){
        
    }
}
