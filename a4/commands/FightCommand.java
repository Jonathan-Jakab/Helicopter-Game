package org.csc133.a4.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a4.GameWorld;

public class FightCommand extends Command {

    private GameWorld gw;

    public FightCommand(GameWorld gw) {

        super("Fight");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter Dropped Water.");
        gw.helicopterFight();
    }
}