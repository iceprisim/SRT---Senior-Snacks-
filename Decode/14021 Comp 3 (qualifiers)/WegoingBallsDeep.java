//package org.firstinspires.ftc.robotcontroller.external.samples;
package org.firstinspires.ftc.teamcode.Autonomous;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

import java.util.List;


/*
 * This OpMode illustrates how to use the Limelight3A Vision Sensor.
 *
 * @see <a href="https://limelightvision.io/">Limelight</a>
 *
 * Notes on configuration:
 *
 *   The device presents itself, when plugged into a USB port on a Control Hub as an ethernet
 *   interface.  A DHCP server running on the Limelight automatically assigns the Control Hub an
 *   ip address for the new ethernet interface.
 *
 *   Since the Limelight is plugged into a USB port, it will be listed on the top level configuration
 *   activity along with the Control Hub Portal and other USB devices such as webcams.  Typically
 *   serial numbers are displayed below the device's names.  In the case of the Limelight device, the
 *   Control Hub's assigned ip address for that ethernet interface is used as the "serial number".
 *
 *   Tapping the Limelight's name, transitions to a new screen where the user can rename the Limelight
 *   and specify the Limelight's ip address.  Users should take care not to confuse the ip address of
 *   the Limelight itself, which can be configured through the Limelight settings page via a web browser,
 *   and the ip address the Limelight device assigned the Control Hub and which is displayed in small text
 *   below the name of the Limelight on the top level configuration screen.
 */


@Config
@Autonomous(name = "LimeLight Reh", group = "Autonomous")
//@Disabled
public class WegoingBallsDeep extends LinearOpMode {

    private Limelight3A limelight;

    // -------------------------
    // Shooter Subsystem
    // -------------------------

    public class Shooter {
        private DcMotorEx shooterLeft;
        private DcMotorEx shooterRight;

        public Shooter(HardwareMap hardwareMap) {
            shooterLeft = hardwareMap.get(DcMotorEx.class, "brown");
            shooterRight = hardwareMap.get(DcMotorEx.class, "pink");

            shooterRight.setDirection((DcMotorSimple.Direction.REVERSE));
        }

