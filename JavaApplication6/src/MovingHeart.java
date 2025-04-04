import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;

public class MovingHeart extends JPanel implements ActionListener {
    private int x = 100;
    private int y = 150;
    private int dx = 2;
    private Timer timer;

    public MovingHeart() {
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Vẽ trái tim chính xác hơn
        g2d.setColor(Color.RED);
        Path2D heart = new Path2D.Double();
        heart.moveTo(x, y + 30);
        heart.curveTo(x - 40, y - 40, x - 80, y + 30, x, y + 80);
        heart.curveTo(x + 80, y + 30, x + 40, y - 40, x, y + 30);
        g2d.fill(heart);

        // Vẽ chữ
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Yêu Bé Yêu Của Anh", x - 40, y + 120);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += dx;
        if (x > getWidth() - 100 || x < 50) {
            dx = -dx;
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Trái Tim Chuyển Động");
        MovingHeart heart = new MovingHeart();
        frame.add(heart);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
