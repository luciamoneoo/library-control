package pio.daw;

public class User implements Localizable {

    private String id;
    private Integer nEntries = 0;
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

    public void registerEvent(EventType E){

        if(E == EventType.ENTRY){

            if(!inside){
                inside = true;
                contadorEntrada++;
            }

        } else if(E == EventType.EXIT){

            if(inside){
                inside = false;
            }
        }

        nEntries = 0;
    }
}
