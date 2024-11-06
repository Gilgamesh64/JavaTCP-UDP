package TCP.clientSide;

import events.Event;
import events.SimpleEvent;

/**
 * class to manage the input-output of a client side TCP socket
 * if you need to use this in a complex project just put the content in main in another method
 * @author Gilgamesh64
 */
public class ClientMain {

    //event to trigger if you want to send a string via the sochet
    private static final Event<String> sendString = new SimpleEvent<>();

    public static Event<String> sendStringEvent() {
        return sendString;
    }

    public static void main(String[] args) {

        Client client = new Client("localhost", 5050);

        sendMessage(System.console().readLine("Enter text: "));

        client.stringReceivedEvent().addListener(
            stringReceivedFromServer -> {
                //gets the recieved string from the socket
                System.out.println("String recieved: " + stringReceivedFromServer);
                sendMessage(System.console().readLine("Enter text: "));
        });

        client.start();
    }
    private static void sendMessage(String s){
        //sends a string to the socket

        sendStringEvent().trigger(s);
    }
}