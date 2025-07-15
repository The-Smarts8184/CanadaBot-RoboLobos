package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class OuttakeSlideCommand extends InstantCommand {
    public OuttakeSlideCommand (int position) {
        super (
                () -> RobotHardware.getInstance().outtake.setSlideTarget(position)
        );
    }
}