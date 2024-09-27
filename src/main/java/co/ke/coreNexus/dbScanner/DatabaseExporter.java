package co.ke.coreNexus.dbScanner;

import co.ke.coreNexus.dbInitializer.models.DataModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * DB-Initializer (co.ke.coreNexus.dbScanner)
 * Created by: oloo
 * On: 27/09/2024. 21:49
 * Description: Exports database models to a file in JSON format.
 **/
public class DatabaseExporter {

    public static void exportDataModelsToFile(List<DataModel> dataModels, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), dataModels);
    }
}