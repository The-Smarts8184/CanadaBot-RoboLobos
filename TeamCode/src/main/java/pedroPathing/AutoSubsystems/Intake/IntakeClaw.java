package pedroPathing.AutoSubsystems.Intake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class IntakeClaw extends Subsystem {
    // BOILERPLATE
    public static final IntakeClaw INSTANCE = new IntakeClaw();
    private IntakeClaw() { }

    // USER CODE
    public Servo servo;


    public Command open() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.clawOpen, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command close() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.clawClosed, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Intake.intakeClaw);
    }
}