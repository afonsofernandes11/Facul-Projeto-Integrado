import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;

import java.text.Format;
import java.text.ParseException;  


public class IFly extends JDialog implements ActionListener
{
	protected ResourceBundle lang;

	private JPanel pnlHeaderCode;
	private JPanel pnlCode;
	private JLabel lblCode;
	private JTextField txtCode;

	private JPanel pnlSourceDestiny;
	private JPanel pnlSource;
	private JLabel lblSource;
	private JTextField txtSource;
	private JPanel pnlDestiny;
	private JLabel lblDestiny;
	private JTextField txtDestiny;

	private JPanel pnlScaleDateTime;
	private JPanel pnlScale;
	private JLabel lblScale;
	private JComboBox cmbScale;
	private JPanel pnlDateTime;
	private JLabel lblDateTime;
	private JTextField txtDateTime;

	private JPanel pnlAirPlaneSituation;
	private JPanel pnlAirPlane;
	private JLabel lblAirPlane;
	private JComboBox cmbAirPlane;
	private JPanel pnlSituation;
	private JLabel lblSituation;
	private JComboBox cmbSituation;
	
	private JPanel pnlValueContainer;
	private JPanel pnlValue;
	private JLabel lblValue;
	private JTextField txtValue;

	private JPanel pnlFooter;
	private JPanel pnlSave;
	private JButton btnSave;
	private JPanel pnlExit;
	private JButton btnExit;
	private List<Aircrafit> aircrafitList;

	private Connection conn;

	private String[] qtdeEscalas = { "0", "1", "2", "3", "4", "5", "6", "7" };
	private String[] statusVoo;
	private int mode;

	public IFly( JDialog Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		initialize();
	}

	public IFly( JFrame Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		initialize();
	}

	protected void initialize()
	{
		createColumns();
		createScreen();
		createLayout();
		createControls();
		addControls();
		createActions();
		loadAircrafit();
	}
	private void createColumns()
	{
		statusVoo = new String[6];
		
		statusVoo[0] = lang.getString( "status.espera" );
		statusVoo[1] = lang.getString( "status.confirmado" );
		statusVoo[2] = lang.getString( "status.cancelado");
		statusVoo[3] = lang.getString( "status.atrasado" );
		statusVoo[4] = lang.getString( "status.embarque" );
		statusVoo[5] = lang.getString( "status.finalizado" );
	}
	
	private void loadAircrafit()
	{
		AircrafitDB model = new AircrafitDB( conn );
		
		aircrafitList = model.seek( "" );
		
		for(int i = 0; i < aircrafitList.size();i++)
		{
			cmbAirPlane.addItem( aircrafitList.get(i).getCode() + "|" + aircrafitList.get(i).getName() );
		}
	}

