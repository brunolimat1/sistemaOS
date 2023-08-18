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

	/**
	 * 
	 */
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Produtos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneFor.setVisible(false);
				scrollPaneProd.setVisible(false);
			}
		});
		setBounds(100, 100, 910, 601);
		getContentPane().setLayout(null);

		scrollPaneProd = new JScrollPane();
		scrollPaneProd.setBorder(null);
		scrollPaneProd.setVisible(false);
		scrollPaneProd.setBounds(76, 161, 339, 34);
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
		txtbarcode.setBounds(76, 39, 339, 20);
		getContentPane().add(txtbarcode);
		txtbarcode.setColumns(10);
		txtbarcode.setDocument(new Validador(50));

		JLabel lblNewLabel_1 = new JLabel("Código");
		lblNewLabel_1.setBounds(22, 90, 62, 14);
		getContentPane().add(lblNewLabel_1);

		txtcodigo = new JTextField();
		txtcodigo.setEditable(false);
		txtcodigo.setBounds(76, 87, 184, 20);
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
		btnbuscarcod.setBounds(270, 86, 134, 23);
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
		txtproduto.setBounds(76, 143, 339, 20);
		getContentPane().add(txtproduto);
		txtproduto.setColumns(10);
		txtproduto.setDocument(new Validador(50));

		JLabel lblNewLabel_3 = new JLabel("Lote");
		lblNewLabel_3.setBounds(40, 371, 44, 14);
		getContentPane().add(lblNewLabel_3);

		txtlote = new JTextField();
		txtlote.setBounds(76, 369, 114, 17);
		getContentPane().add(txtlote);
		txtlote.setColumns(10);
		txtlote.setDocument(new Validador(20));

		JLabel lblNewLabel_4 = new JLabel("Descrição");
		lblNewLabel_4.setBounds(22, 206, 62, 14);
		getContentPane().add(lblNewLabel_4);

		ScpDesc = new JScrollPane();
		ScpDesc.setBounds(83, 206, 366, 140);
		getContentPane().add(ScpDesc);

		txtDescricao = new JTextArea();
		ScpDesc.setViewportView(txtDescricao);

		JLabel lblNewLabel_5 = new JLabel("Fabricante");
		lblNewLabel_5.setBounds(213, 368, 84, 14);
		getContentPane().add(lblNewLabel_5);

		txtfabricante = new JTextField();
		txtfabricante.setBounds(280, 368, 145, 18);
		getContentPane().add(txtfabricante);
		txtfabricante.setColumns(10);
		txtfabricante.setDocument(new Validador(50));

		JLabel lblNewLabel_6 = new JLabel("Estoque");
		lblNewLabel_6.setBounds(22, 415, 52, 14);
		getContentPane().add(lblNewLabel_6);

		txtestoque = new JTextField();
		txtestoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtestoque.setBounds(76, 412, 80, 20);
		getContentPane().add(txtestoque);
		txtestoque.setColumns(10);
		txtestoque.setDocument(new Validador(10));

		JLabel lblNewLabel_7 = new JLabel("Estoque Mínimo");
		lblNewLabel_7.setBounds(195, 415, 107, 14);
		getContentPane().add(lblNewLabel_7);

		txtestoquemin = new JTextField();
		txtestoquemin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtestoquemin.setBounds(292, 412, 75, 20);
		getContentPane().add(txtestoquemin);
		txtestoquemin.setColumns(10);
		txtestoquemin.setDocument(new Validador(10));

		JLabel lblNewLabel_8 = new JLabel("Unidade");
		lblNewLabel_8.setBounds(22, 510, 52, 14);
		getContentPane().add(lblNewLabel_8);

		cboUnidade = new JComboBox();
		cboUnidade.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] { "", "UN", "CX", "PC", "Kg", "m" }));
		cboUnidade.setBounds(76, 509, 80, 23);
		getContentPane().add(cboUnidade);

		JLabel lblNewLabel_9 = new JLabel("Local");
		lblNewLabel_9.setBounds(189, 513, 52, 14);
		getContentPane().add(lblNewLabel_9);

		txtlocalarm = new JTextField();
		txtlocalarm.setBounds(228, 510, 123, 22);
		getContentPane().add(txtlocalarm);
		txtlocalarm.setColumns(10);
		txtlocalarm.setDocument(new Validador(50));

		Panel panel = new Panel();
		panel.setBackground(UIManager.getColor("Button.light"));
		panel.setBounds(489, 42, 360, 62);
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
		lblNewLabel_12.setBounds(28, 466, 56, 14);
		getContentPane().add(lblNewLabel_12);

		txtcusto = new JTextField();
		txtcusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtcusto.setBounds(76, 466, 75, 20);
		getContentPane().add(txtcusto);
		txtcusto.setColumns(10);
		txtcusto.setDocument(new Validador(20));

		JLabel lblNewLabel_12_1 = new JLabel("Lucro");
		lblNewLabel_12_1.setBounds(181, 466, 46, 14);
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
		txtlucro.setBounds(223, 463, 37, 20);
		getContentPane().add(txtlucro);
		txtlucro.setDocument(new Validador(10));

		JLabel lblNewLabel_13 = new JLabel("%");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_13.setBounds(265, 463, 21, 20);
		getContentPane().add(lblNewLabel_13);

		JLabel lblNewLabel_14 = new JLabel("Entrada");
		lblNewLabel_14.setBounds(423, 412, 62, 14);
		getContentPane().add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Validade");
		lblNewLabel_15.setBounds(423, 469, 62, 14);
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
		btnCadastrar.setBounds(576, 476, 48, 48);
		getContentPane().add(btnCadastrar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkalt.isSelected()) {
					editar();
				} else {
					editarexcSenha();
				}
			}
		});
		btnEditar.setFocusPainted(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setBorder(null);
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/edit.png")));
		btnEditar.setBounds(653, 476, 48, 48);
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
		btnDeletar.setBounds(732, 476, 48, 48);
		getContentPane().add(btnDeletar);

		lblfoto = new JLabel("");
		lblfoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblfoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/photo.png")));
		lblfoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblfoto.setBounds(499, 110, 342, 280);
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
		btnLimpar.setBounds(814, 476, 48, 48);
		getContentPane().add(btnLimpar);

		dateEnt = new JDateChooser();
		dateEnt.setBounds(383, 437, 145, 20);
		getContentPane().add(dateEnt);

		dateVal = new JDateChooser();
		dateVal.setBounds(383, 494, 145, 20);
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
		checkalt.setBounds(617, 397, 134, 23);
		getContentPane().add(checkalt);
	}// fim do código

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
	}

	@SuppressWarnings("unchecked")
	private void listarFornecedores() {
		// System.out.println("teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listID.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from fornecedores where fantasia like '" + txtfornecedor.getText() + "%'"
				+ "order by fantasia";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a query (instrução sql)
			pst = con.prepareStatement(readLista);
			// executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// uso do while para trazer os usuários enquanto existir
			while (rs.next()) {
				// mostrar a barra de rolagem (scrollpane)
				scrollPaneFor.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(3));
				// esconder o scrollpane se nenhuma letra for digitada
				if (txtfornecedor.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
			// fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método que busca o usuário selecionado da lista
	 */
	private void buscarFornecedorLista() {
		// System.out.println("teste");
		// variável que captura o índice da linha da lista
		int linha = listID.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o índice 0 e 1 usuário da lista
			String readListaFornecedor = "select * from fornecedores where fantasia like '" + txtfornecedor.getText()
					+ "%'" + "order by fantasia limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaFornecedor);
				// executar e obter o resultado
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneFor.setVisible(false);
					// setar os campos
					txtfornecedor.setText(rs.getString(3));
					txtidfor.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor inexistente");
				}
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
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
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblfoto.getWidth(), lblfoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblfoto.setIcon(foto);
			} else {
				JOptionPane.showMessageDialog(null, "Produto não encontrado");
			}
			con.close();
		} catch (java.lang.NullPointerException se) {
			JOptionPane.showInternalMessageDialog(null,"A imagem do Produto não foi encontrada!");
		} catch (Exception e) {
			e.printStackTrace();
			;
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

	public void editarexcSenha() {
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
				// CRUD - Delete
				String delete = "delete from produtos where codigo=?";
				// tratamento de exceções
				try {
					// abrir a conexão
					con = dao.conectar();
					// preparar a query (instrução sql)
					pst = con.prepareStatement(delete);
					// substituir a ? pelo id do contato
					pst.setString(1, txtcodigo.getText());
					// executar a query
					pst.executeUpdate();
					// limpar campos
					limpar();
					// exibir uma mensagem ao usuário
					JOptionPane.showMessageDialog(null, "Produto excluído!");
					// fechar a conexão
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
		// System.out.println("teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listProd.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from produtos where produto like '" + txtproduto.getText() + "%'"
				+ "order by produto";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a query (instrução sql)
			pst = con.prepareStatement(readLista);
			// executar a query e trazer o resultado para lista
			rs = pst.executeQuery();
			// uso do while para trazer os usuários enquanto existir
			while (rs.next()) {
				// mostrar a barra de rolagem (scrollpane)
				scrollPaneProd.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(3));
				// esconder o scrollpane se nenhuma letra for digitada
				if (txtproduto.getText().isEmpty()) {
					scrollPaneProd.setVisible(false);
				}
			}
			// fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Método que busca o usuário selecionado da lista
	 */
	private void buscarProdutosLista() {
		// System.out.println("teste");
		// variável que captura o índice da linha da lista
		int linha = listProd.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o índice 0 e 1 usuário da lista
			String readListaProdutos = "select * from produtos where produto like '" + txtproduto.getText() + "%'"
					+ "order by produto limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaProdutos);
				// executar e obter o resultado
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneProd.setVisible(false);
					// setar os campos
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
					buscaridfornecedor();
				} else {
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
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
