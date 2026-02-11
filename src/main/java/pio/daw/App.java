package pio.daw;

import java.nio.file.Path;

public class App {
    /**
     * Parse the arguments of the program to get the library registry file
     * path. Exits the program if the args are not correct or the file does
     * not exists.
     * @param args program args.
     * @return Path to file if exists.
     */
    public static Path getPathFromArgs(String[] args){

        //primero deberíamos ver si el path existe
        //entonces, con un if, veremos si hay argumento o no, sino, saldremos del programa
        // la lonitud del argumeno será de 1 que será el nombre del archivo que tieen los registros
        if(args.length != 1){
            System.out.println(" Pase la ruta del archivo: ");
            System.exit(1);
        } 
        // ahora crearemos un objeto path con el argumento que le damos al programa
        // luego veremos si el archivo existe o no, si no existe saldremos
        //(una vez ya hemos comprobado arriba que el argumento es correcto)

        Path path = Path.of(args[0]);

        //en el if, convertimos el path en file, ya que el exists() solo funciona con filess, y 
        // ese metodo funciona como un boolean, devoviendo true si el file existe, o false si no
        if(!path.toFile().exists()){
            System.out.println(" El archivo no existe: " + path);
            System.exit(1);
        }
       
        return path;
    }

    public static void main(String[] args) {
        Path p = getPathFromArgs(args);
        Controlable controler = Library.fromFile(p);
        controler.printResume();
    }
}
