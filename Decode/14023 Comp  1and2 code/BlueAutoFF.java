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
@Autonomous(name = "BlueAutoFF", group = "Autonomous")
public class BlueAutoFF extends LinearOpMode {

    // -------------------------
    // Shooter Subsystem
    // -------------------------
    public class Shooter {
        private DcMotorEx shooterLeft;
        private DcMotorEx shooterRight;

        public Shooter(HardwareMap hardwareMap){
            shooterLeft = hardwareMap.get(DcMotorEx.class, "brown");
            shooterRight = hardwareMap.get(DcMotorEx.class, "pink");

            shooterRight.setDirection((DcMotorSimple.Direction.REVERSE));
        }
        public class Shoot1 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.61);
                shooterRight.setPower(0.61);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shoot1();
        }
        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.66);
                shooterRight.setPower(0.66);
                return false;
            }
        }
        public Action Shoot2 () {
            return new Shoot2();
        }

    }


    public class Spinner {
        private DcMotorEx spinnerLeft;
        private DcMotorEx spinnerRight;

        public Spinner(HardwareMap hardwareMap){
            spinnerLeft = hardwareMap.get(DcMotorEx.class, "gray");
            spinnerRight = hardwareMap.get(DcMotorEx.class, "teal");

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
        private CRServo Conv1;
        private CRServo Conv2;

        public Conveyor(HardwareMap hardwareMap){
            Conv1 = hardwareMap.get(CRServo.class, "conv1");
            Conv2 = hardwareMap.get(CRServo.class, "conv2");
        }
        public class convUp implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                Conv1.setPower(1);
                Conv2.setPower(1);
                return false;
            }
        }

        public Action convUp() {
            return new convUp();
        }
        public class convStop implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                Conv1.setPower(0);
                Conv2.setPower(0);
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
                shootAngle.setPosition(0.18);
                return false;
            }
        }
        public Action angle1() {
            return new angle1();
        }

        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.27);
                return false;
            }
        }
        public Action angle2() {
            return new angle2();
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



        // start pose
        Pose2d beginPose = new Pose2d(new Vector2d(-50, 35), Math.PI *3/4);
        Pose2d afterPose = new Pose2d(new Vector2d(0, -30), 3.14/2);

        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder backwards = drive.actionBuilder(beginPose)
                //.splineToSplineHeading(new Pose2d(3.5, 10, 0.42), Math.PI / 2);
                .setTangent(Math.PI*3/4)
                .splineToLinearHeading(new Pose2d(-20, 10, 2.6), 2.6)
                .waitSeconds(5.5)
                .setTangent(Math.PI*3/4)
                .splineToLinearHeading(new Pose2d(-20, -33, 6.2),6.2)
                .lineToX(-58)
                .waitSeconds(0.2)
                .setTangent(6.2)
                .splineToLinearHeading(new Pose2d(-15, -5, Math.PI*3/4), Math.PI*3/4);



        //TrajectoryActionBuilder reset = drive.actionBuilder(afterPose)
               // .setTangent(0)
               // .splineToSplineHeading(new Pose2d(20, -30, 3.14/2),0);

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
                                       new SleepAction(8)



                                ), new SequentialAction(
                                shooterAngle.angle1()
                        ),
                                new SequentialAction(
                                        new SleepAction(2.5),
                                        conveyor.convUp(),
                                        new SleepAction(9),
                                        conveyor.convStop(),
                                        new SleepAction(4),
                                        conveyor.convUp()
                                ),
                                new SequentialAction(
                                        new SleepAction(3.5),
                                        spinner.intake(),
                                        new SleepAction(9),
                                        spinner.intakeStop(),
                                        new SleepAction(5),
                                        spinner.intake()
                                ),
                                new SequentialAction(
                                     new SleepAction(8.3),
                                     shooterAngle.angle2(),
                                     shooter.Shoot2()
                                )


                        )
                )
        );
    }
}