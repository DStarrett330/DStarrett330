import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/**
 * Represents an object in the game that the player can control
 * manages the colision, movement, player input, firing, and painting of the ship
 * @author Andrew Starrett
 */
public class Ship extends SpaceObject  {
    //Variables
    private double xPos;
    private double yPos;
    private double xVelocity;
    private double yVelocity;
    private int mass;
    private int size;
    private int fireRate;
    private int fireRateTimer;
    private boolean fireing = false;
    private boolean isDead;

     //Constructor
    public Ship(double gravity, GamePanel p, ArrayList<SpaceObject> spaceObjects) {
        super(gravity, p, spaceObjects);
        xVelocity = 0;
        yVelocity = 0;
        size = 10;
        xPos = 15;
        yPos = 15;
        mass = 80;
        fireRate = 50;
        fireRateTimer = fireRate;
        isDead = false;
    }

    //Getters and setters
    public boolean isFireing() {
        return fireing;
    }
    public void setFireing(boolean fireing) {
        this.fireing = fireing;
    }
    @Override
    public double getXPos() {
        return xPos;
    }
    @Override
    public double getYPos() {
        return yPos;
    }
    @Override
    public double getXVelocity() {
        return xVelocity;
    }
    @Override
    public double getYVelocity() {
        return yVelocity;
    }
    @Override
    public double getMass() {
        return mass;
    }
    @Override
    public double getSize() {
        return size;
    }
    public boolean checkDead() {
        return isDead;
    }
    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }
    public void updateXVel(double xVel) {
        xVelocity += xVel;
    }
    public void updateYVel(double yVel) {
        yVelocity += yVel;
    }

    /**
     * Updates the position and status of the Ship based off of the state of the game
     * Inherited method from SpaceObject
     */
    @Override
    public void animate() {
        //Speedlimiter
        if (xVelocity > 20) {
            xVelocity *= (xVelocity - 20) * 0.5;
        }
        if (yVelocity > 20) {
            yVelocity *= (yVelocity - 20) * 0.5;
        }
        //Updates position once per frame
        xPos += xVelocity;
        yPos += yVelocity;
        
        for (SpaceObject s : spaceObjects) {
            //Applies gravity to the ship based on any planets in spaceObjects
            if (s instanceof Planet) {
                applyGravity((Planet) s);
            }
            if (!(s.equals(this))) {
                //Checks for colision with projectiles only if the projectile is old enough to avoid dying everytime you fire
                if (s instanceof Projectile && (((Projectile)s).getTime() > 50) && !isDead) {
                    isDead = this.checkColision(s);
                }
                //Check for colision with everything else
                else if (!(s instanceof Projectile) && !isDead) {
                    isDead = this.checkColision(s);
                }
            }
        }

        //Updates velocity based on user input
        if (gamePanel.getDKey()) {
            xVelocity += 0.25;
        }
        if (gamePanel.getAKey()) {
            xVelocity -= 0.25;
        }
        if (gamePanel.getWKey()) {
            yVelocity -= 0.25;
        }
        if (gamePanel.getSKey()) {
            yVelocity += 0.25;
        }
        //Incriments firerate timer once perframe, you can only fire once fireRateTimer is greater or equal to firerate
        fireRateTimer++;
        if (gamePanel.getFKey() && fireRateTimer >= fireRate) {
            fireing = true;
            //Sets fireRateTimer back to 0 to create a delay on how fast you can fire
            fireRateTimer = 0;
        }
        
        //Checks if the player has moved off of the screen and teleports them to the otehrside
        if (xPos <= 0 - size) {
            xPos = getGamePanel().getWidth() - (size );
        }
        if (yPos <= 0 - size) {
            yPos = getGamePanel().getHeight() - (size + 2);
        }
        if (xPos > getGamePanel().getWidth() - size) {
            xPos = 0 + size;
        }
        if (yPos > getGamePanel().getHeight() - size) {
            yPos = 0 + size;
        }
    }

    //Paints the Ship onto the GamePanel
    public void paint(Graphics g) {
        if (isDead) {
            return;
        }
        else {
            g.setColor(Color.WHITE);
            if (fireRateTimer >= 50) {
                g.fillOval((int)xPos, (int)yPos, (int)size, (int)size);
            }
            else {
                g.drawOval((int)xPos, (int)yPos, (int)size, (int)size);
            }
        }
    }

}
