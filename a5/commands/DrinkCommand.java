package org.csc133.a5.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a5.GameWorld;

public class DrinkCommand extends Command {

    private GameWorld gw;

    public DrinkCommand(GameWorld gw) {

        super("Drink");
        this.gw = gw;
    }

    public void actionPerformed(ActionEvent e) {

        System.out.println("Helicopter Picked Up Water.");
        gw.helicopterDrink();
    }
}