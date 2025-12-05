package org.firstinspires.ftc.teamcode.Autonomous;
// Imports
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "EpicRedStuff", group = "Autonomous")
public class Warcrimes5FGenevaCon extends LinearOpMode {

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
                shooterLeft.setPower(0.75);
                shooterRight.setPower(0.77);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shoot1();
        }

        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0);
                shooterRight.setPower(0);
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

        public class convstop implements Action{
            @Override
            public boolean run (@NonNull TelemetryPacket packet){
                Conv1.setPower(0);
                Conv2.setPower(0);
                return false;
            }
        }

        public Action convstop() {
            return new convstop();
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
                shootAngle.setPosition(0.27);
                return false;
            }
        }
        public Action angle1() {
            return new angle1();
        }
    }


    @Override
    public void runOpMode() {
        //Create Positions for the robot to reference from
        Pose2d beginPose = new Pose2d(-35, 55, Math.PI/2);
        Pose2d imAbigBoy = new Pose2d(-23, 30, Math.PI*2/3); //hopefully universal position
        Pose2d imAbigBoy2 = new Pose2d(0, 0, Math.PI*2/3);

        Pose2d Collect1 = new Pose2d(-12, 30, Math.PI*3/2);
        Pose2d Gather1 = new Pose2d(-12, 50, Math.PI*3/2);


        /*
        define mechanum drive and the other harware maps/ imports
        */

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Spinner spinner = new Spinner(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Conveyor conveyor = new Conveyor(hardwareMap);
        ShooterAngle shooterAngle = new ShooterAngle(hardwareMap);
        ElapsedTime runtime = new ElapsedTime();

        TrajectoryActionBuilder Align1 = drive.actionBuilder(beginPose)
                .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-23, 30, Math.PI*3/4), Math.PI*5/3) // shoot first 2/3 preloaded balls from start
                .waitSeconds(3.5);

        TrajectoryActionBuilder Align2 = drive.actionBuilder(Gather1) //reset pos
                .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-25, 30, Math.PI*2/3), Math.PI) //pos for shooting balls 4 -> 6(may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(10);

        TrajectoryActionBuilder Balls1 = drive.actionBuilder(imAbigBoy)
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-12, 30, Math.PI*3/2), 0) // prep pos for balls 1->3
                .waitSeconds(0);

        TrajectoryActionBuilder Balls2 = drive.actionBuilder(imAbigBoy)
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(10, 40, Math.PI*3/2), 0) // prep pos for balls 1->3
                .waitSeconds(0);


        TrajectoryActionBuilder BCollect = drive.actionBuilder(Collect1)
                .lineToY(60) // collect all 3 balls
                .waitSeconds(0);


        TrajectoryActionBuilder ImFinishing = drive.actionBuilder(imAbigBoy)
                .setTangent(Math.PI*7/6)
                .splineToConstantHeading(new Vector2d(-34, 14), Math.PI/4) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(0);


        //put drive action here :)
        while (!isStopRequested() && !opModeIsActive()) {
            waitForStart();
            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    new SequentialAction(
                                            Align1.build(), //procede to shoot
                                            Balls1.build(), //line 1 balls prep
                                            BCollect.build(),
                                            Align2.build()
                                            //Balls2.build()

                                            //ImFinishing.build()

                                    ),
                                    new SequentialAction(
                                            shooterAngle.angle1(),
                                            new SleepAction(0.5),
                                            shooter.Shoot1(),
                                            new SleepAction(0.5),
                                            conveyor.convUp(),
                                            spinner.intake(),
                                            new SleepAction(4),
                                            conveyor.convstop(),
                                            new SleepAction(1.5),
                                            shooter.Shoot2(),
                                            new SleepAction(5.5),
                                            conveyor.convUp(),
                                            shooter.Shoot1()

                                    ),
                                    new SequentialAction( /*
                                            spin.PewPew() */
                                    )
                            )

                    )
            );
        }
    }




}
