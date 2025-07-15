package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class SlideResetCommand extends InstantCommand {
    public SlideResetCommand() {
        super(
                () -> RobotHardware.getInstance().intake.resetSlides()
        );
    }
}