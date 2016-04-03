package stellarium.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import stellarium.StellarSky;
import stellarium.api.IHourProvider;
import stellarium.api.StellarSkyAPI;
import stellarium.config.EnumViewMode;
import stellarium.stellars.StellarManager;
import stellarium.stellars.view.StellarDimensionManager;

public class StellarSkyClientHandler {
		
	@SubscribeEvent
	public void renderGameOverlay(RenderGameOverlayEvent.Post event) {
		if(event.type == RenderGameOverlayEvent.ElementType.ALL) {
			ClientSettings setting = StellarSky.proxy.getClientSettings();
			EnumViewMode viewMode = setting.getViewMode();
			if(!viewMode.showOnHUD())
				return;
			
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			StellarManager manager = StellarManager.getManager(true);
			StellarDimensionManager dimManager = StellarDimensionManager.get(Minecraft.getMinecraft().theWorld);
			
			if(dimManager == null)
				return;
			
			double currentTick = Minecraft.getMinecraft().theWorld.getWorldTime();
			double time = manager.getSkyTime(currentTick);
			double daylength = manager.getSettings().day;
			double yearlength = manager.getSettings().year;
			double date = currentTick / daylength + dimManager.getSettings().latitude / 180.0;
			double year = date / yearlength;
			
			int yr = (int)Math.floor(year);
			int day = (int)Math.floor(date - yr * yearlength);
			int tick = (int)Math.floor((date - yr * yearlength - day)*daylength);
			
			IHourProvider provider = StellarSkyAPI.getCurrentHourProvider();
			
			int hour = provider.getCurrentHour(daylength, tick);
			int minute = provider.getCurrentMinute(daylength, tick, hour);
			
			int totalhour = provider.getTotalHour(daylength);
			int totalminute = provider.getTotalMinute(daylength, totalhour);
			
			int yOffset = 0;
			
			this.drawString(fontRenderer, "hud.text.year", 5, 10*(yOffset++)+5,
					String.format("%d", yr));
			this.drawString(fontRenderer, "hud.text.day", 5, 10*(yOffset++)+5,
					String.format("%-7d", day),
					String.format("%.2f", yearlength));
			
			if(viewMode.showTick())
				this.drawString(fontRenderer, "hud.text.tick", 5, 10*(yOffset++)+5,
						String.format("%-6d", tick),
						String.format("%.2f", daylength));
			else this.drawString(fontRenderer, "hud.text.time", 5, 10*(yOffset++)+5,
					String.format("%3d", hour), 
					String.format("%02d", minute),
					String.format("%3d", totalhour),
					String.format("%02d", totalminute));
		}
	}
	
	private void drawString(FontRenderer fontRenderer, String str, int x, int y, String... obj) {
		fontRenderer.drawString(I18n.format(str, (Object[])obj), x, y, 0xff888888);
	}
	
	@SubscribeEvent
	public void onInitGui(InitGuiEvent.Post event) {
		if(event.gui instanceof GuiOptions)
		{
			if(event.gui.mc.theWorld != null) {
				boolean locked = StellarManager.getManager(true).isLocked();
				GuiButton guibutton = new GuiButton(30, event.gui.width / 2 + 5, event.gui.height / 6 + 12, 150, 20,
						locked? I18n.format("stellarsky.gui.unlock") : I18n.format("stellarsky.gui.lock"));
				event.buttonList.add(guibutton);
			}
		}
	}
	
	@SubscribeEvent
	public void onButtonActivated(ActionPerformedEvent event) {
		if(event.gui instanceof GuiOptions)
		{
			if(event.button.id == 30)
			{
				boolean locked = StellarManager.getManager(true).isLocked();
				event.gui.mc.thePlayer.sendChatMessage(String.format("/locksky %s", !locked));
				event.button.displayString = locked? I18n.format("stellarsky.gui.unlock") : I18n.format("stellarsky.gui.lock");
			}
		}
	}
}
