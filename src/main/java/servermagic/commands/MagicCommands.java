package servermagic.commands;

import java.net.URI;
import java.util.Optional;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

import servermagic.db.Database;
import servermagic.db.tables.Config;

public class MagicCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("setmagicurl")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(Commands.argument("url", StringArgumentType.greedyString())
                    .executes(context -> {
                        String url = StringArgumentType.getString(context, "url");
                        CommandSourceStack source = context.getSource();
                        Optional<Database> db = Database.GetDB();
                        if (db.isEmpty()) {
                            source.sendFailure(Component.literal("Database is not available."));
                            return 0;
                        }
                        Config.SetWebUrl(db.get(), url);
                        source.sendSuccess(() -> Component.literal("Magic portal URL set to: " + url), true);
                        return 1;
                    })
                )
        );

        dispatcher.register(
            Commands.literal("magic")
                .executes(context -> {
                    CommandSourceStack source = context.getSource();
                    Optional<Database> db = Database.GetDB();
                    Optional<String> url = db.isPresent() ? Config.GetWebUrl(db.get()) : Optional.empty();
                    if (url.isEmpty()) {
                        source.sendSuccess(
                            () -> Component.literal("A server admin needs to configure the web portal URL first."),
                            false
                        );
                        return 1;
                    }
                    Component message = Component.literal("Open the magic portal: ").append(
                        Component.literal(url.get()).withStyle(s -> s
                            .withClickEvent(new ClickEvent.OpenUrl(URI.create(url.get())))
                            .withColor(ChatFormatting.AQUA)
                            .withUnderlined(true))
                    );
                    source.sendSuccess(() -> message, false);
                    return 1;
                })
        );
    }

}
