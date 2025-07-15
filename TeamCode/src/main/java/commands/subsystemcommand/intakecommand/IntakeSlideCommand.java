package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class IntakeSlideCommand extends InstantCommand {
    public IntakeSlideCommand(int position) {
        super(
                () -> RobotHardware.getInstance().intake.setExtensionTarget(position)
        );
    }
}