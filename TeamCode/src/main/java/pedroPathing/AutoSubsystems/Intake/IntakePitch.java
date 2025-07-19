package pedroPathing.AutoSubsystems.Intake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class IntakePitch extends Subsystem {
    // BOILERPLATE
    public static final IntakePitch INSTANCE = new IntakePitch();
    private IntakePitch() { }

    // USER CODE
    public Servo servo;

    public Command defaultPos() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.intakePitchDrive, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command intake() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.intakePitchIntake, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command subIn() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.intakePitchSubIn, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }






    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Intake.intakePitch);
    }
}