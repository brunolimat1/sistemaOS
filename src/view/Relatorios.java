package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;

@SuppressWarnings("unused")
public class Relatorios extends JDialog {

	// objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Relatorios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/clipboard.png")));
		setTitle("Relatórios");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 642, 269);
		getContentPane().setLayout(null);
		
		JButton btnClientes = new JButton("Clientes");
		btnClientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClientes.setVerticalAlignment(SwingConstants.BOTTOM);
		btnClientes.setHorizontalTextPosition(SwingConstants.CENTER);
		btnClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/clients.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}

		});
		btnClientes.setBounds(48, 62, 128, 140);
		getContentPane().add(btnClientes);

		JButton btnServicos = new JButton("Serviços");
		btnServicos.setHorizontalTextPosition(SwingConstants.CENTER);
		btnServicos.setVerticalAlignment(SwingConstants.BOTTOM);
		btnServicos.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnServicos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setToolTipText("Serviços");
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/tools.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setBounds(252, 62, 135, 140);
		getContentPane().add(btnServicos);

		JButton btnEstoque = new JButton("Estoque");
		btnEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioEstoque();
			}
		});
		btnEstoque.setIcon(new ImageIcon(Relatorios.class.getResource("/img/9025861_package_box_icon.png")));
		btnEstoque.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEstoque.setVerticalAlignment(SwingConstants.BOTTOM);
		btnEstoque.setToolTipText("Estoque");
		btnEstoque.setHorizontalTextPosition(SwingConstants.CENTER);
		btnEstoque.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnEstoque.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEstoque.setBounds(456, 62, 135, 140);
		getContentPane().add(btnEstoque);
		setLocationRelativeTo(null);


	}// fim do construtor

	private void relatorioClientes() {
		// instanciar um objeto para construir a página pdf
		Document document = new Document();
		// configurar como A4 e modo paisagem
		// document.setPageSize(PageSize.A4.rotate());
		// gerar o documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			// adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			// adicionar um páragrafo
			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" ")); // pular uma linha
			// ----------------------------------------------------------
			// query (instrução sql para gerar o relatório de clientes)
			String readClientes = "select nome,telefone,cpf from clientes order by idcli";
			try {
				// abrir a conexão com o banco
				con = dao.conectar();
				// preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readClientes);
				// obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				// atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(3); // (2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
				PdfPCell col3 = new PdfPCell(new Paragraph("CPF"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {
					// popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}
				// adicionar a tabela ao documento pdf
				document.add(tabela);
				// fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioServicos() {
		// instanciar um objeto para construir a página pdf
		Document document = new Document();
		// configurar como A4 e modo paisagem
		// document.setPageSize(PageSize.A4.rotate());
		// gerar o documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			// adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			// adicionar um páragrafo
			document.add(new Paragraph("Serviços:"));
			document.add(new Paragraph(" ")); // pular uma linha
			// ----------------------------------------------------------
			// query (instrução sql para gerar o relatório de clientes)
			String readServicos = "select os,nome,data_os,equipamentos,defeito,valor from servicos order by idcli";
			try {
				// abrir a conexão com o banco
				con = dao.conectar();
				// preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readServicos);
				// obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				// atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(6); // (2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("OS"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Clientes"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Data da OS"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Equipamentos"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Defeitos"));
				PdfPCell col6 = new PdfPCell(new Paragraph("Valores"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				tabela.addCell(col6);
				while (rs.next()) {
					// popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
					tabela.addCell(rs.getString(6));
				}
				// adicionar a tabela ao documento pdf
				document.add(tabela);
				// fechar a conexão com o banco
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("servicos.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioEstoque() {
		// instanciar um objeto para construir a página pdf
		Document document = new Document();
		// configurar como A4 e modo paisagem
		// document.setPageSize(PageSize.A4.rotate());
		// gerar o documento pdf
		try {
			// criar um documento em branco (pdf) de nome clientes.pdf
			PdfWriter.getInstance(document, new FileOutputStream("estoque.pdf"));
			// abrir o documento (formatar e inserir o conteúdo)
			document.open();
			// adicionar a data atual
			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));
			// adicionar um páragrafo
			document.add(new Paragraph(" "));
			document.add(new Paragraph("Reposição de Estoque:"));
			document.add(new Paragraph(" ")); // pular uma linha
			// ----------------------------------------------------------
			// query (instrução sql para gerar o relatório de clientes)
			String readEstoque = "select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade,estoque, estoquemin as estoque_mínimo from produtos where estoque < estoquemin";
			try {
				// abrir a conexão com o banco
				con = dao.conectar();
				// preparar a query (executar a instrução sql)
				pst = con.prepareStatement(readEstoque);
				// obter o resultado (trazer do banco de dados)
				rs = pst.executeQuery();
				// atenção uso do while para trazer todos os clientes
				// Criar uma tabela de duas colunas usando o framework(itextPDF)
				PdfPTable tabela = new PdfPTable(5); // (2) número de colunas
				// Criar o cabeçalho da tabela
				PdfPCell col1 = new PdfPCell(new Paragraph("Código"));
				PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
				PdfPCell col3 = new PdfPCell(new Paragraph("Validade"));
				PdfPCell col4 = new PdfPCell(new Paragraph("Estoque"));
				PdfPCell col5 = new PdfPCell(new Paragraph("Estoque Mínimo"));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				tabela.addCell(col4);
				tabela.addCell(col5);
				while (rs.next()) {
					// popular a tabela
					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
					tabela.addCell(rs.getString(4));
					tabela.addCell(rs.getString(5));
				}
				document.add(tabela);
				try {
					document.add(new Paragraph(" "));
					document.add(new Paragraph("Validade já expirada:"));
					document.add(new Paragraph(" "));
					String readValidade = "select codigo as código,produto,date_format(dataval, '%d/%m/%Y') as validade, date_format(dataent, '%d/%m/%Y') as entrada from produtos where dataval < dataent";
					try {
						con = dao.conectar();
						// preparar a query (executar a instrução sql)
						pst = con.prepareStatement(readValidade);
						// obter o resultado (trazer do banco de dados)
						rs = pst.executeQuery();
						// atenção uso do while para trazer todos os clientes
						// Criar uma tabela de duas colunas usando o framework(itextPDF)
						PdfPTable tabela2 = new PdfPTable(4); // (2) número de colunas
						// Criar o cabeçalho da tabela
						PdfPCell col6 = new PdfPCell(new Paragraph("Código"));
						PdfPCell col7 = new PdfPCell(new Paragraph("Produto"));
						PdfPCell col8 = new PdfPCell(new Paragraph("Data de Validade"));
						PdfPCell col9 = new PdfPCell(new Paragraph("Data de Entrada"));
						tabela2.addCell(col6);
						tabela2.addCell(col7);
						tabela2.addCell(col8);
						tabela2.addCell(col9);
						while (rs.next()) {
							// popular a tabela
							tabela2.addCell(rs.getString(1));
							tabela2.addCell(rs.getString(2));
							tabela2.addCell(rs.getString(3));
							tabela2.addCell(rs.getString(4));
						}
						document.add(tabela2);
						try {
							document.add(new Paragraph(" "));
							document.add(new Paragraph("Patrimônio (Custo):"));
							document.add(new Paragraph(" "));
							String readCusto = "select sum(custo * estoque)as Total from produtos";
							try {
								con = dao.conectar();
								// preparar a query (executar a instrução sql)
								pst = con.prepareStatement(readCusto);
								// obter o resultado (trazer do banco de dados)
								rs = pst.executeQuery();
								// atenção uso do while para trazer todos os clientes
								// Criar uma tabela de duas colunas usando o framework(itextPDF)
								PdfPTable tabela3 = new PdfPTable(1); // (2) número de colunas
								// Criar o cabeçalho da tabela
								PdfPCell col10 = new PdfPCell(new Paragraph("Custo"));
								tabela3.addCell(col10);
								while (rs.next()) {
									// popular a tabela
									tabela3.addCell(rs.getString(1));
								}
								document.add(tabela3);
								try {
									document.add(new Paragraph(" "));
									document.add(new Paragraph("Patrimônio (Venda):"));
									document.add(new Paragraph(" "));
									String readVenda = "select sum((custo + (custo * lucro)/100) * estoque) as total from produtos";
									try {
										con = dao.conectar();
										// preparar a query (executar a instrução sql)
										pst = con.prepareStatement(readVenda);
										// obter o resultado (trazer do banco de dados)
										rs = pst.executeQuery();
										// atenção uso do while para trazer todos os clientes
										// Criar uma tabela de duas colunas usando o framework(itextPDF)
										PdfPTable tabela4 = new PdfPTable(1); // (2) número de colunas
										// Criar o cabeçalho da tabela
										PdfPCell col11 = new PdfPCell(new Paragraph("Venda"));
										tabela4.addCell(col11);
										while (rs.next()) {
											// popular a tabela
											tabela4.addCell(rs.getString(1));
										}
										document.add(tabela4);
								con.close();
									} catch (Exception e) {
										e.printStackTrace();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// fechar o documento (pronto para "impressão" (exibir o pdf))
		document.close();
		// Abrir o desktop do sistema operacional e usar o leitor padrão
		// de pdf para exibir o documento
		try {
			Desktop.getDesktop().open(new File("estoque.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	private void Resolucao() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension dimensao = t.getScreenSize();
        this.setSize((dimensao.width + 5), (dimensao.height - 38));

 }
}// fim do código
