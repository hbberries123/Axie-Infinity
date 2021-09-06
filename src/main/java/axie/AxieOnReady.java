package axie;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

public class AxieOnReady extends ListenerAdapter {

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        CommandListUpdateAction commands = event.getJDA().getGuildById("568836899980967948").updateCommands();

        commands.addCommands(
                new CommandData("axies-recently-listed", "Searches for recently listed targets")
                        .addOptions(new OptionData(OptionType.STRING, "price", "Target will not be displayed over this price")
                                .setRequired(true)
                        )
                        .addOptions(new OptionData(OptionType.CHANNEL, "channel", "#channel to send messages")
                                .setRequired(true)
                        )
                        .addOptions(new OptionData(OptionType.STRING, "abilities", "Abilities, comma and space separate ex. Pumpkin, Carrot, Leaf Bug, Clear")
                                .setRequired(false)
                        ),
                        new CommandData("stop", "Stop sending axies")
        ).queue();

    }

}
