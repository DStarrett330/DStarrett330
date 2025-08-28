import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class GamePanel : Manages the panel that the game takes place in as well as all of the objects within it
 * @author Andrew Starrett
 */
public class GamePanel extends JPanel implements KeyListener {
    //Variables
    private static final int sleepTime = 25;    
    private int height;
    private int width;
    private static double gravity = 0.8; 
    private ArrayList<SpaceObject> spaceObjects;
    private int asteroidPeriod = 20;
    private int asteroidTimer = 0;
    private int score;
    private JLabel scoreLabel;
    enum Display {STARTMENU, GAMEPLAY1, RULES};
    Display display;
    private int currentButton;
    JLabel playButton = new JLabel();
    JLabel helpButton = new JLabel();
    JLabel quitButton = new JLabel();
    JLabel rulesText;
    JLabel rulesExitButton;
    JLabel titleCard;
    JLabel menuInstructions;
    Random rng = new Random();
    private boolean wKey = false;
    private boolean aKey = false;
    private boolean dKey = false;
    private boolean sKey = false;
    private boolean fKey = false;
    private boolean enterKey = false;
    private boolean gotInput = false;
    private boolean running = false;

    //Contructor
    public GamePanel(int height, int width){
        super();
        this.height = height - 37;
        this.width = width;
        this.setLayout(null);
        display = Display.STARTMENU;
        currentButton = 0;
        spaceObjects = new ArrayList<SpaceObject>();
    }
    
    //Getters
    public ArrayList<SpaceObject> getSpaceObjects() {
        return spaceObjects;
    }
    public boolean getWKey() {
        return wKey;
    }
    public boolean getAKey() {
        return aKey;
    }
    public boolean getDKey() {
        return dKey;
    }
    public boolean getSKey() {
        return sKey;
    }
    public boolean getFKey() {
        return fKey;
    }

    @Override
    public int getHeight() {
        return height;
    }
    @Override
    public int getWidth() {
        return width;
    }
    public KeyListener getListener() {
        return this;
    }

