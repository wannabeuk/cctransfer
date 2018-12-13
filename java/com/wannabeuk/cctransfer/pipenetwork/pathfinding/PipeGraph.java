package com.wannabeuk.cctransfer.pipenetwork.pathfinding;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeGraph
{
	Map<BlockPos, PipeNode> nodes;

	public PipeGraph(World world)
	{
		nodes = new HashMap<BlockPos, PipeNode>();

	}
}
