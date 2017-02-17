package com.saintsrobotics.corebot.output;

public class PracticeBotMotors extends Motors {

    public PracticeBotMotors() {
        // leftDrivePin, rightDrivePin
        super(2, 3, 4, 5, 0, 1, 6, 7,
                false,
                true,
                false,
                false);
    }
}

