/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrador
 */

package View;

//import Enum.DifficultyLevel;
//import UseCaseController.GenericNewGameUCC;
//import UseCaseController.SettingsUseCaseController;
//import UseCaseController.PlayGameUseCaseController;
import Enum.DifficultyLevel;
import UseCaseController.SettingsUseCaseController;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SettingsView1 extends JFrame{
    private JRadioButton RadioCPU;
    private JRadioButton RadioE;
    private JRadioButton RadioH;
    private JRadioButton RadioM;
    private JRadioButton RadioPlayer;
    private JButton SV_Play;
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private JLabel jLabel1;


public SettingsView1() {

    setSize(600,150);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
     buttonGroup1 = new ButtonGroup();
        RadioE = new JRadioButton();
        RadioM = new JRadioButton();
        RadioH = new JRadioButton();
        buttonGroup1.add(RadioE);
        buttonGroup1.add(RadioM);
        buttonGroup1.add(RadioH);
        RadioE.setText("easy");
        RadioM.setText("normal");
        RadioH.setText("hard");
        RadioM.setSelected(true);

        buttonGroup2 = new ButtonGroup();
        RadioPlayer = new JRadioButton();
        RadioCPU = new JRadioButton();
        buttonGroup2.add(RadioCPU);
        buttonGroup2.add(RadioPlayer);

       RadioCPU.setSelected(true);
       RadioCPU.setText("Versus CPU");
       RadioPlayer.setText("Versus Player");

       RadioCPU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioCPUActionPerformed(evt);
            }

            private void RadioCPUActionPerformed(ActionEvent evt) {
            }
        });
        RadioPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioPlayerActionPerformed(evt);
            }

            private void RadioPlayerActionPerformed(ActionEvent evt) {
                //throw new UnsupportedOperationException("Not yet implemented");
            }
        });

        jLabel1 = new JLabel();
        jLabel1.setText("Choose your level");

        SV_Play = new JButton();
        SV_Play.setText("PLAY");





        JPanel pane = new JPanel();
    pane.setLayout(new BorderLayout());
        JPanel upp = new JPanel();
        JPanel center = new JPanel();
        JPanel east= new JPanel();
         JPanel south= new JPanel();
         
        upp.add(jLabel1);
        east.add(RadioE);
        east.add(RadioM);
        east.add(RadioH);
        south.add(SV_Play);
        center.add(RadioCPU);
        center.add(RadioPlayer);

        pane.add(upp,BorderLayout.NORTH);
        pane.add(center, BorderLayout.WEST);
        pane.add(east, BorderLayout.EAST);
        pane.add (south, BorderLayout.SOUTH);


	        getContentPane().add(pane);
		setResizable(false);
		setVisible(true);

       RadioE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioEActionPerformed(evt);
            }

            private void RadioEActionPerformed(ActionEvent evt) {
                //throw new UnsupportedOperationException("Not yet implemented");
            }
        });

        RadioM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioMActionPerformed(evt);
            }

            private void RadioMActionPerformed(ActionEvent evt) {
                //throw new UnsupportedOperationException("Not yet implemented");
            }
        });


        RadioH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RadioHActionPerformed(evt);
            }

            private void RadioHActionPerformed(ActionEvent evt) {
                //throw new UnsupportedOperationException("Not yet implemented");
            }
        });

        SV_Play.addActionListener(new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent evt) {
                SV_PlayActionPerformed(evt);
            }


         private void SV_PlayActionPerformed(ActionEvent evt) {
             DifficultyLevel d = null;
             if(RadioE.isSelected()){
                d = DifficultyLevel.Easy;
            }else if(RadioM.isSelected()){
                d = DifficultyLevel.Normal;
            }else if(RadioH.isSelected()){
                d = DifficultyLevel.Hard;
            }
            PlayGameViewS gui= new PlayGameViewS();


            Boolean vsCpu = false;
            if(RadioPlayer.isSelected()){
                vsCpu = false;
            }else if(RadioCPU.isSelected()){
                vsCpu = true;
            }

            SettingsUseCaseController s = new SettingsUseCaseController();
            //poner el level aqui de vuestro usecaseconttroller
            s.setSettings(d,vsCpu);
            PlayGameViewS pgv = new PlayGameViewS();
            pgv.setVisible(true);
        }
        });
}
}


/*try{
    UIManager.setLookAndFeel
            ("com.sun.java.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
}
catch
       (Exception ex){}

        }
	  public static void main(String args[]) {
       SettingsView1 oi= new SettingsView1();
            }
}*/
