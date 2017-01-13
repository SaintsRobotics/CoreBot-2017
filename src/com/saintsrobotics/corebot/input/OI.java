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
        
        public final XboxAxis axis;
        
        private XboxInput(int pin) {
            super(pin);
            axis = new XboxAxis();
        }
    
        @Override
        void initJoystick() {
            axis.setJoystick(joystick);
        }
        
        public static class XboxAxis {
        
            private Joystick joystick;
        
            void setJoystick(Joystick joystick) {
                this.joystick = joystick;
            }
        
            public double leftStickX() {
                return joystick.getRawAxis(0);
            }
        
            public double leftStickY() {
                return joystick.getRawAxis(1);
            }
        
            public double leftTrigger() {
                return joystick.getRawAxis(2);
            }
        
            public double rightTrigger() {
                return joystick.getRawAxis(3);
            }
        
            public double rightStickX() {
                return joystick.getRawAxis(4);
            }
        
            public double rightStickY() {
                return joystick.getRawAxis(5);
            }
        }
    }
}
