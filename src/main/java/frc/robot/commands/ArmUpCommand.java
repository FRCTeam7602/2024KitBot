// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.HoodArm;
import edu.wpi.first.wpilibj2.command.Command;

public class ArmUpCommand extends Command {
    private final HoodArm m_hood;

    public ArmUpCommand(HoodArm subsystem) {
        m_hood = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        System.out.println("Up Init");
        // OLD WAY m_subsystem.moveArm(0.20);
        // with PID we'll set target position
        m_hood.moveArmUp();
    }

    @Override
    public void execute() {
        // without pid control we had to adjust speed as we get close
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Up End");
        m_hood.stopArm();
    }

    @Override
    public boolean isFinished() {
        return m_hood.isAtForwardLimit() || m_hood.isAtTop();
    }
}
