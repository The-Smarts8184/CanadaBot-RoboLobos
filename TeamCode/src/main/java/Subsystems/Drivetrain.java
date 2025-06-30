package Subsystems;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drivetrain {

    IMU imu;
    DcMotorEx frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    public double slowMode = 1;

    public Drivetrain(HardwareMap hardwareMap){
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

        frontLeftMotor.setDirection(DcMotorEx.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorEx.Direction.REVERSE);


        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        imu.initialize(parameters);
    }

    public void fieldCentric(Gamepad gamepad1){
        // gamepad1 controls for Mecanum drive
        double y = -gamepad1.left_stick_y;  // Reversed Y-axis
        double x = gamepad1.left_stick_x * 1.1;  // Strafe control
        double rx = gamepad1.right_stick_x;  // Rotation control

        // Reset IMU yaw if touchpad is pressed


        // Get robot's orientation
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Adjust the joystick input for field-centric control
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
        rotX *= 1.1;  // Counteract imperfect strafing

        // Calculate motor power
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        // Set motor powers for driving
        frontLeftMotor.setPower(frontLeftPower*slowMode);
        backLeftMotor.setPower(backLeftPower*slowMode);
        frontRightMotor.setPower(frontRightPower*slowMode);
        backRightMotor.setPower(backRightPower*slowMode);
    }
    public double getHeading(){
        return -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }

    public void resetIMU(){
        imu.resetYaw();
    }
}