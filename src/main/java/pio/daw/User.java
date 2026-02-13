package pio.daw;

public class User implements Localizable { // al usar implmeents, la clase debe usarlos métodos de la clase localizable

    private String id;
    private Integer nEntries = 0; //lo usaremos como contador para las entradas
    private Boolean inside = false; // esta variable nos indicará si el usuario está dentro o no, para evitar que se cuente una entrada si el usuario ya está dentro, o una salida si el usuario ya está fuera

    public User(String id){ // el constructor de la clase que recibira el id del usuario
        this.id = id;
    }

    public String getId(){ //aqui se nsm devolverá el id del usuario
        return this.id;
    }

    public int getnEntries(){ //nos devuelve solo el numero de entradas de cada uno
        return this.nEntries;
    }

    public Boolean isInside(){ //nos dice si el usuario esta dentro o no, sieno true dentro y false si esta fuera
        return inside;
    }

    public void registerEvent(EventType e){ // con este método registraremos las entradas o salidas

        if(e == EventType.ENTRY && !this.inside){ // en este if, sería si el evento es de entrar Y el usuario no esta dentro, entonces se contaría como una entrada, y se pondría el booleano a true
            this.inside = true;
            nEntries++; //i el usuario ya estaba dentro y recibe otra entrada, no suma otra vez
        } 
        else if(e == EventType.EXIT && this.inside){ // aqui si el evento es salida y el usuario esta dentro, entonces se pondría el booleano a false, y no se contaría como una salida si el usuario ya estaba fuera
         this.inside = false;
        }

    }
}
