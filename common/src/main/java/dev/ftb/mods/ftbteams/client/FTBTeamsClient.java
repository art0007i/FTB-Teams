package dev.ftb.mods.ftbteams.client;

import dev.ftb.mods.ftbguilibrary.widget.CustomClickEvent;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.FTBTeamsCommon;
import dev.ftb.mods.ftbteams.net.MessageOpenGUI;
import dev.ftb.mods.ftbteams.net.MessageOpenGUIResponse;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;

public class FTBTeamsClient extends FTBTeamsCommon {
	public static final ResourceLocation OPEN_GUI_ID = new ResourceLocation(FTBTeams.MOD_ID, "open_gui");

	public FTBTeamsClient() {
		CustomClickEvent.EVENT.register(event -> {
			if (event.getId().equals(OPEN_GUI_ID)) {
				new MessageOpenGUI().sendToServer();
				return InteractionResult.SUCCESS;
			}
			return InteractionResult.PASS;
		});
	}

	@Override
	public void openGui(MessageOpenGUIResponse res) {
		new MyTeamScreen(res).openGui();
	}
}