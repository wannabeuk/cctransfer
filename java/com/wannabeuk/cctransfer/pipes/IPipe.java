package com.wannabeuk.cctransfer.pipes;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IPipe
{
	public BlockPos[] GetNeighbours(World worldIn, BlockPos pos);
}
