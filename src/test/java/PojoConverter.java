import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

public class PojoConverter<T> {

    private final Class<T> type;

    public PojoConverter(Class<T> type){
        this.type = type;
    }

    public T convertFrom(Map input){
        Gson gson = new Gson();
        JsonElement jElement = gson.toJsonTree(input);

        return gson.fromJson(jElement, type);
    }
}
