package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {
	private Block field_651_a;

	protected BlockStairs(int var1, Block var2) {
		super(var1, var2.blockIndexInTexture, var2.blockMaterial);
		this.field_651_a = var2;
		this.setHardness(var2.blockHardness);
		this.setResistance(var2.blockResistance / 3.0F);
		this.setStepSound(var2.stepSound);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return super.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
	}

	public boolean allowsAttachment() {
		return false;
	}

	public boolean isSideInsideCoordinate(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return super.isSideInsideCoordinate(var1, var2, var3, var4, var5);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		int var7 = var1.getBlockMetadata(var2, var3, var4);
		if(var7 == 0) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 1) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 2) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		} else if(var7 == 3) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
			this.setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F);
			super.getCollidingBoundingBoxes(var1, var2, var3, var4, var5, var6);
		}

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		this.field_651_a.onBlockClicked(var1, var2, var3, var4, var5);
	}

	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
		this.field_651_a.onBlockDestroyedByPlayer(var1, var2, var3, var4, var5);
	}

	public float func_226_a(Entity var1) {
		return this.field_651_a.func_226_a(var1);
	}

	public int idDropped(int var1, Random var2) {
		return this.field_651_a.idDropped(var1, var2);
	}

	public int quantityDropped(Random var1) {
		return this.field_651_a.quantityDropped(var1);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.field_651_a.getBlockTextureFromSide(var1);
	}

	public int tickRate() {
		return this.field_651_a.tickRate();
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
		this.field_651_a.velocityToAddToEntity(var1, var2, var3, var4, var5, var6);
	}

	public boolean isCollidable() {
		return this.field_651_a.isCollidable();
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return this.field_651_a.canCollideCheck(var1, var2);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		return this.field_651_a.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
		this.onNeighborBlockChange(var1, var2, var3, var4, 0);
		this.field_651_a.onBlockAdded(var1, var2, var3, var4);
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
		this.field_651_a.onBlockRemoval(var1, var2, var3, var4);
	}

	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
		this.field_651_a.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, var6);
	}

	public void dropBlockAsItem(World var1, int var2, int var3, int var4, int var5) {
		this.field_651_a.dropBlockAsItem(var1, var2, var3, var4, var5);
	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
		this.field_651_a.onEntityWalking(var1, var2, var3, var4, var5);
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
		this.field_651_a.updateTick(var1, var2, var3, var4, var5);
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		return this.field_651_a.blockActivated(var1, var2, var3, var4, var5);
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
		this.field_651_a.onBlockDestroyedByExplosion(var1, var2, var3, var4);
	}

	public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
		int var6 = MathHelper.floor_double((double)(var5.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if(var6 == 0) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 2);
		}

		if(var6 == 1) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 1);
		}

		if(var6 == 2) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 3);
		}

		if(var6 == 3) {
			var1.setBlockMetadataWithNotify(var2, var3, var4, 0);
		}

	}
}
