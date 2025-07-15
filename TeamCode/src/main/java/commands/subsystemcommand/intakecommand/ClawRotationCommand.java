package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class ClawRotationCommand extends InstantCommand {
    public ClawRotationCommand(double position) {
        super(
                () -> RobotHardware.getInstance().clawRotation.setPosition(position)
        );
    }
}
