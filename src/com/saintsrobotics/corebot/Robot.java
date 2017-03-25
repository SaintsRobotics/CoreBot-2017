package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.input.CompetitionSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.CompetitionMotors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.NoopServos;
import com.saintsrobotics.corebot.output.Servos;
import com.saintsrobotics.corebot.tasks.PostSensorsToSmartDashboardTask;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.tasks.auton.CenterTargetDeadReckoningAutonTask;
import com.saintsrobotics.corebot.tasks.auton.CrossBaselineTask;
import com.saintsrobotics.corebot.tasks.auton.LeftTargetVisionAutonTask;
import com.saintsrobotics.corebot.tasks.auton.RightTargetVisionAutonTask;
import com.saintsrobotics.corebot.tasks.teleop.*;
import com.saintsrobotics.corebot.tasks.test.TestLEDTask;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.Supplier;

public class Robot extends IterativeRobot {

    public static double MOTOR_RAMPING = 0.10;

    private SendableChooser<Supplier<Task>> taskChooser = new SendableChooser<>();
    public static NetworkTable visionTable;
    public static Preferences prefs;

    public static Flags flags = new Flags();
    public static Sensors sensors = new CompetitionSensors();
    public static Motors motors = new CompetitionMotors();
    public static Servos servos = new NoopServos();
    public static OI oi = new OI();

    private UsbCamera camera;
    public static int cameraWidth;
    public static int cameraHeight;

    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;
    
    public static void log(String message) {
        DriverStation.reportWarning(message, false);
    }

    @Override
    public void robotInit() {
        prefs = Preferences.getInstance();
//        prefs.getKeys().forEach(key -> {
//            prefs.remove((String) key);
//        });
        oi.init();
        sensors.init();
        motors.init();
        servos.init();
        
        cameraWidth = prefs.getInt("camera_width", 320);
        cameraHeight = prefs.getInt("camera_height", 240);
        int cameraFps = prefs.getInt("camera_fps", 8);
        new Thread(() -> {
            camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(cameraWidth, cameraHeight);
            camera.setBrightness(prefs.getInt("camera_brightness", 0));
            camera.setFPS(cameraFps);
        }).start();
        visionTable = NetworkTable.getTable("/GRIP/myContoursReport");
        
        taskChooser.addDefault("CenterTargetDeadReckoningAutonTask", CenterTargetDeadReckoningAutonTask::new);
        taskChooser.addObject("CrossBaselineAutonTask", CrossBaselineTask::new);
        taskChooser.addObject("LeftTargetVisionAutonTask", LeftTargetVisionAutonTask::new);
        taskChooser.addObject("RightTargetVisionAutonTask", RightTargetVisionAutonTask::new);
        SmartDashboard.putData("Autonomous2", taskChooser);
    }
    
    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(
//                new TestGearDropTask(),
                new ArcadeDriveTask(),
                new BackUpATadTask(),
                new LifterTask(),
                new TestLEDTask(),
                new ShifterTask(),
                new GearDropTask(),
                new UpdateMotors(),
                new PostSensorsToSmartDashboardTask()
        );
    }

    @Override
    public void autonomousInit() {
        autonomousRunner = new TaskRunner(
        		taskChooser.getSelected().get(),
                new GearDropTask(),
                new UpdateMotors(),
                new PostSensorsToSmartDashboardTask()
        );
    }

    @Override
    public void testInit() {
        testRunner = new TaskRunner(
//                new TestShifterTask(),
//                new UpdateMotors()
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
        Robot.flags = new Flags();
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
