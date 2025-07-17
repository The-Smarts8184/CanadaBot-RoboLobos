package subsystems;



import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;

import util.RobotConstants;
import util.RobotHardware;

public class Outtake implements Subsystem {
    private final RobotHardware robot;



    private ClawState clawState;

    public enum ClawState {
        CLOSED, OPEN
    }


    public Outtake() {

        this.robot = RobotHardware.getInstance();

    }

    public void setPosition(int position) {
        double power = RobotConstants.Outtake.slidePower;
        robot.outtakeRear.setTargetPosition(position);
        robot.outtakeFront.setTargetPosition(position);
        robot.outtakeRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtakeFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtakeRear.setPower(power);
        robot.outtakeFront.setPower(power);
    }


    public void resetEncoder() {
        robot.outtakeRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.outtakeFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.outtakeRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.outtakeFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void retractSlides() {
        slidePower(-1);
        if (robot.outtakeRear.getCurrentPosition() < 5){
            stopSlides();
        }
    }

    public void slideLIncrement() {
        if (robot.outtakeRear.getCurrentPosition() > RobotConstants.Outtake.slideMax) {
            setPosition(RobotConstants.Outtake.slideMax);;
        } else {
            setPosition(robot.outtakeRear.getCurrentPosition() + RobotConstants.Outtake.slideIncrement);
        }
    }
    public void slideRIncrement() {
        if (robot.outtakeRear.getCurrentPosition() > RobotConstants.Outtake.slideMax) {
            setPosition(RobotConstants.Outtake.slideMax);;
        } else {
            setPosition(robot.outtakeRear.getCurrentPosition() - RobotConstants.Outtake.slideIncrement);
        }
    }

    public void stopSlides() {
        robot.outtakeRear.setPower(0);
        robot.outtakeFront.setPower(0);
    }
    public void slidePower(double power) {
        robot.outtakeRear.setPower(power);
        robot.outtakeFront.setPower(power);

    }



    public void setClawState(ClawState state) {
        clawState = state;
        switch (state) {
            case OPEN:
                robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                break;
            case CLOSED:
                robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawClosed);
                break;
        }
    }

    public ClawState getClawState() {
        return clawState;
    }





}
