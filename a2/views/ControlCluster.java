package org.csc133.a2.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a2.GameWorld;
import org.csc133.a2.commands.*;

public class ControlCluster extends Container{

    private GameWorld gw;

        private  Container leftBtnGroup    = new Container(new GridLayout(1, 3));
        private  Container centerBtnGroup  = new Container(new BorderLayout());
        private  Container rightBtnGroup   = new Container(new GridLayout(1, 3));

        private  Button btnRight;
        private  Button btnLeft;
        private  Button btnFight;
        private  Button btnExit;
        private  Button btnDrink;
        private  Button btnBrake;
        private  Button btnAccel;

        public ControlCluster(GameWorld gw) {

            this.gw = gw;
            this.setLayout(new BorderLayout());
            this.getAllStyles().setBgColor(ColorUtil.WHITE);
            this.getAllStyles().setBgTransparency(255);
            ((BorderLayout)this.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);


            leftBtnGroup    = new Container(new GridLayout(1, 3));
            centerBtnGroup  = new Container(new BorderLayout());
            rightBtnGroup   = new Container(new GridLayout(1, 3));

            btnRight           = this.buttonMaker(new TurnRightCommand(gw), "Right");
            btnLeft            = this.buttonMaker(new TurnLeftCommand(gw), "Left");
            btnFight           = this.buttonMaker(new FightCommand(gw), "Fight");
            btnExit            = this.buttonMaker(new ExitCommand(gw), "Exit");
            btnDrink           = this.buttonMaker(new DrinkCommand(gw), "Drink");
            btnBrake           = this.buttonMaker(new BrakeCommand(gw), "Brake");
            btnAccel           = this.buttonMaker(new AccelerateCommand(gw), "Accel");

                leftBtnGroup.add(btnRight);
                leftBtnGroup.add(btnLeft);
                leftBtnGroup.add(btnFight);
                add(BorderLayout.WEST, leftBtnGroup);

                centerBtnGroup.add(BorderLayout.CENTER, btnExit);
                centerBtnGroup.add(BorderLayout.EAST, new Button());
                centerBtnGroup.add(BorderLayout.WEST, new Button());
                add(BorderLayout.CENTER, centerBtnGroup);



                rightBtnGroup.add(btnDrink);
                rightBtnGroup.add(btnBrake);
                rightBtnGroup.add(btnAccel);
                add(BorderLayout.EAST, rightBtnGroup);
        }

        public Button buttonMaker(Command command, String string){

            String title = string;
            Command newCommand = command;
            Button button = new Button();
            button.setCommand(command);
            button.setText(title);
            button.setFocusable(false);


            if(title == "Exit"){
                button = applyExitStyle(button);
            }else{
                button = applyStyle(button);
            }

            return button;
        }

    private Button applyStyle(Button button) {

        button.getStyle().setBgTransparency(255);
        button.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        button.getAllStyles().setFgColor(ColorUtil.BLUE, true);

        return button;
    }

    private Button applyExitStyle(Button button) {

        button.getStyle().setBgTransparency(255);
        button.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        button.getAllStyles().setFgColor(ColorUtil.BLUE, true);

        button.getAllStyles().setPadding(LEFT, 20);
        button.getAllStyles().setPadding(RIGHT, 20);

        return button;
    }
}