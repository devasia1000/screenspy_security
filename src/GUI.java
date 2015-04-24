import javax.swing.*;
import java.awt.*;

/**
 * Created by devasia on 4/23/15.
 */
public class GUI extends JFrame{

    private JPanel rootPane;
    JLabel label;

    public GUI () {
        super();

        setContentPane(rootPane);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int h = (int) screenSize.getHeight();
        int w = (int) screenSize.getWidth();
        setSize(w, h);

        label.setSize(w, h);

        setVisible(true);
    }

}
