package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import subsystems.Outtake;
import util.RobotHardware;

public class OuttakeClawCommand extends InstantCommand {
    public OuttakeClawCommand(Outtake.ClawState clawState) {
        super(
                () -> RobotHardware.getInstance().outtake.setClawState(clawState)
        );
    }
}