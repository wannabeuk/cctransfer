package com.wannabeuk.cctransfer.blocks;

import com.wannabeuk.cctransfer.Main;
import com.wannabeuk.cctransfer.init.ModBlocks;
import com.wannabeuk.cctransfer.init.ModItems;
import com.wannabeuk.cctransfer.util.IHasModel;
import com.wannabeuk.cctransfer.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

//Basic Custom Block Class
public class BlockBase extends Block
{
	public BlockBase(Material material, String unlocalizedName, String registryName) 
	{
        this(material, SoundType.STONE, unlocalizedName, registryName);
    }
 
    public BlockBase(Material material, SoundType sound, String unlocalizedName, String registryName) 
    {
        super(material);
        setUnlocalizedName(Reference.MOD_ID + "." + unlocalizedName);
        setRegistryName(registryName);
        setCreativeTab(CreativeTabs.MISC);
        setSoundType(sound);
    }

}
