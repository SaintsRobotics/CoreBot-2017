package com.saintsrobotics.corebot.output;

import com.saintsrobotics.corebot.Robot;
import edu.wpi.first.wpilibj.TalonSRX;

public class Motor {
    
    private TalonSRX speedController;

    public Motor(int pin, boolean inverted) {
        this.speedController = new TalonSRX(pin);
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