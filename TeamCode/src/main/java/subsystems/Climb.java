package subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import util.RobotHardware;


public class Climb {

    private final RobotHardware robot;

    public Climb(HardwareMap hardwareMap) {
        robot = RobotHardware.getInstance();
    }

    public enum GearState {
        GearClimb, GearDrive
    }

    public void shiftGears(GearState state) {
        switch(state) {
            case GearDrive:
                robot.gearShift.setPosition(0);
                break;
            case GearClimb:
                robot.gearShift.setPosition(1);
                break;
        }
    }
}