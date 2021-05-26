package objectprotocol;

import DTOs.CapacitateNrParticipanti;

public class GetCapacitateNrParticipantiResponse implements Response{
    private final CapacitateNrParticipanti cp;

    public GetCapacitateNrParticipantiResponse(CapacitateNrParticipanti cp){
        this.cp=cp;
    }

    public CapacitateNrParticipanti getCapacitateNrParticipanti(){
        return cp;
    }
}
