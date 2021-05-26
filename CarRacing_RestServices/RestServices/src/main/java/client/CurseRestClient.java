package client;

import curse.model.Cursa;
import curse.services.rest.ServiceException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class CurseRestClient {
    public static final String URL = "http://localhost:8080/curse/curse";
    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    public Cursa[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Cursa[].class));
    }

    public Cursa create(Cursa cursa) {
        return execute(() -> restTemplate.postForObject(URL, cursa, Cursa.class));
    }

    public Cursa findOne(Integer id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id.toString()), Cursa.class));
    }

    public void update(Cursa cursa) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, cursa.getId()),cursa);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
