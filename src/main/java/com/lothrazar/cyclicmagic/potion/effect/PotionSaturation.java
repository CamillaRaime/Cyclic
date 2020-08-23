/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.cyclicmagic.potion.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class PotionSaturation extends PotionBase {

  private static final double PCT_TRIGGER = 0.03;

  public PotionSaturation() {
    super("saturation", true, 0x5D4033);
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    super.performEffect(entity, amplifier);
    if (!entity.world.isRemote
        && entity instanceof EntityPlayer) {
      ((EntityPlayer) entity).getFoodStats().addStats(amplifier + 1, amplifier + 0.2F);
    }
  }

  //
  @Override
  public void tick(EntityLivingBase entity) {
    if (!entity.world.isRemote
        && entity.world.rand.nextDouble() < PCT_TRIGGER
        && entity instanceof EntityPlayer) {
      PotionEffect pot = entity.getActivePotionEffect(this);
      ((EntityPlayer) entity).getFoodStats().addStats(pot.getAmplifier() + 1, pot.getAmplifier() + 0.5F);
    }
  }
}
