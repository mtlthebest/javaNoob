import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class MySQLconnector extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String dbClassName = "com.mysql.jdbc.Driver";
	private static final String CONFIG_FILE = "MySQLconnector.cfg";
	private static final String[] CONFIG_STRUCTURE = { "Server_Hostname",
			"Port", "Username", "Password", "Database" };

	private static String[] configSettings = new String[5];

	private JPanel contentPane;
	private JTextField databaseTF;
	private JPasswordField passwordTF;
	private JTextField serverHostnameTF;
	private JTextField portTF;
	private JTextField usernameTF;
	private JLabel serverHostnameLabel;
	private JLabel portLabel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel databaseLabel;
	private JButton disconnectButton;
	private JButton connectButton;
	private JButton resetButton;
	private JTextPane statusBar;
	private JButton proceedButton;
	private ConnectButtonHandler cbHandler;
	private DisconnectButtonHandler dbHandler;
	private ResetButtonHandler rbHandler;
	private ProceedButtonHandler pbHandler;
	private Connection connessione = null;
	private JRadioButton SSLOffRadioButton;
	private JRadioButton SSLOnRadioButton;
	private JScrollPane scrollPane;
	private ButtonGroup buttonGroup = new ButtonGroup();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MySQLconnector frame = new MySQLconnector();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MySQLconnector() {

		setLocation(100, 100);
		setTitle("Connect to MySQL database");
		setSize(420, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0));

		cbHandler = new ConnectButtonHandler();
		dbHandler = new DisconnectButtonHandler();
		rbHandler = new ResetButtonHandler();

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(5, 2));

		serverHostnameLabel = new JLabel("Server Hostname: ");
		serverHostnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(serverHostnameLabel);

		serverHostnameTF = new JTextField();
		serverHostnameTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(serverHostnameTF);

		portLabel = new JLabel("Port: ");
		portLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(portLabel);

		portTF = new JTextField();
		portTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(portTF);

		usernameLabel = new JLabel("Username: ");
		usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(usernameLabel);

		usernameTF = new JTextField();
		usernameTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(usernameTF);

		passwordLabel = new JLabel("Password: ");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(passwordLabel);

		passwordTF = new JPasswordField();
		passwordTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(passwordTF);

		databaseLabel = new JLabel("Database: ");
		databaseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(databaseLabel);

		databaseTF = new JTextField();
		databaseTF.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(databaseTF);

		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 3));

		SSLOnRadioButton = new JRadioButton("SSL connection", true);
		buttonGroup.add(SSLOnRadioButton);
		panel.add(SSLOnRadioButton);

		connectButton = new JButton("Connect");
		panel.add(connectButton);

		disconnectButton = new JButton("Disconnect");
		panel.add(disconnectButton);
		disconnectButton.setEnabled(false);

		SSLOffRadioButton = new JRadioButton("Not encrypted");
		buttonGroup.add(SSLOffRadioButton);
		panel.add(SSLOffRadioButton);

		resetButton = new JButton("Reset defaults");
		panel.add(resetButton);

		proceedButton = new JButton("Proceed");
		panel.add(proceedButton);
		proceedButton.setEnabled(false);
		proceedButton.addActionListener(pbHandler);

		resetButton.addActionListener(rbHandler);
		disconnectButton.addActionListener(dbHandler);
		connectButton.addActionListener(cbHandler);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);

		statusBar = new JTextPane();
		scrollPane.setViewportView(statusBar);
		statusBar.setText("MySQL database connection status: off-line.");

		resetDefaults();

	}

	class ProceedButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MySQLConnector_003 frame = new MySQLConnector_003(connessione,
					configSettings);
			frame.setVisible(true);
			System.out.println("Running app...");
		}
	}

	class ConnectButtonHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (connessione != null)
				disconnectButton.doClick();

			String USERNAME = usernameTF.getText();
			String PASSWORD = "";
			for (int i = 1; i <= passwordTF.getPassword().length; i++)
				PASSWORD += passwordTF.getPassword()[i - 1];
			String db = databaseTF.getText();
			String CONNECTION = "jdbc:mysql://" + serverHostnameTF.getText()
					+ ":" + portTF.getText() + "/" + db;
			try {
				connessione = getMySQLConnection(USERNAME, PASSWORD, CONNECTION);

				// info
				String alert = "Using driver \"" + dbClassName
						+ "\" for Java connectivity with MySQL database...\n";
				alert += "\nOpened connection to MySQL database (JDBC + connector/J)...\n";
				alert += "\nConnected user \"" + usernameTF.getText()
						+ "\" to \"mysql://" + serverHostnameTF.getText() + ":"
						+ portTF.getText() + "." + databaseTF.getText()
						+ "\" database...";

				// se la connessione ha avuto successo, fare questo:
				if (connessione != null) {
					statusBar.setText(alert);
					connectButton.setEnabled(false);
					disconnectButton.setEnabled(true);
					proceedButton.setEnabled(true);
					statusBar.setForeground(Color.BLUE);
				}// fine azioni se la connessione è andata a buon fine

				// info

				Statement stmt = connessione.createStatement();
				String sqlQuery = "SHOW TABLES";
				alert = "These tables were found in database \""
						+ databaseTF.getText() + "\":\n\n";
				ResultSet rs = stmt.executeQuery(sqlQuery);
				while (rs.next())
					alert += "- " + rs.getString(1) + "\n";
				JOptionPane.showMessageDialog(
						null,
						alert,
						"Connection to MySQL database \""
								+ databaseTF.getText() + "\" ("
								+ usernameTF.getText() + "@"
								+ serverHostnameTF.getText() + ")",
						JOptionPane.INFORMATION_MESSAGE);

			}

			catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException ex) {
				String alert = null;
				alert += "ExceptionCause: " + ex.getCause();
				alert += "\nSQLException: " + ex.getMessage();
				alert += "\nSQLState: " + ex.getSQLState();
				alert += "\nVendorError: " + ex.getErrorCode();
				statusBar.setForeground(Color.RED);
				statusBar.setText(alert
						+ "\n\nMySQL database connection status: off-line.");
			}
		}
	}

	private static void parseConfigFile() throws FileNotFoundException {
		{
			Scanner inFile = new Scanner(new FileReader(CONFIG_FILE));
			String row;
			while (inFile.hasNext()) {
				row = inFile.nextLine();
				if (row.length() > 0)
					if (row.charAt(0) == '#') {
						continue;

					} else
						parseRow(row);
			}
			inFile.close();
		}
	}

	private static void parseRow(String rowToParse) {
		String newRow = "";
		for (int i = 1; i <= rowToParse.length(); i++) {
			if (rowToParse.charAt(i - 1) == ' ')
				continue;
			else
				newRow += rowToParse.charAt(i - 1);
		}
		rowToParse = newRow;

		if (rowToParse.contains("=")) {
		} else {

			return;
		}
		boolean foundEqual = false;
		String variable = "";
		String value = "";
		for (int i = 1; i <= rowToParse.length(); i++) {
			if (rowToParse.charAt(i - 1) != '=') {
				if (foundEqual == false)
					variable += rowToParse.charAt(i - 1);
				else if (foundEqual == true)
					value += rowToParse.charAt(i - 1);
			} else {
				foundEqual = true;
				continue;
			}
		}
		for (int i = 1; i <= CONFIG_STRUCTURE.length; i++) {
			if (CONFIG_STRUCTURE[i - 1].contentEquals(variable)) {

				configSettings[i - 1] = value;
			}
		}
	}

	private void statusBarAppend(String textToAppend) {
		String temp = statusBar.getText();
		temp += "\n\n" + textToAppend;
		statusBar.setText(temp);
	}

	private void resetDefaults() {

		for (int i = 1; i <= configSettings.length; i++)
			configSettings[i - 1] = null;

		try {
			parseConfigFile();
		} catch (FileNotFoundException e) {
			statusBarAppend(e.toString());
			String alert = "Please check the existence of this file: "
					+ CONFIG_FILE
					+ ". Without proper input, default input values are set to empty strings.";
			statusBarAppend(alert);
		}

		serverHostnameTF.setText(configSettings[0]);
		portTF.setText(configSettings[1]);
		usernameTF.setText(configSettings[2]);
		passwordTF.setText(configSettings[3]);
		databaseTF.setText(configSettings[4]);
		SSLOnRadioButton.setSelected(true);
	}

	private class ResetButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resetDefaults();
		}
	}

	private class DisconnectButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (connessione != null) {
				String alert = "Connection terminated. ";
				try {
					connessione.close();
				} catch (SQLException ex) {
					alert = null;
					alert += "ExceptionCause: " + ex.getCause();
					alert += "\nSQLException: " + ex.getMessage();
					alert += "\nSQLState: " + ex.getSQLState();
					alert += "\nVendorError: " + ex.getErrorCode();
					statusBar.setForeground(Color.RED);
					statusBar
							.setText(alert
									+ "\n\nMySQL database connection status: off-line.");
				}
				connessione = null;
				statusBar.setForeground(Color.BLACK);
				statusBar.setText(alert
						+ "MySQL database connection status: off-line.");
				connectButton.setEnabled(true);
				disconnectButton.setEnabled(false);
				proceedButton.setEnabled(false);
			}
		}
	}

	private Connection getMySQLConnection(String USERNAME, String PASSWORD,
			String CONNECTION) throws ClassNotFoundException, SQLException {

		try {
			Class.forName(dbClassName);

		} catch (ClassNotFoundException e1) {
			JOptionPane
					.showMessageDialog(
							null,
							e1.toString()
									+ "\n\nPlease check your Java environment.\nTo obtain a valid JDBC Driver for MySQL, please visit:\nhttp://www.mysql.com/downloads/connector/j/",
							"Error with JDBC Driver for MySQL (Connector/J)",
							JOptionPane.ERROR_MESSAGE);
		}
		Properties p = new Properties();
		p.put("user", USERNAME);
		p.put("password", PASSWORD);
		return DriverManager.getConnection(CONNECTION, p);
	}
}