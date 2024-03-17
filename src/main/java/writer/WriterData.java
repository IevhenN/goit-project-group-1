package writer;

import chat.ChatSettings;

import java.util.List;
import java.util.Map;

public interface WriterData<T> {
    boolean writeData(List<ChatSettings> data, long chatId);
}
