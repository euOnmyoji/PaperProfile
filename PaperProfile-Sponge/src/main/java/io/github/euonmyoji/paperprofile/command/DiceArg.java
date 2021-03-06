package io.github.euonmyoji.paperprofile.command;

import io.github.euonmyoji.paperprofile.common.DiceException;
import io.github.euonmyoji.paperprofile.common.DiceExpression;
import io.github.euonmyoji.paperprofile.manager.LanguageManager;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.annotation.NonnullByDefault;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @author yinyangshi
 */
@NonnullByDefault
public class DiceArg extends CommandElement {
    protected DiceArg(@Nullable Text key) {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String next = args.next();
        try {
            return new DiceExpression(next);
        } catch (DiceException e) {
            throw args.createError(LanguageManager.getText(e.getTip(), next, e.args));
        }
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return Collections.emptyList();
    }
}
