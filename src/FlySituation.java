import java.util.Date;

public class FlySituation
{
   private Date hours;
   private String destiny;
   private String status;
   
   public FlySituation()
   {}
   
   public FlySituation( String status, String destiny, Date hours )
   {
      setStatus( status );
      setHours( hours );
      setDestiny( destiny );
   }
   
   public String getStatus()
   {
      return status;
   }
  
   public Date getHours()
   {
      return hours;
   }
   
   public String getDestiny()
   {
      return destiny;
   }
   
   public void setStatus( String status )
   {
      this.status = status;
   }
   
   public void setDestiny( String destiny )
   {
      this.destiny = destiny;
   }
   
   public void setHours( Date hours )
   {
      this.hours = hours;
   }
   
}