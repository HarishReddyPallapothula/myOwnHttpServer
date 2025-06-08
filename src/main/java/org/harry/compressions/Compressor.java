package org.harry.compressions;

import java.io.IOException;

public interface Compressor {
    public byte[] compress(String input) throws IOException;
}
