package pedroPathing.AutoSubsystems.Outtake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class Linkage extends Subsystem {
    // BOILERPLATE
    public static final Linkage INSTANCE = new Linkage();
    private Linkage() { }

    // USER CODE
    public Servo servo;


    public Command defaultPos() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.linkageDrive, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command score() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.linkageScore, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Outtake.outtakeLinkage);
    }
}