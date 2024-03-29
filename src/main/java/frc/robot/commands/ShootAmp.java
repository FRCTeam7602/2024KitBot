// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.HoodConstants.kAmpSpeed;
import static frc.robot.Constants.LauncherConstants.*;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HoodArm;
import frc.robot.subsystems.HoodWheels;

// import frc.robot.subsystems.CANLauncher;

/*This is an example of creating a command as a class. The base Command class provides a set of methods that your command
 * will override.
 */
public class ShootAmp extends Command {
  private CANLauncher m_launcher;
  private HoodWheels m_hoodWheels;
  private long m_startTime;

  // CANLauncher m_launcher;

  /** Creates a new LaunchNote. */
  public ShootAmp(CANLauncher launcher, HoodWheels hoodWheels, Drivetrain drivetrain) {
    // save the launcher system internally
    m_launcher = launcher;
    m_hoodWheels = hoodWheels;
  

    // indicate that this command requires the launcher system
    addRequirements(m_launcher);
    addRequirements(m_hoodWheels);
  }

  // The initialize method is called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_startTime = System.currentTimeMillis();

    // start the hood wheels to launching speed
    m_hoodWheels.setHoodWheel(.8);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // after a half second start up the launcher wheels
    if((System.currentTimeMillis() - m_startTime) >= 500) {
      m_launcher.setLaunchWheel(kAmpSpeed);
      m_launcher.setFeedWheel(kAmpSpeed);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Always return false so the command never ends on it's own. In this project we use the
    // scheduler to end the command when the button is released.
    return false;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Stop the wheels when the command ends.
    m_launcher.stop();
    m_hoodWheels.stop();
  }
}
