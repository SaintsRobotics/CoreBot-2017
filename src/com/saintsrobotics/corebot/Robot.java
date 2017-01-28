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
import com.saintsrobotics.corebot.tasks.autonomous.TurnToFaceVisionTargetTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;
import com.saintsrobotics.corebot.tasks.teleop.LifterTask;
import com.saintsrobotics.corebot.tasks.teleop.ShifterTask;
import com.saintsrobotics.corebot.tasks.test.TestMotorsTask;
import com.saintsrobotics.corebot.tasks.test.ToggleForwardDriveTask;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    public static double MOTOR_RAMPING = 0.05;

    public static double RIGHT_SHIFTER_OUT = 78;
    public static double RIGHT_SHIFTER_IN = 110;

    public static double LEFT_SHIFTER_OUT = 73;
    public static double LEFT_SHIFTER_IN = 53;

    private SendableChooser<Task> taskChooser = new SendableChooser<>();
    public static NetworkTable visionTable;
    public static Preferences prefs;

    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new PracticeMotors();
    public static OI oi = new OI();

    public static Servo leftShifter;
    public static Servo rightShifter;

    private UsbCamera camera;
    public static int cameraWidth;
    public static int cameraHeight;

    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;

    @Override
    public void robotInit() {
        prefs = Preferences.getInstance();
        cameraWidth = prefs.getInt("width", 320);
        cameraHeight = prefs.getInt("height", 240);
        new Thread(() -> {
            camera = CameraServer.getInstance().startAutomaticCapture();
            camera.setResolution(cameraWidth, cameraHeight);
            camera.setBrightness(prefs.getInt("camera_brightness", 0));
        }).start();
        visionTable = NetworkTable.getTable("/GRIP/myContoursReport");
        sensors.init();
        motors.init();
        oi.init();
        taskChooser.addDefault("DriveStraightTask", new DriveStraightAutonTask());
        taskChooser.addObject("TestMotorsTask", new TestMotorsTask());
        taskChooser.addObject("TurnToFaceVisionTargetTask", new TurnToFaceVisionTargetTask());
        SmartDashboard.putData("Autonomous", taskChooser);
        leftShifter = new Servo(8);
        rightShifter = new Servo(9);
    }


    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                new LifterTask(),
                new ShifterTask(),
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
