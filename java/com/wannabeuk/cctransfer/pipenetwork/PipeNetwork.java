package com.wannabeuk.cctransfer.pipenetwork;

import java.util.ArrayList;
import java.util.List;

import com.wannabeuk.cctransfer.pipes.INodePipe;
import com.wannabeuk.cctransfer.pipes.blocks.BlockPipe;
import com.wannabeuk.cctransfer.pipes.tileentity.TileEntityPipeBase;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeNetwork
{
	List<INodePipe> nodes = new ArrayList<INodePipe>();
	List<BlockPos> pipes = new ArrayList<BlockPos>();
	List<PipeSection> sections = new ArrayList<PipeSection>();
	World world;

	public PipeNetwork(World world)
	{
		this.world = world;
	}

	public void GenerateSections()
	{
		List<BlockPos> checked = new ArrayList<BlockPos>();
		/*
		 * for each pipe if pipe !check then find neighbours for each neighbour if not
		 * check add to checked if has network add network else create network
		 * 
		 * 
		 */
		for (int i = 0; i < pipes.size(); i++)
		{
			BlockPos pipe = pipes.get(i);
			if (checked.contains(pipe))
			{
				continue;
			}

			checked.add(pipe);

			TileEntityPipeBase tileentitypipe = (TileEntityPipeBase) world.getTileEntity(pipe);
			BlockPos[] neighbours = ((BlockPipe) world.getBlockState(pipe).getBlock()).GetNeighbours(world, pipe);

			PipeSection[] sections = new PipeSection[2];

			for (int n = 0; n < neighbours.length; n++)
			{
				BlockPos pos = neighbours[n];
				TileEntityPipeBase te = (TileEntityPipeBase) world.getTileEntity(pos);
				if (te != null)
				{
					PipeSection section;
					if (te.section == null)
					{
						// Create Section
						section = new PipeSection();
					}
					else
					{
						section = te.section;
					}

					if (sections[0] != null)
					{
						sections[0] = section;
					}
					else
					{
						if (sections[1] != null)
						{
							if (!sections[1].equals(section))
							{
								sections[1] = section;
							}
						}
					}
				}
			}

			if (sections[1] != null)
			{
				// Merge the section. using the first result as the one to merge too.
				sections[0].MergeSections(world, sections[1]);
			}

			sections[0].AddPipeToSection(world, pipe);
		}
	}
}
