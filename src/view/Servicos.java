package view;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;
import java.awt.Cursor;

public class Servicos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtOS;
	private JTextField txtEquipamentos;
	private JTextField txtDefeito;
	private JTextField txtValor;
	private JTextField txtNome;
	private JButton btnBuscar;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnLimpar;
	private JFormattedTextField txtData;
	@SuppressWarnings("rawtypes")
	private JList listID;
	private JScrollPane scrollPane;
	private JTextField txtID;
	private JButton btnPrint;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Servicos dialog = new Servicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public Servicos() {
		setModal(true);
		setTitle("Serviços");
		setBounds(100, 100, 612, 452);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPane.setVisible(false);
			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBorder(null);
		scrollPane.setBounds(448, 44, 138, 64);
		contentPanel.add(scrollPane);

		listID = new JList();
		scrollPane.setViewportView(listID);
		listID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		{
			JLabel lblNewLabel = new JLabel("OS:");
			lblNewLabel.setBounds(27, 39, 24, 14);
			contentPanel.add(lblNewLabel);
		}

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(27, 94, 35, 14);
		contentPanel.add(lblData);

		JLabel lblEquipamentos = new JLabel("Equipamentos:");
		lblEquipamentos.setBounds(27, 154, 89, 14);
		contentPanel.add(lblEquipamentos);

		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setBounds(27, 208, 46, 14);
		contentPanel.add(lblDefeito);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(27, 272, 35, 14);
		contentPanel.add(lblValor);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);
			}
		});
		txtOS.addMouseListener(new MouseAdapter() {

		});
		txtOS.setBounds(55, 36, 131, 20);
		contentPanel.add(txtOS);
		txtOS.setColumns(10);
		txtOS.setDocument(new Validador(11));

		txtEquipamentos = new JTextField();
		txtEquipamentos.setColumns(10);
		txtEquipamentos.setBounds(118, 151, 468, 20);
		contentPanel.add(txtEquipamentos);
		txtEquipamentos.setDocument(new Validador(60));

		txtDefeito = new JTextField();
		txtDefeito.setColumns(10);
		txtDefeito.setBounds(75, 205, 513, 20);
		contentPanel.add(txtDefeito);
		txtDefeito.setDocument(new Validador(60));

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);

			}
		});
		txtValor.setColumns(10);
		txtValor.setBounds(65, 269, 138, 20);
		contentPanel.add(txtValor);
		txtValor.setDocument(new Validador(10));

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.setIcon(new ImageIcon(Servicos.class.getResource("/img/search.png")));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(199, 11, 64, 64);
		contentPanel.add(btnBuscar);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/add.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(27, 333, 64, 64);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/edit.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(151, 333, 64, 64);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/trash.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBounds(275, 333, 64, 64);
		contentPanel.add(btnExcluir);

		JLabel lblIdDoCliente = new JLabel("Cliente:");
		lblIdDoCliente.setBounds(401, 26, 46, 14);
		contentPanel.add(lblIdDoCliente);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setColumns(10);
		txtNome.setBounds(448, 23, 138, 20);
		contentPanel.add(txtNome);
		txtNome.setDocument(new Validador(30));

		btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/eraser.png")));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setBounds(395, 333, 64, 64);
		contentPanel.add(btnLimpar);

		txtData = new JFormattedTextField();
		txtData.setEditable(false);
		txtData.setBounds(65, 91, 138, 20);
		contentPanel.add(txtData);
		txtData.setDocument(new Validador(20));

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(423, 51, 24, 14);
		contentPanel.add(lblId);

		txtID = new JTextField();
		txtID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				OnlyNumber(e);

			}
		});
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(448, 48, 64, 20);
		contentPanel.add(txtID);
		txtID.setDocument(new Validador(10));

		btnPrint = new JButton("");
		btnPrint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnPrint.setIcon(new ImageIcon(Servicos.class.getResource("/img/print.png")));
		btnPrint.setBounds(504, 333, 72, 67);
		contentPanel.add(btnPrint);
		setLocationRelativeTo(null);
	}

	private void limparCampos() {
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamentos.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtNome.setText(null);
		txtID.setText(null);
	}

	/**
	 * Método usado para listar o nome dos usuários na lista
	 */
	@SuppressWarnings("unchecked")
	private void listarClientes() {
		// System.out.println("teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// objeto irá temporariamente armazenar os nomes
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o modelo (vetor na lista)
		listID.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
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
				scrollPane.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder o scrollpane se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
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
	private void buscarClienteLista() {
		// System.out.println("teste");
		// variável que captura o índice da linha da lista
		int linha = listID.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o índice 0 e 1 usuário da lista
			String readListaCliente = "select * from clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query para execução
				pst = con.prepareStatement(readListaCliente);
				// executar e obter o resultado
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPane.setVisible(false);
					// setar os campos
					txtNome.setText(rs.getString(2));
					txtID.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "ID inexistente");
				}
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPane.setVisible(false);
		}
	}

	private void adicionar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Escolha o Cliente");
			txtNome.requestFocus();
		} else if (txtEquipamentos.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha os Equipamentos!");
			txtEquipamentos.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor");
			txtValor.requestFocus();
		} else {
			String create = "insert into servicos(equipamentos,defeito,valor,nome,idcli) values (?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamentos.getText());
				pst.setString(2, txtDefeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtNome.getText());
				pst.setString(5, txtID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "OS cadastrado!");
				limparCampos();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}// fim do método adicionar

	private void buscar() {
		String OS = JOptionPane.showInputDialog(null, "Digite o OS para buscar!");
		try {
			String read = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where OS = ?";
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, OS);
			rs = pst.executeQuery();
			if (rs.next()) {
				txtOS.setText(rs.getString(1));
				txtData.setText(rs.getString(2));
				txtEquipamentos.setText(rs.getString(3));
				txtDefeito.setText(rs.getString(4));
				txtValor.setText(rs.getString(5));
				txtNome.setText(rs.getString(6));
				txtID.setText(rs.getString(8));
			} else {
				JOptionPane.showMessageDialog(null, "OS não cadastrada!");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}// fim do método buscar

	public void editar() {
		String comando = "update servicos set data_os=?,equipamentos=?,defeito=?,valor=? where os=?";
		if (txtID.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo ID não pode estar vazio.");
			txtID.requestFocus();
		} else if (txtEquipamentos.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo Equipamentos não pode estar vazio.");
			txtEquipamentos.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo Defeito não pode estar vazio.");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "O campo Valor não pode estar vazio.");
			txtValor.requestFocus();
		} else {
			try {
				con = dao.conectar();
				pst = con.prepareStatement(comando);

				pst.setString(1, txtData.getText());
				pst.setString(2, txtEquipamentos.getText());
				pst.setString(3, txtDefeito.getText());
				pst.setString(4, txtValor.getText());
				pst.setString(5, txtOS.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados editados com sucesso.");
				limparCampos();

				con.close();
			} catch (SQLException se) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, se);
			} catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}

	private void excluir() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Escolha o Cliente");
			txtNome.requestFocus();
		} else if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a OS!");
			txtOS.requestFocus();
		} else if (txtData.getText().equals("  /  /    ")) {
			JOptionPane.showMessageDialog(null, "Preencha a Data!");
			txtData.requestFocus();
		} else if (txtEquipamentos.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha os Equipamentos!");
			txtEquipamentos.requestFocus();
		} else if (txtDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Defeito");
			txtDefeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Valor");
			txtValor.requestFocus();
		} else {
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS?", "Atenção!",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				// CRUD - Delete
				String delete = "delete from servicos where OS=?";
				// tratamento de exceções
				try {
					// abrir a conexão
					con = dao.conectar();
					// preparar a query (instrução sql)
					pst = con.prepareStatement(delete);
					// substituir a ? pelo id do contato
					pst.setString(1, txtOS.getText());
					// executar a query
					pst.executeUpdate();
					// limpar campos
					limparCampos();
					// exibir uma mensagem ao usuário
					JOptionPane.showMessageDialog(null, "OS Excluída!");
					// fechar a conexão
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * Impressão da OS
	 */
	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		if (txtOS.getText().isEmpty()) {
			JOptionPane.showInternalMessageDialog(null, "Selecione o número da OS antes de imprimir!");
			txtOS.requestFocus();
		} else {
			// documento pdf
			try {
				// criar um documento em branco (pdf) de nome clientes.pdf
				PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
				// abrir o documento (formatar e inserir o conteúdo)
				document.open();
				String readOS = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where OS=?";
				// conexão com o banco
				try {
					// abrir a conexão
					con = dao.conectar();
					// preparar a execução da query (instrução sql)
					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());
					// executar a query
					rs = pst.executeQuery();
					// se existir a OS
					if (rs.next()) {
						// document.add(new Paragraph("OS: " + rs.getString(1)));
						Paragraph os = new Paragraph("OS: " + rs.getString(1));
						os.setAlignment(Element.ALIGN_LEFT);
						document.add(os);
						
						Paragraph nome = new Paragraph("Nome: " + rs.getString(6));
						nome.setAlignment(Element.ALIGN_LEFT);
						document.add(nome);

						Paragraph DataOs = new Paragraph("Data OS: " + rs.getString(2));
						DataOs.setAlignment(Element.ALIGN_LEFT);
						document.add(DataOs);

						Paragraph equipamento = new Paragraph("Equipamento: " + rs.getString(3));
						equipamento.setAlignment(Element.ALIGN_LEFT);
						document.add(equipamento);

						Paragraph Defeito = new Paragraph("Defeito: " + rs.getString(4));
						Defeito.setAlignment(Element.ALIGN_LEFT);
						document.add(Defeito);

						Paragraph Valor = new Paragraph("Valor: " + rs.getString(5));
						Valor.setAlignment(Element.ALIGN_LEFT);
						document.add(Valor);

						// imprimir imagens
						Image imagem = Image.getInstance(Servicos.class.getResource("/img/printer3d.png"));
						imagem.scaleToFit(128, 128);
						imagem.setAbsolutePosition(20, 550);
						document.add(imagem);
					}
					// fechar a conexão com o banco
					con.close();
				} catch (IOException se) {
					JOptionPane.showInternalMessageDialog(null, "OS não encontrada.");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			// fechar o documento (pronto para "impressão" (exibir o pdf))
			document.close();
			// Abrir o desktop do sistema operacional e usar o leitor padrão
			// de pdf para exibir o documento
			try {
				Desktop.getDesktop().open(new File("os.pdf"));
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
