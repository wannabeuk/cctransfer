package com.wannabeuk.cctransfer.blocks.machines.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wannabeuk.cctransfer.Main;
import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.tile.TileEntityBlockStation;
import com.wannabeuk.cctransfer.util.Utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStation extends Block implements IModBlock
{
	public static BlockStation instance;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<BlockStation.EnumPartType> PART = PropertyEnum.<BlockStation.EnumPartType>create(
			"part", BlockStation.EnumPartType.class);
	public static final int SHIFT = 8;
	static double AXIS_MIN_MIN = 0, AXIS_MIN_MAX = 0.1, AXIS_MAX_MIN = 0.9, AXIS_MAX_MAX = 1, AXIS_FLOOR_MIN = -0.01,
			AXIS_FLOOR_MAX = 0;

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

	/**
	 * Get the geometry of the queried face at the given position and state. This is
	 * used to decide whether things like buttons are allowed to be placed on the
	 * face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED},
	 * which represents something that does not fit the other descriptions and will
	 * generally cause other things not to connect to the face.
	 * 
	 * @return an approximation of the form of the given face
	 */
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	public BlockStation(String name)
	{
		super(Material.ROCK);
		Utils.setRegistryNames(this, name);
		setCreativeTab(CreativeTabs.MISC);
		setLightOpacity(1);

		setHardness(5f);

		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		instance = this;
	}

	@Override
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(ModBlocks.BLOCK_STATION);
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ModBlocks.BLOCK_STATION);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			// playerIn.openGui(Main.instance, Reference.GUI_BLOCK_STATION, worldIn,

			BlockPos blockpos = state.getValue(PART) == BlockStation.EnumPartType.TOP ? pos : pos.up();

			TileEntityBlockStation tile = (TileEntityBlockStation) worldIn.getTileEntity(blockpos);
			if (tile != null)
			{
				tile.searchNetwork();
			}
			else
			{
				Main.instance.SendChatMessage("No TE found! :( ");
			}
		}

		return true;
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing) state.getValue(FACING);

			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
			{
				face = EnumFacing.SOUTH;
			}
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
			{
				face = EnumFacing.NORTH;
			}
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock())
			{
				face = EnumFacing.EAST;
			}
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
			{
				face = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		if (state.getValue(PART) == BlockStation.EnumPartType.BOTTOM) { return false; }
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		if (state.getValue(PART) == BlockStation.EnumPartType.BOTTOM) { return null; }
		return new TileEntityBlockStation();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	private boolean IsConnected(World worldIn, BlockPos pos)
	{
		if (worldIn.getBlockState(pos.up()).getBlock() != BlockTube.instance)
		{
			// We're not connected at all, return false.
			return false;
		}

		return false;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack)
	{

		worldIn.setBlockState(pos,
				this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { FACING, PART });
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		if (pos.getY() >= worldIn.getHeight() - 1)
		{
			return false;
		}
		else
		{
			IBlockState state = worldIn.getBlockState(pos.down());
			return (state.isTopSolid()
					|| state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID)
					&& super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
		}
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		switch (side)
		{
		// We only want to render the top if it's the TOP part
		case UP:
			if (blockAccess.getBlockState(pos).getBlock() == this)
			{
				if (blockState.getValue(PART) == BlockStation.EnumPartType.TOP)
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			if (blockAccess.getBlockState(pos).getBlock() == BlockTube.instance) { return false; }
			return true;
		case NORTH:
		case SOUTH:
		case WEST:
		case EAST:
			return true;
		default:
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
	{
		if (entityIn == null) { return; }

		List<AxisAlignedBB> axis = new ArrayList<AxisAlignedBB>();

		EnumFacing facing = worldIn.getBlockState(pos).getValue(FACING);

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		AxisAlignedBB north = new AxisAlignedBB(0, 0, AXIS_MIN_MIN, 1, 1, AXIS_MIN_MAX);
		AxisAlignedBB east = new AxisAlignedBB(AXIS_MAX_MIN, 0, 0, AXIS_MAX_MAX, 1, 1);
		AxisAlignedBB south = new AxisAlignedBB(0, 0, AXIS_MAX_MIN, 1, 1, AXIS_MAX_MAX);
		AxisAlignedBB west = new AxisAlignedBB(AXIS_MIN_MIN, 0, 0, AXIS_MIN_MAX, 1, 1);

		// TODO : Work out how to do upper / lower block
		if (facing == EnumFacing.NORTH)
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, south);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, east);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, west);
		}
		else if (facing == EnumFacing.SOUTH)
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, north);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, east);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, west);
		}
		else if (facing == EnumFacing.EAST)
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, west);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, north);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, south);
		}
		else if (facing == EnumFacing.WEST)
		{
			addCollisionBoxToList(pos, entityBox, collidingBoxes, east);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, north);
			addCollisionBoxToList(pos, entityBox, collidingBoxes, south);
		}

		// if(worldIn.getBlockState(pos).getValue(PART) ==
		// BlockStation.EnumPartType.TOP)
		// {
		// if(entityIn.isSneaking() && worldIn.getBlockState(pos.up()).getValue(FACING)
		// == EnumFacing.UP )
		// {
		// axis.add(new AxisAlignedBB(new BlockPos(x, y + AXIS_MAX_MIN, z), new
		// BlockPos(x + 1, y + AXIS_MAX_MAX, z + 1)));
		// }
		// else if(worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() !=
		// BlockTube.instance)
		// {
		// axis.add(new AxisAlignedBB(new BlockPos(x, y + AXIS_MAX_MIN, z), new
		// BlockPos(x + 1, y + AXIS_MAX_MAX, z + 1)));
		// }
		// }
		// else if (entityIn.posY >= pos.getY())
		// {
		// if(entityIn.isSneaking() &&
		// worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getValue(FACING) ==
		// EnumFacing.DOWN)
		// {
		// axis.add(new AxisAlignedBB(new BlockPos(x, y + AXIS_FLOOR_MIN, z), new
		// BlockPos( x + 1, y + AXIS_FLOOR_MAX, z + 1)));
		// }
		// else if (worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() !=
		// BlockTube.instance)
		// {
		// axis.add(new AxisAlignedBB(new BlockPos(x, y + AXIS_FLOOR_MIN, z), new
		// BlockPos( x + 1, y + AXIS_FLOOR_MAX, z + 1)));
		// }
		// else if (worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getValue(FACING)
		// != EnumFacing.DOWN)
		// {
		// axis.add(new AxisAlignedBB(new BlockPos(x, y + AXIS_FLOOR_MIN, z), new
		// BlockPos( x + 1, y + AXIS_FLOOR_MAX, z + 1)));
		// }
		// }

		// for(AxisAlignedBB a : axis)
		// {
		// if(a != null && entityBox.intersects(a))
		// {
		// collidingBoxes.add(a);
		// }
		// }
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getFront(meta);

		if (facing.getAxis() == EnumFacing.Axis.Y)
		{
			facing = EnumFacing.NORTH;
		}
		return (meta & 8) > 0
				? this.getDefaultState().withProperty(PART, BlockStation.EnumPartType.TOP).withProperty(FACING, facing)
				: this.getDefaultState().withProperty(PART, BlockStation.EnumPartType.BOTTOM).withProperty(FACING,
						facing);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		if (state.getValue(PART) == BlockStation.EnumPartType.BOTTOM)
		{
			IBlockState iblockstate = worldIn.getBlockState(pos.offset(EnumFacing.UP));

			if (iblockstate.getBlock() == this)
			{
				// E.G - get the none meta info from the main block.
				// state = state.withProperty(OCCUPIED, iblockstate.getValue(OCCUPIED));
			}
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumFacing) state.getValue(FACING)).getIndex();

		if (state.getValue(PART) == BlockStation.EnumPartType.TOP)
		{
			i |= 8;
		}

		return i;
	}

	public static enum EnumPartType implements IStringSerializable
	{
		TOP("top"), BOTTOM("bottom");

		private final String name;

		private EnumPartType(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}

		public String getName()
		{
			return this.name;
		}
	}
}
