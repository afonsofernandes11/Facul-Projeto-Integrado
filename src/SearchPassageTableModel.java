import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class SearchPassageTableModel extends DefaultTableModel{
     
    private List<SearchPassage> dados = new ArrayList<SearchPassage>();
    private String[] colunas;
     
    public SearchPassageTableModel( String[] coluns )
    {
        dados = new ArrayList<SearchPassage>();

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
     
    public void addRow( SearchPassage p )
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
            case 1: return dados.get(linha).getName();
            case 2: return dados.get(linha).getDestiny();                       
        }  
        return null;
    }
}