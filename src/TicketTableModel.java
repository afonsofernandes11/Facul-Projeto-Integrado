import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TicketTableModel extends DefaultTableModel{
     
    public List<Ticket> dados = new ArrayList<Ticket>();
    private String[] colunas;
     
    public TicketTableModel( String[] coluns )
    {
        dados = new ArrayList<Ticket>();

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
     
    public void addRow( Ticket p )
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
            case 0: return dados.get(linha).getCode();
            case 1: return dados.get(linha).getStatus();
            case 2: return dados.get(linha).getPassage().getName();
            case 3: return dados.get(linha).getFly().getSource();
            case 4: return dados.get(linha).getFly().getDestiny(); 
            case 5: return dados.get(linha).getValue();
            case 6: return dados.get(linha).getFly().getDateTime();
        }  
        return null;
    }
}