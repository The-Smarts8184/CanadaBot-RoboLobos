package util;

import com.acmerobotics.dashboard.config.Config;

public class RobotConstants {
    @Config
    public static class Intake {
        public static double armLength = 6.75; // SIX SEVEN

        public static String colorSensor = "colorSensor";
        public static String intakeSlide = "intakeSlide";
        public static String intakeClaw = "intakeClaw";
        public static String clawRotation = "clawRotation";
        public static String intakePitch = "intakePitch";
        public static String turret = "turret";

        public static double slideP = .005; //
        public static double slideI = 0.003;
        public static double slideD = 0.0; //
        public static double slideF = 0.0;
        public static int slideMax = 45000; //
        public static int slideStowed = 0;

        public static double turretTransfer = 0.5; // MAYBE CHANGE
        public static double turretDrop = 0.75; // MAYBE CHANGE

        public static double intakePitchStowed = 0.5; // MAYBE CHANGE
        public static double intakePitchActive = 0.9; // MAYBE CHANGE
        public static double intakePitchTransfer = 0.25; // MAYBE CHANGE

        public static double clawOpen = 0;
        public static double clawClosed = 0.9;
        public static double clawTest = 0.5;


        public static double clawRotationStowed = 0.5;
        public static double clawRotationDrop = 0.7; // MAYBE CHANGE

        // Color Sensor Values
        public static int upperRed = 1100; // MAYBE CHANGE
        public static int lowerRed = 600; // MAYBE CHANGE

        public static int upperGreen = 0;
        public static int lowerGreen = 0;

        public static int upperBlue = 0;
        public static int lowerBlue = 0;

    }


    @Config
    public static class Drivetrain {
        public static String leftFront = "leftFront";
        public static String leftRear = "leftRear";
        public static String rightFront = "rightFront";
        public static String rightRear = "rightRear";
    }

    @Config
    public static class Limelight {

    }

    @Config
    public static class Outtake {
        public static String outtakeRear = "outtakeRear";
        public static String outtakeFront = "outtakeFront";
        public static String outtakeClaw = "outtakeClaw";
        public static String outtakePitch = "outtakePitch";
        public static String outtakeLinkage = "outtakeLinkage";
        public static String outtakeLPitch = "outtakeLPitch";
        public static String outtakeRPitch = "outtakeRPitch";

        public static double outtakeExtendingP = 0.006;
        public static double outtakeExtendingI = 0.0;
        public static double outtakeExtendingD = 0.0;
        public static double outtakeExtendingF = 0.0;

        public static double outtakeRetractingP = 0.0044;
        public static double outtakeRetractingI = 0.0;
        public static double outtakeRetractingD = 0.0;
        public static double outtakeRetractingF = 0.0;

        public static int slideStowed = 0;
        public static int slideTransfer = 350; // MAYBE CHANGE
        public static int slideClimb = 450; // MAYBE CHANGE
        public static int slideMax = 450; // MAYBE CHANGE


                // CHANGE MOST NAMES
        public static double linkageStowed = 0.50; // MAYBE CHANGE
        public static double linkageTransfer = 1; // MAYBE CHANGE
        public static double pitchStowed = -0.5; // MAYBE CHANGE
        public static double pitchTransfer = -0.5; // MAYBE CHANGE
        public static double LRPitchStowed = -1; // MAYBE CHANGE
        public static double LRPitchTransfer = 0.53; // MAYBE CHANGE



        public static double clawClosed = 0.48; // MAYBE CHANGE
        public static double clawOpen = 0.25; // MAYBE CHANGE

    }
}
