package com.saintsrobotics.corebot.tasks.auton;

import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.Task;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class BaseAutonTask extends Task {
    
    protected final double lineUpSpeedSides = Robot.prefs.getDouble("vision_line_up_speed", 0);
    protected final double lineUpSpeedCenter = Robot.prefs.getDouble("vision_line_up_speed_straight", 0);
    
    protected final double visionStopDistance = Robot.prefs.getDouble("vision_stop_distance", 10);
    protected final double visionTolerance = Robot.prefs.getDouble("vision_tolerance", 0);
    protected final double visionTurnPower = Robot.prefs.getDouble("vision_motor_power", 0);
    protected final double visionCoastSpeed = Robot.prefs.getDouble("vision_coast_speed", 0);
    protected final double visionCoastTime = Robot.prefs.getDouble("vision_coast_time", 0);
    protected final double visionKickTime = Robot.prefs.getDouble("vision_idle_time", 0);
    
    protected final double deadReckoningForwardSpeed = Robot.prefs.getDouble("dead_reckoning_forward_speed", 0);
    protected final double deadReckoningForwardTime = Robot.prefs.getDouble("dead_reckoning_forward_time", 0);
    protected final double deadReckoningTurnSpeed = Robot.prefs.getDouble("dead_reckoning_turn_speed", 0);
    protected final double deadReckoningTurnTime = Robot.prefs.getDouble("dead_reckoning_turn_time", 0);
    
    protected final double deadReckoningRightTurnSpeed = Robot.prefs.getDouble("dead_reckoning_right_turn_speed", 0);
    protected final double deadReckoningRightTurnTime = Robot.prefs.getDouble("dead_reckoning_right_turn_time", 0);
    protected final double deadReckoningRightSideMoveSpeed = Robot.prefs.getDouble("dead_reckoning_right_side_move_speed", 0);
    protected final double deadReckoningSafetyDistance = Robot.prefs.getDouble("dead_reckoning_safety_distance", 40);
    protected final double deadReckoningRightSideMoveTime = Robot.prefs.getDouble("dead_reckoning_right_side_move_time", 0);
    
    protected final double visionBrightness = Robot.prefs.getDouble("vision_brightness", 0.8);
    
    @Override
    protected final void runTask() {
        Robot.log("Setting LED to " + visionBrightness);
        Robot.motors.ledController.set(visionBrightness);
        wait.forSeconds(0.5);
        
        runVisionTask();
        
        Robot.log("Done, stopping LED");
        Robot.motors.ledController.set(0);
    }
    
    protected abstract void runVisionTask();
    
    protected void stop(double time) {
        Robot.log("Stopping for " + time + "s");
        Robot.motors.allDrive.set(0.01);
        wait.forSeconds(time);
    }
    
    private void turnInternal(double speed, double time, boolean turningRight) {
        double otherSide = -0.05;
        if (turningRight) {
            Robot.motors.rightDrive.set(otherSide);
            Robot.motors.leftDrive.set(speed);
        } else {
            Robot.motors.leftDrive.set(otherSide);
            Robot.motors.rightDrive.set(speed);
        }
        wait.forSeconds(time);
    }
    
    private void rotateInternal(double speed, double time, boolean rotatingRight) {
        if (rotatingRight) {
            Robot.motors.rightDrive.set(-speed);
            Robot.motors.leftDrive.set(speed);
        } else {
            Robot.motors.leftDrive.set(-speed);
            Robot.motors.rightDrive.set(speed);
        }
        wait.forSeconds(time);
    }
    
    protected void rotateRight(double speed, double time) {
        Robot.log("Rotating right at " + speed + " for " + time + "s");
        rotateInternal(speed, time, true);
    }
    
    protected void turnRight(double speed, double time) {
        Robot.log("Turning right at " + speed + " for " + time + "s");
        turnInternal(speed, time, true);
    }
    
    protected void rotateLeft(double speed, double time) {
        Robot.log("Rotating left at " + speed + " for " + time + "s");
        rotateInternal(speed, time, false);
    }
    
    protected void turnLeft(double speed, double time) {
        Robot.log("Turning left at " + speed + " for " + time + "s");
        turnInternal(speed, time, false);
    }
    
    protected void driveForward(double power, double time) {
        Robot.log("Driving forward at " + power + " for " + time + "s");
        Robot.motors.allDrive.set(power);
        wait.forSeconds(time);
    }
    
    protected void driveIntoLiftAndKickAndBackOff(double forwardSpeed, boolean actuallyKick) {
        lineUpWithLift(forwardSpeed);
        dashForwardOntoLift();
        if (actuallyKick) {
            kickGear(visionKickTime);
            backUp();
        } else {
            Robot.log("Pretending to kick gear for " + visionKickTime + "s");
            wait.forSeconds(visionKickTime);
        }
        Robot.flags.wantKick = false;
    }
    
    /**
     * Drives forward, using vision, until the robot is right in front of and facing the lift.
     */
    @SuppressWarnings("Duplicates")
    protected void lineUpWithLift(final double baseForwardSpeed) {
        long startTime = System.currentTimeMillis();
        Robot.log("Lining up with lift, wish me luck! " + startTime);
        
        while (Robot.sensors.ultrasound.getDistance() > visionStopDistance) {
            double forwardSpeed = baseForwardSpeed;
            double turnAmount;
            
            VisionTargets targets = getLowestVisionTargets(
                    Robot.visionTable.getNumberArray("centerX", new double[0]),
                    Robot.visionTable.getNumberArray("centerY", new double[0]));
            
            if (targets == null) {
//                DriverStation.reportError("Less than two vision targets found this frame", false);
                wait.forFrame();
                continue;
            }
            
            double liftPosition = targets.getLiftPosition();
        
            SmartDashboard.putNumber("Vision Target Position", liftPosition);
            if (liftPosition > 1 || liftPosition < -1) {
                DriverStation.reportError("UNEXPECTED VISION TARGET LOCATION: " + liftPosition, false);
            }
        
            if (Math.abs(liftPosition) > visionTolerance) {
                turnAmount = liftPosition * visionTurnPower;
            } else {
                turnAmount = 0;
            }
            
            if (System.currentTimeMillis() - startTime < 1000) {
                forwardSpeed = 0;
                turnAmount *= 0.75;
            }
            
            SmartDashboard.putNumber("Vision Turn Speed", turnAmount);
            SmartDashboard.putNumber("Vision Forward Speed", forwardSpeed);
            
            Robot.motors.rightDrive.set(forwardSpeed - turnAmount);
            Robot.motors.leftDrive.set(forwardSpeed + turnAmount);
            wait.forFrame();
        }
        Robot.motors.allDrive.set(0);
    }
    
    /**
     * Dashes forward onto the lift and stop.
     */
    protected void dashForwardOntoLift() {
        Robot.log("Dashing onto lift...");
        driveForward(visionCoastSpeed, visionCoastTime);
        stop(0);
    }
    
    /**
     * Kicks the gear out.
     */
    protected void kickGear(double time) {
        Robot.log("Kicking gear for " + time + "s");
        Robot.flags.wantKick = true;
        wait.forSeconds(time);
    }
    
    protected void backUp() {
        Robot.log("Backing up...");
        driveForward(-visionCoastSpeed, visionCoastTime);
        stop(0);
    }
    
    protected void waitUntilObstacleSpottedOrTimeout(double timeout) {
        Robot.log("Waiting until obstacle or " + timeout + "s");
        wait.untilWithTimeout(
                () -> {
                    if (Robot.sensors.ultrasound.getDistance() < deadReckoningSafetyDistance) {
                        DriverStation.reportError("Obstacle spotted, stopping! Distance: " + Robot.sensors.ultrasound.getDistance(), false);
                        return true;
                    }
                    return false;
                }, timeout);
    }
    
    /**
     * @return The forward speed to drive at when going forward into the lift
     */
    private double getForwardSpeed() {
        if (Robot.sensors.ultrasound.getDistance() < Robot.prefs.getDouble("vision_slow_distance", 40)) {
            double min = Robot.prefs.getDouble("vision_forward_speed_min", 0);
            double max = Robot.prefs.getDouble("vision_forward_speed_max", 0);
            double difference = max - min;
            return min + difference * Robot.sensors.ultrasound.getDistance() / Robot.prefs.getDouble("vision_slow_distance", 40);
        } else {
            return Robot.prefs.getDouble("vision_coast_speed", 0);
        }
    }
    
    /**
     * @return The two lowest vision targets found, or {@code null} if less than two are found.
     */
    private VisionTargets getLowestVisionTargets(double[] centerXArr, double[] centerYArr) {
        // if we have more than two data points and the two lists have the same length (should always be true)
        if (centerYArr.length != centerXArr.length || centerYArr.length < 2) {
            return null;
        }
        
        int lowestIndex = -1;
        int secondLowestIndex = -1;
        
        double lowestPosition = Integer.MIN_VALUE;
        double secondLowestPosition = Integer.MIN_VALUE;
        
        for (int i = 0; i < centerYArr.length; i++) {
            double yPos = centerYArr[i];
            
            
            // if this y position is less than the lowest on record
            //  * set the second lowest to the (old) lowest
            //  * set the lowest to the current
            if (yPos > lowestPosition) {
                secondLowestIndex = lowestIndex;
                lowestIndex = i;
                
                secondLowestPosition = lowestPosition;
                lowestPosition = yPos;
                // else if this y position is less than the second lowest on record
                //  * set the second lowest to the current
            } else if (yPos > secondLowestPosition) {
                secondLowestIndex = i;
                secondLowestPosition = yPos;
            }
        }
        double lowestTargetX = centerXArr[lowestIndex];
        double lowestTargetY = centerYArr[lowestIndex];
        double secondLowestTargetX = centerXArr[secondLowestIndex];
        double secondLowestTargetY = centerYArr[secondLowestIndex];
        
        return new VisionTargets(
                new VisionTargetPosition(lowestTargetX, lowestTargetY),
                new VisionTargetPosition(secondLowestTargetX, secondLowestTargetY));
    }
    
    private class VisionTargetPosition {
        final double x;
        final double y;
        
        private VisionTargetPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    private class VisionTargets {
        final VisionTargetPosition targetPos1;
        final VisionTargetPosition targetPos2;
        
        private VisionTargets(VisionTargetPosition targetPos1, VisionTargetPosition targetPos2) {
            this.targetPos1 = targetPos1;
            this.targetPos2 = targetPos2;
        }
        
        /**
         * @return The position of the lift, from -1 to 1 based on the location in the camera FOV.
         */
        @SuppressWarnings("UnnecessaryLocalVariable")
        double getLiftPosition() {
            double rawLiftPositionX = (targetPos1.x + targetPos2.x) / 2;
            double normalizedLiftPositionX = rawLiftPositionX / Robot.cameraWidth;
            double liftPosition = 2 * (normalizedLiftPositionX - 0.5);
            return liftPosition;
        }
    }
}
