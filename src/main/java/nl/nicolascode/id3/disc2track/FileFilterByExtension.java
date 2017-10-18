package nl.nicolascode.id3.disc2track;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

public class FileFilterByExtension implements IOFileFilter
{
    private final String extension;

    public FileFilterByExtension(final String extension)
    {
        this.extension = extension;
    }

    @Override
    public boolean accept(final File file)
    {
        return file.isFile() && FilenameUtils.getExtension(file.getName()).compareToIgnoreCase(extension) == 0;
    }

    @Override
    public boolean accept(final File dir, final String name)
    {
        return FilenameUtils.getExtension(name).compareToIgnoreCase(extension) == 0;
    }
}
