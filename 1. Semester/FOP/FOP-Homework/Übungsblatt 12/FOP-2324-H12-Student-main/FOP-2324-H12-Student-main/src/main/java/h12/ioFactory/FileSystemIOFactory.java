package h12.ioFactory;

import org.tudalgo.algoutils.student.Student;

import java.io.*;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An Implementation of {@link IOFactory} for the Filesystem
 */
public class FileSystemIOFactory implements IOFactory{

    @Override
    public boolean supportsReader() {
        return true;
    }

    @Override
    public BufferedReader createReader(final String ioName) throws FileNotFoundException {
        return new BufferedReader(new FileReader(ioName));
    }

    @Override
    public boolean supportsWriter() {
        return true;
    }

    @Override
    public BufferedWriter createWriter(final String ioName) throws IOException {
        return new BufferedWriter(new FileWriter(ioName));
    }
}
