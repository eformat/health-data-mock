package org.pophealth.health.data.mock.api;

import io.netty.util.internal.StringUtil;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.pophealth.health.data.mock.client.HealthDataService;
import org.pophealth.health.data.mock.model.HealthData;

@ApplicationScoped
public class MockDataParser {

    private final Logger log = LoggerFactory.getLogger(MockDataParser.class);

    @ConfigProperty(name="org.pophealth.mockDataPath")
    String mockDataPath;

    @Inject
    @RestClient
    HealthDataService service;

    public List<HealthData> loadData() {
        List<HealthData> data = new ArrayList<>();
        try {
            data = parseData();
            service.loadHealthData(data);
        }catch(IOException e) {
            log.error("Failed to load mock data ",e);
        }

        return data;
    }
    public List<HealthData> parseData() throws IOException {

        File file = new File(getClass().getClassLoader().getResource(mockDataPath).getFile());

        Reader reader = Files.newBufferedReader(file.toPath());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader("User",	"Steps", "Active Minutes", "Age", "Sleep RPMs",	"Sleep Hours",	"Sleep Avg RPMs",	"Heard Rate BPM")
                .withIgnoreHeaderCase()
                .withTrim());

        List<HealthData> healthData = new ArrayList<>();
        for (CSVRecord csvRecord : csvParser) {
            // Accessing values by the names assigned to each column
            String user = csvRecord.get("User");
            String steps = csvRecord.get("Steps");
            if(steps!=null && steps.equals("Steps")){
                continue;
            }

            String activeMinutes = csvRecord.get("Active Minutes");
            String age = csvRecord.get("Age");
            String sleepRpms = csvRecord.get("Sleep RPMs");

            String sleepHours = csvRecord.get("Sleep Hours");
            String sleepAvgRpms = csvRecord.get("Sleep Avg RPMs");
            String heartRate = csvRecord.get("Heard Rate BPM");

            HealthData data = new HealthData();
            if(!StringUtil.isNullOrEmpty(steps)) {
                data.setSteps(Integer.parseInt(steps));
            }
            if(!StringUtil.isNullOrEmpty(activeMinutes)) {
                data.setActiveMinutes(Integer.parseInt(activeMinutes));
            }
            if(!StringUtil.isNullOrEmpty(age)){
                data.setAge(Integer.parseInt(age));
            }
            if(!StringUtil.isNullOrEmpty(sleepRpms)) {
                data.setSleepRpms(Double.parseDouble(sleepRpms));
            }
            if(!StringUtil.isNullOrEmpty(sleepHours)) {
                data.setSleepHours(Double.parseDouble(sleepHours));
            }
            if(!StringUtil.isNullOrEmpty(sleepAvgRpms)){
                data.setSleepAvgRpms(Double.parseDouble(sleepAvgRpms));
            }
            if(!StringUtil.isNullOrEmpty(heartRate)){
                data.setHeartRateBpm(Double.parseDouble(heartRate));
            }
            healthData.add(data);
        }
        return healthData;
    }
}
