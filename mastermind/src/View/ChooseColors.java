/**
 *
 * @author Monica
 */
package View;
//import UseCaseController.PlayGameUseCaseController;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class ChooseColors extends JFrame{



	String currentColor= "G";
	JButton jButtonColor = new JButton("Current Color");
	private String[] trying = new String[6];


	private boolean col1 = false;
	private boolean col2 = false;
	private boolean col3 = false;
	private boolean col4 = false;
        private boolean col5 = false;
        private boolean col6 = false;

        private JLabel choosen= new JLabel("Pattern");
        private JLabel jLabelPattern= new JLabel("Choose the code");
        private JButton[]solution=new JButton[6];


	public ChooseColors(){

		setSize(600,150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

                JButton start= new JButton ("Start Game");

		//start.addActionListener(new playGameUseController());


		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
                JPanel jPanelSolution= new JPanel();
                jPanelSolution.add(choosen);
                for (int i = 0; i < 6; i++){
				JButton sol = new JButton(""+i);
				sol.addActionListener(new GameListener());
				sol.setPreferredSize(new Dimension(37,37));
				sol.setBackground(Color.WHITE);
				solution[i] = sol;
				jPanelSolution.add(solution[i]);
			}

                JPanel boxes= new JPanel();
                boxes.setLayout(new FlowLayout());

		JButton yellow = new JButton(":::");
		JButton magenta = new JButton(":::");
		JButton cyan = new JButton(":::");
		JButton green = new JButton(":::");
		JButton red = new JButton(":::");
		JButton blue = new JButton(":::");


		yellow.addActionListener(new YellowListener());
		magenta.addActionListener(new MagentaListener());
		blue.addActionListener(new BlueListener());
		green.addActionListener(new GreenListener());
		red.addActionListener(new RedListener());
		cyan.addActionListener(new CyanListener());

		yellow.setBackground(Color.yellow);
		magenta.setBackground(Color.magenta);
		cyan.setBackground(Color.cyan);
		green.setBackground(Color.green);
		red.setBackground(Color.red);
		blue.setBackground(Color.blue);

		jButtonColor.setEnabled(false);
		jButtonColor.setBackground(Color.yellow);


                boxes.add(jButtonColor);
		boxes.add(yellow);
                boxes.add(magenta);
                boxes.add(cyan);
                boxes.add(green);
                boxes.add(red);
                boxes.add(blue);
                JPanel jLabelPanel= new JPanel();
                jLabelPanel.add(jLabelPattern);
               Component add = jPanelSolution.add(start);

                pane.add(jLabelPanel,BorderLayout.NORTH);
                pane.add(boxes, BorderLayout.CENTER);
                pane.add(jPanelSolution, BorderLayout.SOUTH);

	        getContentPane().add(pane);
		setResizable(false);
		setVisible(true);
try{
    UIManager.setLookAndFeel
            ("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
}
catch
       (Exception ex){}

        }
	  public static void main(String args[]) {
          ChooseColors choo= new ChooseColors();
            }

	public class YellowListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor = "Y";
			jButtonColor.setBackground(Color.yellow);

		}
	}

	public class MagentaListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor= "M";
			jButtonColor.setBackground(Color.magenta);
		}
	}

	public class CyanListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor = "C";
			jButtonColor.setBackground(Color.CYAN);
		}
	}

	public class GreenListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor= "G";
			jButtonColor.setBackground(Color.green);
		}
	}

	public class RedListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor = "R";
			jButtonColor.setBackground(Color.red);
		}
	}

	public class BlueListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			currentColor = "B";
			jButtonColor.setBackground(Color.blue);
		}
        }

     public class GameListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			JButton campo = (JButton)e.getSource();
			int temp_col = Integer.parseInt(campo.getText());
			if (temp_col == 0) {col1 = true;}
			if (temp_col == 1) {col2 = true;}
			if (temp_col == 2) {col3 = true;}
			if (temp_col == 3) {col4 = true;}
                        if (temp_col == 4) {col5 = true;}
                        if (temp_col == 5) {col6 = true;}

			solution[temp_col].setBackground(jButtonColor.getBackground());
			trying [temp_col] = (""+currentColor);
			if (col1&&col2&&col3&&col4&&col5&&col6){
			//	verifica();

				col1 = false;
				col2 = false;
				col3 = false;
				col4 = false;
                                col5=false;
                                col6=false;
			}
		}
	}
        }


