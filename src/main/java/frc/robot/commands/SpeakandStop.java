package frc.robot.commands;

import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class SpeakandStop extends SequentialCommandGroup {
    public SpeakandStop(Drivetrain drivetrain, CANLauncher launcher) {
        addCommands(
            new PrepareLaunch(launcher, drivetrain)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(launcher, drivetrain))
                .withTimeout(2)
                .handleInterrupt(() -> launcher.stop())
        );
    }
}
