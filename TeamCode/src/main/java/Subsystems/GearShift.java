package Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import Commands.States.GearState;


public class GearShift {
    private final Servo gearShift;
    private GearState gearState;

    public GearShift(HardwareMap hardwareMap) {
        gearShift = hardwareMap.get(Servo.class, "gearShift");
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