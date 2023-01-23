package org.csc133.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a4.GameWorld;

public class TurnRightCommand extends Command {

    private GameWorld gw;

    public TurnRightCommand(GameWorld gw) {

        super("Right");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter Turned Right.");
        gw.helicopterTurnRight();
    }
}