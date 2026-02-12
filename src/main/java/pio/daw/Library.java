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
         try(var lines = java.nio.file.Files.lines(path)){

        lines.forEach(line -> {

            String[] parts = line.split(";");

            String id = parts[0];
            EventType event = parts[1].equals("ENTRADA")
                    ? EventType.ENTRY
                    : EventType.EXIT;

            library.registerChange(id, event);
        });

    } catch(Exception e){
        e.printStackTrace();
        System.exit(1);
    }
        return library;
    }

    private Library(){
        this.users = new HashMap<>();
    }

    public void registerChange(String id, EventType e){

    User user = users.get(id);

    if(user == null){
        user = new User(id);
        users.put(id, user);
    }
    user.registerEvent(e);
}

public List<User> getCurrentInside(){
    return users.values()
            .stream()
            .filter(User::isInside)
            .toList();
}

public List<User> getUserList(){
    return users.values()
            .stream()
            .sorted((u1,u2) -> u1.getId().compareTo(u2.getId()))
            .toList();
}

public List<User> getMaxEntryUsers(){

    int max = users.values()
            .stream()
            .mapToInt(User::getEntryCount)
            .max()
            .orElse(0);

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
