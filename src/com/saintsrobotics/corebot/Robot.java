package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.CompetitionBotMotors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.PracticeBotMotors;
import com.saintsrobotics.corebot.output.Servos;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.tasks.autonomous.CenterTargetAutonTask;
import com.saintsrobotics.corebot.tasks.autonomous.DriveStraightAutonTask;
import com.saintsrobotics.corebot.tasks.autonomous.LeftTargetAutonTask;
import com.saintsrobotics.corebot.tasks.autonomous.RightTargetAutonTask;
import com.saintsrobotics.corebot.tasks.autonomous.TurnToFaceVisionTargetTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;
import com.saintsrobotics.corebot.tasks.teleop.GearDropTask;
import com.saintsrobotics.corebot.tasks.teleop.LifterTask;
import com.saintsrobotics.corebot.tasks.teleop.ShifterTask;
import com.saintsrobotics.corebot.tasks.test.TestGearDropTask;
import com.saintsrobotics.corebot.tasks.test.TestMotorsTask;
import com.saintsrobotics.corebot.tasks.test.TestShifterTask;

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

    public static double MOTOR_RAMPING = 0.05;

    public static double GEAR_DROPPER_OUT = 80;
    public static double GEAR_DROPPER_IN = 125;

    public static double RIGHT_SHIFTER_OUT = 78;
    public static double RIGHT_SHIFTER_IN = 116;

    public static double LEFT_SHIFTER_OUT = 76;
    public static double LEFT_SHIFTER_IN = 56;

    private SendableChooser<Supplier<Task>> taskChooser = new SendableChooser<>();
    public static NetworkTable visionTable;
    public static Preferences prefs;

    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new PracticeBotMotors();
    public static Servos servos = new Servos(9, 8);
    public static OI oi = new OI();

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
        servos.init();
        oi.init();
        taskChooser.addObject("DriveStraightTask", DriveStraightAutonTask::new);
        taskChooser.addObject("TestMotorsTask", TestMotorsTask::new);
        taskChooser.addDefault("RightTargetAutonTask", RightTargetAutonTask::new);
        taskChooser.addObject("CenterTargetAutonTask", CenterTargetAutonTask::new);
        taskChooser.addObject("LeftTargetAutonTask", LeftTargetAutonTask::new);
        taskChooser.addObject("TestShifterTask", TestShifterTask::new);
        taskChooser.addObject("TestGearDropTask", TestGearDropTask::new);
        SmartDashboard.putData("Autonomous", taskChooser);
    }


    @Override
    public void teleopInit() {
        GEAR_DROPPER_OUT = prefs.getInt("GEAR_DROPPER_OUT", 0);
        GEAR_DROPPER_IN = prefs.getInt("GEAR_DROPPER_IN", 0);
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                //new LifterTask(),
                //new ShifterTask(),
                //new GearDropTask(),
                new RunEachFrameTask() {
                    @Override
                    protected void runEachFrame() {
                        SmartDashboard.putNumber("Ultrasound", Robot.sensors.ultrasound.getDistance());
                    }
                },
                new UpdateMotors()
        );
    }

    @Override
    public void autonomousInit() {
    	
        autonomousRunner = new TaskRunner(
                taskChooser.getSelected().get(),
                new UpdateMotors()
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
