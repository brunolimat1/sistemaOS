package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

public class Fornecedores extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JTextField txtRazaoSocial;
	private JTextField txtLogradouro;
	private JFormattedTextField txtTelefone;
	private JFormattedTextField txtCep;
	private JButton btnCadastrar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTextField txtID;
	private JTextField txtComplemento;
	private JLabel lblNumero;
	private JLabel lblBairro;
	private JTextField txtBairro;
	private JLabel lblCidade;
	private JTextField txtCidade;
	private JLabel lblUF;
	private JComboBox<?> cboUf;

	private JTextField txtNumero;
	private JScrollPane scrollPane_1;
	@SuppressWarnings("rawtypes")
	private JList listFornecedores;
	private JFormattedTextField txtCnpj;
	private JTextField txtNomeFantasia;
	private JTextField txtEmail;
	private JTextField txtVendedor;
	private JTextField txtSite;
	private JTextField txtIE;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedores dialog = new Fornecedores();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Fornecedores() {
		setModal(true);
		setTitle("Fornecedores");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedores.class.getResource("/img/Fornecedores.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane_1.setVisible(false);
			}
		});
		setBounds(100, 100, 800, 600);

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(109, 126, 373, 19);
		scrollPane_1.setVisible(false);
		contentPanel.setLayout(null);
		scrollPane_1.setBorder(null);
		contentPanel.add(scrollPane_1);

		listFornecedores = new JList();
		scrollPane_1.setViewportView(listFornecedores);
		listFornecedores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		listFornecedores.setBorder(null);

		JLabel lblTitulo = new JLabel("Cadastro de Fornecedores");
		lblTitulo.setBounds(289, 11, 193, 19);
		lblTitulo.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		contentPanel.add(lblTitulo);

		JLabel lblRazaoSocial = new JLabel("Razão Social:");
		lblRazaoSocial.setBounds(10, 105, 89, 19);
		lblRazaoSocial.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblRazaoSocial);

		txtRazaoSocial = new JTextField();
		txtRazaoSocial.setBounds(109, 106, 373, 20);
		txtRazaoSocial.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		contentPanel.add(txtRazaoSocial);
		txtRazaoSocial.setColumns(10);
		txtRazaoSocial.setDocument(new Validador(50));

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(496, 105, 64, 19);
		lblTelefone.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblTelefone);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setBounds(419, 276, 38, 19);
		lblCep.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblCep);

		JLabel lblLogradouro = new JLabel("Logradouro:");
		lblLogradouro.setBounds(12, 274, 89, 19);
		lblLogradouro.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblLogradouro);

		txtLogradouro = new JTextField();
		txtLogradouro.setBounds(100, 275, 315, 20);
		txtLogradouro.setColumns(10);
		contentPanel.add(txtLogradouro);
		txtLogradouro.setDocument(new Validador(50));

		btnCadastrar = new JButton("");
		btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrar.setBounds(10, 404, 64, 64);
		btnCadastrar.setFocusPainted(false);
		btnCadastrar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/add.png")));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBounds(10, 486, 64, 64);
		btnLimpar.setFocusPainted(false);
		btnLimpar.setEnabled(false);
		btnLimpar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		contentPanel.add(btnLimpar);
		contentPanel.add(btnCadastrar);
		getRootPane().setDefaultButton(btnCadastrar);

		txtTelefone = new JFormattedTextField();
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtTelefone.setBounds(570, 105, 204, 19);
		contentPanel.add(txtTelefone);
		txtTelefone.setDocument(new Validador(15));

		txtCep = new JFormattedTextField();
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtCep.setBounds(458, 276, 204, 19);
		contentPanel.add(txtCep);
		txtCep.setDocument(new Validador(10));

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBounds(710, 404, 64, 64);
		btnEditar.setFocusPainted(false);
		btnEditar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/edit.png")));
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBounds(710, 486, 64, 64);
		btnExcluir.setFocusPainted(false);
		btnExcluir.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/trash.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		contentPanel.add(btnExcluir);

		JLabel lblidFornecedor = new JLabel("ID:");
		lblidFornecedor.setBounds(10, 63, 50, 19);
		lblidFornecedor.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblidFornecedor);

		txtID = new JTextField();
		txtID.setBounds(35, 64, 64, 20);
		txtID.setEditable(false);
		txtID.setColumns(10);
		contentPanel.add(txtID);
		txtID.setDocument(new Validador(10));

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(10, 315, 102, 19);
		lblComplemento.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setBounds(111, 316, 304, 20);
		txtComplemento.setColumns(10);
		contentPanel.add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(640, 227, 74, 19);
		lblNumero.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblNumero);

		lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(431, 317, 74, 19);
		lblBairro.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setBounds(485, 316, 289, 20);
		txtBairro.setColumns(10);
		contentPanel.add(txtBairro);
		txtBairro.setDocument(new Validador(30));

		lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(10, 358, 102, 19);
		lblCidade.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setBounds(67, 359, 348, 20);
		txtCidade.setColumns(10);
		contentPanel.add(txtCidade);
		txtCidade.setDocument(new Validador(30));

		lblUF = new JLabel("UF:");
		lblUF.setBounds(442, 360, 38, 19);
		lblUF.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblUF);

		cboUf = new JComboBox();
		cboUf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUf.setBounds(484, 355, 290, 25);
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		contentPanel.add(cboUf);

		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarCep.setBounds(672, 269, 102, 28);
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		contentPanel.add(btnBuscarCep);

		txtNumero = new JTextField();
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtNumero.setBounds(710, 228, 64, 20);
		txtNumero.setColumns(10);
		contentPanel.add(txtNumero);
		txtNumero.setDocument(new Validador(10));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(329, 422, 128, 128);
		lblNewLabel_1.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/Fornecedores.png")));
		lblNewLabel_1.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		contentPanel.add(lblNewLabel_1);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setBounds(513, 188, 64, 19);
		lblCnpj.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		contentPanel.add(lblCnpj);

		txtCnpj = new JFormattedTextField();
		txtCnpj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtCnpj.setText("");
		txtCnpj.setBounds(570, 189, 204, 19);
		contentPanel.add(txtCnpj);
		txtCnpj.setDocument(new Validador(20));

		JLabel lblNomeFantasia = new JLabel("Nome Fantasia:");
		lblNomeFantasia.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblNomeFantasia.setBounds(10, 146, 113, 19);
		contentPanel.add(lblNomeFantasia);

		txtNomeFantasia = new JTextField();
		txtNomeFantasia.setColumns(10);
		txtNomeFantasia.setBounds(119, 147, 363, 20);
		contentPanel.add(txtNomeFantasia);
		txtNomeFantasia.setDocument(new Validador(50));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblEmail.setBounds(10, 188, 40, 19);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(60, 189, 422, 20);
		contentPanel.add(txtEmail);
		txtEmail.setDocument(new Validador(50));

		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblVendedor.setBounds(496, 146, 74, 19);
		contentPanel.add(lblVendedor);

		txtVendedor = new JTextField();
		txtVendedor.setColumns(10);
		txtVendedor.setBounds(570, 147, 204, 20);
		contentPanel.add(txtVendedor);
		txtVendedor.setDocument(new Validador(20));

		JLabel lblSite = new JLabel("Site:");
		lblSite.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblSite.setBounds(10, 227, 40, 19);
		contentPanel.add(lblSite);

		txtSite = new JTextField();
		txtSite.setColumns(10);
		txtSite.setBounds(45, 228, 412, 20);
		contentPanel.add(txtSite);
		txtSite.setDocument(new Validador(50));

		JLabel lblie = new JLabel("IE:");
		lblie.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblie.setBounds(479, 227, 38, 19);
		contentPanel.add(lblie);

		txtIE = new JTextField();
		txtIE.setColumns(10);
		txtIE.setBounds(502, 228, 128, 20);
		contentPanel.add(txtIE);
		txtIE.setDocument(new Validador(20));

		setLocationRelativeTo(null);

	}

	private void limpar() {
		txtID.setText(null);
		txtRazaoSocial.setText(null);
		txtNomeFantasia.setText(null);
		txtTelefone.setText(null);
		txtVendedor.setText(null);
		txtEmail.setText(null);
		txtSite.setText(null);
		txtCnpj.setText(null);
		txtIE.setText(null);
		txtCep.setText(null);
		txtLogradouro.setText(null);
		txtNumero.setText(null);
		txtComplemento.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		cboUf.setSelectedItem(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnLimpar.setEnabled(false);
	}

	private void cadastrar() {
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Razão Social do Fornecedor!");
			txtRazaoSocial.requestFocus();
		} else if (txtNomeFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor!");
			txtNomeFantasia.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Fornecedor!");
			txtTelefone.requestFocus();
		} else if (txtVendedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Vendedor!");
			txtVendedor.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor!");
			txtEmail.requestFocus();
		} else if (txtSite.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Site do Fornecedor!");
			txtSite.requestFocus();
		} else if (txtCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ do Fornecedor!");
			txtCnpj.requestFocus();
		} else if (txtIE.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o IE do Fornecedor!");
			txtIE.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor!");
			txtCep.requestFocus();
		} else if (txtLogradouro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Logradouro do Fornecedor!");
			txtLogradouro.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número de Residência do Fornecedor!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Fornecedor!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Fornecedor!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade Federal(UF)!");
			cboUf.requestFocus();
		} else {
			String create = "insert into fornecedores (razao, fantasia, fone, vendedor, email, site, cnpj, ie, cep, endereco, numero, complemento, bairro, cidade, uf) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtNomeFantasia.getText());
				pst.setString(3, txtTelefone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCnpj.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCep.getText());
				pst.setString(10, txtLogradouro.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor Cadastrado!");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editar() {
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Razão Social do Fornecedor!");
			txtRazaoSocial.requestFocus();
		} else if (txtNomeFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor!");
			txtNomeFantasia.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Fornecedor!");
			txtTelefone.requestFocus();
		} else if (txtVendedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Vendedor!");
			txtVendedor.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor!");
			txtEmail.requestFocus();
		} else if (txtSite.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Site do Fornecedor!");
			txtSite.requestFocus();
		} else if (txtCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ do Fornecedor!");
			txtCnpj.requestFocus();
		} else if (txtIE.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o IE do Fornecedor!");
			txtIE.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor!");
			txtCep.requestFocus();
		} else if (txtLogradouro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Logradouro do Fornecedor!");
			txtLogradouro.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número de Residência do Fornecedor!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Fornecedor!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Fornecedor!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade Federal(UF)!");
			cboUf.requestFocus();
		} else {
			String update = "update fornecedores set razao=?,fantasia=?,fone=?,vendedor=?,email=?,site=?,cnpj=?,ie=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=? where idfor=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(16, txtID.getText());
				pst.setString(1, txtRazaoSocial.getText());
				pst.setString(2, txtNomeFantasia.getText());
				pst.setString(3, txtTelefone.getText());
				pst.setString(4, txtVendedor.getText());
				pst.setString(5, txtEmail.getText());
				pst.setString(6, txtSite.getText());
				pst.setString(7, txtCnpj.getText());
				pst.setString(8, txtIE.getText());
				pst.setString(9, txtCep.getText());
				pst.setString(10, txtLogradouro.getText());
				pst.setString(11, txtNumero.getText());
				pst.setString(12, txtComplemento.getText());
				pst.setString(13, txtBairro.getText());
				pst.setString(14, txtCidade.getText());
				pst.setString(15, cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Fornecedor Editados com Sucesso.");
				limpar();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um fornecedor com este CNPJ cadastrado!");
				txtCnpj.setText(null);
				txtCnpj.requestFocus();
			} catch (Exception e) {
				System.out.println(e);

			}
		}
	}

	private void excluir() {
		if (txtRazaoSocial.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Razão Social do Fornecedor!");
			txtRazaoSocial.requestFocus();
		} else if (txtNomeFantasia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome Fantasia do Fornecedor!");
			txtNomeFantasia.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Fornecedor!");
			txtTelefone.requestFocus();
		} else if (txtVendedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Vendedor!");
			txtVendedor.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Email do Fornecedor!");
			txtEmail.requestFocus();
		} else if (txtSite.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Site do Fornecedor!");
			txtSite.requestFocus();
		} else if (txtCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CNPJ do Fornecedor!");
			txtCnpj.requestFocus();
		} else if (txtIE.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o IE do Fornecedor!");
			txtIE.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor!");
			txtCep.requestFocus();
		} else if (txtLogradouro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Logradouro do Fornecedor!");
			txtLogradouro.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número de Residência do Fornecedor!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Fornecedor!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Fornecedor!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade Federal(UF)!");
			cboUf.requestFocus();
		} else {
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor?", "Atenção!",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				String delete = "delete from fornecedores where idfor=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtID.getText());
					pst.executeUpdate();
					limpar();
					con.close();
					JOptionPane.showMessageDialog(null, "fornecedor Excluído!");
				} catch (java.sql.SQLIntegrityConstraintViolationException se) {
					JOptionPane.showInternalMessageDialog(null, "Este fornecedor tem um produto cadastrado!");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void listarFornecedores() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listFornecedores.setModel(modelo);
		String readLista = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'"
				+ "order by razao";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane_1.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtRazaoSocial.getText().isEmpty()) {
					scrollPane_1.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listFornecedores.getSelectedIndex();
		if (linha >= 0) {
			String readListaCliente = "select * from fornecedores where razao like '" + txtRazaoSocial.getText() + "%'"
					+ "order by razao limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCliente);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane_1.setVisible(false);
					txtID.setText(rs.getString(1));
					txtRazaoSocial.setText(rs.getString(2));
					txtNomeFantasia.setText(rs.getString(3));
					txtTelefone.setText(rs.getString(4));
					txtVendedor.setText(rs.getString(5));
					txtEmail.setText(rs.getString(6));
					txtSite.setText(rs.getString(7));
					txtCnpj.setText(rs.getString(8));
					txtIE.setText(rs.getString(9));
					txtCep.setText(rs.getString(10));
					txtLogradouro.setText(rs.getString(11));
					txtNumero.setText(rs.getString(12));
					txtComplemento.setText(rs.getString(13));
					txtBairro.setText(rs.getString(14));
					txtCidade.setText(rs.getString(15));
					cboUf.setSelectedItem(rs.getString(16));
					btnCadastrar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnLimpar.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPane_1.setVisible(false);
		}
	}

	private void buscarCep() {
		if (txtCep.getText().equals("     -   ")) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Fornecedor!");
			txtCep.requestFocus();
		} else {
			String logradouro = "";
			String tipoLogradouro = "";
			String resultado = null;
			String cep = txtCep.getText();
			try {
				URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
				SAXReader xml = new SAXReader();
				Document documento = xml.read(url);
				Element root = documento.getRootElement();
				for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
					Element element = it.next();
					if (element.getQualifiedName().equals("cidade")) {
						txtCidade.setText(element.getText());
					}
					if (element.getQualifiedName().equals("bairro")) {
						txtBairro.setText(element.getText());
					}
					if (element.getQualifiedName().equals("uf")) {
						cboUf.setSelectedItem(element.getText());
					}
					if (element.getQualifiedName().equals("logradouro")) {
						logradouro = element.getText();
					}
					if (element.getQualifiedName().equals("resultado")) {
						resultado = element.getText();
						if (resultado.equals("1")) {
						} else {
							JOptionPane.showMessageDialog(null, "CEP não encontrado");
						}
					}

				}
				txtLogradouro.setText(tipoLogradouro + " " + logradouro);
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	public void OnlyNumber(KeyEvent e) {
		char c = e.getKeyChar();
		if (Character.isLetter(c)) {
			e.consume();
		}
	}

}
