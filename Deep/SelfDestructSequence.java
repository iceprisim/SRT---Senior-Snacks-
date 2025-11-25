package org.firstinspires.ftc.teamcode.autocode;

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
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "r", group = "Autonomous")
public class SelfDestructSequence extends LinearOpMode {
    // lift class
    public class Vertical {
        private DcMotorEx viperLeft;
        private DcMotorEx viperRight;

        public Vertical(HardwareMap hardwareMap) {
            viperLeft = hardwareMap.get(DcMotorEx.class, "leftViper");
            viperRight = hardwareMap.get(DcMotorEx.class, "rightViper");

            viperLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            viperRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            viperRight.setDirection(DcMotorSimple.Direction.REVERSE);



            //viperRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        public class ViperUpS implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperRight.setPower(-1);
                    viperLeft.setPower(-1);

                    initialized = true;
                }
                double pos = viperRight.getCurrentPosition();
                telemetry.addData("pos", pos);
                telemetry.update();
                packet.put("LiftPos", pos);
                if (pos >= -100) {  // renovate number
                    return true;
                } else {
                    viperLeft.setPower(-0.2); // renovate number
                    viperRight.setPower(-0.2); // renovate number
                    return false;
                }
            }
        }
        public Action ViperUS() {
            return new ViperUp();
        }
        public class ViperUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperRight.setPower(-1);
                    viperLeft.setPower(-1);

                    initialized = true;
                }
                double pos = viperRight.getCurrentPosition();
                telemetry.addData("pos", pos);
                telemetry.update();
                packet.put("LiftPos", pos);
                if (pos >= -1382) {  // renovate number
                    return true;
                } else {
                    viperLeft.setPower(-0.2); // renovate number
                    viperRight.setPower(-0.2); // renovate number
                    return false;
                }
            }
        }
        public Action ViperU() {
            return new ViperUp();
        }
        public class ViperDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperLeft.setPower(1);
                    viperRight.setPower(1);
                    initialized = true;
                }
                double pos = viperRight.getCurrentPosition();
                telemetry.addData("pos", pos);
                packet.put("LiftPos", pos);
                if (pos <=-100) {  // renovate number
                    return true;
                } else {
                    viperLeft.setPower(0.1); // renovate number
                    viperRight.setPower(0.1); // renovate number
                    return false;
                }
            }
        }

        public Action ViperD() {
            return new ViperDown();
        }

        public class ViperDown2 implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperLeft.setPower(1);
                    viperRight.setPower(1);
                    initialized = true;
                }
                double pos = viperRight.getCurrentPosition();
                telemetry.addData("pos", pos);
                telemetry.update();
                packet.put("LiftPos", pos);
                if (pos <=-1000) {  // renovate number
                    return true;
                } else {
                    viperLeft.setPower(0.1); // renovate number
                    viperRight.setPower(0.1); // renovate number
                    return false;
                }
            }
        }

        public Action ViperD2() {
            return new ViperDown();
        }
