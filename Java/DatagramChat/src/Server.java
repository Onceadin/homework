import java.net.SocketException;


public class Server {
	public static void main(String[] args) throws SocketException {
		new ServerThread(2112).start();
	}
}
