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

import org.firstinspires.ftc.robotcontroller.external.samples.SensorHuskyLens;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "Red9FFT", group = "Autonomous")
public class Red9FFT extends LinearOpMode {

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
                shooterLeft.setPower(0.415);
                shooterRight.setPower(0.415);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shoot1();
        }
        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.41);
                shooterRight.setPower(0.41);
                return false;
            }
        }
        public Action Shoot2 () {
            return new Shoot2();
        }

        public class Shoot3 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.41);
                shooterRight.setPower(0.41);
                return false;
            }
        }
        public Action Shoot3 () {
            return new Shoot3();
        }

    }

    public class Lift {
        private CRServo flLift;
        private CRServo frLift;
        private CRServo blLift;
        private CRServo brLift;

        public Lift(HardwareMap hardwareMap) {
            flLift = hardwareMap.get(CRServo.class, "flLift");
            frLift = hardwareMap.get(CRServo.class, "flLift");
            blLift = hardwareMap.get(CRServo.class, "blLift");
            brLift = hardwareMap.get(CRServo.class, "brLift");

            brLift.setDirection(DcMotorSimple.Direction.REVERSE);
            blLift.setDirection(DcMotorSimple.Direction.REVERSE);

        }

            public class raise implements Action {
                @Override
                public boolean run(@NonNull TelemetryPacket packet){
                    brLift.setPower(0.57);
                    blLift.setPower(0.57);
                    frLift.setPower(0.57);
                    flLift.setPower(0.57);
                    return false;
                }

            }
            public Action raise(){return new raise();
            }

        public class boost implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                brLift.setPower(0.8);
                blLift.setPower(0.8);
                frLift.setPower(0.8);
                flLift.setPower(0.8);
                return false;
            }

        }
        public Action boost(){return new boost();
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
            return new intake();
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
            return new intakeStop();
        }

    }
    public class Conveyor {
        private CRServo lConv1;
        private CRServo lConv2;
        private CRServo rConv1;

        private CRServo rConv2;

        public Conveyor(HardwareMap hardwareMap){
            lConv1 = hardwareMap.get(CRServo.class, "lconv1");
            lConv2 = hardwareMap.get(CRServo.class, "lconv2");
            rConv1 = hardwareMap.get(CRServo.class, "rconv1");
            rConv2 = hardwareMap.get(CRServo.class, "rconv2");

            lConv1.setDirection(DcMotorSimple.Direction.REVERSE);
            lConv2.setDirection(DcMotorSimple.Direction.REVERSE);
        }
        public class convUp implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lConv1.setPower(1);
                lConv2.setPower(1);
                rConv1.setPower(1);
                rConv2.setPower(1);
                return false;
            }
        }

        public Action convUp() {
            return new convUp();
        }
        public class convStop implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                lConv1.setPower(0);
                lConv2.setPower(0);
                rConv1.setPower(0);
                rConv2.setPower(0);
                return false;
            }
        }
        public Action convStop() {
            return new convStop();
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
                shootAngle.setPosition(0.15);
                return false;
            }
        }
        public Action angle1() {
            return new angle1();
        }

        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.15);
                return false;
            }
        }
        public Action angle2() {
            return new angle2();
        }

        public class angle3 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.15);
                return false;
            }
        }
        public Action angle3() {
            return new angle3();
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
            return new blockOff();
        }

        public class blockOn implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                block.setPosition(0.35);
                return false;
            }

        }

        public Action blockOn(){return new blockOn();}


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
        Lift lift = new Lift(hardwareMap);



        // start pose
        Pose2d beginPose = new Pose2d(new Vector2d(0, 0), Math.PI /4);
        Pose2d afterPose = new Pose2d(new Vector2d(0, 0), Math.PI/4);
        Pose2d backPose = new Pose2d(new Vector2d(-20, -43), 3.25);

        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder backwards = drive.actionBuilder(beginPose)
                .splineToConstantHeading(new Vector2d(-25, -25 ), -Math.PI/4)
                .waitSeconds(4)
                .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-20, -43, 3.25), Math.PI);

        TrajectoryActionBuilder back = drive.actionBuilder(backPose)
                .lineToX(25)
                .waitSeconds(0.1)
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-25, -10, 0.6), Math.PI/2);

        TrajectoryActionBuilder getBalls2 = drive.actionBuilder(new Pose2d(-20, -20, Math.PI/4))
                .setTangent(Math.PI*3/2)
                .splineToSplineHeading(new Pose2d(-20, -65, Math.PI), Math.PI*3/4);

        TrajectoryActionBuilder back2 = drive.actionBuilder(new Pose2d(-20, -65, Math.PI))
                .lineToX(28)
                 .waitSeconds(0.1)
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-33, -10, Math.PI/6), Math.PI/2);










        // wait to start
        waitForStart();
        if (isStopRequested()) return;

        // run RoadRunner + shooter actions
        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                new SequentialAction(
                                        shooter.Shoot1(),
                                        backwards.build(),
                                        new SleepAction(0.1),
                                        back.build(),
                                        new SleepAction(4),
                                        getBalls2.build(),
                                        new SleepAction(0.1),
                                        back2.build(),
                                        new SleepAction(6)





                                ), new SequentialAction(
                                shooterAngle.angle1(),
                                new SleepAction(8),
                                shooterAngle.angle2(),
                                new SleepAction(10),
                                shooterAngle.angle3()

                        ),
                                new SequentialAction(
                                        new SleepAction(2),
                                        conveyor.convUp(),
                                        new SleepAction(7.5),
                                        conveyor.convStop(),
                                        new SleepAction(4),
                                        conveyor.convUp(),
                                        new SleepAction(10),
                                        conveyor.convStop(),
                                        new SleepAction(4),
                                        conveyor.convUp()

                                ),
                                new SequentialAction(
                                        new SleepAction(2.2),
                                        spinner.intake(),
                                        new SleepAction(7.5),
                                        spinner.intakeStop(),
                                        new SleepAction(4),
                                        spinner.intake(),
                                        new SleepAction(10),
                                        spinner.intakeStop(),
                                        new SleepAction(4),
                                        spinner.intake()
                                ),
                                new SequentialAction(
                                        blockcontrol.blockOff(),
                                        new SleepAction(6),
                                        blockcontrol.blockOn(),
                                        new SleepAction(6),
                                        blockcontrol.blockOff(),
                                        new SleepAction(7),
                                        blockcontrol.blockOn(),
                                        new SleepAction(6.5),
                                        blockcontrol.blockOff()
                                ),
                                new SequentialAction(
                                        shooter.Shoot1(),
                                        new SleepAction(8),
                                        shooter.Shoot2(),
                                        new SleepAction(10),
                                        shooter.Shoot3()
                                )




                        )
                )
        );
    }
}
