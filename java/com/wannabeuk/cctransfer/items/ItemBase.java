package com.wannabeuk.cctransfer.items;

import com.wannabeuk.cctransfer.Main;
import com.wannabeuk.cctransfer.init.ModItems;
import com.wannabeuk.cctransfer.util.IHasModel;
import com.wannabeuk.cctransfer.util.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item
{
	public ItemBase(String unlocalizedName, String registryName)
	{
		setUnlocalizedName(Reference.MOD_ID + "." + unlocalizedName);
		setRegistryName(registryName);
		setCreativeTab(CreativeTabs.MISC);
	}

}
