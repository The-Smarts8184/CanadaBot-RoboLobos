package subsystems;





import static util.RobotHardware.climbPID;
import static util.RobotHardware.controller;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;

import util.RobotConstants;
import util.RobotHardware;

public class Outtake implements Subsystem {
    private final RobotHardware robot;

    double power;
    private ClawState clawState;
    double p =0.03, i = 0.00005, d = 0.00001;

    private boolean climbMode;

    public enum ClawState {
        CLOSED, OPEN
    }


    public Outtake() {

        this.robot = RobotHardware.getInstance();

        climbMode = false;

    }

    public void PIDLoop(int target) {
//        controller.setPID(this.p, this.i, this.d);
//
//        int pos = robot.outtakeRear.getCurrentPosition();
//        int backMotorPos = pos;
//        int frontMotorPos = robot.outtakeFront.getCurrentPosition();
//
//
//        double pid = controller.calculate(pos, target);
//
//        power = Math.max(-1.0, Math.min(1.0,pid));
//
//
//        robot.outtakeRear.setPower(power);
//        robot.outtakeFront.setPower(power);
//        if(pos < 50 && target == 0) {
//            robot.outtakeRear.setPower(0);
//            robot.outtakeFront.setPower(0);
//        }

        int backPos = robot.outtakeRear.getCurrentPosition();
        int frontPos = robot.outtakeFront.getCurrentPosition();

        int avgPos = (backPos + frontPos) / 2;

        double power;

        if (climbMode) {
            power = climbPID.calculate(avgPos, target);
        } else {
            power = controller.calculate(avgPos, target);
        }

//        double power = controller.calculate(avgPos, target);

        robot.outtakeRear.setPower(power);
        robot.outtakeFront.setPower(power);
        if(avgPos < 50 && target == 0) {
            robot.outtakeRear.setPower(0);
            robot.outtakeFront.setPower(0);
        }

    }

    public double getPower(){
        return power;
    }

//    public void setPosition(int position) {
//        double power = RobotConstants.Outtake.slidePowerUp;
//        robot.outtakeRear.setTargetPosition(position);
//        robot.outtakeFront.setTargetPosition(position);
//        robot.outtakeRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.outtakeFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.outtakeRear.setPower(power);
//        robot.outtakeFront.setPower(power);
//    }

    public void setBackMotorPosition(int position){
        double power = RobotConstants.Outtake.slidePowerUp;
        robot.outtakeRear.setTargetPosition(position);
        robot.outtakeRear.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.outtakeRear.setPower(power);
    }
    public void resetEncoder() {
        robot.outtakeRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.outtakeFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.outtakeRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.outtakeFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

//    public void retractSlides() {
//        setPosition(0);
//        if (robot.outtakeRear.getCurrentPosition() < 5){
//            stopSlides();
//        }
//    }
//
//    public void slideLIncrement() {
//        if (robot.outtakeRear.getCurrentPosition() > RobotConstants.Outtake.slideMax) {
//            setPosition(RobotConstants.Outtake.slideMax);;
//        } else {
//            setPosition(robot.outtakeRear.getCurrentPosition() + RobotConstants.Outtake.slideIncrement);
//        }
//    }
//    public void slideRIncrement() {
//        if (robot.outtakeRear.getCurrentPosition() > RobotConstants.Outtake.slideMax) {
//            setPosition(RobotConstants.Outtake.slideMax);;
//        } else {
//            setPosition(robot.outtakeRear.getCurrentPosition() - RobotConstants.Outtake.slideIncrement);
//        }
//    }

    public void stopSlides() {
        robot.outtakeRear.setPower(0);
        robot.outtakeFront.setPower(0);
    }
    public void setSlidePower(double power) {
        robot.outtakeRear.setPower(power);
        robot.outtakeFront.setPower(power);

    }


    public void setClimbMode(boolean climbMode) {
        this.climbMode = climbMode;
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
