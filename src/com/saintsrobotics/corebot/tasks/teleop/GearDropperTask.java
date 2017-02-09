package com.saintsrobotics.corebot.tasks.teleop;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;

public class GearDropperTask extends RunEachFrameTask {

    private int expected = 0;

    @Override
    protected void runEachFrame() {
        int actual = Robot.gearDropEncoder.get();

        if (Robot.oi.drive.buttons.RB()) {
            expected = 0;
        } else if (Robot.oi.drive.buttons.LB()) {
            expected = Robot.GEAR_DROP_MAX;
        }

        int distance = Math.abs(expected - actual);

        if (distance < 5) {
            Robot.motors.gearDrop.set(0);
        } else if (expected > actual) {
            if (distance < 25) {
                Robot.motors.gearDrop.set(0.2);
            } else {
                Robot.motors.gearDrop.set(0.7);
            }
        } else if (expected < actual) {
            if (distance < 25) {
                Robot.motors.gearDrop.set(-0.2);
            } else {
                Robot.motors.gearDrop.set(-0.7);
            }
        } else {
            Robot.motors.gearDrop.set(0);
        }
    }
}
