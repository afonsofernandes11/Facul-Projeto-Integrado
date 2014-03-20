import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.*;

public class ICheck extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	private JPanel pnlHeaderBank;
	private JPanel pnlBanke;
	private JLabel lblBank;
	private JTextField txtBank;

	private JPanel pnlAgencyAccount;
	private JPanel pnlAgency;
	private JLabel lblAgency;
	private JTextField txtAgency;
	private JPanel pnlAccount;
	private JLabel lblAccount;
	private JTextField txtAccount;

	private JPanel pnlName;
	private JLabel lblName;
	private JTextField txtName;

	private JPanel pnlCpfContainer;
	private JPanel pnlCpf;
	private JLabel lblCpf;
	private JTextField txtCpf;

	private JPanel pnlFooter;
	private JPanel pnlSave;
	private JButton btnSave;
	private JPanel pnlExit;
	private JButton btnExit;

	private Connection conn;
	
	private Boolean isValidate = false;

	public ICheck( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		initialize();
	}

	public ICheck( JFrame Main, ResourceBundle language ){
		super( Main, true );

		lang = language;

		initialize();
	}

	protected void initialize()
	{
		createScreen();
		createLayout();
		createControls();
		addControls();
		createActions();
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout() );
		setContentPane( defaultPanel );

		setSize( 371, 354 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "ICheque.titulo" ) );
	}

	protected void addControls()
	{
		pnlHeaderBank.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlBanke.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlBanke.add( lblBank );
		pnlBanke.add( txtBank );
		pnlBanke.setPreferredSize( new Dimension( 125, 60 ) );

		pnlHeaderBank.add( pnlBanke );
		pnlHeaderBank.setPreferredSize( new Dimension( 260, 60 ) );

		pnlAgencyAccount.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlAgency.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlAccount.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlAgency.add( lblAgency );
		pnlAgency.add( txtAgency );
		pnlAgency.setPreferredSize( new Dimension( 125, 60 ) );

		pnlAccount.add( lblAccount );
		pnlAccount.add( txtAccount );
		pnlAccount.setPreferredSize( new Dimension( 125, 60 ) );

		pnlAgencyAccount.add( pnlAgency );
		pnlAgencyAccount.add( pnlAccount );
		pnlAgencyAccount.setPreferredSize( new Dimension( 260, 60 ) );

		pnlName.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlName.add( lblName );
		pnlName.add( txtName );
		pnlName.setPreferredSize( new Dimension( 260, 60 ) );

		pnlCpfContainer.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlCpf.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlCpf.add( lblCpf );
		pnlCpf.add( txtCpf );
		pnlCpf.setPreferredSize( new Dimension( 180, 60 ) );

		pnlCpfContainer.add( pnlCpf );
		pnlCpfContainer.setPreferredSize( new Dimension( 260, 60 ) );

		pnlFooter.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		pnlSave.add( btnSave );
		pnlSave.setPreferredSize( new Dimension( 100, 50 ) );
		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 50 ) );

		pnlFooter.add( pnlSave );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 260, 60 ) );

		add( pnlHeaderBank );
		add( pnlAgencyAccount );
		add( pnlName );
		add( pnlCpfContainer );
		add( pnlFooter );
	}

	protected void createControls()
	{
		pnlHeaderBank = new JPanel();
		pnlBanke = new JPanel();
		lblBank = new JLabel( lang.getString( "ICheque.lblBanco" ) );
		txtBank = new JTextField();
		txtBank.setPreferredSize( new Dimension( 115, 25 ) );

		pnlAgencyAccount = new JPanel();
		pnlAgency = new JPanel();
		lblAgency = new JLabel( lang.getString( "ICheque.lblAgencia" ) );
		txtAgency = new JTextField();
		txtAgency.setPreferredSize( new Dimension( 115, 25 ) );

		pnlAccount = new JPanel();
		lblAccount = new JLabel( lang.getString( "ICheque.lblConta" ) );
		txtAccount = new JTextField();
		txtAccount.setPreferredSize( new Dimension( 115, 25 ) );

		pnlName = new JPanel();
		lblName = new JLabel( lang.getString( "ICheque.lblNome" ) );
		txtName = new JTextField();
		txtName.setPreferredSize( new Dimension( 250, 25 ) );

		pnlCpfContainer = new JPanel();
		pnlCpf = new JPanel();
		lblCpf = new JLabel( lang.getString( "ICheque.lblCPF" ) );
		txtCpf = new JTextField();
		txtCpf.setPreferredSize( new Dimension( 160, 25 ) );

		pnlFooter = new JPanel();
		pnlSave = new JPanel();
		btnSave = new JButton( lang.getString( "btnSalvar" ) );

		pnlExit = new JPanel();
		btnExit = new JButton( lang.getString( "btnSair" ) );

	}

	private void createActions()
	{
		btnSave.addActionListener( this );
		btnExit.addActionListener( this );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnSave )
		{
			Save();
		}
		else if ( e.getSource() == btnExit )
		{
			Exit();
		}
	}

	public void Save()
	{
		if( validateScreen() )
		{
			isValidate = true;
			dispose();
		}
	}
	
	public Boolean isValidate()
	{
		return isValidate;
	}
	
	private Boolean validateScreen()
	{
		Boolean retorno = false;
		
		if( GeralFunctions.IsEmpty( txtBank.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("ICheque.mensagem.banco.obrigatorio") );
		}
		else if( GeralFunctions.IsEmpty( txtAgency.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("ICheque.mensagem.agencia.obrigatorio") );
		}
		else if( GeralFunctions.IsEmpty( txtAccount.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("ICheque.mensagem.conta.obrigatorio") );
		}
		else if( GeralFunctions.IsEmpty( txtName.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("ICheque.mensagem.nome.obrigatorio") );
		}
		else if( GeralFunctions.IsEmpty( txtCpf.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("ICheque.mensagem.cpf.obrigatorio") );
		}
		else 
		{
			retorno = true;
		}
		
		return retorno;
	}

	public Check getCheck()
	{
		Check check = new Check();

		check.setAccount( txtAccount.getText() );
		check.setAgency( txtAgency.getText() );
		check.setBank( txtBank.getText() );
		check.setCpf( txtCpf.getText() );
		check.setName( txtName.getText() );

		return check;
	}

	public void Exit()
	{
		dispose();
	}

}