package View;


/**
 *
 * @author Monica
 */
//import UseCaseController.PlayGameUseCaseController;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class PlayGameViewM extends PlayGameView{


	private int memory = 9;
	String currentColor= "Y";
	JButton jButtonColor = new JButton("Current Color");
	private String[][] trying = new String[10][5];
        private JLabel round= new JLabel("Round:");

	private boolean col1 = false;
	private boolean col2 = false;
	private boolean col3 = false;
	private boolean col4 = false;
        private boolean col5 = false;

	private JButton[][] pegs = new JButton[10][5];
	private JButton[][] pattern= new JButton[10][5];
          private JLabel svar= new JLabel("Solution");
        private JButton[]solution=new JButton[5];


	public PlayGameViewM(){

		setSize(535,570);
                setTitle("Mastermind                   Setting: Normal");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
                JButton saveGame = new JButton("Save game");
                JButton hint = new JButton("Hint");
		JButton exit = new JButton("Exit");
                JButton ok= new JButton ("Check");
		//saveGame.addActionListener(new saveGameView());
		//hint.addActionListener(new hintGame());??
		//ok.addActionListener(new playGameUseController());


		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
                JPanel jPanelSolution= new JPanel();
                jPanelSolution.add(svar);
                for (int i = 0; i < 5; i++){
				JButton sol = new JButton(""+i);

				sol.setPreferredSize(new Dimension(40,40));
				sol.setBackground(Color.GRAY);
				solution[i] = sol;
				jPanelSolution.add(solution[i]);
			}

                Box boxes= new Box(BoxLayout.PAGE_AXIS);

		JButton yellow = new JButton(":::");
		JButton magenta = new JButton(":::");
		JButton cyan = new JButton(":::");
		JButton green = new JButton(":::");
		JButton red = new JButton(":::");
		JButton blue = new JButton(":::");
                  jPanelSolution.add(ok);

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



		JPanel jPanel1 = new JPanel();
		jPanel1.setPreferredSize(new Dimension(200,400));

		for (int rig = 0; rig < 10; rig++){
			jPanel1.add(new JLabel(""+(1+rig)));
			for (int col = 0; col < 5; col++){
				JButton patt = new JButton(""+col);
				patt.addActionListener(new GameListener());
				patt.setPreferredSize(new Dimension(35,35));
				patt.setBackground(Color.white);
				pattern[rig][col] = patt;
				jPanel1.add(pattern[rig][col]);
			}
		}

		JPanel jPanel2 = new JPanel();
                jPanel2.setPreferredSize(new Dimension(200,400));
		for (int rig = 0; rig < 10; rig++){

			for (int col = 0; col < 5; col++){
				JButton peg = new JButton();
				peg.setPreferredSize(new Dimension(35,35));
				peg.setEnabled(false);
				peg.setBackground(Color.LIGHT_GRAY);
				pegs[rig][col] = peg;
				jPanel2.add(pegs[rig][col]);
			}

		}
		JPanel jPanelButtons = new JPanel();
                jPanelButtons.setLayout(new FlowLayout());
		jPanelButtons.add(saveGame);
		jPanelButtons.add(hint);
		jPanelButtons.add(exit);
                 jPanelButtons.add(round);

                pane.add(jPanelButtons, BorderLayout.NORTH);
		pane.add(jPanel1, BorderLayout.CENTER);
		pane.add(jPanel2, BorderLayout.WEST);
                pane.add(boxes, BorderLayout.EAST);
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
          PlayGameViewM guit= new PlayGameViewM();
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

			pattern[memory][temp_col].setBackground(jButtonColor.getBackground());
			trying[memory][temp_col] = (""+currentColor);
			if (col1&&col2&&col3&&col4&&col5){
			//	verifica();

				col1 = false;
				col2 = false;
				col3 = false;
				col4 = false;
                                col5=false;
			}
		}
	}
        }



