import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Steve here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Steve extends Player
{
    private GreenfootImage hitBox,img, img1, img2,img3;
    private LeftHead leftHead;
    private RightHead rightHead;
    private LeftArm leftArm;
    private RightArm rightArm;
    private LeftBody leftBody;
    private RightBody rightBody;
    private LeftLeg leftLeg;
    private RightLeg rightLeg;
    private int actNum, counter;
    private boolean isPunching;
    public Steve(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange) {
        super(moveSpeed, jumpHeight, reach, canDrop, pickUpRange, true);
        hitBox = new GreenfootImage("steve/hitBox.png");
        hitBox.scale(40,128);
        setImage(hitBox);
        //add bodyparts
        leftHead = new LeftHead(this); leftArm = new LeftArm(this); leftBody = new LeftBody(this); leftLeg = new LeftLeg(this);
        rightHead = new RightHead(this); rightArm = new RightArm(this); rightBody = new RightBody(this); rightLeg = new RightLeg(this);
        int actNum = 0; isPunching = false; counter = 0;
        img1 = new GreenfootImage("steve/side_arm.png");
        img1.scale(16,96);
        img = new GreenfootImage("steve/arm_right.png");
        img.scale(16,96);
        img2 = new GreenfootImage("steve/flipped_side_arm.png");
        img2.scale(16,96);
        img3 = new GreenfootImage("steve/arm_left.png");
        img3.scale(16,96);
    }

    /**
     * Act - do whatever the Steve wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if(actNum == 0){
            getWorld().addObject(leftLeg,0,0);  getWorld().addObject(rightLeg,0,0);
            getWorld().addObject(leftBody,0,0); getWorld().addObject(rightBody,0,0);
            getWorld().addObject(leftArm,0,0);  getWorld().addObject(rightArm,0,0);
            getWorld().addObject(leftHead,0,0); getWorld().addObject(rightHead,0,0);
        }
        punching();
        rotateTowardsMouse();
        super.act();
        updateLayering();
        checkIfMoving();
        //rotateTowardsMouse();
        actNum++;
    }
    /**
     * if player is moving, play swinging animation, else, reset it to idle position
     */
    public void checkIfMoving(){
        if(isMoving){
            swing();
        }
        else if(isPunching && direction){
            leftLeg.setRotation(0);
            rightLeg.setRotation(0);
            leftArm.setRotation(0);
            //rightArm.setRotation(0);
        }
        else if(isPunching && !direction){
            leftLeg.setRotation(0);
            rightLeg.setRotation(0);
            //leftArm.setRotation(0);
            rightArm.setRotation(0);
        }
        else{
            leftLeg.setRotation(0);
            rightLeg.setRotation(0);
            leftArm.setRotation(0);
            rightArm.setRotation(0);
        }
    }
    public void updateLayering() {
        // Clear existing objects
        getWorld().removeObject(leftLeg);
        getWorld().removeObject(rightLeg);
        getWorld().removeObject(leftBody);
        getWorld().removeObject(rightBody);
        getWorld().removeObject(leftArm);
        getWorld().removeObject(rightArm);

        if (!direction) {
            // Facing Left
            getWorld().addObject(rightLeg, 0, 0);
            getWorld().addObject(rightBody, 0, 0);
            getWorld().addObject(rightArm, 0, 0);

            getWorld().addObject(leftLeg, 0, 0);
            getWorld().addObject(leftBody, 0, 0);
            getWorld().addObject(leftArm, 0, 0);

        } else {
            // Facing Right
            getWorld().addObject(leftLeg, 0, 0);
            getWorld().addObject(leftBody, 0, 0);
            getWorld().addObject(leftArm, 0, 0);

            getWorld().addObject(rightLeg, 0, 0);
            getWorld().addObject(rightBody, 0, 0);
            getWorld().addObject(rightArm, 0, 0);

        }
    }

    /**
     * swing arms and legs
     */
    public void swing(){
        int time = actNum/10;
        double radians = Math.sin(time);

        if(!isPunching){
            rightArm.setRotation(40*radians);
        }
        leftLeg.setRotation(45*radians);

        double oppositeRadians = Math.sin(time+ Math.PI);

        rightLeg.setRotation(45*oppositeRadians);
        leftArm.setRotation(40*oppositeRadians);
    }

    /**
     * rotate head towards mouse
     * implemented from chatgpt
     */
    public void rotateTowardsMouse() {
        // Get the current mouse information
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Check if the mouse information is available
        if (mouse != null) {
            // Get the coordinates of the mouse
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();

            if(mouseX <= this.getX()){
                direction = false;
                leftHead.reflectImage();
                rightHead.reflectImage();
            }
            else{
                direction = true;
                leftHead.reflectBack();
                rightHead.reflectBack();
            }
            // Get the coordinates of the head
            int headX = leftHead.getX();
            int headY = leftHead.getY();

            // Set the rotation of the head
            leftHead.turnTowards(mouseX, mouseY);
            rightHead.turnTowards(mouseX, mouseY);
        }
    }

    public void punching(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Check if the mouse information is available
        if (mouse != null && mouse.getButton() != 0) {
            if(!isPunching){
                isPunching = true;
            }
        }
        if(isPunching){

            if(!direction){
                leftArm.setImage(img2);
                if(counter >= 0 && counter < 5){
                    leftArm.setRotation(45);
                }
                if(counter >= 5 && counter < 10){
                    leftArm.setRotation(60);
                }
                if(counter >= 10 && counter < 15){
                    leftArm.setRotation(45);
                }
                if(counter >= 15){
                    leftArm.setRotation(0);
                    leftArm.setImage(img3);
                    counter = 0;
                    isPunching = false;
                }
            }
            else{
                rightArm.setImage(img1);
                if(counter >= 0 && counter < 5){
                    rightArm.setRotation(360-35);
                }
                if(counter >= 5 && counter < 10){
                    rightArm.setRotation(360-50);
                }
                if(counter >= 10 && counter < 15){
                    rightArm.setRotation(360-35);
                }
                if(counter >= 15){
                    rightArm.setRotation(0);
                    rightArm.setImage(img);
                    counter = 0;
                    isPunching = false;
                }
            }
            counter++;
        }
    }

}
