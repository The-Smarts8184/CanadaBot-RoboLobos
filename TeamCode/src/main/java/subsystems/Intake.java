package subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.DcMotor;

import util.Globals;
import util.RobotConstants;
import util.RobotHardware;

public class Intake implements Subsystem {

    private final RobotHardware robot;
    private ClawState clawState;

    public enum ClawState {
        CLOSED, OPEN, LOOSE
    }

    public Intake() {

        this.robot = RobotHardware.getInstance();

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
            case LOOSE:
                robot.intakeClaw.setPosition(RobotConstants.Intake.clawLoose);
                break;
        }
    }

    public ClawState getClawState() {
        return clawState;
    }

    public void IncrementLClawRotation() {
        robot.clawRotation.setPosition(robot.clawRotation.getPosition() + 0.018);
        if (robot.clawRotation.getPosition() > RobotConstants.Intake.clawRotation902){
            robot.clawRotation.setPosition(RobotConstants.Intake.clawRotation902);
        }
    }

    public void IncrementRClawRotation() {
        robot.clawRotation.setPosition(robot.clawRotation.getPosition() - 0.018);
        if (robot.clawRotation.getPosition() < RobotConstants.Intake.clawRotation901){
            robot.clawRotation.setPosition(RobotConstants.Intake.clawRotation901);
        }
    }


    public void setPosition(int position) {
        double power = RobotConstants.Intake.slidePower;
        robot.intakeSlide.setTargetPosition(position);
        robot.intakeSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.intakeSlide.setPower(power);
    }


    public void resetEncoder() {
        robot.intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.intakeSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void retractSlides() {
        setPosition(0);
        if (robot.intakeSlide.getCurrentPosition() < 10){
            stopSlides();
        }
    }

    public void stopSlides() {
        robot.intakeSlide.setPower(0);
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
