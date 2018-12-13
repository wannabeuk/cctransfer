package com.wannabeuk.cctransfer.tile;

import java.util.ArrayDeque;
import java.util.EnumSet;
import java.util.HashSet;

import com.wannabeuk.cctransfer.Main;
import com.wannabeuk.cctransfer.blocks.machines.station.BlockStation;
import com.wannabeuk.cctransfer.blocks.machines.station.BlockTube;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class TileEntityBlockStation extends TileEntity
{
	private HashSet<BlockPos> connected_stations = new HashSet<>();

	public TileEntityBlockStation()
	{

	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return false;
	}

	public void searchNetwork()
	{
		if (this.connected_stations.size() > 0)
		{
			this.connected_stations.clear();
		}

		Main.instance.SendChatMessage("My Pos is: " + getPos().toString());

		ArrayDeque<BlockPos> toSearch = new ArrayDeque<>();
		HashSet<BlockPos> scanned = new HashSet<>();

		if (toSearch.isEmpty() || toSearch.peek() == null)
		{
			// this.getBlocksToScan(toSearch, scanned, this.getPos());
			toSearch.add(this.getPos().up());
		}

		BlockPos current = null;

		while (toSearch.peek() != null)
		{
			current = toSearch.pop();
			scanned.add(current);

			Block block = this.getWorld().getBlockState(current).getBlock();
			if (block == BlockStation.instance)
			{
				TryAddStation(current);
			}
			else if (block == BlockTube.instance)
			{
				this.getBlocksToScan(toSearch, scanned, current);
			}
		}
		Main.instance.SendChatMessage("We have " + this.connected_stations.size() + " connections");
		int i = 0;
		for (BlockPos blockpos : this.connected_stations)
		{
			i++;
			Main.instance.SendChatMessage(
					i + ": x: " + blockpos.getX() + " y: " + blockpos.getY() + " z: " + blockpos.getZ());
		}
	}

	private void TryAddStation(BlockPos station)
	{
		if (station.equals(this.getPos()) || station.equals(this.getPos().down())) { return; }

		if (!this.connected_stations.contains(station))
		{
			this.connected_stations.add(station);
		}
	}

	private void getBlocksToScan(ArrayDeque<BlockPos> toSearch, HashSet<BlockPos> scanned, BlockPos pos)
	{
		for (EnumFacing face : EnumSet.allOf(EnumFacing.class))
		{
			BlockPos offset = pos.offset(face);
			if (!scanned.contains(offset))
			{
				toSearch.add(offset);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		NBTTagList list = new NBTTagList();
		for (BlockPos pos : this.connected_stations)
		{
			NBTTagCompound com = new NBTTagCompound();
			com.setInteger("posX", pos.getX());
			com.setInteger("posY", pos.getY());
			com.setInteger("posZ", pos.getZ());
			list.appendTag(com);
		}
		compound.setTag("stations", list);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		if (compound.hasKey("stations"))
		{
			NBTTagList list = compound.getTagList("stations", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i <= list.tagCount(); i++)
			{
				NBTTagCompound com = list.getCompoundTagAt(i);
				if (com.hasKey("posX") && com.hasKey("posY") && com.hasKey("posZ"))
				{
					this.connected_stations
							.add(new BlockPos(com.getInteger("posX"), com.getInteger("posY"), com.getInteger("posZ")));
				}
			}
		}

	}
}
