package org.shingetsunation.core.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> commands = new ArrayList<>();

        if (args.length == 1) {
            if (sender.hasPermission("coresides.admin")) {
                commands.add("change");
                commands.add("check");
                commands.add("reset");
                commands.add("update");
            }
            StringUtil.copyPartialMatches(args[0], commands, completions);
            Collections.sort(completions);
            return completions;
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("change")) {
                commands.add("crescent");
                commands.add("gibbous");
                commands.add("neutral");

                StringUtil.copyPartialMatches(args[2], commands, completions);
                Collections.sort(completions);
                return completions;
            }
        }
        if (args.length > 3 && args[0].equalsIgnoreCase("change"))
            return new ArrayList<>();
        if (args.length > 2 && args[0].equalsIgnoreCase("check"))
            return new ArrayList<>();
        if (args.length > 2 && args[0].equalsIgnoreCase("reset"))
            return new ArrayList<>();

        return null;
    }
}
