import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class PaymentDB
{
	private Connection conn;
	
	public PaymentDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public int insert( Payment pay )
	{
		String Sql;

		Sql = " INSERT INTO `pagamento`"; 
		Sql += " (`idPassagem`,`Tipo`,`Valor`) ";
		Sql += "  VALUES(?,?,? )";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, pay.getTicket().getCode() );
			stm.setInt( 2, pay.getType() );
			stm.setDouble( 3, pay.getTicket().getValue() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		pay.setIdPayment( getNewId() );
		
		insertPaymentMethod( pay );
		
		return pay.getIdPayment();
	}
	
	
	private void insertPaymentMethod( Payment pay )
	{
		if( pay.getCheck() != null )
		{
			CheckDB model = new CheckDB( conn );
			model.insert( pay.getCheck(), pay.getIdPayment() );
		}
		else
		{
			CreditCardDB model = new CreditCardDB( conn );
			model.insert( pay.getCreditCard(), pay.getIdPayment() );
		}
	}

	private int getNewId()
	{
		String Sql;
		ResultSet rs = null;
		PreparedStatement stm = null;
		int id = -1;
		
		Sql = "SELECT MAX(idPagamento) FROM pagamento";
		
		try
		{
			stm = conn.prepareStatement( Sql );
			rs = stm.executeQuery();

			while ( rs.next() )
			{
				id = rs.getInt( 1 );
			}
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return id;
	}
}
