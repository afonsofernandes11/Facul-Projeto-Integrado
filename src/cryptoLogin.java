import java.io.*;
import java.lang.SecurityException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class cryptoLogin
{
	public static String toHex( byte[] bi) {
		try
		{
	    return String.format("%040x", new BigInteger( 1, bi ) );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
			return "";
		}
	}
	
	public static void main( String[] args )
	{
		Scanner loginTxt;
		String str = "";
		String Final;
		Crypto crip = new Crypto();

		loginTxt = openFile( "loginT.txt" );

		try
		{
			crip.geraChave( new File( "key.cripto" ) );
			Final = "";

			while ( loginTxt.hasNext() )
			{
				Final += loginTxt.next() + "\n";
			}

			System.out.println( Final );

			crip.geraCifra( Final.getBytes( "ISO-8859-1" ), new File( "key.cripto" ) );

			System.out.println( toHex( crip.getTextoCifrado() ) );

			Formatter format = new Formatter( new File( "login.txt" ) );

			format.format( "%s", toHex( crip.getTextoCifrado() )  );

			format.close();

			
			System.out.println( toHex( crip.getTextoCifrado() ) );
		}
		catch ( Exception ex )
		{
			System.out.println( "erro" + ex.getMessage() );
		}

	
		try
		{
			loginTxt = openFile( "login.txt" );
			
			Final = "";

			while ( loginTxt.hasNext() )
			{
				Final += loginTxt.nextLine()+ "\n";
			}
			
			Final = Final.replace( "\n", "" );

			System.out.println( "pre: \n" + Final );
			Final = hexStringToString( Final );
			System.out.println( "unenconde: \n" + Final );
			
			crip.geraDecifra( Final.getBytes( "ISO-8859-1" ), new File( "key.cripto" ) );

			System.out.println( "aqui" + new String( crip.getTextoDecifrado(), "ISO-8859-1" ) );

		
		}
		catch ( Exception ex )
		{
			System.out.println( "erro" + ex.getMessage() );
		}
	
}
	
	public static String hexStringToString(String hex) 
	{
		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
		for (int i = 0; i < hex.length(); i+=2) {
		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
		}
		buff.rewind();
		Charset cs = Charset.forName("ISO-8859-1");                              // BBB
		CharBuffer cb = cs.decode(buff);                                    // BBB

		return cb.toString();
	}

	public static String hexBytesToString( byte[] b )
	{
		String sOut = "";
		String sBgn = "";
		String sMdl = "";
		String sEnd = "";
		String sSpc = "                                                "; // 48
																			// espaços

		for ( int i = 0; i < b.length; i++ )
		{
			// A cada linha de 16 bytes hexadecimais faz:
			if ( i % 16 == 0 )
				sBgn += Integer.toHexString( i & 0xFFFF | 0x10000 ).substring(
						1, 5 )
						+ " - ";

			// Monta a String do meio, contendo os bytes lidos
			sMdl += Integer.toHexString( b[i] & 0xFF | 0x100 ).substring( 1, 3 )
					+ " ";

			// Monta a String do final, contendo os caracteres lidos
			if ( b[i] >= 32 && b[i] <= 126 )
				sEnd += (char) b[i];
			else
				sEnd += ".";

			// Calcula linha de 16 caracteres com o restante de espaços em
			// branco

			// Monta linha a cada 16 caracteres lidos
			if ( ( i % 16 == 15 ) || ( i == b.length - 1 ) )
			{
				sOut += sBgn
						+ sMdl
						+ sSpc.substring( 3 * ( ( i % 16 ) + 1 ), sSpc.length() )
						+ " - " + sEnd + "\n";
				sBgn = sMdl = sEnd = "";
			}
		}
		return sOut;
	}



	public static Scanner openFile( String path )
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