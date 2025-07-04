package motorcycle.network;

import java.io.PrintWriter;
import java.net.Socket;

public class ClientNotifier {

    private final Socket socket;

    public ClientNotifier(Socket socket) {
        this.socket = socket;
    }

    public void sendUpdateRequest() {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("REQUEST_UPDATE");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
