package util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import subsystems.Climb;
import subsystems.Intake;
import subsystems.Outtake;
import subsystems.Drivetrain;


@Config
public class RobotHardware {

    // Intake
    public ColorSensor colorSensor;
    public Servo intakeClaw;
    public Servo clawRotation;
    public Servo intakePitch;
    public Servo turret;
    public DcMotorEx intakeSlide;
    public PIDFController intakeSlidePID;


    // Drivetrain
    public DcMotorEx leftFront, leftRear, rightFront, rightRear;
    public IMU imu;

    // Limelight
    public Limelight3A limelight;

    // Outtake
    public DcMotorEx outtakeRear, outtakeFront;
    public PIDFController outtakeSlideExtendPID, outtakeSlideRetractPID;
    public Servo outtakeClaw;
    public Servo outtakePitch;
    public Servo outtakeLinkage;
    public Servo outtakeLPitch;
    public Servo outtakeRPitch;
    public Servo gearShift;

    public static PIDController controller;
//    public static double p =0.008, i = 0.00005, d = 0.00001;
    public static double p =0.03, i = 0.00005, d = 0.00001;
    public static double f = 0;

    public static int target = 0;

    public final double ticks_in_degree = 384.5/360;

    // Hardware
    private HardwareMap hardwareMap;
    private static RobotHardware instance = null;
    private boolean enabled;

    public GamepadEx driver;

    // Subsystems
    public Intake intake;
    public Drivetrain drivetrain;
    public Climb climb;
    public Outtake outtake;
    public Globals globals;

    // Camera
    public CameraCalculations cameraCalcs;

    public static RobotHardware getInstance() {
        if (instance == null) {
            instance = new RobotHardware();
        }
        instance.enabled = true;
        return instance;
    }

    public void init(final HardwareMap hardwareMap, GamepadEx driver) {
        this.hardwareMap = hardwareMap;
        this.driver = driver;



        // ******************* INTAKE ******************* //
        this.colorSensor = hardwareMap.get(ColorSensor.class,RobotConstants.Intake.colorSensor);

        this.intakeClaw = hardwareMap.servo.get(RobotConstants.Intake.intakeClaw);
        this.intakeClaw.setPosition(RobotConstants.Intake.clawClosed);

        this.clawRotation = hardwareMap.servo.get(RobotConstants.Intake.clawRotation);
        this.clawRotation.setPosition(RobotConstants.Intake.clawRotationDrive);

        this.intakePitch = hardwareMap.servo.get(RobotConstants.Intake.intakePitch);
        this.intakePitch.setPosition(RobotConstants.Intake.intakePitchDrive);

        this.turret = hardwareMap.servo.get(RobotConstants.Intake.turret);
        this.turret.setPosition(RobotConstants.Intake.turretAutoLeft); // for testing
        this.turret.setPosition(RobotConstants.Intake.turretAutoRight); // for testing
        this.turret.setPosition(RobotConstants.Intake.turretDrive);

        this.intakeSlide = hardwareMap.get(DcMotorEx.class, RobotConstants.Intake.intakeSlide);
        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setTargetPosition(0);
        intakeSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // ******************* DRIVETRAIN ******************* //
        leftFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftFront);
        leftRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftRear);
        rightRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightRear);
        rightFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightFront);

        leftFront.setDirection(DcMotorEx.Direction.REVERSE); // MAYBE CHANGE
        leftRear.setDirection(DcMotorEx.Direction.REVERSE); // MAYBE CHANGE

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT, //
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        ));
        imu.initialize(parameters);
        imu.resetYaw();


        /*
        // ******************* LIMELIGHT ******************* //
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0); // MAYBE CHANGE*/



        // ******************* OUTTAKE ******************* //
        this.outtakeRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeRear);
        this.outtakeFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeFront);

        outtakeRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeRear.setTargetPosition(0);
        outtakeRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        outtakeFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeFront.setTargetPosition(0);
        outtakeFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        outtakeRear.setDirection(DcMotorSimple.Direction.REVERSE);

        this.outtakeClaw = hardwareMap.servo.get(RobotConstants.Outtake.outtakeClaw);
        this.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);

        this.outtakeLinkage = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLinkage);
        this.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageDrive);

        this.outtakePitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakePitch);
        this.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);

        this.outtakeLPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLPitch);
        this.outtakeRPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeRPitch);
        this.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
        this.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);

        this.gearShift = hardwareMap.servo.get(RobotConstants.Outtake.gearShift);
        this.gearShift.setPosition(RobotConstants.Outtake.gearShiftDrive);

        controller = new PIDController(p, i,d);

        //backMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        drivetrain = new Drivetrain();
        intake = new Intake();
        outtake = new Outtake();
        climb = new Climb();

