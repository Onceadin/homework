import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Client {
	private String name;
	private DatagramSocket socket;
	private DatagramSocket socketForReceiving;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ByteArrayInputStream bis;
	private ByteArrayOutputStream bos;

	public Client(String name) throws IOException {
		super();
		this.name = name;
		this.socket = new DatagramSocket();
		this.socketForReceiving = new DatagramSocket();
	}

	// form datagram packet for sending
	public DatagramPacket formPacket(byte[] buf) throws UnknownHostException {
		return new DatagramPacket(buf, buf.length,
				InetAddress.getByName("localhost"), 1234);
	}
	
	// message for registration in server list
	public Message formWelcomingMessage() {
		return new Message(this.name, new Date(),
				String.valueOf(socketForReceiving.getLocalPort()), "server");
	}

	// message for logout
	public Message formGoodbuyMessage() {
		return new Message(this.name, new Date(),
				String.valueOf(socketForReceiving.getLocalPort()), "buy");
	}
	
	// form message for sending
	public Message formMessage(String msg, String recipient) {
		return new Message(this.name, new Date(), msg, recipient);
	}

	// receive message
	public Message recieveMessage(DatagramPacket p) throws IOException {
		socketForReceiving.receive(p);
		Message msg = getMessageFromBytes(p.getData());
		return msg;
	}

	// send message
	public void sendMessage(Message msg) throws IOException {
		byte[] data = getBytesFromMessage(msg);
		DatagramPacket p = formPacket(data);
		socket.send(p);
	}

	public Message getMessageFromBytes(byte[] data) throws IOException {
		bis = new ByteArrayInputStream(data);
		ois = new ObjectInputStream(bis);
		Message msg = null;
		try {
			msg = (Message) ois.readObject();
		} catch (ClassNotFoundException e) {
			System.err.println("Something wrong with incoming message" + e);
		}
		ois.close();
		bis.close();
		return msg;
	}

	public byte[] getBytesFromMessage(Message msg) throws IOException {
		bos = new ByteArrayOutputStream();
		oos = new ObjectOutputStream(bos);
		oos.writeObject(msg);
		byte[] data = null;
		data = bos.toByteArray();
		oos.close();
		bos.close();
		return data;
	}

}
