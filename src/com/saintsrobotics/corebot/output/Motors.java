package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;

public abstract class Motors {
    
    private List<Motor> motorList = new ArrayList<>();
    
    public final MotorGroup leftDrive1;
    public final MotorGroup leftDrive2;
    public final MotorGroup rightDrive1;
    public final MotorGroup rightDrive2;
    public final MotorGroup lift1;
    public final MotorGroup lift2;
    public final MotorGroup leftMotors;
    public final MotorGroup rightMotors;
    public final MotorGroup liftMotors;
    public final MotorGroup allMotors;
    
    protected Motors(int leftDrivePin1, int leftDrivePin2,
                     int rightDrivePin1, int rightDrivePin2,
                     int lifterPin1, int lifterPin2) {
        Motor motorLeftDrive1 = new Motor(leftDrivePin1);
        Motor motorLeftDrive2 = new Motor(leftDrivePin2);
        Motor motorRightDrive1 = new Motor(rightDrivePin1);
        Motor motorRightDrive2 = new Motor(rightDrivePin2);
        Motor motorLift1 = new Motor(lifterPin1);
        Motor motorLift2 = new Motor(lifterPin2);
        motorList.add(motorLeftDrive1);
        motorList.add(motorLeftDrive2);
        motorList.add(motorRightDrive1);
        motorList.add(motorRightDrive2);
        motorList.add(motorLift1);
        motorList.add(motorLift2);
        
        leftDrive1 = new MotorGroup(motorLeftDrive1);
        leftDrive2 = new MotorGroup(motorLeftDrive2);
        rightDrive1 = new MotorGroup(motorRightDrive1);
        rightDrive2 = new MotorGroup(motorRightDrive2);
        lift1 = new MotorGroup(motorLift1);
        lift2 = new MotorGroup(motorLift2);
        
        leftMotors = new MotorGroup(motorLeftDrive1, motorLeftDrive2);
        rightMotors = new MotorGroup(motorRightDrive1, motorRightDrive2);

        liftMotors = new MotorGroup(motorLift1, motorLift2);
        
        allMotors = new MotorGroup(leftMotors, rightMotors);
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
    }
    
    public static class Motor {
        
        private final int pin;
        private Talon speedController;
    
        Motor(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            speedController = new Talon(pin);
        }
        
        private double setpoint = 0;
        private double current = 0;
        
        void set(double speed) {
            setpoint = speed;
        }
    
        void stop() {
            //speedController.stopMotor();
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
