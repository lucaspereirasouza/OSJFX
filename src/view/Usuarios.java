package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.DAO;
import util.Validador;

import java.awt.Toolkit;
import javax.swing.JPasswordField;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;

public class Usuarios extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtEmail;
	private JPasswordField txtSenha;
	private JTextField txtFone;
	private JButton bttnAdd;
	private JButton bttnRemove;
	private JButton bttnEditar;
	private JButton bttnBuscar;
	private JLabel lblNewLabel_4;
	private JTextField txtLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/UserIcon.png")));
		setTitle("Usuarios");
		setModal(true);
		setBounds(100, 100, 547, 408);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("id");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(20, 26, 46, 28);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(20, 62, 46, 28);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(20, 220, 46, 28);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(20, 167, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(76, 32, 46, 20);
		getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setBounds(76, 68, 180, 20);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(25));
		
		txtEmail = new JTextField();
		txtEmail.setBounds(76, 226, 241, 20);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(33));
		
		bttnBuscar = new JButton("");
		bttnBuscar.setToolTipText("Buscar");
		getRootPane().setDefaultButton(bttnBuscar);
		bttnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			search();
			}
		
		});
		bttnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		bttnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search.png")));
		bttnBuscar.setBounds(202, 117, 64, 64);
		getContentPane().add(bttnBuscar);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setToolTipText("Remover");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNewButton_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton_1.setIcon(new ImageIcon(Usuarios.class.getResource("/img/erase.png")));
		btnNewButton_1.setBounds(20, 294, 64, 64);
		getContentPane().add(btnNewButton_1);
		
		txtSenha = new JPasswordField();
		txtSenha.setBounds(76, 166, 116, 14);
		getContentPane().add(txtSenha);
