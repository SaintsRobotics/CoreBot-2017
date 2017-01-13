package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;

public abstract class Motors {
    
    private List<Motor> motorList = new ArrayList<>();
    
    public final MotorGroup leftDrive1;
    public final MotorGroup leftDrive2;
    public final MotorGroup leftDrive3;
    public final MotorGroup rightDrive1;
    public final MotorGroup rightDrive2;
    public final MotorGroup rightDrive3;
    public final MotorGroup leftMotors;
    public final MotorGroup rightMotors;
    public final MotorGroup allMotors;
    
    protected Motors(int leftDrivePin1, int leftDrivePin2, int leftDrivePin3,
                     int rightDrivePin1, int rightDrivePin2, int rightDrivePin3) {
        Motor motorLeftDrive1 = new Motor(leftDrivePin1);
        Motor motorLeftDrive2 = new Motor(leftDrivePin2);
        Motor motorLeftDrive3 = new Motor(leftDrivePin3);
        Motor motorRightDrive1 = new Motor(rightDrivePin1);
        Motor motorRightDrive2 = new Motor(rightDrivePin2);
        Motor motorRightDrive3 = new Motor(rightDrivePin3);
        motorList.add(motorLeftDrive1);
        motorList.add(motorLeftDrive2);
        motorList.add(motorLeftDrive3);
        motorList.add(motorRightDrive1);
        motorList.add(motorRightDrive2);
        motorList.add(motorRightDrive3);
        
        leftDrive1 = new MotorGroup(motorLeftDrive1);
        leftDrive2 = new MotorGroup(motorLeftDrive2);
        leftDrive3 = new MotorGroup(motorLeftDrive3);
        rightDrive1 = new MotorGroup(motorRightDrive1);
        rightDrive2 = new MotorGroup(motorRightDrive2);
        rightDrive3 = new MotorGroup(motorRightDrive3);
        
        leftMotors = new MotorGroup(motorLeftDrive1, motorLeftDrive2, motorLeftDrive3);
        rightMotors = new MotorGroup(motorRightDrive1, motorRightDrive2, motorRightDrive3);
        
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
