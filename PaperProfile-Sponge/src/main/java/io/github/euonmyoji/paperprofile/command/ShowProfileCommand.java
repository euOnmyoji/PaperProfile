package io.github.euonmyoji.paperprofile.command;

import io.github.euonmyoji.paperprofile.common.config.IPlayerConfig;
import io.github.euonmyoji.paperprofile.config.PaperDataConfig;
import io.github.euonmyoji.paperprofile.config.PluginConfig;
import io.github.euonmyoji.paperprofile.manager.LanguageManager;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.service.pagination.PaginationList;

import static io.github.euonmyoji.paperprofile.PaperProfile.plugin;
import static org.spongepowered.api.command.CommandResult.success;
import static org.spongepowered.api.text.Text.of;

/**
 * @author yinyangshi
 */
class ShowProfileCommand {
    static final CommandSpec SHOW = CommandSpec.builder()
            .arguments(GenericArguments.playerOrSource(of("user")))
            .executor((src, args) -> {
                Player user = args.<Player>getOne("user").orElseThrow(IllegalArgumentException::new);
                Task.builder().async().execute(() -> {

                    IPlayerConfig playerConfig = PluginConfig.getPlayerConfig(user.getUniqueId());
                    showByText(user, playerConfig);
                }).submit(plugin);
                return success();
            })
            .build();

    private static void showByText(Player player, IPlayerConfig playerConfig) {
        PaginationList.Builder builder = PaginationList.builder();
        builder.header(LanguageManager.getText("pp.pp", playerConfig.getName()));
        builder.padding(of("-"));
        PaperDataConfig.attributes.forEach((s, paperAttribute) -> {
            playerConfig.getAttribute(paperAttribute).toString();
        });
        builder.build().sendTo(player);
    }
}
