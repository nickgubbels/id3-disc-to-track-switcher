package nl.nicolascode.id3.disc2track.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.cli.Options;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;

import nl.nicolascode.id3.disc2track.Settings;

public class OptionsModule implements Module
{
    @Override
    public void configure(final Binder binder)
    {
        // Binding is done in this class through a provider.
        // See the provideOptions method.
    }

    @Provides
    static Options provideOptions(final CLIOptions cliOptions)
    {
        final Options options = new Options();

        final Class<Settings> obj = Settings.class;
        for (final Method m : obj.getMethods())
        {
            final CLIOption cliOption = m.getAnnotation(CLIOption.class);
            if (cliOption != null)
            {
                options.addOption(cliOption.opt(), cliOption.longOpt(), cliOption.hasArguments(),
                                cliOption.description());
                cliOptions.addOption(cliOption.longOpt(), (settings, value) ->
                    {
                        try
                        {
                            m.invoke(settings, value);
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    });
            }
        }

        return options;
    }
}
