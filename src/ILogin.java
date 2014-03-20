import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.SecurityException;
import java.sql.SQLException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.ResourceBundle;
import javax.swing.border.EmptyBorder;

public class ILogin extends JFrame implements ActionListener
{
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	private JComboBox cmbLanguages;
	private JLabel lblLogin;
	private JLabel lblPassword;
	private JLabel lblLanguages;
	private JButton btnLogin;
	private JButton btnCancel;
	private ResourceBundle lang;
	private Login user;

	public ILogin(){
		super( "Login" );
		initialize();
	}

	private void initialize()
	{
		language( 0 );
		createLayout();
		languages();
		createScreen();
	}

	private void language( int i )
	{
		lang = Language.get( i );
	}

	private void createScreen()
	{
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocationRelativeTo( null );
		createControls();
		addControls();
	}

	private void createLayout()
	{
		JPanel defaultPane = new JPanel();
		defaultPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );

		setContentPane( defaultPane );
		setResizable( false );

		setLayout( new GridLayout( 4, 2 ) );
		setSize( 196, 158 );
		setVisible( true );
	}

	private void createControls()
	{
		createTextboxs();
		createButtons();
		createLabels();
	}

	private void languages()
	{
		String[] languages = { "Português", "English", "Español" };
		cmbLanguages = new JComboBox( languages );

		cmbLanguages.addActionListener( this );
	}

	private void addControls()
	{
		Container pane = getContentPane();

		pane.add( lblLanguages );
		pane.add( cmbLanguages );

		pane.add( lblLogin );
		pane.add( txtLogin );

		pane.add( lblPassword );
		pane.add( txtPassword );

		pane.add( btnLogin );
		pane.add( btnCancel );
	}

	private void createButtons()
	{
		btnLogin = new JButton( "Login" );
		btnCancel = new JButton( "Cancelar" );

		btnLogin.addActionListener( this );
		btnCancel.addActionListener( this );
	}

	private void createLabels()
	{
		lblLogin = new JLabel( "Login" );
		lblPassword = new JLabel( "Senha" );
		lblLanguages = new JLabel( "Idiomas" );
	}

	private void setTranslateText()
	{
		lblLogin.setText( lang.getString( "ILogin.lblLogin" ) );
		lblPassword.setText( lang.getString( "ILogin.lblSenha" ) );
		lblLanguages.setText( lang.getString( "ILogin.lblIdiomas" ) );
		btnLogin.setText( lang.getString( "ILogin.btnLogar" ) );
		btnCancel.setText( lang.getString( "ILogin.btnCancelar" ) );
	}

	private void createTextboxs()
	{
		txtLogin = new JTextField( 15 );
		txtPassword = new JPasswordField( 15 );
	}

	public void actionPerformed( ActionEvent ev )
	{
		if ( ev.getSource() == btnCancel )
		{
			cancel();
		}
		else if ( ev.getSource() == btnLogin )
		{
			login();
		}
		else if ( ev.getSource() == cmbLanguages )
		{
			language( cmbLanguages.getSelectedIndex() );
			setTranslateText();
		}
	}

	private void cancel()
	{
		dispose();
	}

	private void login()
	{
		IMain mainScreen;

		try
		{
			if ( validateLogin() )
			{
				dispose();

				mainScreen = new IMain( cmbLanguages.getSelectedIndex(),
						AcessoDB.obtemConexao(), user );
				mainScreen.show();
			}
			else
			{
				JOptionPane.showMessageDialog( null,
						lang.getString( "ILogin.msgLoginInvalido" ) );
			}
		}
		catch ( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	private Boolean validateLogin()
	{
		try
		{
			user = new Login( txtLogin.getText(), txtPassword.getText(), AcessoDB.obtemConexao() );
			return user.validateLogin();
		} 
		catch(Exception ex)
		{
			return false;
		}
	}
}