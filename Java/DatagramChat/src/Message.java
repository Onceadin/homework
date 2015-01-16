import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7187273548319535555L;
	private String name;
	private String message;
	private String recipient;

	public Message(String name, String message, String recipient) {
		super();
		this.name = name;
		this.message = message;
		this.recipient = recipient;
	}

	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the recipient
	 */
	public String getRecipient() {
		return recipient;
	}

	public byte[] toByte() {
		byte[] tmp = null;
		try {
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bs);
			os.writeObject(this);
			os.flush();
			os.close();
			bs.close();
			tmp = bs.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	@Override
	public String toString() {
		return name + ": " + message;
	}
}
