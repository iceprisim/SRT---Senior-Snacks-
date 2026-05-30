package org.firstinspires.ftc.teamcode.Auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "VNBlueBack", group = "Autonomous")
public class VNBlueBack extends LinearOpMode { //BackshotsTillBlueBalls

    //-----------Shooter Subsystem-----------
    public class Shooter {
        private DcMotorEx shooterLeft;
        private DcMotorEx shooterRight;

        public Shooter(HardwareMap hardwareMap) {
            shooterLeft = hardwareMap.get(DcMotorEx.class, "red");
            shooterRight = hardwareMap.get(DcMotorEx.class, "orange");
            shooterRight.setDirection((DcMotorSimple.Direction.REVERSE));
            shooterLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            shooterRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        }
        public class Shoot1 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                shooterRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

                shooterLeft.setVelocity(1370); //1800
                shooterRight.setVelocity(1370); //1800
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shooter.Shoot1();
        }

        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                shooterRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

                shooterLeft.setVelocity(1500);
                shooterRight.setVelocity(1500);
                return false;
            }
        }
        public Action Shoot2 () {
            return new Shooter.Shoot2();
        }

        public class Shoot3 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                shooterRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

                shooterLeft.setVelocity(1350);
                shooterRight.setVelocity(1350);
                return false;
            }
        }
        public Action Shoot3 () {
            return new Shooter.Shoot3();
        }
    }


    //-----------Intake spinner Subsystem-----------
    public class Spinner {
        private DcMotorEx spinnerLeft;
        private DcMotorEx spinnerRight;

        public Spinner(HardwareMap hardwareMap){
            spinnerLeft = hardwareMap.get(DcMotorEx.class, "pink");
            spinnerRight = hardwareMap.get(DcMotorEx.class, "blue");

            spinnerRight.setDirection(DcMotorEx.Direction.REVERSE);
            //spinnerLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        }
        public class intake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                spinnerLeft.setPower(1);
                spinnerRight.setPower(1);

                return false;
            }
        }
        public Action intake(){
            return new Spinner.intake();
        }

        public class intakeStop implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                spinnerLeft.setPower(0);
                spinnerRight.setPower(0);

                return false;
            }
        }
        public Action intakeStop(){
            return new Spinner.intakeStop();
        }
    }

    //-----------Conveyour Subsystem-----------
    public class Conveyor {
        private CRServo lconv1;
        private CRServo lconv2;

        private CRServo rconv1;
        private CRServo rconv2;

        public Conveyor(HardwareMap hardwareMap){
            lconv1 = hardwareMap.get(CRServo.class, "lconv1");
            lconv2 = hardwareMap.get(CRServo.class, "lconv2");

            rconv1 = hardwareMap.get(CRServo.class, "rconv1");
            rconv2 = hardwareMap.get(CRServo.class, "rconv2");

            rconv1.setDirection(CRServo.Direction.REVERSE);
            rconv2.setDirection(CRServo.Direction.REVERSE);

        }
        public class convUp implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lconv1.setPower(0.36);
                lconv2.setPower(0.36);
                rconv1.setPower(0.36);
                rconv2.setPower(0.36);
                return false;
            }
        }
        public Action convUp () {
            return new Conveyor.convUp();
        }

        public class convStop implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lconv1.setPower(0);
                lconv2.setPower(0);
                rconv1.setPower(0);
                rconv2.setPower(0);
                return false;
            }
        }
        public Action convRev () {
            return new Conveyor.convStop();
        }

        public class convRev implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lconv1.setPower(-1);
                lconv2.setPower(-1);
                rconv1.setPower(-1);
                rconv2.setPower(-1);
                return false;
            }
        }
        public Action convStop () {
            return new Conveyor.convStop();
        }
    }

    //-----------Shooter Angle Subsystem-----------
    public class ShooterAngle {
        private Servo shootAngle;
        public ShooterAngle(HardwareMap hardwareMap){
            shootAngle = hardwareMap.get(Servo.class, "ShootAngle");
        }

        public class angle1 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.179); //0.184
                return false;
            }
        }
        public Action angle1() {
            return new ShooterAngle.angle1();
        }


        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.181);
                return false;
            }
        }
        public Action angle2() {
            return new ShooterAngle.angle2();
        }

        public class angle3 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.18);
                return false;
            }
        }
        public Action angle3() {
            return new ShooterAngle.angle2();
        }
    }

    //-----------C-block Subsystem-----------
    public class BlockControl {
        private Servo block;
        public BlockControl(HardwareMap hardwareMap){
            block = hardwareMap.get(Servo.class, "block");
            block.setDirection(Servo.Direction.REVERSE);
        }

        public class blockOff implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                block.setPosition(0);
                return false;
            }
        }
        public Action blockOff() {
            return new BlockControl.blockOff();
        }
        public class blockOn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                block.setPosition(0.5);
                return false;
            }
        }
        public Action blockOn() {
            return new BlockControl.blockOn();
        }
    }

    // -------------------------
    // Main Autonomous
    // -------------------------
    @Override
    public void runOpMode() throws InterruptedException {

        // init shooter
        Spinner spinner = new Spinner(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Conveyor conveyor = new Conveyor(hardwareMap);
        ShooterAngle shooterAngle = new ShooterAngle(hardwareMap);
        BlockControl blockcontrol = new BlockControl(hardwareMap);

        // start pose
        Pose2d zeroPose = new Pose2d(new Vector2d(0, 0), 0);//0
        Pose2d beginPose = new Pose2d(new Vector2d(0, -60), Math.PI/2);//starts
        Pose2d postshootPose = new Pose2d (new Vector2d(0, -55),1.95);//after shooting
        Pose2d hpmovePose = new Pose2d (new Vector2d(-40, -45), 0);//after moving to hp
        Pose2d hpeatPose = new Pose2d (new Vector2d(-46, -60), Math.PI/6);//after eatig hp
        Pose2d hplastPose = new Pose2d (new Vector2d(-46, -60), Math.PI/6);//after eatig the last hp


        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder preloaded = drive.actionBuilder(beginPose)
                .setTangent(Math.PI/2)
                .lineToY(-45)
                .splineToSplineHeading(new Pose2d(0, -50, 1.95), Math.PI / 2)
                ;

        TrajectoryActionBuilder hpmoveballs = drive.actionBuilder(postshootPose)
                .splineToSplineHeading(new Pose2d(0, -48, 0), Math.PI)

                //move to human player zone
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-44, -48, Math.PI/6), Math.PI*2/3
                        , new TranslationalVelConstraint(200),null )
                ;

        TrajectoryActionBuilder hpeatballs = drive.actionBuilder(hpmovePose)
                //line up and move down
                .splineToLinearHeading(new Pose2d(-45.5, -48, Math.PI/6), Math.PI
                        , new TranslationalVelConstraint(200),null )
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-45.5, -60, Math.PI/6), 3*Math.PI/2
                        , new TranslationalVelConstraint(40),null )
                ;

        TrajectoryActionBuilder hplastballs = drive.actionBuilder(hpeatPose)
                .splineToLinearHeading(new Pose2d(-40, -60, 0), Math.PI*7/4
                        , new TranslationalVelConstraint(200),null )
                .setTangent(Math.PI*6/7)
                .splineToLinearHeading(new Pose2d(-48, -60, 0), Math.PI
                        , new TranslationalVelConstraint(30.0),null )

                ;

        TrajectoryActionBuilder hppewballs = drive.actionBuilder(hplastPose)
                .setTangent((11*Math.PI)/6)
                .splineToLinearHeading(new Pose2d(-2, -55, 1.95), Math.PI*3/2
                        , new TranslationalVelConstraint(200),null )
                ;

        TrajectoryActionBuilder park = drive.actionBuilder(postshootPose)
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-20, -55, Math.PI/2), Math.PI
                        , new TranslationalVelConstraint(200),null )
                ;

        // wait to start
        waitForStart();
        if (isStopRequested()) return;

        // run RoadRunner + shooter actions
        Actions.runBlocking(
                new ParallelAction(
                        //c-block
                        new SequentialAction(
                                //preloaded
                                blockcontrol.blockOn(),
                                new SleepAction(1),
                                blockcontrol.blockOff(),
                                //hp
                                new SleepAction(3.2),
                                blockcontrol.blockOn(),
                                new SleepAction(10),
                                blockcontrol.blockOff(),
                                //secondeatballs
                                new SleepAction(3.5),
                                blockcontrol.blockOn(),
                                new SleepAction(5.2),
                                blockcontrol.blockOff()
                        ),

                        //shooters
                        new SequentialAction(
                                shooter.Shoot1(),
                                shooterAngle.angle1()
                                //new SleepAction(6),
                                //shooterAngle.angle2(),
                                //new SleepAction(6),
                                //shooterAngle.angle3()
                        ),

                        //shooters and conveyour
                        new SequentialAction(
                                new SleepAction(1.7),
                                spinner.intake(),
                                conveyor.convUp()
                        ),


                        //trajectiores
                        new SequentialAction(
                                preloaded.build(),//4 secs
                                new SleepAction(2.1),
                                hpmoveballs.build(),
                                hpeatballs.build(),
                                hplastballs.build(),
                                hplastballs.build(),
                                hppewballs.build(), //21 secs
                                new SleepAction(2.1),
                                park.build()

                        )
                )
        );
    }
}