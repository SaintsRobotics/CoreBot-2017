package com.saintsrobotics.corebot.output;

public class CompetitionBotMotors extends Motors {
    
    public CompetitionBotMotors() {
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
        super(2, 3, 4, 5, 0, 1, 6, 7,
                false,
                true,
                false,
                false);
    }
}

