import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private Image body;
    private Image apple;
    private Image wall;
    private Image grass;
    private Image head;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
        setBackground(Color.BLACK);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }
    public void initGame(){
        dots = 3;
        for (int i = 0; i<dots; i++){
            x[i] = 320 - i*DOT_SIZE;
            y[i] = 320;
        }
        timer = new Timer(200,this);
        timer.start();
        createApple();
    }
    public void createApple(){
        appleX = new Random().nextInt(17)*DOT_SIZE + 32;
        appleY = new Random().nextInt(17)*DOT_SIZE + 32;
    }
    public void loadImages(){
        apple = new ImageIcon("image/apple.png").getImage();
        body = new ImageIcon("image/body.png").getImage();
        wall = new ImageIcon("image/wall.png").getImage();
        grass = new ImageIcon("image/grass.jpg").getImage();
        head = new ImageIcon("image/head.png").getImage();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(inGame){
            for (int i = 0; i < 21; i++) {
                g.drawImage(wall,i*DOT_SIZE,0,this);
                g.drawImage(wall,0,i*DOT_SIZE,this);
                g.drawImage(wall,640,i*DOT_SIZE,this);
                g.drawImage(wall,i*DOT_SIZE,640,this);
            }
            for (int i = 1; i < 20; i++) {
                for (int j = 1; j < 20; j++) {
                    g.drawImage(grass,i*DOT_SIZE,j*DOT_SIZE,this);
                }
            }
            g.drawImage(apple,appleX,appleY,this);
            g.drawImage(head,x[0],y[0],this);
            for (int i = 1; i < dots; i++) {
                g.drawImage(body,x[i],y[i],this);
            }

        }else{
            String str = "Game over";
            g.setColor(Color.WHITE);
            g.drawString(str,SIZE/2-30,SIZE/2);
        }
    }
    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }
    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if(x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }
        if(x[0]>SIZE-32){
            inGame = false;
        }
        if(x[0]<32){
            inGame = false;
        }
        if(y[0]>SIZE-32){
            inGame = false;
        }
        if(y[0]<32){
            inGame = false;
        }
    }
    public void actionPerformed(ActionEvent e){
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
    class FieldKeyListener extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !down){
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
