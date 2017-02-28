package com.saintsrobotics.corebot.output;

public class PracticeBotWithCompetitionGearDropMotors extends Motors {
    
    @Override public double getGearDropOut() { return 135.7; } // 136.1
    @Override public double getGearDropIn() { return 161.4; } // 159.8
    
    public PracticeBotWithCompetitionGearDropMotors() {
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
        super(2, 3, 4, 5, 0, 1, 7,6,
                false,
                true,
                false,
                false);
    }
}

