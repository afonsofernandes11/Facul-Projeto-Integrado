import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.*;

public class IRePayment extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	private JPanel pnlHeaderValue;
	private JPanel pnlValue;
	private JLabel lblValue;
	private JTextField txtValue;

	private JPanel pnlContainerBank;
	private JPanel pnlBank;
	private JLabel lblBank;
	private JTextField txtBank;

	private JPanel pnlAgencyAccount;
	private JPanel pnlAgency;
	private JLabel lblAgency;
	private JTextField txtAgency;
	private JPanel pnlAccount;
	private JLabel lblAccount;
	private JTextField txtAccount;

	private JPanel pnlNameContainer;
	private JPanel pnlName;
	private JLabel lblName;
	private JTextField txtName;

	private JPanel pnlCPFContainer;
	private JPanel pnlCPF;
	private JLabel lblCPF;
	private JTextField txtCPF;

	private JPanel pnlFooter;
	private JPanel pnlSave;
	private JButton btnSave;
	private JPanel pnlExit;
	private JButton btnExit;
	private Connection conn;
	private Ticket ticket;
	
	public IRePayment( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		
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
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 415, 357 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "IReembolso.titulo" ) );
	}
	
	public void setTicket( Ticket ticket )
	{
		this.ticket = ticket;
		
		txtValue.setEditable( false );
		
		calcValue();
	}
	
	private void calcValue()
	{
		Date fly =GeralFunctions.formataData( ticket.getFly().getDateTime(), "dd/MM/yyyy HH:mm");
		long flyDiff;
		Double newValue;
		
		int hour = (1000 * 1 * 60 * 60);
		long hour24 = hour * 24;
		long hour12 = hour * 12;
		long hour6 = hour * 6;
		
		flyDiff = new Date( fly.getTime() - new Date().getTime() ).getTime();
		
		if( flyDiff >= hour24 )
		{
			newValue = ticket.getValue();
		}
		else if( flyDiff >= hour12 )
		{
			newValue = ticket.getValue() * 0.4;
		}
		else if( flyDiff >= 6)
		{
			newValue = ticket.getValue() * 0.2;
		}
		else
		{
			newValue = 0.0;
		}
	
		txtValue.setText( "" + newValue );
	}

	protected void addControls()
	{
		// valor do reembolso
		pnlHeaderValue.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlValue.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlValue.add( lblValue );
		pnlValue.add( txtValue );
		pnlValue.setPreferredSize( new Dimension( 160, 60 ) );

		pnlHeaderValue.add( pnlValue );
		pnlHeaderValue.setPreferredSize( new Dimension( 340, 60 ) );

		// banco
		pnlContainerBank.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlBank.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlBank.add( lblBank );
		pnlBank.add( txtBank );
		pnlBank.setPreferredSize( new Dimension( 160, 60 ) );

		pnlContainerBank.add( pnlBank );
		pnlContainerBank.setPreferredSize( new Dimension( 340, 60 ) );

		// agencia e conta
		pnlAgencyAccount.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlAgency.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlAccount.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlAgency.add( lblAgency );
		pnlAgency.add( txtAgency );
		pnlAgency.setPreferredSize( new Dimension( 160, 60 ) );

		pnlAccount.add( lblAccount );
		pnlAccount.add( txtAccount );
		pnlAccount.setPreferredSize( new Dimension( 160, 60 ) );

		pnlAgencyAccount.add( pnlAgency );
		pnlAgencyAccount.add( pnlAccount );
		pnlAgencyAccount.setPreferredSize( new Dimension( 340, 60 ) );

		// nome
		pnlNameContainer.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlName.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlName.add( lblName );
		pnlName.add( txtName );
		pnlName.setPreferredSize( new Dimension( 325, 60 ) );

		pnlNameContainer.add( pnlName );
		pnlNameContainer.setPreferredSize( new Dimension( 340, 60 ) );

		// cpf
		pnlCPFContainer.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlCPF.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlCPF.add( lblCPF );
		pnlCPF.add( txtCPF );
		pnlCPF.setPreferredSize( new Dimension( 160, 60 ) );

		pnlCPFContainer.add( pnlCPF );
		pnlCPFContainer.setPreferredSize( new Dimension( 340, 60 ) );

		// confirmar e sair
		pnlFooter.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		pnlSave.add( btnSave );
		pnlSave.setPreferredSize( new Dimension( 100, 50 ) );
		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 50 ) );

		pnlFooter.add( pnlSave );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 380, 60 ) );

		add( pnlHeaderValue );
		add( pnlAgencyAccount );
		add( pnlNameContainer );
		add( pnlCPFContainer );
		add( pnlFooter );
	}

	protected void createControls()
	{
		pnlHeaderValue = new JPanel();
		pnlValue = new JPanel();
		lblValue = new JLabel( lang.getString( "IReembolso.lblValor" ) );
		txtValue = new JTextField();
		txtValue.setPreferredSize( new Dimension( 150, 25 ) );

		pnlContainerBank = new JPanel();
		pnlBank = new JPanel();
		lblBank = new JLabel( lang.getString( "IReembolso.lblBanco" ) );
		txtBank = new JTextField();
		txtBank.setPreferredSize( new Dimension( 150, 25 ) );

		pnlAgencyAccount = new JPanel();
		pnlAgency = new JPanel();
		lblAgency = new JLabel( lang.getString( "IReembolso.lblAgencia" ) );
		txtAgency = new JTextField();
		txtAgency.setPreferredSize( new Dimension( 150, 25 ) );

		pnlAccount = new JPanel();
		lblAccount = new JLabel( lang.getString( "IReembolso.lblConta" ) );
		txtAccount = new JTextField();
		txtAccount.setPreferredSize( new Dimension( 150, 25 ) );

		pnlNameContainer = new JPanel();
		pnlName = new JPanel();
		lblName = new JLabel( lang.getString( "IReembolso.lblNome" ) );
		txtName = new JTextField();
		txtName.setPreferredSize( new Dimension( 320, 25 ) );

		pnlCPFContainer = new JPanel();
		pnlCPF = new JPanel();
		lblCPF = new JLabel( lang.getString( "IReembolso.lblCPF" ) );
		txtCPF = new JTextField();
		txtCPF.setPreferredSize( new Dimension( 150, 25 ) );

		pnlFooter = new JPanel();
		pnlSave = new JPanel();
		btnSave = new JButton( lang.getString( "IReembolso.Confirmar" ) );

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
		TicketDB model = new TicketDB( conn );
		
		model.cancel( ticket );
		
		JOptionPane.showMessageDialog( null, lang.getString("IReembolso.sucesso") );
	}

	public void Exit()
	{
		dispose();
	}

}