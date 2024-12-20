package net.minecraft.src;

import java.io.File;

public class WorldProvider {
	public World field_4302_a;
	public WorldChunkManager field_4301_b;
	public boolean field_6167_c = false;
	public boolean field_6166_d = false;
	public boolean field_4306_c = false;
	public float[] lightBrightnessTable = new float[16];
	public int field_6165_g = 0;
	private float[] field_6164_h = new float[4];
	public static int dimension = 0;

	public final void func_4093_a(World var1) {
		this.field_4302_a = var1;
		this.func_4090_a();
		this.generateLightBrightnessTable();
	}

	protected void generateLightBrightnessTable() {
		float var1 = 0.05F;

		for(int var2 = 0; var2 <= 15; ++var2) {
			float var3 = 1.0F - (float)var2 / 15.0F;
			this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
		}

	}

	protected void func_4090_a() {
		this.field_4301_b = new WorldChunkManager(this.field_4302_a);
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderGenerate(this.field_4302_a, this.field_4302_a.randomSeed);
	}

	public IChunkLoader getChunkLoader(File var1) {
		return new ChunkLoader(var1, true);
	}

	public boolean canCoordinateBeSpawn(int var1, int var2) {
		int var3 = this.field_4302_a.func_528_f(var1, var2);
		return var3 == Block.sand.blockID;
	}

	public float func_4089_a(long var1, float var3) {
		int var4 = (int)(var1 % 24000L);
		float var5 = ((float)var4 + var3) / 24000.0F - 0.25F;
		if(var5 < 0.0F) {
			++var5;
		}

		if(var5 > 1.0F) {
			--var5;
		}

		float var6 = var5;
		var5 = 1.0F - (float)((Math.cos((double)var5 * Math.PI) + 1.0D) / 2.0D);
		var5 = var6 + (var5 - var6) / 3.0F;
		return var5;
	}

	public static WorldProvider func_4091_a(int var0) {
		dimension = var0;
		return (WorldProvider)(var0 == 0 ? new WorldProvider() : (var0 == -1 ? new WorldProviderHell() : null));
	}
}
