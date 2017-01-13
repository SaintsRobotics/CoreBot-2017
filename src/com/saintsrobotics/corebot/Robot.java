package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.PracticeMotors;
import com.saintsrobotics.corebot.tasks.DriveTask;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
    
    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new PracticeMotors();
    public static OI oi = new OI();
    
    private TaskRunner teleopRunner;
    
    @Override
    public void robotInit() {
        sensors.init();
        motors.init();
        oi.init();
    }
    
    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(new Task[] {
                new DriveTask(),
                new UpdateMotors()
        });
    }
    
    @Override
    public void teleopPeriodic() {
        teleopRunner.run();
    }
    
    @Override
    public void autonomousInit() {
        
    }
    
    @Override
    public void autonomousPeriodic() {
        
    }
    
    @Override
    public void testInit() {
        
    }
    
    @Override
    public void testPeriodic() {
        
    }
    
    @Override
    public void disabledInit() {
        if (teleopRunner != null) {
            teleopRunner.disable();
        }
        motors.stopAll();
    }
    
    @Override
    public void disabledPeriodic() {
        
    }
}
