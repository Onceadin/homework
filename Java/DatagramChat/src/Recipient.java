import java.net.InetAddress;

public class Recipient {
	private InetAddress address;
	private int port;
	private String name;

	public Recipient(InetAddress address, int port) {
		super();
		this.address = address;
		this.port = port;
	}

	/**
	 * @return the address
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
