package nl.nicolascode.id3.disc2track.cli;

import java.io.PrintStream;
import java.util.Collections;

public class ProgressBar
{
    private int current;
    private final int total;
    private final int barLength;

    public ProgressBar(final int barLength, final int current, final int total)
    {
        this.barLength = barLength;
        this.total = total;
        this.current = Math.min(current, total);
    }

    public void increment()
    {
        if (!isFinished())
        {
            current++;
        }
    }

    public boolean isFinished()
    {
        return current == total;
    }

    public void printProgress(final PrintStream stream)
    {
        stream.print(toString() + '\r');
    }

    @Override
    public String toString()
    {
        return String.format("[%s] %d/%d", getProgressBarArrow(), current, total);
    }

    private String getProgressBarArrow()
    {
        final int arrowLength = total > 0 ? (current * barLength) / total : barLength;

        final StringBuilder builder = new StringBuilder();

        Collections.nCopies(Math.max(0, arrowLength - 1), "=").stream().forEach(builder::append);
        builder.append(isFinished() ? "=" : ">");
        Collections.nCopies(barLength - arrowLength, " ").stream().forEach(builder::append);

        return builder.toString();
    }
}
