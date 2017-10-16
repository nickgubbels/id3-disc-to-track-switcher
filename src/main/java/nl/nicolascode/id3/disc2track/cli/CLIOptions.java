package nl.nicolascode.id3.disc2track.cli;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import nl.nicolascode.id3.disc2track.Settings;

public class CLIOptions
{
    private final Map<String, Consumer<Settings>> flagOptions;
    private final Map<String, BiConsumer<Settings, String>> optionsWithParameters;

    public CLIOptions()
    {
        this.flagOptions = new HashMap<>();
        this.optionsWithParameters = new HashMap<>();
    }

    public void addFlagOption(final String option, final Consumer<Settings> methodToApplyForOption)
    {
        flagOptions.put(option, methodToApplyForOption);
    }

    public void addOptionWithArgument(final String option, final BiConsumer<Settings, String> methodToApplyForOption)
    {
        optionsWithParameters.put(option, methodToApplyForOption);
    }

    public Optional<Consumer<Settings>> getFlagOptionHandler(final String option)
    {
        if (flagOptions.containsKey(option))
        {
            return Optional.of(flagOptions.get(option));
        }
        else
        {
            return Optional.empty();
        }
    }

    public Optional<BiConsumer<Settings, String>> getParametrizedOptionHandler(final String option)
    {
        if (optionsWithParameters.containsKey(option))
        {
            return Optional.of(optionsWithParameters.get(option));
        }
        else
        {
            return Optional.empty();
        }
    }
}
