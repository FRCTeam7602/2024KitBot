package frc.robot.commands;

import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HoodArm;
import frc.robot.subsystems.HoodWheels;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AmpandRun extends SequentialCommandGroup {
    public AmpandRun(Drivetrain drivetrain, CANLauncher launcher, HoodArm hoodArm, HoodWheels hoodWheels) {
        var firstStraffe = SmartDashboard.getNumber("Amp first straffe", 2);
        var secondStraffe = SmartDashboard.getNumber("Amp second straffe", 3);
            

        addCommands(
            new StraffeTime(0.6, firstStraffe, drivetrain),
            new DriveTime(0.4, 0.5, drivetrain),
            new ArmUpCommand(hoodArm),
            new ShootAmp(launcher, hoodWheels, drivetrain)
                .withTimeout(2),
            new ArmDownCommand(hoodArm),
            new DriveTime(-0.4, 0.5, drivetrain),
            new StraffeTime(0.6, secondStraffe, drivetrain)
        );
    }
}
