package subsystems;



import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import util.Globals;
import util.RobotConstants;
import util.RobotHardware;

public class Outtake implements Subsystem {
    private final RobotHardware robot;

    private int slideTarget;
    private double pitchPosition;
    private double linkagePosition;
    private double LRPitchPosition;

    public static boolean slideReset = false;

    private ClawState clawState;

    public enum ClawState {
        CLOSED, OPEN
    }

    private OuttakeState outtakeState;

    public enum OuttakeState {
        SPEC_INIT, SPEC_FINAL, STOWED, TRANSFERRING
    }
    public Outtake() {
        this.robot = RobotHardware.getInstance();

        outtakeState = OuttakeState.STOWED;

        slideTarget = RobotConstants.Outtake.slideStowed;
    }

    public void periodic() {
        powerSlides();
    }

    public void setOuttakeState(OuttakeState outtakeState) {
        this.outtakeState = outtakeState;
    }

    public OuttakeState getOuttakeState() {
        return outtakeState;
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

    public void setSlideTarget(int slideTarget) {
        this.slideTarget = slideTarget;
    }

    public void powerSlides() {
        double correction;
        if (slideTarget > robot.leftSlide.getCurrentPosition()) {
            correction = robot.outtakeSlideExtendPID.calculate(robot.leftSlide.getCurrentPosition(), slideTarget);
        } else {
            correction = robot.outtakeSlideRetractPID.calculate(robot.leftSlide.getCurrentPosition(), slideTarget);
        }

        if (slideReset) {
            robot.intakeSlide.setPower(-1); // MAYBE CHANGE
            if (robot.intakeSlide.getCurrent(CurrentUnit.AMPS) > 5) {
                robot.intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.intakeSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.intakeSlide.setPower(0);

                slideReset = false;
            }
        } else if (slideTarget == RobotConstants.Intake.slideStowed && (robot.intakeSlide.getCurrentPosition() - slideTarget) <= 20) {
            robot.intakeSlide.setPower(0);
        } else {
            robot.intakeSlide.setPower(correction);
        }
    }

    public void setOuttakeMotorsPower(double power) {
        robot.leftSlide.setPower(power);
        robot.rightSlide.setPower(power);
    }
    public void setPitchPosition(double position) {
        pitchPosition = position;
        robot.outtakePitch.setPosition(position);
    }

    public void setLRPitchPosition(double position) {
        LRPitchPosition = position;
        robot.outtakeLPitch.setPosition(position);
        robot.outtakeRPitch.setPosition(position);
    }

    public void setLinkagePosition(double position) {
        linkagePosition = position;
        robot.outtakeLinkage.setPosition(position);
    }

}
