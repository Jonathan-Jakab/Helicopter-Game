package org.csc133.a5.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a5.GameWorld;

public class TurnLeftCommand extends Command {

    private GameWorld gw;

    public TurnLeftCommand(GameWorld gw) {

        super("Left");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter Turned Left.");
        gw.helicopterTurnLeft();
    }
}