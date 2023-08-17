package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.DAO;

public class Principal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblAgenda;
	private JLabel lblData;
	private JLabel lblPrinter;
	private JButton btnClients;
	private JButton btnService;
	//Está Label será alterada pela tela de Login (public)
	public JLabel lblUsuario;
	public JButton btnUsers;
	public JButton btnRelatorio;
	public JLabel lblCargo;
	public JPanel panelRodape;
	private JButton btnProdutos;
	private JButton btnFornecedor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setTitle("Ordem de Serviços");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 624);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.activeCaption);
		panelRodape.setBounds(0, 554, 624, 31);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
				lblData = new JLabel("");
				lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
				lblData.setHorizontalAlignment(SwingConstants.CENTER);
				lblData.setBounds(376, 5, 212, 24);
				panelRodape.add(lblData);

		lblStatus = new JLabel("");
		lblStatus.setBounds(590, 5, 24, 24);
		lblStatus.setBorder(null);
		panelRodape.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/databaseoff.png")));
		
				JButton btnAbout = new JButton("");
				btnAbout.setContentAreaFilled(false);
				btnAbout.setBounds(0, 0, 33, 33);
				panelRodape.add(btnAbout);
				btnAbout.setBorderPainted(false);
				btnAbout.setToolTipText("Sobre");
				btnAbout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnAbout.setFocusable(false);
				btnAbout.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Sobre sobre = new Sobre();
						sobre.setVisible(true);
					}
				});
				btnAbout.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
				
				lblCargo = new JLabel("perfil");
				lblCargo.setFont(new Font("Tahoma", Font.BOLD, 12));
				lblCargo.setBounds(43, 5, 42, 24);
				panelRodape.add(lblCargo);
				
				lblUsuario = new JLabel("nome do usuario");
				lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 12));
				lblUsuario.setBounds(83, 5, 212, 24);
				panelRodape.add(lblUsuario);

		btnUsers = new JButton("");
		btnUsers.setEnabled(false);
		btnUsers.setFocusPainted(false);
		btnUsers.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnUsers.setToolTipText("Buscar Usuarios");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuario = new Usuarios();
				usuario.setVisible(true);
			}
		});
		btnUsers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsers.setIcon(new ImageIcon(Principal.class.getResource("/img/Usuarios.png")));
		btnUsers.setBounds(10, 237, 149, 132);
		contentPane.add(btnUsers);
		
		btnRelatorio = new JButton("");
		btnRelatorio.setEnabled(false);
		btnRelatorio.setFocusPainted(false);
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorio = new Relatorios();
				relatorio.setVisible(true);
			}
		});
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/clipboard.png")));
		btnRelatorio.setToolTipText("Relatórios");
		btnRelatorio.setBounds(238, 411, 149, 132);
		contentPane.add(btnRelatorio);
	
		
		
		btnClients = new JButton("");
		btnClients.setFocusPainted(false);
		btnClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);
			}
		});
		btnClients.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClients.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClients.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClients.setToolTipText("Buscar Clientes");
		btnClients.setBounds(10, 411, 149, 132);
		contentPane.add(btnClients);
		
		btnService = new JButton("");
		btnService.setFocusPainted(false);
		btnService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnService.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnService.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnService.setIcon(new ImageIcon(Principal.class.getResource("/img/maintence.png")));
		btnService.setToolTipText("Serviços");
		btnService.setBounds(238, 237, 149, 132);
		contentPane.add(btnService);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setBounds(0, 0, 654, 31);
		contentPane.add(panel_1);
		setLocationRelativeTo(null);
		
				lblAgenda = new JLabel("3DPrintTechGenius");
				lblAgenda.setBounds(10, 5, 203, 24);
				panel_1.add(lblAgenda);
				lblAgenda.setFont(new Font("Bookman Old Style", Font.BOLD, 20));
				
				lblPrinter = new JLabel("");
				lblPrinter.setHorizontalAlignment(SwingConstants.CENTER);
				lblPrinter.setIcon(new ImageIcon(Principal.class.getResource("/img/printer3d.png")));
				lblPrinter.setBounds(221, 60, 166, 142);
				contentPane.add(lblPrinter);
				
				btnProdutos = new JButton("");
				btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnProdutos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Produtos produtos = new Produtos();
						produtos.setVisible(true);
					}
				});
				btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/Produtos.png")));
				btnProdutos.setToolTipText("Produtos");
				btnProdutos.setFocusPainted(false);
				btnProdutos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				btnProdutos.setBounds(465, 411, 149, 132);
				contentPane.add(btnProdutos);
				
				btnFornecedor = new JButton("");
				btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnFornecedor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Fornecedores fornecedores = new Fornecedores();
						fornecedores.setVisible(true);;
					}
				});
				btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/Fornecedores.png")));
				btnFornecedor.setToolTipText("Fornecedores");
				btnFornecedor.setFocusPainted(false);
				btnFornecedor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				btnFornecedor.setBounds(465, 237, 149, 132);
				contentPane.add(btnFornecedor);
	}

	private void status() {
		try {
			con = dao.conectar();
			if (con == null) {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/databaseoff.png")));
			} else {
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/databaseon.png")));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}
