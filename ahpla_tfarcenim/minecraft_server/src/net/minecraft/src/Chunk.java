package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Chunk {
	public static boolean field_694_a;
	public byte[] blocks;
	public boolean field_692_c;
	public World worldObj;
	public NibbleArray data;
	public NibbleArray skylightMap;
	public NibbleArray blocklightMap;
	public byte[] heightMap;
	public int field_686_i;
	public final int xPosition;
	public final int zPosition;
	public Map field_683_l;
	public List[] entities;
	public boolean isTerrainPopulated;
	public boolean isModified;
	public boolean field_679_p;
	public boolean field_678_q;
	public boolean field_677_r;
	public long field_676_s;

	public Chunk(World var1, int var2, int var3) {
		this.field_683_l = new HashMap();
		this.entities = new List[8];
		this.isTerrainPopulated = false;
		this.isModified = false;
		this.field_678_q = false;
		this.field_677_r = false;
		this.field_676_s = 0L;
		this.worldObj = var1;
		this.xPosition = var2;
		this.zPosition = var3;
		this.heightMap = new byte[256];

		for(int var4 = 0; var4 < this.entities.length; ++var4) {
			this.entities[var4] = new ArrayList();
		}

	}

	public Chunk(World var1, byte[] var2, int var3, int var4) {
		this(var1, var3, var4);
		this.blocks = var2;
		this.data = new NibbleArray(var2.length);
		this.skylightMap = new NibbleArray(var2.length);
		this.blocklightMap = new NibbleArray(var2.length);
	}

	public boolean func_351_a(int var1, int var2) {
		return var1 == this.xPosition && var2 == this.zPosition;
	}

	public int getHeightValue(int var1, int var2) {
		return this.heightMap[var2 << 4 | var1] & 255;
	}

	public void func_348_a() {
	}

	public void func_353_b() {
		int var1 = 127;

		int var2;
		int var3;
		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				this.heightMap[var3 << 4 | var2] = -128;
				this.func_339_g(var2, 127, var3);
				if((this.heightMap[var3 << 4 | var2] & 255) < var1) {
					var1 = this.heightMap[var3 << 4 | var2] & 255;
				}
			}
		}

		this.field_686_i = var1;

		for(var2 = 0; var2 < 16; ++var2) {
			for(var3 = 0; var3 < 16; ++var3) {
				this.func_333_c(var2, var3);
			}
		}

		this.isModified = true;
	}

	public void func_4053_c() {
		byte var1 = 32;

		for(int var2 = 0; var2 < 16; ++var2) {
			for(int var3 = 0; var3 < 16; ++var3) {
				int var4 = var2 << 11 | var3 << 7;

				int var5;
				int var6;
				for(var5 = 0; var5 < 128; ++var5) {
					var6 = Block.lightValue[this.blocks[var4 + var5]];
					if(var6 > 0) {
						this.blocklightMap.setNibble(var2, var5, var3, var6);
					}
				}

				var5 = 15;

				for(var6 = var1 - 2; var6 < 128 && var5 > 0; this.blocklightMap.setNibble(var2, var6, var3, var5)) {
					++var6;
					byte var7 = this.blocks[var4 + var6];
					int var8 = Block.lightOpacity[var7];
					int var9 = Block.lightValue[var7];
					if(var8 == 0) {
						var8 = 1;
					}

					var5 -= var8;
					if(var9 > var5) {
						var5 = var9;
					}

					if(var5 < 0) {
						var5 = 0;
					}
				}
			}
		}

		this.worldObj.func_483_a(EnumSkyBlock.Block, this.xPosition * 16, var1 - 1, this.zPosition * 16, this.xPosition * 16 + 16, var1 + 1, this.zPosition * 16 + 16);
		this.isModified = true;
	}

	private void func_333_c(int var1, int var2) {
		int var3 = this.getHeightValue(var1, var2);
		int var4 = this.xPosition * 16 + var1;
		int var5 = this.zPosition * 16 + var2;
		this.func_355_f(var4 - 1, var5, var3);
		this.func_355_f(var4 + 1, var5, var3);
		this.func_355_f(var4, var5 - 1, var3);
		this.func_355_f(var4, var5 + 1, var3);
	}

	private void func_355_f(int var1, int var2, int var3) {
		int var4 = this.worldObj.getHeightValue(var1, var2);
		if(var4 > var3) {
			this.worldObj.func_483_a(EnumSkyBlock.Sky, var1, var3, var2, var1, var4, var2);
		} else if(var4 < var3) {
			this.worldObj.func_483_a(EnumSkyBlock.Sky, var1, var4, var2, var1, var3, var2);
		}

		this.isModified = true;
	}

	private void func_339_g(int var1, int var2, int var3) {
		int var4 = this.heightMap[var3 << 4 | var1] & 255;
		int var5 = var4;
		if(var2 > var4) {
			var5 = var2;
		}

		for(int var6 = var1 << 11 | var3 << 7; var5 > 0 && Block.lightOpacity[this.blocks[var6 + var5 - 1]] == 0; --var5) {
		}

		if(var5 != var4) {
			this.worldObj.func_498_f(var1, var3, var5, var4);
			this.heightMap[var3 << 4 | var1] = (byte)var5;
			int var7;
			int var8;
			int var9;
			if(var5 < this.field_686_i) {
				this.field_686_i = var5;
			} else {
				var7 = 127;

				for(var8 = 0; var8 < 16; ++var8) {
					for(var9 = 0; var9 < 16; ++var9) {
						if((this.heightMap[var9 << 4 | var8] & 255) < var7) {
							var7 = this.heightMap[var9 << 4 | var8] & 255;
						}
					}
				}

				this.field_686_i = var7;
			}

			var7 = this.xPosition * 16 + var1;
			var8 = this.zPosition * 16 + var3;
			if(var5 < var4) {
				for(var9 = var5; var9 < var4; ++var9) {
					this.skylightMap.setNibble(var1, var9, var3, 15);
				}
			} else {
				this.worldObj.func_483_a(EnumSkyBlock.Sky, var7, var4, var8, var7, var5, var8);

				for(var9 = var4; var9 < var5; ++var9) {
					this.skylightMap.setNibble(var1, var9, var3, 0);
				}
			}

			var9 = 15;

			int var10;
			for(var10 = var5; var5 > 0 && var9 > 0; this.skylightMap.setNibble(var1, var5, var3, var9)) {
				--var5;
				int var11 = Block.lightOpacity[this.getBlockID(var1, var5, var3)];
				if(var11 == 0) {
					var11 = 1;
				}

				var9 -= var11;
				if(var9 < 0) {
					var9 = 0;
				}
			}

			while(var5 > 0 && Block.lightOpacity[this.getBlockID(var1, var5 - 1, var3)] == 0) {
				--var5;
			}

			if(var5 != var10) {
				this.worldObj.func_483_a(EnumSkyBlock.Sky, var7 - 1, var5, var8 - 1, var7 + 1, var10, var8 + 1);
			}

			this.isModified = true;
		}
	}

	public int getBlockID(int var1, int var2, int var3) {
		return this.blocks[var1 << 11 | var3 << 7 | var2];
	}

	public boolean setBlockIDWithMetadata(int var1, int var2, int var3, int var4, int var5) {
		byte var6 = (byte)var4;
		int var7 = this.heightMap[var3 << 4 | var1] & 255;
		int var8 = this.blocks[var1 << 11 | var3 << 7 | var2] & 255;
		if(var8 == var4 && this.data.getNibble(var1, var2, var3) == var5) {
			return false;
		} else {
			int var9 = this.xPosition * 16 + var1;
			int var10 = this.zPosition * 16 + var3;
			this.blocks[var1 << 11 | var3 << 7 | var2] = var6;
			if(var8 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[var8].onBlockRemoval(this.worldObj, var9, var2, var10);
			}

			this.data.setNibble(var1, var2, var3, var5);
			if(!this.worldObj.field_4272_q.field_4306_c) {
				if(Block.lightOpacity[var6] != 0) {
					if(var2 >= var7) {
						this.func_339_g(var1, var2 + 1, var3);
					}
				} else if(var2 == var7 - 1) {
					this.func_339_g(var1, var2, var3);
				}

				this.worldObj.func_483_a(EnumSkyBlock.Sky, var9, var2, var10, var9, var2, var10);
			}

			this.worldObj.func_483_a(EnumSkyBlock.Block, var9, var2, var10, var9, var2, var10);
			this.func_333_c(var1, var3);
			if(var4 != 0) {
				Block.blocksList[var4].onBlockAdded(this.worldObj, var9, var2, var10);
			}

			this.data.setNibble(var1, var2, var3, var5);
			this.isModified = true;
			return true;
		}
	}

	public boolean setBlockID(int var1, int var2, int var3, int var4) {
		byte var5 = (byte)var4;
		int var6 = this.heightMap[var3 << 4 | var1] & 255;
		int var7 = this.blocks[var1 << 11 | var3 << 7 | var2] & 255;
		if(var7 == var4) {
			return false;
		} else {
			int var8 = this.xPosition * 16 + var1;
			int var9 = this.zPosition * 16 + var3;
			this.blocks[var1 << 11 | var3 << 7 | var2] = var5;
			if(var7 != 0) {
				Block.blocksList[var7].onBlockRemoval(this.worldObj, var8, var2, var9);
			}

			this.data.setNibble(var1, var2, var3, 0);
			if(Block.lightOpacity[var5] != 0) {
				if(var2 >= var6) {
					this.func_339_g(var1, var2 + 1, var3);
				}
			} else if(var2 == var6 - 1) {
				this.func_339_g(var1, var2, var3);
			}

			this.worldObj.func_483_a(EnumSkyBlock.Sky, var8, var2, var9, var8, var2, var9);
			this.worldObj.func_483_a(EnumSkyBlock.Block, var8, var2, var9, var8, var2, var9);
			this.func_333_c(var1, var3);
			if(var4 != 0 && !this.worldObj.multiplayerWorld) {
				Block.blocksList[var4].onBlockAdded(this.worldObj, var8, var2, var9);
			}

			this.isModified = true;
			return true;
		}
	}

	public int getBlockMetadata(int var1, int var2, int var3) {
		return this.data.getNibble(var1, var2, var3);
	}

	public void setBlockMetadata(int var1, int var2, int var3, int var4) {
		this.isModified = true;
		this.data.setNibble(var1, var2, var3, var4);
	}

	public int getSavedLightValue(EnumSkyBlock var1, int var2, int var3, int var4) {
		return var1 == EnumSkyBlock.Sky ? this.skylightMap.getNibble(var2, var3, var4) : (var1 == EnumSkyBlock.Block ? this.blocklightMap.getNibble(var2, var3, var4) : 0);
	}

	public void setLightValue(EnumSkyBlock var1, int var2, int var3, int var4, int var5) {
		this.isModified = true;
		if(var1 == EnumSkyBlock.Sky) {
			this.skylightMap.setNibble(var2, var3, var4, var5);
		} else {
			if(var1 != EnumSkyBlock.Block) {
				return;
			}

			this.blocklightMap.setNibble(var2, var3, var4, var5);
		}

	}

	public int getBlockLightValue(int var1, int var2, int var3, int var4) {
		int var5 = this.skylightMap.getNibble(var1, var2, var3);
		if(var5 > 0) {
			field_694_a = true;
		}

		var5 -= var4;
		int var6 = this.blocklightMap.getNibble(var1, var2, var3);
		if(var6 > var5) {
			var5 = var6;
		}

		return var5;
	}

	public void addEntity(Entity var1) {
		if(!this.field_678_q) {
			this.field_677_r = true;
			int var2 = MathHelper.floor_double(var1.posX / 16.0D);
			int var3 = MathHelper.floor_double(var1.posZ / 16.0D);
			if(var2 != this.xPosition || var3 != this.zPosition) {
				System.out.println("Wrong location! " + var1);
				Thread.dumpStack();
			}

			int var4 = MathHelper.floor_double(var1.posY / 16.0D);
			if(var4 < 0) {
				var4 = 0;
			}

			if(var4 >= this.entities.length) {
				var4 = this.entities.length - 1;
			}

			var1.field_276_Z = true;
			var1.field_307_aa = this.xPosition;
			var1.field_305_ab = var4;
			var1.field_303_ac = this.zPosition;
			this.entities[var4].add(var1);
		}
	}

	public void func_350_b(Entity var1) {
		this.func_332_a(var1, var1.field_305_ab);
	}

	public void func_332_a(Entity var1, int var2) {
		if(var2 < 0) {
			var2 = 0;
		}

		if(var2 >= this.entities.length) {
			var2 = this.entities.length - 1;
		}

		this.entities[var2].remove(var1);
	}

	public boolean canBlockSeeTheSky(int var1, int var2, int var3) {
		return var2 >= (this.heightMap[var3 << 4 | var1] & 255);
	}

	public TileEntity func_338_d(int var1, int var2, int var3) {
		ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
		TileEntity var5 = (TileEntity)this.field_683_l.get(var4);
		if(var5 == null) {
			int var6 = this.getBlockID(var1, var2, var3);
			if(!Block.isBlockContainer[var6]) {
				return null;
			}

			BlockContainer var7 = (BlockContainer)Block.blocksList[var6];
			var7.onBlockAdded(this.worldObj, this.xPosition * 16 + var1, var2, this.zPosition * 16 + var3);
			var5 = (TileEntity)this.field_683_l.get(var4);
		}

		return var5;
	}

	public void func_349_a(TileEntity var1) {
		int var2 = var1.xCoord - this.xPosition * 16;
		int var3 = var1.yCoord;
		int var4 = var1.zCoord - this.zPosition * 16;
		this.func_352_a(var2, var3, var4, var1);
	}

	public void func_352_a(int var1, int var2, int var3, TileEntity var4) {
		ChunkPosition var5 = new ChunkPosition(var1, var2, var3);
		var4.worldObj = this.worldObj;
		var4.xCoord = this.xPosition * 16 + var1;
		var4.yCoord = var2;
		var4.zCoord = this.zPosition * 16 + var3;
		if(this.getBlockID(var1, var2, var3) != 0 && Block.blocksList[this.getBlockID(var1, var2, var3)] instanceof BlockContainer) {
			if(this.field_692_c) {
				if(this.field_683_l.get(var5) != null) {
					this.worldObj.field_814_b.remove(this.field_683_l.get(var5));
				}

				this.worldObj.field_814_b.add(var4);
			}

			this.field_683_l.put(var5, var4);
		} else {
			System.out.println("Attempted to place a tile entity where there was no entity tile!");
		}
	}

	public void func_359_e(int var1, int var2, int var3) {
		ChunkPosition var4 = new ChunkPosition(var1, var2, var3);
		if(this.field_692_c) {
			this.worldObj.field_814_b.remove(this.field_683_l.remove(var4));
		}

	}

	public void func_358_c() {
		this.field_692_c = true;
		this.worldObj.field_814_b.addAll(this.field_683_l.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.func_464_a(this.entities[var1]);
		}

	}

	public void func_331_d() {
		this.field_692_c = false;
		this.worldObj.field_814_b.removeAll(this.field_683_l.values());

		for(int var1 = 0; var1 < this.entities.length; ++var1) {
			this.worldObj.func_461_b(this.entities[var1]);
		}

	}

	public void func_336_e() {
		this.isModified = true;
	}

	public void getEntitiesWithinAABBForEntity(Entity var1, AxisAlignedBB var2, List var3) {
		int var4 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(var9 != var1 && var9.boundingBox.intersectsWith(var2)) {
					var3.add(var9);
				}
			}
		}

	}

	public void getEntitiesOfTypeWithinAAAB(Class var1, AxisAlignedBB var2, List var3) {
		int var4 = MathHelper.floor_double((var2.minY - 2.0D) / 16.0D);
		int var5 = MathHelper.floor_double((var2.maxY + 2.0D) / 16.0D);
		if(var4 < 0) {
			var4 = 0;
		}

		if(var5 >= this.entities.length) {
			var5 = this.entities.length - 1;
		}

		for(int var6 = var4; var6 <= var5; ++var6) {
			List var7 = this.entities[var6];

			for(int var8 = 0; var8 < var7.size(); ++var8) {
				Entity var9 = (Entity)var7.get(var8);
				if(var1.isAssignableFrom(var9.getClass()) && var9.boundingBox.intersectsWith(var2)) {
					var3.add(var9);
				}
			}
		}

	}

	public boolean func_347_a() {
		return this.field_679_p ? false : (this.field_677_r && this.worldObj.worldTime != this.field_676_s ? true : this.isModified);
	}

	public int func_340_a(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
		int var9;
		int var10;
		int var11;
		int var12;
		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				var11 = var9 << 11 | var10 << 7 | var3;
				var12 = var6 - var3;
				System.arraycopy(this.blocks, var11, var1, var8, var12);
				var8 += var12;
			}
		}

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
				var12 = (var6 - var3) / 2;
				System.arraycopy(this.data.data, var11, var1, var8, var12);
				var8 += var12;
			}
		}

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
				var12 = (var6 - var3) / 2;
				System.arraycopy(this.blocklightMap.data, var11, var1, var8, var12);
				var8 += var12;
			}
		}

		for(var9 = var2; var9 < var5; ++var9) {
			for(var10 = var4; var10 < var7; ++var10) {
				var11 = (var9 << 11 | var10 << 7 | var3) >> 1;
				var12 = (var6 - var3) / 2;
				System.arraycopy(this.skylightMap.data, var11, var1, var8, var12);
				var8 += var12;
			}
		}

		return var8;
	}

	public Random func_334_a(long var1) {
		return new Random(this.worldObj.randomSeed + (long)(this.xPosition * this.xPosition * 4987142) + (long)(this.xPosition * 5947611) + (long)(this.zPosition * this.zPosition) * 4392871L + (long)(this.zPosition * 389711) ^ var1);
	}
}