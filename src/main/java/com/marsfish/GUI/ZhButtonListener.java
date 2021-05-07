package com.marsfish.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZhButtonListener implements ActionListener {
    private JTextField jtf1,jtf3;
    public static String zh_cn;
    public static String zh_cno;

    public ZhButtonListener(JTextField jtf1, JTextField jtf3) {
        this.jtf1 = jtf1;
        this.jtf3 = jtf3;
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
            String tmp1 = fc.getSelectedFile().toString();
            if ( !tmp1.contains("zh_cn") ) {
                jtf1.setText("Please choose the correct zh_cn file.");
            }
            else {
                String tmp2 = tmp1.replace("zh_cn.json","zh_cn-Output.json");
                jtf1.setText(tmp1);
                jtf3.setText(tmp2);
                zh_cn = tmp1;
                zh_cno = tmp2;
            }
        }
        else
        {
            jtf1.setText("Please choose file!");
        }
    }
}
