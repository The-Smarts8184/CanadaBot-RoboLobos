package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import subsystems.Intake;
import util.RobotHardware;

public class IntakeClawCommand extends InstantCommand {
    public IntakeClawCommand(Intake.ClawState clawState) {
        super(
                () -> RobotHardware.getInstance().intake.setClawState(clawState)
        );
    }
}