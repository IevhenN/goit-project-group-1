package writer;

import java.util.List;

public interface WriterData<T> {
    boolean writeData(List<Object> responses, String fileName);
}