//        limelightClass = new Limelight();
//        cameraCalcs = new CameraCalculations();
    }

    public void init(final HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;



        // ******************* INTAKE ******************* //
        this.colorSensor = hardwareMap.get(ColorSensor.class,RobotConstants.Intake.colorSensor);

        this.intakeClaw = hardwareMap.servo.get(RobotConstants.Intake.intakeClaw);
        this.intakeClaw.setPosition(RobotConstants.Intake.clawOpen);

        this.clawRotation = hardwareMap.servo.get(RobotConstants.Intake.clawRotation);
        this.clawRotation.setPosition(RobotConstants.Intake.clawRotationDrive);

        this.intakePitch = hardwareMap.servo.get(RobotConstants.Intake.intakePitch);
        this.intakePitch.setPosition(RobotConstants.Intake.intakePitchDrive);

        this.turret = hardwareMap.servo.get(RobotConstants.Intake.turret);
        this.turret.setPosition(RobotConstants.Intake.turretAutoLeft); // for testing
        this.turret.setPosition(RobotConstants.Intake.turretAutoRight); // for testing
        this.turret.setPosition(RobotConstants.Intake.turretDrive);

        this.intakeSlide = hardwareMap.get(DcMotorEx.class, RobotConstants.Intake.intakeSlide);
        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setTargetPosition(0);
        intakeSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // ******************* DRIVETRAIN ******************* //
        leftFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftFront);
        leftRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftRear);
        rightRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightRear);
        rightFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightFront);

        leftFront.setDirection(DcMotorEx.Direction.REVERSE); // MAYBE CHANGE
        leftRear.setDirection(DcMotorEx.Direction.REVERSE); // MAYBE CHANGE

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT, //
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        ));
        imu.initialize(parameters);
        imu.resetYaw();


        /*
        // ******************* LIMELIGHT ******************* //
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0); // MAYBE CHANGE*/



        // ******************* OUTTAKE ******************* //
        this.outtakeRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeRear);
        this.outtakeFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeFront);

        outtakeRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        outtakeFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        outtakeRear.setDirection(DcMotorSimple.Direction.REVERSE);


        this.outtakeClaw = hardwareMap.servo.get(RobotConstants.Outtake.outtakeClaw);
        this.outtakeClaw.setPosition(RobotConstants.Outtake.clawClosed);

        this.outtakeLinkage = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLinkage);
        this.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageDrive);

        this.outtakePitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakePitch);
        this.outtakePitch.setPosition(RobotConstants.Outtake.pitchDrive);

        this.outtakeLPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLPitch);
        this.outtakeRPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeRPitch);
        this.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
        this.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);

        this.gearShift = hardwareMap.servo.get(RobotConstants.Outtake.gearShift);
        this.gearShift.setPosition(RobotConstants.Outtake.gearShiftDrive);

        controller = new PIDController(p, i,d);

        //backMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        drivetrain = new Drivetrain();
        intake = new Intake();
        outtake = new Outtake();
        climb = new Climb();

//        limelightClass = new Limelight();
//        cameraCalcs = new CameraCalculations();
    }


}