    /**
     * Loop that runs until the game is ended
     * One of three bodies of code will excecute depending on which menu the user is in
     * If the menu is being ran for the first time the !running blocks run to initialize variables make the menu ready to run
     * Each block calls its associated run method to manage the menu as it runs, each run method calls the paintComponent method which
     * displays the proper menu
     */
    public void runTheGame() {
        while (true) {
            //This is the main gameplay block
            if (display == Display.GAMEPLAY1) {
                if (!running) {
                    //Removes the start menu elements
                    this.remove(playButton);
                    this.remove(helpButton);
                    this.remove(quitButton);
                    this.remove(titleCard);
                    this.remove(menuInstructions);
                    playButton = null;
                    helpButton = null;
                    quitButton = null;
                    titleCard = null;
                    menuInstructions = null;

                    //Initalizes the game
                    spaceObjects.add(new Ship(gravity, this, spaceObjects));
                    spaceObjects.add(new Planet(gravity, this, spaceObjects));
                    //Score is displayed in the bottom left corner of the screen and is initalized her
                    score = 0;
                    scoreLabel = new JLabel("Score: 0");
                    scoreLabel.setBounds(10, this.getHeight() - 25, 100, 20);
                    scoreLabel.setForeground(Color.WHITE); 
                    scoreLabel.setFont(new Font("Arial", Font.CENTER_BASELINE, 16)); 
                    this.add(scoreLabel);
                    //Sets running to true so this code does not execute until the player has moved to another menu and back to this one
                    running = true;
                }
                //Calls the menu manager for the gameplay
                runPlanitaryDefense();
            }
            //This is the start menu block
            else if (display == Display.STARTMENU) {
                if (!running) {
                    //Current button keeps track of what button is currently selected
                    //0 is the start button
                    //1 is the rules button
                    //2 is the quit button
                    currentButton = 0;
                    
                    //Initalizes the main buttons of the game
                    playButton = new JLabel();
                    helpButton = new JLabel();
                    quitButton = new JLabel();
                    titleCard = new JLabel();
                    menuInstructions = new JLabel();

                    menuInstructions.setFont(new Font("Monospaced", Font.BOLD, 16)); 
                    menuInstructions.setBounds(43, 170, 600, 20);
                    menuInstructions.setText("Press the W, S, and Enter keys to use the menu");
                    menuInstructions.setForeground(Color.WHITE); 

                    titleCard.setFont(new Font("Monospaced", Font.BOLD, 50)); 
                    titleCard.setBounds(40, 100, this.getWidth(), 50);
                    titleCard.setText("PLANETARY DEFENSE");
                    titleCard.setForeground(Color.WHITE); 
                    
                    playButton.setFont(new Font("Arial", Font.BOLD, 24)); 
                    playButton.setBounds(40, 400, 158, 20);
                    playButton.setText("START GAME");
                    playButton.setForeground(Color.WHITE); 
                    
                    helpButton.setFont(new Font("Arial", Font.BOLD, 24));
                    helpButton.setBounds(40, 440, 81, 20);
                    helpButton.setText("RULES");
                    helpButton.setForeground(Color.WHITE); 

                    quitButton.setFont(new Font("Arial", Font.BOLD, 24));
                    quitButton.setBounds(40, 480, 57, 20);
                    quitButton.setText("QUIT");
                    quitButton.setForeground(Color.WHITE); 
                    
                    this.add(playButton);
                    this.add(helpButton);
                    this.add(quitButton);
                    this.add(titleCard);
                    this.add(menuInstructions);
                    //Sets running to true so this code does not execute until the player has moved to another menu and back to this one
                    running = true;
                }
                playButton.setVisible(true);
                helpButton.setVisible(true);
                quitButton.setVisible(true);
                titleCard.setVisible(true);
                menuInstructions.setVisible(true);
                //Calls the menu manager for the start manager
                runStartMenu();
            }
            //This is the rules menu block
            else if (display == Display.RULES) {
                if (!running) {
                    //Removes the elements of the start menu
                    this.remove(playButton);
                    this.remove(helpButton);
                    this.remove(quitButton);
                    this.remove(titleCard);
                    this.remove(menuInstructions);
                    playButton = null;
                    helpButton = null;
                    quitButton = null;
                    titleCard = null;
                    menuInstructions = null;

                    //Initializes the exit button so the user can go back to the start
                    rulesExitButton = new JLabel();
                    rulesExitButton.setText("BACK TO START MENU");
                    rulesExitButton.setFont(new Font("Arial", Font.BOLD, 24));
                    rulesExitButton.setBounds(10, 500, 273, 20);
                    rulesExitButton.setForeground(Color.WHITE);
                    rulesExitButton.setVisible(true);
                    this.add(rulesExitButton);
                    //Creats the Label that displays all of the text for the rules
                    rulesText = new JLabel();
                    rulesText.setText(  "<html>• The goal of the game is to shoot the asteroids before they hit your planet<br><br>" + 
                                        "• The game is physics based so your ship follows conservation of momentum as if you were in space<br><br>" + 
                                        "• To control your ship use WASD to apply trust up, left, down, or right respectively<br><br>" +
                                        "• Your ship is effected by planetary gravity so you must use your thrusters to stay in<br>orbit while also aiming your torpedos<br><br>" +
                                        "• To fire a torpedo use the F-key, the torpedo are also effected by planetary gravity<br><br>" +
                                        "• Torpedos are fired in the direction that your ship is moving with higher velocity<br><br>" +
                                        "• After a torpedo is fired you cannont colide with it for 0.25 seconds and must<br> wait 1.25 seconds to fire again, a torpedo is ready to fire when your ship is solid white<br><br>" +
                                        "• Unlike your ship, torpedos have a magnetic attraction to ateroids making them a little easier to hit<br><br>" +
                                        "• Each asteroid you hit gains you one point and each asteroid that hits your <br>planet subtracts one point<br><br>" +
                                        "• If your points go below zero or if you colide with any other object the game ends<br><br>" +
                                        "• Good luck!!</html>");
                    rulesText.setFont(new Font("Arial", Font.BOLD, 16));
                    rulesText.setBounds(10, 0, this.getWidth(), this.getHeight() - 65);
                    rulesText.setForeground(Color.WHITE);
                    rulesText.setVisible(true);
                    this.add(rulesText);
                    //Sets running to true so this code does not execute until the player has moved to another menu and back to this one
                    running = true;
                }
                //Calls the menu manager for the help menu
                runHelpMenu();
            }
            //Sleeps the code for sleepTime number of miliseconds
            try {
                Thread.sleep(sleepTime);
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    

    /**
     * Allows the SpaceObjects to adust the games score
     * @param num adds this value to the score
     */
    public void adjustScore(int num) {
        score += num;
    }
    
    /**
     * Manages the start menu
     * Updates the current button based on the user input
     * Will move the user into the next menu or leave the game once enter is pressed
     * Calls the repaint and invalidate method to update the Panel Display
     */
    public void runStartMenu() {
        //Moves the "cursor" that underlines the button 1 up
        if (wKey && gotInput) {
            currentButton -= 1;
            gotInput = false;
        }
        //Moves the "cursor" that underlines the button 1 down
        else if (sKey && gotInput) {
            currentButton += 1;
            gotInput = false;
        }
        //Block for if the user selects a button
        else if (enterKey && gotInput) {
            gotInput = false;
            //Starts the game by changing display to gameplay1 and exiting the method
            if (currentButton == 0) {
                display = Display.GAMEPLAY1;
                running = false;
                return;
            }
            //Enters the Rules menu by change display to rules and exiting the method
            else if (currentButton == 1) {
                display = Display.RULES;
                running = false;
                return;
            }
            //Exits the game by ending the code
            else if (currentButton == 2) {
                System.exit(0);
            }
        }
        //If the user tries to move the "cursor" below the lowest button this code loops it back to the top
        if (currentButton == -1) {
            currentButton = 2;
        }
        //If the user tries to move the "cursor" above the highest button this code loops it back to the tbottom
        else if (currentButton == 3) {
            currentButton = 0;
        }
        //Calls the invalidate and repaint to update the display
        invalidate();
        repaint();
    }

    /**
     * Runs the gameplay by calling 
     * the repaint and invalidate method to update the Panel Display
     */
    public void runPlanitaryDefense() {
        //Updates the score label
        scoreLabel.setText("Score: " + score);
        scoreLabel.repaint();
        //Calls the invalidate and repaint to update the display
        invalidate();
        repaint();
    }

    /**
     * Runs the help menu screen
     * Detects if the player has pressed enter to go back to the start menu and updates variables to do so
     * Calls the repaint and invalidate method to update the Panel Display
     */
    public void runHelpMenu() {
        //Runs if the user hits enter on the back to start menu button
        if (enterKey && gotInput) {
            //Prepares variables to enter another menu
            display = Display.STARTMENU;
            gotInput = false;
            running = false;
            //Removes the rulesMenu labels from the Panel
            this.remove(rulesText);
            this.remove(rulesExitButton);
            rulesText = null;
            rulesExitButton = null;
            return;
        }
         //Calls the invalidate and repaint to update the display
        invalidate();
        repaint();
    }

    /**
     * Manages the creation of new asteroids
     * @param asteroidFrequncy every asteroidFequency * 25 miliseconds there is a 10 percent chance to spawn a new asteroid
     */
    public void runAsteroids (int asteroidFrequncy) {
        Random rng = new Random();
        if (asteroidTimer > asteroidFrequncy) {
            if (rng.nextInt(0,10) == 0) {
                spaceObjects.add(new Asteroid(gravity, this, spaceObjects));
            }
            asteroidTimer = 0;
        }
        else {
            asteroidTimer++;
        }
    }

    /**
     * Contains three different code blacks that run depending on what menu display is set to
     * Draws all elements of the current menu onto GamePanel every time it is called
     * Inherited mehtod from JPanel
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //This is the startMenu block for paintComponent
        if (display == Display.STARTMENU) {
            //Adds a blackbackground
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            
            //Draws the dectorations for the menu
            g.setColor(Color.GRAY);
            g.fillOval(this.getWidth() - 450, this.getHeight() - 445, 600, 600);
            g.setColor(Color.BLACK);
            g.fillOval(this.getWidth() - 445, this.getHeight() - 440, 595, 595);
            g.fillRect(0, 0, this.getWidth(), this.getHeight() / 2);

            g.setColor(Color.BLUE);
            g.fillOval(this.getWidth() - 400, this.getHeight() - 400, 600, 600);
            g.setColor(Color.WHITE);
            g.fillOval(380, 268, 20, 20);


            //Draws the "cursor" under the start button if it is selected
            if (currentButton == 0) {
                g.fillRect(40, 421, 158, 3);
            }
            //Draws the "cursor" under the rules button if it is selected
            else if (currentButton == 1) {
                g.fillRect(40, 461, 81, 3);
            }
            //Draws the "cursor" under the quit button if it is selected
            else if (currentButton == 2) {  
                g.fillRect(40, 501, 57, 3);
            }
        }
        //This is the gameplay1 block for paintComponent
        else if (display == Display.GAMEPLAY1) {
            runAsteroids(asteroidPeriod);
            //Draws the background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            //This loop calls the animate and paint method on each space object
            //Each object update manages itself during gameplay via this loop
            //animate updates position and velocity, checks collisions, and more depending on the type of SpaceObject
            //paint causes each object to paint itself onto the gamePanel based on its position
            for (SpaceObject s : spaceObjects) {
                s.paint(g);
                s.animate();
            }
            //This loop removes dead objects from the game, creates new projectiles 
            //if the user fires one, and returns the player to the StartMenu if they die
            for (int i = spaceObjects.size() - 1; i >= 0; i--) {
                //Prepares variables for entering a new menu and exits the method as well as delates all SpaceObjects
                if ((spaceObjects.get(i) instanceof Ship && spaceObjects.get(i).checkDead()) || score < 0) {
                    display = Display.STARTMENU;
                    running = false;
                    spaceObjects.clear();
                    this.remove(scoreLabel);
                    scoreLabel = null;
                    return;
                }
                //Removes dead SpaceObjects from the game
                if (spaceObjects.get(i).checkDead()) {
                    spaceObjects.remove(i);
                }
                //If the user can fire and hits the F-key this creates a new projectile
                else if (spaceObjects.get(i) instanceof Ship) {
                    if (((Ship)spaceObjects.get(i)).isFireing()) {
                        spaceObjects.add(new Projectile(spaceObjects.get(i)));
                        ((Ship)spaceObjects.get(i)).setFireing(false);
                    }
                }
            }
        }
        //This is the rulesMenu block for paintComponent
        else if (display == Display.RULES) {
            //Paints a background
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setColor(Color.WHITE);
            //Displays the "cursor" under the BACK TO START MENU button
            g.fillRect(10, 520, 273, 3);
        }
    }

    /**
     * Detects for keyboard inputs from the player
     * Sets wKey, dKey, aKey, sKey, and fKey to true if their corresponding keys are pressed
     * Also sets got input to true to be used in the runTheStartMenu and runHelpMenu to limit uses of each key press to once
     * Implemented from KeyListener interface
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            wKey = true;
            gotInput = true;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            dKey = true;
            gotInput = true;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
            aKey = true;
            gotInput = true;
        } 
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            sKey = true;
            gotInput = true;
        } 
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("F")) {
            fKey = true;
            gotInput = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKey = true;
            gotInput = true;
        }
    }

    /**
     * Detects for keyboard inputs from the player
     * Sets wKey, dKey, aKey, sKey, and fKey to false if their corresponding keys are released
     * Implemented from KeyListener interface
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
            wKey = false;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
            dKey = false;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
            aKey = false;
        } 
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
            sKey = false;
        }
        if (KeyEvent.getKeyText(e.getKeyCode()).equals("F")) {
            fKey = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            enterKey = false;
        }
    }

    /**
     * Implimented from KeyListener interface
     * Not used
     */
    @Override
    public void keyTyped(KeyEvent e) {}
}