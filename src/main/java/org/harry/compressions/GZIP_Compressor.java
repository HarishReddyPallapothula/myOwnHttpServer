package org.harry.compressions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZIP_Compressor implements Compressor{
    @Override
    public byte[] compress(String input) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipStream = new GZIPOutputStream(byteStream)) {
            gzipStream.write(input.getBytes());
        }
        return byteStream.toByteArray();
    }
}
