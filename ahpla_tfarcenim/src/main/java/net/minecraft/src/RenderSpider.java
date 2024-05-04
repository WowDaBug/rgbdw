package net.minecraft.src;

import net.PeytonPlayz585.opengl.GL11;

public class RenderSpider extends RenderLiving {
	public RenderSpider() {
		super(new ModelSpider(), 1.0F);
		this.func_4013_a(new ModelSpider());
	}

	protected float a(EntitySpider var1) {
		return 180.0F;
	}

	protected boolean a(EntitySpider var1, int var2) {
		if(var2 != 0) {
			return false;
		} else if(var2 != 0) {
			return false;
		} else {
			this.loadTexture("/mob/spider_eyes.png");
			float var3 = (1.0F - var1.getEntityBrightness(1.0F)) * 0.5F;
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, var3);
			return true;
		}
	}

	protected float func_172_a(EntityLiving var1) {
		return this.a((EntitySpider)var1);
	}

	protected boolean func_166_a(EntityLiving var1, int var2) {
		return this.a((EntitySpider)var1, var2);
	}
}
