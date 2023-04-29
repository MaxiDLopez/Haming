import java.util.ArrayList;
import java.util.BitSet;
import java.io.*;

import javax.lang.model.util.ElementScanner6;
import javax.swing.JOptionPane;

public class Decodificar{

    private static int caract;

    private static ArrayList<Integer> arreglo;
    private static ArrayList<Integer> aux;
    private static ArrayList<Integer> contenedor;
    
    private static FileReader fr;
    private static BufferedReader br;

    private static File archivo;
    private static FileWriter fw;
    private static BufferedWriter bw;
    private static String nombre;
    
    private static boolean existe; 

    public static ArrayList<Integer> leerArchivo(int bits) throws IOException{//TIRA UNA EXCEPCION EN CASO DE NO ENCONTRAR UN ARCHIVO

        existe = false;
        arreglo.clear();
        aux.clear();
        contenedor.clear();

        if (bits == 32){//Tenemos que leer el archivo que fue codificado en 32 bits

            try{
            
                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\PruebasArchivo\\HA1.txt");
            
                if(archivo.exists() & archivo.isFile()){//EL ARCHIVO EXISTE
                    existe = true;
                    nombre = "DE1.txt";
                }
                else{
                    System.out.println("\nAUN NO SE REALIZADO UNA CODIFICACION DE HAMING 32\n");
                }

            }catch(Exception e){
                System.out.println("\nERROR CUANDO ABRIMOS HA1.txt: "+ e.getMessage());
            }


        
        }
        else if(bits == 2048){//Tenemos que leer el archivo que fue codificado en 2048 bits

            try{

                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\PruebasArchivo\\HA2.txt");
            
                if(archivo.exists() & archivo.isFile()){//EL ARCHIVO EXISTE
                    existe = true;
                    nombre = "DE2.txt";
                }
                else{
                    System.out.println("\nAUN NO SE REALIZADO UNA CODIFICACION DE HAMING 2048\n");
                }
            
            }catch(Exception e){
                System.out.println("\nERROR CUANDO ABRIMOS HA2.txt: "+ e.getMessage());
            }
        
        }
        else{//Tenemos que leer el archivo que fue codificado en 65536 bits

            try{

                archivo = new File("C:\\Users\\Carolina\\Desktop\\Haming\\PruebasArchivo\\HA3.txt");
            
                if(archivo.exists() & archivo.isFile()){//EL ARCHIVO EXISTE
                    existe = true;
                    nombre = "DE3.txt";
                }
                else{
                    System.out.println("\nAUN NO SE REALIZADO UNA CODIFICACION DE HAMING 65536\n");
                }
            
            }catch(Exception e){
                System.out.println("\nERROR CUANDO ABRIMOS HA3.txt: "+ e.getMessage());
            }


        
        }


        if(existe){//SI EL ARCHIVO EXISTE
            
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            caract = br.read();

            while(caract != -1){//SI el caracter leido no es nulo

                arreglo = funciones.CaracterToBits(caract);

                if ( (arreglo.size()%bits) == 0){//Si el arreglo ya llego a completar el bloque

                    aux = funciones.decodificar(arreglo);//Aux se queda con la informacion sin los bits de control
                    arreglo.clear();//reiniciamos el bloque

                    if (!contenedor.isEmpty()){
                        
                    }
                    

                

                    arreglo.clear();
                }
            
                caract = br.read();
            }

            
        }
    

        return arreglo;
    }

}