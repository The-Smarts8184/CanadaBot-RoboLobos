package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class IntakePitchCommand extends InstantCommand {
    public IntakePitchCommand(double position) {
        super(
                () -> RobotHardware.getInstance().intakePitch.setPosition(position)
        );
    }
}
