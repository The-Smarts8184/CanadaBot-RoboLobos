package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class OuttakeLRPitchCommand extends InstantCommand {
    public OuttakeLRPitchCommand(double position) {
        super(
                () -> RobotHardware.getInstance().outtake.setLRPitchPosition(position)
        );
    }
}