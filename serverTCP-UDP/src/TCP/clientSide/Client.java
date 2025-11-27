package TCP.clientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * client class to manage a TCP socket
 * @see ClientMain
 * @author Gilgamesh64
 */
public class Client implements Runnable {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    private Consumer<String> onReceive = s -> {};
    private Thread thread;

    public Client(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                onReceive.accept(line);
            }
        } catch (IOException e) {System.out.println("Connection closed: " + e.getMessage());
        } finally {dispose();}
    }

    public void start() {
        thread = Thread.startVirtualThread(this);
    }

    public void send(String text) {
        writer.println(text);
    }

    public void onReceive(Consumer<String> c) {
        onReceive = c;
    }

    public void dispose() {
        try {socket.close();} catch (IOException ignored) {}

        if (thread != null) thread.interrupt();
    }
}
