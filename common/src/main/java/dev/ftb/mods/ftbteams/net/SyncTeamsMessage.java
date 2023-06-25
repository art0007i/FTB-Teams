package dev.ftb.mods.ftbteams.net;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import dev.ftb.mods.ftbteams.FTBTeams;
import dev.ftb.mods.ftbteams.client.MyTeamScreen;
import dev.ftb.mods.ftbteams.data.ClientTeamManager;
import dev.ftb.mods.ftbteams.data.Team;
import net.minecraft.network.FriendlyByteBuf;

import java.util.UUID;

public class SyncTeamsMessage extends BaseS2CMessage {
	private final ClientTeamManager manager;
	private final UUID selfTeamID;
	private final boolean fullSync;
	private final UUID selfUserID;

	SyncTeamsMessage(FriendlyByteBuf buffer) {
		manager = new ClientTeamManager(buffer);
		selfTeamID = buffer.readUUID();
		fullSync = buffer.readBoolean();
		selfUserID = buffer.readUUID();
	}

	public SyncTeamsMessage(ClientTeamManager manager, Team selfTeam, boolean fullSync, UUID selfUserID) {
		this.manager = manager;
		this.selfTeamID = selfTeam.getId();
		this.fullSync = fullSync;
		this.selfUserID = selfUserID;
	}

	@Override
	public MessageType getType() {
		return FTBTeamsNet.SYNC_TEAMS;
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		manager.write(buffer, selfTeamID);
		buffer.writeUUID(selfTeamID);
		buffer.writeBoolean(fullSync);
		buffer.writeUUID(selfUserID);
	}

	@Override
	public void handle(NetworkManager.PacketContext context) {
		ClientTeamManager.syncFromServer(manager, selfTeamID, fullSync, selfUserID);
		MyTeamScreen.refreshIfOpen();
	}
}
