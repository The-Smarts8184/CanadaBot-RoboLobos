package util;

public class IntakeInverseKinematics {
    public static double turretAngle = RobotConstants.Intake.turretStowed;
    public static int slideExtension = RobotConstants.Intake.slideStowed;
    public static double intakeYaw = RobotConstants.Intake.intakeYawStowed;
    public static double slideExtensionInches = 0.0;
    public static int slideOffset = 0;
    public static double turretAngleDeg = 0.0;
    public static double turretOffset = 0.11;
    public static double clawRotationOffset = -0.1;

    public static double intakeXOffset = -5.199;

    public static void calculateIK(double x, double y, double r) {
        x += intakeXOffset;
        turretAngleDeg = getIKTurretAngleDeg(x, y);
        turretAngle = getIKTurretAngle(x, y);
        slideExtension = getIKSlideExtension(x, y);
        slideExtensionInches = getIKSlideExtensionInches(x, y);
        intakeYaw = getClawRotation(r);
    }

    public static double getClawRotation(double r) {
        return (r + clawRotationOffset) / 180;
    }

    public static double getIKTurretAngle(double x, double y) {
        double temp = Math.toDegrees(Math.acos(x / RobotConstants.Intake.armLength));
        temp = ((temp) + 90) / 360;
        temp += turretOffset;
        return temp;
    }

    public static double getIKTurretAngleDeg(double x, double y) {
        double temp = Math.toDegrees(Math.acos(x / RobotConstants.Intake.armLength));
        return ((temp) + 90);
    }

    public static int getIKSlideExtension(double x, double y) {
        return inchesToMotorTicks(y - Math.sqrt(Math.pow(RobotConstants.Intake.armLength, 2) - Math.pow(x, 2)));
    }

    public static double getIKSlideExtensionInches(double x, double y) {
        return y - Math.sqrt(Math.pow(RobotConstants.Intake.armLength, 2) - Math.pow(x, 2));
    }

    public static int inchesToMotorTicks(double inches) {
        int result = (int) (inches / .024);

        result -= slideOffset;

        return result;
    }
}
