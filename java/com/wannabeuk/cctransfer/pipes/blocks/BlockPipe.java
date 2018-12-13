package com.wannabeuk.cctransfer.pipes.blocks;

import com.wannabeuk.cctransfer.pipes.IPipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPipe extends Block implements IPipe
{

	public BlockPipe()
	{
		super(Material.ROCK);

	}

	@Override
	public BlockPos[] GetNeighbours(World worldIn, BlockPos pos)
	{
		return null;
	}

}
