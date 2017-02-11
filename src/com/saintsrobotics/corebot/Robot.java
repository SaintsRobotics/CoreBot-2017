package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.RunEachFrameTask;
import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.CompetitionBotMotors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.output.Servos;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.tasks.autonomous.DriveStraightAutonTask;
import com.saintsrobotics.corebot.tasks.autonomous.TurnToFaceVisionTargetTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;
import com.saintsrobotics.corebot.tasks.teleop.GearDropperTask;
import com.saintsrobotics.corebot.tasks.teleop.LifterTask;
import com.saintsrobotics.corebot.tasks.test.TestMotorsTask;
import com.saintsrobotics.corebot.tasks.test.TestShifterTask;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

    public static double MOTOR_RAMPING = 0.05;

    public static double GEAR_DROPPER_OUT = 80;
    public static double GEAR_DROPPER_IN = 125;

    public static double RIGHT_SHIFTER_OUT = 78;
    public static double RIGHT_SHIFTER_IN = 108;

    public static double LEFT_SHIFTER_OUT = 76;
    public static double LEFT_SHIFTER_IN = 56;

    private SendableChooser<Task> taskChooser = new SendableChooser<>();
    public static NetworkTable visionTable;
    public static Preferences prefs;

    public static Sensors sensors = new PracticeSensors();
    public static Motors motors = new CompetitionBotMotors();
    public static Servos servos = new Servos(9, 8, 7);
    public static OI oi = new OI();

    private UsbCamera camera;
    public static int cameraWidth;
    public static int cameraHeight;

    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;

    public static Encoder gearDropEncoder;
    public static int GEAR_DROP_MAX = 0;


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
        taskChooser.addDefault("DriveStraightTask", new DriveStraightAutonTask());
        taskChooser.addObject("TestMotorsTask", new TestMotorsTask());
        taskChooser.addObject("TurnToFaceVisionTargetTask", new TurnToFaceVisionTargetTask());
        taskChooser.addObject("TestShifterTask", new TestShifterTask());
        SmartDashboard.putData("Autonomous", taskChooser);

        gearDropEncoder =  new Encoder(8,9);
    }


    @Override
    public void teleopInit() {
        GEAR_DROP_MAX = prefs.getInt("GEAR_DROP_MAX", 0);
        gearDropEncoder.reset();
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                new LifterTask(),
//                new ShifterTask(),
                new GearDropperTask(),
                new RunEachFrameTask() {
                    @Override
                    protected void runEachFrame() {
                        if (oi.drive.buttons.Y()) {
                            gearDropEncoder.reset();
                        }
                        if (oi.drive.axes.rightTrigger() != 0 || oi.drive.axes.leftTrigger() != 0) {
                            motors.gearDrop.set(-oi.drive.axes.rightTrigger() / 2 + oi.drive.axes.leftTrigger() / 2);
                        }
                        SmartDashboard.putNumber("Encoder", gearDropEncoder.get());
                    }
                },
        new UpdateMotors()
        );
    }

    @Override
    public void autonomousInit() {
        autonomousRunner = new TaskRunner(
                taskChooser.getSelected()
//                new UpdateMotors()
        );
    }

    @Override
    public void testInit() {
        testRunner = new TaskRunner(
                //new TestShifterTask(),
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
