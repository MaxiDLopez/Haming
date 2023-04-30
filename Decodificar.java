import java.util.ArrayList;
import java.util.BitSet;
import java.io.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.JOptionPane;

public class Decodificar{

    private static int caract;

    private static ArrayList<Integer> bloque = new ArrayList<Integer>();
    private static ArrayList<Integer> aux = new ArrayList<Integer>();
    private static ArrayList<Integer> informacion = new ArrayList<Integer>();;
    private static ArrayList<Integer> contenedor = new ArrayList<Integer>();
    
    private static File archivo;
    private static FileReader fr;
    private static BufferedReader br;

    private static String nombre;

    public static void leerArchivo(int bits) {//TIRA UNA EXCEPCION EN CASO DE NO ENCONTRAR UN ARCHIVO


        try{

            if(bits == 32){//Obetener la ruta del archivo correspondiente
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA1.txt");
                nombre = "DE1.txt";
            }
            else if(bits == 2048){
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA2.txt");
                nombre = "DE2.txt";
            }
            else{
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\HA3.txt");
                nombre = "DE3.txt";
            }

            if(archivo.exists() & archivo.isFile()){//Si existe la ruta y es un archivo

                System.out.println("\nEl archivo codificado existe\n");

                bloque.clear();
                informacion.clear();
                contenedor.clear();
                aux.clear();

                fr = new FileReader(archivo);
                br = new BufferedReader(fr);

                caract=br.read();

                while(caract != -1){//Si el caracter no es nulo
                
                    aux = funciones.CaracterToBits(caract);
                    for(int i:aux){
                        bloque.add(i);
                    }

                    if( bloque.size() == bits){//Completamos un bloque pero con bits de control

                        aux = funciones.decodificar(bloque);//Decodficamos el bloque y agregamos la informacion en un nuevo arreglo
                        bloque.clear();//Reiniciamos el bloque
                        for(int i:aux){
                            informacion.add(i);
                        }

                        if( informacion.size() >= bits){//Completamos un bloque solamente de informacion

                            while( contenedor.size() < bits ){

                                contenedor.add(informacion.get(0));//Metemos el primer bit del arreglo aux
                                informacion.remove(0);//Borramos ese elemento que ingresamos
                            
                            }

                            Archivo.escribir(nombre, contenedor);
                            contenedor.clear();
                        }

                        if( (contenedor.size()%bits) == 0){//Preguntamos si se detuvo el while porque ya esta listo para escribir en el archivo

                            Archivo.escribir(nombre, contenedor);//Pasamos a escribir en el archivo
                            contenedor.clear();//Borramos todo ya que lo escribimos
                        
                        }
                    
                    }

                    caract=br.read();
                }

                if( (bloque.size()%bits) != 0){//Nos quedamos con un bloque sin completar
                    while( (bloque.size()%bits) != 0){
                        bloque.add(0);
                    }
                    informacion = funciones.decodificar(bloque);
                    bloque.clear();
                }//Ya completamos el ultimo bloque codificado

                while( informacion.size()>bits ){//Hay mas de un caracter

                    while( (contenedor.size()%bits) != 0 ){

                        contenedor.add(informacion.get(0));//Metemos el primer bit del arreglo aux
                        informacion.remove(0);//Borramos ese elemento que ingresamos
                    
                    }
                    Archivo.escribir(nombre, contenedor);
                    contenedor.clear();
                
                }

                if(!informacion.isEmpty()){
                    while( (informacion.size()%bits) != 0){
                        informacion.add(0);
                    }
                }

                Archivo.escribir(nombre, informacion);
                informacion.clear();

            }

            else{//Si no existe el archivo
                System.out.println("\nEl archivo codificado correspondiente no existe\n");
            }

        
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}