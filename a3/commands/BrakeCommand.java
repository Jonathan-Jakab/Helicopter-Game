package org.csc133.a3.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

public class BrakeCommand extends Command {

    private GameWorld gw;

    public BrakeCommand(GameWorld gw) {

        super("Brake");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter Has Decelerated.");
        gw.decelerateHelicopter();
    }
}