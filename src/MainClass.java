import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalIconFactory;

public class MainClass {

  public static void main(String[] a) {
    JFrame frame = new JFrame("Titled Borders");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Border thisBorder = BorderFactory.createTitledBorder("Easy");
    Icon thatIcon = MetalIconFactory.getFileChooserHomeFolderIcon();
    
    Border thatBorder1 = new MatteBorder(18, 20, 18, 20, thatIcon);
    Border thatBorder2 = new TitledBorder(thatBorder1, "Harder");
    
    Font font = new Font("Serif", Font.ITALIC, 12);
    Border thatBorder = new TitledBorder(thatBorder2, "Hardest",
        TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM, font, Color.RED);
    
    JPanel thisButton = new JPanel();
    thisButton.setBorder(thisBorder);
    JButton thatButton = new JButton("Harder");
    thatButton.setBorder(thatBorder);
    Container contentPane = frame.getContentPane();
    contentPane.setLayout(new GridLayout(1, 2));
    contentPane.add(thisButton);
    contentPane.add(thatButton);
    frame.setSize(300, 200);
    frame.setVisible(true);
  }
}