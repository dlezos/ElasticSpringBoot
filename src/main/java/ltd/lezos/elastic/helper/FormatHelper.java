package ltd.lezos.elastic.helper;

import java.util.Iterator;
import java.util.StringTokenizer;

final public class FormatHelper {
    public static final String print(String in) {
        StringBuffer buffer = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(in, "{}", true);
        while (tokenizer.hasMoreTokens()) {
            buffer.append(tokenizer.nextToken()).append("\n");
        }
        return buffer.toString();
    }
}
