package commands.subsystemcommand.intakecommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import util.IntakeInverseKinematics;
import util.RobotHardware;

public class RefreshIntakeIK extends InstantCommand {
    public RefreshIntakeIK() {
        super(
                () -> IntakeInverseKinematics.calculateIK(RobotHardware.getInstance().limelightClass.getTargetedSample().x, RobotHardware.getInstance().limelightClass.getTargetedSample().y, RobotHardware.getInstance().limelightClass.getTargetedSample().r)
        );
    }
}