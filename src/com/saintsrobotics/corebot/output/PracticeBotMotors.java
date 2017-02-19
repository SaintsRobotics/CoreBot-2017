package com.saintsrobotics.corebot.output;

public class PracticeBotMotors extends Motors {

    public PracticeBotMotors() {
        /*
        int leftDrivePin1, int leftDrivePin2,
        int rightDrivePin1, int rightDrivePin2,
        int liftPin1, int liftPin2,
        int gearDropPin1, int gearDropPin2,
        boolean leftDriveInverted,
        boolean rightDriveInverted,
        boolean lifterInverted,
        boolean gearDropInverted
        */
        super(6, 3, 7, 5, 0, 1, 2, 4,
                false,
                true,
                false,
                false);
    }
}

