import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeralFunctions
{
	public static Boolean IsDate( String date, String mask )
	{
		SimpleDateFormat format = new SimpleDateFormat( mask );
		Date data;

		try
		{
			data = format.parse( date );
			return true;
		}
		catch ( Exception ex )
		{
			data = null;
			return false;
		}
	}

	public static Date now()
	{
		return new Date();
	}
	

	public static Date formataData( String data, String format ) 
	{
		if ( data == null || data.equals( "" ) )
			return null;

		Date date = null;
		
		try
		{
			DateFormat formatter = new SimpleDateFormat( format );
			date = (java.util.Date) formatter.parse( data );
		}
		catch ( Exception e )
		{
			
		}
		return date;
	}

	public static Boolean IsInteger( String value )
	{
		try
		{
			int valueTemp = Integer.parseInt( value );

			return true;
		}
		catch ( Exception ex )
		{
			return false;
		}
	}

	public static Boolean IsDouble( String value )
	{
		try
		{
			Double valueTemp = Double.parseDouble( value );

			return true;
		}
		catch ( Exception ex )
		{
			return false;
		}
	}

	public static Boolean IsEmpty( String text )
	{
		return text.equals( "" );
	}
}
