package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.TalonSRX;

import java.util.ArrayList;
import java.util.List;

public abstract class Motors {
    
    public abstract double getGearDropOut();
    public abstract double getGearDropIn();
    
    private List<Motor> motorList = new ArrayList<>();
    
    public final MotorGroup leftDrive1;
    public final MotorGroup leftDrive2;
    public final MotorGroup rightDrive1;
    public final MotorGroup rightDrive2;
    public final MotorGroup leftDrive;
    public final MotorGroup rightDrive;
    public final MotorGroup allDrive;
    public final MotorGroup lift1;
    public final MotorGroup lift2;
    public final MotorGroup lift;
    public final MotorGroup gearDrop;
    public final MotorGroup ledController;

    protected Motors(int leftDrivePin1, int leftDrivePin2,
                     int rightDrivePin1, int rightDrivePin2,
                     int liftPin1, int liftPin2,
                     int gearDropPin, int ledControllerPin,
                     boolean leftDriveInverted,
                     boolean rightDriveInverted,
                     boolean lifterInverted,
                     boolean gearDropInverted) {
        Motor motorLeftDrive1 = new Motor(leftDrivePin1, leftDriveInverted);
        Motor motorLeftDrive2 = new Motor(leftDrivePin2, leftDriveInverted);
        Motor motorRightDrive1 = new Motor(rightDrivePin1, rightDriveInverted);
        Motor motorRightDrive2 = new Motor(rightDrivePin2, rightDriveInverted);
        Motor motorLift1 = new Motor(liftPin1, lifterInverted);
        Motor motorLift2 = new Motor(liftPin2, lifterInverted);
        Motor motorGearDrop = new Motor(gearDropPin, gearDropInverted);
        Motor motorLEDController = new Motor(ledControllerPin, false);

        motorList.add(motorLeftDrive1);
        motorList.add(motorLeftDrive2);
        motorList.add(motorRightDrive1);
        motorList.add(motorRightDrive2);
        motorList.add(motorLift1);
        motorList.add(motorLift2);
        motorList.add(motorGearDrop);
        motorList.add(motorLEDController);

        leftDrive1 = new MotorGroup(motorLeftDrive1);
        leftDrive2 = new MotorGroup(motorLeftDrive2);
        rightDrive1 = new MotorGroup(motorRightDrive1);
        rightDrive2 = new MotorGroup(motorRightDrive2);
        lift1 = new MotorGroup(motorLift1);
        lift2 = new MotorGroup(motorLift2);
        gearDrop = new MotorGroup(motorGearDrop);
        ledController = new MotorGroup(motorLEDController);

        leftDrive = new MotorGroup(motorLeftDrive1, motorLeftDrive2);
        rightDrive = new MotorGroup(motorRightDrive1, motorRightDrive2);
        allDrive = new MotorGroup(leftDrive, rightDrive);

        lift = new MotorGroup(motorLift1, motorLift2);
    }
    
    public void init() {
        motorList.forEach(Motor::init);
    }
    
    public void stopAll() {
        motorList.forEach(Motor::stop);
    }
    
    public void update() {
        motorList.forEach(Motor::update);
    }
    
    public static class MotorGroup {
    
        private Motor[] motors;
    
        private MotorGroup(Motor... motors) {
            this.motors = motors;
        }
    
        private MotorGroup(MotorGroup... motorGroups) {
            List<Motor> motorList = new ArrayList<>();
            for (MotorGroup motorGroup : motorGroups) {
                for (Motor motor : motorGroup.motors) {
                    if (!motorList.contains(motor)) {
                        motorList.add(motor);
                    }
                }
            }
            motors = motorList.toArray(new Motor[0]);
        }
        
        public void set(double speed) {
            for (Motor motor : motors) {
                motor.set(speed);
            }
        }
        
        public void turnOnLED() {
            if (motors.length == 1) {
                motors[0].turnOnLED();
            } else {
                DriverStation.reportError("Attempting to enable LED on motor group", true);
            }
        }
    
        public void turnOffLED() {
            if (motors.length == 1) {
                motors[0].turnOffLED();
            } else {
                DriverStation.reportError("Attempting to enable LED on motor group", true);
            }
        }
    }
    public static class Motor {
        
        private final int pin;
        private final boolean inverted;
        private SpeedController speedController;
    
        Motor(int pin, boolean inverted) {
            this.pin = pin;
            this.inverted = inverted;
        }
        
        private void init() {
            speedController = new TalonSRX(pin);
            speedController.setInverted(inverted);
        }
        
        private double setpoint = 0;
        private double current = 0;
        
        void turnOnLED() {
            setpoint = 1;
            current = 1;
        }
        
        void turnOffLED() {
            setpoint = 0;
            current = 0;
        }
        
        void set(double speed) {
            setpoint = speed;
        }
    
        void stop() {
            speedController.stopMotor();
            setpoint = 0;
            current = 0;
        }
    
        void update() {
            if (Math.abs(setpoint - current) < Robot.MOTOR_RAMPING) {
                current = setpoint;
            } else if (setpoint > current) {
                current += Robot.MOTOR_RAMPING;
            } else if (setpoint < current) {
                current -= Robot.MOTOR_RAMPING;
            }
            speedController.set(current);
        }
    }
}
