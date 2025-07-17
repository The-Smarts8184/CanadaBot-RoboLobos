package opmode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
    private SpecStates specState = SpecStates.DRIVE;
    TeleopStates teleopState = TeleopStates.SPEC;
    private boolean tempBucket1 = true;
    private boolean tempBucket2 = true;



    public void setSampleState(SampleStates state) {
        sampleState = state;
    }

    public void setSpecState(SpecStates state) {
        specState = state;
    }

    public void setTeleopState(TeleopStates state) {
        teleopState = state;
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
        telemetry.addData("State: ",teleopState);
        telemetry.addData("Sample State: ",sampleState);
        telemetry.addData("Spec State: ",specState);
        telemetry.update();


        switch (teleopState) {
            case SPEC:

                if (driver.gamepad.share) {
                    setTeleopState(TeleopStates.SAMPlE);
                }

            switch (specState) {
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
                    robot.turret.setPosition(RobotConstants.Intake.turretDrive);


                    if (driver.gamepad.y) {
                        setSpecState(SpecStates.SCORE);
                    }
                    if (driver.gamepad.a) {
                        setSpecState(SpecStates.INTAKE);
                    }
                    if (driver.gamepad.x) {
                        setSpecState(SpecStates.WALL);
                    }
                    break;
                case SCORE:
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

                    if (robot.intake.getClawState() == Intake.ClawState.CLOSED && robot.intake.isSample()) {
                        setSampleState(SampleStates.DRIVE);
                    }
                    break;
                case WALL:
                    if (driver.gamepad.b) {
                        setSampleState(SampleStates.DRIVE);
                    }

                    robot.intake.retractSlides();
                    robot.intakePitch.setPosition(RobotConstants.Intake.pitchDropOff);
                    robot.clawRotation.setPosition(RobotConstants.Intake.clawRotation902);
                    robot.outtake.setClawState(Outtake.ClawState.OPEN);
                    robot.outtake.retractSlides();
                    robot.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageDrive);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
                    robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                    robot.turret.setPosition(RobotConstants.Intake.turretDropOff);



                    break;

            }
            break;
            case SAMPlE:

                if (driver.gamepad.options) {
                    setTeleopState(TeleopStates.SPEC);
                }

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

                    if (robot.intake.getClawState() == Intake.ClawState.CLOSED && robot.intake.isSample()) {
                        setSampleState(SampleStates.DRIVE);
                    }
                    break;
                case SCORE:
                    if (driver.gamepad.b) {
                        setSampleState(SampleStates.DRIVE);
                    }

                    TimerTask Transfer1 = new TimerTask() {
                        public void run() {
                            robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                        }
                    };

                    TimerTask Transfer2 = new TimerTask() {
                        public void run() {
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
                        timer1.schedule(Transfer1, 150);
                        timer2.schedule(Transfer2,250);
                        timer1.schedule(score, 500);
                    }


                    if (driver.gamepad.right_trigger > 0.1) {
                        robot.outtake.setClawState(Outtake.ClawState.OPEN);
                    }

                    break;

            } break;
        }

    }
}
