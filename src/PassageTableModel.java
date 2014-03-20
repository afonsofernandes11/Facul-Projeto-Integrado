import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PassageTableModel extends DefaultTableModel{
     
    public List<TicketBuy> dados = new ArrayList<TicketBuy>();
    private String[] colunas;
	DecimalFormat mask; 
	
    public PassageTableModel( String[] coluns, ResourceBundle lang )
    {
        dados = new ArrayList<TicketBuy>();
        mask = new DecimalFormat(lang.getString("mask.dinheiro"));
        
        colunas = coluns;
        createColumns();
    }
     
    private void createColumns()
    {
        for( int i = 0; i < colunas.length; i++ )
        {
            addColumn( colunas[i] );
        }
    } 
     
    public void addRow( TicketBuy p )
    {
        this.dados.add(p);
        this.fireTableDataChanged();
    }
 
    public String getColumnName( int num )
    {
        return this.colunas[num];
    }
 
    @Override
    public int getRowCount() 
    {
        if( dados == null )
        {
           return 0;
        }  
        return dados.size();
    }
 
    @Override
    public int getColumnCount() {
        return colunas.length;
    }
 
    @Override
    public Object getValueAt(int linha, int coluna) {
        switch(coluna){
            case 0: return dados.get(linha).getPassage().getName();
            case 1: return mask.format( dados.get(linha).getSimValue() );
        }  
        return null;
    }
}