import java.io.*;
import java.lang.SecurityException;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AcessoTXT
{
	public Scanner openFile( String path )
	{
		File output;
		Scanner file = null;
		
		try
		{
			output = new File( path );
			file = new Scanner( output );
		}
		catch ( SecurityException securityException )
		{
			System.err.println( "Não tem permissão para acessar o arquivo" );
			System.exit( 1 );
		}
		catch ( FileNotFoundException filesNotFoundException )
		{
			System.err.println( "Arquivo '" + path + "' não existe" );
			System.exit( 1 );
		}

		return file;
	}
}