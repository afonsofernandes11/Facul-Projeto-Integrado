import java.util.Date;

public class Passage
{
	private int code;
	private String name;
	private String lastName;
	private String email;
	private Date dateBirth;
	private int profile;
	private int formTreatment;
	private String phone;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone( String phone )
	{
		this.phone = phone;
	}

	public Passage(){
	}

	public Passage( int code, String name, String lastName, String email,
			Date dateBirth, int profile, int formTreatment ){
		setCode( code );
		setName( name );
		setLastName( lastName );
		setEmail( email );
		setDateBirth( dateBirth );
		setProfile( profile );
		setFormTreatment( formTreatment );
	}

	public int getCode()
	{
		return code;
	}

	public String getName()
	{
		return name;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getEmail()
	{
		return email;
	}

	public Date getDateBirth()
	{
		return dateBirth;
	}

	public String getStrDateBirth()
	{
		if ( getDateBirth() == null )
			return "";
		return getDateBirth().toString();
	}

	public int getProfile()
	{
		return profile;
	}

	public int getFormTreatment()
	{
		return formTreatment;
	}

	public void setCode( int code )
	{
		this.code = code;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public void setDateBirth( Date dateBirth )
	{
		this.dateBirth = dateBirth;
	}

	public void setProfile( int profile )
	{
		this.profile = profile;
	}

	public void setFormTreatment( int formTreatment )
	{
		this.formTreatment = formTreatment;
	}
}