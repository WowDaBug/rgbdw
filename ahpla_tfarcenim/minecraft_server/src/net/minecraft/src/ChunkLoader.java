package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class ChunkLoader implements IChunkLoader {
	private File field_945_a;
	private boolean field_944_b;

	public ChunkLoader(File var1, boolean var2) {
		this.field_945_a = var1;
		this.field_944_b = var2;
	}

	private File func_665_a(int var1, int var2) {
		String var3 = "c." + Integer.toString(var1, 36) + "." + Integer.toString(var2, 36) + ".dat";
		String var4 = Integer.toString(var1 & 63, 36);
		String var5 = Integer.toString(var2 & 63, 36);
		File var6 = new File(this.field_945_a, var4);
		if(!var6.exists()) {
			if(!this.field_944_b) {
				return null;
			}

			var6.mkdir();
		}

		var6 = new File(var6, var5);
		if(!var6.exists()) {
			if(!this.field_944_b) {
				return null;
			}

			var6.mkdir();
		}

		var6 = new File(var6, var3);
		return !var6.exists() && !this.field_944_b ? null : var6;
	}

	public Chunk func_659_a(World var1, int var2, int var3) throws IOException {
		File var4 = this.func_665_a(var2, var3);
		if(var4 != null && var4.exists()) {
			try {
				FileInputStream var5 = new FileInputStream(var4);
				NBTTagCompound var6 = CompressedStreamTools.func_770_a(var5);
				if(!var6.hasKey("Level")) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is missing level data, skipping");
					return null;
				}

				if(!var6.getCompoundTag("Level").hasKey("Blocks")) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is missing block data, skipping");
					return null;
				}

				Chunk var7 = func_664_a(var1, var6.getCompoundTag("Level"));
				if(!var7.func_351_a(var2, var3)) {
					System.out.println("Chunk file at " + var2 + "," + var3 + " is in the wrong location; relocating. (Expected " + var2 + ", " + var3 + ", got " + var7.xPosition + ", " + var7.zPosition + ")");
					var6.setInteger("xPos", var2);
					var6.setInteger("zPos", var3);
					var7 = func_664_a(var1, var6.getCompoundTag("Level"));
				}

				return var7;
			} catch (Exception var8) {
				var8.printStackTrace();
			}
		}

		return null;
	}

	public void func_662_a(World var1, Chunk var2) throws IOException {
		var1.func_476_g();
		File var3 = this.func_665_a(var2.xPosition, var2.zPosition);
		if(var3.exists()) {
			var1.sizeOnDisk -= var3.length();
		}

		try {
			File var4 = new File(this.field_945_a, "tmp_chunk.dat");
			FileOutputStream var5 = new FileOutputStream(var4);
			NBTTagCompound var6 = new NBTTagCompound();
			NBTTagCompound var7 = new NBTTagCompound();
			var6.setTag("Level", var7);
			this.func_663_a(var2, var1, var7);
			CompressedStreamTools.func_769_a(var6, var5);
			var5.close();
			if(var3.exists()) {
				var3.delete();
			}

			var4.renameTo(var3);
			var1.sizeOnDisk += var3.length();
		} catch (Exception var8) {
			var8.printStackTrace();
		}

	}

	public void func_663_a(Chunk var1, World var2, NBTTagCompound var3) {
		var2.func_476_g();
		var3.setInteger("xPos", var1.xPosition);
		var3.setInteger("zPos", var1.zPosition);
		var3.setLong("LastUpdate", var2.worldTime);
		var3.setByteArray("Blocks", var1.blocks);
		var3.setByteArray("Data", var1.data.data);
		var3.setByteArray("SkyLight", var1.skylightMap.data);
		var3.setByteArray("BlockLight", var1.blocklightMap.data);
		var3.setByteArray("HeightMap", var1.heightMap);
		var3.setBoolean("TerrainPopulated", var1.isTerrainPopulated);
		var1.field_677_r = false;
		NBTTagList var4 = new NBTTagList();

		Iterator var6;
		NBTTagCompound var8;
		for(int var5 = 0; var5 < var1.entities.length; ++var5) {
			var6 = var1.entities[var5].iterator();

			while(var6.hasNext()) {
				Entity var7 = (Entity)var6.next();
				var1.field_677_r = true;
				var8 = new NBTTagCompound();
				if(var7.func_95_c(var8)) {
					var4.setTag(var8);
				}
			}
		}

		var3.setTag("Entities", var4);
		NBTTagList var9 = new NBTTagList();
		var6 = var1.field_683_l.values().iterator();

		while(var6.hasNext()) {
			TileEntity var10 = (TileEntity)var6.next();
			var8 = new NBTTagCompound();
			var10.writeToNBT(var8);
			var9.setTag(var8);
		}

		var3.setTag("TileEntities", var9);
	}

	public static Chunk func_664_a(World var0, NBTTagCompound var1) {
		int var2 = var1.getInteger("xPos");
		int var3 = var1.getInteger("zPos");
		Chunk var4 = new Chunk(var0, var2, var3);
		var4.blocks = var1.getByteArray("Blocks");
		var4.data = new NibbleArray(var1.getByteArray("Data"));
		var4.skylightMap = new NibbleArray(var1.getByteArray("SkyLight"));
		var4.blocklightMap = new NibbleArray(var1.getByteArray("BlockLight"));
		var4.heightMap = var1.getByteArray("HeightMap");
		var4.isTerrainPopulated = var1.getBoolean("TerrainPopulated");
		if(!var4.data.isValid()) {
			var4.data = new NibbleArray(var4.blocks.length);
		}

		if(var4.heightMap == null || !var4.skylightMap.isValid()) {
			var4.heightMap = new byte[256];
			var4.skylightMap = new NibbleArray(var4.blocks.length);
			var4.func_353_b();
		}

		if(!var4.blocklightMap.isValid()) {
			var4.blocklightMap = new NibbleArray(var4.blocks.length);
			var4.func_348_a();
		}

		NBTTagList var5 = var1.getTagList("Entities");
		if(var5 != null) {
			for(int var6 = 0; var6 < var5.tagCount(); ++var6) {
				NBTTagCompound var7 = (NBTTagCompound)var5.tagAt(var6);
				Entity var8 = EntityList.func_566_a(var7, var0);
				var4.field_677_r = true;
				if(var8 != null) {
					var4.addEntity(var8);
				}
			}
		}

		NBTTagList var10 = var1.getTagList("TileEntities");
		if(var10 != null) {
			for(int var11 = 0; var11 < var10.tagCount(); ++var11) {
				NBTTagCompound var12 = (NBTTagCompound)var10.tagAt(var11);
				TileEntity var9 = TileEntity.createAndLoadEntity(var12);
				if(var9 != null) {
					var4.func_349_a(var9);
				}
			}
		}

		return var4;
	}

	public void func_661_a() {
	}

	public void func_660_b() {
	}

	public void func_4104_b(World var1, Chunk var2) throws IOException {
	}
}
