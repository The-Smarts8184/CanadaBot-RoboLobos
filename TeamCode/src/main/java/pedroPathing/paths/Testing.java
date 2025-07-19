package pedroPathing.paths;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import pedroPathing.AutoSubsystems.Intake.IntakeClaw;
import pedroPathing.AutoSubsystems.Intake.IntakeClawRotation;
import pedroPathing.AutoSubsystems.Intake.IntakePitch;
import pedroPathing.AutoSubsystems.Intake.IntakeSlides;
import pedroPathing.AutoSubsystems.Intake.Turret;
import pedroPathing.AutoSubsystems.Outtake.LeftPitch;
import pedroPathing.AutoSubsystems.Outtake.Linkage;
import pedroPathing.AutoSubsystems.Outtake.OuttakeClaw;
import pedroPathing.AutoSubsystems.Outtake.OuttakeWrist;
import pedroPathing.AutoSubsystems.Outtake.RightPitch;
import pedroPathing.AutoSubsystems.Outtake.Slides;

@Autonomous(name = "Testing")
public class Testing extends PedroOpMode {
    public Testing() {
    super(IntakeClaw.INSTANCE, IntakeClawRotation.INSTANCE, IntakePitch.INSTANCE, IntakeSlides.INSTANCE,
            Turret.INSTANCE, LeftPitch.INSTANCE, Linkage.INSTANCE, OuttakeClaw.INSTANCE, OuttakeWrist.INSTANCE, RightPitch.INSTANCE, Slides.INSTANCE);
    }
}