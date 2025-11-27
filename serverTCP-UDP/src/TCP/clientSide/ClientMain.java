package TCP.clientSide;

/**
 * class to manage the input-output of a client side TCP socket
 * if you need to use this in a complex project just copy paste this class
 * @author Gilgamesh64
 */
public class ClientMain {

    public static void main(String[] args) {

        Client client = new Client("localhost", 5050);
        client.start();

        client.onReceive(
            stringReceivedFromServer -> 
                System.out.println("String received: " + stringReceivedFromServer));
        
        client.send(System.console().readLine("Enter text: "));
    }
}