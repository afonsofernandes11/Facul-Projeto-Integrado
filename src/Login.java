import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Login
{
	private String login;
	private String password;
	private Boolean adminstrator;
	
	private ArrayList<Login> users;
	private Connection conn;
	
	public Login( Connection conn){
		login = "";
		password = "";
		this.conn = conn;
	}

	public boolean IsAdministrator()
	{
		return adminstrator;
	}
	
	public void setLogin( String login )
	{
		this.login = login;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPassword()
	{
		return password;
	}

	public Login( String pLogin, String ppassword, Connection conn )
	{
		login = pLogin;
		password = ppassword;
		this.conn = conn;

		loadUsers();
	}

	private void loadUsers()
	{
		Scanner loginTxt;
		AcessoTXT fileText;
		Login aux;
		String[] FileLines;
		
		fileText = new AcessoTXT();
		users = new ArrayList<Login>();

		loginTxt = fileText.openFile( "login.txt" );
		FileLines = getDecriptLogin( loginTxt ).split( "\n" );
		
		for ( int i = 0; i < FileLines.length; i+=2 )
		{
			aux = new Login( conn );

			aux.setLogin( FileLines[i] );
			aux.setPassword( FileLines[i +1] );

			users.add( aux );
		}

		loginTxt.close();

	}

	public String getDecriptLogin( Scanner loginTxt )
	{
		String Final = "";
		Crypto crip;
		
		
		try
		{	
			Final = "";
			crip = new Crypto();
			
			while ( loginTxt.hasNext() )
			{
				Final += loginTxt.nextLine()+ "\n";
			}
			
			Final = Final.replace( "\n", "" );

			Final = crip.hexStringToString( Final );
			
			crip.geraDecifra( Final.getBytes( "ISO-8859-1" ), new File( "key.cripto" ) );

			Final = new String( crip.getTextoDecifrado(), "ISO-8859-1" );

		
		}
		catch ( Exception ex )
		{
			System.out.println( "erro" + ex.getMessage() );
		}
		
		return Final;
	}
	
	public Boolean validateLogin( String pLogin, String ppassword )
	{
		int low = 0;
		int high = users.size() - 1;
		int mid;
		Boolean validate = false;

		while ( low <= high )
		{
			mid = ( low + high ) / 2;

			if ( users.get( mid ).getLogin().compareTo( pLogin ) < 0 )
			{
				low = mid + 1;
			}
			else if ( users.get( mid ).getLogin().compareTo( pLogin ) > 0 )
			{
				high = mid - 1;
			}
			else
			{
				if ( users.get( mid ).getLogin().compareTo( pLogin ) == 0 )
				{
					if ( users.get( mid ).getPassword().equals( ppassword ) )
					{
						validate = true;
						load();
					}
				}

				low = high + 1;
			}
		}

		return validate;
	}
	
	private void load()
	{		
		String Sql;
		ResultSet rs = null;
		PreparedStatement stm = null;

		Sql = "SELECT * FROM FUNCIONARIO";
		Sql += " WHERE login = ? AND senha = ?";
		
		try
		{
			stm = conn.prepareStatement( Sql );
			stm.setString( 1, getLogin() );
			stm.setString( 2, getPassword() );
			
			rs = stm.executeQuery();
			
			while( rs.next() )
			{
				if( rs.getInt( "tipo" ) ==  1 )
				{
					adminstrator = true;
				}
				else 
				{
					adminstrator = false;
				}
			}
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

	}

	public Boolean validateLogin()
	{
		return validateLogin( login, password );
	}
}