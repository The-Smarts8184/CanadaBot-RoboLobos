package Subsystems;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
public class Outtake {
    private Servo claw;
    private Servo pitch;
    private Servo linkage;
    private Servo lPitch;
    private Servo rPitch;
    public Outtake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
        pitch = hardwareMap.get(Servo.class, "pitch");
        linkage = hardwareMap.get(Servo.class, "linkage");
        lPitch = hardwareMap.get(Servo.class, "lPitch");
        rPitch = hardwareMap.get(Servo.class, "rPitch");
    }

    public void closeClaw() {

    }
}
