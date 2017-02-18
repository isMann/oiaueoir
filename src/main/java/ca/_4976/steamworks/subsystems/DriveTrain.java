package ca._4976.steamworks.subsystems;

import ca._4976.library.math.Vector2D;
import ca._4976.steamworks.Robot;

public class DriveTrain {

    private Robot module;

    private Vector2D velocity = new Vector2D(0, 0);

    public DriveTrain(Robot module) {

        this.module = module;

        module.driver.LH.addListener(value -> {

            velocity.setX(value > 0 ? value * value : value * -value);
            drive();
        });

        module.driver.BT.addListener(value -> {

            velocity.setY(value);
            drive();
        });
    }

    private void drive() {

        module.outputs.driveLeftFront.set(-velocity.getX() - velocity.getY() - 0.01);
        module.outputs.driveLeftRear.set(-velocity.getX() - velocity.getY() - 0.01);
        module.outputs.driveRightFront.set(-velocity.getX() + velocity.getY() + 0.01);
        module.outputs.driveRightRear.set(-velocity.getX() + velocity.getY() + 0.01);
    }
}
