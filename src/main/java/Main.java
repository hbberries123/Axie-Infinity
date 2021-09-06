import axie.AxieCore;
import axie.AxieOnReady;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Main extends ListenerAdapter {

    public static void main(String[] args) throws Exception {

        String DISCORD_TOKEN = "";

        JDABuilder JDA = JDABuilder.createDefault(DISCORD_TOKEN);

        JDA.addEventListeners(new Main());
        JDA.addEventListeners(new AxieOnReady());
        JDA.addEventListeners(new AxieCore());

        JDA.build();

    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {

        System.out.println("===============================");
        System.out.println("DISCORD '' BOT");
        System.out.println("===============================");

    }
}
