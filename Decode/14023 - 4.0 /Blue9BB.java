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
@Autonomous(name = "Blue9BB", group = "Autonomous")
public class Blue9BB extends LinearOpMode {

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
                shooterLeft.setPower(0.533);
                shooterRight.setPower(0.533);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shooter.Shoot1();
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
            return new Shooter.Shoot2();
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
        public Action convStop () {
            return new Conveyor.convStop();
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
                shootAngle.setPosition(0.171);
                return false;
            }
        }
        public Action angle1() {
            return new ShooterAngle.angle1();
        }


        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.17);
                return false;
            }
        }
        public Action angle2() {
            return new ShooterAngle.angle2();
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
        Pose2d postprePose = new Pose2d (new Vector2d(0, -42),2.08);//after shooting preloaded (3)
        Pose2d posteat1Pose = new Pose2d (new Vector2d(-55, -32),0);//after intaking first (3)
        Pose2d postshoot1Pose = new Pose2d (new Vector2d(0, -60), 1.9);//after shooting first (6)
        Pose2d posteat2Pose = new Pose2d (new Vector2d(-45, -24),0);//after intaking first (3)


        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder preloaded = drive.actionBuilder(beginPose)
                .setTangent(Math.PI/2)
                .lineToY(-45)
                .splineToSplineHeading(new Pose2d(0, -50, 1.9), Math.PI / 2);

        TrajectoryActionBuilder firsteatballs = drive.actionBuilder(postprePose)
                .splineToLinearHeading(new Pose2d(-15, -34, 0), Math.PI )
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-48, -34, 0), Math.PI );

        TrajectoryActionBuilder firstpewballs = drive.actionBuilder(posteat1Pose)
                .splineToSplineHeading(new Pose2d(0, -55, 1.92), -1.5);

        TrajectoryActionBuilder secondeatballs = drive.actionBuilder(postshoot1Pose)
                .splineToLinearHeading(new Pose2d(-15, -9.2, 0), Math.PI )
                .waitSeconds(0.1)
                .splineToLinearHeading(new Pose2d(-48, -9.2, 0), Math.PI );

        TrajectoryActionBuilder secondpewballs = drive.actionBuilder(posteat2Pose)
                .splineToLinearHeading(new Pose2d(0, -55, 1.92), Math.PI*3/2);

//        TrajectoryActionBuilder thirdeatballs = drive.actionBuilder(postshoot1Pose)
//                .splineToLinearHeading(new Pose2d(-50, -35, 1.6), Math.PI )
//                .waitSeconds(0.1)
//                .splineToLinearHeading(new Pose2d(-50, -55, 1.6), Math.PI /2);
//
//        TrajectoryActionBuilder thirdpewballs = drive.actionBuilder(posteat2Pose)
//                .splineToLinearHeading(new Pose2d(0, -55, 1.92), Math.PI*3/2);

        TrajectoryActionBuilder park = drive.actionBuilder(postshoot1Pose)
                .splineToLinearHeading(new Pose2d(0, -30, Math.PI/2), Math.PI*3/2);




        // wait to start
        waitForStart();
        if (isStopRequested()) return;

        // run RoadRunner + shooter actions
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                new SequentialAction(
                                        //shoot first 3
                                        blockcontrol.blockOn(),
                                        conveyor.convStop(),
                                        spinner.intakeStop(),
                                        shooter.Shoot1(),
                                        shooterAngle.angle1(),
                                        preloaded.build(),
                                        blockcontrol.blockOff(),
                                        new SleepAction(0.5),
                                        conveyor.convUp(),
                                        spinner.intake(),
                                        new SleepAction(3),

                                        //shoot nxt 3 (6)
                                        blockcontrol.blockOn(),
                                        firsteatballs.build(),
                                        new SleepAction(0.5),
                                        conveyor.convStop(),
                                        spinner.intakeStop(),
                                        firstpewballs.build(),
                                        blockcontrol.blockOff(),
                                        new SleepAction(0.5),
                                        conveyor.convUp(),
                                        spinner.intake(),
                                        new SleepAction(3),

                                        //shoot nxt 3 (9)
                                        blockcontrol.blockOn(),
                                        secondeatballs.build(),
                                        new SleepAction(0.5),
                                        conveyor.convStop(),
                                        spinner.intakeStop(),
                                        secondpewballs.build(),
                                        blockcontrol.blockOff(),
                                        new SleepAction(0.6),
                                        conveyor.convUp(),
                                        spinner.intake(),
                                        new SleepAction(3),

                                        //escape
                                        park.build()


                                )

                        )
                )
        );
    }
}
