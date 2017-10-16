package nl.nicolascode.id3.disc2track.cli;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
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

            for (final Option option : line.getOptions())
            {
                hasSettings = true;

                if (option.hasArg())
                {
                    final String value = option.getValue();
                    final Optional<BiConsumer<Settings, String>> optionHandler = cliOptions
                                    .getParametrizedOptionHandler(option.getLongOpt());
                    if (value != null && optionHandler.isPresent())
                    {
                        optionHandler.get().accept(settings, value);
                    }
                    else
                    {
                        // ToDo: error handling
                    }
                }
                else
                {
                    final Optional<Consumer<Settings>> optionHandler = cliOptions
                                    .getFlagOptionHandler(option.getLongOpt());
                    if (optionHandler.isPresent())
                    {
                        optionHandler.get().accept(settings);
                    }
                    else
                    {
                        // ToDo: error handling
                    }
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
