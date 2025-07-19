package opmode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Timer;
import java.util.TimerTask;

import subsystems.Climb;
import subsystems.Intake;
import subsystems.Outtake;
import util.RobotConstants;
import util.RobotHardware;
import commands.states.*;

// TODO:
//  1. Intake : setPositions
//  2. Intake : States

@TeleOp (name = "Telemetry")
public class SlideTelemetry extends CommandOpMode {

    private final RobotHardware robot = RobotHardware.getInstance();
    private GamepadEx driver;
    private SampleStates sampleState = SampleStates.DRIVE;
    private SpecStates specState = SpecStates.DRIVE;
    TeleopStates teleopState = TeleopStates.SPEC;


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

        telemetry.addData("slide pos: ",robot.outtakeRear.getCurrentPosition());
        telemetry.addData("slide pos: ",robot.outtakeFront.getCurrentPosition());
        telemetry.addData("State: ",teleopState);
        telemetry.addData("Sample State: ",sampleState);
        telemetry.addData("Spec State: ",specState);
        telemetry.update();
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();

        driver.readButtons();

        telemetry.addData("slide pos: ",robot.outtakeRear.getCurrentPosition());
        telemetry.addData("slide pos: ",robot.outtakeFront.getCurrentPosition());
        telemetry.addData("State: ",teleopState);
        telemetry.addData("Sample State: ",sampleState);
        telemetry.addData("Spec State: ",specState);
        telemetry.update();


    }
}
