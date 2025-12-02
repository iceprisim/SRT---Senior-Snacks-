package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class ThisIsHowTheBombExplodes {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-35, 55, 3.14/2))
                .setTangent(3.14*3/2)
                .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI*5/3) // shoot first 2/3 preloaded balls
                .waitSeconds(2.5)
/*
section 1
 */
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-12, 30, Math.PI*3/2), 0) // prep pos for balls 1->3
                .waitSeconds(0)

                .lineToY(56) // collect all 3 balls
                .waitSeconds(0)
/*
section 2
 */

                .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(2.5)
/*
section 3
 */

                .setTangent(0)
                .splineToLinearHeading(new Pose2d(12, 30, Math.PI*3/2), 0) // prep pos for balls 4->6
                .waitSeconds(0)

                .lineToY(56) // collect all 3 balls
                .waitSeconds(0)
/*
section 4
 */

                .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(2.5)
/*
section 4 - at this point you will have 9 balls and 1 ranking point
 */

                .setTangent(0)
                .splineToLinearHeading(new Pose2d(36, 30, Math.PI*3/2), 0) // prep pos for balls 4->6
                .waitSeconds(0)

                .lineToY(56) // collect all 3 balls
                .waitSeconds(0)
/*
section 5
 */

                .setTangent(Math.PI*3/2)
                .splineToLinearHeading(new Pose2d(-23, 23, Math.PI*2.6/3.5), Math.PI) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(2.5)
/*
section 6 - Finale
 */

                .setTangent(Math.PI*7/6)
                .splineToConstantHeading(new Vector2d(-34, 14), Math.PI/4) //pos for shooting next 3 balls (may need adjesting due to motor power but should(in theory) be constant)
                .waitSeconds(0)

                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_DECODE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.1f)
                .addEntity(myBot)
                .start();
    }
}