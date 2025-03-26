import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class MouseWatcher implements MouseListener {
    private JButton button;
    private String window;
    private JFrame frame;

    MouseWatcher(JButton button){
        this.button = button;
    }

    MouseWatcher(JButton button, String window, JFrame frame) {
        this.button = button;
        this.window = window;
        this.frame = frame;
    }

    public void mouseEntered(MouseEvent e){
        button.setBorder(new LineBorder(Color.green, 10, true));
    }
    public void mouseExited(MouseEvent e) {
        button.setBorder(new LineBorder(Color.green, 5, true));
    }
    public void mouseDragged(MouseEvent e) { }

    public void mouseClicked(MouseEvent e) {
        NextWindow nextFrame = new NextWindow(window);
        nextFrame.next();
        frame.dispose();
        // this needs to be config
    }
    public void mousePressed(MouseEvent e) { }
    public void mouseReleased(MouseEvent e) { } 
}
