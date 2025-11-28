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
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import com.acmerobotics.roadrunner.ParallelAction;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "Ragebait", group = "Autonomous")
public class CrashoutERA extends LinearOpMode {

    public class Spinner {
        private DcMotorEx pinLeft;
        private DcMotorEx pinRight;

        private DcMotorEx shooterRight;
        private DcMotorEx shooterLeft;

        public Spinner(HardwareMap hardwareMap) {
            pinLeft = hardwareMap.get(DcMotorEx.class, "PinwheelLeft"); // define names of super sigma motors
            pinRight = hardwareMap.get(DcMotorEx.class, "PinwheelRight");

            pinLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            pinRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            pinLeft.setDirection(DcMotorSimple.Direction.REVERSE); // change which side is reversed

            shooterRight = hardwareMap.get(DcMotorEx.class, "ShooterRight"); // define names of super sigma motors
            shooterLeft = hardwareMap.get(DcMotorEx.class, "ShooterLeft");

            shooterRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            shooterLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            shooterLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            shooterRight.setDirection(DcMotorSimple.Direction.REVERSE);

        }

        public class superSpin implements Action { //inside class "Spiner" create action Spin 1 (will rotate spin mechanism 1/3)
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) { //spin motors until the (encoder value below) is true
                if (!initialized) {
                    pinLeft.setPower(0.3); //halving speed cuz idk how fast it will spin
                    pinRight.setPower(0.3);

                    initialized = true;
                }
                double pos = pinRight.getCurrentPosition();
                telemetry.addData("pos", pos);
                telemetry.update();
                packet.put("SpinPos", pos);
                if (pos >= -5010) {  // renovate number (this number is the encoder stuff)
                    return true;
                } else {
                    pinLeft.setPower(0);
                    pinRight.setPower(0);
                    return false;
                }

                //create an acation for spin2 and an action for spin 3

                //probably need to create actions for intaking the balls and shooting the balls (make different public classes for each to differentiate easier)

            }
        }

        public Action superSpin() {

            return new superSpin();
        }

        public class PewPew implements Action { //inside class "Spiner" create action Spin 1 (will rotate spin mechanism 1/3)

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                shooterLeft.setPower(1);
                shooterRight.setPower(1);
                return false;

            }
        }

        public Action PewPew () {return new PewPew();}

    }



    @Override
    public void runOpMode() {
            //pose 2d stuff
        Pose2d beginPose = new Pose2d(0, 0, Math.toDegrees(90));
        Pose2d imAbigBoy = new Pose2d(0, 40, Math.toDegrees(90));

            //define mechanum drive and the other harware maps/ imports
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Spinner spin  = new Spinner(hardwareMap); //from public class Spinner create variable -Spin- to call subsections (spin1, etc) later.
        ElapsedTime runtime = new ElapsedTime();

        TrajectoryActionBuilder Align1 = drive.actionBuilder(beginPose)
                .lineToY(40)
                .waitSeconds(1);

        TrajectoryActionBuilder Align2 = drive.actionBuilder(imAbigBoy)
                .lineToY(0);


            //put drive action here :)
        while (!isStopRequested() && !opModeIsActive()) {
            waitForStart();
            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    new SequentialAction(
                                            Align1.build(),
                                            Align2.build()
                                    ),
                                    new SequentialAction(
                                            new SleepAction(1.5),
                                            spin.superSpin()
                                    ),
                                    new SequentialAction(
                                            spin.PewPew()
                                    )
                            )

                    )
            );
        }
    }




}