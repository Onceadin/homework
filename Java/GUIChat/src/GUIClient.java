import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;

public class GUIClient {

	private JFrame frame;
	private JTextField textForSending;
	private Client client;
	private JTextField txtName;
	private TextArea textArea;
	private JTextField textNameOfRecipient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIClient window = new GUIClient();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIClient() {
		initialize();
	}

	public void startListening() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					byte[] incomingData = new byte[1024];
					DatagramPacket p = new DatagramPacket(incomingData,
							incomingData.length);
					System.out.println("I'm listening");
					try {
						client.recieveMessage(p);
					} catch (IOException e) {
						System.err.println("Can't get the message" + e);
					}
					System.out.println("=====>");
					Message msg = null;
					try {
						msg = client.getMessageFromBytes(p.getData());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					textArea.append(msg.toString() + '\n');
				}
			}
		}).start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panelForMessages = new JPanel();
		panelForMessages.setBounds(0, 0, 330, 275);
		frame.getContentPane().add(panelForMessages);
		panelForMessages.setLayout(null);

		JPanel panelForGetting = new JPanel();
		panelForGetting.setBounds(0, 0, 330, 201);
		panelForMessages.add(panelForGetting);
		panelForGetting.setLayout(new CardLayout(0, 0));

		textArea = new TextArea();
		textArea.setEditable(false);
		panelForGetting.add(textArea, "name_32238910945477");

		JPanel panelForSending = new JPanel();
		panelForSending.setBounds(0, 213, 330, 61);
		panelForMessages.add(panelForSending);
		panelForSending.setLayout(new CardLayout(0, 0));

		textForSending = new JTextField();
		panelForSending.add(textForSending, "name_10163533542484");
		textForSending.setColumns(10);

		JPanel panelForButtons = new JPanel();
		panelForButtons.setBounds(342, 0, 106, 275);
		frame.getContentPane().add(panelForButtons);
		panelForButtons.setLayout(null);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = client.formMessage(textForSending.getText(),
						"all");
				textForSending.setText("");
				try {
					client.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSend.setBounds(12, 238, 70, 25);
		btnSend.setHorizontalAlignment(SwingConstants.RIGHT);
		panelForButtons.add(btnSend);

		JButton btnLogin = new JButton("logIn");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					client = new Client(txtName.getText());
					Message helloServer = client.formWelcomingMessage();
					client.sendMessage(helloServer);
					startListening();
					textForSending.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(12, 36, 70, 25);
		panelForButtons.add(btnLogin);

		txtName = new JTextField();
		txtName.setText("Name");
		txtName.setBounds(0, 12, 94, 19);
		panelForButtons.add(txtName);
		txtName.setColumns(10);
		
		textNameOfRecipient = new JTextField();
		textNameOfRecipient.setBounds(0, 115, 94, 19);
		panelForButtons.add(textNameOfRecipient);
		textNameOfRecipient.setColumns(10);
		
		JButton btnSendForHim = new JButton("Send for him");
		btnSendForHim.setFont(new Font("Dialog", Font.BOLD, 5));
		btnSendForHim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Message msg = client.formMessage(textForSending.getText(), textNameOfRecipient.getText());
				try {
					client.sendMessage(msg);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSendForHim.setBounds(0, 146, 94, 25);
		panelForButtons.add(btnSendForHim);
	}
}
