package org.csc133.a3;

import com.codename1.ui.geom.Dimension;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Point2D;
import org.csc133.a3.gameobjects.*;
import org.csc133.a3.interfaces.HelicopterState;


import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {

    public static final int INIT_FUEL = 25000;
    private int TOTAL_FIREUNITS;
    private int NUMFIRE_BUILDING;
    private double CURRENT_DESTROYED;
    private double CURRENT_FINANCIAL;

    private Dimension worldSize;
    private River river;
    private Helipad helipad;
    private Helicopter helicopter;
    private Fires fires;
    private Buildings buildings;

    private CopyOnWriteArrayList < GameObject > gameObjects;

    public GameWorld() {

        worldSize = new Dimension();
    }

    public void init() {

        TOTAL_FIREUNITS = 1000;
        NUMFIRE_BUILDING = 2;
        CURRENT_DESTROYED = 0;
        CURRENT_FINANCIAL = 0;

        Random rand = new Random();

        gameObjects = new CopyOnWriteArrayList < > ();

        river = new River(worldSize);
        gameObjects.add(river);
        helipad = new Helipad(worldSize);
        gameObjects.add(helipad);
        helicopter = new Helicopter(worldSize, helipad);

        Point2D building1Location = new Point2D(worldSize.getWidth() / 5.0, worldSize.getHeight() / 3.0);
        Point2D building2Location = new Point2D(worldSize.getWidth() / 2, worldSize.getHeight() / 15.0 * 14.0);
        Point2D building3Location = new Point2D(worldSize.getWidth() / 5.0 * 4.0, worldSize.getHeight() / 3.0);
        Dimension buildingSizeTall = new Dimension(worldSize.getWidth() / 10, worldSize.getHeight() / 2);
        Dimension buildingSizeWide = new Dimension(worldSize.getWidth() / 4 * 3, worldSize.getHeight() / 10);

        gameObjects.add(new Building(worldSize, buildingSizeTall, building1Location));
        gameObjects.add(new Building(worldSize, buildingSizeWide, building2Location));
        gameObjects.add(new Building(worldSize, buildingSizeTall, building3Location));

        for (GameObject go: gameObjects) {
            if (go instanceof Building) {
                Building building = (Building) go;

                for (int i = 0; i < NUMFIRE_BUILDING; i++) {
                    int fireSize = rand.nextInt(400 - 200) + 200;
                    fireSize = (int) Math.sqrt(fireSize / 3.14);

                    Fire newFire = new Fire(worldSize, fireSize);
                    gameObjects.add(newFire);
                    building.startFireInBuilding(newFire);
                }
            }
        }

        gameObjects.add(helicopter);
    }


    public HelicopterState getHelicopterState() {

        return helicopter.getHelicopterState();
    }

    public boolean isHelicopterMoving() {

        if (helicopter.getSpeed() > 0) {

            return true;
        }
        return false;
    }

    public void startEngineHelicopter() {
        helicopter.startEngine();
    }

    public void stopEngineHelicopter() {
        helicopter.stopEngine();
    }

    public void accelerateHelicopter() {
        helicopter.accelerate();
    }

    public void decelerateHelicopter() {
        helicopter.brake();
    }

    public void helicopterTurnRight() {
        helicopter.steerRight();
    }

    public void helicopterTurnLeft() {
        helicopter.steerLeft();
    }

    public void helicopterFight() {

        Iterator iterator = gameObjects.iterator();

        while (iterator.hasNext()) {

            GameObject tempGameObjects = (GameObject) iterator.next();
            if (tempGameObjects instanceof Fire) {
                Fire tempFire = (Fire) tempGameObjects;
                if (helicopter.near(tempFire)) {
                    helicopter.fight(tempFire);
                    if (tempFire.getFireState().toString() == "extinguished") {
                        gameObjects.remove(tempGameObjects);
                    }
                }
            }
        }
        helicopter.dumpWater();
    }

    public void helicopterDrink() {
        helicopter.drinkFrom(river);
    }

    public void quitGame() {
        Display.getInstance().exitApplication();
    }

    public void tick(int time) {

        Iterator iterator = gameObjects.iterator();

        while (iterator.hasNext()) {

            GameObject tempGameObjects = (GameObject) iterator.next();

            if (100 == Integer.valueOf(getTotalPercentageBuildingsDestroyed())) {
                restartOrQuit(Dialog.show("Game Over!",
                        "The Buildings Have Been Destroyed! :( \nPlay Again?",
                        "Heck Yeah!", "Some Other Time"));
            }

            if (tempGameObjects instanceof Fire) {
                Fire tempFire = (Fire) tempGameObjects;

                if ((new Random().nextInt(70)) < 5) {
                    tempFire.grow();
                }
            }

            if (tempGameObjects instanceof Building) {
                Building building = (Building) tempGameObjects;

                double buildingDamage = 0;

                if (building.getBuildingDamagePercent() >= 100) {
                    buildingDamage = (building.getDimension().getWidth() * building.getDimension().getHeight());
                } else {
                    for (GameObject go: gameObjects) {
                        if (go instanceof Fire) {
                            Fire fire = (Fire) go;
                            if (building.isFireInBuilding(fire)) {
                                buildingDamage += (3.14 * Math.pow(fire.getDimension().getWidth() / 2, 2));
                            }
                        }
                    }
                }
                building.damageBuildings(buildingDamage);
            }

            if (tempGameObjects instanceof MovableGameObject) {
                MovableGameObject movable = (MovableGameObject) tempGameObjects;
                if (tempGameObjects instanceof Helicopter) {

                    Helicopter tempHelicopter = (Helicopter) tempGameObjects;
                    if (helicopter.getHelicopterState().toString() == "ready") {

                        movable.move(time);
                    }
                    if (helicopter.hasLandedAt(helipad) && Integer.valueOf(getNumBurningFires()) == 0 && getHelicopterState().toString() == "off") {
                        restartOrQuit(Dialog.show("Game Over!",
                                "You Won \nScore: " + helicopter.getFuel() + "\nPlay Again?",
                                "Heck Yeah!", "Some Other Time"));
                    }
                    if (helicopter.getFuel() <= 0) {
                        restartOrQuit(Dialog.show("Game Over!",
                                "You ran out of fuel! :( \nPlay Again?",
                                "Heck Yeah!", "Some Other Time"));
                    }
                }
            }
        }
    }

    private void restartOrQuit(boolean restart) {

        if (restart) {
            this.init();
        } else {
            this.quitGame();
        }
    }

    public CopyOnWriteArrayList < GameObject > getGameObjectCollection() {

        return gameObjects;
    }

    public String getHeading() {

        return String.valueOf(helicopter.getHeading());
    }

    public String getSpeed() {

        return String.valueOf(helicopter.getSpeed());
    }

    public String getFuel() {

        return String.valueOf(helicopter.getFuel());
    }

    public String getNumBurningFires() {

        int numFires = 0;
        for (GameObject go: gameObjects) {
            if (go instanceof Fire) {
                numFires++;
            }
        }
        return String.valueOf(numFires);
    }

    public String getTotalBurningFireSize() {

        int fireSize = 0;
        for (GameObject go: gameObjects) {
            if (go instanceof Fire) {
                Fire fire = (Fire) go;
                fireSize += fire.getDimension().getWidth();
            }
        }
        return String.valueOf(fireSize);
    }

    public String getTotalPercentageBuildingsDestroyed() {

        double totalFireArea = totalFireArea();
        double totalBuildingArea = totalBuildingSize();
        double precentDestroyed = 0;

        precentDestroyed = (totalFireArea / totalBuildingArea) * 100;
        if (precentDestroyed > CURRENT_DESTROYED) {
            CURRENT_DESTROYED = Math.ceil(precentDestroyed);
            return String.valueOf((int) CURRENT_DESTROYED);
        }
        return String.valueOf((int) CURRENT_DESTROYED);

    }

    public String getFinancialLoss() {

        double financialLoss = 0;

        for (GameObject go: gameObjects) {
            if (go instanceof Building) {
                Building building = (Building) go;
                financialLoss += building.getBuildingLoss();
            }
        }

        return String.valueOf((int) financialLoss);
    }

    public double totalBuildingSize() {

        int buildingSize = 0;

        for (GameObject go: gameObjects) {
            if (go instanceof Building) {
                Building building = (Building) go;
                buildingSize += (building.getDimension().getWidth() * building.getDimension().getHeight());
            }
        }
        return buildingSize;
    }

    public double totalFireArea() {

        double totalFireArea = 0;

        for (GameObject go1: gameObjects) {
            if (go1 instanceof Building) {
                Building building = (Building) go1;

                if (building.getBuildingDamagePercent() >= 100) {
                    totalFireArea += (building.getDimension().getWidth() * building.getDimension().getHeight());
                } else {
                    for (GameObject go: gameObjects) {
                        if (go instanceof Fire) {
                            Fire fire = (Fire) go;
                            if (building.isFireInBuilding(fire)) {
                                totalFireArea += (3.14 * Math.pow(fire.getDimension().getWidth() / 2, 2));
                            }
                        }
                    }
                }
            }
        }
        return totalFireArea;
    }

    public void setDimension(Dimension worldSize) {

        this.worldSize = worldSize;
    }
}