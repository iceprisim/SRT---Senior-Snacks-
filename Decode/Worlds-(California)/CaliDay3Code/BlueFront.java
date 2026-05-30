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
@Autonomous(name = "BlueFront", group = "Autonomous")
public class BlueFront extends LinearOpMode { //GonnaCuckTheAudience

    // Shooter Systems + Actions

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

        public class Shoot1 implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shooterLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                shooterRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

                shooterLeft.setVelocity(890);
                shooterRight.setVelocity(890);
                return false;
            }
        }

        public Action Shoot1() {
            return new Shoot1();
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

            rConv1.setDirection(DcMotorSimple.Direction.REVERSE);
            rConv2.setDirection(DcMotorSimple.Direction.REVERSE);
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
                shootAngle.setPosition(0.153);
                return false;
            }
        }
        public Action angle2() {
            return new angle2();
        }

        public class angle3 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(1);
                return false;
            }
        }
        public Action angle3(){return new angle3();}
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

    //auto starts here

    @Override
    public void runOpMode() throws InterruptedException {

        // init shooter
        Spinner spinner = new Spinner(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Conveyor conveyor = new Conveyor(hardwareMap);
        ShooterAngle shooterAngle = new ShooterAngle(hardwareMap);
        BlockControl blockcontrol = new BlockControl(hardwareMap);


        // start pose
        Pose2d beginPose = new Pose2d(-45, -52, Math.PI * 4.5 / 3.5);
        Pose2d afterPose = new Pose2d(-24, -24, Math.PI * 4.5 / 3.5);
        Pose2d Balls1D = new Pose2d(-6, -57, Math.PI/2);
        Pose2d Balls2D = new Pose2d(18, -50, Math.PI/2);
        Pose2d Balls3D = new Pose2d(40, -65, Math.PI/2);


        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder backwards = drive.actionBuilder(beginPose)
                .setTangent(Math.PI / 4)
                .splineToLinearHeading(new Pose2d(-24, -24, Math.PI * 4.5 / 3.5), Math.PI / 4.5)
                .waitSeconds(0.5)
                ;

        TrajectoryActionBuilder back = drive.actionBuilder(afterPose)
                .setTangent(Math.PI*0)
                .splineToLinearHeading(new Pose2d(-6, -32, Math.PI/2), Math.PI* 3 / 2) //align

                .setTangent(Math.PI*3/2)
                .splineToConstantHeading(new Vector2d(-6, -57), Math.PI* 3/2) //eat dem balls
                ;

        TrajectoryActionBuilder back2 = drive.actionBuilder(afterPose)
                .setTangent(Math.PI*0)
                .splineToLinearHeading(new Pose2d(18, -34, Math.PI/2 ), Math.PI*11/6)

                .setTangent(Math.PI*3/2)
                .splineToConstantHeading(new Vector2d(17, -68), Math.PI* 3/2, new TranslationalVelConstraint(200), null)
                .waitSeconds(0.1)

                .setTangent(Math.PI/2)
                .splineToConstantHeading(new Vector2d(18, -50), Math.PI/2)
                ;

        TrajectoryActionBuilder back3 = drive.actionBuilder(afterPose)
                .setTangent(Math.PI*0)
                .splineToLinearHeading(new Pose2d(40, -34, Math.PI/2), Math.PI*11/6,  new TranslationalVelConstraint(200), null)

                .setTangent(Math.PI*3/2)
                .splineToConstantHeading(new Vector2d(40, -65), Math.PI/2)
                ;

        TrajectoryActionBuilder reset = drive.actionBuilder(Balls1D) //shoot 1
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-24, -24, Math.PI*4.5/3.5 ), Math.PI/2)//reset to pos
                .waitSeconds(1)
                ;

        TrajectoryActionBuilder reset2 = drive.actionBuilder(Balls2D) //shoot 2
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-24, -24, Math.PI*4.5/3.5 ), Math.PI/2, new TranslationalVelConstraint(150), null)//reset to pos
                .waitSeconds(1)
                ;

        TrajectoryActionBuilder reset3 = drive.actionBuilder(Balls3D) //shoot 3 + park
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(-24, -24, Math.PI*4.5/3.5 ), Math.PI, new TranslationalVelConstraint(200), null)//reset to pos
                .waitSeconds(1)

                .setTangent(Math.PI)
                .splineToConstantHeading(new Vector2d(-40, -24), Math.PI)
                ;




        // wait to start
        waitForStart();
        if (isStopRequested()) return;

        // run RoadRunner + shooter actions
        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                shooter.Shoot1(),
                                backwards.build(),
                                //new SleepAction(0.1),
                                back.build(),
                                reset.build(),
                                back2.build(),
                                reset2.build(),
                                back3.build(),
                                reset3.build()
                        ),
                        new SequentialAction(
                                shooterAngle.angle1()
                        ),
                        new SequentialAction(
                                new SleepAction(0.8),
                                conveyor.convUp(),
                                new SleepAction(25),
                                conveyor.convStop()
                        ),
                        new SequentialAction(
                                new SleepAction(0.5),
                                spinner.intake(),
                                new SleepAction(25),
                                spinner.intakeStop()
                        ),
                        new SequentialAction(
                                blockcontrol.blockOff(),//shoot 1
                                new SleepAction(3.5),
                                blockcontrol.blockOn(),
                                new SleepAction(4.5),
                                blockcontrol.blockOff(),//shoot 2
                                new SleepAction(3),
                                blockcontrol.blockOn(),
                                new SleepAction(5),
                                blockcontrol.blockOff(),//shoot 3
                                new SleepAction(3),
                                blockcontrol.blockOn(),
                                new SleepAction(5),
                                blockcontrol.blockOff()//shoot 4
                        )
                )

        );
    }
}