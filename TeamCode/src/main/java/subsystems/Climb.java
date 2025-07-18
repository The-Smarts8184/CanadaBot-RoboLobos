package subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import util.RobotConstants;
import util.RobotHardware;


public class Climb {

    private final RobotHardware robot;

    public Climb() {
        robot = RobotHardware.getInstance();
    }

    public enum GearState {
        GearClimb, GearDrive
    }

    public void shiftGears(GearState state) {
        switch(state) {
            case GearDrive:
                robot.gearShift.setPosition(RobotConstants.Outtake.gearShiftDrive);
                break;
            case GearClimb:
                robot.gearShift.setPosition(RobotConstants.Outtake.gearShiftClimb);
                break;
        }
    }
}