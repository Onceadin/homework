import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Client {
	public static void main(String[] args) throws IOException {
		if(args.length != 3){
			System.err.println("Usage: java Client <hostname> <port> <your name>");
		}
		Console c = System.console();
		DatagramSocket socket = new DatagramSocket();
		byte[] buf = new byte[256];
		InetAddress address = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		Message msg = null;
		if(c == null){
			msg = new Message(args[2], "I'm here", "all");
		}
		else{
			String message = c.readLine();
			msg = new Message(args[2], message, "all");
		}
		buf = msg.toString().getBytes();
		DatagramPacket packet = new DatagramPacket(msg.toString().getBytes(), buf.length, address, port);
		socket.send(packet);
		packet = new DatagramPacket(buf, buf.length);
		socket.receive(packet);
		ByteArrayInputStream bs = new ByteArrayInputStream(packet.getData());
		ObjectInputStream os = new ObjectInputStream(bs);		
		msg = null;
		try {
			msg = (Message) os.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(msg);
		
		socket.close();
	}
}
