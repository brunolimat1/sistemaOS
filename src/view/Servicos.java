package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import utils.Validador;

public class Servicos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
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

	public static void main(String[] args) {
		try {
			Servicos dialog = new Servicos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public Servicos() {
		setModal(true);
		setTitle("Serviços");
		setBounds(100, 100, 800, 600);
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
		scrollPane.setBounds(601, 84, 157, 29);
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
			lblNewLabel.setBounds(27, 58, 24, 14);
			contentPanel.add(lblNewLabel);
		}

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(27, 128, 35, 14);
		contentPanel.add(lblData);

		JLabel lblEquipamentos = new JLabel("Equipamentos:");
		lblEquipamentos.setBounds(29, 189, 89, 14);
		contentPanel.add(lblEquipamentos);

		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setBounds(27, 251, 46, 14);
		contentPanel.add(lblDefeito);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(29, 322, 35, 14);
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
		txtOS.setBounds(55, 55, 131, 20);
		contentPanel.add(txtOS);
		txtOS.setColumns(10);
		txtOS.setDocument(new Validador(11));

		txtEquipamentos = new JTextField();
		txtEquipamentos.setColumns(10);
		txtEquipamentos.setBounds(120, 186, 638, 20);
		contentPanel.add(txtEquipamentos);
		txtEquipamentos.setDocument(new Validador(60));

		txtDefeito = new JTextField();
		txtDefeito.setColumns(10);
		txtDefeito.setBounds(75, 248, 683, 20);
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
		txtValor.setBounds(67, 319, 352, 20);
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
		btnBuscar.setBounds(196, 26, 64, 64);
		contentPanel.add(btnBuscar);

		btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Servicos.class.getResource("/img/add.png")));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setBounds(55, 464, 64, 64);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/edit.png")));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setBounds(199, 464, 64, 64);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/trash.png")));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setBounds(355, 464, 64, 64);
		contentPanel.add(btnExcluir);

		JLabel lblIdDoCliente = new JLabel("Cliente:");
		lblIdDoCliente.setBounds(554, 68, 46, 14);
		contentPanel.add(lblIdDoCliente);

		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setColumns(10);
		txtNome.setBounds(601, 65, 157, 20);
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
		btnLimpar.setBounds(522, 464, 64, 64);
		contentPanel.add(btnLimpar);

		txtData = new JFormattedTextField();
		txtData.setEditable(false);
		txtData.setBounds(65, 125, 138, 20);
		contentPanel.add(txtData);
		txtData.setDocument(new Validador(20));

		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(565, 40, 24, 14);
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
		txtID.setBounds(601, 37, 100, 20);
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
		btnPrint.setBounds(663, 461, 72, 67);
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

	@SuppressWarnings("unchecked")
	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		listID.setModel(modelo);
		String readLista = "select * from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();
			while (rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(2));
				if (txtNome.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClienteLista() {
		int linha = listID.getSelectedIndex();
		if (linha >= 0) {
			String readListaCliente = "select * from clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome limit " + (linha) + " , 1";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaCliente);
				rs = pst.executeQuery();
				if (rs.next()) {
					scrollPane.setVisible(false);
					txtNome.setText(rs.getString(2));
					txtID.setText(rs.getString(1));
				} else {
					JOptionPane.showMessageDialog(null, "ID inexistente");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
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

	}

	private void buscar() {
		String OS = JOptionPane.showInputDialog(null, "Digite o OS para buscar!");
		if (OS != null) {
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
		}

	}

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
				JOptionPane.showMessageDialog(null, se);
			} catch (Exception e) {
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
				String delete = "delete from servicos where OS=?";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(delete);
					pst.setString(1, txtOS.getText());
					pst.executeUpdate();
					limparCampos();
					JOptionPane.showMessageDialog(null, "OS Excluída!");
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void imprimirOS() {
		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Selecione o número da OS antes de imprimir!");
			txtOS.requestFocus();
			return;
		}
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
			document.open();
			String query = "SELECT * FROM servicos INNER JOIN clientes ON servicos.idcli = clientes.idcli WHERE OS=?";
			con = dao.conectar();
			pst = con.prepareStatement(query);
			pst.setString(1, txtOS.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				Paragraph osInfo = new Paragraph("3DPRINTECHGENIUS");
				osInfo.setAlignment(Element.ALIGN_CENTER);
				document.add(osInfo);

				Paragraph osInfo1 = new Paragraph("Assistência Técnica de Impressoras 3D");
				osInfo1.setAlignment(Element.ALIGN_CENTER);
				document.add(osInfo1);

				Paragraph osInfo2 = new Paragraph("Ordem de Serviço N°: " + rs.getString(1));
				osInfo2.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo2);

				Paragraph osInfo3 = new Paragraph("Data da OS: " + rs.getString(2));
				osInfo3.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo3);

				Paragraph osInfo6 = new Paragraph("Equipamento Defeituoso: " + rs.getString(3));
				osInfo6.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo6);

				Paragraph osInfo4 = new Paragraph("Defeito Encontrado: " + rs.getString(4));
				osInfo4.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo4);

				Paragraph osInfo5 = new Paragraph("Valor: " + rs.getString(5));
				osInfo5.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo5);

				Paragraph osInfo7 = new Paragraph("Cliente: " + rs.getString(6));
				osInfo7.setAlignment(Element.ALIGN_LEFT);
				document.add(osInfo7);

				con.close();
			} else {
				JOptionPane.showMessageDialog(null, "A Ordem de Serviço não foi encontrada.");
			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			document.close();
		}

		try {
			Desktop.getDesktop().open(new File("os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void OnlyNumber(KeyEvent e) {
		char c = e.getKeyChar();
		if (Character.isLetter(c)) {
			e.consume();
		}
	}

}
