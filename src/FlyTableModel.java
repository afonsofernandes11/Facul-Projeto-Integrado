import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlyTableModel extends DefaultTableModel{
     
    public List<Fly> dados = new ArrayList<Fly>();
    private String[] colunas;
    private ResourceBundle lang; 
    
    public FlyTableModel( String[] coluns, ResourceBundle rs )
    {
        colunas = coluns;
        lang = rs;
        createColumns();
    }
     
    private void createColumns()
    {
        for( int i = 0; i < colunas.length; i++ )
        {
            addColumn( colunas[i] );
        }
    } 
     
    public void addRow( Fly p )
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
            case 1: return dados.get(linha).getDescSituation( lang );
            case 2: return dados.get(linha).getSource();
            case 3: return dados.get(linha).getDestiny();
        }  
        return null;
    }
}