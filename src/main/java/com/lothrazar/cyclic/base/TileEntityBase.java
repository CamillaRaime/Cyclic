package com.lothrazar.cyclic.base;

import com.lothrazar.cyclic.block.cable.TileCableEnergy;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public abstract class TileEntityBase extends TileEntity {

  public static final int FUEL_WEAK = 256;
  public static final int MENERGY = 64 * 1000;

  public TileEntityBase(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public boolean isPowered() {
    return this.getWorld().isBlockPowered(this.getPos());
  }

  protected void moveEnergy(Direction myFacingDir, int quantity) {
    IEnergyStorage handlerHere = this.getCapability(CapabilityEnergy.ENERGY, myFacingDir).orElse(null);
    if (handlerHere == null || handlerHere.getEnergyStored() == 0) {
      return;
    }
    Direction themFacingMe = myFacingDir.getOpposite();
    BlockPos posTarget = pos.offset(myFacingDir);
    TileEntity tileTarget = world.getTileEntity(posTarget);
    if (tileTarget == null) {
      return;
    }
    IEnergyStorage handlerOutput = tileTarget.getCapability(CapabilityEnergy.ENERGY, themFacingMe).orElse(null);
    if (handlerOutput == null) {
      return;
    }
    //    System.out.println("Bttery export " + myFacingDir);
    if (handlerHere != null && handlerOutput != null
        && handlerHere.canExtract() && handlerOutput.canReceive()) {
      //first simulate
      int drain = handlerHere.extractEnergy(quantity, true);
      if (drain > 0
      //          && handlerOutput.getEnergyStored() + drain <= handlerOutput.getMaxEnergyStored()
      ) {
        //now push it into output, but find out what was ACTUALLY taken
        int filled = handlerOutput.receiveEnergy(drain, false);
        //now actually drain that much from here
        handlerHere.extractEnergy(filled, false);
        if (filled > 0 && tileTarget instanceof TileCableEnergy) {
          //TODO: not so compatible with other fluid systems. itl do i guess
          TileCableEnergy cable = (TileCableEnergy) tileTarget;
          cable.updateIncomingEnergyFace(themFacingMe);
        }
      }
    }
  }

  public abstract void setField(int field, int value);
}