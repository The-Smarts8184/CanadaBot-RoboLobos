package subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import util.Globals;
import util.RobotConstants;
import util.RobotHardware;

public class Intake implements Subsystem {

    private final RobotHardware robot;
    private double turretPosition;
    private ClawState clawState;
    private int target, prevTarget;

    public static boolean slideReset = false;

    private int slideSampleCheck;

    public enum IntakeState {
        INTAKING, TRANSFERRING, STOWED
    }

    public enum ClawState {
        CLOSED, OPEN
    }

    public Intake() {

        this.robot = RobotHardware.getInstance();

        setExtensionTarget(RobotConstants.Intake.slideStowed);
        setTurretPosition(RobotConstants.Intake.turretLL);
        slideSampleCheck = 0;

    }

    public void setClawState(ClawState state) {
        clawState = state;
        switch (state) {
            case OPEN:
                robot.intakeClaw.setPosition(RobotConstants.Intake.clawOpen);
                break;
            case CLOSED:
                robot.intakeClaw.setPosition(RobotConstants.Intake.clawClosed);
                break;
        }
    }

//    public void setState(IntakeState state) {
//
//    }

    public void periodic() {
        powerSlides();
    }

    public void setExtensionTarget(int position) {
        position += slideSampleCheck;
        if (position <= RobotConstants.Intake.slideMax && position >= RobotConstants.Intake.slideStowed) {
            prevTarget = target;
            target = position;
        }
    }

    public int getSlideSampleCheck() {
        return slideSampleCheck;
    }

    public void setSlideSampleCheck(int slideSampleCheck) {
        this.slideSampleCheck = slideSampleCheck;
    }

    public int getExtensionTarget() {
        return target;
    }

    public void powerSlides() {
        double correction;
        correction = robot.intakeSlidePID.calculate(robot.intakeSlide.getCurrentPosition(), target);

        if (slideReset) {
            robot.intakeSlide.setPower(-1);
            if (robot.intakeSlide.getCurrent(CurrentUnit.AMPS) > 5) {
                robot.intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                robot.intakeSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                robot.intakeSlide.setPower(0);

                slideReset = false;
            }
        } else if (target == RobotConstants.Intake.slideStowed && (robot.intakeSlide.getCurrentPosition() - target) <= 2000) {
            robot.intakeSlide.setPower(0);
        } else {
            robot.intakeSlide.setPower(correction);
        }
    }

    public void resetSlides() {
        slideReset = true;
    }

    public void setTurretPosition(double position) {
        turretPosition = position;
        robot.turret.setPosition(position);
    }

    public double getTurretPosition() {
         return robot.turret.getPosition();
    }

    public void updateSample() {
        Globals.SAMPLE_LOADED = isSample();
    }

    public boolean isSample() {
        int red = robot.colorSensor.red();
        int green = robot.colorSensor.green();
        int blue = robot.colorSensor.blue();

        return  red < RobotConstants.Intake.upperRed && red > RobotConstants.Intake.lowerRed ||
                green < RobotConstants.Intake.upperGreen && green > RobotConstants.Intake.lowerGreen ||
                blue < RobotConstants.Intake.upperBlue && blue > RobotConstants.Intake.lowerBlue;
    }
}
