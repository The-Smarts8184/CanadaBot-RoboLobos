package commands.subsystemcommand.outtakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import subsystems.Outtake;
import util.RobotHardware;

public class OuttakeStateCommand extends InstantCommand {
    public OuttakeStateCommand(Outtake.OuttakeState outtakeState) {
        super(
                () -> RobotHardware.getInstance().outtake.setOuttakeState(outtakeState)
        );
    }
}