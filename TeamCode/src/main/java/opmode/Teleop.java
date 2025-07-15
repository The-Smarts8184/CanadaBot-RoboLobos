package opmode;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import commands.subsystemcommand.intakecommand.IntakeClawCommand;
//import commands.teleopcommand.IntakeSampleCommand;
//import org.firstinspires.ftc.teamcode.commands.teleopcommand.LoadClipCommand;
//import org.firstinspires.ftc.teamcode.commands.teleopcommand.ScoreOnChamber;
//import org.firstinspires.ftc.teamcode.commands.teleopcommand.StowOuttakeSlides;
//import org.firstinspires.ftc.teamcode.commands.teleopcommand.TransferSampleCommand;
import subsystems.Intake;
import subsystems.Outtake;
import util.Globals;
import util.IntakeInverseKinematics;
import util.RobotConstants;
import util.RobotHardware;
import util.Sample;

// TODO:
//  1. Motor Directions

@TeleOp
public class Teleop extends CommandOpMode {

    private final RobotHardware robot = RobotHardware.getInstance();
    private GamepadEx driver;
    private GamepadEx operator;
    private int x, y;
    private Sample targetedSample;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        robot.init(hardwareMap,driver);
    }

}
