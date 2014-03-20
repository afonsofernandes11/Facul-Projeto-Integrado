import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class AircrafitTableModel extends DefaultTableModel
{

	public List<Aircrafit> dados = new ArrayList<Aircrafit>();
	private String[] colunas;

	public AircrafitTableModel( String[] coluns ){
		dados = new ArrayList<Aircrafit>();

		colunas = coluns;
		createColumns();
	}

	private void createColumns()
	{
		for ( int i = 0; i < colunas.length; i++ )
		{
			addColumn( colunas[i] );
		}
	}

	public void addRow( Aircrafit p )
	{
		this.dados.add( p );
		this.fireTableDataChanged();
	}

	public String getColumnName( int num )
	{
		return this.colunas[num];
	}

	@Override
	public int getRowCount()
	{
		if ( dados == null )
		{
			return 0;
		}
		return dados.size();
	}

	@Override
	public int getColumnCount()
	{
		return colunas.length;
	}

	@Override
	public Object getValueAt( int linha, int coluna )
	{
		if ( linha >= 0 && linha < dados.size() )
		{
			switch ( coluna )
			{
				case 0:
					return dados.get( linha ).getCode();
				case 1:
					return dados.get( linha ).getName();
				case 2:
					return dados.get( linha ).getQtdeAssentos();
			}
		}
		return null;
	}
}