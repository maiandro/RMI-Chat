package ClientChat;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import ServerChat.IChat;
import ServerChat.Message;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.Calendar;

public class chatGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final String SIGNAL = "#CONNECTION_ENDED";
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea;

	private Client client;
	private IChat atendent;
	
	private boolean firstDeliver;
	/**
	 * Create the frame.
	 */
	public chatGUI(IChat a, Client c) {
		
		client = c;
		atendent = a;
		firstDeliver = true;
		
		/**
		 * GUI components
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 535);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(29, 370, 373, 81);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		textArea = new JTextArea();
		textArea.setBounds(29, 32, 373, 318);
		contentPane.add(textArea);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (textField.getText().equals("")) return;
				
				Message m = new Message();
				m.setDate(Calendar.getInstance());
				m.setMessage(textField.getText());
				m.setFrom(c.name);
				
				client.deliver(m);
				try {
					if (firstDeliver)
						atendent.fistDeliver(m, c);
					else
						atendent.deliver(m);
				} catch (RemoteException e) { 
					e.printStackTrace();
				}
				
				textField.setText("");
				firstDeliver = false;
			}
		});
		btnEnviar.setBounds(294, 462, 89, 23);
		contentPane.add(btnEnviar);
		
		JButton btnSair = new JButton("Sair");
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Message m = new Message();
				m.setMessage(SIGNAL);
				
				client.deliver(m);
				try {
					atendent.deliver(m);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnSair.setBounds(50, 462, 89, 23);
		contentPane.add(btnSair);
		
	}
	
	public void update() {
		Update u = new Update();
		u.run();
	}
	
	
	class Update implements Runnable {

		@Override
		public void run() {
			textArea.setText("");
			for (Message m : client.getChat())
				textArea.setText(textArea.getText() +"\n"+ m.getDate().getTime() + " - " + m.getFrom() + ": "+ m.getMessage());
		}
		
	}
}
