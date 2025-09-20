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

    private static int min =  20;
    private static int max = 100;

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

    private int[][] rates;  //individual bee rates
    private int[][] counts; //individual bee counts
    private int[][] speeds; //individual bee speeds
    private int r;          // base blink rate (inverse rate, technically)
    private Timer t;        // time
    private int w;          // user-input simulation width

    public GridPanel (int wid) {
 
        r   = 30; 
        w   = wid; 

        rates  = new int[w][w];
        counts = new int[w][w];

        for (int i=0; i<w; i++) {
            for (int j=0; j<w; j++){
                rates[i][j] = (int)( (1+Math.random()*2)*r*w/20 ); //( (w/20)*r + Math.random()*r/2 );
                counts[i][j] = (int) (Math.random() * (r+1));
            }
        }

        t = new Timer(3000/(2*w), new Step());
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
                    // if it is blinking by itself, reset the rate
                    if (counts[i][j] == 0) {
                        rates[i][j] = (int)( (w/20)*r + Math.random()*r/2 );
                    }      
                                  
                }
            }

            // if a bee happens to be brave, reset its count
            double brave = Math.random();
            if (brave < 0.01) {
                int randx = (int)(Math.random()*w);
                int randy = (int)(Math.random()*w);
                counts[randx][randy] = 0;
            }

            repaint();

            for (int i=0; i<w; i++) {
                for (int j=0; j<w; j++){
                    // if it hasnt blinked recently and something else around it blinks, reset counts[i][j]
                    if (counts[i][j]>rates[i][j]/2 && (checkBlink(next(i),j) || checkBlink(prev(i),j) 
                                                    || checkBlink(i,next(j)) || checkBlink(i,prev(j)))) {
                        counts[i][j] = -2 + (int)(Math.random()*2); // random number between -2 and -1
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

        int cw = 800/w; // cell width

        for (int i=0; i<w; i++) { // for each x
            for (int j=0; j<w; j++){ // for each y
                //blink if counts[i][j] < some value based on width
                if (counts[i][j] < 1+w/20){
                    g2.setColor(Color.black);
                    //g2.fillRect(4+cw*i,4+cw*j,cw-4,cw-4);
                    g2.fillOval(cw*i,cw*j,cw,cw);
                    
                } else {
                    g2.setColor(new Color(0.7f,0.5f,0.0f));
                    //g2.fillRect(4+cw*i,4+cw*j,cw-4,cw-4);
                    g2.fillOval(cw*i,cw*j,cw,cw);
                }
            }
        }

    }
}