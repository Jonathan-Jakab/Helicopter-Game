package org.csc133.a4.gameobjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


///////////////////////////////////////////////////////////////////////////////////////
//////////////WERE NOT IMPLEMENTED DUE TO TIME AND DIFFICULTY CONSTRAINTS//////////////
///////////////////////////////////////////////////////////////////////////////////////



public class FlightPath extends GameObject{

    private BezierCurve bezier;
    private CopyOnWriteArrayList< GameObject > gameObjects;

    public void FlightPath() {

        bezier = new BezierCurve();

        this.gameObjects = gameObjects;
    }

















    public void setPath(BezierCurve bc) {

        this.bezier = bc;
    }

    @Override
    public void draw(Graphics g, Point originParent, Point originScreen) {

    }


    class BezierCurve extends GameObject {

        ArrayList<Point2D> controlPoints;

        public BezierCurve(ArrayList<Point2D> controlPoints) {

            this.controlPoints = controlPoints;
        }

        public BezierCurve() {

            for (GameObject go : gameObjects) {
                controlPoints = new ArrayList<>();
                controlPoints.add(new Point2D(0, 0));
                controlPoints.add(new Point2D(200, 700));
                controlPoints.add(new Point2D(500, 50));
                controlPoints.add(new Point2D(1000, 1000));

            }
        }

        @Override
        public void draw(Graphics g, Point originParent, Point originScreen) {

//            containerTranslate(g, originParent);        //Translate to origin
            drawBezierCurve(g, controlPoints);          //Draw Bezier
        }


        Point2D evaluateCurve(double t) {

            Point2D p = new Point2D(0, 0);
            int d = controlPoints.size() - 1;

            for (int i = 0; i < controlPoints.size(); i++) {

                double b = bernsteinD(d, i, t);
                double mx = b * controlPoints.get(i).getX();
                double my = b * controlPoints.get(i).getX();
                p.setX(p.getX() + mx);
                p.setY(p.getY() + my);
            }
            return p;
        }

        private void drawBezierCurve(Graphics g, ArrayList<Point2D> controlPoints) {

            final double smallFloatIncrement = 0.1;
            g.setColor(ColorUtil.GRAY);
            for (Point2D p : controlPoints) {

                g.fillArc((int) p.getX() - 15, (int) p.getY() - 15, 30, 30, 0, 360);
            }

            g.setColor(ColorUtil.GREEN);

            Point2D currentPoint = controlPoints.get(0);
            Point2D nextPoint;

            double t = 0;
            while (t < 1) {

                nextPoint = evaluateCurve(t);

                g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                        (int) nextPoint.getX(), (int) nextPoint.getY());
                currentPoint = nextPoint;
                t = t + smallFloatIncrement;
            }

            nextPoint = controlPoints.get(controlPoints.size() - 1);

            g.drawLine((int) currentPoint.getX(), (int) currentPoint.getY(),
                    (int) nextPoint.getX(), (int) nextPoint.getY());
        }

        private double bernsteinD(int d, int i, double t) {

            return choose(d, i) * Math.pow(t, i) * Math.pow(1 - t, d - i);
        }

        private double choose(int n, int k) {

            if (k < 0 || k >= n) {

                return 1;
            }
            return choose(n - 1, k - 1) + choose(n - 1, k);
        }
    }


}
