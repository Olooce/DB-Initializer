package co.ke.coreNexus.dbInitializer;

import co.ke.coreNexus.dbInitializer.models.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * DB-Initializer (co.ke.coreNexus)
 * Created by: oloo
 * On: 27/09/2024. 21:53
 * Description: Loads data models from a JSON file for database initialization.
 **/
public class ModelLoader {
    public static List<DataModel> loadDataModelsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), objectMapper.getTypeFactory().constructCollectionType(List.class, DataModel.class));
    }
}
