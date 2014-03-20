import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AircrafitDB
{
	private Connection conn;
	
	public AircrafitDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public void insert( Aircrafit aircrafit )
	{
		String Sql;

		Sql = "INSERT INTO AERONAVE( NOME, ASSENTOS ) VALUES( ?, ? )";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			
			stm.setString( 1, aircrafit.getName() );
			stm.setInt( 2, aircrafit.getQtdeAssentos() );
			
			
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void update( Aircrafit aircrafit )
	{
		String Sql;

		Sql = "UPDATE AERONAVE SET NOME = ?, ASSENTOS = ? ";
		Sql += " WHERE CODIGO = ?";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setString( 1, aircrafit.getName() );
			stm.setInt( 2, aircrafit.getQtdeAssentos() );
			stm.setInt( 3, aircrafit.getCode() );
	
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void delete( Aircrafit aircrafit )
	{
		String Sql;

		Sql = "DELETE FROM AERONAVE ";
		Sql += " WHERE CODIGO = ?";
		
		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, aircrafit.getCode() );
	
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}
	
	public Aircrafit get( int id )
	{
		List<Aircrafit> listTemp;
		Aircrafit aircrafit = null;
		
		listTemp = seek( "codigo=" + id );
		
		if( listTemp.size() > 0 )
		{
			aircrafit = listTemp.get( 0 );
		}
		
		return aircrafit;
	}

	public List<Aircrafit> seek( String Filter )
	{
		List<Aircrafit> list = new ArrayList<Aircrafit>();
		Aircrafit single;
		String Sql;
		ResultSet rs = null;
		PreparedStatement stm = null;

		Sql = "SELECT * FROM AERONAVE";

		if ( !Filter.equals( "" ) )
		{
			Sql += " WHERE " + Filter;
		}
		
		try
		{
			stm = conn.prepareStatement( Sql );
			rs = stm.executeQuery();
			
			while( rs.next() )
			{
				single = new Aircrafit( );
				single.setCode( rs.getInt( "codigo" ) );
				single.setName( rs.getString( "nome" ) );
				single.setQtdeAssentos( rs.getInt( "assentos" ) );
				
				list.add( single );
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return list;
	} 
}
