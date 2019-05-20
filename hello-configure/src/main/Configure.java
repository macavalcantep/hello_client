package main;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import classes.ManagerDB;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JPasswordField;
import java.awt.Toolkit;

public class Configure extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_ip;
	private JTextField textField_serverUser;
	private JPasswordField textField_serverPass;
	//private String os = System.getProperty("os.name");;
	private static String user = System.getProperty("user.name"); // Get user login
	private File dirDir = new File("/home/"+user+"/hello");
	private File dirDb = new File("/home/"+user+"/hello/hello.db");
	//private String version = "1.0.190424";

	/**
	 * Launch the application.
	 */
	

	// Creates directories
	public void createDir() {
		
		System.out.println(dirDir.toString());

		if (!dirDir.exists()) {
			dirDir.mkdirs();

		}

	}

	// Create DB
	public void createDB() throws IOException {

		ManagerDB.CreateDB();

	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Configure frame = new Configure();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			
		});
	}

	/**
	 * Create the frame.
	 */
	public Configure() {
		setFont(new Font("SansSerif", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Configure.class.getResource("/img/roboto.png")));
		setTitle("Hello - Configurações");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 359);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Set look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {

			e1.printStackTrace();
		}

		JButton btnInstall = new JButton("OK");
		btnInstall.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnInstall.setBackground(Color.WHITE);
		btnInstall.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String iptext = textField_ip.getText();
				String serverUser = textField_serverUser.getText();
				String serverPassword = textField_serverPass.getText();

				

				createDir();

				if (dirDb.exists()) {

					System.out.println("Banco de dados já existe!");

				} else {

					try {
						createDB();
					} catch (IOException e1) {

						e1.printStackTrace();
					}
					try {
						ManagerDB.CreateTable();
					} catch (IOException e1) {

						e1.printStackTrace();
					}

				}

				if (!iptext.equals("")) {

					try {
						ManagerDB.InsertTable(iptext, serverUser, serverPassword);

						JOptionPane.showMessageDialog(null, "Configurações completa com sucesso!");
						System.out.println("IP selecionado: " + iptext);

					} catch (IOException e1) {

						e1.printStackTrace();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Por favor, preencha os campos.");
				}

			}

		});
		btnInstall.setBounds(427, 260, 117, 30);
		contentPane.add(btnInstall);

		JLabel lblNewLabel = new JLabel("IP do servidor:");
		lblNewLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lblNewLabel.setBounds(75, 107, 126, 15);
		contentPane.add(lblNewLabel);

		textField_ip = new JTextField();
		textField_ip.setForeground(Color.DARK_GRAY);
		textField_ip.setBounds(200, 100, 240, 30);
		contentPane.add(textField_ip);
		textField_ip.setColumns(10);

		JTextPane txtpnWelcomeThis = new JTextPane();
		txtpnWelcomeThis.setBackground(Color.WHITE);
		txtpnWelcomeThis.setFont(new Font("SansSerif", Font.PLAIN, 13));
		txtpnWelcomeThis.setEditable(false);
		txtpnWelcomeThis.setText("Preencha os campos com as informações do seu banco de dados.");
		txtpnWelcomeThis.setBounds(116, 50, 355, 40);
		contentPane.add(txtpnWelcomeThis);

		JLabel lblWelcome = new JLabel("Bem vindo !");
		lblWelcome.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblWelcome.setBounds(251, 12, 98, 15);
		contentPane.add(lblWelcome);

		JLabel lbl_serverUser = new JLabel("Usuário do BD:");
		lbl_serverUser.setLabelFor(this);
		lbl_serverUser.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl_serverUser.setBounds(75, 140, 126, 15);
		contentPane.add(lbl_serverUser);

		textField_serverUser = new JTextField();
		textField_serverUser.setForeground(Color.DARK_GRAY);
		textField_serverUser.setBounds(200, 135, 240, 30);
		contentPane.add(textField_serverUser);
		textField_serverUser.setColumns(10);

		JLabel lbl_severPass = new JLabel("Senha do BD:");
		lbl_severPass.setFont(new Font("SansSerif", Font.PLAIN, 13));
		lbl_severPass.setBounds(75, 174, 122, 15);
		contentPane.add(lbl_severPass);

		JLabel lblObrigatoryFields = new JLabel("* obrigatorio");
		lblObrigatoryFields.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblObrigatoryFields.setBounds(23, 270, 149, 15);
		contentPane.add(lblObrigatoryFields);

		JLabel label = new JLabel("*");
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(448, 103, 35, 15);
		contentPane.add(label);

		textField_serverPass = new JPasswordField();
		textField_serverPass.setForeground(Color.DARK_GRAY);
		textField_serverPass.setBounds(200, 170, 240, 30);
		contentPane.add(textField_serverPass);

		JButton btnCancelar = new JButton("Sair");
		btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnCancelar.setForeground(Color.BLACK);
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(298, 260, 117, 30);
		contentPane.add(btnCancelar);

		JLabel lblVersion = new JLabel("Versão");
		lblVersion.setFont(new Font("SansSerif", Font.PLAIN, 11));
		lblVersion.setBounds(23, 294, 180, 15);
		contentPane.add(lblVersion);
		lblVersion.setText("Linux - Versão: 1.0.190520");
	}
}
