// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_forward;
  private final DoubleSupplier m_rotation;
  private final DoubleSupplier m_side;


  public Drive(Drivetrain drivetrain, DoubleSupplier forward, DoubleSupplier side, DoubleSupplier rotation) {
    m_drivetrain = drivetrain;
    m_forward = forward;
    m_rotation = rotation;
    m_side = side;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.drive(m_forward.getAsDouble(),m_side.getAsDouble(),m_rotation.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}