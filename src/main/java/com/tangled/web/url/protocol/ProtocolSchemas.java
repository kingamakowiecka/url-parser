package com.tangled.web.url.protocol;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public enum ProtocolSchemas {

    INSTANCE;

    private static List<String> uriSchemas = new ArrayList<>();

    public static ProtocolSchemas getInstance() throws IOException {
        if (uriSchemas.isEmpty()) {
            prepareUriSchemas();
        }

        return INSTANCE;
    }

    public List<String> getUriSchemas() {
        return uriSchemas;
    }

    private static void prepareUriSchemas() throws IOException {
        final Reader reader = new InputStreamReader(
                new URL("http://www.iana.org/assignments/uri-schemes/uri-schemes-1.csv").openStream(), "UTF-8");
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withSkipHeaderRecord().parse(reader);
        records.forEach(r -> uriSchemas.add(r.get(0)));
    }
}
