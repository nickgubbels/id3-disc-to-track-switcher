package nl.nicolascode.id3.disc2track;

import nl.nicolascode.id3.disc2track.cli.CLIOption;

public class Settings
{
    private String directory;
    private boolean force;
    private boolean printOnly;
    private boolean remove;

    public String getDirectory()
    {
        return directory;
    }

    public boolean isForced()
    {
        return force;
    }

    public boolean isPrintOnly()
    {
        return printOnly;
    }

    public boolean shouldRemove()
    {
        return remove;
    }

    @CLIOption(opt = "d", longOpt = "directory", hasArguments = true, description = "Dictory to scan for music files.")
    public void setDirectory(final String directory)
    {
        this.directory = directory;
    }

    @CLIOption(opt = "f", longOpt = "forced", hasArguments = false, description = "Force adding the disc# in front of the track#.")
    public void setIsForcedSettings(final boolean isForced)
    {
        force = isForced;
    }

    @CLIOption(opt = "p", longOpt = "print", hasArguments = false, description = "Print current disc# and track# values per file. This does not alter any file.")
    public void setPrintOnlySettings(final boolean isPrintOnly)
    {
        printOnly = isPrintOnly;
    }

    @CLIOption(opt = "r", longOpt = "remove", hasArguments = false, description = "Remove disc# from track# when present.")
    public void setRemoveOption(final boolean remove)
    {
        this.remove = remove;
    }
}
