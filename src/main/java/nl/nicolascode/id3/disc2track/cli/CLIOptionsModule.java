package nl.nicolascode.id3.disc2track.cli;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Singleton;

public class CLIOptionsModule implements Module
{
    @Override
    public void configure(final Binder binder)
    {
        binder.bind(CLIOptions.class).in(Singleton.class);
    }
}
