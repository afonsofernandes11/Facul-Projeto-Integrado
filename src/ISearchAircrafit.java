import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class ISearchAircrafit extends JDialog implements ActionListener
{
	private ResourceBundle lang;

	private JPanel headerPane;
	private JPanel filterPane;
	private JLabel lblTypeFilter;
	private JLabel lblFilter;
	private JLabel valueFilterPane;
	private JComboBox cmgColumnsFilter;
	private JTextField txtFilter;

	private JTable tableData;

	private JPanel footerPane;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;

	private String[] columns;

	public AircrafitTableModel dataTable;
	public Connection conn;
	private AircrafitDB aircrafit;
	
	public ISearchAircrafit( JFrame Main, ResourceBundle language,	Connection conn )
	{
		super( Main, true );

		this.conn = conn;
		lang = language;
		aircrafit = new AircrafitDB( conn );
		
		initialize();
	}

	private void initialize()
	{
		createScreen();
		createLayout();
		createColunms();
		createData();
		createControls();
		addControls();
		createActions();
	}

	private void createData()
	{
		dataTable = new AircrafitTableModel( columns );
		refresh();
	}
	
	private void refresh()
	{
		refresh("");
	}
	
	private void refresh( String filter )
	{
		dataTable.dados = aircrafit.seek( filter );
		dataTable.fireTableDataChanged();
	}

	private void createLayout()
	{
		getContentPane().setLayout( new BorderLayout( 10, 10 ) );
		setLocationRelativeTo( null );
	}

	private void createScreen()
	{
		setTitle( lang.getString( "IConsultaAeronave.titulo" ) );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
	}

	private void createColunms()
	{
		columns = new String[3];
		columns[0] = lang.getString( "IConsultaAeronave.coluna.codigo" );
		columns[1] = lang.getString( "IConsultaAeronave.coluna.nome" );
		columns[2] = lang.getString( "IConsultaAeronave.coluna.qtdeAssentos" );
	}

	private void addControls()
	{
		headerPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		filterPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		valueFilterPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		headerPane.add( filterPane );
		headerPane.add( valueFilterPane );
		filterPane.add( lblTypeFilter );
		filterPane.add( cmgColumnsFilter );

		valueFilterPane.add( lblFilter );
		valueFilterPane.add( txtFilter );

		cmgColumnsFilter.setPreferredSize( new Dimension( 100, 25 ) );
		filterPane.setPreferredSize( new Dimension( 200, 60 ) );
		filterPane.setBorder( new EmptyBorder( 0, 30, 0, 10 ) );
		valueFilterPane.setPreferredSize( new Dimension( 200, 60 ) );
		txtFilter.setPreferredSize( new Dimension( 170,25) );
		txtFilter.addKeyListener( new KeyDownChanged() );
		
		footerPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		footerPane.add( btnInsert );
		footerPane.add( btnUpdate );
		footerPane.add( btnDelete );

		JScrollPane scrollPane = new JScrollPane( tableData );

		add( headerPane, BorderLayout.PAGE_START );
		add( scrollPane, BorderLayout.CENTER );
		add( footerPane, BorderLayout.PAGE_END );
	}

	private void createActions()
	{
		btnInsert.addActionListener( this );
		btnUpdate.addActionListener( this );
		btnDelete.addActionListener( this );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnInsert )
		{
			onClickInsert();
		}
		else if ( e.getSource() == btnUpdate )
		{
			onClickUpdate();
		}
		else if ( e.getSource() == btnDelete )
		{
			onClickDelete();
		}
	}

	private void onClickInsert()
	{
		IAircrafit frm = new IAircrafit( this, lang, conn );
		frm.setMode( 1 );
		frm.show();
		
		refresh();
		tableData.repaint();
	}

	private void onClickUpdate()
	{
		if ( tableData.getSelectedRow() >= 0 )
		{
			IAircrafit frm = new IAircrafit( this, lang, conn );
			frm.setMode( 2 );
			frm.setAircrafitFields( dataTable.dados.get( tableData.getSelectedRow() ) );

			frm.show();
		}
		
		refresh();
	}

	private void onClickDelete()
	{
		if ( tableData.getSelectedRow() >= 0 )
		{
			IAircrafit frm = new IAircrafit( this, lang, conn );
			frm.setMode( 3 );
			frm.setAircrafitFields( dataTable.dados.get( tableData.getSelectedRow() ) );

			frm.show();
		}

		refresh();
	}

	private void createControls()
	{
		headerPane = new JPanel();
		filterPane = new JPanel();
		lblTypeFilter = new JLabel( lang.getString( "IConsultaAeronave.lblTipoFiltro" ) );
		cmgColumnsFilter = new JComboBox( columns );
		valueFilterPane = new JLabel();
		lblFilter = new JLabel( lang.getString( "IConsultaAeronave.lblFiltro" ) );
		txtFilter = new JTextField( );
		tableData = new JTable( dataTable );

		footerPane = new JPanel();

		btnInsert = new JButton(
				lang.getString( "IConsultaAeronave.btnInserir" ) );
		btnUpdate = new JButton(
				lang.getString( "IConsultaAeronave.btnAlterar" ) );
		btnDelete = new JButton(
				lang.getString( "IConsultaAeronave.btnExcluir" ) );
	}

	class KeyDownChanged implements KeyListener
	{
	    @Override
	    public void keyPressed(KeyEvent e)
	    {
	      String field;
	      String search;
	      
	      if( e.getKeyCode() == 10 )
	      {
	    	  field = getField( cmgColumnsFilter.getSelectedIndex());
	    	  
	    	  if( !GeralFunctions.IsEmpty( field ) && !GeralFunctions.IsEmpty( txtFilter.getText() ))
	    	  {
	    		  if( cmgColumnsFilter.getSelectedIndex() == 0 || cmgColumnsFilter.getSelectedIndex() == 2 )
	    		  {
	    			  if( GeralFunctions.IsInteger( txtFilter.getText() ) )
	    			  {
	    				  search = field + " = " + txtFilter.getText();
	    				  refresh( search );
	    			  }
	    			  else
	    			  {
	    				  if( cmgColumnsFilter.getSelectedIndex() == 0 )
	    				  {
	    					  JOptionPane.showMessageDialog( null, lang.getString( "IConsultaAeronave.mensagem.codigoNumero" ) );  
	    				  }
	    				  else
	    				  {
	    					  JOptionPane.showMessageDialog( null, lang.getString( "IConsultaAeronave.mensagem.qtdeNumero" ) );
	    				  }
	    				  
	    				  txtFilter.setFocusable( true );
	    			  } 
	    		  }
	    		  else
	    		  {
	    			  search = field + " LIKE '%" + txtFilter.getText() +"%'";
	    			  refresh( search );
	    		  }
	    	  }
	    	  else
	    	  {
	    		  refresh();
	    	  }
	      }
	    }

	    private String getField( int idx )
	    {
	    	switch( idx )
	    	{
	    		case 0:
	    			return "codigo";
	    		
	    		case 1:
	    			return "nome";
	    			
	    		case 2:
	    			return "assentos";
	    			
    			default:
    				return "";
	    	}
	    }
	    
		@Override
		public void keyReleased( KeyEvent e )
		{
		}

		@Override
		public void keyTyped( KeyEvent e )
		{	
		}       
	}
}
