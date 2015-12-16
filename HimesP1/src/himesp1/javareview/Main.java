//Alan Himes
//ahimes1@cnm.edu
//main.java
package himesp1.javareview;

import javax.swing.JFrame;

/**
 *
 * @author ahimes1
 */
public class Main {
    private TipsterFrame frame;
    
    public static void main(String[] args) {
        new Main();
    }
    
    public Main() {
        frame = new TipsterFrame();
        frame.setTitle("Alan Himes, P1 Tipster in Java, Java Review");
        frame.setSize(400, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
