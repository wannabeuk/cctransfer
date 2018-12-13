package com.wannabeuk.cctransfer.init;

import java.util.ArrayList;
import java.util.List;

import com.wannabeuk.cctransfer.blocks.machines.station.*;
import com.wannabeuk.cctransfer.util.Reference;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Reference.MOD_ID)
public class ModBlocks 
{
	 @ObjectHolder("tube_station")
	 public static final BlockStation BLOCK_STATION = null;	
	 @ObjectHolder("tube")
	 public static final BlockTube BLOCK_TUBE = null;
}
