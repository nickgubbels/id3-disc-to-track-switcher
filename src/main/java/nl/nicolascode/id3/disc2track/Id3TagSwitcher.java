package nl.nicolascode.id3.disc2track;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.NotSupportedException;
import com.mpatric.mp3agic.UnsupportedTagException;

import nl.nicolascode.id3.disc2track.cli.ProgressBar;

public class Id3TagSwitcher
{
    private final static String EXTENSION = "mp3";
    private final Settings settings;

    public Id3TagSwitcher(final Settings settings)
    {
        this.settings = settings;
    }

    public void doWork()
    {
        final File directory = new File(this.settings.getDirectory());
        final Collection<File> allFiles = FileUtils.listFiles(directory, new FileFilterByExtension(EXTENSION),
                        TrueFileFilter.INSTANCE);

        if (allFiles.size() == 0)
        {
            return;
        }

        final ProgressBar progressBar = new ProgressBar(50, 0, allFiles.size());
        progressBar.printProgress(System.out);
        for (final File file : allFiles)
        {
            handleSingleFile(file);
            progressBar.increment();
            progressBar.printProgress(System.out);
        }
        System.out.println();
    }

    private void handleSingleFile(final File file)
    {
        try
        {
            final Mp3File mp3file = new Mp3File(file);
            if (mp3file.hasId3v2Tag())
            {
                final ID3v2 tag = mp3file.getId3v2Tag();

                final String discNr = tag.getPartOfSet();
                final String trackNr = tag.getTrack();
                if (discNr != null && trackNr != null)
                {
                    final Optional<String> newTrackNr = newTrackNr(discNr, trackNr);

                    if (settings.isPrintOnly())
                    {
                        System.out.printf("\r%s: [disc: %s, track: %s, newTrack: %s]\r\n", file.getAbsolutePath(),
                                        discNr, trackNr, newTrackNr.orElse(trackNr));
                    }
                    else if (newTrackNr.isPresent())
                    {
                        tag.setTrack(newTrackNr.get());
                        mp3file.setId3v2Tag(tag);

                        saveMp3File(file, mp3file);
                    }
                }
            }
        }
        catch (UnsupportedTagException | InvalidDataException | IOException e)
        {
            // ToDo exception handling
            e.printStackTrace();
            return;
        }
    }

    private Optional<String> newTrackNr(final String discNr, final String trackNr)
    {
        if (!settings.shouldRemove())
        {
            return newTrackNrAfterAddition(discNr, trackNr);
        }
        else
        {
            return newTrackAfterRemoval(discNr, trackNr);
        }
    }

    private Optional<String> newTrackNrAfterAddition(final String discNr, final String trackNr)
    {
        if (settings.isForced() || !trackNr.startsWith(discNr) || trackNr.length() <= 2)
        {
            return Optional.of(discNr + trackNr);
        }
        else
        {
            return Optional.empty();
        }
    }

    private Optional<String> newTrackAfterRemoval(final String discNr, final String trackNr)
    {
        if (settings.isForced() || (trackNr.startsWith(discNr) && trackNr.length() > 2))
        {
            return Optional.of(trackNr.substring(discNr.length()));
        }
        else
        {
            return Optional.empty();
        }
    }

    private static void saveMp3File(final File originalFile, final Mp3File mp3file)
    {
        final File newfile = new File(originalFile.getAbsolutePath() + ".new");

        try
        {
            mp3file.save(newfile.getAbsolutePath());
            originalFile.delete();
            newfile.renameTo(originalFile.getAbsoluteFile());
        }
        catch (NotSupportedException | IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
