package frc.robot.subsystems;

import static frc.robot.Constants.HoodConstants.kShooterID;
//import static frc.robot.Constants.HoodConstants.kShooterSpeed;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodWheels extends SubsystemBase {
    CANSparkBase m_shooter;

    public HoodWheels() {
        m_shooter = new CANSparkMax(kShooterID, MotorType.kBrushless);
    }

    // TODO - question: do we want an reverse / re-intake option?

    public void reverseShooter() {
        m_shooter.set(-0.6);
    }

    public void stop() {
        m_shooter.set(0);
    }

    public void setHoodWheel(double speed) {
        m_shooter.set(speed);
    }

    @Override
    public void periodic() {
    }
}