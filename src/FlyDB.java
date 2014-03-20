import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class FlyDB
{
	private Connection conn;
	
	public FlyDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public void insert( Fly fly )
	{
		String Sql;

		Sql = "INSERT INTO VOO( ESCALAS, DATAPARTIDA, SITUACAO, IDAERONAVE, ORIGEM, DESTINO, VALOR, ASSENTOSDISPONIVEIS ) ";
		Sql += " VALUES( ?, ?, ?, ?, ?, ?, ?, ? )";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, fly.getQtdeScales() );
			stm.setString( 2, fly.getFormatDate() );
			stm.setString( 3, fly.getSituation() );
			stm.setInt( 4, fly.getAircrafit().getCode() );
			stm.setString( 5, fly.getSource() );
			stm.setString( 6, fly.getDestiny());
			stm.setDouble( 7, fly.getValue());
			stm.setInt( 8, fly.getAircrafit().getQtdeAssentos() );
			
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void update( Fly fly )
	{
		String Sql;

		Sql = " UPDATE voo SET ";
		Sql += " 	Escalas = ?, ";
		Sql += " 	dataPartida = ?, ";
		Sql += " 	situacao = ?, ";
		Sql += " 	idAeronave = ?,";
		Sql += " 	Origem = ?,";
		Sql += " 	Destino = ?,";
		Sql += "	Valor = ?";
		Sql += " 	WHERE idVoo = ?;";
		
		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, fly.getQtdeScales() );
			stm.setString( 2, fly.getFormatDate() );
			stm.setString( 3, fly.getSituation() );
			stm.setInt( 4, fly.getAircrafit().getCode() );
			stm.setString( 5, fly.getSource() );
			stm.setString( 6, fly.getDestiny() );
			stm.setDouble( 7, fly.getValue() );
			stm.setInt( 8, fly.getCode() );
	
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void delete( Fly fly )
	{
		String Sql;

		Sql = "DELETE FROM VOO ";
		Sql += " WHERE idVoo = ?";
		
		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, fly.getCode() );
	
			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
	}

	public List<Fly> seek( String Filter )
	{
		List<Fly> list = new ArrayList<Fly>();
		Aircrafit single;
		Fly fly;
		String Sql;
		ResultSet rs = null;
		PreparedStatement stm = null;

		Sql = "SELECT * FROM VOO";
		Sql += " INNER JOIN AERONAVE ";
		Sql += "	on codigo = idAeronave";

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
				
				fly = new Fly();
				fly.setCode( rs.getInt( "idVoo" ) );
				fly.setFormatDate( rs.getString( "dataPartida" ) );
				fly.setAircrafit( single );
				fly.setSource( rs.getString( "origem" ) );
				fly.setDestiny( rs.getString( "destino" ) );
				fly.setSituation( rs.getString( "situacao" ) );
				fly.setQtdeScales( rs.getInt( "escalas" ) );
				fly.setValue( rs.getDouble( "valor" ) );
				
				list.add( fly );
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		return list;
	} 
}
