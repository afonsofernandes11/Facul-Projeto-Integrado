import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.border.*;

public class ICheckin extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	// container da esquerda agrupr tipo de passagem e número da passagem
	private JPanel pnlLeft;

	// tipo passagem
	private JPanel pnlPassageType;

	private ButtonGroup bgpPassageType;
	private JRadioButton rdbNormal;
	private JRadioButton rdbReserve;

	// número da passagem
	private JPanel pnlNumberPassage;
	private JLabel lblNumberPassage;
	private JTextField txtNumberPassage;

	private JPanel pnlPlace;
	private JLabel lblPlace;
	private JComboBox cmbPlace;

	// dados dos passageiros
	private JPanel pnlPassengerData;

	// nome e sobrenome
	private JPanel pnlNameLastName;
	private JPanel pnlName;
	private JLabel lblName;
	private JTextField txtName;
	private JPanel pnlLastName;
	private JLabel lblLastName;
	private JTextField txtLastName;

	// tipo de passageiro e data de nascimento
	private JPanel pnlTypePassengerDateBirth;
	private JPanel pnlType;
	private JLabel lblTypePassenger;
	private JComboBox CmbTypePassenger;
	private String[] TypePassegers;
	private JPanel pnlDateBirth;
	private JLabel lblDateBirth;
	private JTextField txtDateBirth;

	// forma de tratamento e botão adicionar
	private JPanel pnlFormTreatmentAdd;
	private JPanel pnlFormTreatment;
	private JLabel lblFormTreatment;
	private JComboBox CmbFormTreatment;
	private String[] FormTreatments;

	// comprar sair
	private JPanel pnlFooter;

	private JPanel pnlCheckin;
	private JButton btnCheckin;
	private JPanel pnlExit;
	private JButton btnExit;

	private Ticket ticket;
	private Connection conn;

	public ICheckin( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;

		initialize();
	}

	public ICheckin( JFrame Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		initialize();
	}

	protected void initialize()
	{
		createScreen();
		createValuesCmb();
		createLayout();
		createControls();
		addControls();
		createActions();
	}

	private void createValuesCmb()
	{
		TypePassegers = new String[3];
		TypePassegers[0] = lang.getString( "ICheckin.bebe" );
		TypePassegers[1] = lang.getString( "ICheckin.adulto" );
		TypePassegers[2] = lang.getString( "ICheckin.velhinho" );

		FormTreatments = new String[3];
		FormTreatments[0] = lang.getString( "ICheckin.senhor" );
		FormTreatments[1] = lang.getString( "ICheckin.senhora" );
		FormTreatments[2] = lang.getString( "ICheckin.senhorita" );
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 666, 321 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "ICheckin.titulo" ) );
	}

	protected void addControls()
	{
		add( getPanelLeft() );
		add( getPanelPassengerData() );
		add( getPanelFooter() );
	}

	private JPanel getPanelPassengerData()
	{
		Border borderPassagerData;

		pnlPassengerData.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlNameLastName.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlName.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlLastName.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlName.add( lblName );
		pnlName.add( txtName );
		pnlName.setPreferredSize( new Dimension( 115, 60 ) );

		pnlLastName.add( lblLastName );
		pnlLastName.add( txtLastName );
		pnlLastName.setPreferredSize( new Dimension( 115, 60 ) );

		pnlNameLastName.add( pnlName );
		pnlNameLastName.add( pnlLastName );
		pnlNameLastName.setPreferredSize( new Dimension( 350, 60 ) );

		// tipo de passageiro e data de nascimento
		pnlTypePassengerDateBirth
				.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlType.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlDateBirth.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlType.add( lblTypePassenger );
		pnlType.add( CmbTypePassenger );
		pnlType.setPreferredSize( new Dimension( 115, 60 ) );

		pnlDateBirth = new JPanel();
		pnlDateBirth.add( lblDateBirth );
		pnlDateBirth.add( txtDateBirth );
		pnlDateBirth.setPreferredSize( new Dimension( 115, 60 ) );

		pnlTypePassengerDateBirth.add( pnlType );
		pnlTypePassengerDateBirth.add( pnlDateBirth );
		pnlTypePassengerDateBirth.setPreferredSize( new Dimension( 350, 60 ) );

		// forma de tratamento e botão adicionar
		pnlFormTreatmentAdd.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlFormTreatment.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlFormTreatment.add( lblFormTreatment );
		pnlFormTreatment.add( CmbFormTreatment );
		pnlFormTreatment.setPreferredSize( new Dimension( 115, 60 ) );

		pnlFormTreatmentAdd.add( pnlFormTreatment );
		pnlFormTreatmentAdd.setPreferredSize( new Dimension( 350, 60 ) );

		// adicionando todos paineis de containers
		pnlPassengerData.add( pnlNameLastName );
		pnlPassengerData.add( pnlTypePassengerDateBirth );
		pnlPassengerData.add( pnlFormTreatmentAdd );
		pnlPassengerData.setPreferredSize( new Dimension( 370, 220 ) );

		borderPassagerData = BorderFactory.createTitledBorder( lang
				.getString( "ICheckin.gpbDadosPassageiro" ) );
		pnlPassengerData.setBorder( borderPassagerData );

		return pnlPassengerData;
	}

	private JPanel getPanelLeft()
	{
		Border borderTypePassage = BorderFactory.createTitledBorder( lang
				.getString( "ICheckin.gpbTipoPassagem" ) );

		pnlLeft.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlPassageType.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		bgpPassageType = new ButtonGroup();
		bgpPassageType.add( rdbNormal );
		bgpPassageType.add( rdbReserve );

		rdbNormal.setSelected( true );

		pnlPassageType.add( rdbNormal );
		pnlPassageType.add( rdbReserve );

		pnlPassageType.setPreferredSize( new Dimension( 250, 65 ) );
		pnlPassageType.setBorder( borderTypePassage );

		pnlNumberPassage.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlNumberPassage.add( lblNumberPassage );
		pnlNumberPassage.add( txtNumberPassage );
		pnlNumberPassage.setPreferredSize( new Dimension( 250, 65 ) );

		pnlPlace.add( lblPlace );
		pnlPlace.add( cmbPlace );

		pnlPlace.setPreferredSize( new Dimension( 250, 65 ) );

		pnlLeft.add( pnlPassageType );
		pnlLeft.add( pnlNumberPassage );
		pnlLeft.add( pnlPlace );
		pnlLeft.setPreferredSize( new Dimension( 255, 220 ) );

		return pnlLeft;
	}

	private void loadPlaces()
	{
		String[] places = new String[ticket.getFly().getAircrafit()
				.getQtdeAssentos()];
		TicketDB model = new TicketDB( conn );

		List<Ticket> placesUsed = model.seek( " voo.idVoo =  "
				+ ticket.getFly().getCode() );

		for ( int i = 0; i < places.length; i++ )
		{
			if ( !containPlaces( placesUsed, i ) )
			{
				cmbPlace.addItem( "" + ( i + 1 ) );
			}
		}
	}

	private Boolean containPlaces( List<Ticket> list, int place )
	{
		for ( int i = 0; i < list.size(); i++ )
		{
			if ( list.get( i ).getPlace() == place )
			{
				return true;
			}
		}

		return false;
	}

	private JPanel getPanelFooter()
	{
		pnlFooter.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		pnlCheckin.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlCheckin.add( btnCheckin );
		pnlCheckin.setPreferredSize( new Dimension( 100, 60 ) );

		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 60 ) );

		pnlFooter.add( pnlCheckin );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 500, 60 ) );

		return pnlFooter;
	}

	protected void createControls()
	{
		createPanelLeft();
		createPanelPassengerData();
		createPanelFooter();
		createPanelPlace();
	}

	private void createPanelPassengerData()
	{
		// nome e sobrenome
		pnlPassengerData = new JPanel();
		pnlNameLastName = new JPanel();

		pnlName = new JPanel();
		lblName = new JLabel( lang.getString( "ICheckin.lblNome" ) );
		txtName = new JTextField();
		txtName.setPreferredSize( new Dimension( 110, 25 ) );

		pnlLastName = new JPanel();
		lblLastName = new JLabel( lang.getString( "ICheckin.lblSobreNome" ) );
		txtLastName = new JTextField();
		txtLastName.setPreferredSize( new Dimension( 110, 25 ) );

		// tipo de passageiro e data de nascimento
		pnlTypePassengerDateBirth = new JPanel();

		pnlType = new JPanel();
		lblTypePassenger = new JLabel(
				lang.getString( "ICheckin.lblTipoPassageiro" ) );
		CmbTypePassenger = new JComboBox( TypePassegers );
		CmbTypePassenger.setPreferredSize( new Dimension( 110, 25 ) );

		pnlDateBirth = new JPanel();
		lblDateBirth = new JLabel(
				lang.getString( "ICheckin.lblDataNascimento" ) );
		txtDateBirth = new JTextField();
		txtDateBirth.setPreferredSize( new Dimension( 110, 25 ) );

		// forma de tratamento e botão adicionar
		pnlFormTreatmentAdd = new JPanel();
		pnlFormTreatment = new JPanel();
		lblFormTreatment = new JLabel(
				lang.getString( "ICheckin.lblFormaTratamento" ) );
		CmbFormTreatment = new JComboBox( FormTreatments );

	}

	private void createPanelLeft()
	{
		pnlLeft = new JPanel();

		createPanelPassageType();
		createPanelNumberPassage();
	}

	private void createPanelPassageType()
	{
		pnlPassageType = new JPanel();

		bgpPassageType = new ButtonGroup();
		rdbNormal = new JRadioButton( lang.getString( "ICheckin.radioNormal" ) );
		rdbReserve = new JRadioButton( lang.getString( "ICheckin.radioReserva" ) );
	}

	private void createPanelPlace()
	{
		pnlPlace = new JPanel();

		lblPlace = new JLabel( lang.getString( "ICheckin.lblLugar" ) );
		cmbPlace = new JComboBox();
		cmbPlace.setPreferredSize( new Dimension( 170, 25 ) );
	}

	private void createPanelNumberPassage()
	{
		pnlNumberPassage = new JPanel();

		lblNumberPassage = new JLabel(
				lang.getString( "ICheckin.lblNumeroPassagem" ) );
		txtNumberPassage = new JTextField();
		txtNumberPassage.setPreferredSize( new Dimension( 200, 25 ) );

	}

	private void createPanelFooter()
	{
		pnlFooter = new JPanel();

		pnlCheckin = new JPanel();
		btnCheckin = new JButton( lang.getString( "ICheckin.btnCheckin" ) );
		pnlExit = new JPanel();
		btnExit = new JButton( lang.getString( "btnSair" ) );
	}

	private void createActions()
	{
		btnCheckin.addActionListener( this );
		btnExit.addActionListener( this );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnCheckin )
		{
			Checkin();
		}
		else if ( e.getSource() == btnExit )
		{
			Exit();
		}
	}

	public void Checkin()
	{
		TicketDB model = new TicketDB( conn );
		model.checkIn( ticket, cmbPlace.getSelectedIndex() );

		JOptionPane.showMessageDialog( null,
				lang.getString( "ICheckin.checkinsucess" ) );

		dispose();
	}

	public void Exit()
	{
		dispose();
	}

	public void setTicket( Ticket ticket )
	{
		this.ticket = ticket;

		txtName.setText( ticket.getPassage().getName() );
		txtLastName.setText( ticket.getPassage().getLastName() );
		txtDateBirth.setText( ticket.getPassage().getStrDateBirth() );
		CmbFormTreatment.setSelectedIndex( ticket.getPassage()
				.getFormTreatment() );
		CmbTypePassenger.setSelectedIndex( ticket.getPassage().getProfile() );

		txtNumberPassage.setText( "" + ticket.getCode() );

		loadPlaces();
	}

}