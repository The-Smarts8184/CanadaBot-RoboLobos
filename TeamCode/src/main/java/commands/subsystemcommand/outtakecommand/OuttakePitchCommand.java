package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class OuttakePitchCommand extends InstantCommand {
    public OuttakePitchCommand(double position) {
        super(
                () -> RobotHardware.getInstance().outtake.setPitchPosition(position)
        );
    }
}