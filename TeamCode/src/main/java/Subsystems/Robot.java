package Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;



public class Robot {
    public Drivetrain driveTrain;
    public Intake intake;
    public Outtake outtake;
    public GearShift gearShift;
    public RevColorSensorV3 colorSensor;

    Telemetry telemetry;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry){

        this.telemetry = telemetry;

        driveTrain = new Mecanum(hardwareMap);
        aX = new Arm(hardwareMap);
        servoClaw = new Claw(hardwareMap);
        servoRClaw = new RClaw(hardwareMap);
        s = new Slides(hardwareMap);
        sr = new SlideRotation(hardwareMap);

    }
}