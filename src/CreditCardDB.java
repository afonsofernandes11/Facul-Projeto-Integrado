import java.sql.Connection;
import java.sql.PreparedStatement;


public class CreditCardDB
{
	private Connection conn;
	
	public CreditCardDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public void insert( CreditCard card, int idPayment )
	{
		String Sql;

		Sql = " INSERT INTO `cartaodecredito` (`Operadora`,`DataValidate`,`Codigo`,`numero`, idPagamento )"; 
		Sql += " VALUES (?,?,?,?,?);"; 

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setString( 1, card.getOperadora() );
			stm.setDate( 2, (java.sql.Date)card.getValidate() );
			stm.setString( 3, card.getCode() );
			stm.setString( 4, card.getNumber() );
			stm.setInt( 5, idPayment );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		
	}
}