//        public class ViperPickUp implements Action {
//            private boolean initialized = false;
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                if (!initialized) {
//                    viperLeft.setPower(-1);
//                    viperRight.setPower(-1);
//                    initialized = true;
//                }
//
//                double pos = viperRight.getCurrentPosition();
//                packet.put("LiftPOs", pos);
//                if (pos > 1) {
//                    return true;
//                } else {
//                    viperLeft.setPower(0.13);
//                    viperRight.setPower(0.13);
//                    return false;
//                }
//            }
//        }
//
//        public Action ViperPickup() {
//            return new ViperPickUp();
//        }
//
//        public class ViperDrive implements Action {
//            private boolean initialized = false;
//
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                if (!initialized) {
//                    viperLeft.setPower(1);
//                    viperRight.setPower(1);
//                    initialized = true;
//                }
//
//                double pos = viperRight.getCurrentPosition();
//                packet.put("LiftPos", pos);
//                if (pos < 400) {
//                    return true;
//                } else {
//                    viperLeft.setPower(0.13);
//                    viperRight.setPower(0.13);
//                    return false;
//                }
//            }
//        }
//
//        public Action drive() {
//            return new ViperDrive();
//        }

        public class specimenHang implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperLeft.setPower(-1);
                    viperRight.setPower(-1);
                    initialized = true;
                }

                double pos = viperRight.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 2603) {
                    return true;
                } else {
                    viperLeft.setPower(0);
                    viperRight.setPower(0);
                    return false;
                }
            }
        }

        public Action hang() {
            return new specimenHang();
        }

        public class specimenUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    viperLeft.setPower(1);
                    viperRight.setPower(1);
                    initialized = true;
                }

                double pos = viperRight.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos < 3165) {
                    return true;
                } else {
                    viperLeft.setPower(0);
                    viperRight.setPower(0);
                    return false;
                }
            }
        }

        public Action SUp() {
            return new specimenUp();
        }
    }

    @Override
    public void runOpMode() {
        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(180));
        Pose2d firstPose = new Pose2d(27.1, 0, Math.toRadians(180));
        Pose2d movePose1 = new Pose2d(33.1, 0, Math.toRadians(180));
        Pose2d pushPose1 = new Pose2d(45.6, -36.5, 0);
        Pose2d returnPos1 = new Pose2d(3, -45.6, 0);
        Pose2d pickupPos = new Pose2d(-7, -36, 0);

        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        ElapsedTime runtime = new ElapsedTime();
        Vertical vertical = new Vertical(hardwareMap);

        TrajectoryActionBuilder prepHang = drive.actionBuilder(beginPose)
                .strafeTo(new Vector2d(35, 0));
        TrajectoryActionBuilder firstPushPos = drive.actionBuilder(movePose1)
                .strafeTo(new Vector2d(28.1, 0))
                .splineTo(new Vector2d(49.6, -30), 0)
                .strafeTo(new Vector2d(49.6, -35.6));
        TrajectoryActionBuilder push = drive.actionBuilder(pushPose1)
                //.strafeTo(new Vector2d(45.6, -45.6))
                .strafeTo(new Vector2d(3, -38.6));
        TrajectoryActionBuilder push2 = drive.actionBuilder(pushPose1)
                .strafeTo(new Vector2d(45.6, -45.6))
                .strafeTo(new Vector2d(-12, -45.6));

        TrajectoryActionBuilder reset = drive.actionBuilder(returnPos1)
                //.strafeTo(new Vector2d(51.5, -45.6))
                .splineTo(new Vector2d(45.6, -45), 0);
        //.strafeTo(new Vector2d(53.8, -65));
        TrajectoryActionBuilder pickup = drive.actionBuilder(returnPos1)
                //.strafeTo(new Vector2d(12, -36))
                .strafeTo(new Vector2d(-15, -40))
                .waitSeconds(0.2);
        TrajectoryActionBuilder dropOff = drive.actionBuilder(pickupPos)
                .splineTo(new Vector2d(15, 20 ), Math.toRadians(180))
                .strafeTo(new Vector2d(38.8, 20));
        TrajectoryActionBuilder pickup2 = drive.actionBuilder(new Pose2d(33.1, 20, Math.toRadians(180)))
                .splineTo(new Vector2d(12, -34 ), 0)
                .strafeTo(new Vector2d(-16, -32));
        TrajectoryActionBuilder drop3 = drive.actionBuilder(new Pose2d(-7, -32, 0))
                .splineTo(new Vector2d(15,10 ), Math.toRadians(180))
                .strafeTo(new Vector2d(40, 10));



//                .setTangent(0)
//                .splineToLinearHeading(new Pose2d(0,-27,Math.toRadians(-100)),Math.toRadians(0))
//                .waitSeconds(5)
//                .splineToLinearHeading(new Pose2d(0,-35,Math.toRadians(-100)),Math.toRadians(0))

        while (!isStopRequested() && !opModeIsActive()) {


            waitForStart();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    new SequentialAction(
                                            prepHang.build(),
                                            firstPushPos.build(),
                                            push.build(),
                                            reset.build(),
                                            push2.build(),
                                            dropOff.build(),
                                            pickup2.build(),
                                            drop3.build()
                                    ),
                                    new SequentialAction(
                                            vertical.ViperU(),
                                            new SleepAction(0.75),
                                            vertical.ViperD2(),
                                            new SleepAction(0.5),
                                            vertical.ViperD(),
                                            new SleepAction(10.75),
                                            vertical.ViperU(),
                                            new SleepAction(4),
                                            vertical.ViperD(),
                                            new SleepAction(4.3),
                                            vertical.ViperU(),
                                            new SleepAction(4),
                                            vertical.ViperD()

                                    )

                            )

//                            prepHang.build(),
//                            vertical.ViperU(),
//                            hangPos.build(),
//                            vertical.ViperD(),
//                            firstPushPos.build(),
//                            push.build(),
//                            reset.build(),
//                            push.build(),
//                            pickup.build(),
//                            vertical.ViperUS(),
//                            dropOff.build(),
//                            vertical.ViperD()
                    )
            );

        }
    }
}