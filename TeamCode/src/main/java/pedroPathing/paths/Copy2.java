package pedroPathing.paths;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import subsystems.Intake;
import subsystems.Outtake;
import util.RobotConstants;
import util.RobotHardware;

@Config
@Autonomous(name = "fourth Write", group = "Examples")
public class Copy2 extends OpMode {

    int target = RobotConstants.Outtake.slideGround;

    private Follower follower;
    private Timer pathTimer, opmodeTimer,actionTimer;
    private double initX = RobotConstants.Auto.initX;
    private double initY = RobotConstants.Auto.specInitY;
    private final RobotHardware robot = RobotHardware.getInstance();
    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;
    private boolean tempAutoSpec = true;
    private boolean tempAutoSpec2 = true;
    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;

    /* Create and Define Poses + Paths
     * Poses are built with thee constructors: x, y, and heading (in Radians).
     * Pedro uses 0 - 144 for x and y, with 0, 0 being on the bottom left.
     * (For Into the Deep, this would be Blue Observation Zone (0,0) to Red Observation Zone (144,144).)
     * Even though Pedro uses a different coordinate system than RR, you can convert any roadrunner pose by adding +72 both the x and y.
     * This visualizer is very easy to use to find and create paths/pathchains/poses: <https://pedro-path-generator.vercel.app/>
     * Lets assume our robot is 18 by 18 inches
     * Lets assume the Robot is facing the human player and we want to score in the bucket */


    /** list of Poses.
     Start Pose of our robot */
    //0 radians = outtake claw facing wall
    private final Pose startPose = new Pose(7.375,64.5,Math.toRadians(0));
    ///
    ///
    private final Pose preloadPose = new Pose(22 + initX, 0 + initY, Math.toRadians(0));

    private final Pose endIntakeOnePose = new Pose(9.75 + initX, -43 + initY, Math.toRadians(0));
    private final Pose endIntakeTwoPose = new Pose(9.75 + initX, -53.5 + initY, Math.toRadians(0));

    private final Pose controlToPushOne1Pose = new Pose(13, 25, Math.toRadians(0)); //150
    private final Pose controltoPushOne2Pose = new Pose(105,28,Math.toRadians(0));
    private final Pose pushedSpecOnePose = new Pose(11 + initX, -42 + initY, Math.toRadians(0));


    private final Pose controlToPushTwo1Pose = new Pose(75, 28.5, Math.toRadians(0));
    private final Pose controlToPushTwo2Pose = new Pose(65, 8.5, Math.toRadians(0));
    private final Pose endPush2Pose = new Pose(13 + initX, initY -52.5, Math.toRadians(0));

    private final Pose controlToPushThree1Pose = new Pose(56, 22, Math.toRadians(0));
    private final Pose controlToPushThree2Pose = new Pose(70, 7, Math.toRadians(0));
    private final Pose endPush3Pose = new Pose(17, 7, Math.toRadians(0));



    private final Pose goToSpec = new Pose(1 + initX, 34, Math.toRadians(0));
    private final Pose goToSpecControl = new Pose(32.5, 22.5, Math.toRadians(0));
    private final Pose pickUpFirstSpec = new Pose(8.25 + initX, 34, Math.toRadians(0));
    private final Pose pickUpSpec = new Pose(4.5 + initX, 34, Math.toRadians(0));

    private final Pose controlToScoreSpec = new Pose(13, 67.5, Math.toRadians(0));
    private final Pose scoreSpec1 = new Pose(19.75 + initX, 1.5 + initY, Math.toRadians(0));

    private final Pose prepareForPushingOnePose = new Pose(45 + initX,  initY -37.7, Math.toRadians(0)); //30
    private final Pose goingToPushSpecTwoPose = new Pose(50 + initX, -52 + initY, Math.toRadians(315));
    private final Pose pushedSpecTwoPose = new Pose(52 + initX, -52 + initY, Math.toRadians(0));
    private final Pose pushSpecPose2 = new Pose(12.5 + initX, -52 + initY, Math.toRadians(0));

