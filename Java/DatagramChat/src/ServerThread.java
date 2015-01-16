import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {
	private DatagramSocket socket;
	private List<Recipient> listOfClients;
	private boolean goingOn;

	public ServerThread(int port) throws SocketException {
		socket = new DatagramSocket(port);
		listOfClients = new ArrayList<>();
		goingOn = true;
	}

	public void run() {
		while (goingOn) {
			try {
				byte[] buf = new byte[256];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				buf = packet.getData();
				ByteArrayInputStream bs = new ByteArrayInputStream(buf);
				ObjectInputStream os = new ObjectInputStream(bs);		
				Message msg = null;
				try {
					msg = (Message) os.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String recipient = msg.getRecipient();
				switch (recipient) {
				case "all":
					sendForAll(msg);
					System.out.println(msg);
					break;
				case "server":
					InetAddress ia = packet.getAddress();
					int port = packet.getPort();
					Recipient goingOut = new Recipient(ia, port);
					if (listOfClients.contains(goingOut)) {
						listOfClients.remove(goingOut);
						System.err.println(goingOut.getName() + " going out.");
					} else {
						System.err
								.println("There are no recipient with these data.");
					}
					break;
				default:
					for(Recipient r: listOfClients){
						if(r.getName() == recipient)
							sendFor(r, msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				goingOn = false;
			}
		}

	}

	private void sendForAll(Message msg) throws IOException {
		byte[] buf = new byte[256];
		for (Recipient a : listOfClients) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length,
					a.getAddress(), a.getPort());
			socket.send(packet);
		}
	}

	private void sendFor(Recipient recipient, Message msg) throws IOException {
		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length,
				recipient.getAddress(), recipient.getPort());
		socket.send(packet);

	}
}
