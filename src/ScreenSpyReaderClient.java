import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by devasia on 4/23/15.
 */
class ScreenSpyReaderClient {

    private static String IP_ADDRESS = "127.0.0.1";

    public static void main(String args[]) {

        Socket sock = null;

        try {
            sock = new Socket(IP_ADDRESS, 8081);
            System.out.println("Connected to Server");

            GUI gui = new GUI();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setVisible(true);

            while (true) {
                BufferedImage temp = ImageIO.read(sock.getInputStream());
                if (temp != null) {
                    gui.label.setIcon(new ImageIcon(temp));
                    System.out.println("Read and Showed Image");
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            if (sock != null) {

                try {
                    sock.close();
                } catch (Exception eg) {}
            }

            main(null);
        }
    }
}