	public void setMode( int newMode )
	{
		mode = newMode;

		if ( mode == 2 )
		{
			setTitle( lang.getString( "Alterar" ) + " - " + getTitle() );
			cmbSituation.setEditable( false );
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
	
	private int getIdx( Aircrafit aircrafit )
	{	
		for ( int i = 0; i < aircrafitList.size(); i++ )
		{
			if( aircrafitList.get( i ).getCode() == aircrafit.getCode())
			{
				return i;
			}
		}
		
		return -1;
	}
	
	private Aircrafit getAircrafit( String item )
	{
		int Code = Integer.parseInt(item.split( "\\|")[0] );
		Aircrafit aircrafit = null;
		
		for ( int i = 0; i < aircrafitList.size(); i++ )
		{
			if( aircrafitList.get( i ).getCode() == Code)
			{
				aircrafit = aircrafitList.get( i );
			}
		}
		
		return aircrafit;
	}

	protected void createLayout()
	{
		JPanel defaultPanel = new JPanel();

		defaultPanel.setLayout( new FlowLayout() );
		defaultPanel.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( defaultPanel );

		setSize( 371, 434 );
		setLocationRelativeTo( null );
	}

	protected void createScreen()
	{
		setTitle( lang.getString( "IVoo.titulo" ) );
	}

	protected void addControls()
	{
		pnlHeaderCode.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlCode.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlCode.add( lblCode );
		pnlCode.add( txtCode );
		pnlCode.setPreferredSize( new Dimension( 150, 60 ) );

		pnlHeaderCode.add( pnlCode );
		pnlHeaderCode.setPreferredSize( new Dimension( 315, 60 ) );

		pnlSourceDestiny.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlSource.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlDestiny.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlSource.add( lblSource );
		pnlSource.add( txtSource );
		pnlSource.setPreferredSize( new Dimension( 150, 60 ) );

		pnlDestiny.add( lblDestiny );
		pnlDestiny.add( txtDestiny );
		pnlDestiny.setPreferredSize( new Dimension( 150, 60 ) );

		pnlSourceDestiny.add( pnlSource );
		pnlSourceDestiny.add( pnlDestiny );
		pnlSourceDestiny.setPreferredSize( new Dimension( 315, 60 ) );

		pnlScaleDateTime.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlScale.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlDateTime.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlScale.add( lblScale );
		pnlScale.add( cmbScale );
		pnlScale.setPreferredSize( new Dimension( 150, 60 ) );

		pnlDateTime.add( lblDateTime );
		pnlDateTime.add( txtDateTime );
		pnlDateTime.setPreferredSize( new Dimension( 150, 60 ) );

		pnlScaleDateTime.add( pnlScale );
		pnlScaleDateTime.add( pnlDateTime );
		pnlScaleDateTime.setPreferredSize( new Dimension( 315, 60 ) );

		pnlAirPlaneSituation.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlAirPlane.setLayout( new FlowLayout( FlowLayout.LEADING ) );
		pnlSituation.setLayout( new FlowLayout( FlowLayout.LEADING ) );

		pnlAirPlane.add( lblAirPlane );
		pnlAirPlane.add( cmbAirPlane );
		pnlAirPlane.setPreferredSize( new Dimension( 150, 60 ) );

		pnlSituation.add( lblSituation );
		pnlSituation.add( cmbSituation );
		pnlSituation.setPreferredSize( new Dimension( 150, 60 ) );

		pnlAirPlaneSituation.add( pnlAirPlane );
		pnlAirPlaneSituation.add( pnlSituation );
		pnlAirPlaneSituation.setPreferredSize( new Dimension( 315, 60 ) );

		pnlValueContainer.setLayout( new FlowLayout( FlowLayout.LEFT) );
		pnlValue.setLayout( new FlowLayout( FlowLayout.LEFT ));
		
		pnlValue.add( lblValue );
		pnlValue.add( txtValue );
		
		pnlValueContainer.add( pnlValue  );
		
		pnlValue.setPreferredSize( new Dimension( 150, 60 ) );
		pnlValueContainer.setPreferredSize( new Dimension( 315, 60 ) );
		
		pnlFooter.setLayout( new FlowLayout( FlowLayout.TRAILING ) );
		pnlSave.add( btnSave );
		pnlSave.setPreferredSize( new Dimension( 100, 50 ) );
		pnlExit.add( btnExit );
		pnlExit.setPreferredSize( new Dimension( 100, 50 ) );

		pnlFooter.add( pnlSave );
		pnlFooter.add( pnlExit );
		pnlFooter.setPreferredSize( new Dimension( 315, 60 ) );

		add( pnlHeaderCode );
		add( pnlSourceDestiny );
		add( pnlScaleDateTime );
		add( pnlAirPlaneSituation );
		add( pnlValueContainer );
		add( pnlFooter );
	}

	protected void createControls()
	{
		try
		{
			pnlHeaderCode = new JPanel();
			pnlCode = new JPanel();
			lblCode = new JLabel( lang.getString( "IVoo.lblCodigo" ) );
			txtCode = new JTextField();
			txtCode.setPreferredSize( new Dimension( 110, 25 ) );
			txtCode.setEditable( false );
			
			pnlSourceDestiny = new JPanel();
			pnlSource = new JPanel();
			lblSource = new JLabel( lang.getString( "IVoo.lblOrigem" ) );
			txtSource = new JTextField();
			txtSource.setPreferredSize( new Dimension( 110, 25 ) );
	
			pnlDestiny = new JPanel();
			lblDestiny = new JLabel( lang.getString( "IVoo.lblDestino" ) );
			txtDestiny = new JTextField();
			txtDestiny.setPreferredSize( new Dimension( 110, 25 ) );
	
			pnlScaleDateTime = new JPanel();
			pnlScale = new JPanel();
			lblScale = new JLabel( lang.getString( "IVoo.lblEscalas" ) );
			cmbScale = new JComboBox( qtdeEscalas );
			cmbScale.setPreferredSize( new Dimension( 110, 25 ) );
	
			pnlDateTime = new JPanel();
			lblDateTime = new JLabel( lang.getString( "IVoo.lblDataHora" ) );
			
			MaskFormatter msk = new MaskFormatter( "##/##/#### ##:##" ) ;
			
			txtDateTime = new JFormattedTextField( msk );
			txtDateTime.setPreferredSize( new Dimension( 110, 25 ) );
			msk.setValidCharacters( "0123456790" );
			
			
			pnlAirPlaneSituation = new JPanel();
			pnlAirPlane = new JPanel();
			lblAirPlane = new JLabel( lang.getString( "IVoo.aeronave" ) );
			cmbAirPlane = new JComboBox(  );
			cmbAirPlane.setPreferredSize( new Dimension( 110, 25 ) );
	
			pnlSituation = new JPanel();
			lblSituation = new JLabel( lang.getString( "IVoo.situacao" ) );
			cmbSituation = new JComboBox( statusVoo );
			cmbSituation.setPreferredSize( new Dimension( 110, 25 ) );
	
			pnlValueContainer = new JPanel();
			pnlValue = new JPanel();
			lblValue = new JLabel( lang.getString( "IVoo.valor" ));
			lblValue.setPreferredSize( new Dimension( 110,15) );
			txtValue = new JTextField();
			txtValue.setPreferredSize( new Dimension( 110, 25 ) );
			
			pnlFooter = new JPanel();
			pnlSave = new JPanel();
			btnSave = new JButton( lang.getString( "btnSalvar" ) );
	
			pnlExit = new JPanel();
			btnExit = new JButton( lang.getString( "btnSair" ) );
		}
		catch( Exception ex )
		{
		
		}
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
			if( Validate() )
			{
				Save();
			}
		}
		else if ( e.getSource() == btnExit )
		{
			Exit();
		}
	}

	public Boolean Validate()
	{
		Boolean Retorno = false;
		
		if( GeralFunctions.IsEmpty( txtSource.getText() ) )
		{
			JOptionPane.showMessageDialog( null, lang.getString("IVoo.mensagem.origem.obrigatorio") );
		}
		else if( GeralFunctions.IsEmpty( txtDestiny.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("IVoo.mensagem.destino.obrigatorio") );
		}
		else if( !GeralFunctions.IsDate( txtDateTime.getText(), "dd/MM/yyyy HH:mm"))
		{
			JOptionPane.showMessageDialog( null, lang.getString("IVoo.mensagem.data.invalida"));
		}
		else if( !GeralFunctions.IsDouble( txtValue.getText() ))
		{
			JOptionPane.showMessageDialog( null, lang.getString("IVoo.mensagem.valor"));
		}
		else
		{
			Retorno = true;
		}
		return Retorno;
	}
	
	public void Save()
	{
		FlyDB flyDB = new FlyDB( conn );

		switch ( mode )
		{
			case 2:
				flyDB.update( getFly() );
				JOptionPane.showMessageDialog( this,
						lang.getString( "mensagem.update" ) );
			break;

			case 3:
				flyDB.delete( getFly() );
				JOptionPane.showMessageDialog( this,
						lang.getString( "mensagem.delete" ) );
			break;

			default:
				flyDB.insert( getFly() );
				JOptionPane.showMessageDialog( this,
						lang.getString( "mensagem.insert" ) );
			break;

		}

		dispose();
	}

	public Fly getFly()
	{
		Fly fly = new Fly();

		if ( !txtCode.getText().equals( "" ) )
		{
			fly.setCode( Integer.parseInt( txtCode.getText() ) );
		}

		fly.setAircrafit( getAircrafit( cmbAirPlane.getSelectedItem().toString() ) );
		fly.setQtdeScales( Integer.parseInt( cmbScale.getSelectedItem().toString()) );
		fly.setSituation( "" + cmbSituation.getSelectedIndex() );
		fly.setDateTime(txtDateTime.getText() );
		fly.setDestiny( txtDestiny.getText() );
		fly.setSource( txtSource.getText() );
		fly.setValue( Double.parseDouble( txtValue.getText() ) );
		
		return fly;
	}

	public void setFly( Fly fly )
	{
		txtCode.setText( "" + fly.getCode() );
		cmbAirPlane.setSelectedIndex( getIdx ( fly.getAircrafit() ) );
		cmbScale.setSelectedIndex( fly.getQtdeScales() );
		cmbSituation.setSelectedIndex( Integer.parseInt(  fly.getSituation() ) );
		txtSource.setText( fly.getSource());
		txtDestiny.setText( fly.getDestiny() );
		txtValue.setText( "" + fly.getValue() );
		
		if( fly.getDateTime() != null && !GeralFunctions.IsEmpty( fly.getDateTime().toString() ))
		{
			txtDateTime.setText( fly.getDateTime().toString() );
		}
	}

	public void Exit()
	{
		dispose();
	}

}