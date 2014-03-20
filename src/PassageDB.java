import java.sql.Connection;
import java.sql.PreparedStatement;


public class PassageDB
{
	private Connection conn;
	
	public PassageDB( Connection conn )
	{
		this.conn = conn;
	}
	
	public void insert( Passage pass, int idPassage )
	{
		String Sql;
		
		Sql = " INSERT INTO passageiro"; 
		Sql += " (`Nome`,`Sobrenome`,`Email`,`Telefone`,"; 
		Sql += " `Passagem_idPassagem`,`DataNascimento`,`Perfil`,`Tratamento`)"; 
		Sql += "  VALUES (?,?,?,?,?,?,?,?);"; 
		
		PreparedStatement stm = null;
		
		try
		{
			stm = conn.prepareStatement( Sql );

			stm.setString( 1, pass.getName() );
			stm.setString( 2, pass.getLastName() );
			stm.setString( 3, pass.getEmail() );
			stm.setString( 4, pass.getPhone() );
			stm.setInt( 5, idPassage );
			stm.setDate( 6, (java.sql.Date)pass.getDateBirth() );
			stm.setInt( 7, pass.getProfile() );
			stm.setInt( 8, pass.getFormTreatment() );

			stm.execute();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}


	}
}
