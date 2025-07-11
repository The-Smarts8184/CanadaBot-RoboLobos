package util;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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


@Config
public class RobotHardware {

    // Intake
    public ColorSensor colorSensor;
    public Servo intakeClaw;
    public Servo intakeYaw;
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
    public DcMotorEx leftSlide;
    public DcMotorEx rightSlide;
    public Servo outtakeClaw;
    private Servo outtakePitch;
    private Servo outtakeLinkage;
    private Servo outtakeLPitch;
    private Servo outtakeRPitch;

    // Hardware
    private HardwareMap hardwareMap;
    private static RobotHardware instance = null;
    private boolean enabled;

    public GamepadEx driver;

    // Subsystems
    public Intake intake;
    public org.firstinspires.ftc.teamcode.subsystem.Drivetrain drivetrain;
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
        this.hardwareMap =hardwareMap;
        this.driver = driver;

        // ******************* INTAKE ******************* //
        this.colorSensor = hardwareMap.get(ColorSensor.class,RobotConstants.Intake.colorSensor);

        this.intakeClaw = hardwareMap.servo.get(RobotConstants.Intake.intakeClaw);
        this.intakeClaw.setPosition(RobotConstants.Intake.clawOpen);

        this.intakeYaw = hardwareMap.servo.get(RobotConstants.Intake.intakeYaw);
        this.intakeYaw.setPosition(RobotConstants.Intake.intakeYawStowed);

        this.intakePitch = hardwareMap.servo.get(RobotConstants.Intake.intakePitch);
        this.intakePitch.setPosition(RobotConstants.Intake.intakePitchStowed);

        this.intakeSlide = hardwareMap.get(DcMotorEx.class, RobotConstants.Intake.intakeSlide);
        intakeSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeSlidePID = new PIDFController(RobotConstants.Intake.slideP,RobotConstants.Intake.slideI,RobotConstants.Intake.slideD,RobotConstants.Intake.slideF);
        intakeSlide.setPower(0);

        // ******************* DRIVETRAIN ******************* //
        leftFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftFront);
        leftRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.leftRear);
        rightRear = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightRear);
        rightFront = hardwareMap.get(DcMotorEx.class, RobotConstants.Drivetrain.rightFront);

        leftFront.setDirection(DcMotorEx.Direction.REVERSE);
        leftRear.setDirection(DcMotorEx.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT, //
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        ));
        imu.initialize(parameters);
        imu.resetYaw();

        // ******************* LIMELIGHT ******************* //
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0); // MAYBE CHANGE
    }
}