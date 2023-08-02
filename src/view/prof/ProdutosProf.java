package view.prof;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

//import com.toedter.calendar.JDateChooser;



import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.table.DefaultTableModel;

import model.DAO;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.JList;
import java.awt.SystemColor;
import java.awt.Cursor;
import com.toedter.calendar.JDateChooser;
import java.awt.ScrollPane;

public class ProdutosProf extends JDialog {
	
	// Instanciar objetos JDBC
		DAO dao = new DAO();
		private Connection con;
		private PreparedStatement pst;
		private ResultSet rs;

	
	private JTextField txtBarcode;
	private JTextField txtCodigo;
	private JTextField txtFornecedor;
	private JTextField txtId;
	private JTextField txtProduto;
	private JTextField txtCusto;
	private JTextField txtLucro;
	private JTextField txtFabricante;
	private JTextField txtEstoque;
	private JTextField txtEstoquemin;
	private JTextField txtLocal;
	private JTextArea txtaDescricao;
	private JComboBox cboUnidade;
	private JTextField txtLote;
	private JDateChooser dataEntrada;
	//private JDateChooser dateEntrada;
	//private JDateChooser dateValidade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProdutosProf dialog = new ProdutosProf();
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
	public ProdutosProf() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				txtBarcode.requestFocus();
			}
			
		});
		setTitle("Produtos");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);
		
		//dateEntrada = new JDateChooser();
		//dateEntrada.setBounds(379, 226, 147, 20);
		//getContentPane().add(dateEntrada);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/barcode.png")));
		lblNewLabel.setBounds(22, 31, 64, 45);
		getContentPane().add(lblNewLabel);
		
		txtBarcode = new JTextField();
		txtBarcode.addKeyListener(new KeyAdapter() {
			//leitor de c�digo de barras
			//evento ao pressionar uma tecla espec�fica (ENTER)
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					pesquisarProdutoBarcode();
				}
			}
		});
		txtBarcode.setBounds(96, 43, 240, 20);
		getContentPane().add(txtBarcode);
		txtBarcode.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("C\u00F3digo");
		lblNewLabel_1.setBounds(33, 91, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(90, 88, 103, 20);
		getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setForeground(SystemColor.textHighlight);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarProduto();
			}
		});
		btnPesquisar.setBounds(203, 87, 95, 23);
		getContentPane().add(btnPesquisar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(380, 25, 375, 67);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarFornecedor();
			}
		});
		txtFornecedor.setBounds(25, 26, 152, 20);
		panel.add(txtFornecedor);
		txtFornecedor.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/search.png")));
		lblNewLabel_2.setBounds(185, 22, 24, 24);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("ID");
		lblNewLabel_3.setBounds(243, 29, 24, 14);
		panel.add(lblNewLabel_3);
		
		txtId = new JTextField();
		txtId.setBounds(268, 26, 86, 20);
		panel.add(txtId);
		txtId.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Produto");
		lblNewLabel_4.setBounds(33, 140, 46, 14);
		getContentPane().add(lblNewLabel_4);
		
		txtProduto = new JTextField();
		txtProduto.setBounds(90, 137, 360, 20);
		getContentPane().add(txtProduto);
		txtProduto.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Descri\u00E7\u00E3o");
		lblNewLabel_5.setBounds(22, 201, 58, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtaDescricao = new JTextArea();
		txtaDescricao.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtaDescricao.setBounds(90, 185, 360, 74);
		getContentPane().add(txtaDescricao);
		
		JLabel lblNewLabel_6 = new JLabel("Entrada");
		lblNewLabel_6.setBounds(385, 326, 58, 14);
		getContentPane().add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Validade");
		lblNewLabel_7.setBounds(386, 387, 64, 14);
		getContentPane().add(lblNewLabel_7);
		
		//dateValidade = new JDateChooser();
		//dateValidade.setBounds(577, 226, 147, 20);
		//getContentPane().add(dateValidade);
		
		JLabel lblNewLabel_8 = new JLabel("Custo");
		lblNewLabel_8.setBounds(50, 382, 46, 14);
		getContentPane().add(lblNewLabel_8);
		
		txtCusto = new JTextField();
		txtCusto.setBounds(89, 379, 103, 20);
		getContentPane().add(txtCusto);
		txtCusto.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Lucro");
		lblNewLabel_9.setBounds(217, 379, 46, 14);
		getContentPane().add(lblNewLabel_9);
		
		txtLucro = new JTextField();
		txtLucro.setBounds(260, 376, 38, 20);
		getContentPane().add(txtLucro);
		txtLucro.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("%");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_10.setBounds(308, 378, 28, 14);
		getContentPane().add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Fabricante");
		lblNewLabel_11.setBounds(234, 286, 64, 14);
		getContentPane().add(lblNewLabel_11);
		
		txtFabricante = new JTextField();
		txtFabricante.setBounds(303, 283, 147, 20);
		getContentPane().add(txtFabricante);
		txtFabricante.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("Estoque");
		lblNewLabel_12.setBounds(33, 332, 46, 14);
		getContentPane().add(lblNewLabel_12);
		
		txtEstoque = new JTextField();
		txtEstoque.setBounds(89, 329, 51, 20);
		getContentPane().add(txtEstoque);
		txtEstoque.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("Estoque m\u00EDnimo");
		lblNewLabel_13.setBounds(171, 332, 95, 14);
		getContentPane().add(lblNewLabel_13);
		
		txtEstoquemin = new JTextField();
		txtEstoquemin.setColumns(10);
		txtEstoquemin.setBounds(267, 329, 51, 20);
		getContentPane().add(txtEstoquemin);
		
		JLabel lblNewLabel_14 = new JLabel("Unidade");
		lblNewLabel_14.setBounds(39, 430, 58, 14);
		getContentPane().add(lblNewLabel_14);
		
		cboUnidade = new JComboBox();
		cboUnidade.setModel(new DefaultComboBoxModel(new String[] {"", "UN", "PC", "CX", "KG", "g", "M", "CM"}));
		cboUnidade.setBounds(90, 426, 51, 22);
		getContentPane().add(cboUnidade);
		
		JLabel lblNewLabel_15 = new JLabel("Local");
		lblNewLabel_15.setBounds(161, 430, 38, 14);
		getContentPane().add(lblNewLabel_15);
		
		txtLocal = new JTextField();
		txtLocal.setBounds(203, 427, 116, 20);
		getContentPane().add(txtLocal);
		txtLocal.setColumns(10);
		
		JButton btnAdicionar = new JButton("");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/cliAdd.png")));
		btnAdicionar.setBorder(null);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inserirProduto();
			}
		});
		btnAdicionar.setBounds(456, 470, 64, 64);
		getContentPane().add(btnAdicionar);
		
		JButton btnAlterar = new JButton("");
		btnAlterar.setToolTipText("Editar");
		btnAlterar.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/cliEdit.png")));
		btnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAlterar.setContentAreaFilled(false);
		btnAlterar.setBorder(null);
		btnAlterar.setBounds(534, 470, 64, 64);
		getContentPane().add(btnAlterar);
		
		JButton btnExcluir = new JButton("");
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/cliRemove.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorder(null);
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBounds(611, 470, 64, 64);
		getContentPane().add(btnExcluir);
		
		JLabel lblNewLabel_4_1 = new JLabel("Lote");
		lblNewLabel_4_1.setBounds(40, 286, 46, 14);
		getContentPane().add(lblNewLabel_4_1);
		
		txtLote = new JTextField();
		txtLote.setColumns(10);
		txtLote.setBounds(90, 283, 121, 20);
		getContentPane().add(txtLote);
		
		JLabel lblNewLabel_16 = new JLabel("");
		lblNewLabel_16.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_16.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/produtosIcon.png")));
		lblNewLabel_16.setBounds(496, 129, 256, 256);
		getContentPane().add(lblNewLabel_16);
		
		JButton btnNewButton = new JButton("Carregar imagem");
		btnNewButton.setForeground(SystemColor.textHighlight);
		btnNewButton.setBounds(611, 395, 141, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnLimparCampos = new JButton("");
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//			limparcampos();
			}
		});
		btnLimparCampos.setIcon(new ImageIcon(ProdutosProf.class.getResource("/img/erase.png")));
		btnLimparCampos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimparCampos.setToolTipText("Limpar");
		btnLimparCampos.setContentAreaFilled(false);
		btnLimparCampos.setBorder(null);
		btnLimparCampos.setBounds(691, 470, 64, 64);
		getContentPane().add(btnLimparCampos);
		
		dataEntrada = new JDateChooser();
		dataEntrada.setBounds(346, 351, 124, 20);
		getContentPane().add(dataEntrada);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(346, 412, 124, 20);
		getContentPane().add(dateChooser_1);
		
		JButton btnNewButton_1 = new JButton("Buscar");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//			Buscar();
			}
		});
		btnNewButton_1.setBounds(96, 483, 89, 23);
		getContentPane().add(btnNewButton_1);

	}// fim do construtor
	
	
	
	private void pesquisarFornecedor() {
		
	}
	
	private void pesquisarProduto() {
		//System.out.println("teste bot�o pesquisar produto");
		String comando = "select * from produtosDida where codigo = ?";		
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(comando);
			pst.setString(1, txtCodigo.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtProduto.setText(rs.getString(2));
				txtBarcode.setText(rs.getString(3));
				txtLote.setText(rs.getNString(5));
				
				String setarDataent = rs.getString(8);
				
				Date dataEntradex = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataent);
				dataEntrada.setDate(dataEntradex);
				txtaDescricao.setText(rs.getNString(5));
				txtEstoque.setText(rs.getString(10));
				txtEstoquemin.setText(rs.getString(11));
				cboUnidade.setSelectedItem(rs.getString(12));
				txtCusto.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtFornecedor.setText(rs.getString(16));
				
				
				//				txtBarcode.setText(rs.getString(2));
//				txtProduto.setText(rs.getString(3));
//				txtaDescricao.setText(rs.getString(4));
//				//etar a data do JCalendar
//				String setardataEnt = rs.getString(8);
//				txtFabricante.setText(rs.getString(5));
//				txtEstoque.setText(rs.getString(8));
//				txtEstoquemin.setText(rs.getNString(9));
//				cboUnidade.setSelectedItem(rs.getString(10));
//				txtLocal.setText(rs.getString(11));
//				txtId.setText(rs.getString(14));
//				//formata��o da data recebida pelo MySQL
//				// JCalendar - formata��o para exibi��o
//				String setarData = rs.getString(6);
//				//apoio a l�gica
//				//System.out.println(setarData);
//				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
//				//dateEntrada.setDate(dataFormatada);
//				String setarData2 = rs.getString(7);
//				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
//				//dateValidade.setDate(dataFormatada2);
//				txtCusto.setText(rs.getString(12));
//				txtLucro.setText(rs.getString(13));
			} else {
				JOptionPane.showMessageDialog(null, "Produto n�o cadastrado");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void pesquisarProdutoBarcode() {
		//System.out.println("teste bot�o pesquisar produto");
		String read = "select * from produtos where barcode = ?";		
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(read);
			pst.setString(1, txtBarcode.getText());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				txtCodigo.setText(rs.getString(1));
				txtProduto.setText(rs.getString(3));
				txtaDescricao.setText(rs.getString(4));
				txtFabricante.setText(rs.getString(5));
				txtEstoque.setText(rs.getString(8));
				txtEstoquemin.setText(rs.getString(9));
				cboUnidade.setSelectedItem(rs.getString(10));
				txtLocal.setText(rs.getString(11));
				txtId.setText(rs.getString(14));
				//formata��o da data recebida pelo MySQL
				// JCalendar - formata��o para exibi��o
				String setarData = rs.getString(6);
				//apoio a l�gica
				//System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				//dateEntrada.setDate(dataFormatada);
				String setarData2 = rs.getString(7);
				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
				//dateValidade.setDate(dataFormatada2);
				txtCusto.setText(rs.getString(12));
				txtLucro.setText(rs.getString(13));
			} else {
				JOptionPane.showMessageDialog(null, "Produto n�o cadastrado");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void inserirProduto() {
		//valida��o
		//...
		String insert = "insert into produtosDida (barcode,produto,descricao,fabricante,dataval,estoque,estoquemin,unidade,localizacao,custo,lucro,idFor) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(insert);
			pst.setString(1, txtBarcode.getText());
			pst.setString(2, txtProduto.getText());
			pst.setString(3, txtaDescricao.getText());
			pst.setString(4, txtFabricante.getText());
			// Formatar o valor do JCalendar para inser��o correta no banco
			SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
			//String dataFormatada = formatador.format(dateValidade.getDate());
			//pst.setString(5, dataFormatada); 
			pst.setString(6, txtEstoque.getText());
			pst.setString(7, txtEstoquemin.getText());
			pst.setString(8, cboUnidade.getSelectedItem().toString());
			pst.setString(9, txtLocal.getText());
			pst.setString(10, txtCusto.getText());
			pst.setString(11, txtLucro.getText());
			pst.setString(12, txtId.getText());
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
			} else {
				JOptionPane.showMessageDialog(null, "Erro ao cadastrar o produto");
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}//fim do c�digo






