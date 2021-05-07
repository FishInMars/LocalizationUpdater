package com.marsfish.GUI;

import com.marsfish.LocalizationUpdater;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        try {
            LocalizationUpdater.main(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
