package com.lothrazar.cyclic.block.buildershape;

import com.lothrazar.cyclic.base.ScreenBase;
import com.lothrazar.cyclic.gui.EnergyBar;
import com.lothrazar.cyclic.registry.TextureRegistry;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ScreenStructure extends ScreenBase<ContainerStructure> {

  //  private ButtonMachine btnToggle;
  private EnergyBar energy;

  public ScreenStructure(ContainerStructure screenContainer, PlayerInventory inv, ITextComponent titleIn) {
    super(screenContainer, inv, titleIn);
    this.energy = new EnergyBar(this);
    energy.max = TileStructure.MAX;
  }

  @Override
  public void init() {
    super.init();
    energy.guiLeft = guiLeft;
    energy.guiTop = guiTop;
    //    int x = guiLeft + 132, y = guiTop + 8;
    //    btnToggle = addButton(new ButtonMachine(x, y, 20, 20, "", (p) -> {
    //      container.tile.setFlowing((container.getFlowing() + 1) % 2);
    //      PacketRegistry.INSTANCE.sendToServer(new PacketTileData(0, container.tile.getFlowing(), container.tile.getPos()));
    //    }));
  }

  @Override
  public void render(int mouseX, int mouseY, float partialTicks) {
    this.renderBackground();
    super.render(mouseX, mouseY, partialTicks);
    this.renderHoveredToolTip(mouseX, mouseY);
    energy.renderHoveredToolTip(mouseX, mouseY, container.getEnergy());
  }

  @Override
  protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    //    btnToggle.setTooltip(UtilChat.lang("gui.cyclic.flowing" + container.getFlowing()));
    //    btnToggle.setTextureId(container.getFlowing() == 1 ? TextureEnum.POWER_MOVING : TextureEnum.POWER_STOP);
    this.drawButtonTooltips(mouseX, mouseY);
  }

  @Override
  protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    this.drawBackground(TextureRegistry.GUI);
    this.drawSlot(60, 20);
    energy.renderEnergy(container.getEnergy());
  }
}