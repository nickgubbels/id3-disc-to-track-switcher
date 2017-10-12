package nl.nicolascode.id3.disc2track;

import java.util.Optional;

import com.google.inject.Guice;
import com.google.inject.Injector;

import nl.nicolascode.id3.disc2track.cli.CLIHandler;
import nl.nicolascode.id3.disc2track.cli.CLIOptionsModule;
import nl.nicolascode.id3.disc2track.cli.CommandLineParserModule;
import nl.nicolascode.id3.disc2track.cli.OptionsModule;

public class App
{
    public static void main(final String[] args)
    {
        final Injector injector = Guice.createInjector(new OptionsModule(), new CommandLineParserModule(),
                        new CLIOptionsModule());

        final CLIHandler cliHandler = injector.getInstance(CLIHandler.class);

        final Optional<Settings> settings = cliHandler.handle(args);

        if (!settings.isPresent())
        {
            return;
        }

        final Id3TagSwitcher switcher = new Id3TagSwitcher(settings.get());
        switcher.doWork();
    }
}
