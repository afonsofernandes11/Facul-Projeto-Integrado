import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.border.EmptyBorder;

public class ISearchPassage extends JDialog implements ActionListener
{
	private ResourceBundle lang;

	private JPanel headerPane;
	private JPanel filterPane;
	private JLabel lblTypeFilter;
	private JLabel lblFilter;
	private JLabel valueFilterPane;
	private JComboBox cmgColumnsFilter;
	private JTextField txtFilter;

	private JTable tableData;
	private TicketTableModel dataSource;
	private TicketDB model;

	private JPanel footerPane;
	private JButton btnCheckIn;
	private JButton btnTransfer;
	private JButton btnCancel;
	private JButton btnExit;

	private String[] columns;
	private Connection conn;

	public ISearchPassage( JFrame Main, ResourceBundle language, Connection conn ){
		super( Main, true );

		lang = language;
		this.conn = conn;
		model = new TicketDB( conn );

		initialize();
	}

	private void initialize()
	{
		createScreen();
		createLayout();
		createColunms();
		createData();
		createControls();
		addControls();
		createActions();
	}

	private void createLayout()
	{
		getContentPane().setLayout( new BorderLayout( 10, 10 ) );
		setLocationRelativeTo( null );
	}

	private void createData()
	{
		dataSource = new TicketTableModel( columns );
		refresh();
	}

	private void refresh()
	{
		refresh( "" );
	}

	private void refresh( String filter )
	{
		dataSource.dados = model.seek( filter );
		dataSource.fireTableDataChanged();
	}

	private void createScreen()
	{
		setTitle( lang.getString( "IConsultaPassagem.titulo" ) );
		setSize( 800, 600 );
		setLocationRelativeTo( null );
	}

	private void createColunms()
	{
		columns = new String[7];
		columns[0] = lang.getString( "IConsultaPassagem.coluna.codigo" );
		columns[1] = lang.getString( "IConsultaPassagem.coluna.status" );
		columns[2] = lang.getString( "IConsultaPassagem.coluna.passageiro" );
		columns[3] = lang.getString( "IConsultaPassagem.coluna.origem" );
		columns[4] = lang.getString( "IConsultaPassagem.coluna.destino" );
		columns[5] = lang.getString( "IConsultaPassagem.coluna.valor" );
		columns[6] = lang.getString( "IConsultaPassagem.coluna.horario" );
	}

	private void addControls()
	{
		headerPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		filterPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );
		valueFilterPane.setLayout( new FlowLayout( FlowLayout.LEFT ) );

		headerPane.add( filterPane );
		headerPane.add( valueFilterPane );
		filterPane.add( lblTypeFilter );
		filterPane.add( cmgColumnsFilter );

		valueFilterPane.add( lblFilter );
		valueFilterPane.add( txtFilter );

		cmgColumnsFilter.setPreferredSize( new Dimension( 100, 25 ) );
		filterPane.setPreferredSize( new Dimension( 170, 60 ) );
		filterPane.setBorder( new EmptyBorder( 0, 30, 0, 10 ) );
		valueFilterPane.setPreferredSize( new Dimension( 200, 60 ) );

		footerPane.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		footerPane.add( btnCheckIn );
		footerPane.add( btnTransfer );
		footerPane.add( btnCancel );
		footerPane.add( btnExit );

		JScrollPane scroll = new JScrollPane( tableData );

