import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AcessoDB
{

	static
	{
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
		}
		catch ( Exception e )
		{
			System.out.println( e.getMessage() );
			e.printStackTrace();
		}
	}

	public static Connection obtemConexao() throws SQLException
	{
		return DriverManager.getConnection( "jdbc:mysql://localhost/sistema?user=root&password=1234" );
	}
}