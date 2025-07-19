package pedroPathing.paths;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import pedroPathing.AutoSubsystems.Intake.IntakeClaw;
import pedroPathing.AutoSubsystems.Intake.IntakeClawRotation;
import pedroPathing.AutoSubsystems.Intake.IntakePitch;
import pedroPathing.AutoSubsystems.Intake.IntakeSlides;
import pedroPathing.AutoSubsystems.Intake.Turret;
import pedroPathing.AutoSubsystems.Outtake.LeftPitch;
import pedroPathing.AutoSubsystems.Outtake.Linkage;
import pedroPathing.AutoSubsystems.Outtake.OuttakeClaw;
import pedroPathing.AutoSubsystems.Outtake.OuttakeWrist;
import pedroPathing.AutoSubsystems.Outtake.RightPitch;
import pedroPathing.AutoSubsystems.Outtake.Slides;
import util.RobotConstants;

@Autonomous(name = "Testing")
public class Testing extends PedroOpMode {
    public Testing() {
    super(IntakeClaw.INSTANCE, IntakeClawRotation.INSTANCE, IntakePitch.INSTANCE, IntakeSlides.INSTANCE,
            Turret.INSTANCE, LeftPitch.INSTANCE, Linkage.INSTANCE, OuttakeClaw.INSTANCE, OuttakeWrist.INSTANCE, RightPitch.INSTANCE, Slides.INSTANCE);
    }

    private double initX = RobotConstants.Auto.initX;
    private double initY = RobotConstants.Auto.initY;

    private final Pose startPose = new Pose(7.375,111.75,Math.toRadians(0));

    /// Scoring Pose of our robot. It is facing the submersible at a -45 degree (315 degree) angle.
    private final Pose scorePose = new Pose(4.8 + initX, 11.25 + initY, Math.toRadians(315));

    /// Lowest (First) Sample from the Spike Mark
    private final Pose pickup1Pose = new Pose(25.25 + initX, 7.5 + initY, Math.toRadians(0));

    /// Middle (Second) Sample from the Spike Mark
    private final Pose pickup2Pose = new Pose(25.5 + initX, 17.75 + initY, Math.toRadians(0));

    /// Highest (Third) Sample from the Spike Mark
    private final Pose pickup3Pose = new Pose(26.25 + initX, 24.17 + initY, Math.toRadians(45));

    /// Park Pose for our robot, after we do all of the scoring.
    private final Pose parkPose = new Pose(46 + initX, -25 + initY, Math.toRadians(270));

    /** Park Control Pose for our robot, this is used to manipulate the bezier curve that we will create for the parking.
     * The Robot will not go to this pose, it is used a control point for our bezier curve. */
    private final Pose parkControlPose = new Pose(62 + initX, 24 + initY);

    /** These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {

        /* There are two major types of paths components: BezierCurves and BezierLines.
         *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
         *    - Control points manipulate the curve between the start and end points.
         *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
         *    * BezierLines are straight, and require 2 points. There are the start and end points.
         * Paths can have heading interpolation: Constant, Linear, or Tangential
         *    * Linear heading interpolation:
         *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
         *    * Constant Heading Interpolation:
         *    - Pedro will maintain one heading throughout the entire path.
         *    * Tangential Heading Interpolation:
         *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
         * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
         * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */

        /* This is our scorePreload path. We are using a BezierLine, which is a straight line.*/
        scorePreload = new Path(new BezierLine(new Point(startPose),new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(),scorePose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(),pickup2Pose.getHeading())
                .build();

        /* This is our scorePickup2 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our park path. We are using a BezierCurve with 3 points,
        which is a curved line that is curved based off of the control point */
        park = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path,
     * but does NOT wait for it to finish before moving on. */

    public Command secondRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scorePreload),
                        Slides.INSTANCE.toScoreBucket()
                ),
                new ParallelGroup(
                        new FollowPath(grabPickup1),
                        IntakeClaw.INSTANCE.open(),
                        Slides.INSTANCE.toGround()
                ),
                new Delay(1.0),
                Slides.INSTANCE.toGround()
        );

    }
}