import java.util.Date;

public class SearchPassage
{
   private int code;
   private String name;
   private String destiny;
   
   public SearchPassage()
   {}
   
   public SearchPassage( int code, String name, String destiny )
   {
      setCode( code );
      setName( name );
      setDestiny( destiny );
   }
   
   public int getCode()
   {
      return code;
   }
   
   public String getName()
   {
      return name;
   }
   
   public String getDestiny()
   {
      return destiny;
   }
   
   public void setCode( int code )
   {
      this.code = code;
   }
   
   public void setName( String name )
   {
      this.name = name;
   }
   
   public void setDestiny( String destiny )
   {
      this.destiny = destiny;
   }
}