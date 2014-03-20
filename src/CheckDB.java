import java.sql.Connection;
import java.sql.PreparedStatement;


public class CheckDB
{
	private Connection conn;
	
	public CheckDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public void insert( Check check, int idPayment )
	{
		String Sql;

		Sql = "INSERT INTO cheque(idPagamento,nome, cpf, agencia, conta ) ";
		Sql += " VALUES ( ?, ?, ?, ?, ?) ";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, idPayment );
			stm.setString( 2, check.getName() );
			stm.setString( 3, check.getCpf() );
			stm.setString( 4, check.getAgency() );
			stm.setString( 5, check.getAccount());
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
}
