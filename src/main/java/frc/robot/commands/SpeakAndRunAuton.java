package frc.robot.commands;

import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpeakAndRunAuton extends SequentialCommandGroup {
    public SpeakAndRunAuton(Drivetrain drivetrain, CANLauncher launcher) {
        addCommands(
            new PrepareLaunch(launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(launcher))
                .withTimeout(2)
                .handleInterrupt(() -> launcher.stop()),
            new DriveTime(-0.4, 1.0, drivetrain)
        // new TurnTime(-0.5, 1.3, drivetrain),
        // new DriveTime(-0.6, 2.0, drivetrain),
        // new TurnTime(0.5, 1.3, drivetrain)
        );
    }
}
