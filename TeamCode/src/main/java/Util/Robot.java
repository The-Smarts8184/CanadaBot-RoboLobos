package Util;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.ColorSensor;

import Subsystems.Drivetrain;
import Subsystems.GearShift;
import Subsystems.Intake;
import Subsystems.Outtake;


public class Robot {
    public Drivetrain driveTrain;
    public Intake intake;
    public Outtake outtake;
    public GearShift gearShift;
    public ColorSensor colorSensor;

    Telemetry telemetry;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry){

        this.telemetry = telemetry;

        driveTrain = new Drivetrain(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
        gearShift = new GearShift(hardwareMap);
        colorSensor = new ColorSensor(hardwareMap);
        sr = new SlideRotation(hardwareMap);

    }
}