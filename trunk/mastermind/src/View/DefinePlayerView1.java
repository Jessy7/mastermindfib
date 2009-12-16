
/**
 *
 * @author Monica
 */

package View;

//import View.*;
//import UseCaseController.PlayGameUseCaseController;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class DefinePlayerView1 extends JFrame{


        private JLabel name= new JLabel("Please enter your name");
        private JTextField DF_NameText = new JTextField(30);
        private JButton DF_SubmitButton = new JButton();

	public DefinePlayerView1(){

		setSize(500,130);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());

                JPanel upp= new JPanel();
                upp.add(name);
                JPanel center= new JPanel();
                center.add(DF_NameText);
                JPanel down= new JPanel();
                down.setLayout(new FlowLayout());
                down.add (DF_SubmitButton);

                DF_SubmitButton.setText("Submit");
                DF_SubmitButton.addActionListener(new java.awt.event.ActionListener() {

         public void actionPerformed(java.awt.event.ActionEvent evt) {
                DF_SubmitButtonActionPerformed(evt);
            }

         private void DF_SubmitButtonActionPerformed(ActionEvent evt) {

        //PlayGameUseCaseController p = new PlayGameUseCaseController();

        if(DF_NameText.getText().compareTo("")==0){
       JOptionPane.showMessageDialog(null, "You must enter a name", "Error", JOptionPane.WARNING_MESSAGE);

        }else{
        //p.updateRanking(DF_NameText.getText(),0);
        }
            }
        });

                pane.add(upp,BorderLayout.NORTH);
                pane.add(center, BorderLayout.CENTER);
                pane.add(down, BorderLayout.SOUTH);


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
          DefinePlayerView1 df= new DefinePlayerView1();
            }

}