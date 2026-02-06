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
@Autonomous(name = "Blue6dot5BB", group = "Autonomous")
public class Blue6dot5BB extends LinearOpMode {

    // -------------------------
    // Shooter Subsystem
    // -------------------------
    public class Shooter {
        private DcMotorEx shooterLeft;
        private DcMotorEx shooterRight;

        public Shooter(HardwareMap hardwareMap){
            shooterLeft = hardwareMap.get(DcMotorEx.class, "red");
            shooterRight = hardwareMap.get(DcMotorEx.class, "orange");

            shooterRight.setDirection((DcMotorSimple.Direction.REVERSE));
        }
        public class Shoot1 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.55);
                shooterRight.setPower(0.55);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Blue6dot5BB.Shooter.Shoot1();
        }

        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.55);
                shooterRight.setPower(0.55);
                return false;
            }
        }
        public Action Shoot2 () {
            return new Blue6dot5BB.Shooter.Shoot2();
        }
    }


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
            return new Blue6dot5BB.Spinner.intake();
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
            return new Blue6dot5BB.Spinner.intakeStop();
        }
    }
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
        }
        public class convUp implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lconv1.setPower(-1);
                lconv2.setPower(-1);
                rconv1.setPower(1);
                rconv2.setPower(1);
                return false;
            }
        }
        public Action convUp () {
            return new Blue6dot5BB.Conveyor.convUp();
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
        public Action convStop () {
            return new Blue6dot5BB.Conveyor.convStop();
        }
    }
    public class ShooterAngle {
        private Servo shootAngle;
        public ShooterAngle(HardwareMap hardwareMap){
            shootAngle = hardwareMap.get(Servo.class, "ShootAngle");
        }

        public class angle1 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.17);
                return false;
            }
        }
        public Action angle1() {
            return new Blue6dot5BB.ShooterAngle.angle1();
        }


        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.17);
                return false;
            }
        }
        public Action angle2() {
            return new Blue6dot5BB.ShooterAngle.angle2();
        }
    }
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
            return new Blue6dot5BB.BlockControl.blockOff();
        }
        public class blockOn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                block.setPosition(0.5);
                return false;
            }
        }
        public Action blockOn() {
            return new Blue6dot5BB.BlockControl.blockOn();
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
        Pose2d postprePose = new Pose2d (new Vector2d(0, -50),2.05);//after shooting preloaded (3)
        Pose2d posteat1Pose = new Pose2d (new Vector2d(-58, -24),0);//after intaking first (3)
        Pose2d postshootPose = new Pose2d (new Vector2d(10, -60), 2.02);//after shooting first (6)

        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder preloaded = drive.actionBuilder(beginPose)
                .setTangent(Math.PI/2)
                .lineToY(-35)
                .waitSeconds(0.2)
                .splineToSplineHeading(new Pose2d(0, -42, 2.087), Math.PI / 2);

        TrajectoryActionBuilder firsteatballs = drive.actionBuilder(postprePose)
                .splineToLinearHeading(new Pose2d(-15, -26.5, -0.1), Math.PI )
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-55, -26.5, 0), Math.PI )
                .waitSeconds(0.1);

        TrajectoryActionBuilder firstpewballs = drive.actionBuilder(posteat1Pose)
                .splineToSplineHeading(new Pose2d(0, -60, 1.92), -1.5);

        TrajectoryActionBuilder secondeatballs = drive.actionBuilder(postshootPose)
                .splineToLinearHeading(new Pose2d(-15, -14, 0), (Math.PI*3)/2)
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-52, -14, 0), Math.PI)
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-15, -14, 0), Math.PI);



        // wait to start
        waitForStart();
        if (isStopRequested()) return;

        // run RoadRunner + shooter actions
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                new SequentialAction(
                                        shooter.Shoot1(),
                                        preloaded.build(),
                                        new SleepAction(4),
                                        firsteatballs.build(),
                                        new SleepAction(0.1),
                                        shooter.Shoot2(),
                                        firstpewballs.build(),
                                        new SleepAction(5),
                                        secondeatballs.build()

                                        ), new SequentialAction(
                                        shooterAngle.angle1(),
                                        new SleepAction(10),
                                        shooterAngle.angle2()
                                ),
                                new SequentialAction(
                                        new SleepAction(2.8),
                                        conveyor.convUp(),
                                        new SleepAction(8.8),
                                        conveyor.convStop(),
                                        new SleepAction(5),
                                        conveyor.convUp(),
                                        new SleepAction(9),
                                        conveyor.convStop(),
                                        new SleepAction(10)
                                ),

                                new SequentialAction(
                                        new SleepAction(4),
                                        spinner.intake(),
                                        new SleepAction(8),
                                        spinner.intakeStop(),
                                        new SleepAction(5.5),
                                        spinner.intake(),
                                        new SleepAction(9),
                                        spinner.intakeStop(),
                                        new SleepAction(10)
                                ),

                                new SequentialAction(
                                        blockcontrol.blockOff(),
                                        new SleepAction(8),
                                        blockcontrol.blockOn(),
                                        new SleepAction(8),
                                        blockcontrol.blockOff(),
                                        new SleepAction(7.5),
                                        blockcontrol.blockOn()

                                )

                        )
                )
        );
    }
}