package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import subsystems.Intake;
import util.RobotHardware;

public class SetSlideExtendCheckCommand extends InstantCommand {
    public SetSlideExtendCheckCommand(int extension) {
        super(
                () -> RobotHardware.getInstance().intake.setSlideSampleCheck(extension)
        );
    }
}