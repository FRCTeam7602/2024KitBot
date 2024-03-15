package frc.robot.commands;

import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class BlueSpeakAndLongStraffe extends SequentialCommandGroup {
    public BlueSpeakAndLongStraffe(Drivetrain drivetrain, CANLauncher launcher) {
        var straffeTime = 4.0;

        // if (SmartDashboard.getBoolean("Auto straffe", true)){
        //     straffeTime = 2.0;
        // }

        addCommands(
            new PrepareLaunch(launcher, drivetrain)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(launcher, drivetrain))
                .withTimeout(2)
                .handleInterrupt(() -> launcher.stop()),
            new DriveTime(-.4, 2,drivetrain),
            new StraffeTime(0.6, straffeTime, drivetrain),
            new TurnTime (-0.5, 1.5, drivetrain)
        );
    }
}
