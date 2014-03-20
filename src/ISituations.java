import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.border.*;

import java.sql.Connection;
import java.util.ResourceBundle;

public class ISituations extends JDialog
{
   private JTable tableData;
   private FlySituationTableModel dataSource;
   private ResourceBundle lang;
   private String columns[];
   private Connection conn;
   
   public ISituations( JFrame main, ResourceBundle bn, Connection conn )
   {
      super( main, true );
      
      lang = bn;
      
      this.conn = conn;
      initialize();
   } 
   
   private void initialize()
   {
      getContentPane().setLayout( new GridLayout( 1, 1 ) );

      setTitle( lang.getString( "ISituacoes.titulo" ) );

      setSize( 800, 600 );
      
      createColumns();
      createControls();
      
      refresh();
   }
   
   private void createColumns()
   {
      columns = new String[4];
      columns[0] = lang.getString( "ISituacoes.status" );
      columns[1] = lang.getString( "ISituacoes.horario" );
      columns[2] = lang.getString( "ISituacoes.origem" ); 
      columns[3] = lang.getString( "ISituacoes.destino" );
   }
   
	private void refresh()
	{
		FlyDB fly = new FlyDB( conn );
		dataSource.dados = fly.seek( "" );
		dataSource.fireTableDataChanged();
	}

   
   private void createControls()
   {
      dataSource = new FlySituationTableModel ( columns, lang );
      tableData = new JTable( dataSource );
      
      JScrollPane scroll = new JScrollPane( tableData );
      add( scroll );     
   }
}