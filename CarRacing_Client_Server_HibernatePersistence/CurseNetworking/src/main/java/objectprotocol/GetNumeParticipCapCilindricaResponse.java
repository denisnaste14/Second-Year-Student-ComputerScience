package objectprotocol;

import DTOs.NumeParticipCapCilindrica;

public class GetNumeParticipCapCilindricaResponse implements Request{
    private final NumeParticipCapCilindrica nc;

    public GetNumeParticipCapCilindricaResponse(NumeParticipCapCilindrica nc){
        this.nc=nc;
    }

    public NumeParticipCapCilindrica getNumeParticipantCapCilindrica(){
        return nc;
    }
}
