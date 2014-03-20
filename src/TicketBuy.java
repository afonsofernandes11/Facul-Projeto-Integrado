
public class TicketBuy extends Ticket
{
	private double percent;
	private String date;
	private double valueBack;
	
	public double getValueBack()
	{
		return valueBack;
	}
	public void setValueBack( double valueBack )
	{
		this.valueBack = valueBack;
	}
	
	public double getSimValue()
	{
		return getValue() + getValueBack();
	}
	
	public double getPercent()
	{
		return percent;
	}
	public void setPercent( double percent )
	{
		this.percent = percent;
	}
	public String getDate()
	{
		return date;
	}
	public void setDate( String date )
	{
		this.date = date;
	}
}
