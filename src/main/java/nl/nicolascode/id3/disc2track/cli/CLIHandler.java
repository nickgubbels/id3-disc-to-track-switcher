package nl.nicolascode.id3.disc2track.cli;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.inject.Inject;

import nl.nicolascode.id3.disc2track.Settings;

public class CLIHandler
{
    Options options;
    CommandLineParser parser;
    CLIOptions cliOptions;

    @Inject
    public CLIHandler(final Options options, final CommandLineParser parser, final CLIOptions cliOptions)
    {
        this.options = options;
        this.parser = parser;
        this.cliOptions = cliOptions;
    }

    public Optional<Settings> handle(final String[] args)
    {
        if (args.length == 0)
        {
            printHelp();
            return Optional.empty();
        }

        try
        {
            final CommandLine line = parser.parse(options, args);

            boolean hasSettings = false;
            final Settings settings = new Settings();

            final Iterator<Map.Entry<String, BiConsumer<Settings, String>>> optionsIterator = cliOptions
                            .getOptionsIterator();
            while (optionsIterator.hasNext())
            {
                final Map.Entry<String, BiConsumer<Settings, String>> current = optionsIterator.next();
                if (line.hasOption(current.getKey()))
                {
                    final String value = line.getOptionValue(current.getKey());
                    hasSettings = true;
                    current.getValue().accept(settings, value != null ? value : "true");
                }
            }

            return hasSettings ? Optional.of(settings) : Optional.empty();
        }
        catch (final ParseException e)
        {
            System.err.println("Parsing error. " + e.getMessage());
            return Optional.empty();
        }
    }

    private void printHelp()
    {
        final HelpFormatter formatter = new HelpFormatter();
        // ToDo: set application name
        formatter.printHelp("test", options);
    }
}
