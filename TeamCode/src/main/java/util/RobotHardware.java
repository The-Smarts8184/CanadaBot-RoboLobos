package util;

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
import subsystems.Limelight;
import subsystems.Outtake;
import org.firstinspires.ftc.teamcode.subsystem.Drivetrain;


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

    // Climb
    public Servo gearShift;

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
    public IntakeInverseKinematics intakeIK;
    public CameraCalculations cameraCalcs;
    public Limelight limelightClass;

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
        /*this.colorSensor = hardwareMap.get(ColorSensor.class,RobotConstants.Intake.colorSensor);

        this.intakeClaw = hardwareMap.servo.get(RobotConstants.Intake.intakeClaw);
        this.intakeClaw.setPosition(RobotConstants.Intake.clawOpen);

        this.clawRotation = hardwareMap.servo.get(RobotConstants.Intake.clawRotation);
        this.clawRotation.setPosition(RobotConstants.Intake.clawRotationStowed);

        this.intakePitch = hardwareMap.servo.get(RobotConstants.Intake.intakePitch);
        this.intakePitch.setPosition(RobotConstants.Intake.intakePitchStowed);

        this.intakeSlide = hardwareMap.get(DcMotorEx.class, RobotConstants.Intake.intakeSlide);

        intakeSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        intakeSlidePID = new PIDFController(RobotConstants.Intake.slideP,RobotConstants.Intake.slideI,RobotConstants.Intake.slideD,RobotConstants.Intake.slideF);
        intakeSlide.setPower(0);*/

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
        limelight.pipelineSwitch(0); // MAYBE CHANGE



        // ******************* OUTTAKE ******************* //
        this.outtakeRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeRear);
        this.outtakeFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeFront);

        outtakeRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        outtakeRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeRear.setPower(0);

        outtakeFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        outtakeFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        outtakeFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        outtakeFront.setPower(0);

        outtakeRear.setDirection(DcMotorSimple.Direction.REVERSE); // MAYBE CHANGE
        outtakeFront.setDirection(DcMotorSimple.Direction.REVERSE); // MAYBE CHANGE

        outtakeSlideExtendPID = new PIDFController(RobotConstants.Outtake.outtakeExtendingP, RobotConstants.Outtake.outtakeExtendingI, RobotConstants.Outtake.outtakeExtendingD, RobotConstants.Outtake.outtakeExtendingF);
        outtakeSlideRetractPID = new PIDFController(RobotConstants.Outtake.outtakeRetractingP, RobotConstants.Outtake.outtakeRetractingI, RobotConstants.Outtake.outtakeRetractingD, RobotConstants.Outtake.outtakeRetractingF);

        this.outtakeClaw = hardwareMap.servo.get(RobotConstants.Outtake.outtakeClaw);
        this.outtakeClaw.setPosition(RobotConstants.Outtake.clawClosed);

        this.outtakeLinkage = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLinkage);
        this.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageStowed);

        this.outtakePitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakePitch);
        this.outtakePitch.setPosition(RobotConstants.Outtake.pitchStowed);

        this.outtakeLPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeLPitch);
        this.outtakeRPitch = hardwareMap.servo.get(RobotConstants.Outtake.outtakeRPitch);
        this.outtakePitch.setPosition(RobotConstants.Outtake.LRPitchStowed);*/


        drivetrain = new Drivetrain();
        intake = new Intake();
        outtake = new Outtake();

//        intakeIK = new IntakeInverseKinematics();
//        limelightClass = new Limelight();
//        cameraCalcs = new CameraCalculations();
    }

    public void periodic() {
        //intake.periodic();
        drivetrain.periodic();
        //outtake.periodic();
    }
}