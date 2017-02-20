package com.saintsrobotics.corebot.output;

import edu.wpi.first.wpilibj.Servo;

import java.util.ArrayList;
import java.util.List;

public abstract class Servos {
    
    public abstract int getRightShifterOut();
    public abstract int getRightShifterIn();
    
    public abstract int getLeftShifterOut();
    public abstract int getLeftShifterIn();

    private List<ServoWrapper> servos = new ArrayList<>();

    public ServoWrapper rightShifter;
    public ServoWrapper leftShifter;

    public Servos(int rightShifterPin, int leftShifterPin) {
        rightShifter = new ServoWrapper(rightShifterPin);
        leftShifter = new ServoWrapper(leftShifterPin);
        servos.add(rightShifter);
        servos.add(leftShifter);
    }

    public void init() {
        servos.forEach(ServoWrapper::init);
    }

    public static class ServoWrapper {

        private final int pin;
        private Servo servo;

        ServoWrapper(int pin) {
            this.pin = pin;
        }

        void init() {
            if (pin != -1) {
                servo = new Servo(pin);
            }
        }

        public void setAngle(double degrees) {
            if (pin != -1) {
                servo.setAngle(degrees);
            }
        }
    }
}
