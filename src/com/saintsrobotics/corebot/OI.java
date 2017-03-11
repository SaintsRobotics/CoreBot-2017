package com.saintsrobotics.corebot;

import edu.wpi.first.wpilibj.Joystick;

import java.util.ArrayList;
import java.util.List;

public class OI {
    
    private List<Input> inputs = new ArrayList<>();
    
    public final XboxInput drive;
    
    public OI() {
        drive = new XboxInput(0);
        inputs.add(drive);
    }
    
    public void init() {
        inputs.forEach(Input::init);
    }

    public static abstract class Input {
    
        private final int pin;
        Joystick joystick;
    
        private Input(int pin) {
            this.pin = pin;
        }
        
        private void init() {
            joystick = new Joystick(pin);
        }
    }
    
    public static class XboxInput extends Input {
        
        private XboxInput(int pin) {
            super(pin);
        }
    
        public boolean A() { return joystick.getRawButton(1); }
        public boolean B() { return joystick.getRawButton(2); }
        public boolean X() { return joystick.getRawButton(3); }
        public boolean Y() { return joystick.getRawButton(4); }
        public boolean LB() { return joystick.getRawButton(5); }
        public boolean RB() { return joystick.getRawButton(6); }
        public boolean SELECT() { return joystick.getRawButton(7); }
        public boolean START() { return joystick.getRawButton(8); }
        public boolean L3() { return joystick.getRawButton(9); }
        public boolean R3() { return joystick.getRawButton(10); }
        public boolean DPAD_UP() { return joystick.getPOV(0) == 0; }
        public boolean DPAD_RIGHT() { return joystick.getPOV(0) == 90; }
        public boolean DPAD_DOWN() { return joystick.getPOV(0) == 180; }
        public boolean DPAD_LEFT() { return joystick.getPOV(0) == 270; }
    
        public double leftStickX() { return deadzone(joystick.getRawAxis(0)); }
        public double leftStickY() { return deadzone(joystick.getRawAxis(1)); }
        public double leftTrigger() { return deadzone(joystick.getRawAxis(2)); }
        public double rightTrigger() { return deadzone(joystick.getRawAxis(3), 0.05); }
        public double rightStickX() { return deadzone(joystick.getRawAxis(4), 0.05); }
        public double rightStickY() { return deadzone(joystick.getRawAxis(5)); }
    
        private double deadzone(double rawAxis) {
            return deadzone(rawAxis, 0.10);
        }
    
        private double deadzone(double rawAxis, double deadzone) {
            if (Math.abs(rawAxis) < deadzone) return 0;
            else return rawAxis;
        }
    }
}
