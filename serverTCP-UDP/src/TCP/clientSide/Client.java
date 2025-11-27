package TCP.clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

/**
 * client class to manage a TCP socket
 * @see ClientMain
 * @author Gilgamesh64
 */
public class Client implements Runnable{
    private Socket socket;

    private Consumer<String> onReceive;

    public Client(String hostname, int port){

        try { socket = new Socket(hostname, port); }
        catch (Exception e) { e.printStackTrace(); }
        
    }

    @Override
    public void run() {
        while(socket != null) readMessage(); //continuously waits for a message to arrive
    }

    public void start() {
        Thread.startVirtualThread(this);
    }

    /**
     * reads the input buffer waiting for a message
     */
    private void readMessage(){
        try{
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String recieved = reader.readLine();
            onReceive.accept(recieved);

        }catch(IOException e){
            System.out.println("I/O error: " + e.getMessage());
        }    
    }

    /**
     * sends a message on the socket
     * @param text message to send
     */
    public void send(String text){
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            writer.println(text);

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }

    public void onReceive(Consumer<String> c){
        onReceive = c;
    }

    public void dispose(){
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
