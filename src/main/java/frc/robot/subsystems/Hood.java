package frc.robot.subsystems;

import static frc.robot.Constants.HoodConstants.kShooterID;
//import static frc.robot.Constants.HoodConstants.kShooterSpeed;

import com.revrobotics.CANSparkBase;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkLimitSwitch;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase {
    private static final double TOP_ENCODER_POSITON = 38.0;
    private static final double BOTTOM_ENCODER_POSITION = 0.0;

    CANSparkBase m_shooter;

    private static final int UP_SLOT = 0;
    private final CANSparkBase m_arm = new CANSparkMax(9, MotorType.kBrushless);

    private SparkPIDController m_pidController;
    private RelativeEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, maxRPM, maxVel, minVel, maxAcc, allowedErr;

    private SparkLimitSwitch m_forwardLimit;
    private SparkLimitSwitch m_reverseLimit;

    public Hood() {
        m_shooter = new CANSparkMax(kShooterID, MotorType.kBrushless);

        m_forwardLimit = m_arm.getForwardLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);
        m_reverseLimit = m_arm.getReverseLimitSwitch(SparkLimitSwitch.Type.kNormallyOpen);

        m_forwardLimit.enableLimitSwitch(false);
        m_reverseLimit.enableLimitSwitch(false);

        m_arm.setInverted(false);

        m_pidController = m_arm.getPIDController();
        m_encoder = m_arm.getEncoder();

        m_encoder.setPosition(0);

        maxVel = 2000;
        maxAcc = 1500;

        m_pidController.setSmartMotionMaxVelocity(maxVel, UP_SLOT);
        m_pidController.setSmartMotionMinOutputVelocity(minVel, UP_SLOT);
        m_pidController.setSmartMotionMaxAccel(maxAcc, UP_SLOT);
        m_pidController.setSmartMotionAllowedClosedLoopError(allowedErr, UP_SLOT);
    }

    public void moveArmUp() {
        m_pidController.setSmartMotionMaxAccel(maxAcc, UP_SLOT);
        m_pidController.setReference(TOP_ENCODER_POSITON, CANSparkBase.ControlType.kSmartMotion, UP_SLOT);
    }

    public void moveArmDown() {
        m_pidController.setSmartMotionMaxAccel(700, UP_SLOT);
        m_pidController.setReference(BOTTOM_ENCODER_POSITION, CANSparkBase.ControlType.kSmartMotion, UP_SLOT);
    }

    public void stopArm() {
        m_arm.stopMotor();
    }

    public boolean isAtForwardLimit() {
        return m_forwardLimit.isPressed();
    }

    public boolean isAtReverseLimit() {
        return m_reverseLimit.isPressed();
    }

    public boolean isAtTop() {
        return m_encoder.getPosition() >= 38.0;
    }

    public boolean isAtBottom() {
        return m_encoder.getPosition() <= 0.0;
    }

    private double maxVelocity = 0.0;
    private double maxCurrent = 0.0;

    // TODO - question: do we want an reverse / re-intake option?

    // public void shoot() {
    // m_shooter.set(kShooterSpeed);
    // }

    public void stop() {
        m_shooter.set(0);
    }

    public void setHoodWheel(double speed) {
        m_shooter.set(speed);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Forward Limit Enabled", m_forwardLimit.isLimitSwitchEnabled());
        SmartDashboard.putBoolean("Reverse Limit Enabled", m_reverseLimit.isLimitSwitchEnabled());

        SmartDashboard.putBoolean("Forward Limit Switch", m_forwardLimit.isPressed());
        SmartDashboard.putBoolean("Reverse Limit Switch", m_reverseLimit.isPressed());

        SmartDashboard.putNumber("Encoder Position", m_encoder.getPosition());

        var velocity = m_encoder.getVelocity();
        if (velocity > maxVelocity) {
            maxVelocity = velocity;
        }
        SmartDashboard.putNumber("Encoder Velocity", velocity);
        SmartDashboard.putNumber("Max Encoder Velocity", maxVelocity);

        var current = m_arm.getOutputCurrent();
        if (current > maxCurrent) {
            maxCurrent = current;
        }
        SmartDashboard.putNumber("Motor Current", current);
        SmartDashboard.putNumber("Max Motor Current", maxCurrent);

        double maxA = SmartDashboard.getNumber("Max Acceleration", 0);
        
    }
}