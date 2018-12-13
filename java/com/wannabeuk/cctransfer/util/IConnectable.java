package com.wannabeuk.cctransfer.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IConnectable 
{
	public boolean canConnectTo(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing);
	
	public boolean canConnectToStrict(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing);
}
