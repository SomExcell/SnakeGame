import javax.swing.*;
import java.awt.*;
public class MainWindow extends JFrame {
    private Image icon;
    public MainWindow(){
        setTitle("Змейка");
        setIconImage(icon = new ImageIcon("image/snakeIcon.png").getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(686,710);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new GameField());
        setVisible(true);
    }
    public static void main(String [] args){
        MainWindow frame = new MainWindow();
    }
}

