package socialnetwork.service;

import socialnetwork.domain.Message;
import socialnetwork.domain.ReplyMessage;
import socialnetwork.domain.Utilizator;
import socialnetwork.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MessageService {
    private Repository<Long, Message> repoMessage;
    private Repository<Long,Utilizator> repoUtilizator;


    public MessageService(Repository<Long,Message> repo,Repository<Long,Utilizator> repo2)
    {
        this.repoMessage = repo;
        this.repoUtilizator = repo2;
    }

    public Iterable<Message> getAll()
    {
        return repoMessage.findAll();
    }
    public Message getOne(Long id){return repoMessage.findOne(id);}

    public Message get_message_for_user(Long id)
    {
        return repoMessage.findOne(id);
    }

    public void send_message(Long id_mesaj,Long id_exp,List<Long>destinatari,String mesaj)
    {
        Utilizator exp = repoUtilizator.findOne(id_exp);

        List<Utilizator> lista_destinatari = new ArrayList<Utilizator>();

        for(Long i: destinatari)
        {
            lista_destinatari.add(repoUtilizator.findOne(i));
        }

        Message message = new Message(exp,lista_destinatari,mesaj);
        message.setId(id_mesaj);
        repoMessage.save(message);
    }

    public void reply_all(Long new_id,Long id_msg_to_rply, Long id_who_rply, String msg_rply)
    {
            Message mess_init = repoMessage.findOne(id_msg_to_rply);
              System.out.println(mess_init.get_to());
            ///
            //
        Utilizator s = mess_init.get_from();



            List<Utilizator> lista_destinatari = new ArrayList<Utilizator>();
            lista_destinatari.add(mess_init.get_from());
            for(Utilizator i : mess_init.get_to())
            {
                if(! i.equals(repoUtilizator.findOne(id_who_rply)))
                    lista_destinatari.add(i);
            }

        ReplyMessage reply = new ReplyMessage(repoUtilizator.findOne(id_who_rply),lista_destinatari,msg_rply);
            reply.setId(new_id);

            reply.setReply(mess_init);
            System.out.println(reply.getReply());


            repoMessage.save(reply);


            //Message reply_msg = new Message()
    }



    public void reply_filter(Long new_id,Long id_msg_to_rply, Long id_who_rply, String msg_rply,ArrayList<Long> lista_destinatari)
    {
        Message mess_init = repoMessage.findOne(id_msg_to_rply);
       // System.out.println(mess_init.get_to());
        ///
        //
        lista_destinatari.forEach(System.out::println);
        Utilizator s = mess_init.get_from();
        ArrayList<Utilizator> lista_dest = new ArrayList<Utilizator>();
        ArrayList<Utilizator> lista_din_mesaj  = (ArrayList<Utilizator>) mess_init.get_to();

        for (Long id: lista_destinatari)
            for(Utilizator user: lista_din_mesaj)
                if(user.getId() == id)
                    lista_dest.add(user);
        ReplyMessage reply = new ReplyMessage(repoUtilizator.findOne(id_who_rply),lista_dest,msg_rply);
        reply.setId(new_id);
        reply.setReply(mess_init);
        repoMessage.save(reply);
    }

    public void reply_one(Long idReplyMsg, Long idMesajToReply, Long idWhoReply, String mesaj, Long dest){
        Message mess_init = repoMessage.findOne(idMesajToReply);
        List<Utilizator> to= new ArrayList<>();
        to.add(repoUtilizator.findOne(dest));
        ReplyMessage replymsg=new ReplyMessage(repoUtilizator.findOne(idWhoReply),to,mesaj);
        replymsg.setId(idReplyMsg);
        replymsg.setReply(mess_init);
        repoMessage.save(replymsg);
    }
    public List<Message> cronologic_message(Long id1,Long id2)
    {
        List<Message> mesaje = new ArrayList<Message>();

        repoMessage.findAll().forEach(x->
        {

            if(x.get_from().getId()==id1 && x.get_to().contains(repoUtilizator.findOne(id2)))
                mesaje.add(x);
            else if(x.get_from().getId() == id2 && x.get_to().contains(repoUtilizator.findOne(id1)))
                mesaje.add(x);

        });
     return mesaje.stream().sorted((m1,m2)->m1.getData().compareTo(m2.getData())).collect(Collectors.toList());

    }

    public Iterable<Message> convCronologic(Long id1,Long id2){
        Utilizator u1 = repoUtilizator.findOne(id1);
        Utilizator u2 = repoUtilizator.findOne(id2);
        return StreamSupport.stream(getAll().spliterator(), false)
                .filter(x->x.get_from().getId().equals(id1) || x.get_from().getId().equals(id2))
                .filter(x->x.get_to().contains(u1) || x.get_to().contains(u2))
                .sorted(Comparator.comparing(Message::getData))
                .collect(Collectors.toList());
    }



}
