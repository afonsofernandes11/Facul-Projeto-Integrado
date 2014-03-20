import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDB
{
	private Connection conn;

	public TicketDB( Connection conn )
	{
		this.conn = conn;
	}
	
	private void insertBuy( TicketBuy ticket )
	{
		PreparedStatement stm = null;
		String Sql;

		Sql = " INSERT INTO passagemcompra";
		Sql += "     ( idfkpassagem,percentualembarque )";
		Sql += " VALUES (?,?);";
		
		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, ticket.getCode() );
			stm.setDouble( 2, ticket.getPercent() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void transfer( Ticket ticket)
	{
		String Sql;
		
		Sql = " UPDATE passagem"; 
		Sql += " SET idVoo = ?"; 
		Sql += " WHERE idPassagem = ?;"; 
		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, ticket.getFly().getCode() );
			stm.setInt( 2, ticket.getCode() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void cancel( Ticket ticket )
	{
		String Sql;
		
		Sql = " UPDATE passagem"; 
		Sql += " SET status = 'C'"; 
		Sql += " WHERE idPassagem = ?;"; 
		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setInt( 1, ticket.getCode() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public int insert( TicketBuy ticket )
	{
		String Sql;

		Sql = " INSERT INTO `passagem`";
		Sql += "             (";
		Sql += "              `valor`,";
		Sql += "              `idPassagemMain`,";
		Sql += "              `tipo`,";
		Sql += "              `status`,";
		Sql += "              `idvoo`)";
		Sql += " VALUES      (?,";
		Sql += "              ?,";
		Sql += "              ?,";
		Sql += "              'P',";
		Sql += "              ?);";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setDouble( 1, ticket.getValue() );
			stm.setInt( 2, ticket.getIdMain() );
			stm.setString( 3, ticket.getType() );
			stm.setInt( 4, ticket.getFly().getCode() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		ticket.setCode( getNewId() );
		
		if( ticket.getIdMain() == -1 )
		{
			setIdMain( ticket );
		}
		
		insertBuy( ticket );
		insertPassage( ticket );
		
		return ticket.getCode();
	}

	private void insertPassage( TicketBuy ticket )
	{
		PassageDB model = new PassageDB( conn );
		
		model.insert( ticket.getPassage(), ticket.getCode() );
	}

	public void setIdMain( TicketBuy ticket )
	{
		
		String Sql;

		Sql = " UPDATE `passagem` SET ";
		Sql += "       idPassagemMain = ? ";
		Sql += " WHERE idPassagem = ?";

		PreparedStatement stm = null;

		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setDouble( 1, ticket.getCode() );
			stm.setInt( 2, ticket.getCode() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private int getNewId()
	{
		String Sql;
		ResultSet rs = null;
		PreparedStatement stm = null;
		int id = -1;
		
		Sql = "SELECT MAX(idPassagem) FROM passagem";
		
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

	public void checkIn( Ticket ticket, int place )
	{
		String Sql;
		PreparedStatement stm = null;
		
		Sql = "UPDATE passagem SET dataCheckIn = current_date, status='F', ";
		Sql += " lugar = ? ";
		Sql += " WHERE idPassagem = ?";
		
		try
		{
			stm = conn.prepareStatement( Sql );
			
			stm.setInt( 1, place );
			stm.setInt( 2, ticket.getCode() );
			stm.execute();
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
	
	public List<Ticket> seek( String filter )
	{
		String Sql;
		List<Ticket> list = new ArrayList<Ticket>();
		ResultSet rs = null;
		PreparedStatement stm = null;
		Aircrafit aircrafit;
		Fly fly;
		Ticket ticket;
		Passage passage;

		Sql = "SELECT * FROM passagem";
		Sql += "	INNER JOIN passageiro on passagem_idPassagem = idPassagem";
		Sql += "	INNER JOIN voo on passagem.idVoo = voo.idVoo";
		Sql += "	INNER JOIN aeronave on idAeronave = codigo";
		
		if ( !filter.equals( "" ) )
		{
			Sql += " WHERE " + filter;
		}

		Sql += " ORDER BY idPassagem DESC";
		
		try
		{
			stm = conn.prepareStatement( Sql );
			rs = stm.executeQuery();

			while ( rs.next() )
			{
				aircrafit = new Aircrafit();
				aircrafit.setCode( rs.getInt( "codigo" ) );
				aircrafit.setName( rs.getString( "nome" ) );
				aircrafit.setQtdeAssentos( rs.getInt( "assentos" ) );

				fly = new Fly();
				fly.setCode( rs.getInt( "idVoo" ) );
				fly.setFormatDate( rs.getString( "dataPartida" ) );
				fly.setAircrafit( aircrafit );
				fly.setSource( rs.getString( "origem" ) );
				fly.setDestiny( rs.getString( "destino" ) );
				fly.setSituation( rs.getString( "situacao" ) );
				fly.setQtdeScales( rs.getInt( "escalas" ) );
				fly.setValue( rs.getDouble( "valor" ) );

				passage = new Passage();
				passage.setCode( rs.getInt( "idPassageiro" ) );
				passage.setEmail( rs.getString( "Email" ) );
				passage.setFormTreatment( rs.getInt( "Tratamento" ) );
				passage.setLastName( rs.getString( "SobreNome" ) );
				passage.setProfile( rs.getInt( "Perfil" ) );
				passage.setName( rs.getString( "nome" ));
				
				ticket = new Ticket();
				ticket.setCode( rs.getInt( "idPassagem" ) );
				ticket.setFly( fly );
				ticket.setStatus( rs.getString( "status" ) );
				ticket.setPassage( passage );
				ticket.setType( rs.getString( "tipo" ) );
				ticket.setValue( rs.getDouble( "valor" ) );
				ticket.setPlace( rs.getInt( "lugar" ) );
				list.add( ticket );
			}

		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}

		return list;
	}
}
