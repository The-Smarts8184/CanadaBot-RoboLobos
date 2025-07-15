package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.RobotHardware;

public class TurretCommand extends InstantCommand {
    public TurretCommand(double position) {
        super(
                () -> RobotHardware.getInstance().intake.setTurretPosition(position)
        );
    }
}