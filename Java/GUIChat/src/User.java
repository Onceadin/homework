import java.net.InetAddress;


public class User {
	private String name;
	private InetAddress inetAddress;
	private int portNumber;
	public User(InetAddress inetAddress, int portNumber, String name) {
		super();
		this.name = name;
		this.inetAddress = inetAddress;
		this.portNumber = portNumber;
	}
	/**
	 * @return the inetAddress
	 */
	public InetAddress getInetAddress() {
		return inetAddress;
	}
	/**
	 * @return the portNumber
	 */
	public int getPortNumber() {
		return portNumber;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	
}
