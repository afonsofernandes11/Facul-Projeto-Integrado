import java.util.*;

public class CreditCard
{
	private String operadora;
	private String number;
	private String code;
	private Date validate;
	
	public String getOperadora()
	{
		return operadora;
	}
	public void setOperadora( String operadora )
	{
		this.operadora = operadora;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber( String number )
	{
		this.number = number;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode( String code )
	{
		this.code = code;
	}
	public Date getValidate()
	{
		return validate;
	}
	public void setValidate( Date validate )
	{
		this.validate = validate;
	}

	public Boolean validate()
	{
		Scanner fileText;
		AcessoTXT txtReader;
		String tempOperadora;
		String tempCode;
		String tempNumber;
		Date tempValidate;
		Boolean validate = false;
		
		txtReader = new AcessoTXT();
		fileText = txtReader.openFile( "cartao.txt");
		
		while( fileText.hasNext() )
		{
			tempOperadora = fileText.next();
			tempNumber = fileText.next();
			tempCode = fileText.next();
			tempValidate = GeralFunctions.formataData( fileText.next(), "dd/MM" );
			
			if( tempOperadora.equals( getOperadora() ) )
			{
				if( tempNumber.equals( getNumber() ) && tempCode.equals( getCode() ) )
				{
					if( tempValidate.equals( getValidate() ) )
					{
						validate = true;
					}
				}
			}
		}
		
		return validate;
	}
	
	
}
