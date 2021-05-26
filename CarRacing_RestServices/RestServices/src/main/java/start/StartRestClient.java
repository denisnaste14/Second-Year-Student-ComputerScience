package start;

import client.CurseRestClient;
import curse.model.Cursa;
import curse.services.rest.ServiceException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class StartRestClient {
    private final static CurseRestClient curseRestClient= new CurseRestClient();
    public static void main(String[] args) {
        Cursa cursaNoua= new Cursa(4000);
        cursaNoua.setId(17);
        try{
            //show(()->curseRestClient.update(cursaNoua));
            //show(()-> curseRestClient.delete(cursaNoua.getId().toString()));
            //show(()-> System.out.println(curseRestClient.findOne(16)));
            //show(()-> System.out.println(curseRestClient.create(cursaNoua)));
            show(()->{
                Cursa[] curse = curseRestClient.getAll();
                for(Cursa c :curse){
                    System.out.println(c);
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception"+ e);
        }
    }
}
