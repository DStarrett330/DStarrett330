import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/**
 * Represents a planet in the game
 * The planet is stationary and acts mainly as an obsticle and a source of gravity
 * @author Andrew Starrett
 */
public class Planet extends SpaceObject {
    //Variables
    private double xPos;
    private double yPos;
    private int mass;
    private int size;
    private boolean isDead;
    
    //Constructor
    public Planet(double gravity, GamePanel p, ArrayList<SpaceObject> spaceObjects) {
        super(gravity, p, spaceObjects);
        size = 75;
        xPos = p.getWidth() / 2.0 - (size / 2);
        yPos = p.getHeight() / 2.0 - (size / 2);
        mass = 125;
        isDead = false;
    }

    //Getters
    @Override
    public double getXVelocity() {
        return 0;
    }
    @Override
    public double getYVelocity() {
        return 0;
    }
    @Override
    public double getSize() {
        return size;
    }
    @Override
    public void updateXVel(double xVel) {
    }
    @Override
    public void updateYVel(double yVel) {
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
    public double getMass() {
        return mass;
    }
    public boolean checkDead () {
        return isDead;
    }

    //Planets dont move so no implimentation of animate is need
    @Override
    public void animate() {
    }

    /**
     * Paints the planet onto the GamePanel
     */
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) xPos, (int) yPos, size, size);
    }

}
