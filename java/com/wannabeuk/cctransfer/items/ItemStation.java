package com.wannabeuk.cctransfer.items;

import com.wannabeuk.cctransfer.blocks.machines.station.BlockStation;
import com.wannabeuk.cctransfer.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStation extends ItemBlock
{
	public ItemStation()
	{
		super(ModBlocks.BLOCK_STATION);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (facing != EnumFacing.UP)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (!block.isReplaceable(worldIn, pos))
			{
				pos = pos.offset(facing);
			}

			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos))
			{
				EnumFacing enumfacing = EnumFacing.fromAngle((double) player.rotationYaw);
				placeStation(worldIn, pos, enumfacing, this.block);
				SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos),
						worldIn, pos, player);
				worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS,
						(soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
				itemstack.shrink(1);
				return EnumActionResult.SUCCESS;
			}
			else
			{
				return EnumActionResult.FAIL;
			}
		}

	}

	public static void placeStation(World worldIn, BlockPos pos, EnumFacing facing, Block block)
	{
		BlockPos blockpos = pos.offset(facing.rotateY());
		BlockPos blockpos1 = pos.offset(facing.rotateYCCW());
		// int i = (worldIn.getBlockState(blockpos1).isNormalCube() ? 1 : 0) +
		// (worldIn.getBlockState(blockpos1.up()).isNormalCube() ? 1 : 0);
		// int j = (worldIn.getBlockState(blockpos).isNormalCube() ? 1 : 0) +
		// (worldIn.getBlockState(blockpos.up()).isNormalCube() ? 1 : 0);
		// boolean flag = worldIn.getBlockState(blockpos1).getBlock() == block ||
		// worldIn.getBlockState(blockpos1.up()).getBlock() == block;
		// boolean flag1 = worldIn.getBlockState(blockpos).getBlock() == block ||
		// worldIn.getBlockState(blockpos.up()).getBlock() == block;

		BlockPos blockpos2 = pos.up();
		IBlockState iblockstate = block.getDefaultState().withProperty(BlockStation.FACING, facing);
		worldIn.setBlockState(pos, iblockstate.withProperty(BlockStation.PART, BlockStation.EnumPartType.BOTTOM), 2);
		worldIn.setBlockState(blockpos2, iblockstate.withProperty(BlockStation.PART, BlockStation.EnumPartType.TOP), 2);
		worldIn.notifyNeighborsOfStateChange(pos, block, false);
		worldIn.notifyNeighborsOfStateChange(blockpos2, block, false);
	}
}
