package pl.komorowskidev.taxitracker.infrastructure.webclient;

import pl.komorowskidev.taxitracker.common.WebClientTimeoutException;
import pl.komorowskidev.taxitracker.domain.model.Geolocation;

public class JavaWebClient {
    public Geolocation callForGeolocation(String address) throws WebClientTimeoutException {
        throw new WebClientTimeoutException("timeout");
    }
}
