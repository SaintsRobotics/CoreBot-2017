package com.saintsrobotics.corebot;

import com.saintsrobotics.corebot.input.LimitSwitches;
import com.saintsrobotics.corebot.input.OI;
import com.saintsrobotics.corebot.output.Motors;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        NetworkTable.class,
        DriverStation.class,
        Motors.Motor.class,
        OI.Input.class,
        LimitSwitches.LimitSwitch.class})
@SuppressStaticInitializationFor("edu.wpi.first.wpilibj.DriverStation")
public class RobotTest {
    
    private Robot robot;
    private Talon leftDrive1 = Mockito.mock(Talon.class);
    private Talon leftDrive2 = Mockito.mock(Talon.class);
    private Talon leftDrive3 = Mockito.mock(Talon.class);
    private Talon rightDrive1 = Mockito.mock(Talon.class);
    private Talon rightDrive2 = Mockito.mock(Talon.class);
    private Talon rightDrive3 = Mockito.mock(Talon.class);
    private DigitalInput exampleLimitSwitch = Mockito.mock(DigitalInput.class);
    private Joystick driveStick = Mockito.mock(Joystick.class);
    private Joystick operatorStick = Mockito.mock(Joystick.class);
    
    @Before
    public void setupRobot() throws Exception {
        Robot.MOTOR_RAMPING = 1;
        
        PowerMockito.mockStatic(NetworkTable.class);
        ITable mockSubTable = Mockito.mock(ITable.class);
        NetworkTable mockNetworkTable = Mockito.mock(NetworkTable.class);
        PowerMockito.when(mockNetworkTable.getSubTable("~STATUS~")).thenReturn(mockSubTable);
        PowerMockito.when(NetworkTable.class, "getTable", "LiveWindow").thenReturn(mockNetworkTable);
        
        DriverStation ds = Mockito.mock(DriverStation.class);
        PowerMockito.when(ds.isEnabled()).thenReturn(true);
        
        Field dsInstance = DriverStation.class.getDeclaredField("instance");
        dsInstance.setAccessible(true);
        dsInstance.set(null, ds);
        
        PowerMockito.whenNew(Talon.class).withArguments(1).thenReturn(leftDrive1);
        PowerMockito.whenNew(Talon.class).withArguments(2).thenReturn(leftDrive2);
        PowerMockito.whenNew(Talon.class).withArguments(3).thenReturn(leftDrive3);
        PowerMockito.whenNew(Talon.class).withArguments(4).thenReturn(rightDrive1);
        PowerMockito.whenNew(Talon.class).withArguments(5).thenReturn(rightDrive2);
        PowerMockito.whenNew(Talon.class).withArguments(6).thenReturn(rightDrive3);
        PowerMockito.whenNew(Joystick.class).withArguments(0).thenReturn(driveStick);
        PowerMockito.whenNew(Joystick.class).withArguments(1).thenReturn(operatorStick);
        PowerMockito.whenNew(DigitalInput.class).withArguments(0).thenReturn(exampleLimitSwitch);
        
        PowerMockito.when(driveStick.getRawAxis(anyInt())).thenReturn(-0.5);
        
        robot = new Robot();
        robot.robotInit();
    }
    
    @Test
    public void testTeleop() {
        robot.teleopInit();
        for (int i = 0; i < 1200; i++) {
            robot.teleopPeriodic();
        }
        verify(leftDrive1, times(1200)).set(anyDouble());
        verify(leftDrive2, times(1200)).set(anyDouble());
        verify(leftDrive3, times(1200)).set(anyDouble());
        verify(rightDrive1, times(1200)).set(anyDouble());
        verify(rightDrive2, times(1200)).set(anyDouble());
        verify(rightDrive3, times(1200)).set(anyDouble());
        
        verify(leftDrive1, atLeastOnce()).set(0.0);
        verify(leftDrive2, atLeastOnce()).set(0.0);
        verify(leftDrive3, atLeastOnce()).set(0.0);
        verify(rightDrive1, atLeastOnce()).set(1.0);
        verify(rightDrive2, atLeastOnce()).set(1.0);
        verify(rightDrive3, atLeastOnce()).set(1.0);
    }
    
    @Test
    public void testTest() throws InterruptedException {
        robot.testInit();
        
        robot.testPeriodic();
        verify(leftDrive1).set(0.2);
        Thread.sleep(210);
        
        robot.testPeriodic();
        verify(leftDrive2).set(0.2);
        Thread.sleep(210);
        
        robot.testPeriodic();
        verify(leftDrive3).set(0.2);
        Thread.sleep(210);
    
        robot.testPeriodic();
        verify(rightDrive1).set(0.2);
        Thread.sleep(210);
    
        robot.testPeriodic();
        verify(rightDrive2).set(0.2);
        Thread.sleep(210);
    
        robot.testPeriodic();
        verify(rightDrive3).set(0.2);
        Thread.sleep(210);
    
        verify(leftDrive1, times(6)).set(0.2);
        
        robot.testPeriodic();
        verify(leftDrive1, times(1)).set(0);
        Thread.sleep(210);
        
        robot.testPeriodic();
        robot.testPeriodic();
        verify(leftDrive1, times(7)).set(0.2);
        Thread.sleep(210);
    }
}
