package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.PracticeMotors;
import com.saintsrobotics.corebot.tasks.autonomous.DriveStraightAutonTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
    
    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new PracticeMotors();
    public static OI oi = new OI();
    
    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;
    
    @Override
    public void robotInit() {
        sensors.init();
        motors.init();
        oi.init();
    }
    
    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                new UpdateMotors()
        );
    }
    
    @Override
    public void autonomousInit() {
        autonomousRunner = new TaskRunner(
                new DriveStraightAutonTask(),
                new UpdateMotors()
        );
    }
    
    @Override
    public void testInit() {
        testRunner = new TaskRunner(
                new UpdateMotors()
        );
    }
    
    @Override
    public void teleopPeriodic() {
        teleopRunner.run();
    }
    
    @Override
    public void autonomousPeriodic() {
        autonomousRunner.run();
    }
    
    @Override
    public void testPeriodic() {
        testRunner.run();
    }
    
    @Override
    public void disabledInit() {
        if (teleopRunner != null) {
            teleopRunner.disable();
        }
        if (autonomousRunner != null) {
            autonomousRunner.disable();
        }
        if (testRunner != null) {
            testRunner.disable();
        }
        motors.stopAll();
    }
    
    @Override
    public void disabledPeriodic() {
        
    }
}
