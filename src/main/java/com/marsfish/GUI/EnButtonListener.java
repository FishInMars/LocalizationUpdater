package com.marsfish.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnButtonListener implements ActionListener {
    private JTextField jtf2;
    public static String en_us;
    public EnButtonListener(JTextField jtf2) {
        this.jtf2 = jtf2;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        GUI.JsonFilter jf = new GUI.JsonFilter();
        JFileChooser fc=new JFileChooser();
        fc.setFileFilter(jf);
        int val=fc.showOpenDialog(null);
        if(val== JFileChooser.APPROVE_OPTION)
        {
            //正常选择文件
            String tmp = fc.getSelectedFile().toString();
            if ( !tmp.contains("en_us")) {
                jtf2.setText("Please choose the correct en_us file.");
            }
            else {
                jtf2.setText(tmp);
                en_us = tmp;
            }
        }
        else {
            jtf2.setText("Please choose file!");
        }
    }

}
