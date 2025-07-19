package subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import util.RobotConstants;
import util.RobotConstants.*;

@Config
public class PIDF extends OpMode {

    private PIDController controller;
    public static double p =0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    public final double ticks_in_degree = 384.5/360;

    DcMotorEx frontMotor, backMotor;


    @Override
    public void init() {
    controller = new PIDController(p, i,d);
    telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    frontMotor = hardwareMap.get(DcMotorEx.class,RobotConstants.Outtake.outtakeFront);
    backMotor = hardwareMap.get(DcMotorEx.class, RobotConstants.Outtake.outtakeRear);
    }

    @Override
    public void loop() {
        controller.setPID(p,i,d);
        int pos = frontMotor.getCurrentPosition();

        double pid = controller.calculate(pos, target);
        double ff = Math.cos(Math.toRadians(target/ticks_in_degree)) * f;

        double power = pid +ff;

        frontMotor.setPower(power);
        backMotor.setPower(power);

        telemetry.addData("Front Pos", frontMotor.getCurrentPosition());
        telemetry.addData("target", target);


    }
}
