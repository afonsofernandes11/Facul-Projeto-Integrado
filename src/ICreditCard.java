import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;

public class ICreditCard extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	private JPanel pnlHeaderType;
	private JPanel pnlType;
	private JLabel lblType;
	private JComboBox cmbType;

	private JPanel pnlNumberCode;
	private JPanel pnlNumber;
	private JLabel lblNumber;
	private JTextField txtNumber;
	private JPanel pnlCode;
	private JLabel lblCode;
	private JTextField txtCode;

	private JPanel pnlContainerDateTime;
	private JPanel pnlDateTime;
	private JLabel lblDateTime;
	private JFormattedTextField txtDateTime;

	private JPanel pnlFooter;
	private JPanel pnlSave;
	private JButton btnSave;
	private JPanel pnlExit;
	private JButton btnExit;

	private String[] CreditCards = { "VISA", "MASTERCARD", "DINNERS",
			"AMERICAN EXPRESS", "ELO", "AURA", "SOROCRED" };

	private int mode;
	private Boolean isValidate;
	
	public ICreditCard( JDialog Main, ResourceBundle language ){
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

	public void setMode( int newMode )
	{
		mode = newMode;
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout() );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 420, 300 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "ICreditCard.titulo" ) );
	}

	protected void addControls()
	{
		// tipo de cartão de crédito
		pnlHeaderType.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlType.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlType.add( lblType );
		pnlType.add( cmbType );
		pnlType.setPreferredSize( new Dimension( 210, 60 ) );

		pnlHeaderType.add( pnlType );
		pnlHeaderType.setPreferredSize( new Dimension( 380, 60 ) );

		// número do cartão e código de segurança
		pnlNumberCode.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlNumber.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlCode.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlNumber.add( lblNumber );
		pnlNumber.add( txtNumber );
		pnlNumber.setPreferredSize( new Dimension( 210, 60 ) );

		pnlCode.add( lblCode );
		pnlCode.add( txtCode );
		pnlCode.setPreferredSize( new Dimension( 150, 60 ) );

		pnlNumberCode.add( pnlNumber );
		pnlNumberCode.add( pnlCode );
		pnlNumberCode.setPreferredSize( new Dimension( 380, 60 ) );

		pnlContainerDateTime.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlDateTime.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlDateTime.add( lblDateTime );
		pnlDateTime.add( txtDateTime );
		pnlDateTime.setPreferredSize( new Dimension( 120, 60 ) );

		pnlContainerDateTime.add( pnlDateTime );
		pnlContainerDateTime.setPreferredSize( new Dimension( 380, 60 ) );

		pnlFooter.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		pnlSave.add( btnSave );
		pnlSave.setPreferredSize( new Dimension( 100, 50 ) );
		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 50 ) );

		pnlFooter.add( pnlSave );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 380, 60 ) );

		add( pnlHeaderType );
		add( pnlNumberCode );
		add( pnlContainerDateTime );
		add( pnlFooter );
	}

	protected void createControls()
	{
		pnlHeaderType = new JPanel();
		pnlType = new JPanel();
		lblType = new JLabel( lang.getString( "ICreditCard.lblBandeiraCartao" ) );
		cmbType = new JComboBox( CreditCards );
		cmbType.setPreferredSize( new Dimension( 200, 25 ) );

		pnlNumberCode = new JPanel();
		pnlNumber = new JPanel();
		lblNumber = new JLabel( lang.getString( "ICreditCard.lblNumeroCartao" ) );
		txtNumber = new JTextField();
		txtNumber.setPreferredSize( new Dimension( 200, 25 ) );

		pnlCode = new JPanel();
		lblCode = new JLabel( lang.getString( "ICreditCard.lblCodigoSeguranca" ) );
		txtCode = new JTextField();
		txtCode.setPreferredSize( new Dimension( 90, 25 ) );

		pnlContainerDateTime = new JPanel();
		pnlDateTime = new JPanel();
		lblDateTime = new JLabel(
				lang.getString( "ICreditCard.lblDataVencimento" ) );

		try
		{
			MaskFormatter msk = new MaskFormatter( "##/####" );

			txtDateTime = new JFormattedTextField( msk );
		}
		catch ( Exception ex )
		{

		}
		
		txtDateTime.setPreferredSize( new Dimension( 110, 25 ) );

		pnlFooter = new JPanel();
		pnlSave = new JPanel();
		btnSave = new JButton( lang.getString( "ICreditCard.Validar" ) );

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
		if ( validateScreen() )
		{
			isValidate =  true;
			dispose();
		}
	}
	
	public Boolean isValidate()
	{
		return isValidate;
	}

	public CreditCard getCreditCard()
	{
		CreditCard credit = new CreditCard();

		credit.setCode( txtCode.getText() );
		credit.setNumber( txtNumber.getText() );
		credit.setOperadora( cmbType.getSelectedItem().toString() );
		credit.setValidate( GeralFunctions.formataData( txtDateTime.getText(),
				"dd/MM" ) );

		return credit;
	}

	private Boolean validateScreen()
	{
		Boolean retorno = false;

		if ( GeralFunctions.IsEmpty( txtNumber.getText() ) )
		{
			JOptionPane
					.showMessageDialog(
							null,
							lang.getString( "ICreditCard.mensagem.numero.obrigatorio" ) );
		}
		else if ( GeralFunctions.IsEmpty( txtCode.getText() ) )
		{
			JOptionPane
					.showMessageDialog(
							null,
							lang.getString( "ICreditCard.mensagem.codigo.obrigatorio" ) );
		}
		else if ( !GeralFunctions.IsDate( txtDateTime.getText(), "dd/MM" ) )
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "ICreditCard.mensagem.data.invalido" ) );
		}
		else if ( !getCreditCard().validate() )
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "ICreditCard.mensagem.cartao.autorizado" ) );
		}
		else
		{
			retorno = true;
		}

		return retorno;
	}

	public void Exit()
	{
		dispose();
	}

}