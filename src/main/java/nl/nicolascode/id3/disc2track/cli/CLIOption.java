package nl.nicolascode.id3.disc2track.cli;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CLIOption
{
    /**
     * @return Short single-character name of the option.
     */
    public String opt();

    /**
     * @return Long multi-character name of the option.
     */
    public String longOpt();

    /**
     * @return If an argument is required after this option.
     */
    public boolean hasArguments();

    /**
     * @return Self-documenting description.
     */
    public String description();
}
