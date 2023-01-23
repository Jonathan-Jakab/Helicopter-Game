package org.csc133.a2.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a2.GameWorld;

public class ExitCommand extends Command {

    GameWorld gw;

    public ExitCommand(GameWorld gw){

        super("Exit");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Closing Game!");
        gw.quitGame();
    }
}
