package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Robot;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;

public class Motor {
    
    private Talon speedController;

    public Motor(int pin, boolean inverted) {
        this.speedController = new Talon(pin);
        speedController.setInverted(inverted);
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
    
    private final double rampRate = Robot.MOTOR_RAMPING;

    void update(double sec) {
        if (Math.abs(setpoint - current) < rampRate * sec) {
            current = setpoint;
        } else if (setpoint > current) {
            current += rampRate * sec;
        } else if (setpoint < current) {
            current -= rampRate * sec;
        }
        speedController.set(current);
    }
}