//		txtSenha.setDocument(new Validador(20));
		
		bttnAdd = new JButton("");
		bttnAdd.setEnabled(false);
		bttnAdd.setToolTipText("Adicionar");
		bttnAdd.setIcon(new ImageIcon(Usuarios.class.getResource("/img/plusIcon.png")));
		bttnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			adicionar();
			}
		});
		bttnAdd.setBounds(457, 112, 64, 64);
		getContentPane().add(bttnAdd);
		
		bttnEditar = new JButton("");
		bttnEditar.setEnabled(false);
		bttnEditar.setToolTipText("Editar");
		bttnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			refresh();
			}
		});
		bttnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/EditIcon.png")));
		bttnEditar.setBounds(457, 187, 64, 64);
		getContentPane().add(bttnEditar);
		
		JLabel lblNewLabel_3_1 = new JLabel("Fone");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(20, 253, 46, 14);
		getContentPane().add(lblNewLabel_3_1);
		
		txtFone = new JTextField();
		txtFone.setColumns(10);
		txtFone.setBounds(76, 252, 118, 20);
		txtFone.setDocument(new Validador(16));
		getContentPane().add(txtFone);
		
		bttnRemove = new JButton("X");
		bttnRemove.setEnabled(false);
		bttnRemove.setToolTipText("Remover");
		bttnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			remove();
			}
		});
		bttnRemove.setFont(new Font("Tahoma", Font.PLAIN, 49));
		bttnRemove.setBounds(457, 26, 64, 64);
		getContentPane().add(bttnRemove);
		
		lblNewLabel_4 = new JLabel("Login");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(20, 127, 46, 28);
		getContentPane().add(lblNewLabel_4);
		
		txtLogin = new JTextField();
		txtLogin.setColumns(10);
		txtLogin.setBounds(76, 133, 116, 20);
		getContentPane().add(txtLogin);
		txtLogin.setDocument(new Validador(18));
	}
	private void search() {
		//dica - testar o evento primeiro
		//System.out.println("teste do botão buscar");
		
		// Criar uma variável com a query (instrução do banco)
		String read = "select * from usuarios where login = ?";
		String Value = "      ";
		//tratamento de exceções
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtId.setText(rs.getString(1)); //1º ID
				txtNome.setText(rs.getString(2)); //2° NOME
				txtLogin.setText(rs.getString(3)); // 3° Login
				txtEmail.setText(rs.getString(4));//3° EMAIL
				txtSenha.setText(Value);
				txtFone.setText(rs.getString(6));
				
				bttnEditar.setEnabled(true);
				bttnAdd.setEnabled(true);
				bttnBuscar.setEnabled(true);
				bttnRemove.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Usuario inexistente");
				bttnAdd.setEnabled(true);
				
//				bttnBuscar.setEnabled(false);
			
			}
			// fechar a conexão (IMPORTANTE!)
			con.close();
		} catch (Exception e) {
			System.out.println(e);}
	} // fim do metodo buscar
	
	@SuppressWarnings("deprecation")
	private void adicionar() {
		//
		String capturaSenha = new String(txtSenha.getPassword());
		// condição
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nome obrigatorio.");
			txtNome.requestFocus();
		}else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Senha obrigatoria.");
			txtSenha.requestFocus();
		}else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Email obrigatorio.");
			txtEmail.requestFocus();
		}else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login obrigatorio.");
			txtLogin.requestFocus();
		}else if (capturaSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Senha obrigatoria.");
			txtSenha.requestFocus();
		}
		else if (txtFone.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Telefone obrigatorio.");
		txtFone.requestFocus();
		
		}else {
			//adicionar contato
			//usar dao e pst via con
			try {
				
			con = dao.conectar();
			//estrutura dao
			//adicionar mensagem
			String create = "insert into usuarios(nome,login,email,senha,telefone) values(?,?,?,md5(?),?)";
			
			pst = con.prepareStatement(create);
			// Lista dos usuarios
			pst.setString(1,txtNome.getText()); 
			pst.setString(2,txtLogin.getText());
			pst.setString(3,txtEmail.getText());
			pst.setString(4,txtSenha.getText());
			pst.setString(5,txtFone.getText());
			
			pst.executeUpdate(); // execute update
			JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso");
			// limpar os campos
			limparCampos();
			bttnEditar.setEnabled(true);
			// fechar a conexão com o banco
			con.close();
			}catch(SQLIntegrityConstraintViolationException e) {
				JOptionPane.showMessageDialog(null, "Já existe uma conta com esse login, tente com outro nome.");
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
				// TODO: handle exception
			}
		}
		
	}
		private void limparCampos() {
			txtId.setText(null);
			txtNome.setText(null);
			txtSenha.setText(null);
			txtEmail.setText(null);
			txtFone.setText(null);
			txtLogin.setText(null);
			
			bttnAdd.setEnabled(false);
			bttnRemove.setEnabled(false);
			bttnAdd.setEnabled(false);
			bttnEditar.setEnabled(false);
			
			bttnBuscar.setEnabled(true);
		}//fim do método limparCampos()
	@SuppressWarnings("deprecation")
	private void refresh() {
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Login obrigatorio.");
			txtLogin.requestFocus();
		}else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Email obrigatorio.");
			txtEmail.requestFocus();
		}else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Senha obrigatoria.");
			txtSenha.requestFocus();
		}
		else if (txtFone.getText().isEmpty()) {
		JOptionPane.showMessageDialog(null, "Telefone obrigatorio.");
		txtFone.requestFocus();
		}else {
			String update = "update usuarios set nome=?,email=?,senha=md5(?),telefone=? where id=?";
			try{
				con = dao.conectar();
				pst = con.prepareStatement(update);
				
				pst.setString(5, txtId.getText());
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtEmail.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, txtFone.getText());
				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Dados contato editados com sucesso.");
				limparCampos();
				
				con.close();
			}catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
	private void remove() {
		con = dao.conectar();
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?","Atenção!", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			try {
			String delete = "delete from usuarios where id = ?;";
			con = dao.conectar();
			pst = con.prepareStatement(delete);
			pst.setString(1, txtId.getText());
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "Usuario removidos com sucesso");

			limparCampos();
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
		}
	}
}

