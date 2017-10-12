package nl.nicolascode.id3.disc2track.cli;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;

import nl.nicolascode.id3.disc2track.Settings;

public class CLIOptions
{
    private final Map<String, BiConsumer<Settings, String>> options;

    public CLIOptions()
    {
        this.options = new HashMap<>();
    }

    public void addOption(final String option, final BiConsumer<Settings, String> methodToApplyForOption)
    {
        options.put(option, methodToApplyForOption);
    }

    public Iterator<Map.Entry<String, BiConsumer<Settings, String>>> getOptionsIterator()
    {
        return options.entrySet().iterator();
    }
}
