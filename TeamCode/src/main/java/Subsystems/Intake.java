package Subsystems;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class Intake {
    private RevColorSensorV3 colorSensor;
    private Servo claw;
    private Servo clawYaw;
    private Servo clawPitch;
    private Servo turret;
    public Intake(HardwareMap hardwareMap){
        claw = hardwareMap.get(Servo.class, "claw");
        clawYaw = hardwareMap.get(Servo.class,"clawYaw");
        clawPitch = hardwareMap.get(Servo.class, "clawPitch");
        turret = hardwareMap.get(Servo.class, "turret");
    }
    public void closeClaw() {

    }
}
