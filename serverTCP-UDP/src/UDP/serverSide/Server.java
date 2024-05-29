package UDP.serverSide;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread{
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[65535];

    public Server() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException e) { e.printStackTrace(); }
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) { e.printStackTrace(); }
            
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            String text = new String(buf, 0, packet.getLength());
            String reverseText = new StringBuilder(text).reverse().toString();
            buf = reverseText.getBytes();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());
            
            if (received.equals("end")) {
                running = false;
                continue;
            }
            try {
                socket.send(packet);
            } catch (IOException e) { e.printStackTrace(); }
        }
        socket.close();
    }
}
