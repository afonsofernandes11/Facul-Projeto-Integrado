import java.sql.Connection;

public class Payment
{
	private Check check;
	private CreditCard creditCard;
	private int idPayment;
	private Ticket ticket;

	public Ticket getTicket()
	{
		return ticket;
	}
	public void setTicket( Ticket ticket )
	{
		this.ticket = ticket;
	}
	public int getIdPayment()
	{
		return idPayment;
	}
	public void setIdPayment( int idPayment )
	{
		this.idPayment = idPayment;
	}
	public Check getCheck()
	{
		return check;
	}
	public void setCheck( Check check )
	{
		this.check = check;
	}
	public CreditCard getCreditCard()
	{
		return creditCard;
	}
	public void setCreditCard( CreditCard creditCard )
	{
		this.creditCard = creditCard;
	}
	
	public int getType()
	{
		if( check != null  )
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}
	
	public Payment()
	{
		check = null;
		creditCard = null;
	}
	
	
}
