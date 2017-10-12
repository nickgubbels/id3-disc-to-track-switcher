package nl.nicolascode.id3.disc2track.cli;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import com.google.inject.Binder;
import com.google.inject.Module;

public class CommandLineParserModule implements Module
{
    @Override
    public void configure(final Binder binder)
    {
        binder.bind(CommandLineParser.class).to(DefaultParser.class);
    }
}
