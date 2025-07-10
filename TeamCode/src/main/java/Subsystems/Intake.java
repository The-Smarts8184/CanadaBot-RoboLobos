package Subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

public class Intake implements Subsystem {
    private ColorSensor colorSensor;
    private Servo claw;
    private Servo clawYaw;
    private Servo clawPitch;
    private Servo turret;
    public Intake(HardwareMap hardwareMap){
        claw = hardwareMap.get(Servo.class, "claw");
        clawYaw = hardwareMap.get(Servo.class,"clawYaw");
        clawPitch = hardwareMap.get(Servo.class, "clawPitch");
        turret = hardwareMap.get(Servo.class, "turret");
        colorSensor = hardwareMap.get(ColorSensor.class,"colorSensor");
    }
    public void closeClaw() {

    }
}
