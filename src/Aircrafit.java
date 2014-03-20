import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Aircrafit
{
	private int code;
	private String name;
	private int qtdeAssentos;

	public Aircrafit( ){
		code = -1;
		name = "";
		qtdeAssentos = -1;
	}

	public Aircrafit( int pCode, String pName, int pQtdeEscalas ){
		setCode( pCode );
		setName( pName );
		setQtdeAssentos( pQtdeEscalas );
	}

	public void setCode( int pCode )
	{
		code = pCode;
	}

	public void setName( String pName )
	{
		name = pName;
	}

	public void setQtdeAssentos( int pQtdeAssentos )
	{
		qtdeAssentos = pQtdeAssentos;
	}

	public int getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public int getQtdeAssentos()
	{
		return qtdeAssentos;
	}
	
}