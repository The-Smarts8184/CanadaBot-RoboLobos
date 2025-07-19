package pedroPathing.AutoSubsystems.Outtake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class OuttakeWrist extends Subsystem {
    // BOILERPLATE
    public static final OuttakeWrist INSTANCE = new OuttakeWrist();
    private OuttakeWrist() { }

    // USER CODE
    public Servo servo;


    public Command pitchTransfer() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.pitchDrive, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command pitchScore() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.pitchScore, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command pitchPickUp() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.pitchDropOff, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Outtake.outtakePitch);
    }
}