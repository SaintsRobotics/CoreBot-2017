package com.saintsrobotics.corebot.tasks.teleop;
import com.saintsrobotics.corebot.Robot;
import com.saintsrobotics.corebot.coroutine.RepeatingTask;
import com.saintsrobotics.corebot.output.MotorGroup;
import com.saintsrobotics.corebot.output.MotorsWebDashboard;

public class ArcadeDriveTask extends RepeatingTask {
	private MotorGroup left;
	private MotorGroup right;
	@Override
	protected void init(){
		left = new MotorGroup("left1","left2","left3");
		right = new MotorGroup("right1","right2","right3");
	}
    @Override
    protected void doOnRepeat(double sec) {
        double forward = -Robot.oi.drive.axes.leftStickY();
        double turn = Robot.oi.drive.axes.rightStickX();
        
        left.set(forward + turn);
        right.set(forward - turn);
    }
}
