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

        public static int slideMax = 750;
        public static int slideDrive = 0;
        public static int slidePower = 1;
        public static int slideSub = 400;
        public static int slideSubLong = 575;
        public static int slideAutoPickup = 625;


        public static double turretDrive = 0.5;
        public static double turretAutoLeft = 0.62; // change value
        public static double turretAutoRight = 0.38; // change value

        public static double clawRotationDrive = 0.5;
        public static double clawRotationAutoPickUp = 0.5; //change when needed
        public static double clawRotation45 = 0.375;
        public static double clawRotation135 = 0.625;
        public static double clawRotation901 = 0.28;
        public static double clawRotation902 = 0.72;
        public static double clawRotationAutoLeft = 0.62;
        public static double clawRotationAutoRight = 0.38;

        public static double clawOpen = 0;
        public static double clawClosed = 0.9;
        public static double clawLoose = 0.8;


        //SAMPLE

        public static double intakePitchSubIn = 0.82;
        public static double intakePitchIntake = 0.93;
        public static double intakePitchDrive = 0.22;
        public static double intakePitchAutoDrive = 0.5;





        //SPECIMEN
        public static double turretDropOff = 0.15;
        public static double pitchDropOff = 0.6;


        // Color Sensor Values
        public static int upperRed = 10000; // MAYBE CHANGE
        public static int lowerRed = 3000; // MAYBE CHANGE

        public static int upperGreen = 10000;
        public static int lowerGreen = 3000;

        public static int upperBlue = 10000;
        public static int lowerBlue = 3000;

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
        public static String gearShift = "gearShift";


        public static int slideGround = 0;
        public static int slideSpec = 700;
        public static int slideSample = 1320;
        public static int slideLowSample = 400;
        public static int slideHighSample = 1500;
        public static int slideIncrement = 20;
        public static int slideClimb1 = 2400;
        public static int slideClimb2 = 1000;
        public static int slideClimb3 = 4500;
        public static int slideMax = 1600;
        public static double slidePowerUp = 1;
        public static double slidePowerDown = -1;



        public static double linkageDrive = 0.03;
        public static double linkageScore = 1;

        public static double pitchDrive = 0;
        public static double pitchScore = 0.64;
        public static double LRPitchDrive = 0.6;
        public static double LRPitchScore = 0.25;
        public static double LRPitchTransfer = 0.675;
        public static double LRPitchClimb1 = 0.4;
        public static double LRPitchClimb2 = 0.55;

        // SPEC
        public static double pitchDropOff = 0.7;
        public static double LRPitchDropOff = 0.03;
        public static double pitchSpecScore = 0.62;
        public static double LRPitchSpecScore =0.66;
        public static double LRPitchSpecLongDropOff1 = 0.1;
        public static double LRPitchSpecLongDropOff2 = 0.07;
        public static double LRPitchSpecLongDropOff3 = 0.15;


        public static double gearShiftDrive = 0.95;
        public static double gearShiftClimb = 0;



        public static double clawClosed = 0.75;
        public static double clawOpen = 0;

    }

    @Config
    public static class Auto {
        public static double initX = 7.375;
        public static double initY = 111.75;
        public static double specInitY = 64.5;
    }
}
