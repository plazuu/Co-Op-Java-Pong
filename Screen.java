import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Screen {
    public JFrame frame;


    public static void main(String[] args) {
        Screen screen = new Screen();
        screen.go();
    }


    public void go() {
        frame = new JFrame("PONG BY CHOMPERT");
        JLabel nameLabel = new JLabel("PONG", SwingConstants.CENTER);

        Font customFont = Screen.initFont();

        JPanel mainOptionsPanel = new JPanel(new BorderLayout()); 

        Box optionLayout = new Box(BoxLayout.Y_AXIS);

        JButton startButton = new JButton("Start");
        startButton.setMinimumSize(new Dimension(100,50));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        JButton settingsButton = new JButton("Settings");
        settingsButton.setMinimumSize(new Dimension(100,50));
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //
        //FONT FORMATING AND COLOR
        //

        nameLabel.setFont(customFont.deriveFont(Font.BOLD, 30));
        nameLabel.setForeground(Color.green); 
        startButton.setFont(customFont.deriveFont(Font.PLAIN, 15));
        startButton.setForeground(Color.green); 
        settingsButton.setFont(customFont.deriveFont(Font.PLAIN, 15));
        settingsButton.setForeground(Color.green); 

        startButton.setBorderPainted(true);
        startButton.setBorder(new LineBorder(Color.green, 5, true));
        startButton.setBackground(Color.black);

        MouseWatcher startWatcher = new MouseWatcher(startButton, "coop", frame);

        startButton.addMouseListener(startWatcher);

        settingsButton.setBorderPainted(true);
        settingsButton.setBorder(new LineBorder(Color.green, 5, true));
        settingsButton.setBackground(Color.black);
        
        MouseWatcher settingsWatcher = new MouseWatcher(settingsButton,"settings",
                 frame);

        settingsButton.addMouseListener(settingsWatcher);

        GridLayout grid = new GridLayout(2,1);
        grid.setHgap(100);
        grid.setVgap(100);

        mainOptionsPanel.setLayout(grid);
        mainOptionsPanel.setBackground(Color.black);
        mainOptionsPanel.add(startButton);
        mainOptionsPanel.add(settingsButton);



        frame.setLayout(new GridLayout(3, 3));

        for(int i = 0; i < 9; i++) {

            switch(i){
                case 1:
                    frame.getContentPane().add(nameLabel);
                    continue;
                case 4:
                    frame.getContentPane().add(mainOptionsPanel);
                    continue;
            }
            JLabel j = new JLabel("");
            j.setBackground(Color.black);
            frame.getContentPane().add(j);
        }


        Screen.setWindow(frame);
    }


    public static Font initFont(){
        Font result = null;

        try {
            GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            Font customFont = Font.createFont(Font.TRUETYPE_FONT,
                    new File("Px437_IBM_BIOS-2x.ttf"));
            ge.registerFont(customFont);
            result = customFont;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("failed to load");
        }
        return result;
    }

    public static void setWindow(JFrame frame){
        frame.getContentPane().setBackground(Color.black);     
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setMinimumSize(new Dimension(500,500));
        frame.setLocation(200,100);
        frame.pack();
        frame.setVisible(true);

    }

}
