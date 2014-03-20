import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

public class IMain extends JFrame implements ActionListener
{
	private ResourceBundle lang;
	private int language;

	// barra de menu
	private JMenuBar menuBar;

	// menu voo
	private JMenu menuFly;
	private JMenuItem menuFlySearch;
	private JMenuItem menuFlyInsert;
	private JMenuItem menuFlyStatus;

	// menu aeronave
	private JMenu menuAircraft;
	private JMenuItem menuAircraftSearch;
	private JMenuItem menuAircraftInsert;

	// menu passagens
	private JMenu menuPassage;
	private JMenuItem menuPassageSearch;
	private JMenuItem menuPassageCheckIn;

	private Connection conn;
	private Login login;

	public IMain( int Language, Connection conn, Login login ){
		language = Language;
		
		this.conn = conn;
		this.login = login;
		
		initialize();
	}

	private void initializeLanguage()
	{
		lang = Language.get( language );
	}

	private void initialize()
	{
		initializeLanguage();

		setExtendedState( JFrame.MAXIMIZED_BOTH );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		createScreen();

		createActions();
	}

	private void createScreen()
	{
		createControls();
	}

	private void createActions()
	{
		menuFlySearch.addActionListener( this );
		
		menuAircraftSearch.addActionListener( this );
		
		if( login.IsAdministrator() )
		{
			menuAircraftInsert.addActionListener( this );
			menuFlyInsert.addActionListener( this );
		}
		
		menuPassageSearch.addActionListener( this );
		menuFlyStatus.addActionListener( this );
		menuPassageCheckIn.addActionListener( this );
	}

	private void createControls()
	{
		createMenus();
	}

	private void createMenus()
	{
		menuBar = new JMenuBar();

		createMenuFly();
		createMenuAircraft();
		createMenuPassage();

		setJMenuBar( menuBar );

	}

	private void createMenuPassage()
	{
		menuPassage = new JMenu( lang.getString( "menu.passagem" ) );
		menuPassageSearch = new JMenuItem(
				lang.getString( "menu.passagem.consultar" ) );
		menuPassageCheckIn = new JMenuItem(
				lang.getString( "menu.passagem.checkin" ) );

		menuPassage.setMnemonic( lang.getString( "menu.passagem.m" ).charAt( 0 ) );
	
		menuPassageCheckIn.setMnemonic( lang.getString( "menu.passagem.checkin.m" ).charAt( 0 ) );

		menuBar.add( menuPassage );

		menuPassage.add( menuPassageSearch );
		//menuPassage.add( menuPassageCheckIn );
	}

	private void createMenuAircraft()
	{
		menuAircraft = new JMenu( lang.getString( "menu.aeronave" ) );
		menuAircraftSearch = new JMenuItem( lang.getString( "menu.aeronave.consultar" ) );
		menuAircraftInsert = new JMenuItem( lang.getString( "menu.aeronave.cadastrar" ) );
		
		menuAircraft.setMnemonic( lang.getString( "menu.aeronave.m" ).charAt( 0 ) );
		menuAircraftSearch.setMnemonic( lang.getString( "menu.aeronave.consultar.m" ).charAt( 0 ) );
		menuAircraftInsert.setMnemonic( lang.getString( "menu.aeronave.cadastrar.m" ).charAt( 0 ) );

		menuBar.add( menuAircraft );

		menuAircraft.add( menuAircraftSearch );
		menuAircraft.add( menuAircraftInsert );
	
		if( !login.IsAdministrator() )
		{
			menuAircraft.setVisible( false );
		}
	}

	private void createMenuFly()
	{
		menuFly = new JMenu( lang.getString( "menu.voo" ) );
		menuFlySearch = new JMenuItem( lang.getString( "menu.voo.consultar" ) );
		menuFlyInsert = new JMenuItem( lang.getString( "menu.voo.cadastrar" ) );
		menuFlyStatus = new JMenuItem( lang.getString( "menu.voo.situacoes" ) );

		menuFly.setMnemonic( lang.getString( "menu.voo.m" ).charAt( 0 ) );
		menuFlySearch.setMnemonic( lang.getString( "menu.voo.consultar.m" ).charAt( 0 ) );
		menuFlyInsert.setMnemonic( lang.getString( "menu.voo.cadastrar.m" ).charAt( 0 ) );
		menuFlyStatus.setMnemonic( lang.getString( "menu.voo.situacoes.m" ).charAt( 0 ) );

		menuBar.add( menuFly );

		menuFly.add( menuFlySearch );
		menuFly.add( menuFlyInsert );
		menuFly.add( menuFlyStatus );
	
		if( !login.IsAdministrator() )
		{
			menuFlyInsert.setVisible( false );
		}
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == menuFlySearch )
		{
			onClickMenyFlyeSearch();
		}
		else if ( e.getSource() == menuFlyInsert )
		{
			onClickMenuFlyInsert();
		}
		else if ( e.getSource() == menuAircraftSearch )
		{
			onMenuAircrafitSearch();
		}
		else if ( e.getSource() == menuAircraftInsert )
		{
			onMenuAircraftInsert();
		}
		else if ( e.getSource() == menuPassageSearch )
		{
			onMenuPassageSearch();
		}
		else if ( e.getSource() == menuFlyStatus )
		{
			onMenuFlyStatus();
		}
		else if ( e.getSource() == menuPassageCheckIn )
		{
			onMenuPassageCheckIn();
		}
	}

	public void onMenuPassageCheckIn()
	{
		ICheckin frm = new ICheckin( this, lang, conn );
		frm.show();
	}

	public void onMenuFlyStatus()
	{
		ISituations frm = new ISituations( this, lang, conn );
		frm.show();
	}

	public void onMenuAircrafitSearch()
	{
		ISearchAircrafit Frm = new ISearchAircrafit( this, lang, conn);
		Frm.show();
	}

	public void onMenuAircraftInsert()
	{
		IAircrafit frm = new IAircrafit( this, lang, conn );
		frm.show();
	}

	public void onMenuPassageSearch()
	{
		ISearchPassage frm = new ISearchPassage( this, lang, conn );
		frm.show();
	}

	public void onClickMenyFlyeSearch()
	{
		ISearchFly Frm = new ISearchFly( this, lang, conn, login );
		Frm.show();
	}

	public void onClickMenuFlyInsert()
	{
		IFly Frm = new IFly( this, lang, conn );
		Frm.setMode( 1 );
		Frm.show();
	}

}