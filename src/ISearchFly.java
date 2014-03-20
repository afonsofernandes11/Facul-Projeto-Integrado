import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.EmptyBorder;

public class ISearchFly extends JDialog implements ActionListener
{
   private ResourceBundle lang;

   private JPanel headerPane;
   private JPanel filterPane;
   private JLabel lblTypeFilter;
   private JLabel lblFilter;
   private JLabel valueFilterPane;
   private JComboBox cmgColumnsFilter;
   private JTextField txtFilter;

   private FlyDB fly;
   
   private JTable tableData;		

   private JPanel footerPane;
   private JButton btnInsert;
   private JButton btnUpdate;
   private JButton btnDelete;
   private JButton btnBuy;
   
   private Connection conn;

   private String[] columns;         

   private FlyTableModel dataTable;
   private Login login;

   public ISearchFly( JFrame Main, ResourceBundle language, Connection conn, Login login )
   {
      super( Main, true );
   
      lang = language;
      this.conn = conn;
      this.login = login;
      
      fly = new FlyDB( conn );
      
      initialize();
   }

   private void setAdminPermissions()
   {
	   if( !login.IsAdministrator())
	   {
		   btnInsert.setVisible( false );
		   btnUpdate.setVisible( false );
		   btnDelete.setVisible( false );
	   }
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
      setAdminPermissions();
   }

   private void createLayout()
   {
      getContentPane().setLayout( new BorderLayout ( 10, 10 ) );
      setLocationRelativeTo( null );       
   }

   private void createScreen()
   {
      setTitle( lang.getString( "IConsultaVoo.titulo" ) );
      setSize( 800, 600 );
      setLocationRelativeTo( null );  
   }

   private void createColunms()
   {
      columns = new String[4];
      columns[0] = lang.getString( "IConsultaVoo.coluna.codigo" );
      columns[1] = lang.getString( "IConsultaVoo.coluna.status" );
      columns[2] = lang.getString( "IConsultaVoo.coluna.origem" );
      columns[3] = lang.getString( "IConsultaVoo.coluna.destino" );
   }

   private void createData()
   {
      dataTable = new FlyTableModel( columns, lang );
      refresh();
   }

   private void refresh()
   {
	   refresh("");
   }
   
	private void refresh( String Filter )
	{
		dataTable.dados = fly.seek( Filter );
		dataTable.fireTableDataChanged();
	}

   private void addControls()
   {
      headerPane.setLayout( new FlowLayout( FlowLayout.LEADING ) );
      filterPane.setLayout( new FlowLayout( FlowLayout.LEADING ) );
      valueFilterPane.setLayout( new FlowLayout( FlowLayout.LEADING ) );
   
      headerPane.add( filterPane );
      headerPane.add( valueFilterPane );
      filterPane.add( lblTypeFilter );
      filterPane.add( cmgColumnsFilter );
   
      valueFilterPane.add( lblFilter );     
      valueFilterPane.add( txtFilter );     
   
      cmgColumnsFilter.setPreferredSize( new Dimension( 100, 25 ) );
      filterPane.setPreferredSize( new Dimension( 200, 60 ) );
      filterPane.setBorder( new EmptyBorder( 0, 30, 0, 10 ) );
      txtFilter.setPreferredSize( new Dimension( 180,25) );
      valueFilterPane.setPreferredSize( new Dimension( 200, 60 ) );
   
      footerPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
      footerPane.add( btnInsert );
      footerPane.add( btnUpdate );
      footerPane.add( btnDelete );
      footerPane.add( btnBuy );
   
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
      btnBuy.addActionListener( this );
      
      txtFilter.addKeyListener( new KeyDownChanged() );
   }

   public void actionPerformed( ActionEvent e )
   {
      if( e.getSource() == btnInsert )
      { 
         onClickInsert();
      }
      else if( e.getSource() == btnUpdate )
      {
         onClickUpdate();
      }
      else if( e.getSource() == btnDelete )
      {
         onClickDelete();
      }
      else if( e.getSource() == btnBuy )
      {
         onClickBuy();
      }
   }

   private void onClickInsert()
   {
      IFly frm = new IFly( this, lang, conn );
      frm.setMode( 1 );
      frm.show();
      
      refresh();
   }

   private void onClickUpdate()
   {
      IFly frm = new IFly( this, lang, conn );
      frm.setMode( 2 );
      frm.setFly( dataTable.dados.get( tableData.getSelectedRow() ) );
      frm.show();   
      
      refresh();
   }

   private void onClickDelete()
   {
      IFly frm = new IFly( this, lang, conn );
      frm.setMode( 3 );
      frm.setFly( dataTable.dados.get( tableData.getSelectedRow() ) );
      frm.show();   
      
      refresh();
   }

   private void onClickBuy()
   {
      IBuyPassage frm = new IBuyPassage( this, lang, conn );
      frm.show();
      
      refresh();
   }

   private void createControls()
   {
      headerPane = new JPanel();
      filterPane = new JPanel();
      lblTypeFilter = new JLabel( lang.getString( "IConsultaVoo.lblTipoFiltro" ) );
      cmgColumnsFilter = new JComboBox( columns );
      valueFilterPane = new JLabel();
      lblFilter = new JLabel( lang.getString( "IConsultaVoo.lblFiltro" ) );
      txtFilter = new JTextField( );
   
      tableData = new JTable( dataTable ); 
   
      footerPane = new JPanel();
      btnInsert = new JButton ( lang.getString( "IConsultaVoo.btnInserir") );
      btnUpdate = new JButton ( lang.getString( "IConsultaVoo.btnAlterar") );      
      btnDelete = new JButton ( lang.getString( "IConsultaVoo.btnExcluir") );      
      btnBuy = new JButton ( lang.getString( "IConsultaVoo.btnComprar") );      
   
   }
   
   class KeyDownChanged implements KeyListener{
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
	    		  if( cmgColumnsFilter.getSelectedIndex() == 0 )
	    		  {
	    			  if( GeralFunctions.IsInteger( txtFilter.getText() ) )
	    			  {
	    				  search = field + " = " + txtFilter.getText();
	    				  refresh( search );
	    			  }
	    			  else
	    			  {
	    				  JOptionPane.showMessageDialog( null, lang.getString( "IConsultaVoo.mensagem.codigoNumero" ) );
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
	    			return "idVoo";
	    		
	    		case 1:
	    			return "situacao";
	    			
	    		case 2:
	    			return "origem";
	    			
	    		case 3:
	    			return "destino";
	    			
    			default:
    				return "";
	    	}
	    }
	    
		@Override
		public void keyReleased( KeyEvent e )
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped( KeyEvent e )
		{
			// TODO Auto-generated method stub
			
		}       
	}
}
