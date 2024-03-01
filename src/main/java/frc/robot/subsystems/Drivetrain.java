package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain  extends SubsystemBase {

    private MecanumDrive m_robotDrive;

    private static final int kFrontLeftChannel = 1;
    private static final int kRearLeftChannel = 4;
    private static final int kFrontRightChannel = 2;
    private static final int kRearRightChannel = 7;

    public Drivetrain() {

        WPI_VictorSPX frontLeft = new WPI_VictorSPX(kFrontLeftChannel);
        WPI_VictorSPX rearLeft = new WPI_VictorSPX(kRearLeftChannel);
        WPI_VictorSPX frontRight = new WPI_VictorSPX(kFrontRightChannel);
        WPI_VictorSPX rearRight = new WPI_VictorSPX(kRearRightChannel);

        SendableRegistry.addChild(m_robotDrive, frontLeft);
        SendableRegistry.addChild(m_robotDrive, rearLeft);
        SendableRegistry.addChild(m_robotDrive, frontRight);
        SendableRegistry.addChild(m_robotDrive, rearRight);

        frontRight.setInverted(true);
        rearRight.setInverted(true);

        m_robotDrive = new MecanumDrive(frontLeft::set, rearLeft::set, frontRight::set, rearRight::set);
        
    }
    public void drive(double x, double y, double r){
        m_robotDrive.driveCartesian(x,y,r);
    }
}