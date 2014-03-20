import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.*;

public class IAircrafit extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	private JPanel pnlHeaderCode;
	private JPanel pnlCode;
	private JLabel lblCode;
	private JTextField txtCode;

	private JPanel pnlNameContainer;
	private JPanel pnlName;
	private JLabel lblName;
	private JTextField txtName;

	private JPanel pnlQtdeContainer;
	private JPanel pnlQtde;
	private JLabel lblQtde;
	private JTextField txtQtde;

	private JPanel pnlFooter;
	private JPanel pnlSave;
	private JButton btnSave;
	private JPanel pnlExit;
	private JButton btnExit;

	private Connection conn;

	private int mode;

	public IAircrafit( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;

		initialize();
	}

	public IAircrafit( JFrame Main, ResourceBundle language, Connection conn ){
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
	
	public void setMode( int newMode )
	{
		mode = newMode;

		if ( mode == 2 )
		{
			setTitle( lang.getString( "Alterar" ) + " - " + getTitle() );
		}
		else if ( mode == 3 )
		{
			setTitle( lang.getString( "Excluir" ) + " - " + getTitle() );
		}
		else
		{
			setTitle( lang.getString( "Inserir" ) + " - " + getTitle() );
		}
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout() );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 371, 293 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "IAeronave.titulo" ) );
	}

	protected void addControls()
	{
		pnlHeaderCode.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlCode.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlCode.add( lblCode );
		pnlCode.add( txtCode );
		pnlCode.setPreferredSize( new Dimension( 150, 60 ) );

		pnlHeaderCode.add( pnlCode );
		pnlHeaderCode.setPreferredSize( new Dimension( 315, 60 ) );

		pnlNameContainer.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlName.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlName.add( lblName );
		pnlName.add( txtName );
		pnlName.setPreferredSize( new Dimension( 250, 60 ) );

		pnlNameContainer.add( pnlName );
		pnlNameContainer.setPreferredSize( new Dimension( 315, 60 ) );

		pnlQtdeContainer.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlQtde.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlQtde.add( lblQtde );
		pnlQtde.add( txtQtde );
		pnlQtde.setPreferredSize( new Dimension( 120, 60 ) );

		pnlQtdeContainer.add( pnlQtde );
		pnlQtdeContainer.setPreferredSize( new Dimension( 315, 60 ) );

		pnlFooter.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		pnlSave.add( btnSave );
		pnlSave.setPreferredSize( new Dimension( 100, 50 ) );
		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 50 ) );

		pnlFooter.add( pnlSave );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 315, 60 ) );

		add( pnlHeaderCode );
		add( pnlNameContainer );
		add( pnlQtdeContainer );
		add( pnlFooter );
	}

	protected void createControls()
	{
		pnlHeaderCode = new JPanel();
		pnlCode = new JPanel();
		lblCode = new JLabel( lang.getString( "IAeronave.lblCodigo" ) );
		txtCode = new JTextField();
		txtCode.setPreferredSize( new Dimension( 110, 25 ) );
		txtCode.setEditable( false );

		pnlNameContainer = new JPanel();
		pnlName = new JPanel();
		lblName = new JLabel( lang.getString( "IAeronave.lblNome" ) );
		txtName = new JTextField();
		txtName.setPreferredSize( new Dimension( 230, 25 ) );
		txtName.setColumns( 100 );

		pnlQtdeContainer = new JPanel();
		pnlQtde = new JPanel();
		lblQtde = new JLabel( lang.getString( "IAeronave.lblQtde" ) );
		txtQtde = new JTextField();
		txtQtde.setPreferredSize( new Dimension( 110, 25 ) );
		txtQtde.setColumns( 5 );

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
		AircrafitDB aircrafitDB = new AircrafitDB( conn );

		if ( IsValidate() )
		{
			switch ( mode )
			{
				case 2:
					aircrafitDB.update( getAircrafit() );
					JOptionPane.showMessageDialog( this,
							lang.getString( "mensagem.update" ) );
				break;

				case 3:
					aircrafitDB.delete( getAircrafit() );
					JOptionPane.showMessageDialog( this,
							lang.getString( "mensagem.delete" ) );
				break;

				default:
					aircrafitDB.insert( getAircrafit() );
					JOptionPane.showMessageDialog( this,
							lang.getString( "mensagem.insert" ) );
				break;

			}

			dispose();
		}
	}

	public Boolean IsValidate()
	{
		Boolean validate = false;

		if ( GeralFunctions.IsEmpty( txtName.getText() ) )
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "IAeronave.txtNome.mensagem.obrigatorio" ) );
		}
		else if ( GeralFunctions.IsEmpty( txtQtde.getText() ) )
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "IAeronave.txtQtde.mensagem.obrigatorio" ) );
		}
		else if ( !GeralFunctions.IsInteger( txtQtde.getText() ) )
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "IAeronave.txtQtde.mensagem.numero" ) );
		}
		else
		{
			validate = true;
		}

		return validate;
	}

	public Aircrafit getAircrafit()
	{
		Aircrafit aircrafit = new Aircrafit();

		if ( !txtCode.getText().equals( "" ) )
		{
			aircrafit.setCode( Integer.parseInt( txtCode.getText() ) );
		}

		aircrafit.setName( txtName.getText() );
		aircrafit.setQtdeAssentos( Integer.parseInt( txtQtde.getText() ) );

		return aircrafit;
	}

	public void setAircrafitFields( Aircrafit aircrafit )
	{
		txtCode.setText( "" + aircrafit.getCode() );
		txtName.setText( aircrafit.getName() );
		txtQtde.setText( "" + aircrafit.getQtdeAssentos() );
	}

	public void Exit()
	{
		dispose();
	}

}