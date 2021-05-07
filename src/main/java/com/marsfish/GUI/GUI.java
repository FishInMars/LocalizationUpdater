package com.marsfish.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;

public class GUI
{
    private JLabel label1=new JLabel(" Old  zh_cn: ");
    private JTextField jtf1=new JTextField();
    private JButton button1=new JButton("Open");
    private JLabel label2=new JLabel("New en_us: ");
    private JTextField jtf2=new JTextField();
    private JButton button2=new JButton("Open");
    private JLabel label3=new JLabel("New zh_cn: ");
    private JTextField jtf3=new JTextField();
    private JButton button3=new JButton("Open");
    private JButton button4 = new JButton("Start");
    public static String zh_cn1;
    public static String zh_cno1;
    public static String en_us1;
    public GUI()
    {
        JFrame jf=new JFrame("TranslationUpdater By:StarFish");
        JPanel panel=new JPanel();

        jtf1.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);
                    String filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    String tmp1 = filepath;
                    String tmp2 = tmp1.replace("zh_cn.json","zh_cn-Output.json");
                    jtf1.setText(tmp1);
                    jtf3.setText(tmp2);
                    zh_cn1 = tmp1;
                    zh_cno1 = tmp2;
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });

        jtf2.setTransferHandler(new TransferHandler()
        {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    Object o = t.getTransferData(DataFlavor.javaFileListFlavor);

                    String filepath = o.toString();
                    if (filepath.startsWith("[")) {
                        filepath = filepath.substring(1);
                    }
                    if (filepath.endsWith("]")) {
                        filepath = filepath.substring(0, filepath.length() - 1);
                    }
                    jtf2.setText(filepath);
                    en_us1 = jtf2.getText().trim();
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (int i = 0; i < flavors.length; i++) {
                    if (DataFlavor.javaFileListFlavor.equals(flavors[i])) {
                        return true;
                    }
                }
                return false;
            }
        });

        panel.add(label1);
        panel.add(jtf1);
        panel.add(button1);
        panel.add(label2);
        panel.add(jtf2);
        panel.add(button2);
        panel.add(label3);
        panel.add(jtf3);
        panel.add(button3);
        panel.add(button4);
        jtf1.setColumns(25);
        jtf2.setColumns(25);
        jtf3.setColumns(25);
        jtf1.setEditable(false);
        jtf2.setEditable(false);
        jtf3.setEditable(false);
        panel.setLayout(new FlowLayout(FlowLayout.LEADING,5 ,5));
        jf.add(panel);
        jf.setBounds(600,600,470,180);
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button1.addActionListener(new ZhButtonListener(jtf1, jtf3));
        button2.addActionListener(new EnButtonListener(jtf2));
        button4.addActionListener(new StartButtonListener());
        
    }

    public static class JsonFilter extends FileFilter {
        @Override
        public boolean accept(File f) {
            if(f.getName().endsWith(".json") || f.isDirectory()){
                return true;
            }
            return false;
        }
        @Override
        public String getDescription() {
            return "Json File";
        }
    }

    public static void main(String[] args)
    {
        new GUI();
    }
}
