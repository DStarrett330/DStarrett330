import java.awt.Color;
import java.awt.Graphics;
/**
 * Represents a projectile object in the game that is fired from the player's Ship object
 * They are effected by planitary gravity as well as gravity from asteroids
 * They will colide with planets as well as kill Ships or Asteroids if they colide
 * @author Andrew Starrett
 */
public class Projectile extends SpaceObject {
    //Variables
    private double xPos;
    private double yPos;
    private double xVelocity;
    private double yVelocity;
    private double mass;
    private double size;
    private int timer;
    private boolean isDead;

    //Contructor
    public Projectile(SpaceObject other) {
        super(other.getGravity(), other.getGamePanel(), other.getObjects());
        xPos = other.getXPos();
        yPos = other.getYPos();
        mass = 80;
        yVelocity = other.getYVelocity() * 1.7;
        xVelocity = other.getXVelocity() * 1.7;
        timer = 0;
        size = 4;
        isDead = false;
    }

    //Getters
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
        return getYVelocity();
    }
    @Override
    public double getMass() {
        return mass;
    }
    @Override
    public double getSize() {
        return 0;
    }
    public int getTime() {
        return timer;
    }
    public boolean checkDead() {
        return isDead;
    }
    public void updateXVel(double xVel) {
        xVelocity += xVel;
    }
    public void updateYVel(double yVel) {
        yVelocity += yVel;
    }

    /**
     * Updates the position and status of the projectile based off of the state of the game
     * Inherited method from SpaceObject
     */
    public void animate() {
        //Timer increments every frame to keep track of how old the projetile is
        timer++;
        //Deletes the projectile if it is older than 12.5 seconds
        if (timer > 500) {
            isDead = true;
        }
        //Speed limiter
        if (xVelocity > 20) {
            xVelocity *= (xVelocity - 20) * 0.5;
        }
        if (yVelocity > 20) {
            yVelocity *= (yVelocity - 20) * 0.5;
        }
        //Upates the position of the object
        xPos += xVelocity;
        yPos += yVelocity;
        //Applies gravity and checks colision
        for (SpaceObject s : spaceObjects) {
            //Applies gravity to the projectile from all planets and asteroids in spaceObjects
            if (s instanceof Planet || s instanceof Asteroid) {
                applyGravity(s);
            }
            //Checks colision with planets
            if (!s.equals(this) && !(s instanceof Ship) && !(s instanceof Asteroid)) {
                if (!isDead) {
                    isDead = this.checkColision(s);
                }
            }
        }
        //Checks if the projectile has left the view of the game
        if (xPos <= 0 || yPos <= 0 || xPos > getGamePanel().getWidth() || yPos > getGamePanel().getHeight()){
            isDead = true;
        }
    }

    /**
     * Paints the projectile onto the GamePanel
     */
    public void paint(Graphics g) {
        if (!isDead) {
            g.setColor(Color.WHITE);
            g.fillOval((int)xPos, (int)yPos, (int)size, (int)size);
        }
    }


}
