package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.HoodConstants.kShooterID;
//import static frc.robot.Constants.HoodConstants.kShooterSpeed;

public class Hood extends SubsystemBase {
    CANSparkMax m_shooter;

    public Hood() {
        m_shooter = new CANSparkMax(kShooterID, MotorType.kBrushless);
    }

    // TODO - question: do we want an reverse / re-intake option?

    // public void shoot() {
    //     m_shooter.set(kShooterSpeed);
    // }

    public void stop() {
        m_shooter.set(0);
    }

    public void setHoodWheel(double speed) {
        m_shooter.set(speed);
    }
}