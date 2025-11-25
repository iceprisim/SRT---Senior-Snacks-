package org.firstinspires.ftc.teamcode.autocode;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "HighVoltR", group = "Autocode")
public class RightAuto extends LinearOpMode {
    public class Vertical {
        private DcMotor ViperV1;
        private DcMotor ViperV2;
        //private Servo ClawMain;

        public Vertical(HardwareMap hardwareMap) {
            ViperV1 = hardwareMap.get(DcMotor.class, "viper");
            ViperV2 = hardwareMap.get(DcMotor.class, "worm");
            ViperV1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ViperV2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //reverse any motors as needed
            ViperV1.setDirection(DcMotorSimple.Direction.REVERSE);
        }


        public class ViperUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) { //in tyce's code its for making his claw eat the specimen
                if (!initialized) {
                    //ClawMain.setPosition(1); // may not need for arm extention
                    ViperV1.setPower(1);
                    //ViperV2.setPower(1); //use if needed
                    initialized = true;
                }
                double pos = ViperV1.getCurrentPosition();
                packet.put("LiftPos", pos);
                if (pos < 1750) { //experiment with this value for height -  turn this down
                    return true;
                } else {
                    ViperV1.setPower(0.2); //code to keep the motor up in the sky - also experiment with value
                    return false;
                }

            }


        }


        public Action viperUp() {return new ViperUp();
        }

        public class ViperDown implements Action {

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    ViperV1.setPower(-1); //retract the arm
                    initialized = true;
                }

                double pos = ViperV1.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 100.0) { // may need to change this for stop space
                    return true;
                } else {
                    ViperV1.setPower(-1); //when true set power to 0
                    return false;
                }
            }
        }

        public Action viperDown() {
            return new ViperDown();
        }

        public class ViperDrive implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    ViperV1.setPower(-1); //retract the arm
                    initialized = true;
                }

                double pos = ViperV1.getCurrentPosition();
                packet.put("liftPos", pos);
                if (pos > 1350) { // may need to change this for stop space
                    return true;
                } else {
                    ViperV1.setPower(0); //when true set power to 0
                    return false;
                }
            }
        }

        public Action viperDrive() {
            return new ViperDrive();
        }
    }

    // within the Claw class

    public class Claw {
        private Servo claw;


        public Claw(HardwareMap hardwareMap) {
            claw = hardwareMap.get(Servo.class, "claw");
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0.35);


                return false;
            }
        }

        public Action closeClaw() {return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                claw.setPosition(0);

                return false;
            }
        }

        public Action openClaw() {
            return new OpenClaw();
        }
    }

    public class Rotate {
        private Servo wrist;


        public Rotate(HardwareMap hardwareMap) {
            wrist = hardwareMap.get(Servo.class, "wrist");
        }

        public class RotateHold implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0);


                return false;
            }
        }

        public Action rotateHold() {
            return new RotateHold();
        }

        public class RotateDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.5);
                return false;
            }
        }
    }

    @Override
    public void runOpMode() {

        Pose2d BlockPos = new Pose2d(0, 0, 0);
        Pose2d ZA_HANGO = new Pose2d(12, 0, 0);
        Pose2d HAPPENING = new Pose2d(20, 0,0 );

        Claw claw = new Claw(hardwareMap);
        Rotate wrist = new Rotate(hardwareMap);
        Vertical vertical = new Vertical(hardwareMap);
        MecanumDrive drive = new MecanumDrive(hardwareMap, BlockPos);
        ElapsedTime runtime = new ElapsedTime();


        TrajectoryActionBuilder tab1 = drive.actionBuilder(BlockPos)
                .splineToConstantHeading(new Vector2d(12, 0), 0) // hang first specimen
                //.turn(Math.toRadians(10))
                .waitSeconds(1.5)//viper up
                //.splineToConstantHeading(new Vector2d(17, -4), 0)//align
                //.waitSeconds(3.5)//pull down to hand, open claw, retract fully
                //.splineToConstantHeading(new Vector2d(24, -4), 0)
                ;



        TrajectoryActionBuilder tab2 = drive.actionBuilder(ZA_HANGO)
                .splineToConstantHeading(new Vector2d(20, 0), 0)//align
                .waitSeconds(0.5)
                ;

        TrajectoryActionBuilder tab3 = drive.actionBuilder(HAPPENING)
                .waitSeconds(1)
                .splineToConstantHeading(new Vector2d(12, -10), -3)
                .waitSeconds(0.3)
                .setTangent(1)
                .splineToConstantHeading(new Vector2d(4, -40), 1)
                ;

        TrajectoryActionBuilder tab4 = drive.actionBuilder(BlockPos)

                ;

        TrajectoryActionBuilder pause = drive.actionBuilder(BlockPos)
                .waitSeconds(0.5)
                ;


        Action trajectoryActionCloseOut = tab1.fresh()
                .build();

        // actions that need to happen on init; for instance, a claw tightening.

        Actions.runBlocking(wrist.rotateHold());
        Actions.runBlocking(claw.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        Action trajectoryActionChosen;
        trajectoryActionChosen = tab1.build();

        Action trajectoryActionChosen2;
        trajectoryActionChosen2 = tab2.build();

        Action trajectoryActionChosen3;
        trajectoryActionChosen3 = tab3.build();

        Action trajectoryActionChosen4;
        trajectoryActionChosen4 = tab4.build();

        Action trajectoryActionChosen5;
        trajectoryActionChosen5 = pause.build();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryActionChosen,
                        vertical.viperUp(),
                        claw.closeClaw(),
                        trajectoryActionChosen2,
                        vertical.viperDrive(),
                        claw.openClaw(),
                        //trajectoryActionChosen3,
                        //trajectoryActionChosen5,// pause

                        //claw.openClaw(),
                        //vertical.viperDown(),
                        //wrist.rotateHold(),
                        //trajectoryActionChosen4,
                        //vertical.viperDown(),
                        //trajectoryActionChosen3,
                        trajectoryActionCloseOut
                )
        );
    }
}