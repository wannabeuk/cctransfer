package com.wannabeuk.cctransfer.pipenetwork;

import java.util.ArrayList;
import java.util.List;

import com.wannabeuk.cctransfer.pipes.tileentity.TileEntityPipeBase;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeSection
{
	private final int sectionID = -1;
	private List<BlockPos> pipes = new ArrayList<BlockPos>();

	public PipeSection()
	{
		// Generate a section id, or get one from somewhere.
		// sectionID = GetNextID();

	}

	public List<BlockPos> GetAllPipesInSection()
	{
		return pipes;
	}

	public void ClearPipes()
	{
		pipes.clear();
	}

	public void AddPipesToSection(World world, List<BlockPos> pipes)
	{
		for (int i = 0; i < pipes.size(); i++)
		{
			AddPipeToSection(world, pipes.get(i));
		}
	}

	public void AddPipeToSection(World world, BlockPos pipe)
	{
		if (!pipes.contains(pipe))
		{
			((TileEntityPipeBase) world.getTileEntity(pipe)).section = this;
			pipes.add(pipe);
		}
	}

	public void RemoveFromPipeSection(BlockPos pipe)
	{
		if (pipes.contains(pipe))
		{
			pipes.remove(pipe);
		}

		if (pipes.size() < 1)
		{
			// TODO: Mark this section for removal?
		}
	}

	// Merge this section with the other.
	public void MergeSections(World world, PipeSection pipeSection)
	{
		this.AddPipesToSection(world, pipeSection.GetAllPipesInSection());
		pipeSection.ClearPipes();
	}
}
