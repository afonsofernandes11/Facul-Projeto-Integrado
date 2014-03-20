import java.util.Date;
import java.util.ResourceBundle;

public class Fly
{
   private int code;
   private String source;
   private String destiny;
   private double value;
   
   private int qtdeScales;
   private String date; 

   private Aircrafit aircrafit;
   private String situation;   

   public Fly()
   {
      setCode( -1 );
      setSource( "" );
      setDestiny ( "" );
      setQtdeScales( -1 );
      setAircrafit( null );
      setSituation( "" );
      setValue( 0.00 );
   }
   
   public void setValue( double value )
   {
	   this.value = value;
   }
   
   public void setCode( int code )
   {
      this.code = code;
   }
   
   public void setSource( String source )
   {
      this.source = source;
   }
   
   public double getValue ()
   {
	   return value;
   }
   
   public void setDestiny( String destiny )
   {
      this.destiny = destiny;
   }
   
   public void setDateTime( String date )
   {
      this.date = date;
   }
   
   public void setQtdeScales( int qtde )
   {
      this.qtdeScales = qtde;
   }
   
   public void setAircrafit( Aircrafit aircrafit )
   {
      this.aircrafit = aircrafit;
   }
   
   public void setSituation( String situation )
   {
      this.situation = situation;
   }
   
   public int getCode()
   {
      return code;
   }
   
   public String getSource()
   {
      return source;
   }
   
   public String getDestiny()
   {
      return destiny;
   }
   
   public String getDateTime()
   {
      return date;
   }
   
   
   public String getFormatDate()
   {
	    String oldDate = getDateTime();
		String day = oldDate.substring(0,2);
		String months = oldDate.substring(3,5);
		String year = oldDate.substring(6, 10);
		String hour = oldDate.substring( 10, 16  );
		
		return year + "/" + months + "/" + day + hour;
	}

   public void setFormatDate(String oldDate)
   {
	    if( oldDate != null && oldDate.length() >= 15 )
	    {
			String year = oldDate.substring(0,4);
			String months = oldDate.substring(5,7);
			String day = oldDate.substring(8,10);
			String hour = oldDate.substring( 10, 16  );
			
			setDateTime( day + "/" + months + "/" + year + hour);
		}
   }
   public int getQtdeScales()
   {
      return qtdeScales;
   }
   
   public Aircrafit getAircrafit()
   {
      return aircrafit;
   }
   
   public String getDescSituation( ResourceBundle lang )
   {
	   String Return;
	   
	   switch( getSituation() )
	   {
		   default:
			   return lang.getString("status.espera");
			   
		   case "2" :
			   return lang.getString( "status.cancelado");
			
		   case "3":
			   return lang.getString( "status.atrasado" );
		
		   case "4":
			   return lang.getString( "status.embarque" );
		   
		   case "5":
			   return lang.getString( "status.finalizado" );
	   
		   case "1" :
			   return lang.getString( "status.confirmado" );
	   }
   }
   
   public String getSituation()
   {
      return situation;
   }
}