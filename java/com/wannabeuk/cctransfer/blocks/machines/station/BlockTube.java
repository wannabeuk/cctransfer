package com.wannabeuk.cctransfer.blocks.machines.station;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.Maps;
import com.wannabeuk.cctransfer.tile.TileEntityTube;
import com.wannabeuk.cctransfer.util.BlockUtils;
import com.wannabeuk.cctransfer.util.IConnectable;
import com.wannabeuk.cctransfer.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTube extends Block implements IConnectable
{
	public static BlockTube instance;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private static final Map<EnumFacing, PropertyBool> SIDE_PROP_MAP = Stream.of(EnumFacing.VALUES)
			.collect(Maps.toImmutableEnumMap(Function.identity(), side -> PropertyBool.create(side.getName())));

	@Override
	public boolean isNormalCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}

	@Override
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	public BlockTube(String name)
	{
		super(Material.GLASS);

		IBlockState state = this.getDefaultState();
		for (final PropertyBool property : BlockTube.SIDE_PROP_MAP.values())
		{
			state = state.withProperty(property, true);
		}
		setCreativeTab(CreativeTabs.MISC);
		setSoundType(SoundType.GLASS);
		Utils.setRegistryNames(this, name);
		instance = this;
		setLightOpacity(0);
		setHardness(3f);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityTube();
	}

	@Override
	public boolean canConnectTo(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing)
	{
		Block b = blockAccess.getBlockState(new BlockPos(pos.getX() + facing.getFrontOffsetX(),
				pos.getY() + facing.getFrontOffsetY(), pos.getZ() + facing.getFrontOffsetZ())).getBlock();
		if (b == this || b == BlockStation.instance) { return true; }
		return false;
	}

	@Override
	public boolean canConnectToStrict(IBlockAccess blockAccess, BlockPos pos, EnumFacing facing)
	{
		return canConnectTo(blockAccess, pos, facing);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
	{
		if (entityIn == null) { return; }

		EnumFacing direction = worldIn.getBlockState(pos).getValue(FACING);

		for (EnumFacing dir : EnumFacing.VALUES)
		{
			if (!canConnectTo(worldIn, pos, dir))
			{
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BlockUtils.getCollisionBoxPart(dir));
			}
		}
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return false;
	}

	public List<EnumFacing> GetConnectedSides(World worldIn, BlockPos pos)
	{
		List<EnumFacing> connections = new ArrayList<EnumFacing>();

		EnumFacing direction = worldIn.getBlockState(pos).getValue(FACING);

		for (EnumFacing dir : EnumFacing.VALUES)
		{
			if (canConnectTo(worldIn, pos, dir))
			{
				connections.add(dir);
			}
		}

		return connections;
	}

	private IProperty[] GetCombinedPropArray(IProperty[] origin, IProperty newValue)
	{
		IProperty[] newArray = new IProperty[origin.length + 1];
		for (int i = 0; i < origin.length; i++)
		{
			newArray[i] = origin[i];
		}
		newArray[newArray.length - 1] = newValue;

		return newArray;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		final Collection<PropertyBool> properties = BlockTube.SIDE_PROP_MAP.values();
		IProperty[] propArray = properties.toArray(new PropertyBool[0]);

		IProperty[] props = GetCombinedPropArray(propArray, FACING);

		return new BlockStateContainer(this, props);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return world.getBlockState(pos.offset(face)).getBlock() == this;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.getFront(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((EnumFacing) state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		for (final EnumFacing side : EnumFacing.VALUES)
		{
			state = state.withProperty(BlockTube.SIDE_PROP_MAP.get(side),
					worldIn.getBlockState(pos.offset(side)).getBlock() == this);
		}
		return state;
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}
}
