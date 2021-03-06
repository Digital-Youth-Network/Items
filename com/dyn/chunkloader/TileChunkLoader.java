package com.dyn.chunkloader;

import cpw.mods.fml.common.FMLLog;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;

public class TileChunkLoader extends TileEntity {
	private ForgeChunkManager.Ticket chunkTicket;

	public List<ChunkCoordIntPair> getLoadArea() {
		List<ChunkCoordIntPair> loadArea = new LinkedList();
		ChunkCoordIntPair chunkCoords = new ChunkCoordIntPair((this.xCoord >> 4), (this.zCoord >> 4));

		loadArea.add(chunkCoords);
		return loadArea;
	}

	@Override
	public void validate() {
		super.validate();
		if ((!this.worldObj.isRemote) && (this.chunkTicket == null)) {
			ForgeChunkManager.Ticket ticket = ForgeChunkManager.requestTicket(ChunkLoaderMod.instance, this.worldObj,
					ForgeChunkManager.Type.NORMAL);
			if (ticket != null) {
				forceChunkLoading(ticket);
			}
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		stopChunkLoading();
	}

	public void forceChunkLoading(ForgeChunkManager.Ticket ticket) {
		stopChunkLoading();
		this.chunkTicket = ticket;
		for (ChunkCoordIntPair coord : getLoadArea()) {
			ChunkLoaderMod.logger.info(String.format("Force loading chunk %s in %s",
					new Object[] { coord, this.worldObj.provider.getClass() }), new Object[0]);
			ForgeChunkManager.forceChunk(this.chunkTicket, coord);
		}
	}

	public void unforceChunkLoading() {
		for (Object obj : this.chunkTicket.getChunkList()) {
			ChunkCoordIntPair coord = (ChunkCoordIntPair) obj;
			ForgeChunkManager.unforceChunk(this.chunkTicket, coord);
		}
	}

	public void stopChunkLoading() {
		if (this.chunkTicket != null) {
			ForgeChunkManager.releaseTicket(this.chunkTicket);
			this.chunkTicket = null;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	}
}
