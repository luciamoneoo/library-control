package pio.daw;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library implements Controlable {
    private Map<String,User> users;

    /**
     * Read the library register file (.txt) and create a library object
     * with the current status of the users.
     * @param path Library registry file path.
     * @return Library object.
     */
    public static Library fromFile(Path path){
        Library library = new Library();

        // la clase library deberá abrir el archivo y leerlo
        //en cada línea se separara el usuario y el evento
        // además se añadirá el evento a cada usuario
         try(var lines = java.nio.file.Files.lines(path)){

        lines.forEach(line -> { //para que se lea cala linea

            String[] parts = line.split(";");// aqui es donde se sepaeara el usuario del evento con ;
            //siendo el usuario guardado en la posicion 0 y el evento en la posicion 1
            String usuario = parts[0]; //aqui como hemos dicho antes, se guqrda el usuario
            EventType evento; // definimos el evento como eventype para que nos de entrada o salida
            //aqui, estamos convirtiendo el evento en un eventype, para que defina si es entrada o salida

            //con un if, compararemos el evento para que el programa diga si es una entrada o una salida
                    if(parts[1].equals("ENTRADA")){
                    evento = EventType.ENTRY;
                    } else {
                    evento = EventType.EXIT;
                    }

            library.registerChange(usuario, evento); //sirve para guardar lso datos que han entrado de usuario y evento

        });

    } catch(Exception e){ // por si hubiese algun error como que el archivo no existe o no se puede leer
        e.printStackTrace(); //printStackTrace sirve para que se imprima el error ocurrido y te diga donde ha pasado 
        System.exit(1); // esta linea significaría que saldríamos del programa
    }
        return library; // se devuelve la biblioteca con todo actualizado
    }

    private Library(){
        this.users = new HashMap<>();
    }

    public void registerChange(String usuario, EventType evento){

    User user = this.users.get(usuario);

    if(user == null){
        user = new User(usuario);
        users.put(usuario, user); //aqui se crearia y se añadiria al map si el usuario no existiese
    }
    user.registerEvent(evento);
}

public List<User> getCurrentInside(){ // para que el prgrama nos devuelva la lista de los usuarios
    
    return users.values()
            .stream()// recorre todos los usuarios
            .filter(User::isInside)// esto filtraria los usuario con Entry
            .toList(); // los devuelve en formato de lista
}

public List<User> getUserList(){ //aqui se ordenaran según el numero de cada usuario 
    return users.values()
            .stream()
            .sorted((u1,u2) -> u1.getId().compareTo(u2.getId())) //es uel metodo que los ordena pro el nombre id
            .toList();
}

public List<User> getMaxEntryUsers(){ // para calcular el numero de enradas de cada uno, por eso lo definimos como int

    int max = users.values() // va reccorriendo los usuarios y las entrads para calcular luego el máximo
            .stream()
            .mapToInt(User::getEntryCount) //toma cada usuario 1 por 1 y obtiene su numero de entradas
            .max() // calcula el maximo de entradas
            .orElse(0); //en caso de que no hubiese ningun usuario, se quedaria en 0

    return users.values()
            .stream()
            .filter(u -> u.getEntryCount() == max)
            .toList();
}

public void printResume(){

    System.out.println("Usuarios actualmente dentro de la biblioteca:");
    getCurrentInside()
            .forEach(u -> System.out.println(u.getId()));

    System.out.println("\nNúmero de entradas por usuario:");
    getUserList()
            .forEach(u ->
                System.out.println(u.getId() + " -> " + u.getEntryCount())
            );

    System.out.println("\nUsuario(s) con más entradas:");
    getMaxEntryUsers()
            .forEach(u -> System.out.println(u.getId()));
}
 
}
