package com.saintsrobotics.corebot.output;

import edu.wpi.first.wpilibj.Servo;

import java.util.ArrayList;
import java.util.List;

public class Servos {

    private List<ServoWrapper> servos = new ArrayList<>();

    public ServoWrapper rightShifter;
    public ServoWrapper leftShifter;
    public ServoWrapper gearDropArm;

    public Servos(int rightShifterPin, int leftShifterPin, int gearDropArmPin) {
        rightShifter = new ServoWrapper(rightShifterPin);
        leftShifter = new ServoWrapper(leftShifterPin);
        gearDropArm = new ServoWrapper(gearDropArmPin);
        servos.add(rightShifter);
        servos.add(leftShifter);
        servos.add(gearDropArm);
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
            servo = new Servo(pin);
        }

        public void setAngle(double degrees) {
            servo.setAngle(degrees);
        }
    }

}
