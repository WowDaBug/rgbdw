package net.minecraft.src;

public class EntitySpider extends EntityMobs {
	public EntitySpider(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/spider.png";
		this.setSize(1.4F, 0.9F);
		this.field_9126_bt = 0.8F;
	}

	public double func_130_h() {
		return (double)this.height * 0.75D - 0.5D;
	}

	protected Entity func_158_i() {
		float var1 = this.getEntityBrightness(1.0F);
		if(var1 < 0.5F) {
			double var2 = 16.0D;
			return this.worldObj.getClosestPlayerToEntity(this, var2);
		} else {
			return null;
		}
	}

	protected String getLivingSound() {
		return "mob.spider";
	}

	protected String getHurtSound() {
		return "mob.spider";
	}

	protected String getDeathSound() {
		return "mob.spiderdeath";
	}

	protected void func_157_a(Entity var1, float var2) {
		float var3 = this.getEntityBrightness(1.0F);
		if(var3 > 0.5F && this.field_9064_W.nextInt(100) == 0) {
			this.field_389_ag = null;
		} else {
			if(var2 > 2.0F && var2 < 6.0F && this.field_9064_W.nextInt(10) == 0) {
				if(this.onGround) {
					double var4 = var1.posX - this.posX;
					double var6 = var1.posZ - this.posZ;
					float var8 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
					this.motionX = var4 / (double)var8 * 0.5D * (double)0.8F + this.motionX * (double)0.2F;
					this.motionZ = var6 / (double)var8 * 0.5D * (double)0.8F + this.motionZ * (double)0.2F;
					this.motionY = (double)0.4F;
				}
			} else {
				super.func_157_a(var1, var2);
			}

		}
	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	protected int getDropItemId() {
		return Item.silk.swiftedIndex;
	}
}
