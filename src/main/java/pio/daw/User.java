package pio.daw;

public class User implements Localizable {
    private String id;
    private EventType nEntries = 0;
    private Boolean inside = false;
    private int contadorEntrada = 0;

    public User(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public int getEntryCount(){
    return contadorEntrada;
}


    public Boolean isInside(){
    return inside;
}

    public void registerEvent(EventType e){

    if(e == EventType.ENTRY){

        // Entrada duplicada → ignorar
        if(!inside){
            inside = true;
            contadorEntrada++;
        }

    } else if(e == EventType.EXIT){

        // Salida sin entrada → ignorar
        if(inside){
            inside = false;
        }
    }

    nEntries = e;
}
}
