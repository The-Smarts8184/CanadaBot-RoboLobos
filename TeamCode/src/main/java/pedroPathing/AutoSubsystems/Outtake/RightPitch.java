package pedroPathing.AutoSubsystems.Outtake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class RightPitch extends Subsystem {
    // BOILERPLATE
    public static final RightPitch INSTANCE = new RightPitch();
    private RightPitch() { }

    // USER CODE
    public Servo servo;

    public Command specDefault() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.LRPitchDropOff, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command smpleDefault() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.LRPitchDrive, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command transfer() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.LRPitchTransfer, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command scoreSample() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.LRPitchScore, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command scoreSpecimen() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Outtake.LRPitchSpecScore, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }





    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Outtake.outtakeRPitch);
    }
}