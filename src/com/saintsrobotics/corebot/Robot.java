package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.coroutine.Task;
import com.saintsrobotics.corebot.coroutine.TaskRunner;
import com.saintsrobotics.corebot.dash.ValueFamily;
import com.saintsrobotics.corebot.dash.WebDashboard;
import com.saintsrobotics.corebot.dash.WebDashboardActual;
import com.saintsrobotics.corebot.dash.WebDashboardDummy;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.input.PracticeSensors;
import com.saintsrobotics.corebot.input.Sensors;
import com.saintsrobotics.corebot.output.Motors;
import com.saintsrobotics.corebot.tasks.UpdateMotors;
import com.saintsrobotics.corebot.tasks.autonomous.DriveStraightAutonTask;
import com.saintsrobotics.corebot.tasks.teleop.ArcadeDriveTask;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;

import org.simpleHTTPServer.SimpleHTTPServer;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Ultrasonic;

import java.io.File;
import java.net.UnknownHostException;

public class Robot extends IterativeRobot {
    
    public static double MOTOR_RAMPING = 0.001;
    
    public static Sensors sensors = new PracticeSensors();
    public static OI oi = new OI();
    public static WebDashboard webDashboard;
    
    private TaskRunner teleopRunner;
    private TaskRunner autonomousRunner;
    private TaskRunner testRunner;
    
    @Override
    public void robotInit() {
        sensors.init();
        oi.init();
        SimpleHTTPServer server;
        try {
        	server = new SimpleHTTPServer(8080, new File("./home/lvuser/html"));
        	server.start();
			webDashboard = new WebDashboardActual();
			((WebDashboardActual) webDashboard).start();
		} catch (UnknownHostException e) {
			webDashboard = new WebDashboardDummy();
			Robot.log("WebDashboard broke, falling back");
		}
    }
    
    @Override
    public void teleopInit() {
        teleopRunner = new TaskRunner(
                new ArcadeDriveTask(),
                new UpdateMotors()
        );
        webDashboard.save();
    }
    
    @Override
    public void autonomousInit() {
        autonomousRunner = new TaskRunner(
                new DriveStraightAutonTask(),
                new UpdateMotors()
        );
        webDashboard.save();
    }
    
	
    @Override
    public void testInit() {
    	ValueFamily outputs = webDashboard.family("outputs");
        testRunner = new TaskRunner(
                new Task(){
					@Override
					protected void run() {
						while(true){
							String val = String.format("%1$,.2f", sensors.gyro.getAngle());
							outputs.change("gyro", val);
							//logSafe("gyro " + val);
							wait.forSeconds(0.5);
						}
					}
                	
                },
                new Task() {
                    @java.lang.Override
                    protected void run() {
                    	ValueFamily inputs = webDashboard.family("inputs");
                        /*DIGITAL
                        ValueFamily outputs = webDashboard.family( key: "outputs");
                        Ultrasonic ultra = new Ultrasonic(1,1);
                        while(true){
                            String val = ultra.getRangeInches().toString();
                            outputs.change("ultrasound", val);
                            logSafe("ultrasound " + val);
                            wait.forSeconds(0.5);

                        }
                        */
                        while(true){
                            String val = String.format("%1$,.2f", sensors.ultra.getVoltage()/0.0098);
                            outputs.change("distance", val);
                            //logSafe("distance " + val);
                            wait.forSeconds(0.5);

                        }

                    }
                }

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
        Motors.stopAll();
        webDashboard.save();
    }

    @Override
    public void disabledPeriodic() {

    }
    public static void log(String s){
    	webDashboard.log(s);
    }
    public static void logSafe(String s){
    	DriverStation.reportError(s+"\n", false);
    }
    public static void log(String... s){
    	log(String.join("\n", s));
    }
}
