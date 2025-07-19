package pedroPathing.AutoSubsystems.Intake;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

import util.RobotConstants.*;
public class IntakeSlides extends Subsystem {
    // BOILERPLATE
    public static final IntakeSlides INSTANCE = new IntakeSlides();
    private IntakeSlides() { }

    // USER CODE
    public MotorEx intakeSlideMotor;


    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));

    public Command defaultPos() {
        return new RunToPosition(intakeSlideMotor, // MOTOR TO MOVE
                Intake.slideDrive, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command slideMax() {
        return new RunToPosition(intakeSlideMotor, // MOTOR TO MOVE
                Intake.slideMax, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command slideSub() {
        return new RunToPosition(intakeSlideMotor, // MOTOR TO MOVE
                Intake.slideSub, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    @Override
    public void initialize() {
        intakeSlideMotor = new MotorEx(Intake.intakeSlide);

    }

}