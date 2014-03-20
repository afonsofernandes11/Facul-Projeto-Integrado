import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;

public class IBuyPassage extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	// voo e pagamento
	private JPanel pnlFly;

	// check de ida e volta
	private JPanel pnlJustGo;
	private JCheckBox chkJustGo;

	// seleciona voo
	private JPanel pnlSelectFly;
	// ida
	private JPanel pnlGo;
	private JComboBox cmbGo;
	// volta
	private JPanel pnlBack;
	private JComboBox cmbBack;

	// forma de pagamento
	private JPanel pnlPayType;

	private ButtonGroup bgpPayType;
	private JRadioButton rdbCheck;
	private JRadioButton rdbCreditCard;

	private JPanel pnlTotal;
	private JLabel lblTotal;

	// dados dos passageiros
	private JPanel pnlPassengerData;
	
	// nome e sobrenome e nascimento
	private JPanel pnlNameLastNameBirth;
	private JPanel pnlName;
	private JLabel lblName;
	private JTextField txtName;
	private JPanel pnlLastName;
	private JLabel lblLastName;
	private JTextField txtLastName;
	private JPanel pnlDateBirth;
	private JLabel lblDateBirth;
	private JFormattedTextField txtDateBirth;

	// tipo de passageiro tratamento e add
	private JPanel pnlTypePassengerTratAdd;
	private JPanel pnlType;
	private JLabel lblTypePassenger;
	private JComboBox CmbTypePassenger;
	private String[] TypePassegers;

	private JPanel pnlFormTreatment;
	private JLabel lblFormTreatment;
	private JComboBox CmbFormTreatment;
	private String[] FormTreatments;

	private JPanel pnlAdd;
	private JButton btnAdd;

	private JPanel pnlRight;
	// dados do responsável
	private JPanel pnlAccountable;

	private JPanel pnlPhone;
	private JLabel lblPhone;
	private JTextField txtPhone;
	private JPanel pnlEmail;
	private JLabel lblEmail;
	private JTextField txtEmail;

	// passageiros
	private JPanel pnlPassengers;
	private JTable tablePassengers;

	// comprar sair
	private JPanel pnlFooter;

	private JPanel pnlBuy;
	private JButton btnBuy;
	private JPanel pnlExit;
	private JButton btnExit;
	private JPanel pnlClear;
	private JButton btnClear;

	private String[] columns;
	private PassageTableModel dataSource;

	private List<Fly> flyGo;
	private List<Fly> flyBack;
	private Connection conn;
	
	private List<TicketBuy> tickets;
	
	public IBuyPassage( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		tickets = new ArrayList<TicketBuy>();
		
		initialize();
	}

	protected void initialize()
	{
		createScreen();
		createValuesCmb();
		createColumns();
		createData();
		createLayout();
		createControls();
		addControls();
		createActions();
		loadFly( cmbGo );
	}

	private void createValuesCmb()
	{
		TypePassegers = new String[3];
		TypePassegers[0] = lang.getString( "ICompraPassagem.bebe" );
		TypePassegers[1] = lang.getString( "ICompraPassagem.adulto" );
		TypePassegers[2] = lang.getString( "ICompraPassagem.velhinho" );

		FormTreatments = new String[3];
		FormTreatments[0] = lang.getString( "ICompraPassagem.senhor" );
		FormTreatments[1] = lang.getString( "ICompraPassagem.senhora" );
		FormTreatments[2] = lang.getString( "ICompraPassagem.senhorita" );
	}

	private void createData()
	{
		dataSource = new PassageTableModel( columns, lang );
	}

	private void createColumns()
	{
		columns = new String[2];
		columns[0] = lang.getString( "ICompraPassagem.passageiro" );
		columns[1] = lang.getString( "ICompraPassagem.valor" );
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 782, 620 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "ICompraPassagem.titulo" ) );
	}

	protected void addControls()
	{
		add( getPanelFlyPay());
		add( getPanelPassengerData() );
		add( getPanelAccountable() );
		add( getPanelPassengers() );
		add( getPanelFooter() );
	}
	
	private void loadFly( JComboBox cmb )
	{
		FlyDB model = new FlyDB( conn );
		Fly sample; 
		flyGo = model.seek( "dataPartida > curtime()" );
		DecimalFormat mask = new DecimalFormat(lang.getString("mask.dinheiro")); 
		
		for(int i = 0; i < flyGo.size();i++)
		{
			sample = flyGo.get( i );
			cmb.addItem( sample.getCode() + "|" + 
						 sample.getSource() + " >> " + 
						 sample.getDestiny() + " - " + 
						 sample.getDateTime() + " [ " + 
						 sample.getQtdeScales() + " ] " + 
						 mask.format( sample.getValue() )
						 );
		}
	}
	
	private void loadDestinyFly()
	{
		FlyDB model = new FlyDB( conn );
		Fly sample; 
		DecimalFormat mask = new DecimalFormat(lang.getString("mask.dinheiro")); 
		
		sample = getFly( cmbGo.getSelectedItem().toString() );
		
		flyBack = model.seek( "dataPartida > '" + sample.getFormatDate() + "' AND destino = '" + sample.getSource() + "'"  );
		
		cmbBack.removeAllItems();
		
		for(int i = 0; i < flyBack.size();i++)
		{
			sample = flyBack.get( i );
			cmbBack.addItem( sample.getCode() + "|" + 
							 sample.getSource() + " << " + 
							 sample.getDestiny() + " - " + 
							 sample.getDateTime() + " [ " + 
							 sample.getQtdeScales() + " ] " +
							 mask.format( sample.getValue() )
							 );
		}
	}
	
	private JPanel getPanelFlyPay()
	{
		Border borderFly;
		Border borderGo;
		Border borderBack;
		Border borderTotal;

		borderFly = BorderFactory.createTitledBorder( lang.getString( "ICompraPassagem.gpbVoo" ) );
		pnlFly.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlFly.setBorder( borderFly );
		
		//check de ida e volta
		pnlJustGo.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlJustGo.add( chkJustGo );
		pnlJustGo.setPreferredSize( new Dimension( 380, 30 ) );
		chkJustGo.setSelected( true );
		
		// seleciona voo
		pnlSelectFly.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		// ida
		borderGo = BorderFactory.createTitledBorder( lang.getString( "ICompraPassagem.gpbIda" ) );
		pnlGo.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlGo.setBorder( borderGo );
		pnlGo.add( cmbGo );
		cmbGo.setPreferredSize( new Dimension( 350, 25 ) );
		pnlGo.setPreferredSize( new Dimension( 390, 60 ) );
		
		// volta
		borderBack = BorderFactory.createTitledBorder( lang.getString( "ICompraPassagem.gpbVolta" ) );
		pnlBack.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlBack.setBorder( borderBack );
		pnlBack.add( cmbBack );
		cmbBack.setPreferredSize( new Dimension( 350, 25 ) );
		pnlBack.setPreferredSize( new Dimension( 390, 60 ) );
		pnlBack.setVisible( false );
		
		borderTotal = BorderFactory.createTitledBorder( lang.getString( "ICompraPassagem.gpbTotal" ) );
		pnlTotal.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlTotal.setBorder( borderTotal );
		pnlTotal.add( lblTotal );
		pnlTotal.setPreferredSize( new Dimension( 220, 60 ) );
		
		pnlSelectFly.add( pnlJustGo );
		pnlSelectFly.add( pnlGo );
		pnlSelectFly.add( pnlBack );
		pnlSelectFly.add( pnlTotal );
		pnlSelectFly.setPreferredSize( new Dimension( 400, 180 ) );
	
		pnlRight = new JPanel();
		pnlRight.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlRight.add( getPanelPayType() );
		pnlRight.add( pnlTotal );
		pnlRight.setPreferredSize( new Dimension( 260, 180 ) );
		pnlRight.setBorder( new EmptyBorder( 0, 10, 0, 0 ) );

		
		pnlFly.add( pnlSelectFly );
		pnlFly.add( pnlRight);
		pnlFly.setPreferredSize( new Dimension( 690, 220 ) );
		
		return pnlFly;
	}

	private JPanel getPanelPassengerData()
	{
		Border borderPassagerData;

		pnlPassengerData.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		// nome sobrenome nascimento
		pnlNameLastNameBirth.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlName.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlLastName.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlDateBirth.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlName.add( lblName );
		pnlName.add( txtName );
		pnlName.setPreferredSize( new Dimension( 115, 60 ) );

		pnlLastName.add( lblLastName );
		pnlLastName.add( txtLastName );
		pnlLastName.setPreferredSize( new Dimension( 115, 60 ) );

		pnlDateBirth.add( lblDateBirth );
		pnlDateBirth.add( txtDateBirth );
		pnlDateBirth.setPreferredSize( new Dimension( 125, 60 ) );

		pnlNameLastNameBirth.add( pnlName );
		pnlNameLastNameBirth.add( pnlLastName );
		pnlNameLastNameBirth.add( pnlDateBirth );
		pnlNameLastNameBirth.setPreferredSize( new Dimension( 450, 60 ) );

		// tipo de passageiro e tratamento add
		pnlTypePassengerTratAdd.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlType.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlFormTreatment.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlAdd.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlType.add( lblTypePassenger );
		pnlType.add( CmbTypePassenger );
		pnlType.setPreferredSize( new Dimension( 115, 60 ) );

		pnlFormTreatment.add( lblFormTreatment );
		pnlFormTreatment.add( CmbFormTreatment );
		pnlFormTreatment.setPreferredSize( new Dimension( 115, 60 ) );

		pnlAdd.add( btnAdd );
		pnlAdd.setPreferredSize( new Dimension( 115, 60 ) );

		pnlTypePassengerTratAdd.add( pnlType );
		pnlTypePassengerTratAdd.add( pnlFormTreatment );
		pnlTypePassengerTratAdd.add( pnlAdd );
		pnlTypePassengerTratAdd.setPreferredSize( new Dimension( 450, 60 ) );

		// adicionando todos paineis de containers
		pnlPassengerData.add( pnlNameLastNameBirth );
		pnlPassengerData.add( pnlTypePassengerTratAdd );
		pnlPassengerData.setPreferredSize( new Dimension( 470, 160 ) );

		borderPassagerData = BorderFactory.createTitledBorder( lang
				.getString( "ICompraPassagem.gpbDadosPassageiro" ) );
		pnlPassengerData.setBorder( borderPassagerData );

		return pnlPassengerData;
	}

	private JPanel getPanelAccountable()
	{
		Border borderAccountable;

		pnlAccountable.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlPhone.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlEmail.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlPhone.add( lblPhone );
		pnlPhone.add( txtPhone );
		pnlPhone.setPreferredSize( new Dimension( 200, 55 ) );

		pnlEmail.add( lblEmail );
		pnlEmail.add( txtEmail );
		pnlEmail.setPreferredSize( new Dimension( 200, 55 ) );

		pnlAccountable.add( pnlPhone );
		pnlAccountable.add( pnlEmail );
		pnlAccountable.setPreferredSize( new Dimension( 220, 160 ) );

		borderAccountable = BorderFactory.createTitledBorder( lang
				.getString( "ICompraPassagem.gpbResponsavel" ) );

		pnlAccountable.setBorder( borderAccountable );

		return pnlAccountable;
	}

	private JPanel getPanelPayType()
	{
		Border borderPayType;

		pnlPayType.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		bgpPayType = new ButtonGroup();
		bgpPayType.add( rdbCheck );
		bgpPayType.add( rdbCreditCard );

		rdbCheck.setSelected( true );

		pnlPayType.add( rdbCheck );
		pnlPayType.add( rdbCreditCard );

		pnlPayType.setPreferredSize( new Dimension( 220, 60 ) );

		borderPayType = BorderFactory.createTitledBorder( lang
				.getString( "ICompraPassagem.gpbFormaPagamento" ) );
		pnlPayType.setBorder( borderPayType );

		return pnlPayType;
	}

	private JPanel getPanelPassengers()
	{
		pnlPassengers.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlPassengers.setLayout(new GridLayout(1,1 ));
		
		JScrollPane scrool = new JScrollPane( tablePassengers );
		
		pnlPassengers.add( scrool );
		pnlPassengers.setPreferredSize( new Dimension( 690, 100 ) );

		return pnlPassengers;
	}

	private JPanel getPanelFooter()
	{
		pnlFooter.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlBuy.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		
		pnlBuy.add( btnBuy );
		pnlBuy.setPreferredSize( new Dimension( 100, 60 ) );

		pnlClear.add( btnClear);
		pnlClear.setPreferredSize( new Dimension( 200, 60 ) );

		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 350, 60 ) );
		
		pnlFooter.add( pnlBuy );
		pnlFooter.add( pnlClear );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 690, 60 ) );

		return pnlFooter;
	}

	protected void createControls()
	{
		createPanelFly();
		createPanelPassengerData();
		createPanelAccountable();
		createPanelPassengers();
		createPanelFooter();
	}

	private void createPanelFly()
	{
		pnlFly = new JPanel();

		// check de ida e volta
		pnlJustGo = new JPanel();
		chkJustGo = new JCheckBox( lang.getString("ICompraPassagem.checkSoIda") );

		// seleciona voo
		pnlSelectFly = new JPanel();
		// ida
		pnlGo = new JPanel();
		cmbGo = new JComboBox();
		
		// volta
		pnlBack = new JPanel();
		cmbBack = new JComboBox();

		createPanelPayType();
		
		pnlTotal = new JPanel();
		lblTotal = new JLabel("0,00");
	}
	
	private Fly getFly( String item )
	{
		int Code = Integer.parseInt(item.split( "\\|")[0] );
		Fly fly = null;
		
		for ( int i = 0; i < flyGo.size(); i++ )
		{
			if( flyGo.get( i ).getCode() == Code)
			{
				fly = flyGo.get( i );
			}
		}
		
		return fly;
	}
	
	private void createPanelPassengerData()
	{
		// nome e sobrenome e data de nascimento
		pnlPassengerData = new JPanel();
		pnlNameLastNameBirth = new JPanel();

		pnlName = new JPanel();
		lblName = new JLabel( lang.getString( "ICompraPassagem.lblNome" ) );
		txtName = new JTextField();
		txtName.setPreferredSize( new Dimension( 110, 25 ) );

		pnlLastName = new JPanel();
		lblLastName = new JLabel(
				lang.getString( "ICompraPassagem.lblSobreNome" ) );
		txtLastName = new JTextField();
		txtLastName.setPreferredSize( new Dimension( 110, 25 ) );

		// tipo de passageiro tratamento e adicionar
		pnlTypePassengerTratAdd = new JPanel();

		pnlType = new JPanel();
		lblTypePassenger = new JLabel(
				lang.getString( "ICompraPassagem.lblTipoPassageiro" ) );
		CmbTypePassenger = new JComboBox( TypePassegers );
		CmbTypePassenger.setPreferredSize( new Dimension( 110, 25 ) );

		pnlDateBirth = new JPanel();
		lblDateBirth = new JLabel(
				lang.getString( "ICompraPassagem.lblDataNascimento" ) );
		
		try
		{
			MaskFormatter msk = new MaskFormatter( "##/##/####" ) ;
			
			txtDateBirth = new JFormattedTextField( msk );
		}
		catch ( Exception ex )
		{
			
		}
		txtDateBirth.setPreferredSize( new Dimension( 110, 25 ) );

		pnlFormTreatment = new JPanel();
		lblFormTreatment = new JLabel(
				lang.getString( "ICompraPassagem.lblFormaTratamento" ) );
		CmbFormTreatment = new JComboBox( FormTreatments );
		pnlAdd = new JPanel();
		btnAdd = new JButton( lang.getString( "ICompraPassagem.btnAdicionar" ) );
	}

	private void createPanelAccountable()
	{
		pnlAccountable = new JPanel();
		pnlPhone = new JPanel();
		lblPhone = new JLabel( lang.getString( "ICompraPassagem.lblTelefone" ) );
		txtPhone = new JTextField();
		txtPhone.setPreferredSize( new Dimension( 190, 25 ) );

		pnlEmail = new JPanel();
		lblEmail = new JLabel( lang.getString( "ICompraPassagem.lblEmail" ) );
		txtEmail = new JTextField();
		txtEmail.setPreferredSize( new Dimension( 190, 25 ) );
	}

	private void createPanelPayType()
	{
		pnlPayType = new JPanel();

		bgpPayType = new ButtonGroup();
		rdbCheck = new JRadioButton(
				lang.getString( "ICompraPassagem.radioCheque" ) );
		rdbCreditCard = new JRadioButton(
				lang.getString( "ICompraPassagem.radioCartaoCredito" ) );
	}

	private void createPanelPassengers()
	{
		pnlPassengers = new JPanel();
		tablePassengers = new JTable( dataSource );
	}

	private void createPanelFooter()
	{
		pnlFooter = new JPanel();

		pnlBuy = new JPanel();
		btnBuy = new JButton( lang.getString( "ICompraPassagem.btnComprar" ) );
		pnlExit = new JPanel();
		btnExit = new JButton( lang.getString( "btnSair" ) );
		
		pnlClear = new JPanel();
		btnClear = new JButton( lang.getString("ICompraPassagem.btnLimpar"));
	}

	private void createActions()
	{
		btnBuy.addActionListener( this );
		btnAdd.addActionListener( this );
		btnExit.addActionListener( this );
		chkJustGo.addActionListener( this );
		cmbGo.addActionListener( this );
		btnClear.addActionListener( this );
		cmbBack.addActionListener( this );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnBuy )
		{
			Buy();
		}
		else if ( e.getSource() == btnExit )
		{
			Exit();
		}
		else if ( e.getSource() == btnAdd )
		{
			addPasseger();
		}
		else if ( e.getSource() == chkJustGo )
		{
			rulesJustGo();
			clearPassagers();
		}
		else if( e.getSource() == cmbGo )
		{
			loadDestinyFly();
			clearPassagers();
		}
		else if( e.getSource() == cmbBack || e.getSource() == btnClear )
		{
			clearPassagers();
		}
	}
	
	private void clearPassagers()
	{
		dataSource.dados.clear();
		dataSource.fireTableDataChanged();
		tickets.clear();
		
		CalcValues();
	}
	
	public void rulesJustGo()
	{
		pnlBack.setVisible( !chkJustGo.isSelected() );
	}

	public void addPasseger()
	{
		PassageTableModel dataSource = (PassageTableModel) tablePassengers.getModel();
		TicketBuy ticketGo;
		TicketBuy ticketBack;
		Passage p;
		
		p = getPassage();
		ticketGo = getTicket( p, getFly( cmbGo.getSelectedItem().toString()));
		
		tickets.add( ticketGo );
		
		if( !chkJustGo.isSelected() )
		{
			ticketBack = getTicket( p, getFly( cmbBack.getSelectedItem().toString()) );
			tickets.add( ticketBack );
			
			ticketGo.setValueBack( ticketBack.getValue() );
		}
		
		dataSource.addRow( ticketGo );
		
		CalcValues();
		JOptionPane.showMessageDialog( null, lang.getString("ICompraPassagem.mensagem.addPassageiro"));
		clearPasseger();
	}

	private Passage getPassage()
	{
		Passage p = new Passage();
		
		p.setName( txtName.getText() );
		p.setProfile( CmbTypePassenger.getSelectedIndex() );
		p.setFormTreatment( CmbFormTreatment.getSelectedIndex() );
		p.setLastName( txtLastName.getText() );
	
		return p;
	}
	
	private TicketBuy getTicket( Passage p, Fly f)
	{
		TicketBuy ticket = new TicketBuy();
		double value;
		double taxa;
		
		ticket.setPassage( p );
		ticket.setType( "C" );
		ticket.setFly( f );
		
		value = f.getValue();
		taxa = value * 0.8;
		
		switch( p.getProfile() )
		{
			case 0:
				ticket.setValue( 0 );
				ticket.setPercent( taxa );
				break;
				
			case 1:
				ticket.setValue( value * 0.65 );
				ticket.setPercent( taxa );
			break;
			
			case 2:
				ticket.setValue( value );
				ticket.setPercent( taxa );
			break;
		}
		
		return ticket;
	}
	
	private void clearPasseger()
	{
		txtName.setText( "" );
		txtLastName.setText( "" );
		txtDateBirth.setValue( null );
		
		CmbTypePassenger.setSelectedIndex( 0 );
		CmbFormTreatment.setSelectedIndex( 0 );
	}
	
	private void CalcValues()
	{
		double valueTotal = 0.00;
		
		for (TicketBuy ticket : tickets )
		{
			valueTotal += ticket.getValue();
		}
		
		DecimalFormat mask = new DecimalFormat(lang.getString("mask.dinheiro")); 
		
		lblTotal.setText( mask.format( valueTotal ) );
	}

	public void Buy()
	{
		if ( rdbCheck.isSelected() )
		{
			ICheck frm = new ICheck( this, lang, conn );
			frm.show();
			
			if( frm.isValidate() )
			{
				InsertTickets( frm.getCheck(), null );
			}
			else	
			{
				JOptionPane.showMessageDialog( null, lang.getString("ICompraPassagem.pagamentoInvalido") );
			}
		}
		else
		{
			ICreditCard frm = new ICreditCard( this, lang );
			frm.show();
			
			if( frm.isValidate() )
			{
				InsertTickets( null, frm.getCreditCard() );
			}
			else	
			{
				JOptionPane.showMessageDialog( null, lang.getString("ICompraPassagem.pagamentoInvalido") );
			}
		}
		
		dispose();
	}
	
	public void InsertTickets( Check check, CreditCard card )
	{
		TicketDB model;
		int idMain = -1;
		int idTemp;
		Payment pay = new Payment();
		PaymentDB modelPay;
		
		model = new TicketDB( conn );
		modelPay = new PaymentDB( conn );
		
		if( check != null )
		{
			pay.setCheck( check );
		}
		else 
		{
			pay.setCreditCard( card );
		}
		
		for( TicketBuy ticket : tickets )
		{
			ticket.setIdMain( idMain );
			
			idTemp = model.insert( ticket );
			
			pay.setTicket( ticket );
			
			modelPay.insert( pay );
			
			if( idMain == -1 )
			{
				idMain = idTemp;
			}
		}
	}

	public void Exit()
	{
		dispose();
	}

}