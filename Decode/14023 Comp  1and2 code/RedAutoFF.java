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
@Autonomous(name = "RedAutoFF", group = "Autonomous")
public class RedAutoFF extends LinearOpMode {

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
                shooterLeft.setPower(0.63);
                shooterRight.setPower(0.63);
                return false;
            }
        }
        public Action Shoot1 () {
            return new Shoot1();
        }

        public class Shoot2 implements Action{

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(0.6);
                shooterRight.setPower(0.6);
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

        public class intakeOff implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinnerLeft.setPower(0);
                spinnerRight.setPower(0);

                return false;
            }
        }
        public Action intakeOff(){return new intakeOff();}
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
        public Action convStop(){return new convStop();}
    }
    public class ShooterAngle {
        private Servo shootAngle;

        public ShooterAngle(HardwareMap hardwareMap){
            shootAngle = hardwareMap.get(Servo.class, "ShootAngle");
        }

        public class angle1 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.19);
                return false;
            }
        }
        public Action angle1() {
            return new angle1();
        }

        public class angle2 implements Action{
            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shootAngle.setPosition(0.24);
                return false;
            }

        }
        public Action angle2(){return new angle2();}
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
        Pose2d beginPose = new Pose2d(new Vector2d(50, 35), Math.PI /4);
        Pose2d afterPose = new Pose2d(new Vector2d(0, -30), 3.14/2);
        Pose2d nextPose = new Pose2d(new Vector2d(0, 0), 0);


        // build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        // build trajectories
        TrajectoryActionBuilder backwards = drive.actionBuilder(beginPose)
                .setTangent(Math.PI/4)
                .splineToConstantHeading(new Vector2d(20, 15), Math.PI/4)
                .waitSeconds(5.5)

                .setTangent(Math.PI/4)
                .splineToLinearHeading(new Pose2d(20, 2, 3.2),3.2 )

                .lineToX(53)
                .waitSeconds(0.7)

                .setTangent(3.2)
                .splineToLinearHeading(new Pose2d(-15, 25, Math.PI/4), Math.PI/4);








                //.setTangent(3.14*3/2)
               // .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI*5/3) // shoot first 2/3 preloaded balls
               // .waitSeconds(2.5);
/*
section 1
 */
               // .setTangent(0)
               // .splineToLinearHeading(new Pose2d(-12, 30, Math.PI*3/2), 0) // prep pos for balls 1->3
               // .waitSeconds(0)

              //  .lineToY(56) // collect all 3 balls
               // .waitSeconds(0)
/*
section 2
 */

               // .setTangent(Math.PI*3/2)
               // .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
               // .waitSeconds(2.5)
/*
section 3
 */

               // .setTangent(0)
                //.splineToLinearHeading(new Pose2d(12, 30, Math.PI*3/2), 0) // prep pos for balls 4->6
               // .waitSeconds(0)

                //.lineToY(56) // collect all 3 balls
               // .waitSeconds(0)
/*
section 4
 */

                //.setTangent(Math.PI*3/2)
                //.splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                //.waitSeconds(2.5)
/*
section 4 - at this point you will have 9 balls and 1 ranking point
 */

                //.setTangent(0)
                //.splineToLinearHeading(new Pose2d(36, 30, Math.PI*3/2), 0) // prep pos for balls 4->6
                //.waitSeconds(0)

               // .lineToY(56) // collect all 3 balls
                //.waitSeconds(0)
/*
section 5
 */

               /* .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(2.5)
/*
section 6 - Finale
 */

               /* .setTangent(Math.PI*7/6)
                .splineToConstantHeading(new Vector2d(-34, 14), Math.PI/4) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(0); */


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
                                       //getBalls2.build(),
                                       new SleepAction(8)



                                ), new SequentialAction(
                                shooterAngle.angle1()
                        ),
                                new SequentialAction(
                                        new SleepAction(2.5),
                                       conveyor.convUp(),
                                        new SleepAction(8.2),
                                        conveyor.convStop(),
                                        new SleepAction(4),
                                        conveyor.convUp()
                                ),
                                new SequentialAction(
                                       new SleepAction(3.5),
                                       spinner.intake(),
                                        new SleepAction(7.2),
                                        spinner.intakeOff(),
                                        new SleepAction(5),
                                        spinner.intake()
                                ),
                                new SequentialAction(
                                        new SleepAction(10),
                                        shooterAngle.angle2(),
                                        shooter.Shoot2()
                                )


                        )
                )
        );
    }
}