package me.jishuna.monstermorpher.commands;

import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jishuna.commonlib.commands.SimpleCommandHandler;
import me.jishuna.monstermorpher.MonsterMorpher;
import me.jishuna.monstermorpher.api.morph.Morph;
import me.jishuna.monstermorpher.api.player.MorphPlayer;

public class MorphTestCommand extends SimpleCommandHandler {
	private final MonsterMorpher plugin;
	
	public MorphTestCommand(MonsterMorpher plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
		Player player = (Player) sender;
		Optional<MorphPlayer> morphPlayer = plugin.getPlayerManager().getPlayer(player);
		Optional<Morph> morph = plugin.getMorphManager().getMorph(args[0]);
		
		if (morphPlayer.isPresent() && morph.isPresent()) {
			morphPlayer.get().setMorph(morph.get());
		}
		
		return true;
	}

}
