package TCP.clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import events.Event;
import events.SimpleEvent;

/**
 * client class to manage a TCP socket
 * @see ClientMain
 * @author Gilgamesh64
 */
public class Client extends Thread{
    private Socket socket;

    private final Event<String> stringReceived = new SimpleEvent<>();

    public Event<String> stringReceivedEvent() {
        return stringReceived;
    }

    public Client(String hostname, int port){

        try {
            socket = new Socket(hostname, port);

        } catch (UnknownHostException e) { 
            e.printStackTrace();

        } catch (IOException e) { 
            e.printStackTrace(); 
        }
        
        ClientMain.sendStringEvent().addListener(
            message -> { //event triggered from the outside to send a message
                if(message.equals("close")) dispose();
                else sendMessage(message);
        });
    }

    @Override
    public void run() {
        while(socket.isConnected()) readMessage(); //continuously waits for a message to arrive
    }

    /**
     * reads the input buffer waiting for a message
     */
    private void readMessage(){
        try{
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String received = reader.readLine();
            stringReceivedEvent().trigger(received); //triggers an event from the outside to receive a message

        }catch(IOException e){
            System.out.println("I/O error: " + e.getMessage());
        }    
    }

    /**
     * sends a message on the socket
     * @param text message to send
     */
    private void sendMessage(String text){
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

    public void dispose(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
