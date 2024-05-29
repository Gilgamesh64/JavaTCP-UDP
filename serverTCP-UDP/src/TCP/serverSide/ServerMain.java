package TCP.serverSide;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Server s = new Server(5050);
        s.start();
    }
}
