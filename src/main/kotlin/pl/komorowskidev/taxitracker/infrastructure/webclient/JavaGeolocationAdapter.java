package pl.komorowskidev.taxitracker.infrastructure.webclient;

import org.springframework.stereotype.Component;
import pl.komorowskidev.taxitracker.domain.model.Geolocation;

@Component
public class JavaGeolocationAdapter {
    private JavaWebClient javaWebClient;

    public JavaGeolocationAdapter(JavaWebClient javaWebClient) {
        this.javaWebClient = javaWebClient;
    }

    public Geolocation read(String address) {
          return javaWebClient.callForGeolocation(address);
    }
}
