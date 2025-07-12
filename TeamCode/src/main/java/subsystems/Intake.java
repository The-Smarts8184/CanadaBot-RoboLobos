package subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import util.RobotConstants;
import util.RobotHardware;

public class Intake implements Subsystem {

    private final RobotHardware robot;
    private double turretTarget;
    private ClawState clawState;
    private int target, prevTarget;

    public static boolean slideReset = false;

    public static int slideSampleCheck;

    public enum IntakeState {
        INTAKING, TRANSFERRING, STOWED
    }

    public enum ClawState {
        CLOSED, OPEN
    }

    public Intake(RobotHardware robot){

        this.robot = RobotHardware.getInstance();

        setExtensionTarget(RobotConstants.Intake.slideStowed);
        setTurretTarget(RobotConstants.Intake.turretStowed);
        slideSampleCheck = 0;
    }

}
