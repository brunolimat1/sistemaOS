package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Cursor;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	public static void main(String[] args) {
		try {
			Sobre dialog = new Sobre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Sobre() {
		setModal(true);
		setTitle("Sobre ");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/about.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Sobre");
			lblNewLabel.setBounds(177, 11, 67, 30);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
			contentPanel.add(lblNewLabel);
		}
		
		JLabel lblNewLabel_1 = new JLabel("Autor: Bruno Lima");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(25, 55, 101, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("3DPrintTechGenius - Assistência técnica de Impressoras 3D");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(25, 95, 333, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setToolTipText("MIT LICENSE");
		lblNewLabel_3.setBorder(null);
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit.png")));
		lblNewLabel_3.setBounds(296, 122, 128, 128);
		contentPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2_1 = new JLabel("Sob a licença MIT");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel_2_1.setBounds(25, 115, 101, 14);
		contentPanel.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Sistema para gestão de serviços e controle de estoque");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(25, 75, 399, 14);
		contentPanel.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setToolTipText("GITHUB");
		lblNewLabel_4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		        try{
		            URI link = new URI("https://github.com/brunolimat1/sistemaOS");
		            Desktop.getDesktop().browse(link);
		        }catch(Exception erro){
		            System.out.println(erro);
		        }
			}
		});
		lblNewLabel_4.setIcon(new ImageIcon(Sobre.class.getResource("/img/211904_social_github_icon.png")));
		lblNewLabel_4.setBounds(25, 155, 64, 64);
		contentPanel.add(lblNewLabel_4);
		setLocationRelativeTo(null);
	}
}
