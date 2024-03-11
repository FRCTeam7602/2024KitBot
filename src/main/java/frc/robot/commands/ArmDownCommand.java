// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Hood;

/** An example command that uses an example subsystem. */
public class ArmDownCommand extends Command {
    private final Hood m_hood;

    public ArmDownCommand(Hood subsystem) {
        m_hood = subsystem;
        addRequirements(subsystem);
    }

	@Override
	public void initialize() {
		System.out.println("Down Init");
    	// OLD WAY m_subsystem.moveArm(-0.1);
        // with PID we'll set target position
        m_hood.moveArmDown();
	}

    @Override
    public void execute() {
        // without pid control we had to adjust speed as we get close
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("Down End");
        m_hood.stopArm();
    }

    @Override
    public boolean isFinished() {
        return m_hood.isAtReverseLimit() || m_hood.isAtBottom();
    }
}
