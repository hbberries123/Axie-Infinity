package axie;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class AxieCore extends ListenerAdapter {

    private final ArrayList<Integer> axieIds = new ArrayList<>();

    private Timer timer;

    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {

        try {

            if (event.getName().equals("stop")) {
                timer.cancel();
                System.out.println("[AxiesCore] :: Axies Listing has been stopped.");
                event.getInteraction().reply("Stopped Axies Listing.").setEphemeral(true).queue();
            }

            if (event.getName().equals("axies-recently-listed")) {

                event.getInteraction().reply("Loading...").setEphemeral(true).queue();

                long delay = 5000L;

                timer = new Timer();

                timer.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        try {

                            int axiesToLoad = 5;

                            List<AxieModel> axies = AxieLatestDataHandler.processAxies(axiesToLoad);

                            double price = Integer.parseInt(Objects.requireNonNull(event.getOption("price")).getAsString());

                            MessageChannel channel = Objects.requireNonNull(event.getOption("channel")).getAsMessageChannel();

                            if (event.getOption("abilities") != null) {

                                String[] options = Objects.requireNonNull(event.getOption("abilities")).getAsString().split(", ");

                                List<String> filters = new ArrayList<>(Arrays.asList(options));

                                printAxie(event, axies, price, filters, channel);

                            } else {

                                printAxie(event, axies, price, channel);

                            }

                        } catch (Exception timerException) {
                            System.out.println("[Axies Loader] :: " + timerException.getMessage());
                        }

                    }

                }, delay, 3000L);

            }

        } catch (Exception ex) {
            System.out.println("[AxieCore] :: " + ex.getMessage());
        }

    }

    private void printAxie(@NotNull SlashCommandEvent event, List<AxieModel> axieList, double price, List<String> filters, MessageChannel channel) {
        for (AxieModel axie : axieList) {

            if (!axieIds.contains(axie.getId())) {

                if (axie.getAbilities().containsAll(filters) && axie.getPrice() <= price) {

                    sendEmbedBuilder(event, axie, channel);

                }

            }

            axieIds.add(axie.getId());
        }
    }

    private void printAxie(@NotNull SlashCommandEvent event, List<AxieModel> axieList, double price, MessageChannel channel) {
        for (AxieModel axie : axieList) {

            if (!axieIds.contains(axie.getId())) {

                if (axie.getPrice() <= price) {
                    sendEmbedBuilder(event, axie, channel);
                }

            }

            axieIds.add(axie.getId());
        }
    }

    private void sendEmbedBuilder(@NotNull SlashCommandEvent event, AxieModel axie, MessageChannel channel) {

        StringBuilder abilities = new StringBuilder();

        for (int i = 2; i < axie.getAbilities().size(); i++) {
            abilities.append(axie.getAbilities().get(i)).append(", ");
        }

        StringBuilder bodyParts = new StringBuilder();

        for (int i = 0; i < 2; i++) {
            bodyParts.append(axie.getAbilities().get(i)).append(", ");
        }

        channel.sendMessage(
                new EmbedBuilder()
                        .setImage(axie.getImage())
                        .setTitle(axie.getName(), axie.getUrl())
                        .addField("Brrs", "" + axie.getBreedCount() + "/7", true)
                        .addField("Price", "$" + axie.getPrice(), true)
                        .addField("Body Parts", ""+ bodyParts + "", false)
                        .addField("Abilities", ""+ abilities + "", false)
                        .setTimestamp(event.getTimeCreated())
                        .build()
        ).queue();
    }

}
