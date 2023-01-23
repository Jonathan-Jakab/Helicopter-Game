package org.csc133.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a4.GameWorld;

public class StartEngineCommand extends Command {

    private GameWorld gw;

    public StartEngineCommand(GameWorld gw) {

        super("Start Engine");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter's Engine has started.");
        gw.startEngineHelicopter();
    }
}