		add( headerPane, BorderLayout.PAGE_START );
		add( scroll, BorderLayout.CENTER );
		add( footerPane, BorderLayout.PAGE_END );
	}

	private Boolean canUse( Ticket ticket )
	{
		return ( !ticket.getStatus().equals( "C" ) && !ticket.getStatus()
				.equals( "F" ) );
	}

	private void createActions()
	{
		btnCheckIn.addActionListener( this );
		btnTransfer.addActionListener( this );
		btnExit.addActionListener( this );
		btnCancel.addActionListener( this );
		txtFilter.addKeyListener( new KeyDownChanged() );
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == btnCheckIn )
		{
			onBtnCheckInClick();
			refresh();
		}
		else if ( e.getSource() == btnTransfer )
		{
			onBtnTransferClick();
			refresh();
		}
		else if ( e.getSource() == btnExit )
		{
			onBtnExitClick();
		}
		else if ( e.getSource() == btnCancel )
		{
			onBtnCancelClick();
			refresh();
		}
	}

	private void onBtnTransferClick()
	{
		if ( canUse( dataSource.dados.get( tableData.getSelectedRow() ) ) )
		{
			ITransfer frm = new ITransfer( this, lang, conn );
			frm.setTicket( dataSource.dados.get( tableData.getSelectedRow() ) );
			frm.show();
		}
		else
		{
			JOptionPane
					.showMessageDialog(
							null,
							lang.getString( "IConsultaPassagem.finalizadooucancelado" ) );
		}
	}

	private void onBtnExitClick()
	{
		dispose();
	}

	private void onBtnCancelClick()
	{
		if ( canUse( dataSource.dados.get( tableData.getSelectedRow() ) ) )
		{
			IRePayment frm = new IRePayment( this, lang, conn );
			frm.setTicket( dataSource.dados.get( tableData.getSelectedRow() ) );
			frm.show();
		}
		else
		{
			JOptionPane
					.showMessageDialog(
							null,
							lang.getString( "IConsultaPassagem.finalizadooucancelado" ) );
		}
	}

	public void onBtnCheckInClick()
	{
		if ( canUse( dataSource.dados.get( tableData.getSelectedRow() ) ) )
		{
			ICheckin frm = new ICheckin( this, lang, conn );
			frm.setTicket( dataSource.dados.get( tableData.getSelectedRow() ) );
			frm.show();
		}
		else
		{
			JOptionPane
			.showMessageDialog(
					null,
					lang.getString( "IConsultaPassagem.finalizadooucancelado" ) );
		}
	}

	private void createControls()
	{
		headerPane = new JPanel();
		filterPane = new JPanel();
		lblTypeFilter = new JLabel(
				lang.getString( "IConsultaPassagem.lblTipoFiltro" ) );
		cmgColumnsFilter = new JComboBox( columns );
		valueFilterPane = new JLabel();
		lblFilter = new JLabel( lang.getString( "IConsultaPassagem.lblFiltro" ) );
		txtFilter = new JTextField( 15 );

		tableData = new JTable( dataSource );

		footerPane = new JPanel();
		btnCheckIn = new JButton(
				lang.getString( "IConsultaPassagem.btnCheckIn" ) );
		btnTransfer = new JButton(
				lang.getString( "IConsultaPassagem.btnTrasnferir" ) );
		btnCancel = new JButton( lang.getString( "IConsultaPassagem.btnCancel" ) );
		btnExit = new JButton( lang.getString( "IConsultaPassagem.btnExit" ) );
	}

	class KeyDownChanged implements KeyListener
	{
		@Override
		public void keyPressed( KeyEvent e )
		{
			String field;
			String search;

			if ( e.getKeyCode() == 10 )
			{
				field = getField( cmgColumnsFilter.getSelectedIndex() );

				if ( !GeralFunctions.IsEmpty( field )
						&& !GeralFunctions.IsEmpty( txtFilter.getText() ) )
				{
					if ( cmgColumnsFilter.getSelectedIndex() == 0 )
					{
						if ( GeralFunctions.IsInteger( txtFilter.getText() ) )
						{
							search = field + " = " + txtFilter.getText();
							refresh( search );
						}
						else
						{
							JOptionPane
									.showMessageDialog(
											null,
											lang.getString( "IConsultaVoo.mensagem.codigoNumero" ) );
							txtFilter.setFocusable( true );
						}
					}
					else
					{
						search = field + " LIKE '%" + txtFilter.getText()
								+ "%'";
						refresh( search );
					}
				}
				else
				{
					refresh();
				}
			}
		}

		private String getField( int idx )
		{
			switch ( idx )
			{
				case 0:
					return "idPassagem";

				case 1:
					return "passageiro.nome";

				case 2:
					return "destino";

				default:
					return "";
			}
		}

		@Override
		public void keyReleased( KeyEvent e )
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped( KeyEvent e )
		{
			// TODO Auto-generated method stub

		}
	}
}