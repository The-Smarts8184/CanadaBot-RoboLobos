package opmode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.Timer;
import java.util.TimerTask;

import subsystems.Intake;
import subsystems.Outtake;
import util.RobotConstants;
import util.RobotHardware;
import commands.states.*;

// TODO:
//  1. Intake : setPositions
//  2. Intake : States

@TeleOp
public class Teleop extends CommandOpMode {

    private final RobotHardware robot = RobotHardware.getInstance();
    private GamepadEx driver;
    private SampleStates sampleState = SampleStates.DRIVE;




    public void setSampleState(SampleStates state) {
        sampleState = state;
    }

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        driver = new GamepadEx(gamepad1);

        robot.init(hardwareMap,driver);
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        if(driver.gamepad.left_trigger > 0.1) {
            robot.drivetrain.periodic(0.3);
        } else {
            robot.drivetrain.periodic(1);
        }

        driver.readButtons();

        Timer timer1 = new Timer();
        Timer timer2 = new Timer();


        telemetry.addData("slide pos: ",robot.outtakeRear.getCurrentPosition());
        telemetry.addData("slide pos: ",robot.outtakeFront.getCurrentPosition());
        telemetry.update();




        switch (sampleState) {
            case DRIVE:
                robot.intake.retractSlides();
                robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchDrive);
                robot.intake.setClawState(Intake.ClawState.CLOSED);
                robot.clawRotation.setPosition(RobotConstants.Intake.clawRotationDrive);
                robot.outtake.setClawState(Outtake.ClawState.OPEN);
                robot.outtake.retractSlides();
                robot.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageDrive);
                robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchDrive);
                robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchDrive);
                robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDrive);


                if (driver.gamepad.y) {
                    setSampleState(SampleStates.SCORE);
                }
                if (driver.gamepad.a) {
                    setSampleState(SampleStates.INTAKE);
                }
                break;
            case INTAKE:
                if (driver.gamepad.b) {
                    setSampleState(SampleStates.DRIVE);
                }
                robot.intake.setPosition(RobotConstants.Intake.slideSub);
                if (robot.intakePitch.getPosition() != RobotConstants.Intake.intakePitchIntake) {
                    robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchSubIn);
                }
                if (robot.intakePitch.getPosition() == RobotConstants.Intake.intakePitchSubIn) {
                    robot.intake.setClawState(Intake.ClawState.OPEN);
                }

                if (driver.gamepad.left_bumper) {
                    robot.intake.IncrementLClawRotation();
                }
                if (driver.gamepad.right_bumper) {
                    robot.intake.IncrementRClawRotation();
                }

                TimerTask grabSub = new TimerTask() {
                    public void run() {
                        robot.intake.setClawState(Intake.ClawState.CLOSED);
                    }
                };

                if (driver.gamepad.right_trigger > 0.1) {
                    robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchIntake);
                    timer1.schedule(grabSub, 150);
                }

                if (robot.intake.getClawState() == Intake.ClawState.CLOSED && robot.intake.isSample()){
                    setSampleState(SampleStates.DRIVE);
                }
                break;
            case SCORE:
                if (driver.gamepad.b) {
                    setSampleState(SampleStates.DRIVE);
                }

                TimerTask Transfer = new TimerTask() {
                    public void run() {
                        robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                        robot.intake.setClawState(Intake.ClawState.OPEN);
                    }
                };

                TimerTask score = new TimerTask() {
                    public void run() {
                        robot.outtake.setPosition(RobotConstants.Outtake.slideSample);
                        robot.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageScore);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchScore);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchScore);
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchScore);
                    }
                };

                if (robot.intake.getClawState() == Intake.ClawState.CLOSED) {
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchTransfer);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchTransfer);
                    timer1.schedule(Transfer, 150);
                }




                if(robot.intake.getClawState() == Intake.ClawState.OPEN && robot.outtake.getClawState() == Outtake.ClawState.CLOSED) {
                    timer2.schedule(score, 350);
                }




                if (driver.gamepad.right_trigger > 0.1) {
                    robot.outtake.setClawState(Outtake.ClawState.OPEN);
                };

                break;

        }


    }
}
