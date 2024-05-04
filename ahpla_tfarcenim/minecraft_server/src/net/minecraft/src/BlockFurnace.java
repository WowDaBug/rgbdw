package net.minecraft.src;

import java.util.Random;

public class BlockFurnace extends BlockContainer {
	private final boolean field_655_a;
	private Random field_656_a = new Random();

	protected BlockFurnace(int var1, boolean var2) {
		super(var1, Material.rock);
		this.field_655_a = var2;
		this.blockIndexInTexture = 45;
	}

	public int idDropped(int var1, Random var2) {
		return Block.stoneOvenIdle.blockID;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		super.onBlockAdded(var1, var2, var3, var4);
		this.func_296_g(var1, var2, var3, var4);
	}
	
	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		TileEntityFurnace var5 = (TileEntityFurnace)var1.getBlock(var2, var3, var4);

		for(int var6 = 0; var6 < var5.func_83_a(); ++var6) {
			ItemStack var7 = var5.getStackInSlot(var6);
			if(var7 != null) {
				float var8 = this.field_656_a.nextFloat() * 0.8F + 0.1F;
				float var9 = this.field_656_a.nextFloat() * 0.8F + 0.1F;
				float var10 = this.field_656_a.nextFloat() * 0.8F + 0.1F;

				while(var7.stackSize > 0) {
					int var11 = this.field_656_a.nextInt(21) + 10;
					if(var11 > var7.stackSize) {
						var11 = var7.stackSize;
					}

					var7.stackSize -= var11;
					EntityItem var12 = new EntityItem(var1, (double)((float)var2 + var8), (double)((float)var3 + var9), (double)((float)var4 + var10), new ItemStack(var7.itemID, var11, var7.itemDamage));
					float var13 = 0.05F;
					var12.motionX = (double)((float)this.field_656_a.nextGaussian() * var13);
					var12.motionY = (double)((float)this.field_656_a.nextGaussian() * var13 + 0.2F);
					var12.motionZ = (double)((float)this.field_656_a.nextGaussian() * var13);
					var1.entityJoinedWorld(var12);
				}
			}
		}

		super.onBlockRemoval(var1, var2, var3, var4);
	}

	private void func_296_g(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3, var4 - 1);
		int var6 = var1.getBlockId(var2, var3, var4 + 1);
		int var7 = var1.getBlockId(var2 - 1, var3, var4);
		int var8 = var1.getBlockId(var2 + 1, var3, var4);
		byte var9 = 3;
		if(Block.field_540_p[var5] && !Block.field_540_p[var6]) {
			var9 = 3;
		}

		if(Block.field_540_p[var6] && !Block.field_540_p[var5]) {
			var9 = 2;
		}

		if(Block.field_540_p[var7] && !Block.field_540_p[var8]) {
			var9 = 5;
		}

		if(Block.field_540_p[var8] && !Block.field_540_p[var7]) {
			var9 = 4;
		}

		var1.setBlockMetadataWithNotify(var2, var3, var4, var9);
	}

	public int getBlockTextureFromSide(int var1) {
		return var1 == 1 ? Block.stone.blockID : (var1 == 0 ? Block.stone.blockID : (var1 == 3 ? this.blockIndexInTexture - 1 : this.blockIndexInTexture));
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		TileEntityFurnace var6 = (TileEntityFurnace)var1.getBlock(var2, var3, var4);
		var5.func_170_a(var6);
		return true;
	}

	public static void func_295_a(boolean var0, World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockMetadata(var2, var3, var4);
		TileEntity var6 = var1.getBlock(var2, var3, var4);
		if(var0) {
			var1.setBlockWithNotify(var2, var3, var4, Block.stoneOvenActive.blockID);
		} else {
			var1.setBlockWithNotify(var2, var3, var4, Block.stoneOvenIdle.blockID);
		}

		var1.setBlockMetadataWithNotify(var2, var3, var4, var5);
		var1.func_473_a(var2, var3, var4, var6);
	}

	protected TileEntity func_294_a_() {
		return new TileEntityFurnace();
	}

	public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
		int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(var6 == 0) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
		}

		if(var6 == 1) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 5);
		}

		if(var6 == 2) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
		}

		if(var6 == 3) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 4);
		}

	}
}
