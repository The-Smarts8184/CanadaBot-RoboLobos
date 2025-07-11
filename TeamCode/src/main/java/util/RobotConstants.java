package util;

import com.acmerobotics.dashboard.config.Config;

public class RobotConstants {
    @Config
    public static class Intake {
        public static double armLength = 9.46; // CHANGE

        public static String colorSensor = "colorSensor";
        public static String intakeSlide = "intakeSlide";
        public static String intakeClaw = "intakeClaw";
        public static String intakeYaw = "intakeYaw";
        public static String intakePitch = "intakePitch";
        public static String turret = "turret";

        public static double slideP = .005; // MAYBE CHANGE
        public static double slideI = 0.0;
        public static double slideD = 0.0;
        public static double slideF = 0.0;
        public static int slideMax = 450; // CHANGE
        public static int slideStowed = 0;

        public static double turretStowed = 0.59; // CHANGE
        public static double turretTransfer = 0.115; // CHANGE

        public static double intakePitchStowed = 0.0; // MAYBE CHANGE
        public static double intakePitchActive = 0.9; // MAYBE CHANGE
        public static double intakePitchTransfer = 0.5; // MAYBE CHANGE

        public static double clawOpen = 0.5; // MAYBE CHANGE
        public static double clawClosed = 0.0; // MAYBE CHANGE

        public static double intakeYawStowed = 0.0; // MAYBE CHANGE
        public static double intakeYawTransfer = 0.6; // MAYBE CHANGE
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
    public static class ClipMech {
        public static String railServo = "railServo";
        public static String railServoInput = "railServoInput";
        public static String clipHolderServo = "clipHolderServo";
        public static String clipHolderServoInput = "clipHolderServoInput";
        public static String clipPivotServo = "clipPivotServo";
        public static String clipHolderClawServo = "clipHolderClawServo";

        public static double railP = 2.0;
        public static double railI = 0.0;
        public static double railD = 0.0;
        public static double railF = 0.0;

        public static double railStowed = 0.05;
        public static double railClipping = 1.0;
        public static double railSecureClip = 0.8;
        public static double railOutTheWay = 1.3;

        public static double railFirstClip = 0.55;
        public static double railSecondClip = 0.45;
        public static double railThirdClip = 0.35;
        public static double railFourthClip = 0.25;

        public static double clipHolderP = 2.0;
        public static double clipHolderI = 0.0;
        public static double clipHolderD = 0.0;
        public static double clipHolderF = 0.0;

        public static double clipHolderStowed = -1.0;
        public static double clipHolderTransfer = 0.9;

        public static double clipHolderClawClosed = 0.0;
        public static double clipHolderClawOpen = 0.7;
        public static double clipHolderClawHalfOpen = 0.35;

        public static double clipPivotUp = 0.6;
        public static double clipPivotDown = 0.0;
        public static double clipPivotTransfer = 0.25;
        public static double clipPivotOutTheWay = 0.15;
    }

    @Config
    public static class Outtake {
        public static String outtakeMotorOne = "outtakeMotorOne";
        public static String outtakeMotorTwo = "outtakeMotorTwo";
        public static String outtakeMotorThree = "outtakeMotorThree";
        public static String outtakeArmServo = "outtakeArmServo";
        public static String outtakeArmInput = "outtakeArmInput";
        public static String outtakeClawServo = "outtakeClawServo";
        public static String outtakeColorSensor = "outtakeColorSensor";

        public static double outtakeExtendingP = 0.006;
        public static double outtakeExtendingI = 0.0;
        public static double outtakeExtendingD = 0.0;
        public static double outtakeExtendingF = 0.0;

        public static double outtakeRetractingP = 0.0044;
        public static double outtakeRetractingI = 0.0;
        public static double outtakeRetractingD = 0.0;
        public static double outtakeRetractingF = 0.0;

        public static int slideStowed = 0;
        public static int slideTransfer = 350;
        public static int slideChamber = 450;



        public static double armP = 2.0;
        public static double armI = 0.0;
        public static double armD = 0.0;
        public static double armF = 0.0;

        public static double armStowed = 0.50;
        public static double armClip = -0.5;
        public static double armClipInter = 0.53;
        public static double armChamberScoreReady = -1;
        public static double armChamberScoreInitial = -1.6;
        public static double armChamberScoreFinal = -1.4;
        public static double armTest = -1;



        public static double clawClosed = 0.48;
        public static double clawOpen = 0.25;



        // Color Sensor Values
        public static int upperRed = 1100;
        public static int lowerRed = 600;

        public static int upperGreen = 0;
        public static int lowerGreen = 0;

        public static int upperBlue = 0;
        public static int lowerBlue = 0;
    }
}
