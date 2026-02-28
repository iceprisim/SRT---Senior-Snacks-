package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class DriveCode_Test extends LinearOpMode {

    private CRServo lconv1;
    private CRServo lconv2;
    private CRServo rconv1;
    private CRServo rconv2;
    private Servo ShootAngle;
    private Servo block;
    private CRServo frLift;
    private CRServo brLift;
    private CRServo flLift;
    private CRServo blLift;
    //private Servo ShootHelp;

    // Strafe correction factors
    private double leftStrafeCorrectionFactor = -0.005;
    private double rightStrafeCorrectionFactor = -0.01;

    // Toggle button state tracking
    private boolean prevLeftBumper = false;
    private boolean prevRightBumper = false;
    private boolean prevBlock = false;

    // Toggle power storage
    private double conveyorTogglePower = 0;
    private double intakeTogglePower = 0;
    private double blockPower = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        
        
        double DriveSpeed = 1;
        double ShooterPower = 0;
        double turnspeed = 0.5;
        

        // Hardware mapping
        DcMotor backRight = hardwareMap.dcMotor.get("yellow");
        DcMotor frontRight = hardwareMap.dcMotor.get("brown");
        DcMotor backLeft = hardwareMap.dcMotor.get("green");
        DcMotor frontLeft = hardwareMap.dcMotor.get("black");
        DcMotor RShooter = hardwareMap.dcMotor.get("orange");
        DcMotor LShooter = hardwareMap.dcMotor.get("red");
        DcMotor Lintake = hardwareMap.dcMotor.get("pink");
        DcMotor Rintake = hardwareMap.dcMotor.get("blue");

        lconv1 = hardwareMap.get(CRServo.class, "lconv1");
        lconv2 = hardwareMap.get(CRServo.class, "lconv2");
        rconv1 = hardwareMap.get(CRServo.class, "rconv1");
        rconv2 = hardwareMap.get(CRServo.class, "rconv2");
        ShootAngle = hardwareMap.get(Servo.class, "ShootAngle");
        block = hardwareMap.get(Servo.class, "block");
        frLift = hardwareMap.get(CRServo.class, "frLift");
        brLift = hardwareMap.get(CRServo.class, "brLift");
        flLift = hardwareMap.get(CRServo.class, "flLift");
        blLift = hardwareMap.get(CRServo.class, "blLift");
        

        // Motor directions
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //LShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        RShooter.setDirection(DcMotorSimple.Direction.REVERSE);
        Rintake.setDirection(DcMotorSimple.Direction.REVERSE);
        lconv1.setDirection(DcMotorSimple.Direction.REVERSE);
        lconv2.setDirection(DcMotorSimple.Direction.REVERSE);
        block.setDirection(Servo.Direction.REVERSE);
        brLift.setDirection(CRServo.Direction.REVERSE);
        blLift.setDirection(CRServo.Direction.REVERSE);
        //ShootAngle.setDirection(Servo.Direction.REVERSE);

        // Zero power behavior
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        
        //ShootHelp.setPosition(0);

        waitForStart();
        if (isStopRequested()) return;
        
        //frLift.setPosition(0);
        //brLift.setPosition(0);

        while (opModeIsActive()) {
            
            

            // MOVEMENT INPUT
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x * 1.1;
            double rx = gamepad1.right_stick_x * turnspeed;

            // Strafe correction
            double strafeCorrection;
            if (x > 0) strafeCorrection = -Math.pow(x, 2) * leftStrafeCorrectionFactor;
            else if (x < 0) strafeCorrection = Math.pow(x, 2) * rightStrafeCorrectionFactor;
            else strafeCorrection = 0;

            rx += strafeCorrection;

            // SPEED MODES
            if (gamepad1.right_trigger > 0) { DriveSpeed = 1; turnspeed = 0.5; }
            if (gamepad1.b) { DriveSpeed = 0.5; turnspeed = 0.8; }
            if (gamepad1.left_trigger > 0) { DriveSpeed = 0.2; turnspeed = 0.9; }
            

            // WHEEL POWER CALC
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double fl = (y + x + rx) / denominator * DriveSpeed;
            double bl = (y - x + rx) / denominator * DriveSpeed;
            double fr = (y - x - rx) / denominator * DriveSpeed;
            double br = (y + x - rx) / denominator * DriveSpeed;

            frontLeft.setPower(fl);
            backLeft.setPower(bl);
            frontRight.setPower(fr);
            backRight.setPower(br);
            
    
            // Shooter Speed
            if (gamepad2.aWasPressed()) {ShooterPower += 0.015;}
            if (gamepad2.bWasPressed()) {ShooterPower -= 0.015;}
            //if (gamepad2.x) {ShooterPower = 0.55;}
            if (gamepad2.y)
                {ShootAngle.setPosition(0.155);
                ShooterPower = 0.4;}
            if (gamepad2.start) {ShooterPower = 0;}
            
            // Shooter Angle
            if (gamepad2.dpad_right) // Long Range
                {ShootAngle.setPosition(0.175);
                ShooterPower = 0.58;}
                
            if (gamepad2.dpad_up) //2nd Lowest
                {ShootAngle.setPosition(0.16);
                ShooterPower = 0.48;}
                
            if (gamepad2.dpad_left) // Mid Range
                {ShootAngle.setPosition(0.155);
                ShooterPower = 0.465;}
            
            if (gamepad2.dpad_down) //Close Range
                {ShootAngle.setPosition(0.155);
                ShooterPower = 0.42;}
                
            //C-BLock Trust
            if (gamepad1.leftBumperWasPressed()){blockPower = 1;}
                if (blockPower == 1){
                    block.setPosition(0.35);
                }
            if (gamepad2.backWasPressed()){blockPower += 1;}
                if (blockPower == 1){
                    block.setPosition(0.35);
                }    
                if (blockPower == 2){
                    block.setPosition(0);
                }
                if (blockPower > 2){
                    blockPower = 1;
                }
            
            //if (gamepad2.dpad_left) {ShootAngle.setPosition(0.18);}
            //if (gamepad1.start) {ShootAngle.setPosition(0);}
            
            //Holy$Hit button
            if (gamepad1.dpad_left){ShootAngle.setPosition(0.3);}
            
            //if (gamepad1.start) {block.setPosition(0);}
            //if (gamepad2.dpad_left) {block.setPosition(0.18);}

            RShooter.setPower(ShooterPower);
            LShooter.setPower(ShooterPower);
            
            //if (gamepad2.back) {ShootHelp.setPosition(0.2);}
                //else {ShootHelp.setPosition(0);}
                
                    
            

            //=========================================================
            //                  **CONVEYOR SYSTEM**
            //=========================================================

            //Toggle with left bumper
             if (gamepad2.left_bumper && !prevLeftBumper) {
                conveyorTogglePower = (conveyorTogglePower == 0) ? 1 : 0;
            }
            prevLeftBumper = gamepad2.left_bumper;

            // Trigger override (reverse)
            double conveyorOutput;
            if (gamepad2.left_trigger > 0.05) {
                conveyorOutput = -0.8;
            } else {
                conveyorOutput = conveyorTogglePower;
            }

            //=========================================================
            //                   **INTAKE SYSTEM**
            //=========================================================

            // Toggle with right bumper
            if (gamepad2.right_bumper && !prevRightBumper) {
                intakeTogglePower = (intakeTogglePower == 0) ? 1 : 0;
            }
            prevRightBumper = gamepad2.right_bumper;

            // Trigger override (reverse)
            double intakeOutput;
            if (gamepad2.right_trigger > 0.05) {
                intakeOutput = -0.8;
            } else {
                intakeOutput = intakeTogglePower;
            }

            // APPLY POWER (must be at bottom!)
            lconv1.setPower(conveyorOutput);
            lconv2.setPower(conveyorOutput);
            rconv1.setPower(conveyorOutput);
            rconv2.setPower(conveyorOutput);
            Lintake.setPower(intakeOutput);
            Rintake.setPower(intakeOutput);
            
            //Lift Controls:
           if (gamepad1.dpad_up && gamepad1.start) {
            // Spin both servos down at 0.8 power for 2 seconds
            frLift.setPower(-1);
            brLift.setPower(-1);
            flLift.setPower(-1);
            blLift.setPower(-1);
            sleep(10900); // 2000 milliseconds = 2 seconds
            
            // Stop the servos after the timer
            frLift.setPower(-0.6);
            brLift.setPower(-0.6);
            flLift.setPower(-0.6);
            blLift.setPower(-0.6);
            sleep(800);
            frLift.setPower(-0.7);
            brLift.setPower(-0.7);
            flLift.setPower(-0.7);
            blLift.setPower(-0.7);
          
            
            
            }

            if (gamepad1.dpad_down) {
            // Spin both servos up at -0.8 power for 2 seconds
            frLift.setPower(1);
            brLift.setPower(1);
            
            flLift.setPower(1);
            blLift.setPower(1);
            sleep(10500);
             
            // Stop the servos
            frLift.setPower(0);
            brLift.setPower(0);
            
            flLift.setPower(0);
            blLift.setPower(0);
        }
        
        if (gamepad1.dpad_right) {
            frLift.setPower(0.15);
            brLift.setPower(0.15);
            flLift.setPower(0.15);
            blLift.setPower(0.15);
        }
        
        else {
            frLift.setPower(0);
            brLift.setPower(0);
            flLift.setPower(0);
            blLift.setPower(0);
        }

            // TELEMETRY
            telemetry.addData("FL", fl);
            telemetry.addData("FR", fr);
            telemetry.addData("BL", bl);
            telemetry.addData("BR", br);
            telemetry.addData("DriveSpeed", DriveSpeed);
            telemetry.addData("Shooter Power", ShooterPower);
            telemetry.addData("ShootAngle", ShootAngle.getPosition());
            telemetry.addData("Conveyor Output", conveyorOutput);
            telemetry.addData("Intake Output", intakeOutput);
            telemetry.addData("Block Position", block.getPosition());
            telemetry.addData("Lift Power", frLift.getPower());
            //telemetry.addData("ShootHelper", ShootHelp.getPosition());
            telemetry.update();
        }
    }
}
