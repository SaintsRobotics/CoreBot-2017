package com.saintsrobotics.corebot.output;

public class PracticeBotMotors extends Motors {

    public PracticeBotMotors() {
        // leftDrivePin, rightDrivePin
        super(2, 3, 0, 1, 4, 5, 6, 7,
                false,
                false,
                false,
                false);
    }
}