        public class Shoot1 implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shooterLeft.setPower(0.85);
                shooterRight.setPower(0.85);
                return false;
            }
        }

        public Action Shoot1() {
            return new Shoot1();
        }

        public class Shoot2 implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shooterLeft.setPower(0);
                shooterRight.setPower(0);
                return false;
            }
        }

        public Action Shoot2() {
            return new Shoot2();
        }

    }

    public class Spinner {
        private DcMotorEx spinnerLeft;
        private DcMotorEx spinnerRight;

        public Spinner(HardwareMap hardwareMap) {
            spinnerLeft = hardwareMap.get(DcMotorEx.class, "gray");
            spinnerRight = hardwareMap.get(DcMotorEx.class, "teal");

            //spinnerRight.setDirection(DcMotorEx.Direction.REVERSE);
            //spinnerLeft.setDirection(DcMotorSimple.Direction.REVERSE);


        }

        public class intake implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinnerLeft.setPower(1);
                spinnerRight.setPower(1);

                return false;
            }
        }

        public Action intake() {
            return new intake();
        }

        public class intakeStop implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinnerLeft.setPower(0);
                spinnerRight.setPower(0);

                return false;
            }
        }

        public Action intakeStop() {
            return new intakeStop();
        }

    } //includes intake

    public class Conveyor {
        private CRServo Conv1;
        private CRServo Conv2;

        public Conveyor(HardwareMap hardwareMap) {
            Conv1 = hardwareMap.get(CRServo.class, "conv1");
            Conv2 = hardwareMap.get(CRServo.class, "conv2");
        }

        public class convUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                Conv1.setPower(1);
                Conv2.setPower(1);
                return false;
            }
        }

        public Action convUp() {
            return new convUp();
        }

        public class convStop implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
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

        public ShooterAngle(HardwareMap hardwareMap) {
            shootAngle = hardwareMap.get(Servo.class, "ShootAngle");
        }

        public class angle1 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shootAngle.setPosition(0.25);
                return false;
            }
        }

        public Action angle1() {
            return new angle1();
        }

        public class angle2 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                shootAngle.setPosition(0.25);
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

        limelight = hardwareMap.get(Limelight3A.class, "limelight"); // get the camera stuff working
        //init robot
        Spinner spinner = new Spinner(hardwareMap);
        Shooter shooter = new Shooter(hardwareMap);
        Conveyor conveyor = new Conveyor(hardwareMap);
        ShooterAngle shooterAngle = new ShooterAngle(hardwareMap);

        telemetry.setMsTransmissionInterval(11);
        limelight.pipelineSwitch(0); //default is set to 0


        // start pose
        Pose2d beginPose = new Pose2d(-45, 52, Math.PI * 3 / 4);
        Pose2d shootPose = new Pose2d(-24, 24, Math.PI * 3 / 4);
        Pose2d alignPos = new Pose2d(0, 28, Math.PI*7/6);

        Pose2d GOBACK23 = new Pose2d(-10, 40, Math.PI*3/2);
        Pose2d GOBACK22 = new Pose2d(14, 40, Math.PI*3/2);

        //build drive
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        //build trajectories :)
        TrajectoryActionBuilder backwards = drive.actionBuilder(beginPose) //this trajectory shoots first 3 then aligns to scan april tag
                .setTangent(Math.PI *7/ 4)
                .splineToLinearHeading(new Pose2d(-24, 24, Math.PI*3/4), Math.PI*7/4)
                .waitSeconds(8);

        TrajectoryActionBuilder llpos = drive.actionBuilder(shootPose)
                .splineToLinearHeading(new Pose2d(0, 28, Math.PI*7/6), Math.PI*2);

        TrajectoryActionBuilder align23 = drive.actionBuilder(alignPos)
                .setTangent(Math.PI*3/4)
                .splineToLinearHeading(new Pose2d(-10, 40, Math.PI*3/2), Math.PI/2); // 5/3 for angle because the robot is right heavy causing left to drift off course

        TrajectoryActionBuilder back23 = drive.actionBuilder(GOBACK23)
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(-10, 64, Math.PI*3/2), Math.PI/2);

        TrajectoryActionBuilder align22 = drive.actionBuilder(alignPos)
                .setTangent(Math.PI*3/4)
                .splineToLinearHeading(new Pose2d(14, 40, Math.PI*3/2), Math.PI/2); //same reason as above

        TrajectoryActionBuilder back22 = drive.actionBuilder(GOBACK22)
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(14, 70, Math.PI*3/2), Math.PI/2)
                .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(14, 55, Math.PI*3/2), Math.PI/2);

        TrajectoryActionBuilder park = drive.actionBuilder(shootPose)
                .splineToLinearHeading(new Pose2d(-40, 24, Math.PI/2), Math.PI*2);



        //limelight.start();

        telemetry.update();
        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) { //use while for testing - change to if statement for auto

            Actions.runBlocking(
                    new ParallelAction(
                            new SequentialAction( //trajectories here :)
                                    shooter.Shoot1(),
                                    backwards.build(),
                                    llpos.build()
                                    //new SleepAction(2)
                            ),
                            new SequentialAction( //each of these actions will only end when all other trajectories fully end (end of auto)
                                    shooterAngle.angle1()
                            ),
                            new SequentialAction(
                                    new SleepAction(2),
                                    conveyor.convUp()
                            ),
                            new SequentialAction(
                                    new SleepAction(2),
                                    spinner.intake()
                            )
                            //new SleepAction(0.5)
                    )
            );


            limelight.start();

            LLStatus status = limelight.getStatus();
            telemetry.addData("Name", "%s", status.getName());
            telemetry.addData("LL", "Temp: %.1fC, CPU: %.1f%%, FPS: %d",status.getTemp(), status.getCpu(),(int)status.getFps());
            telemetry.addData("Pipeline", "Index: %d, Type: %s", status.getPipelineIndex(), status.getPipelineType());

//--------------------------------------------------------------------------------------------------------------------------------------

            limelight.pipelineSwitch(0);
            LLResult result = limelight.getLatestResult();
            telemetry.update();

            if (result.isValid()) {

                List<LLResultTypes.FiducialResult> fiducialResults = result.getFiducialResults();
                for (LLResultTypes.FiducialResult fiducial : fiducialResults) {
                    int id = fiducial.getFiducialId(); // This gets the ID number
                    telemetry.addData("Fiducial id:", id);
                    telemetry.update();

                    //the code below applies the ID number

//--------------------------------------------------------------------------------------------------------------------------------------

                    if (id == 23) {
                        Actions.runBlocking(
                                new SequentialAction(
                                        new ParallelAction(
                                                new SequentialAction( //drive trajectories
                                                        align23.build(),
                                                        shooter.Shoot2(),
                                                        //new SleepAction(1),
                                                        back23.build(),
                                                        conveyor.convStop(),
                                                        backwards.build(),
                                                        new SleepAction(1),
                                                        shooter.Shoot1(),
                                                        conveyor.convUp(),
                                                        new SleepAction(8),
                                                        park.build()
                                                )
                                        )
                                )
                        );

                    }

//--------------------------------------------------------------------------------------------------------------------------------------

                    if (id == 22) {
                        Actions.runBlocking(
                                new SequentialAction(
                                        new ParallelAction(
                                                new SequentialAction( //drive trajectories
                                                        align22.build(),
                                                        shooter.Shoot2(),
                                                        //new SleepAction(1),
                                                        back22.build(),
                                                        conveyor.convStop(),
                                                        backwards.build(),
                                                        new SleepAction(1),
                                                        shooter.Shoot1(),
                                                        conveyor.convUp(),
                                                        new SleepAction(8),
                                                        park.build()

                                                )
                                        )
                                )
                        );

                    }

//--------------------------------------------------------------------------------------------------------------------------------------
                    if (id == 21) {
                        Actions.runBlocking(
                                new SequentialAction(
                                        new ParallelAction(
                                                new SequentialAction( //drive trajectories
                                                        align23.build(),
                                                        shooter.Shoot2(),
                                                        //new SleepAction(1),
                                                        back23.build(),
                                                        conveyor.convStop(),
                                                        backwards.build(),
                                                        new SleepAction(1),
                                                        shooter.Shoot1(),
                                                        conveyor.convUp(),
                                                        new SleepAction(8),
                                                        park.build()
                                                )
                                        )
                                )
                        );

                    }

//--------------------------------------------------------------------------------------------------------------------------------------

                }
                limelight.stop();



            }
        }
    }
}