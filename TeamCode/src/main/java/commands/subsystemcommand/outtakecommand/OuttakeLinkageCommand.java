package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class OuttakeLinkageCommand extends InstantCommand {
    public OuttakeLinkageCommand(double position) {
        super(
                () -> RobotHardware.getInstance().outtake.setLinkagePosition(position)
        );
    }
}