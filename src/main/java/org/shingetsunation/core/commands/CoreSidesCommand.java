package org.shingetsunation.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingetsunation.core.Core;
import org.shingetsunation.core.database.Database;
import org.shingetsunation.core.database.Query;
import org.shingetsunation.core.utils.CPlayer;
import org.shingetsunation.core.utils.PlayerSide;
import org.shingetsunation.core.utils.TeamManager;
import org.shingetsunation.core.utils.Utils;

import java.sql.PreparedStatement;

public class CoreSidesCommand implements CommandExecutor {
    Core core = Core.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            Bukkit.getLogger().warning(ChatColor.DARK_RED + "Only players can execute this command");
            return true;
        }
        String crescent = PlayerSide.CRESCENT.get();
        String gibbous = PlayerSide.GIBBOUS.get();
        String neutral = PlayerSide.NEUTRAL.get();

        Player p = (Player) sender;

        if (!p.hasPermission("coresides.admin")) {
            Utils.sendMessage(p, "messages.no-permission");
            return true;
        }

        if (args.length == 0)
            Utils.sendMultilineMessage(p, "messages.help");
        else {
            switch (args[0].toLowerCase()) {
                case "change":
                    if (args.length > 2) {
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == null) {
                            Utils.sendMessage(p, "messages.invalid-player");
                            return true;
                        }

                        CPlayer cp = CPlayer.getCPlayer(target);
                        assert cp != null;

                        switch (args[2].toLowerCase()) {
                            case "neutral":
                                if (cp.getSide().equalsIgnoreCase(neutral)) {
                                    Utils.sendMessage(p, "messages.same-side");
                                    return true;
                                }

                                this.execute(target, neutral);

                                Utils.sendMessage(p, "messages.success-command");
                                break;
                            case "gibbous":
                                if (cp.getSide().equalsIgnoreCase(gibbous)) {
                                    Utils.sendMessage(p, "messages.same-side");
                                    return true;
                                }

                                this.execute(target, gibbous);

                                Utils.sendMessage(p, "messages.success-command");
                                break;
                            case "crescent":
                                if (cp.getSide().equalsIgnoreCase(crescent)) {
                                    Utils.sendMessage(p, "messages.same-side");
                                    return true;
                                }

                                this.execute(target, crescent);

                                Utils.sendMessage(p, "messages.success-command");
                                break;
                            default:
                                Utils.sendMessage(p, "messages.invalid-side");
                                break;
                        }
                    } else // args not enough
                        Utils.sendMultilineMessage(p, "messages.change-help");
                    return true;
                case "check":
                    if (args.length > 1) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            Utils.sendMessage(p, "messages.invalid-player");
                            return true;
                        }

                        String side = "";
                        CPlayer cPlayer = CPlayer.getCPlayer(target);
                        if (cPlayer != null)
                            side = cPlayer.getSide();

                        Utils.sendMessageText(p, Utils.getString("messages.check-command")
                                .replace("%player%", target.getName())
                                .replace("%side%", side));
                    } else // args not enough
                        Utils.sendMultilineMessage(p, "messages.check-help");
                    return true;
                case "reset":
                    if (args.length > 1) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            Utils.sendMessage(p, "messages.invalid-player");
                            return true;
                        }

                        this.execute(target, neutral);

                        TeamManager.updateScoreboard(p, neutral);
                        Utils.sendMessage(p, "messages.success-command");
                    } else // args not enough
                        Utils.sendMultilineMessage(p, "messages.reset-help");
                    return true;
                case "update":
                    if (args.length > 1) {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == null) {
                            Utils.sendMessage(p, "messages.invalid-player");
                            return true;
                        }
                        TeamManager.updateScoreboard(target, CPlayer.getCPlayer(target).getSide());
                        Utils.sendMessage(p, "messages.success-command");
                    }
                default: // none of the arguments are correct
                    Utils.sendMultilineMessage(p, "messages.help");
                    return true;
            }
        }
        return true;
    }

    private void execute(Player p, String side) {
        Bukkit.getScheduler().runTaskAsynchronously(core, () -> Database.execute(conn -> {
            PreparedStatement statement = conn.prepareStatement(Query.CHANGE_SIDE.getQuery());
            statement.setString(1, side);
            statement.setString(2, p.getUniqueId() + "");
            statement.executeUpdate();
            statement.close();

            CPlayer.replaceCPlayer(p, side);
            TeamManager.updateScoreboard(p, side);
        }));
    }

}
