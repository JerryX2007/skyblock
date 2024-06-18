import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Represents the main character, Steve, in the game.
 * 
 * This class represents the main character, Steve, in the game. Steve has various body parts
 * and can perform actions such as punching and moving.
 * 
 * @author Nick Chen
 * @version 1.0.0
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
    private boolean isPunching, isHoldingItem;
    private ArrayList<Item> heldItems;
    private Item itemInHand;
    protected int X;
    protected int Y;
    protected ArrayList<BodyPart> bodyparts = new ArrayList<>();

    /**
     * Constructor for the Steve class.
     * 
     * @param moveSpeed The movement speed of Steve.
     * @param jumpHeight The jump height of Steve.
     * @param reach The reach of Steve.
     * @param canDrop A boolean indicating whether Steve can drop items.
     * @param pickUpRange The pick-up range of Steve.
     * @param inventory The inventory of Steve.
     */
    public Steve(int moveSpeed, int jumpHeight, int reach, boolean canDrop, int pickUpRange, Inventory inventory) {
        super(moveSpeed, jumpHeight, reach, canDrop, pickUpRange, true, inventory);
        //hitBox
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
        heldItems = inventory.getHeldItems();
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
        //update the inventory
        heldItems = inventory.getHeldItems();
        holdItem();
        actNum++;
    }

    /**
     * Check if Steve is moving and update the animation accordingly.
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
            leftArm.setImage(img3);
            rightArm.setRotation(0);
            rightArm.setImage(img);
        }
    }

    /**
     * Update the layering of body parts based on Steve's direction.
     */
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
     * Rotate Steve's head towards the mouse pointer.
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

    /**
     * Perform the swinging animation when Steve is moving.
     */
    public void swing(){
        // function y = sinx where actNum is x and angle radians is y
        int time = actNum/10;//swing speed
        double radians = Math.sin(time);//swing angle
        double oppositeRadians = Math.sin(time+ Math.PI);
        if(isPunching && direction){
            leftLeg.setRotation(45*radians);
            rightLeg.setRotation(45*oppositeRadians);
            leftArm.setRotation(40*oppositeRadians);
        }
        else if(isPunching && !direction){
            rightArm.setRotation(40*radians);
            leftLeg.setRotation(45*radians);
            rightLeg.setRotation(45*oppositeRadians);
        }
        else{
            rightArm.setRotation(40*radians);
            leftLeg.setRotation(45*radians);
            rightLeg.setRotation(45*oppositeRadians);
            leftArm.setRotation(40*oppositeRadians);
        }
    }

    /**
     * Perform the punching animation when Steve punches.
     */
    public void punching(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        // Check if the mouse information is available
        if (mouse != null && mouse.getButton() != 0) {
            if(!isPunching){
                //punch when mouse clicks
                isPunching = true;
            }
        }
        if(isPunching){
            //play the correct animatin depending on the state of steve
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

    /**
     * makes steve holds an item
     */
    public void holdItem(){
        String key = Greenfoot.getKey();
        int numberPressed;
        //check if player can hold the item
        if (key != null && key.matches("[1-9]")) {
            numberPressed =  (int)Integer.parseInt(key);
            if(heldItems.size() > 0 && numberPressed < heldItems.size() - 1){
                //if the number key pressed on a item that exist, set it to that item
                //itemInHand = heldItems.get(numberPressed);
                itemInHand = new Bones(32, 32, getWorld(), 0,0);
            }
            else{
                //else make an empty item
                //itemInHand = new Empty(32, 32, getWorld(), 0,0);
                itemInHand = new Bones(32, 32, getWorld(), 0,0);
            }
        }
        //check if player's hand is empty
        if(itemInHand != null && !(itemInHand instanceof Empty)){
            isHoldingItem = true;
        }
        else{
            isHoldingItem = false;
        }
        //let the player hold the item if it exists
        if(isHoldingItem){
            if(direction){
                itemInHand.setLocation(getX() + 32, getY()-16);

            }
            else{
                itemInHand.setLocation(getX() - 32, getY()-16);
            }
        }
    }

}
