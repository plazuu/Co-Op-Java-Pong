import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class NextWindow {

    private GameScreen currentScreen;
    private String window;
    NextWindow(String window) { 
        this.window = window;
    }

    public void next() {
        if (window.equals("coop")) {
            currentScreen = new GameScreen();
            currentScreen.go();
        }
    }   
}
