// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutonomousTime;
import frc.robot.commands.Autos;
import frc.robot.commands.Drive;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.NoopAuton;
import frc.robot.commands.PrepareLaunch;
import frc.robot.commands.ShootAmp;
import frc.robot.commands.SpeakAndLongRunAuton;
import frc.robot.commands.SpeakAndRunAuton;
import frc.robot.commands.SpeakandStop;
import frc.robot.commands.ToggleArm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.CANLauncher;

// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  private final Drivetrain m_drivetrain = new Drivetrain();
  // private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final CANLauncher m_launcher = new CANLauncher();
  // private final CANLauncher m_launcher = new CANLauncher();
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  private final Hood m_hood = new Hood();


  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandPS5Controller m_operatorController =
      new CommandPS5Controller(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
      new Drive(m_drivetrain, () -> -m_driverController.getLeftY(),() -> m_driverController.getLeftX(),() -> m_driverController.getRightX()));
             
    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    m_operatorController
        .R2()
        .whileTrue(
            new PrepareLaunch(m_launcher,m_drivetrain)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher,m_drivetrain))
                .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_operatorController.L2().whileTrue(m_launcher.getIntakeCommand());

    m_operatorController.R1().whileTrue(new ShootAmp(m_launcher,m_hood,m_drivetrain));

    m_operatorController.cross().onTrue(new ToggleArm(m_hood));

    m_chooser.setDefaultOption("LEAVE START ZONE", new AutonomousTime(m_drivetrain));
    m_chooser.addOption("DO NOTHING", new NoopAuton());
    m_chooser.addOption("SCORE SPEAKER", new SpeakandStop(m_drivetrain, m_launcher));
    m_chooser.addOption("SCORE SPEAKER THEN LEAVE", new SpeakAndRunAuton(m_drivetrain, m_launcher));
    m_chooser.addOption("SCORE SPEAKER THEN LEAVE LONGER", new SpeakAndLongRunAuton(m_drivetrain, m_launcher));
    SmartDashboard.putData(m_chooser);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_chooser.getSelected();
  }
}
