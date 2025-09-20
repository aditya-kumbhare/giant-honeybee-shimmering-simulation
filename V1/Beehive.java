/**
 * @author Aditya Kumbhare
 * 
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math;

class Beehive extends JFrame {

    private static int min = 10;
    private static int max = 50;

    public static void main (String[] args) {
        try {
            if (args.length<1 || Integer.parseInt(args[0])<min || Integer.parseInt(args[0])>max) {
                System.out.println("Usage: java Beehive <integer width between "+min+" and "+max+">");
                return;
            } else {

                System.setProperty("sun.java2d.opengl", "true");

                JFrame frame = new JFrame();

                frame.setTitle("Simulation");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setSize(880,900);
                frame.setLocationRelativeTo(null);

                GridPanel panel = new GridPanel(Integer.parseInt(args[0]));
                frame.add(panel);
                
                frame.setVisible(true);
                frame.setResizable(false);

            }
        } catch (Exception e) {
            System.out.println("Usage: java Beehive <integer width between "+min+" and "+max+">");
            return;
        }
    }
}

class GridPanel extends JPanel {

    private int[][] rates;
    private int[][] counts;
    private int[][] speeds;
    private int max;
    private int min;
    private int r;
    private Timer t;
    private int w;

    public GridPanel (int wid) {

        max = 15;
        min = 10;
        r   = 30;
        w   = wid;
        

        rates  = new int[w][w];
        counts = new int[w][w];
        speeds = new int[w][w];

        for (int i=0; i<w; i++) {
            for (int j=0; j<w; j++){
                rates [i][j] = r; //(int) (Math.random() * (max - min + 1)) + min;
                counts[i][j] = (int) (Math.random() * (r+1)); //0;
                speeds[i][j] = (int) (Math.random() * (10)) + 1; // how fast does the cell reset its count?
            }
        }

        t = new Timer(3000/(w+20), new Step());
        t.start();
    }

    private int next(int a){
        //return (a+1) % rates.length;
        return Math.min(a+1, w-1);
    }
    private int prev(int a){
        //return (a+rates.length-1) % rates.length;
        return Math.max(a-1, 0);
    }
    private boolean checkBlink (int i, int j) {
        if (i<0 || j<0 || i>=w || j>=w) return false;
        return counts[i][j]==0;
    }
    

    private class Step implements ActionListener {

        @Override
        public void actionPerformed (ActionEvent a) {
            
            for (int i=0; i<w; i++) {
                for (int j=0; j<w; j++){
                    counts[i][j] = (counts[i][j]+1) % rates[i][j];
                }
            }

            repaint();

            for (int i=0; i<w; i++) {
                for (int j=0; j<w; j++){
                    //if it hasnt blinked recently and something else around it blinks, reset counts[i][j]
                    if (counts[i][j]>r/2 && (checkBlink(next(i),j) || checkBlink(prev(i),j) || checkBlink(i,next(j)) || checkBlink(i,prev(j)))) {
                        counts[i][j] = -1;
                        //counts[i][j] *=9;
                        //counts[i][j] /=10;
                        //counts[i][j] /= speeds[i][j];
                    }
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D)g;

        setBackground(new Color(0.3f,0.3f,0.3f));

        int cellWidth = 800/w;

        for (int i=0; i<w; i++) { // for each x
            for (int j=0; j<w; j++){ // for each y
                //blink if counts[i][j] == 0
                if (counts[i][j]==0 || counts[i][j]==1){
                    g2.setColor(Color.black);
                    g2.fillRect(4+cellWidth*i,4+cellWidth*j,cellWidth-4,cellWidth-4);
                    
                } else {
                    g2.setColor(new Color(0.7f,0.5f,0.0f));
                    g2.fillRect(4+cellWidth*i,4+cellWidth*j,cellWidth-4,cellWidth-4);
                }
            }
        }

    }
}