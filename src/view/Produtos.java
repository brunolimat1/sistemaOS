package view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class Produtos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private FileInputStream fis;
	private int tamanho;
	private static final long serialVersionUID = 1L;
	private JTextField txtbarcode;
	private JTextField txtcodigo;
	private JTextField txtproduto;
	private JTextField txtlote;
	private JTextField txtfabricante;
	private JTextField txtestoque;
	private JTextField txtestoquemin;
	private JTextField txtlocalarm;
	private JTextField txtfornecedor;
	private JTextField txtidfor;
	private JTextField txtcusto;
	private JTextField txtlucro;
	private JButton btnCadastrar;
	private JButton btnEditar;
	private JButton btnDeletar;
	private JButton btnLimpar;
	private JButton btnbuscarcod;
	private JButton btnbuscarfornecedor;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUnidade;
	private JLabel lblfoto;
	private JScrollPane ScpDesc;
	private JTextArea txtDescricao;
	private JDateChooser dateVal;
	private JDateChooser dateEnt;
	@SuppressWarnings("rawtypes")
	private JList listID;
	private JScrollPane scrollPaneFor;
	private JCheckBox checkalt;
	private JScrollPane scrollPaneProd;
	@SuppressWarnings("rawtypes")
	private JList listProd;

	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Produtos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneFor.setVisible(false);
				scrollPaneProd.setVisible(false);
			}
		});
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPaneProd = new JScrollPane();
		scrollPaneProd.setBorder(null);
		scrollPaneProd.setVisible(false);
		scrollPaneProd.setBounds(76, 161, 268, 23);
		getContentPane().add(scrollPaneProd);

		listProd = new JList();
		listProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutosLista();
			}
		});
		scrollPaneProd.setViewportView(listProd);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Produtos.class.getResource("/img/8541700_barcode_code_icon.png")));
		lblNewLabel.setBounds(22, 26, 48, 48);
		getContentPane().add(lblNewLabel);

		txtbarcode = new JTextField();
		txtbarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarProdutoBarcode();
				}
			}
		});
		txtbarcode.setBounds(76, 39, 268, 20);
		getContentPane().add(txtbarcode);
		txtbarcode.setColumns(10);
		txtbarcode.setDocument(new Validador(50));

		JLabel lblNewLabel_1 = new JLabel("Código");
		lblNewLabel_1.setBounds(22, 90, 62, 14);
		getContentPane().add(lblNewLabel_1);

		txtcodigo = new JTextField();
		txtcodigo.setEditable(false);
		txtcodigo.setBounds(76, 87, 91, 20);
		getContentPane().add(txtcodigo);
		txtcodigo.setColumns(10);

		btnbuscarcod = new JButton("Pesquisar");
		btnbuscarcod.setFocusPainted(false);
		btnbuscarcod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProduto();
			}
		});
		btnbuscarcod.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnbuscarcod.setBounds(195, 86, 134, 23);
		getContentPane().add(btnbuscarcod);

		JLabel lblNewLabel_2 = new JLabel("Produto");
		lblNewLabel_2.setBounds(22, 143, 52, 14);
		getContentPane().add(lblNewLabel_2);

		txtproduto = new JTextField();
		txtproduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarProdutos();
			}
		});
		txtproduto.setBounds(76, 143, 268, 20);
		getContentPane().add(txtproduto);
		txtproduto.setColumns(10);
		txtproduto.setDocument(new Validador(50));

		JLabel lblNewLabel_3 = new JLabel("Lote");
		lblNewLabel_3.setBounds(22, 299, 44, 14);
		getContentPane().add(lblNewLabel_3);

		txtlote = new JTextField();
		txtlote.setBounds(58, 297, 114, 17);
		getContentPane().add(txtlote);
		txtlote.setColumns(10);
		txtlote.setDocument(new Validador(20));

		JLabel lblNewLabel_4 = new JLabel("Descrição");
		lblNewLabel_4.setBounds(22, 206, 62, 14);
		getContentPane().add(lblNewLabel_4);

		ScpDesc = new JScrollPane();
		ScpDesc.setBounds(83, 206, 261, 71);
		getContentPane().add(ScpDesc);

		txtDescricao = new JTextArea();
		ScpDesc.setViewportView(txtDescricao);

		JLabel lblNewLabel_5 = new JLabel("Fabricante");
		lblNewLabel_5.setBounds(195, 296, 84, 14);
		getContentPane().add(lblNewLabel_5);

		txtfabricante = new JTextField();
		txtfabricante.setBounds(262, 296, 145, 18);
		getContentPane().add(txtfabricante);
		txtfabricante.setColumns(10);
		txtfabricante.setDocument(new Validador(50));

		JLabel lblNewLabel_6 = new JLabel("Estoque");
		lblNewLabel_6.setBounds(22, 340, 52, 14);
		getContentPane().add(lblNewLabel_6);

		txtestoque = new JTextField();
		txtestoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtestoque.setBounds(76, 337, 80, 20);
		getContentPane().add(txtestoque);
		txtestoque.setColumns(10);
		txtestoque.setDocument(new Validador(10));

		JLabel lblNewLabel_7 = new JLabel("Estoque Mínimo");
		lblNewLabel_7.setBounds(195, 340, 107, 14);
		getContentPane().add(lblNewLabel_7);

		txtestoquemin = new JTextField();
		txtestoquemin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtestoquemin.setBounds(292, 337, 75, 20);
		getContentPane().add(txtestoquemin);
		txtestoquemin.setColumns(10);
		txtestoquemin.setDocument(new Validador(10));

		JLabel lblNewLabel_8 = new JLabel("Unidade");
		lblNewLabel_8.setBounds(22, 424, 52, 14);
		getContentPane().add(lblNewLabel_8);

		cboUnidade = new JComboBox();
		cboUnidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] { "", "UN", "CX", "PC", "Kg", "m" }));
		cboUnidade.setBounds(76, 420, 80, 23);
		getContentPane().add(cboUnidade);

		JLabel lblNewLabel_9 = new JLabel("Local");
		lblNewLabel_9.setBounds(189, 424, 52, 14);
		getContentPane().add(lblNewLabel_9);

		txtlocalarm = new JTextField();
		txtlocalarm.setBounds(228, 421, 123, 22);
		getContentPane().add(txtlocalarm);
		txtlocalarm.setColumns(10);
		txtlocalarm.setDocument(new Validador(50));

		Panel panel = new Panel();
		panel.setBackground(UIManager.getColor("Button.light"));
		panel.setBounds(414, 39, 360, 62);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setBorder(null);
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBounds(20, 39, 150, 23);
		panel.add(scrollPaneFor);

		listID = new JList();
		listID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
			}
		});
		scrollPaneFor.setViewportView(listID);

		JLabel lblNewLabel_10 = new JLabel("Fornecedor");
		lblNewLabel_10.setBounds(10, 0, 71, 14);
		panel.add(lblNewLabel_10);

		txtfornecedor = new JTextField();
		txtfornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		txtfornecedor.setBounds(20, 21, 150, 20);
		panel.add(txtfornecedor);
		txtfornecedor.setColumns(10);

		JLabel lblNewLabel_11 = new JLabel("ID");
		lblNewLabel_11.setBounds(202, 24, 11, 14);
		panel.add(lblNewLabel_11);

		txtidfor = new JTextField();
		txtidfor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtidfor.setBounds(223, 21, 86, 20);
		panel.add(txtidfor);
		txtidfor.setColumns(10);
		txtidfor.setDocument(new Validador(10));

		btnbuscarfornecedor = new JButton("");
		btnbuscarfornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscaridfornecedor();
			}
		});
		btnbuscarfornecedor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnbuscarfornecedor.setBorderPainted(false);
		btnbuscarfornecedor.setFocusPainted(false);
		btnbuscarfornecedor.setIcon(new ImageIcon(Produtos.class.getResource("/img/172546_search_icon.png")));
		btnbuscarfornecedor.setBounds(319, 15, 31, 30);
		panel.add(btnbuscarfornecedor);

		JLabel lblNewLabel_12 = new JLabel("Custo");
		lblNewLabel_12.setBounds(22, 381, 56, 14);
		getContentPane().add(lblNewLabel_12);

		txtcusto = new JTextField();
		txtcusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtcusto.setBounds(70, 381, 75, 20);
		getContentPane().add(txtcusto);
		txtcusto.setColumns(10);
		txtcusto.setDocument(new Validador(20));

		JLabel lblNewLabel_12_1 = new JLabel("Lucro");
		lblNewLabel_12_1.setBounds(175, 381, 46, 14);
		getContentPane().add(lblNewLabel_12_1);

		txtlucro = new JTextField();
		txtlucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtlucro.setText("0");
		txtlucro.setColumns(10);
		txtlucro.setBounds(217, 378, 37, 20);
		getContentPane().add(txtlucro);
		txtlucro.setDocument(new Validador(10));

		JLabel lblNewLabel_13 = new JLabel("%");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_13.setBounds(259, 378, 21, 20);
		getContentPane().add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Entrada");
		lblNewLabel_14.setBounds(62, 469, 62, 14);
		getContentPane().add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Validade");
		lblNewLabel_15.setBounds(245, 469, 62, 14);
		getContentPane().add(lblNewLabel_15);

		btnCadastrar = new JButton("");
		btnCadastrar.setFocusPainted(false);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadprod();
			}
		});
		btnCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCadastrar.setBorder(null);
		btnCadastrar.setIcon(new ImageIcon(Produtos.class.getResource("/img/add.png")));
		btnCadastrar.setBounds(458, 452, 48, 48);
		getContentPane().add(btnCadastrar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkalt.isSelected()) {
					editar();
				} else {
					editarexcFoto();
				}
			}
		});
		btnEditar.setFocusPainted(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/edit.png")));
		btnEditar.setBounds(535, 452, 48, 48);
		getContentPane().add(btnEditar);

		btnDeletar = new JButton("");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnDeletar.setFocusPainted(false);
		btnDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletar.setBorder(null);
		btnDeletar.setIcon(new ImageIcon(Produtos.class.getResource("/img/delete.png")));
		btnDeletar.setBounds(614, 452, 48, 48);
		getContentPane().add(btnDeletar);

		lblfoto = new JLabel("");
		lblfoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblfoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo.png")));
		lblfoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblfoto.setBounds(424, 107, 342, 280);
		getContentPane().add(lblfoto);

		btnLimpar = new JButton("");
		btnLimpar.setFocusPainted(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/eraser.png")));
		btnLimpar.setBounds(696, 452, 48, 48);
		getContentPane().add(btnLimpar);

		dateEnt = new JDateChooser();
		dateEnt.setBounds(22, 494, 145, 20);
		getContentPane().add(dateEnt);

		dateVal = new JDateChooser();
		dateVal.setBounds(205, 494, 145, 20);
		getContentPane().add(dateVal);

		checkalt = new JCheckBox("Alterar Imagem");
		checkalt.setFocusPainted(false);
		checkalt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkalt.isSelected()) {
					carregarFoto();
				}
			}
		});
		checkalt.setBounds(538, 394, 134, 23);
		getContentPane().add(checkalt);

		setLocationRelativeTo(null);
	}

	private void limpar() {
		txtbarcode.setText(null);
		txtcodigo.setText(null);
		txtproduto.setText(null);
		txtDescricao.setText(null);
		lblfoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo.png")));
		txtlote.setText(null);
		txtestoque.setText(null);
		txtcusto.setText(null);
		cboUnidade.setSelectedIndex(0);
		txtfabricante.setText(null);
		txtestoquemin.setText(null);
		txtlucro.setText(null);
		txtlocalarm.setText(null);
		dateEnt.setCalendar(null);
		dateVal.setCalendar(null);
		txtfornecedor.setText(null);
		txtidfor.setText(null);
		checkalt.setSelected(false);
		scrollPaneFor.setVisible(false);
		scrollPaneProd.setVisible(false);
	}

	@SuppressWarnings("unchecked")
	private void listarFornecedores() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listID.setModel(modelo);
		String readLista = "select * from fornecedores where fantasia like '" + txtfornecedor.getText() + "%'"
				+ "order by fantasia";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneFor.setVisible(true);
				modelo.addElement(rs.getString(3));
				if (txtfornecedor.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarFornecedorLista() {
		int linha = listID.getSelectedIndex();
		if (linha >= 0) {
			String readListaFornecedor = "select * from fornecedores where fantasia like '" + txtfornecedor.getText()
					+ "%'" + "order by fantasia limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaFornecedor);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneFor.setVisible(false);
					txtfornecedor.setText(rs.getString(3));
					txtidfor.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneFor.setVisible(false);
		}
	}

	private void buscaridfornecedor() {
		if (txtidfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do fornecedor!");
			txtidfor.requestFocus();
		} else {
			String readId = "select * from fornecedores where idfor = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readId);
				pst.setString(1, txtidfor.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtfornecedor.setText(rs.getString(3));
					txtidfor.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "ID não encontrado!");
				}
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void buscarProduto() {
		String Cod = JOptionPane.showInputDialog(null, "Digite o Código do produto para buscar!");

		if (Cod != null) {
			String readCodigo = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where codigo = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, Cod);
				rs = pst.executeQuery();
				if (rs.next()) {
					txtcodigo.setText(rs.getString(1));
					txtbarcode.setText(rs.getString(2));
					txtproduto.setText(rs.getString(3));
					txtlote.setText(rs.getString(4));
					txtDescricao.setText(rs.getNString(5));
					txtfabricante.setText(rs.getString(7));
					dateEnt.setDate(rs.getDate(8));
					dateVal.setDate(rs.getDate(9));
					txtestoque.setText(rs.getString(10));
					txtestoquemin.setText(rs.getString(11));
					cboUnidade.setSelectedItem(rs.getString(12));
					txtlocalarm.setText(rs.getString(13));
					txtcusto.setText(rs.getString(14));
					txtlucro.setText(rs.getString(15));
					txtidfor.setText(rs.getString(16));
					txtfornecedor.setText(rs.getString(19));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblfoto.getWidth(),
							lblfoto.getHeight(), Image.SCALE_SMOOTH));
					lblfoto.setIcon(foto);
				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
				txtbarcode.setText(null);
				txtbarcode.requestFocus();
			} catch (java.lang.NullPointerException se) {
				JOptionPane.showInternalMessageDialog(null, "A imagem do Produto não foi encontrada!");
			} catch (Exception e) {
				e.printStackTrace();
				;
			}
		}
	}

	private void buscarProdutoBarcode() {
		String readBarcode = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where barcode = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readBarcode);
			pst.setString(1, txtbarcode.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtcodigo.setText(rs.getString(1));
				txtproduto.setText(rs.getString(3));
				txtlote.setText(rs.getString(4));
				txtDescricao.setText(rs.getNString(5));
				txtfabricante.setText(rs.getString(7));
				dateEnt.setDate(rs.getDate(8));
				dateVal.setDate(rs.getDate(9));
				txtestoque.setText(rs.getString(10));
				txtestoquemin.setText(rs.getString(11));
				cboUnidade.setSelectedItem(rs.getString(12));
				txtlocalarm.setText(rs.getString(13));
				txtcusto.setText(rs.getString(14));
				txtlucro.setText(rs.getString(15));
				txtidfor.setText(rs.getString(16));
				txtfornecedor.setText(rs.getString(19));
				Blob blob = (Blob) rs.getBlob(6);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblfoto.getWidth(), lblfoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblfoto.setIcon(foto);
			} else {
				JOptionPane.showMessageDialog(null, "Produto não encontrado");
			}
			con.close();
		} catch (java.sql.SQLIntegrityConstraintViolationException se) {
			JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
			txtbarcode.setText(null);
			txtbarcode.requestFocus();
		} catch (java.lang.NullPointerException se) {
			JOptionPane.showInternalMessageDialog(null, "A imagem do Produto não foi encontrada!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void cadprod() {
		if (txtbarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Código de barras do produto!");
			txtbarcode.requestFocus();
		} else if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do produto!");
			txtproduto.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Descrição do produto!");
			txtDescricao.requestFocus();
		} else if (txtlote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Lote do produto!");
			txtlote.requestFocus();
		} else if (txtestoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque do produto!");
			txtestoque.requestFocus();
		} else if (txtcusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Custo do produto!");
			txtcusto.requestFocus();
		} else if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade do produto!");
			cboUnidade.requestFocus();
		} else if (txtestoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque Mínimo do produto!");
			txtestoquemin.requestFocus();
		} else if (dateVal.getDate() == null) {
			dateVal.setDate(null);
			JOptionPane.showMessageDialog(null, "Preencha a Data de Validade do produto!");
			dateVal.requestFocus();
		} else if (txtfornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor do produto!");
			txtfornecedor.requestFocus();
		} else if (txtidfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Fornecedor do produto!");
			txtidfor.requestFocus();
		} else {
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
			String dataFormatada = formatador.format(dateVal.getDate());
			String create = "insert into produtos(barcode, produto, lote, descricao, foto, fabricante, dataval, estoque, estoquemin, unidade, localarm, custo, lucro, idfor) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtbarcode.getText());
				pst.setString(2, txtproduto.getText());
				pst.setString(3, txtlote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setBlob(5, fis, tamanho);
				pst.setString(6, txtfabricante.getText());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtestoque.getText());
				pst.setString(9, txtestoquemin.getText());
				pst.setString(10, cboUnidade.getSelectedItem().toString());
				pst.setString(11, txtlocalarm.getText());
				pst.setString(12, txtcusto.getText());
				pst.setString(13, txtlucro.getText());
				pst.setString(14, txtidfor.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Produto Cadastrado!");
				limpar();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
				txtbarcode.setText(null);
				txtbarcode.requestFocus();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	public void editar() {
		String comando = "update produtos set barcode=?,produto=?,lote=?,descricao=?,foto=?,fabricante=?,dataval=?,estoque=?,estoquemin=?,unidade=?,localarm=?,custo=?,lucro=? where codigo=?";
		if (txtbarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Código de barras do produto!");
			txtbarcode.requestFocus();
		} else if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do produto!");
			txtproduto.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Descrição do produto!");
			txtDescricao.requestFocus();
		} else if (txtlote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Lote do produto!");
			txtlote.requestFocus();
		} else if (txtestoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque do produto!");
			txtestoque.requestFocus();
		} else if (txtcusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Custo do produto!");
			txtcusto.requestFocus();
		} else if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade do produto!");
			cboUnidade.requestFocus();
		} else if (txtestoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque Mínimo do produto!");
			txtestoquemin.requestFocus();
		} else if (dateVal.getDate() == null) {
			dateVal.setDate(null);
			JOptionPane.showMessageDialog(null, "Preencha a Data de Validade do produto!");
			dateVal.requestFocus();
		} else if (txtfornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor do produto!");
			txtfornecedor.requestFocus();
		} else if (txtidfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Fornecedor do produto!");
			txtidfor.requestFocus();
		} else {
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
			String dataFormatada = formatador.format(dateVal.getDate());
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);
				pst.setString(1, txtbarcode.getText());
				pst.setString(2, txtproduto.getText());
				pst.setString(3, txtlote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setBlob(5, fis, tamanho);
				pst.setString(6, txtfabricante.getText());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtestoque.getText());
				pst.setString(9, txtestoquemin.getText());
				pst.setString(10, cboUnidade.getSelectedItem().toString());
				pst.setString(11, txtlocalarm.getText());
				pst.setString(12, txtcusto.getText());
				pst.setString(13, txtlucro.getText());
				pst.setString(14, txtcodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso.");
				limpar();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void editarexcFoto() {
		String comando = "update produtos set barcode=?,produto=?,lote=?,descricao=?,fabricante=?,dataval=?,estoque=?,estoquemin=?,unidade=?,localarm=?,custo=?,lucro=? where codigo=?";
		if (txtbarcode.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Código de barras do produto!");
			txtbarcode.requestFocus();
		} else if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nome do produto!");
			txtproduto.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a Descrição do produto!");
			txtDescricao.requestFocus();
		} else if (txtlote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Lote do produto!");
			txtlote.requestFocus();
		} else if (txtestoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque do produto!");
			txtestoque.requestFocus();
		} else if (txtcusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Custo do produto!");
			txtcusto.requestFocus();
		} else if (cboUnidade.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha a Unidade do produto!");
			cboUnidade.requestFocus();
		} else if (txtestoquemin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Estoque Mínimo do produto!");
			txtestoquemin.requestFocus();
		} else if (dateVal.getDate() == null) {
			dateVal.setDate(null);
			JOptionPane.showMessageDialog(null, "Preencha a Data de Validade do produto!");
			dateVal.requestFocus();
		} else if (txtfornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Fornecedor do produto!");
			txtfornecedor.requestFocus();
		} else if (txtidfor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o ID do Fornecedor do produto!");
			txtidfor.requestFocus();
		} else {
			SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
			String dataFormatada = formatador.format(dateVal.getDate());
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);
				pst.setString(1, txtbarcode.getText());
				pst.setString(2, txtproduto.getText());
				pst.setString(3, txtlote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setString(5, txtfabricante.getText());
				pst.setString(6, dataFormatada);
				pst.setString(7, txtestoque.getText());
				pst.setString(8, txtestoquemin.getText());
				pst.setString(9, cboUnidade.getSelectedItem().toString());
				pst.setString(10, txtlocalarm.getText());
				pst.setString(11, txtcusto.getText());
				pst.setString(12, txtlucro.getText());
				pst.setString(13, txtcodigo.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso.");
				limpar();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
				txtbarcode.setText(null);
				txtbarcode.requestFocus();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	private void excluir() {
		if (txtcodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o código do produto!");
			txtcodigo.requestFocus();
		} else {
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produto?", "Atenção!",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				String delete = "delete from produtos where codigo=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtcodigo.getText());
					pst.executeUpdate();
					limpar();
					JOptionPane.showMessageDialog(null, "Produto excluído!");
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens (*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblfoto.getWidth(),
						lblfoto.getHeight(), Image.SCALE_SMOOTH);
				lblfoto.setIcon(new ImageIcon(foto));
				lblfoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (resultado == JFileChooser.CANCEL_OPTION) {
			checkalt.setSelected(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void listarProdutos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listProd.setModel(modelo);
		String readLista = "select * from produtos where produto like '" + txtproduto.getText() + "%'"
				+ "order by produto";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPaneProd.setVisible(true);
				modelo.addElement(rs.getString(3));
				if (txtproduto.getText().isEmpty()) {
					scrollPaneProd.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarProdutosLista() {
		int linha = listProd.getSelectedIndex();
		if (linha >= 0) {
			String readListaProdutos = "select * from produtos inner join fornecedores on produtos.idfor = fornecedores.idfor where produto like '"
					+ txtproduto.getText() + "%'" + "order by produto limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaProdutos);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPaneProd.setVisible(false);
					txtcodigo.setText(rs.getString(1));
					txtbarcode.setText(rs.getString(2));
					txtproduto.setText(rs.getString(3));
					txtlote.setText(rs.getString(4));
					txtDescricao.setText(rs.getNString(5));
					txtfabricante.setText(rs.getString(7));
					dateEnt.setDate(rs.getDate(8));
					dateVal.setDate(rs.getDate(9));
					txtestoque.setText(rs.getString(10));
					txtestoquemin.setText(rs.getString(11));
					cboUnidade.setSelectedItem(rs.getString(12));
					txtlocalarm.setText(rs.getString(13));
					txtcusto.setText(rs.getString(14));
					txtlucro.setText(rs.getString(15));
					txtidfor.setText(rs.getString(16));
					txtfornecedor.setText(rs.getString(19));
					Blob blob = (Blob) rs.getBlob(6);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblfoto.getWidth(),
							lblfoto.getHeight(), Image.SCALE_SMOOTH));
					lblfoto.setIcon(foto);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException se) {
				JOptionPane.showInternalMessageDialog(null, "Já existe um produto com o código de barras cadastrado!");
				txtbarcode.setText(null);
				txtbarcode.requestFocus();
			} catch (java.lang.NullPointerException se) {
				JOptionPane.showInternalMessageDialog(null, "A imagem do Produto não foi encontrada!");
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			scrollPaneProd.setVisible(false);
		}
	}

	public void OnlyNumber(KeyEvent e) {
		char c = e.getKeyChar();
		if (Character.isLetter(c)) {
			e.consume();
		}
	}

}
