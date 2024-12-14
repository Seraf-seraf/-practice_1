package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Game extends JFrame {

    private static Game game;
    private static Image php;

    public static void main(String[] args) throws IOException {
        game = new Game();
        php = ImageIO.read(Game.class.getResourceAsStream("php.png"));

        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(900, 600);
        game.setLocationRelativeTo(null);

        GameField game_field = new GameField();
        game.add(game_field);       

        game.setResizable(false);
        game.setVisible(true);
    }    

    private static class GameField extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

        private Timer timer;
        private int php_x = 300;
        private int php_original_y = 100;
        private int php_y = php_original_y;
        private int jump_height = 50;
        private int jump_speed = 5;
        private boolean going_up = true;

        private boolean dragging = false;
        private int mouse_offset_x;
        private int mouse_offset_y;

        public GameField() {
            timer = new Timer(30, this);
            timer.start();
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(php, php_x, php_y, this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!dragging) {
                if (going_up) {
                    php_y -= jump_speed;
                    if (php_y <= php_original_y - jump_height) {
                        php_y = php_original_y - jump_height;
                        going_up = false;
                    }
                } else {
                    php_y += jump_speed;
                    if (php_y >= php_original_y) {
                        php_y = php_original_y;
                        going_up = true;
                    }
                }
                repaint();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int mouse_x = e.getX();
            int mouse_y = e.getY();
            if (mouse_x >= php_x && mouse_x <= php_x + php.getWidth(null) &&
                mouse_y >= php_y && mouse_y <= php_y + php.getHeight(null)) {
                dragging = true;
                mouse_offset_x = mouse_x - php_x;
                mouse_offset_y = mouse_y - php_y;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            dragging = false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (dragging) {
                php_x = e.getX() - mouse_offset_x;
                php_y = e.getY() - mouse_offset_y;
                repaint();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseMoved(MouseEvent e) {}
    }
}
