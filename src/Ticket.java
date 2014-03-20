
public class Ticket
{
	private double value;
	private String dataCheckIn;
	private String number;
	private String type;
	private Passage passage;
	private Fly fly;
	private int code;
	private int idMain;
	private String status;
	private int place;
	
	public int getPlace()
	{
		return place;
	}
	
	public void setPlace( int place )
	{
		this.place = place;
	}
	
	public String getStatus()
	{
		if( status == null) return "";
		else return status;
	}


	public void setStatus( String status )
	{
		this.status = status;
	}

	public Ticket()
	{
		idMain = -1;
	}
	
	public int getIdMain()
	{
		return idMain;
	}
	public void setIdMain( int idMain )
	{
		this.idMain = idMain;
	}
	public Passage getPassage()
	{
		return passage;
	}
	public void setPassage( Passage passage )
	{
		this.passage = passage;
	}
	public Fly getFly()
	{
		return fly;
	}
	public void setFly( Fly fly )
	{
		this.fly = fly;
	}

	public int getCode()
	{
		return code;
	}
	public void setCode( int code )
	{
		this.code = code;
	}
	public double getValue()
	{
		return value;
	}
	public void setValue( double value )
	{
		this.value = value;
	}
	
	public String getDataCheckIn()
	{
		return dataCheckIn;
	}
	public void setDataCheckIn( String dataCheckIn )
	{
		this.dataCheckIn = dataCheckIn;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber( String number )
	{
		this.number = number;
	}
	public String getType()
	{
		return type;
	}
	public void setType( String type )
	{
		this.type = type;
	}
}
