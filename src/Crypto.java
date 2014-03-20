import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Crypto
{
	private byte[] textoCifrado;
	private byte[] textoDecifrado;

	public Crypto(){
		textoCifrado = null;
		textoDecifrado = null;
	}

		public void geraChave( File fDummy ) throws IOException
	{

		// Gera uma chave Dummy simétrica (dk: 0 a 100):
		int dk = (int) ( Math.random() * 101 );

		// Grava a chave Dummy simétrica em formato serializado
		ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(
				fDummy ) );
		oos.writeObject( dk );
		oos.close();

	}

	public void geraCifra( byte[] texto, File fDummy ) throws IOException,
			ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream( new FileInputStream(
				fDummy ) );
		int iDummy = (Integer) ois.readObject();
		ois.close();
		textoCifrado = texto;
		for ( int i = 0; i < texto.length; i++ )
		{
			textoCifrado[i] = (byte) ( textoCifrado[i] + i + iDummy );
		}
	}

	public byte[] getTextoCifrado() throws Exception
	{
		return textoCifrado;
	}

	public void geraDecifra( byte[] texto, File fDummy ) throws IOException,
			ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream( new FileInputStream(
				fDummy ) );
		int iDummy = (Integer) ois.readObject();
		ois.close();
		textoDecifrado = texto;
		for ( int i = 0; i < texto.length; i++ )
		{
			textoDecifrado[i] = (byte) ( textoDecifrado[i] - i - iDummy );
		}
	}
	
	public String hexStringToString(String hex) 
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

	public byte[] getTextoDecifrado() throws Exception
	{
		return textoDecifrado;
	}
}
