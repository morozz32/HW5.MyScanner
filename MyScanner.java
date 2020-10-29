import java.io.*;
import java.nio.charset.*;

public class MyScanner implements Closeable {
    public Reader reader;
    public char[] buffer = new char[100];
    public int start = -1;
    public int end;
    public Separator separator;
    public int counter = 0;
    
    public MyScanner(final FileReader reader, Separator separator) {
        this.reader = reader;
        this.separator = separator;
    }
   
    public MyScanner(final InputStream s, Separator separator) {
        this.reader = new InputStreamReader(s);
        this.separator = separator;
    }
    
    public MyScanner(final InputStream s, final Charset ch, Separator separator) {
        this.reader = new InputStreamReader(s, ch);
        this.separator = separator;
    }
    
    public MyScanner(final String str, Separator separator) throws IOException {
        this.reader = new FileReader(str);
        this.separator = separator;
    }    
    
    public MyScanner(final String str, final Charset ch, Separator separator) throws IOException {
        this.reader = new FileReader(str, ch);
        this.separator = separator;
    }
    
    public int readBuffer() throws IOException {
        end = reader.read(buffer);
        if (end != -1) {
            start = 0;
        }
        return end;
    }
    
    public void nextSymbol() throws IOException {
        start++;
        if (start == end) {
            readBuffer();
        }
    }
    
    public void skipSpace() throws IOException {
        if (start == -1 || start >= end) {
            readBuffer();
        }
        while (separator.isSeparator(buffer[start]) && end != -1) {
            if (buffer[start] == '\r') {
               
                nextSymbol();
                if (end != -1 && buffer[start] == '\n') {
                    nextSymbol();
                }
                counter++;
            } else {
                if (buffer[start] == '\n') {
                    counter++;
                }
                nextSymbol();
            }
        }
    }

    public boolean hasNext() throws IOException {
        skipSpace();
        if (end == -1) {
            return false;
        }
        return true;
    }
    
    public String next() throws IOException {
        if (!hasNext()) {
            return null;
        }
        StringBuilder s = new StringBuilder();
        while (end != -1 && !separator.isSeparator(buffer[start])) {
            s.append(buffer[start]);
            nextSymbol();
        }
        if (s.length() > 0) {
            return s.toString();
        }
        return null;
    }
    
    public Integer nextInt() throws IOException {
        String s = next();
        if (s == null) {
            return null;
        }
        if (s.length() >= 3 && s.charAt(0) == '0' && (s.charAt(1) == 'x' || s.charAt(1) == 'X')) {
            return Integer.parseUnsignedInt(s.substring(2), 16);
        }
        return Integer.parseInt(s);
    }
    
    public void close() throws IOException {
        reader.close();
        reader = null;
    }
    
    public int getCounter() {
        return counter;
    }
    
    public void setCounter(int val) {
        counter = val;
    }

    public void setSeparator(Separator separator) {
        this.separator = separator;
    }
                
    public interface Separator {
        public boolean isSeparator(char c);
    }
}
