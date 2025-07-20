package pedroPathing.AutoSubsystems.Outtake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import util.RobotConstants.*;
public class Slides extends Subsystem {
    // BOILERPLATE
    public static final Slides INSTANCE = new Slides();
    MotorGroup slideSystem;
    private Slides() { }

    // USER CODE
    public MotorEx encoderMotor, drivingMotor;

    public PIDFController leadController = new PIDFController(0.03, 0.00005, 0.00001, new StaticFeedforward(0.0));



    public Command toGround() {
        return new RunToPosition(slideSystem, // MOTOR TO MOVE
                Outtake.slideGround, // TARGET POSITION, IN TICKS
                leadController, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toScoreSpec() {
        return new RunToPosition(slideSystem, // MOTOR TO MOVE
                Outtake.slideSpec, // TARGET POSITION, IN TICKS
                leadController, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toScoreBucket() {
        return new RunToPosition(slideSystem, // MOTOR TO MOVE
                Outtake.slideSample, // TARGET POSITION, IN TICKS
                leadController, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    @Override
    public void initialize() {
        encoderMotor = new MotorEx(Outtake.outtakeFront);
        drivingMotor = new MotorEx(Outtake.outtakeRear);
        drivingMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideSystem = new MotorGroup(drivingMotor, encoderMotor);
    }

}