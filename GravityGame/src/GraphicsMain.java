import java.awt.*;
import javax.swing.*;   
public class GraphicsMain{
    public static void main(String args[]){
        //Creates the JFrame for the game to take place in
        int height = 600;
        int width = 800;
        JFrame frame = new JFrame("PLANETARY DEFENSE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new GridLayout(1, 1));
        ImageIcon image = new ImageIcon("asteroid.png");

        
        Image cover = image.getImage();
        frame.setIconImage(cover);

        //Creates the GamePanel where all of the game takes place in
        GamePanel panel = new GamePanel(height, width);
        frame.add(panel);
        frame.addKeyListener(panel.getListener());
        frame.setVisible(true);
        frame.setResizable(false);

        //Calls the method that runs the whole game in GamePanel then the main code ends
        panel.runTheGame();
    }
}