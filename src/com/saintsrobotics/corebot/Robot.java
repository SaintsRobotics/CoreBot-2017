package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.PracticeMotors;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.tasks.autonomous.DriveStraightAutonTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;
import com.saintsrobotics.corebot.tasks.teleop.LifterTask;
import com.saintsrobotics.corebot.tasks.test.TestMotorsTask;
import com.saintsrobotics.corebot.tasks.test.ToggleForwardDriveTask;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Arrays;

public class Robot extends IterativeRobot {
    
    public static double MOTOR_RAMPING = 0.05;

    private SendableChooser<Task> taskChooser = new SendableChooser<>();
    private NetworkTable visionTable;

    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new PracticeMotors();
    public static OI oi = new OI();

    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;

    private Preferences prefs;

    @Override
    public void robotInit() {
        prefs = Preferences.getInstance();
        new Thread(() -> CameraServer.getInstance().startAutomaticCapture()
                .setResolution(prefs.getInt("width", 320), prefs.getInt("height", 240))).start();
        visionTable = NetworkTable.getTable("/GRIP/myContoursReport");
        sensors.init();
        motors.init();
        oi.init();
        taskChooser.addDefault("DriveStraight", new DriveStraightAutonTask());
        taskChooser.addObject("TestMotors", new TestMotorsTask());
        SmartDashboard.putData("Autonomous", taskChooser);
    }
    
    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                new LifterTask(),
                new UpdateMotors()
        );
    }
    
    @Override
    public void autonomousInit() {
        autonomousRunner = new TaskRunner(
                taskChooser.getSelected(),
                new UpdateMotors()
        );
    }
    
    @Override
    public void testInit() {
        testRunner = new TaskRunner(
                new ToggleForwardDriveTask(),
                new UpdateMotors()
        );
    }

    @Override
    public void teleopPeriodic() {
        teleopRunner.run();
        SmartDashboard.putString("areas", Arrays.toString(visionTable.getNumberArray("area", new double[0])));
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
