package TCP.serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * simple TCP server thread that sends a string when received one
 * @author Gilgamesh64
 */
public class ServerThread extends Thread {
    
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String text;

            do {
                text = reader.readLine();
                writer.println(text + " :)");
            } while (!text.equals("close"));

            close();
        } catch (IOException ex) {
            close();
            return;
        }
    }
    private void close(){
        try {
            socket.close();
            System.out.println("Closing Socket");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}