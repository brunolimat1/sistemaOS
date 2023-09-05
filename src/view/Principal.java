package view;

import java.awt.Component;
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
import javax.swing.border.EtchedBorder;

import model.DAO;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private JPanel PainelPrincipal;
	private JLabel lblStatus;
	private JLabel lblAgenda;
	private JLabel lblData;
	private JLabel lblPrinter;
	private JButton btnClients;
	private JButton btnService;
	public JButton btnUsers;
	public JButton btnRelatorio;
	public JLabel lblCargo;
	public JPanel panelRodape;
	private JButton btnProdutos;
	private JButton btnFornecedor;
	public JLabel lblUsuario;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
					frame.setSize(800, 600);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Principal() {
		setResizable(false);
		setLocationByPlatform(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
				setarData();
			}
		});
		setTitle("Ordem de Serviços");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		PainelPrincipal = new JPanel();
		PainelPrincipal.setBorder(null);

		setContentPane(PainelPrincipal);

		btnService = new JButton("");
		btnService.setBounds(265, 185, 264, 164);
		btnService.setFocusPainted(false);
		btnService.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});

		btnUsers = new JButton("");
		btnUsers.setBounds(12, 185, 247, 164);
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

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 784, 32);
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setLayout(null);

		lblAgenda = new JLabel("3DPrintTechGenius");
		lblAgenda.setBounds(10, 5, 203, 24);
		panel_1.add(lblAgenda);
		lblAgenda.setFont(new Font("Bookman Old Style", Font.BOLD, 20));

		lblPrinter = new JLabel("");
		lblPrinter.setBounds(279, 49, 222, 128);
		lblPrinter.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrinter.setIcon(new ImageIcon(Principal.class.getResource("/img/printer3d.png")));
		btnUsers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsers.setIcon(new ImageIcon(Principal.class.getResource("/img/Usuarios.png")));
		btnService.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnService.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnService.setIcon(new ImageIcon(Principal.class.getResource("/img/maintence.png")));
		btnService.setToolTipText("Serviços");

		btnProdutos = new JButton("");
		btnProdutos.setBounds(535, 360, 234, 153);
		btnProdutos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
			}
		});

		btnRelatorio = new JButton("");
		btnRelatorio.setBounds(265, 360, 264, 153);
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

		btnClients = new JButton("");
		btnClients.setBounds(15, 360, 244, 153);
		btnClients.setFocusPainted(false);
		btnClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);
			}
		});

		btnFornecedor = new JButton("");
		btnFornecedor.setBounds(535, 185, 237, 164);
		btnFornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
				;
			}
		});
		btnFornecedor.setIcon(new ImageIcon(Principal.class.getResource("/img/Fornecedores.png")));
		btnFornecedor.setToolTipText("Fornecedores");
		btnFornecedor.setFocusPainted(false);
		btnFornecedor.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClients.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClients.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClients.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes.png")));
		btnClients.setToolTipText("Buscar Clientes");
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/clipboard.png")));
		btnRelatorio.setToolTipText("Relatórios");
		btnProdutos.setIcon(new ImageIcon(Principal.class.getResource("/img/Produtos.png")));
		btnProdutos.setToolTipText("Produtos");
		btnProdutos.setFocusPainted(false);
		btnProdutos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		panelRodape = new JPanel();
		panelRodape.setBounds(0, 529, 784, 32);
		panelRodape.setBackground(SystemColor.activeCaption);
		panelRodape.setLayout(null);

		JButton btnAbout = new JButton("");
		btnAbout.setBounds(0, 0, 52, 33);
		btnAbout.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAbout.setContentAreaFilled(false);
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
		lblCargo.setBounds(62, 0, 52, 33);
		lblCargo.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelRodape.add(lblCargo);

		lblData = new JLabel("");
		lblData.setBounds(494, 0, 246, 33);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		panelRodape.add(lblData);

		lblStatus = new JLabel("");
		lblStatus.setBounds(750, 0, 24, 33);
		lblStatus.setBorder(null);
		panelRodape.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/databaseoff.png")));
		PainelPrincipal.setLayout(null);
		PainelPrincipal.add(panel_1);
		PainelPrincipal.add(lblPrinter);
		PainelPrincipal.add(btnUsers);
		PainelPrincipal.add(btnService);
		PainelPrincipal.add(btnFornecedor);
		PainelPrincipal.add(btnClients);
		PainelPrincipal.add(btnRelatorio);
		PainelPrincipal.add(btnProdutos);
		PainelPrincipal.add(panelRodape);

		lblUsuario = new JLabel("");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblUsuario.setBounds(106, 0, 350, 33);
		panelRodape.add(lblUsuario);
		setLocationRelativeTo(null);
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
