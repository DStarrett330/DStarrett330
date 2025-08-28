import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
/**
 * Represents an object in the game that will slowly move towards the planet unless the player shoots and hits it
 * Choses a random angle to aproach from and will move in a straight line at a constant speed, it is uneffected by gravity
 * @author Andrew Starrett
 */
public class Asteroid extends SpaceObject {
    //Variables
    private double xPos;
    private double yPos;
    private int width;
    private int height;
    private int mass;
    private double yVelocity;
    private double xVelocity;
    private double angle;
    private boolean isDead;
    private Random rng = new Random();
    
    //Constructor
    public Asteroid (double gravity, GamePanel gamePanel, ArrayList<SpaceObject> spaceObjects) {
        super(gravity, gamePanel, spaceObjects);
        //This finds the distance asteroids will spawn away from the planet based on the size of the
        //GamePanel to ensure that the asteroids always spawn outside of the veiw of the plater
        double distance = Math.sqrt(Math.pow(gamePanel.getHeight() / 2.0, 2) + Math.pow(gamePanel.getWidth() / 2.0, 2)) + 80;
        angle = rng.nextInt(0,360);
        angle *= (Math.PI / 180.0);
        //Finds the velocity of the asteroid based on the angle
        xVelocity = -0.8 * Math.cos(angle);
        yVelocity = -0.8 * Math.sin(angle);
        //Places the asteroid at the start of its path to the planet
        xPos = (distance * Math.cos(angle)) + (gamePanel.getWidth() / 2.0);
        yPos = (distance * Math.sin(angle)) + (gamePanel.getHeight() / 2.0);
        width = rng.nextInt(20,41);
        height = rng.nextInt(20,41);
        mass = 10;
    }

    //Geters
    public double getXPos() {
        return xPos;
    }
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
        //Because asteroids are not always perfect circles for colision logic sake we return the size as whatever the bigest dimension of the object is
        if (width > height) {
            return width;
        }
        else {
            return height;
        }
    }
    @Override
    public boolean checkDead() {
        return isDead;
    }
    @Override
    public void updateXVel(double xVel) {}
    @Override
    public void updateYVel(double yVel) {}
    
    /**
     * Updates the position and status of the asteroid based off of the state of the game
     * Inherited method from SpaceObject
     */
    @Override
    public void animate() {
        //Updates position
        xPos += xVelocity;
        yPos += yVelocity;
        //Checks for colision
        for (SpaceObject s : spaceObjects) {
            //If an asteroid colides with a planet the score is decremented by 1
            if (s instanceof Planet) {
                if (this.checkColision(s)) {
                    isDead = true;
                    gamePanel.adjustScore(-1);
                }
            }
            //If an asteroid colides with a projectile the score is incremented by 1
            if (s instanceof Projectile) {
                if (this.checkColision(s)) {
                
                    isDead = true;
                    gamePanel.adjustScore(1);
                }
            }
            //If an asteroid colides with a ship the player has died and the game will end
            if (s instanceof Ship) {
                if (this.checkColision(s)) {
                    ((Ship)s).setDead(true);
                }
            }
        }
    }

    /**
     * Paints the asteroid onto the GamePanel
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);  
        g.fillOval((int)xPos, (int)yPos, 20, 20);
        g.fillOval((int)xPos, (int)yPos, width, height);
    }
}
