import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class ITransfer extends JDialog implements ActionListener
{
	// voo
	private JPanel pnlFly;

	// seleciona voo
	private JPanel pnlSelectFly;
	// Voo
	private JPanel pnlGo;
	private JComboBox cmbGo;

	// Novo Voo
	private JPanel pnlNewFly;
	private JComboBox cmbNewFly;

	// trasnferir sair
	private JPanel pnlFooter;

	private JPanel pnlTransfer;
	private JButton btnTransfer;
	private JPanel pnlExit;
	private JButton btnExit;
	
	private Ticket ticket;

	private List<Fly> flyNewFly;
	private Connection conn;

	private ResourceBundle lang;

	public ITransfer( JDialog Main, ResourceBundle language, Connection conn ){
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

	protected void createControls()
	{
		createPanelFly();
		createPanelFooter();
	}

	protected void addControls()
	{
		add( getPanelFly() );
		add( getPanelFooter() );
	}

	public void setTicket( Ticket ticket )
	{
		this.ticket = ticket;
		
		addFlyToCmb( cmbGo, ticket.getFly() );
		cmbGo.setEnabled( false );

		loadFly( ticket.getFly() );
	}

	private void loadFly( Fly sample )
	{
		FlyDB model = new FlyDB( conn );

		flyNewFly = model.seek( "dataPartida > curtime() AND destino = '"
				+ sample.getDestiny().replace( "'", "''" ) + "' and idVoo <> "
				+ sample.getCode() );
		
		if ( flyNewFly != null && flyNewFly.size() > 0 )
		{
			for ( int i = 0; i < flyNewFly.size(); i++ )
			{
				sample = flyNewFly.get( i );
				addFlyToCmb( cmbNewFly, sample );
			}
		}
		else
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "ITransferencia.vazio" ) );
			dispose();
		}
	}

	private void addFlyToCmb( JComboBox cmb, Fly sample )
	{
		cmb.addItem( sample.getCode() + "|" + sample.getSource() + ">>"
				+ sample.getDestiny() + " - " + sample.getDateTime() + " [ "
				+ sample.getQtdeScales() + " ] " );
	}

	private JPanel getPanelFooter()
	{
		pnlFooter.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlTransfer.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		pnlTransfer.add( btnTransfer );
		pnlTransfer.setPreferredSize( new Dimension( 100, 60 ) );

		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 60 ) );

		pnlFooter.add( pnlTransfer );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 690, 60 ) );

		return pnlFooter;
	}

	private JPanel getPanelFly()
	{
		Border borderGo;
		Border borderBack;

		pnlFly.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		// seleciona voo
		pnlSelectFly.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		// ida
		borderGo = BorderFactory.createTitledBorder( lang
				.getString( "ITransferencia.gpbVoo" ) );
		pnlGo.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlGo.setBorder( borderGo );
		pnlGo.add( cmbGo );
		cmbGo.setPreferredSize( new Dimension( 380, 25 ) );
		pnlGo.setPreferredSize( new Dimension( 410, 60 ) );

		// volta
		borderBack = BorderFactory.createTitledBorder( lang
				.getString( "ITransferencia.gpbNovoVoo" ) );
		pnlNewFly.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		pnlNewFly.setBorder( borderBack );
		pnlNewFly.add( cmbNewFly );
		cmbNewFly.setPreferredSize( new Dimension( 380, 25 ) );
		pnlNewFly.setPreferredSize( new Dimension( 410, 60 ) );

		pnlSelectFly.add( pnlGo );
		pnlSelectFly.add( pnlNewFly );
		pnlSelectFly.setPreferredSize( new Dimension( 415, 135 ) );

		pnlFly.add( pnlSelectFly );
		pnlFly.setPreferredSize( new Dimension( 420, 145 ) );

		return pnlFly;
	}

	private void createPanelFooter()
	{
		pnlFooter = new JPanel();

		pnlTransfer = new JPanel();
		btnTransfer = new JButton(
				lang.getString( "ITransferencia.btnTransferir" ) );

		pnlExit = new JPanel();
		btnExit = new JButton( lang.getString( "btnSair" ) );
	}

	private void createPanelFly()
	{
		pnlFly = new JPanel();

		// seleciona voo
		pnlSelectFly = new JPanel();
		// ida
		pnlGo = new JPanel();
		cmbGo = new JComboBox();

		// volta
		pnlNewFly = new JPanel();
		cmbNewFly = new JComboBox();
	}

	private Fly getFly( String item )
	{
		int Code = Integer.parseInt( item.split( "\\|" )[0] );
		Fly fly = null;

		if ( flyNewFly != null && flyNewFly.size() > 0 )
		{
			for ( int i = 0; i < flyNewFly.size(); i++ )
			{
				if ( flyNewFly.get( i ).getCode() == Code )
				{
					fly = flyNewFly.get( i );
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog( null,
					lang.getString( "ITransferencia.vazio" ) );
			dispose();
		}

		return fly;
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "ITransferencia.titulo" ) );
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 470, 250 );
		setLocationRelativeTo( null );
	}

	public void createActions()
	{
		btnTransfer.addActionListener( this );
		btnExit.addActionListener( this );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnTransfer )
		{
			transfer();
			dispose();
		}
		else if ( e.getSource() == btnExit )
		{
			dispose();
		}
	}

	private void transfer()
	{
		TicketDB model = new TicketDB( conn );

		ticket.setFly( getFly( cmbNewFly.getSelectedItem().toString() ) );
		
		model.transfer( ticket );
		
		JOptionPane.showMessageDialog( null, lang.getString("ITransferencia.mensagem.concluido") );
	}
}
