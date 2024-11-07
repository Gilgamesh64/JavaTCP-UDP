package TCP.clientSide;

import events.Event;
import events.SimpleEvent;

/**
 * class to manage the input-output of a client side TCP socket
 * if you need to use this in a complex project just copy paste this class
 * @author Gilgamesh64
 */
public class ClientMain {

    //event to trigger if you want to send a string via the socket
    private static final Event<String> sendString = new SimpleEvent<>();

    /**
     * example: sendStringEvent().trigger("foo")
     * sends foo to every actionListener
     * @return triggerable event
     */
    public static Event<String> sendStringEvent() {
        return sendString;
    }

    public static void main(String[] args) {

        Client client = new Client("localhost", 5050);

        sendMessage(System.console().readLine("Enter text: "));

        client.stringReceivedEvent().addListener(
            stringReceivedFromServer -> {
                //gets the received string from the socket
                System.out.println("String received: " + stringReceivedFromServer);
                sendMessage(System.console().readLine("Enter text: "));
        });

        client.start();
    }

    /**
     * sends the string to the server
     * @param s
     */
    private static void sendMessage(String s){
        
        sendStringEvent().trigger(s);
    }
}