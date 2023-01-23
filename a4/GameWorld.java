package org.csc133.a4;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Point2D;

import org.csc133.a4.gameobjects.*;
import org.csc133.a4.interfaces.HelicopterState;
import org.csc133.a4.strategies.AvoidStrategy;
import org.csc133.a4.strategies.FightStrategy;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameWorld {

    Random rand;

    public static final int INIT_FUEL = 25000;
    private int TOTAL_FIREUNITS;
    private int NUMFIRE_BUILDING;
    private double CURRENT_DESTROYED;
    private double CURRENT_FINANCIAL;

    private Dimension worldSize;
    private River river;
    private Helipad helipad;

    private PlayerHelicopter PH;
    private NonPlayerHelicopter NPH;

    private AvoidStrategy avoid;
    private FightStrategy fight;

    private CopyOnWriteArrayList < GameObject > gameObjects;

    private static GameWorld gameWorld = null;

    public static GameWorld getInstance() {

        if(gameWorld == null) {

            gameWorld = new GameWorld();
        }

        return gameWorld;
    }

    private GameWorld() {

        worldSize = new Dimension();
    }


    public void init() {

        TOTAL_FIREUNITS = 1000;
        NUMFIRE_BUILDING = 2;
        CURRENT_DESTROYED = 0;
        CURRENT_FINANCIAL = 0;

        rand = new Random();

        gameObjects = new CopyOnWriteArrayList < > ();

        river = new River(worldSize);
        gameObjects.add(river);
        helipad = new Helipad(worldSize);
        gameObjects.add(helipad);

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

        gameObjects.add(PH.getInstance(worldSize, helipad));
        gameObjects.add(NPH.getInstance(worldSize, helipad));
    }


    public HelicopterState getHelicopterState() {

        return PH.getInstance(worldSize, helipad).getHelicopterState();
    }

    public boolean isHelicopterMoving() {

        if (PH.getInstance(worldSize, helipad).getSpeed() > 0) {

            return true;
        }
        return false;
    }

    public void startEngineHelicopter() {
        PH.getInstance(worldSize, helipad).startEngine();
    }

    public void stopEngineHelicopter() {
        PH.getInstance(worldSize, helipad).stopEngine();
    }

    public void accelerateHelicopter() {
        PH.getInstance(worldSize, helipad).accelerate();
    }

    public void decelerateHelicopter() {
        PH.getInstance(worldSize, helipad).brake();
    }

    public void helicopterTurnRight() {
        PH.getInstance(worldSize, helipad).steerRight();
    }

    public void helicopterTurnLeft() {
        PH.getInstance(worldSize, helipad).steerLeft();
    }

    public void helicopterFight() {

        Iterator iterator = gameObjects.iterator();

        while (iterator.hasNext()) {

            GameObject tempGameObjects = (GameObject) iterator.next();
            if (tempGameObjects instanceof Fire) {
                Fire tempFire = (Fire) tempGameObjects;
                if (PH.getInstance(worldSize, helipad).near(tempFire)) {
                    PH.getInstance(worldSize, helipad).fight(tempFire);
                    if (tempFire.getFireState().toString() == "extinguished") {
                        gameObjects.remove(tempGameObjects);
                    }
                }
            }
        }
        PH.getInstance(worldSize, helipad).dumpWater();
    }

    public void helicopterDrink() {
        PH.getInstance(worldSize, helipad).drinkFrom(river);
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

                if(tempFire.isSelected()) {

                    if(NPH.getInstance(worldSize, helipad).getHelicopterState().toString() == "off") {

                        NPH.getInstance(worldSize, helipad).startEngine();
                    }

                    if(NPH.getInstance(worldSize, helipad).getWater() < 1000) {

                        for (GameObject go: gameObjects) {
                            if (go instanceof River) {
                                River tempRiver = (River) go;

                                if (tempRiver.underneathHelicopter(NPH.getInstance(worldSize, helipad))) {

                                    NPH.getInstance(worldSize, helipad).drinkFrom(tempRiver);
                                    NPH.getInstance(worldSize, helipad).setStrategy(new AvoidStrategy(NPH.getInstance(worldSize, helipad)));
                                } else {

                                    NPH.getInstance(worldSize, helipad).setStrategy(new FightStrategy(NPH.getInstance(worldSize, helipad), tempRiver));
                                    NPH.getInstance(worldSize, helipad).move(time);
                                }
                            }
                        }
                    } else {

                        if(tempFire.near( NPH.getInstance(worldSize, helipad)) ) {

                            NPH.getInstance(worldSize, helipad).fight(tempFire);
                            NPH.getInstance(worldSize, helipad).setStrategy(new AvoidStrategy(NPH.getInstance(worldSize, helipad)));
                            if (tempFire.getFireState().toString() == "extinguished") {
                                gameObjects.remove(tempGameObjects);
                            }
                        } else {

                            NPH.getInstance(worldSize, helipad).setStrategy(new FightStrategy(NPH.getInstance(worldSize, helipad), tempFire));
                            NPH.getInstance(worldSize, helipad).move(time);
                        }

                    }


                }
            }

            if (tempGameObjects instanceof Building) {
                Building building = (Building) tempGameObjects;

                double buildingDamage = 0;

                if (building.getBuildingDamagePercent() >= 100) {

                    buildingDamage = (building.getDimension().getWidth() * building.getDimension().getHeight());
                } else {

                if (building.getBuildingDamagePercent() >= 10 && building.getNumBurningFires(gameObjects) != 0) {

                    if (rand.nextInt(300000) < Math.pow(building.getBuildingDamagePercent(), 2)) {

                        int fireSize = rand.nextInt(400 - 200) + 200;
                        fireSize = (int) Math.sqrt(fireSize / 3.14);
                        Fire newFire = new Fire(worldSize, fireSize);
                        gameObjects.add(newFire);
                        building.startFireInBuilding(newFire);
                    }
                }

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
                    if (PH.getInstance(worldSize, helipad).getHelicopterState().toString() == "ready") {

//                        movable.move(time);
                        PH.getInstance(worldSize, helipad).move(time);
                    }
                    if (PH.getInstance(worldSize, helipad).hasLandedAt(helipad) && Integer.valueOf(getNumBurningFires()) == 0 && getHelicopterState().toString() == "off") {

                        restartOrQuit(Dialog.show("Game Over!",
                                "You Won \nScore: " + PH.getInstance(worldSize, helipad).getFuel() + "\nPlay Again?",
                                "Heck Yeah!", "Some Other Time"));
                    }
                    if (PH.getInstance(worldSize, helipad).getFuel() <= 0) {

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

            PH.getInstance(worldSize, helipad).restartInstance(worldSize, helipad);
            NPH.getInstance(worldSize, helipad).restartInstance(worldSize, helipad);

            this.init();
        } else {

            this.quitGame();
        }
    }

    public CopyOnWriteArrayList < GameObject > getGameObjectCollection() {

        return gameObjects;
    }

    public String getHeading() {

        return String.valueOf(PH.getInstance(worldSize, helipad).getHeading());
    }

    public String getSpeed() {

        return String.valueOf(PH.getInstance(worldSize, helipad).getSpeed());
    }

    public String getFuel() {

        return String.valueOf(PH.getInstance(worldSize, helipad).getFuel());
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