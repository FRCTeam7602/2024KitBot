// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.Hood;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ToggleArm extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final Hood m_hood;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ToggleArm(Hood subsystem) {
    m_hood = subsystem;
    System.out.println("CTOR");
    // Use addRequirements() here to declare subsystem dependencies.

  }

  // Arm Down is the target for where we think the arm is going.
  private boolean armDown = true;
  private Command runningCommand;

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("Toggle Init");
    if(armDown){
      armDown = false;
      runningCommand = new ArmUpCommand(m_hood);
    }
    else{
      armDown = true;
      runningCommand= new ArmDownCommand(m_hood);
    }
    runningCommand.schedule();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Toggle End");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return runningCommand.isFinished();
  }
}
