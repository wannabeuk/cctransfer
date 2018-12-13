package com.wannabeuk.cctransfer.util;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class BlockUtils 
{
    static double AXIS_MIN_MIN = 0, AXIS_MIN_MAX = 0.1, AXIS_MAX_MIN = 0.9, AXIS_MAX_MAX = 1, AXIS_FLOOR_MIN = -0.01, AXIS_FLOOR_MAX = 0;

    public static AxisAlignedBB getCollisionBoxPart(EnumFacing direction) 
    {
        if (direction == EnumFacing.EAST)
            return new AxisAlignedBB(AXIS_MAX_MIN, 0, 0, AXIS_MAX_MAX, 1,  1);
        else if (direction == EnumFacing.WEST)
            return new AxisAlignedBB(AXIS_MIN_MIN, 0, 0, AXIS_MIN_MAX, 1, 1);
        else if (direction == EnumFacing.SOUTH)
            return new AxisAlignedBB(0, 0, AXIS_MAX_MIN, 1, 1, AXIS_MAX_MAX);
        else if (direction == EnumFacing.NORTH)
            return new AxisAlignedBB(0, 0, AXIS_MIN_MIN, 1, 1, AXIS_MIN_MAX);
        else if (direction == EnumFacing.UP)
            return new AxisAlignedBB(0, AXIS_MAX_MIN, 0, 1, AXIS_MAX_MAX, 1);
        else if (direction == EnumFacing.DOWN)
            return new AxisAlignedBB(0,AXIS_MIN_MIN, 0, 1, AXIS_MIN_MAX, 1);

        return null;
    }
}
