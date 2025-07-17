package subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import util.RobotHardware;

public class Drivetrain implements Subsystem {
    private RobotHardware robot;
    private double y, x, rx, leftFrontPower, leftRearPower, rightFrontPower, rightRearPower, heading, rotX, rotY;

    public Drivetrain() {
        this.robot = RobotHardware.getInstance();
    }

    public void periodic(double slowmode) {
        double y = robot.driver.getLeftY();
        double x = robot.driver.getLeftX() * 1.1;
        rx = robot.driver.getRightX() * 1.1;

        heading = robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        leftFrontPower = (rotY + rotX + rx);
        leftRearPower = (rotY - rotX + rx);
        rightFrontPower = (rotY - rotX - rx);
        rightRearPower = (rotY + rotX - rx);

        robot.leftFront.setPower(leftFrontPower * slowmode);
        robot.leftRear.setPower(leftRearPower * slowmode);
        robot.rightFront.setPower(rightFrontPower * slowmode);
        robot.rightRear.setPower(rightRearPower * slowmode);
    }
}