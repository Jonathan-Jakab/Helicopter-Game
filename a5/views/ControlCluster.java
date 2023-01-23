package org.csc133.a5.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import org.csc133.a5.GameWorld;
import org.csc133.a5.commands.*;
import org.csc133.a5.interfaces.HelicopterState;

public class ControlCluster extends Container {

    private GameWorld gw;

    private Container leftBtnGroup = new Container(new GridLayout(1, 3));
    private Container centerBtnGroup = new Container(new BorderLayout());
    private Container rightBtnGroup = new Container(new GridLayout(1, 3));

    private Button btnLeft;
    private Button btnRight;
    private Button btnFight;

    private Button btnStartEngine;
    private Button btnSpacer;
    private Button btnExit;

    private Button btnDrink;
    private Button btnBrake;
    private Button btnAccel;

    private HelicopterState currHelicopterState;

    public ControlCluster(GameWorld gw) {

        this.gw = gw;
        this.setLayout(new BorderLayout());
        this.getAllStyles().setBgColor(ColorUtil.WHITE);
        this.getAllStyles().setBgTransparency(255);
        ((BorderLayout) this.getLayout()).setCenterBehavior(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE);

        btnLeft = this.buttonMaker(new TurnLeftCommand(gw), "Left");
        btnRight = this.buttonMaker(new TurnRightCommand(gw), "Right");
        btnFight = this.buttonMaker(new FightCommand(gw), "Fight");

        btnStartEngine = this.buttonMaker(new StartEngineCommand(gw), "Start Engine");
        btnSpacer = this.buttonMaker();
        btnExit = this.buttonMaker(new ExitCommand(gw), "Exit");

        btnDrink = this.buttonMaker(new DrinkCommand(gw), "Drink");
        btnBrake = this.buttonMaker(new BrakeCommand(gw), "Brake");
        btnAccel = this.buttonMaker(new AccelerateCommand(gw), "Accel");

        leftBtnGroup.add(btnLeft);
        leftBtnGroup.add(btnRight);
        leftBtnGroup.add(btnFight);
        add(BorderLayout.WEST, leftBtnGroup);

        centerBtnGroup.add(BorderLayout.WEST, btnStartEngine);
        centerBtnGroup.add(BorderLayout.CENTER, btnSpacer);
        centerBtnGroup.add(BorderLayout.EAST, btnExit);
        add(BorderLayout.CENTER, centerBtnGroup);

        rightBtnGroup.add(btnDrink);
        rightBtnGroup.add(btnBrake);
        rightBtnGroup.add(btnAccel);
        add(BorderLayout.EAST, rightBtnGroup);
    }


    public Button buttonMaker() {

        Button button = new Button();
        button.setText(" ");

        button = applySpacerStyle(button);

        return button;
    }

    public Button buttonMaker(Command command, String string) {

        String title = string;
        Command newCommand = command;
        Button button = new Button();
        button.setCommand(command);
        button.setText(title);
        button.setFocusable(false);

        button = applyStyle(button);

        return button;
    }

    private Button applyStyle(Button button) {

        button.getStyle().setBgTransparency(255);
        button.getAllStyles().setBgColor(ColorUtil.LTGRAY);
        button.getAllStyles().setFgColor(ColorUtil.BLUE, true);

        return button;
    }

    private Button applySpacerStyle(Button button) {

        button.getStyle().setBgTransparency(255);
        button.getAllStyles().setBgColor(ColorUtil.WHITE);
        button.getAllStyles().setFgColor(ColorUtil.WHITE, true);

        button.getAllStyles().setPadding(LEFT, 2);
        button.getAllStyles().setPadding(RIGHT, 2);

        return button;
    }

    public void update() {

        currHelicopterState = gw.getHelicopterState();
        if (currHelicopterState.toString() == "off") {

            btnStartEngine.setText("Start Engine");
            btnStartEngine.setCommand(new StartEngineCommand(gw));
            btnStartEngine.getAllStyles().setFgColor(ColorUtil.BLUE, true);
        } else if (currHelicopterState.toString() == "starting") {

            btnStartEngine.setText("Stop Engine");
            btnStartEngine.setCommand(new StopEngineCommand(gw));
            btnStartEngine.getAllStyles().setFgColor(ColorUtil.BLUE, true);
        } else if (currHelicopterState.toString() == "ready" && gw.isHelicopterMoving()) {

            btnStartEngine.setText("Stop Engine");
            btnStartEngine.setCommand(null);
            btnStartEngine.getStyle().setFgColor(ColorUtil.GRAY, true);
        } else if (currHelicopterState.toString() == "ready") {

            btnStartEngine.setText("Stop Engine");
            btnStartEngine.setCommand(new StopEngineCommand(gw));
            btnStartEngine.getAllStyles().setFgColor(ColorUtil.BLUE, true);
        } else if (currHelicopterState.toString() == "stopping") {

            btnStartEngine.setText("Start Engine");
            btnStartEngine.setCommand(new StartEngineCommand(gw));
            btnStartEngine.getAllStyles().setFgColor(ColorUtil.BLUE, true);
        }
    }
}