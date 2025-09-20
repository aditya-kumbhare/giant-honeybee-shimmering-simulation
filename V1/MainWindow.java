public class MainWindow {
    private JFrame window;

    public MainWindow() {
        window = new JFrame();
        window.setTitle("Simulation");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(800,800);
        window.setLocationRelativeTo(null); 
    }

    public void show() {
        window.setVisible(true);
    }

}