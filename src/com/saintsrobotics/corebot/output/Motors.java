package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Constants;
import edu.wpi.first.wpilibj.Talon;

import java.util.ArrayList;
import java.util.List;

public abstract class Motors {
    
    public List<Motor> motorList = new ArrayList<>();
    
    public final Motor leftDrive1;
    public final Motor leftDrive2;
    public final Motor leftDrive3;
    public final Motor rightDrive1;
    public final Motor rightDrive2;
    public final Motor rightDrive3;
    
    protected Motors(int leftDrivePin1, int leftDrivePin2, int leftDrivePin3,
    				 int rightDrivePin1, int rightDrivePin2, int rightDrivePin3) {
        leftDrive1 = new Motor(leftDrivePin1);
        leftDrive2 = new Motor(leftDrivePin2);
        leftDrive3 = new Motor(leftDrivePin3);
        rightDrive1 = new Motor(rightDrivePin1);
        rightDrive2 = new Motor(rightDrivePin2);
        rightDrive3 = new Motor(rightDrivePin3);
        motorList.add(leftDrive1);
        motorList.add(leftDrive2);
        motorList.add(leftDrive3);
        motorList.add(rightDrive1);
        motorList.add(rightDrive2);
        motorList.add(rightDrive3);
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
        
        public void set(double speed) {
            setpoint = speed;
        }
    
        void stop() {
            //speedController.stopMotor();
            setpoint = 0;
            current = 0;
        }
    
        void update() {
            if (Math.abs(setpoint - current) < Constants.MOTOR_RAMPING) {
                current = setpoint;
            } else if (setpoint > current) {
                current += Constants.MOTOR_RAMPING;
            } else if (setpoint < current) {
                current -= Constants.MOTOR_RAMPING;
            }
            speedController.set(current);
        }
    }
}
