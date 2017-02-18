package ca._4976.steamworks.subsystems;

import ca._4976.library.Evaluable;
import ca._4976.library.listeners.ButtonListener;
import ca._4976.steamworks.Robot;
import edu.wpi.first.wpilibj.PIDController;

public class Shooter {

    boolean speed = false;
    boolean shooter_firing = false;
    double linearAc = 0;
    public int turret_result = 0, rumble = 0;
    double RPM = 500;

    private Robot module;

    public Shooter(Robot module) {


        PIDController ShooterPid = new PIDController((0.0002), (0), (0), module.inputs.shooter_encoder, module.outputs.shooterMaster); //test for values with finished shooter
        this.module = module;

        //ShooterPid.setPID(table.getNumber("p", 0), table.getNumber("i", 0), table.getNumber("d", 0));
        module.operator.A.addListener(new ButtonListener() {
            @Override
            public void falling() {

                //module.visionClass.lazySusan();
                module.outputs.shooterMaster.set(500);
                module.elevator.cockingSetup();
                turret_result = module.lazySusan.getVision_state();//visionClass.getTargetState();
                System.out.println("turret result = " + turret_result);

                //RPM = module.outputs.shooterMaster.getEncVelocity();
                if (RPM < 10000 && RPM > 100){// get min rps values
                    speed = true;
                    System.out.println("speed = true");
                }
                if (speed == true && turret_result == 2){

                    System.out.println("START THE RUMBLE!!!!!!!!");
                    linearAc = 0; //linearAc = visionClass.getLinearAc();
                    module.outputs.shooterHood.set(linearAc);

                        module.runNextLoop(new Evaluable() {

                            @Override

                            public void eval() {

                                for (int i = 0; i < 6; i++) {

                                    if (i % 2 == 0) module.runNextLoop(() -> module.operator.setRumble(1), 500 * i);

                                    else module.runNextLoop(() -> module.operator.setRumble(0), 500 * i);

                                    if (i % 2 == 0) module.runNextLoop(() -> module.driver.setRumble(1), 500 * i);

                                    else module.runNextLoop(() -> module.driver.setRumble(0), 500 * i);
                                }

                            }

                        },3000);// get actual time for the linear acctuator
                }
                else if(turret_result == 0){


                    module.runNextLoop(() -> module.driver.setRumble(1), 0);

                    module.runNextLoop(() -> module.operator.setRumble(1), 0);

                    module.runNextLoop(() -> module.driver.setRumble(0), 3000);

                    module.runNextLoop(() -> module.operator.setRumble(0), 3000);


                }
            }
        });

        module.operator.B.addListener(new ButtonListener() {
            @Override
            public void rising() {
                //RPM = module.outputs.shooterMaster.getEncVelocity();
                if (RPM < 10000 && RPM  > 100){
                    module.elevator.stopMotors();
                    module.elevator.fire();
                    System.out.println("Fire");
                }
                if (RPM > 10000 || RPM < 100){
                    module.elevator.stopMotors();
                    System.out.println("no fire");
                }
            }

            @Override
            public void falling() {
                module.elevator.stopMotors();
                System.out.println("not firing");
            }
        });

        module.operator.X.addListener(new ButtonListener() {
            @Override
            public void falling() {
                module.elevator.stopMotors();
                module.outputs.shooterMaster.set(500);
                System.out.println("mark its done");
            }
        });
    }

}
//TODO get min RPM values, Get pid numbers from miduraz.