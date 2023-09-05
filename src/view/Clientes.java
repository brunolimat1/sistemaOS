package view;

import java.awt.BorderLayout;
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
import java.awt.Cursor;

public class Clientes extends JDialog {

	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JTextField txtNome;
	private JTextField txtEndereco;
	private JFormattedTextField txtTelefone;
	private JFormattedTextField txtCep;
	private JButton btnCadastrar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTextField txtID;
	private JTextField txtComplemento;
	private JLabel lblNmero;
	private JLabel lblBairro;
	private JTextField txtBairro;
	private JLabel lblCidade;
	private JTextField txtCidade;
	private JLabel lblUF;
	private JComboBox<?> cboUf;
	private JTextField txtNumero;
	private JScrollPane scrollPane_1;
	private JList<String> listClientes;
	private JFormattedTextField txtCpf;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					dialog.setSize(800, 600);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Clientes() {
		setResizable(false);
		setLocationByPlatform(true);
		setModal(true);
		setTitle("Clientes");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/clientes.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane_1.setVisible(false);
			}
		});
		setBounds(100, 100, 800, 600);

		JPanel contentPanel = new JPanel();
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setVisible(false);
		scrollPane_1.setBorder(null);
		scrollPane_1.setBounds(60, 158, 380, 19);
		contentPanel.add(scrollPane_1);

		listClientes = new JList<String>();
		scrollPane_1.setViewportView(listClientes);
		listClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		listClientes.setBorder(null);

		JLabel lblNewLabel = new JLabel("Cadastro de Clientes");
		lblNewLabel.setBounds(289, 11, 151, 19);
		lblNewLabel.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		contentPanel.add(lblNewLabel);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblNome.setBounds(10, 141, 64, 19);
		contentPanel.add(lblNome);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(60, 141, 380, 20);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblTelefone.setBounds(496, 141, 64, 19);
		contentPanel.add(lblTelefone);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblCep.setBounds(430, 191, 38, 19);
		contentPanel.add(lblCep);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblEndereco.setBounds(10, 237, 74, 19);
		contentPanel.add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(84, 238, 428, 20);
		contentPanel.add(txtEndereco);
		txtEndereco.setDocument(new Validador(50));

		btnCadastrar = new JButton("");
		btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrar.setFocusPainted(false);
		btnCadastrar.setIcon(new ImageIcon(Clientes.class.getResource("/img/add.png")));
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setFocusPainted(false);
		btnLimpar.setEnabled(false);
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setBounds(10, 486, 64, 64);
		contentPanel.add(btnLimpar);
		btnCadastrar.setBounds(10, 404, 64, 64);
		contentPanel.add(btnCadastrar);
		getRootPane().setDefaultButton(btnCadastrar);

		txtTelefone = new JFormattedTextField();
		txtTelefone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtTelefone.setBounds(570, 142, 204, 19);
		contentPanel.add(txtTelefone);
		txtTelefone.setDocument(new Validador(15));

		txtCep = new JFormattedTextField();
		txtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtCep.setBounds(469, 192, 193, 19);
		contentPanel.add(txtCep);
		txtCep.setDocument(new Validador(10));

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setFocusPainted(false);
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/edit.png")));
		btnEditar.setEnabled(false);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(710, 404, 64, 64);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setFocusPainted(false);
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/trash.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(710, 486, 64, 64);
		contentPanel.add(btnExcluir);

		JLabel lblidCliente = new JLabel("ID:");
		lblidCliente.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblidCliente.setBounds(10, 89, 50, 19);
		contentPanel.add(lblidCliente);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(35, 90, 64, 20);
		contentPanel.add(txtID);
		txtID.setDocument(new Validador(10));

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblComplemento.setBounds(10, 283, 102, 19);
		contentPanel.add(lblComplemento);

		txtComplemento = new JTextField();
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(111, 284, 270, 20);
		contentPanel.add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));

		lblNmero = new JLabel("Número:");
		lblNmero.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblNmero.setBounds(569, 240, 74, 19);
		contentPanel.add(lblNmero);

		lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblBairro.setBounds(431, 287, 74, 19);
		contentPanel.add(lblBairro);

		txtBairro = new JTextField();
		txtBairro.setColumns(10);
		txtBairro.setBounds(485, 286, 289, 20);
		contentPanel.add(txtBairro);
		txtBairro.setDocument(new Validador(30));

		lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblCidade.setBounds(10, 330, 102, 19);
		contentPanel.add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setColumns(10);
		txtCidade.setBounds(72, 331, 330, 20);
		contentPanel.add(txtCidade);
		txtCidade.setDocument(new Validador(30));

		lblUF = new JLabel("UF:");
		lblUF.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblUF.setBounds(442, 333, 38, 19);
		contentPanel.add(lblUF);

		cboUf = new JComboBox();
		cboUf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		cboUf.setBounds(484, 330, 290, 25);
		contentPanel.add(cboUf);

		JButton btnBuscarCep = new JButton("Buscar CEP");
		btnBuscarCep.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(672, 188, 102, 28);
		contentPanel.add(btnBuscarCep);

		txtNumero = new JTextField();
		txtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtNumero.setColumns(10);
		txtNumero.setBounds(639, 241, 135, 20);
		contentPanel.add(txtNumero);
		txtNumero.setDocument(new Validador(10));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Clientes.class.getResource("/img/clientes.png")));
		lblNewLabel_1.setFont(new Font("Swis721 Lt BT", Font.BOLD, 15));
		lblNewLabel_1.setBounds(331, 411, 128, 128);
		contentPanel.add(lblNewLabel_1);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Swis721 Lt BT", Font.PLAIN, 15));
		lblCpf.setBounds(13, 188, 64, 19);
		contentPanel.add(lblCpf);

		txtCpf = new JFormattedTextField();
		txtCpf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtCpf.setBounds(63, 189, 314, 19);
		contentPanel.add(txtCpf);
		txtCpf.setDocument(new Validador(50));

		setLocationRelativeTo(null);

	}

	private void limpar() {
		txtID.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtCpf.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
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
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente!");
			txtNome.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente!");
			txtTelefone.requestFocus();
		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do Cliente!");
			txtCpf.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente!");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número de Residência do Cliente!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Cliente!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Cliente!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade Federal(UF)!");
			cboUf.requestFocus();
		} else {
			String create = "insert into clientes (nome, telefone, cpf, cep, endereco, numero, complemento, bairro, cidade, uf) values (?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtTelefone.getText());
				pst.setString(3, txtCpf.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Cliente Cadastrado!");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do Cliente!");
			txtNome.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente!");
			txtTelefone.requestFocus();
		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF do Cliente!");
			txtCpf.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente!");
			txtCep.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente!");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número de Residência do Cliente!");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Bairro do Cliente!");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Cidade do Cliente!");
			txtCidade.requestFocus();
		} else if (cboUf.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade Federal(UF)!");
			cboUf.requestFocus();
		} else {
			String update = "update clientes set nome=?,telefone=?,cpf=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=? where idcli=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(11, txtID.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtTelefone.getText());
				pst.setString(3, txtCpf.getText());
				pst.setString(4, txtCep.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCidade.getText());
				pst.setString(10, cboUf.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do Cliente Editados com Sucesso.");
				limpar();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void excluir() {
		if (txtID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Busque o ID do Cliente!");
			txtID.requestFocus();
		} else {
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuário?", "Atenção!",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				String delete = "delete from clientes where idcli=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtID.getText());
					pst.executeUpdate();
					limpar();
					con.close();
					JOptionPane.showMessageDialog(null, "Cliente Excluído!");
				} catch (java.sql.SQLIntegrityConstraintViolationException se) {
					JOptionPane.showInternalMessageDialog(null, "Este Cliente tem um Serviço OS pendente!");
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

	}

	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listClientes.setModel(modelo);
		String readLista = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane_1.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane_1.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {
			String readListaCliente = "select * from clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCliente);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane_1.setVisible(false);
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtTelefone.setText(rs.getString(3));
					txtCpf.setText(rs.getString(4));
					txtCep.setText(rs.getString(5));
					txtEndereco.setText(rs.getString(6));
					txtNumero.setText(rs.getString(7));
					txtComplemento.setText(rs.getString(8));
					txtBairro.setText(rs.getString(9));
					txtCidade.setText(rs.getString(10));
					cboUf.setSelectedItem(rs.getString(11));
					btnCadastrar.setEnabled(true);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnLimpar.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuário inexistente");
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
		if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente!");
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
				txtEndereco.setText(tipoLogradouro + " " + logradouro);
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
