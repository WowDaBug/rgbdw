package net.minecraft.src;

public class EntityZombie extends EntityMobs {
	public EntityZombie(World var1) {
		super(var1);
		this.field_9119_aG = "/mob/zombie.png";
		this.field_9126_bt = 0.5F;
		this.field_404_af = 5;
	}

	public void onLivingUpdate() {
		if(this.worldObj.isDaytime()) {
			float var1 = this.getEntityBrightness(1.0F);
			if(var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.field_9064_W.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
				this.field_9061_Z = 300;
			}
		}

		super.onLivingUpdate();
	}

	protected String getLivingSound() {
		return "mob.zombie";
	}

	protected String getHurtSound() {
		return "mob.zombiehurt";
	}

	protected String getDeathSound() {
		return "mob.zombiedeath";
	}

	protected int getDropItemId() {
		return Item.feather.swiftedIndex;
	}
}