package com.saintsrobotics.corebot.input;

import edu.wpi.first.wpilibj.Joystick;

import java.util.ArrayList;
import java.util.List;

public class OI {
    
    private List<Input> inputs = new ArrayList<>();
    
    public final XboxInput drive;
    public final XboxInput operator;
    
    public OI() {
        drive = new XboxInput(0);
        operator = new XboxInput(1);
        inputs.add(drive);
        inputs.add(operator);
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
            initJoystick();
        }
        
        abstract void initJoystick();
    }
    
    public static class XboxInput extends Input {
        
        public final XboxAxis axes;
        public final XboxButtons buttons;
        
        private XboxInput(int pin) {
            super(pin);
            axes = new XboxAxis();
            buttons = new XboxButtons();
        }
    
        @Override
        void initJoystick() {
            axes.setJoystick(joystick);
            buttons.setJoystick(joystick);
        }
        
        public static class XboxAxis {
        
            private Joystick joystick;
        
            void setJoystick(Joystick joystick) {
                this.joystick = joystick;
            }
        
            public double leftStickX() { return ramp(joystick.getRawAxis(0)); }
            public double leftStickY() { return ramp(joystick.getRawAxis(1)); }
            public double leftTrigger() { return ramp(joystick.getRawAxis(2)); }
            public double rightTrigger() { return ramp(joystick.getRawAxis(3)); }
            public double rightStickX() { return ramp(joystick.getRawAxis(4)); }
            public double rightStickY() { return ramp(joystick.getRawAxis(5)); }

            private double ramp(double rawAxis) {
                if (Math.abs(rawAxis) < 0.15) return 0;
                else return rawAxis;
            }
        }
        
        public static class XboxButtons {
    
            private Joystick joystick;
    
            void setJoystick(Joystick joystick) {
                this.joystick = joystick;
            }
            
            public boolean A() { return joystick.getRawButton(1); }
            public boolean B() { return joystick.getRawButton(2); }
            public boolean X() { return joystick.getRawButton(3); }
            public boolean Y() { return joystick.getRawButton(4); }
            public boolean LB() { return joystick.getRawButton(5); }
            public boolean RB() { return joystick.getRawButton(6); }
            public boolean SELECT() { return joystick.getRawButton(7); }
            public boolean START() { return joystick.getRawButton(8); }
            public boolean L3() { return joystick.getRawButton(8); }
            public boolean R3() { return joystick.getRawButton(9); }
        }
    }
}
