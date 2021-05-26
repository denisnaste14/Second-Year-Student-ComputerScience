package curse.services.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import curse.model.Cursa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import curse.repository.interfaces.ICursaRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/curse/curse")
public class CurseController {

    @Autowired
    private ICursaRepository cursaRepository;

    private static final String template = "Hello, %s!";

    @RequestMapping("/salutari")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method=RequestMethod.GET)
    public Cursa[] getAll(){
        Iterable<Cursa> curse =cursaRepository.findAll();
        List<Cursa> cursaList= StreamSupport.stream(curse.spliterator(),false).collect(Collectors.toList());
        Cursa[] crs = cursaList.toArray(new Cursa[0]);
        return crs;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findOne(@PathVariable String id){
        System.out.println("findOne request");
        Cursa cursa = null;
        try{
            cursa=cursaRepository.findOne(Integer.valueOf(id));
        }
        catch (NumberFormatException nfe){
            return new ResponseEntity<String>("Worng id format (id has to be an integer value)", HttpStatus.BAD_REQUEST);
        }
        if (cursa==null)
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Cursa>(cursa, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cursa create(@RequestBody Cursa cursa){
        cursa.setId(0);
        return cursaRepository.save(cursa);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Cursa update(@RequestBody Cursa cursa, @PathVariable String id) {
        cursa.setId(Integer.valueOf(id));
        return cursaRepository.update(cursa);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id){
        Cursa c = cursaRepository.delete(Integer.parseInt(id));
        if(c==null)
            return new ResponseEntity<String>("Nu exista nici o cursa cu acest id!",HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<String>("Cursa care a fost stearsa este: "+c,HttpStatus.OK);
    }
}
