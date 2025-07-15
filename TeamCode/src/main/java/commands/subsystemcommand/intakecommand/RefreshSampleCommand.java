package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class RefreshSampleCommand extends InstantCommand {
    public RefreshSampleCommand() {
        super(
                () -> RobotHardware.getInstance().limelightClass.refreshSamples()
        );
    }
}