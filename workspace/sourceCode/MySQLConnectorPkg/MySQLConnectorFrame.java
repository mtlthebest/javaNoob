package MySQLConnectorPkg;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JScrollPane;

public class MySQLConnectorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField passwordTF;
	private JTextField gradoTF;
	private JTextField portTF;
	private JTextField usernameTF;
	private JLabel gradoLabel;
	private JLabel corpoLabel;
	private JLabel nomeLabel;
	private JLabel cognomeLabel;
	private JButton newCrewMemberButton;
	private JTextPane statusBar;
	private newCrewMemberButtonHandler cbHandler;
	private JScrollPane scrollPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MySQLConnectorFrame frame = new MySQLConnectorFrame(null, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MySQLConnectorFrame(Connection conn, String[] connectionParameters) {

		setLocation(100, 100);
		setTitle("Add new crew member");
		setSize(420, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);

		cbHandler = new newCrewMemberButtonHandler();
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(4, 2));

		gradoLabel = new JLabel("Grado: ");
		gradoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(gradoLabel);

		gradoTF = new JTextField();
		gradoTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(gradoTF);

		corpoLabel = new JLabel("Corpo/Categoria: ");
		corpoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(corpoLabel);

		portTF = new JTextField();
		portTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(portTF);

		nomeLabel = new JLabel("Nome: ");
		nomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(nomeLabel);

		usernameTF = new JTextField();
		usernameTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(usernameTF);

		cognomeLabel = new JLabel("Cognome: ");
		cognomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(cognomeLabel);

		passwordTF = new JTextField();
		passwordTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(passwordTF);

		newCrewMemberButton = new JButton("New crew member");
		contentPane.add(newCrewMemberButton);
		newCrewMemberButton.addActionListener(cbHandler);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		statusBar = new JTextPane();
		scrollPane.setViewportView(statusBar);

	}

	class newCrewMemberButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}
	}
}