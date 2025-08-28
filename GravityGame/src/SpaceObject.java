import java.awt.Graphics;
import java.util.ArrayList;
/**
 * This abstract class creates a framework for a general object in the game
 * It has methods that will be used by its child classes to simulate semi realistic gravitational attaction
 * @author Andrew Starrett
 */
public abstract class SpaceObject {
    //Varibales
    private double gravityForce;
    protected static double gravity;
    protected static GamePanel gamePanel;
    protected static ArrayList<SpaceObject> spaceObjects;

    //Constructor
    public SpaceObject (double gravity, GamePanel p, ArrayList<SpaceObject> spaceObjects) {
        SpaceObject.spaceObjects = spaceObjects;
        SpaceObject.gravity = gravity;
        gamePanel = p;
    }

    //Geters
    public ArrayList<SpaceObject> getObjects() {
        return spaceObjects;
    }
    public double getGravity() {
        return gravity;
    }
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    //Abstract methods
    public abstract double getXPos();
    public abstract double getYPos(); 
    public abstract double getXVelocity();
    public abstract double getYVelocity();
    public abstract double getMass();
    public abstract double getSize();
    public abstract boolean checkDead();
    public abstract void updateXVel(double xVel);
    public abstract void updateYVel(double yVel); 
    public abstract void paint(Graphics g);
    public abstract void animate();

    /**
     * Checks if objects are coliding based on thier position
     * @param other SpaceObject that the SpaceObject will check for colision with
     * @return true if the object colides with other and false if not
     */
    public boolean checkColision(SpaceObject other) {
        if (!(other instanceof Projectile) && (this.getDistanceTo(other) < (other.getSize() / 2.0) + (this.getSize() / 2.0))) {
            return true;
        }
        else if ((other instanceof Projectile) && (this.getDistanceTo(other) < (other.getSize() / 2.0) + (this.getSize() / 2.0))) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Finds the distance between two objects using their coordinates and the distance formula
     * @param other SpaceObject that the SpaceObject will find its distance to
     * @return the distance to other
     */
    public double getDistanceTo (SpaceObject other) {
        return Math.sqrt(Math.pow((other.getXPos() + other.getSize() / 2) - (this.getXPos() + this.getSize() / 2), 2.0) + Math.pow((other.getYPos() + other.getSize() / 2) - (this.getYPos() + this.getSize() / 2), 2.0));
        
    }

    /**
     * Finds the angle between two objects using their coordinates and arctangent
     * @param other SpaceObject that the SpaceObject will find its angle between
     * @return the angle to other in radians
     */
    public double getAngle(SpaceObject other) {
        return Math.atan(Math.abs(other.getYPos() - this.getYPos()) / Math.abs(other.getXPos() - this.getXPos()));
    }

    /**
     * Applies a gravity velocity vector to the object its called on
     * @param attractor the object that the veloctiy vector is found from, can be either a Planet or a Asteroid 
     */
    public void applyGravity(SpaceObject attractor) {
        //Block for it the attractor is a Planet
        if (attractor instanceof Planet) {
            //Gravity force is the scaler of the vector that we will apply
            //the equation for gravity force is similar to the Universal Gravitation formula but the gravitational constant is replaced with my own because it would be way to weak if otherwise
            //distance is also to the power of 2.1 not 2 as it is in the Universal Gravitation formula to make the planet a little harder to fall into
            gravityForce = this.getGravity() * (this.getMass() * attractor.getMass()) / Math.pow(this.getDistanceTo(attractor),2.1);
            //The next six if statements find which quadrant we are in to figure out which direction to apply the x and y componet of the gravity vector in
            //Each of the four blocks of code break the gravity vector into x and y components and adjust the velocity of the SpaceObject acordingly
            if (this.getXPos() > attractor.getXPos()) {
                if (this.getYPos() > attractor.getYPos()) {
                    updateYVel(-1 * gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(-1 * gravityForce * Math.cos(getAngle(attractor)));
                }
                else if (this.getYPos() <= attractor.getYPos()) {
                    updateYVel(gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(-1 * gravityForce * Math.cos(getAngle(attractor)));
                }
            }
            else if (this.getXPos() <= attractor.getXPos()) {
                if (this.getYPos() > attractor.getYPos()) {
                    updateYVel((-1 * gravityForce * Math.sin(getAngle(attractor))));
                    updateXVel((gravityForce * Math.cos(getAngle(attractor))));
                }
                else if (this.getYPos() <= attractor.getYPos()) {
                    updateYVel(gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(gravityForce * Math.cos(getAngle(attractor)));
                }
            }
        }
        //Block for it the attractor is a Planet
        else if (attractor instanceof Asteroid) {
            //Gravity force is the scaler of the vector that we will apply
            //the equation for gravity force is similar to the Universal Gravitation formula but the gravitational constant is replaced with my own because it would be way to weak if otherwise
            gravityForce = this.getGravity() * (this.getMass() * attractor.getMass()) / Math.pow(this.getDistanceTo(attractor),2);
            //The next six if statements find which quadrant we are in to figure out which direction to apply the x and y componet of the gravity vector in
            //Each of the four blocks of code break the gravity vector into x and y components and adjust the velocity of the SpaceObject acordingly
            if (this.getXPos() > attractor.getXPos()) {
                if (this.getYPos() > attractor.getYPos()) {
                    updateYVel(-1 * gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(-1 * gravityForce * Math.cos(getAngle(attractor)));
                }
                else if (this.getYPos() <= attractor.getYPos()) {
                    updateYVel(gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(-1 * gravityForce * Math.cos(getAngle(attractor)));
                }
            }
            else if (this.getXPos() <= attractor.getXPos()) {
                if (this.getYPos() > attractor.getYPos()) {
                    updateYVel((-1 * gravityForce * Math.sin(getAngle(attractor))));
                    updateXVel((gravityForce * Math.cos(getAngle(attractor))));
                }
                else if (this.getYPos() <= attractor.getYPos()) {
                    updateYVel(gravityForce * Math.sin(getAngle(attractor)));
                    updateXVel(gravityForce * Math.cos(getAngle(attractor)));
                }
            }
        }
    }    
}
