package ca._4976.steamworks;

import ca._4976.library.AsynchronousRobot;
import ca._4976.library.controllers.XboxController;
import ca._4976.steamworks.io.Inputs;
import ca._4976.steamworks.io.Outputs;
import ca._4976.steamworks.subsystems.*;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Robot extends AsynchronousRobot {

    public XboxController driver = new XboxController(this, 0);
    public XboxController operator = new XboxController(this, 1);

    private DriveTrain driveTrain = new DriveTrain(this);

    private int state = 0;

    boolean stop = false;

    public Inputs inputs = new Inputs(this);
    public Outputs outputs = new Outputs(this);

    PIDController pid10ft;
    PIDController pid90r;
    PIDController pid135;
    PIDController pid45;
    PIDController pid6ft;
    PIDController pidLong;
    PIDController pid90l;

    NetworkTable ft10;
    NetworkTable deg90r;
    NetworkTable deg135;
    NetworkTable deg45;
    NetworkTable ft6;
    NetworkTable ftLong;
    NetworkTable deg90l;



    @Override public void robotInit() {


        System.out.println("Robot Initalized!");

        ft10 = NetworkTable.getTable("5ft table");
        ft6 = NetworkTable.getTable("3ft table");
        ftLong = NetworkTable.getTable("Long table");
        deg90r = NetworkTable.getTable("90 deg R table");
        deg135 = NetworkTable.getTable("135 deg table");
        deg45 = NetworkTable.getTable("45 deg table");
        deg90l = NetworkTable.getTable("90 deg L table");

        ft10.putNumber("P", 0);
        ft10.putNumber("I", 0);
        ft10.putNumber("D", 0);


        ft6.putNumber("P", 0);
        ft6.putNumber("I", 0);
        ft6.putNumber("D", 0);

        ftLong.putNumber("P", 0);
        ftLong.putNumber("I", 0);
        ftLong.putNumber("D", 0);

        deg45.putNumber("P", 0);
        deg45.putNumber("I", 0);
        deg45.putNumber("D", 0);

        deg90r.putNumber("P", 0);
        deg90r.putNumber("I", 0);
        deg90r.putNumber("D", 0);

        deg90l.putNumber("P", 0);
        deg90l.putNumber("I", 0);
        deg90l.putNumber("D", 0);

        deg135.putNumber("P", 0);
        deg135.putNumber("I", 0);
        deg135.putNumber("D", 0);



    }

    @Override public void disabledInit() {

        System.out.println("Robot was Disabled!");
    }

    @Override public void autonomousInit() {

        System.out.println("Autonomous Initialized!");
//        pid10ft.setSetpoint();
        while(!stop){
            switch (state){
                case 0:
                    System.out.println(inputs.driveLeft.pidGet());
            }
        }
    }



    @Override public void teleopInit() {

        System.out.println("Operator Control Initialized!");
    }

    @Override public void testInit() {

        System.out.println("Test Initialized!");
    }


}
class PIDOutputs implements PIDOutput{

    Robot module;
    @Override
    public void pidWrite(double output) {
        module.outputs.driveLeftFront.set(output);
        module.outputs.driveRightFront.set(-output);
    }
}