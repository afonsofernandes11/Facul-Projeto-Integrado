import java.util.Locale;
import java.util.ResourceBundle;

public class Language
{
	public static ResourceBundle get( int language )
	{
		ResourceBundle lang;
		
		switch ( language ) 
		{
			case 0:
				lang = ResourceBundle.getBundle( "idiomas", new Locale( "pt" ,"BR" ) );
			break;
	
			case 1:
				lang = ResourceBundle.getBundle( "idiomas", Locale.US );
			break;
	
			default:
				lang = ResourceBundle.getBundle( "idiomas", new Locale( "es" ,"ES" ) );
			break;
	
		}
		
		return lang;
	}
	
}