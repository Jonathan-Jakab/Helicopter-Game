package org.csc133.a5.views;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import java.io.IOException;

public class DigitalDisplayComponent extends Component {

    Image[] digitImages = new Image[10];

    private int ledColor;
    private int numDigitsShowing;
    Image[] displayDigits;

    public DigitalDisplayComponent(int color, int numDigits) {

        ledColor = color;
        numDigitsShowing = numDigits;
        displayDigits = new Image[numDigitsShowing];

        try {
            digitImages[0] = Image.createImage("/LED_digit_0.png");
            digitImages[1] = Image.createImage("/LED_digit_1.png");
            digitImages[2] = Image.createImage("/LED_digit_2.png");
            digitImages[3] = Image.createImage("/LED_digit_3.png");
            digitImages[4] = Image.createImage("/LED_digit_4.png");
            digitImages[5] = Image.createImage("/LED_digit_5.png");
            digitImages[6] = Image.createImage("/LED_digit_6.png");
            digitImages[7] = Image.createImage("/LED_digit_7.png");
            digitImages[8] = Image.createImage("/LED_digit_8.png");
            digitImages[9] = Image.createImage("/LED_digit_9.png");

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < numDigitsShowing; i++) {
            displayDigits[i] = digitImages[0];
        }
        ledColor = color;

    }

    public void paint(Graphics g) {

        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = displayDigits[0].getWidth();
        int digitHeight = displayDigits[0].getHeight();
        int displayWidth = numDigitsShowing*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float)displayWidth);

        int displayDigitWidth = (int)(scaleFactor*digitWidth);
        int displayDigitHeight = (int)(scaleFactor*digitHeight);
        int displayDisplayWidth = displayDigitWidth*numDigitsShowing;

        int displayX = getX() + (getWidth() - displayDisplayWidth)/2;
        int displayY = getY() + (getHeight() - displayDigitHeight)/2;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());

        g.setColor(ledColor);
        g.fillRect(displayX + COLOR_PAD, displayY + COLOR_PAD,
                displayDisplayWidth - COLOR_PAD*2, displayDigitHeight - COLOR_PAD*2);

        for(int i = 0; i < numDigitsShowing; i++) {

            g.drawImage(displayDigits[i],
                    displayX + i * displayDigitWidth,
                    displayY,  displayDigitWidth, displayDigitHeight);
        }
    }

    public void setHeading(String heading) {

        int size = heading.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(heading.charAt(i)));
        }

        int difference = numDigitsShowing - heading.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setSpeed(String speed) {

        int size = speed.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(speed.charAt(i)));
        }

        int difference = numDigitsShowing - speed.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setFuel(String fuel) {

        int size = fuel.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(fuel.charAt(i)));
        }

        int difference = numDigitsShowing - fuel.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setFireCount(String numBurningFires) {

        int size = numBurningFires.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(numBurningFires.charAt(i)));
        }

        int difference = numDigitsShowing - numBurningFires.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setFireSize(String totalBurningFireSize) {

        int size = totalBurningFireSize.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(totalBurningFireSize.charAt(i)));
        }

        int difference = numDigitsShowing - totalBurningFireSize.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setBuildPercent(String buildingPerc) {

        int size = buildingPerc.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(buildingPerc.charAt(i)));
        }

        int difference = numDigitsShowing - buildingPerc.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }

    public void setBuildingVal(String buildingVal) {

        int size = buildingVal.length();
        int[] arr = new int[size];
        int j = 0;

        for (int i = 0; i < size; i++) {

            arr[i] = Integer.parseInt(String.valueOf(buildingVal.charAt(i)));
        }

        int difference = numDigitsShowing - buildingVal.length();

        for (int i = 0; i < difference; i++) {

            displayDigits[i] = digitImages[0];
        }
        for (int i = difference ; i < numDigitsShowing; i++) {

            displayDigits[i] = digitImages[arr[j]];
            j++;
        }
    }
}