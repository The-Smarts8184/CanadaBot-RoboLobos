package subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class Climb {
    private final Servo gearShift;

    public Climb(HardwareMap hardwareMap) {
        gearShift = hardwareMap.get(Servo.class, "gearShift");
    }

    public enum GearState {
        GearClimb, GearGround
    }

    public void shiftGears(GearState state) {
        switch(state) {
            case GearGround:
                gearShift.setPosition(0);
                break;
            case GearClimb:
                gearShift.setPosition(1);
                break;
        }
    }
}