    private final Pose prepareForPushingTwoPose = new Pose(46 + initX, -46 + initY, Math.toRadians(0)); //30
    private final Pose goingToPushSpecThreePose = new Pose(52 + initX, -57.5 + initY, Math.toRadians(0)); //315
    private final Pose pushSpec3Pose2 = new Pose(12.5 + initX, -55 + initY, Math.toRadians(0));  //315
    // private final Pose pushedSpecThreePose = new Pose(4.8 + initX, 11.25 + initY, Math.toRadians(315));

    private final Pose aboutToGrabOnePose = new Pose(5,-30,Math.toRadians(0));
    private final Pose grabOnePose = new Pose(0,-30,Math.toRadians(0));


    private final Pose scoreOnePosePose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose aboutToGrabTwoPose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose grabTwoPose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose scoreTwoPosePose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose aboutToGrabThreePose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose grabThreePose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose scoreThreePosePose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose aboutToGrabFourPose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose grabFourPose = new Pose(7.375,111.75,Math.toRadians(0));
    private final Pose scoreFourPosePose = new Pose(7.375,111.75,Math.toRadians(0));




    /** These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload, park;
    private PathChain intakeOne, intakeTwo ,push1, push2, push3, pickUp1, scoreSpecimen1, scoreSpecimen2,scoreSpecimen3, scoreSpecimen4, pickUp3,finalScore,grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

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
        scorePreload = new Path(new BezierLine(new Point(startPose),new Point(preloadPose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(),preloadPose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */
        intakeOne = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(preloadPose),new Point(endIntakeOnePose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        intakeTwo = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(preloadPose), new Point(endIntakeTwoPose)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

//        push1 = follower.pathBuilder()
//                .addPath(new BezierCurve(new Point(preloadPose), new Point(controlToPushOne1Pose),new Point(controltoPushOne2Pose),new Point(pushedSpecOnePose)))
//                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .build();
//
//        push2 = follower.pathBuilder()
//                .addPath(new BezierCurve(new Point(pushedSpecOnePose), new Point(controlToPushTwo1Pose),new Point(controlToPushTwo2Pose),new Point(endPush2Pose)))
//                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .build();
//
//        push3 = follower.pathBuilder()
//                .addPath(new BezierCurve(new Point(endPush2Pose), new Point(controlToPushThree1Pose),new Point(controlToPushThree2Pose),new Point(endPush3Pose)))
//                .setConstantHeadingInterpolation(Math.toRadians(0))
//                .build();

        pickUp1 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(endIntakeTwoPose), new Point(goToSpecControl),new Point(pickUpFirstSpec)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        scoreSpecimen1 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(pickUpSpec), new Point(controlToScoreSpec),new Point(scoreSpec1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        scoreSpecimen2 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(pickUpSpec), new Point(controlToScoreSpec),new Point(scoreSpec1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        scoreSpecimen3 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(pickUpSpec), new Point(controlToScoreSpec),new Point(scoreSpec1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        scoreSpecimen4 = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(pickUpSpec), new Point(controlToScoreSpec),new Point(scoreSpec1)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        pickUp3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scoreSpec1), new Point(pickUpSpec)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        /* This is our scorePickup1 PathChain.
        We are using a single path with a BezierLine, which is a straight line. */

    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path,
     * but does NOT wait for it to finish before moving on. */
    public void autonomousPathUpdate() { // think about using index to make repetitive actions during auto
        switch (pathState) {
            case 0:
                target = RobotConstants.Outtake.slideSpec;
                robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchSpecScore);
                robot.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageScore);
                robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                robot.intake.retractSlides();
                telemetry.addData("target", target);
                telemetry.addData("power", robot.outtake.getPower());
                //   robot.intake.setClawState(Intake.ClawState.CLOSED);
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()) {
                    robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                    target = RobotConstants.Outtake.slideGround;
                    robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff3);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff3);
                    robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchSubIn);
                    robot.intake.retractSlides();
                    /* Score Preload */
                    /* Since this is a pathChain, we can have Pedro hold the end point
                    while we are grabbing the sample */
                    // robot.intake.setClawState(Intake.ClawState.OPEN);
                    follower.followPath(intakeOne,true);
                    //      robot.outtake.PIDLoop(RobotConstants.Outtake.slideGround);
                    setPathState(2);
                }
                break;
            case 2:
                if (!follower.isBusy()) {
                    if (tempAutoSpec2) {
                        actionTimer.resetTimer();
                        tempAutoSpec2 = false;
                    }
                    robot.intake.setPosition(RobotConstants.Intake.slideAutoPickup);
//                    robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchSubIn);
                    robot.turret.setPosition(RobotConstants.Intake.turretAutoLeft);
                    robot.clawRotation.setPosition(RobotConstants.Intake.clawRotationAutoLeft);
                    if (robot.intakeSlide.getCurrentPosition() >= 570){
                        robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchIntake);
                        robot.intakeClaw.setPosition(RobotConstants.Outtake.clawClosed);
                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                    robot.intake.setPosition(RobotConstants.Intake.slideDrive);
//                    robot.turret.setPosition(RobotConstants.Intake.turretDropOff);
//                    robot.intakePitch.setPosition(RobotConstants.Intake.pitchDropOff);
//                    robot.clawRotation.setPosition(RobotConstants.Intake.clawRotation902);
//                    if (robot.intakeSlide.getCurrentPosition() <= 200){
//                        robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
//                    }
//                    // add delay
//                    robot.intakeClaw.setPosition(RobotConstants.Outtake.clawClosed);
//                    // add delay
//                    robot.intakeSlide.setTargetPosition(RobotConstants.Intake.slideDrive);
//                    robot.turret.setPosition(RobotConstants.Intake.turretDropOff);
//                    robot.intakePitch.setPosition(RobotConstants.Intake.pitchDropOff);
//                    robot.clawRotation.setPosition(RobotConstants.Intake.clawRotation902);
//                   // add delay
//                    robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                    // delay and repeat for the right one
//                    follower.followPath(intakeTwo,true);
                    //       robot.outtake.PIDLoop(RobotConstants.Outtake.slideSample);
//                    setPathState(3);
                }
                break;
            case 100:

                break;
            case 3:
