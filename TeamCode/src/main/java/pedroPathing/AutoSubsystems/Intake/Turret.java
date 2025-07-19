package pedroPathing.AutoSubsystems.Intake;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import util.RobotConstants.*;

public class Turret extends Subsystem {
    // BOILERPLATE
    public static final Turret INSTANCE = new Turret();
    private Turret() { }

    // USER CODE
    public Servo servo;


    public Command defaultPos() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.turretDrive, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command dropOff() {
        return new ServoToPosition(servo, // SERVO TO MOVE
                Intake.turretDropOff, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    @Override
    public void initialize() {
        servo = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Intake.turret);
    }
}