//                if(!follower.isBusy()) {
//                    /* Score Sample */
//
//                    //      robot.intake.setClawState(Intake.ClawState.OPEN);
//                    //follower.followPath(push3,false);
//                    //       robot.outtake.PIDLoop(RobotConstants.Outtake.slideGround);
//                    setPathState(4);
//                }
                setPathState(4);
                break;
            case 4:
                if(!follower.isBusy()) {
                    /* Grab Sample */

                    follower.followPath(pickUp1,true);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);

                    setPathState(5);


                }
                break;
            case 5:
             //   if(pathTimer.getElapsedTimeSeconds() > 1) {}

                if(!follower.isBusy()) { //score 1
                    if (tempAutoSpec) {
                        actionTimer.resetTimer();
                        tempAutoSpec = false;
                    }
                    robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                    if(actionTimer.getElapsedTimeSeconds() > .2){
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchSpecScore);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        follower.followPath(scoreSpecimen1,true);
                       target = RobotConstants.Outtake.slideSpec;
                        tempAutoSpec = true;
                        setPathState(6);
                    }

                }
                break;
            case 6:
                if(!follower.isBusy()) {

                    robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                    target = RobotConstants.Outtake.slideGround;
                    robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                    follower.followPath(pickUp3,true);
                    actionTimer.resetTimer();

                    setPathState(7);
                }
                break;
            case 7://score 2
                if(!follower.isBusy()) {
                    if (tempAutoSpec) {
                        actionTimer.resetTimer();
                        tempAutoSpec = false;
                    }
                    robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                    if(actionTimer.getElapsedTimeSeconds() > .2) {
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchSpecScore);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        follower.followPath(scoreSpecimen2, true);
                        target = RobotConstants.Outtake.slideSpec;
                        tempAutoSpec = true;
                        setPathState(8);
                    }
                }
                break;
            case 8:
                if(!follower.isBusy()) {

                    robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                    target = RobotConstants.Outtake.slideGround;
                    robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                //    robot.intake.setClawState(Intake.ClawState.OPEN);
                    follower.followPath(pickUp3,true);
               //     robot.outtake.PIDLoop(RobotConstants.Outtake.slideGround);
                    setPathState(9);
                }
                break;
            case 9://score 3
                if(!follower.isBusy()) {
                    if (tempAutoSpec) {
                        actionTimer.resetTimer();
                        tempAutoSpec = false;
                    }
                    robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                    if(actionTimer.getElapsedTimeSeconds() > .2) {
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchSpecScore);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        follower.followPath(scoreSpecimen3, true);
                        target = RobotConstants.Outtake.slideSpec;
                        tempAutoSpec = true;
                        setPathState(10);
                    }
                }
                break;
            case 10:
                if(!follower.isBusy()) {
                    robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                    target = RobotConstants.Outtake.slideGround;
                    robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                    robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
                    robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecLongDropOff2);
               //     robot.intake.setClawState(Intake.ClawState.OPEN);
                    follower.followPath(pickUp3,true);
                //    robot.outtake.PIDLoop(RobotConstants.Outtake.slideGround);
                    setPathState(11);
                }
                break;
            case 11://score 3
                if(!follower.isBusy()) {
                    if (tempAutoSpec) {
                        actionTimer.resetTimer();
                        tempAutoSpec = false;
                    }
                    robot.outtake.setClawState(Outtake.ClawState.CLOSED);
                    if(actionTimer.getElapsedTimeSeconds() > .2) {
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchSpecScore);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchSpecScore);
                        follower.followPath(scoreSpecimen4, true);
                        target = RobotConstants.Outtake.slideSpec;
                        tempAutoSpec = true;
                        setPathState(12);
                    }
                }
                break;
            case 12:
                if(!follower.isBusy()) {
                    if (tempAutoSpec) {
                        actionTimer.resetTimer();
                        tempAutoSpec = false;
                    }
                    if(actionTimer.getElapsedTimeSeconds() > .2) {
                        robot.outtakeClaw.setPosition(RobotConstants.Outtake.clawOpen);
                        target = RobotConstants.Outtake.slideGround;
                        robot.outtakeLinkage.setPosition(RobotConstants.Outtake.linkageDrive);
                        robot.outtakePitch.setPosition(RobotConstants.Outtake.pitchDropOff);
                        robot.outtakeLPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
                        robot.outtakeRPitch.setPosition(RobotConstants.Outtake.LRPitchDropOff);
                        telemetry.addLine("idle");
                        telemetry.update();
                    }
                }
                break;
        }
    }

    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        CommandScheduler.getInstance().run();

        opmodeTimer.resetTimer();
        robot.outtake.PIDLoop(target);

        poseUpdater.update();
        dashboardPoseTracker.update();


        Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();



        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        CommandScheduler.getInstance().run();

        poseUpdater = new PoseUpdater(hardwareMap, FConstants.class, LConstants.class);

        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);


        robot.init(hardwareMap);
        robot.outtake.resetEncoder();

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        actionTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        robot.intakePitch.setPosition(RobotConstants.Intake.intakePitchDrive);
        //robot.intake.setClawState(Intake.ClawState.OPEN);
        //robot.turret.setPosition(RobotConstants.Intake.turretDrive);
        //robot.clawRotation.setPosition(RobotConstants.Intake.clawRotationDrive);

        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();

    }

    @Override
    public void start(){
        opmodeTimer.resetTimer();
        actionTimer.resetTimer();
        setPathState(0);

    }







}
