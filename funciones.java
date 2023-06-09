import java.util.ArrayList;
import java.util.BitSet;

import javax.lang.model.util.ElementScanner6;

import java.io.*;

public class funciones {

    public static char arr1[] = new char[4];
    public static char arr2[] = new char[256];
    public static char arr3[] = new char[8192];
    public static int acumulador=0;

    public static char[] convertirInvers(ArrayList<Integer> arreglo){
     char arr1[] = new char[4];
     char arr2[] = new char[256];
     char arr3[] = new char[8192];
     int pos=0, exponente;

        for(int j=0; j<(arreglo.size()/8); j++){

            acumulador = 0;
            exponente = 7;  //7?

            for(int i=pos; i<pos+8 ; i++){
        
                if (arreglo.get(i) == 1){//SI el bit es 1, entonces lo elevamos a la potencia correspondiente
                    
                    acumulador = acumulador + (int)(Math.pow(2, (double)exponente));
                }
                //En el caso de que el bit es 0, no hace falta hacer nada
                exponente=exponente-1;
            }
            pos+=8;
            if(arreglo.size() == 32)//Trabajamos con 32 bits
                arr1[j] = (char)acumulador;
            else if(arreglo.size() == 2048)//Trabajamos con 2048 bits
                arr2[j] = (char)acumulador;
            else//Trabajamos con 65536 bits
                arr3[j]= (char)acumulador;
        }
        if(arreglo.size() == 32)//Devolvemos con 4 caracteres
            return arr1;
        else if(arreglo.size() == 2048)//Devolvemos con 256 caracteres
            return arr2;
        else//Devolvemos con 8192 caracteres
            return arr3;

    }
   
    //Funcion que pasa bits del segundo array al primero
    public static ArrayList<Integer> agruparBits(ArrayList<Integer> base,ArrayList<Integer> nuevos,int posInicial,int cantidad){

        for (int i = 0; i < cantidad; i++) { 
            base.add(posInicial+i,nuevos.get(i));
        }
        return base;
    }

    //Mete los bits de un caracter en un bitset de 8 bits

    public static ArrayList<Integer> CaracterToBits(int caracter){
        ArrayList<Integer> arreglo;
        arreglo = new ArrayList<Integer>();
        int j=0;
        boolean valor=false;

        for (int i = 0; i < 8; i++) {
            valor= ((caracter >> (7-j)) & 1) == 1;
            if(valor==true){
                arreglo.add(i,1);
            }else{
                arreglo.add(i,0);
            }
            j++;
        }
        return arreglo;
    }


    //Hace corrimientos hacia la izquierda
    public static ArrayList<Integer> correrBitsIzq(ArrayList<Integer> arreglo,int tamañoArreglo,int corrimientos){
        for (int i = 0; i < tamañoArreglo-corrimientos; i++) {
            arreglo.set(i,arreglo.get(corrimientos+i));
        }

        for (int i = 0; i < corrimientos; i++) {
            arreglo.remove(tamañoArreglo-corrimientos);    
        }

        return arreglo;
    }

    public static BitSet control32(BitSet arreglo, int r)       //r es la cantidad de bits de control
    {
        for (int i = 0; i < r; i++) {
            int x = (int)Math.pow(2, i);    //Devuelve 2^i y lo guarda en x
            for (int j = 1; j < arreglo.length(); j++) {
                if (((j >> i) & 1) == 1) {
                    if (x != j)     
                    //System.out.println("El bit "+ j + " entra en el control del bit " + x);           
                    arreglo.set(x, arreglo.get(x) ^ arreglo.get(j));;  //Xor del bit de control con los que le corresponda
                }
            }
            System.out.println("r" + x + " = " + arreglo.get(x));  //Muestra los bits de control y su valor
        }
        return arreglo;
    }
    
    //Da valor a los bits de control
    public static ArrayList<Integer> control(ArrayList<Integer> arreglo, int r)       //r es la cantidad de bits de control
    {
        for (int i = 0; i < r; i++) {
            int x = (int)Math.pow(2, i);    // X tiene la posición del bit de control
            arreglo.set((x-1), 0);
            int result;

            for (int j = 1; j < arreglo.size(); j++) {
                if (((j >> i) & 1) == 1) {
                    if (x-1 != j-1){     
                    //System.out.println("El bit con indice "+ (j-1) + " entra en el control del bit con indice " + (x-1)); 
                    result = (arreglo.get((x-1)) ^ arreglo.get((j-1)));
                  //  System.out.println("Xor entre pos "+(x-1)+ " y pos "+(j-1)+" : " +result);        
                    arreglo.set(x-1, result);  //Actualizo bit de control
                    }
                }
            }
           
           // System.out.println("Bit de control " + x + " = " + arreglo.get(x-1));  //Muestra los bits de control y su valor
        }
        return arreglo;
    }

    static int getParityBit(ArrayList<Integer> returnData,int pow){
        int parityBit = 0;  
        int size = returnData.size();
        System.out.println("Size es "+size);
//Problema
        for (int i = 0; i < size; i++) {
          
            if(returnData.get(i) != 2){
                System.out.println("i vale: "+i);
                int k = (i+1);
                String str = Integer.toBinaryString(k);  
                System.out.println("STR: "+str);
                int temp = ((Integer.parseInt(str)) / ((int) Math.pow(10, pow))) % 10;  
                if(temp == 1){
                    if(returnData.get(i) == 1){
                        parityBit = (parityBit + 1) % 2;
                    }
                }
            }
        }
// problema cerrado

        for (int j = 0; j < size; j++) {
            int x = (int)Math.pow(2, pow); //X es la posición del bit de control

            if (returnData.get(j) != 2) {
                
            }
        }

        return parityBit;
    }

    static int ultimoBit(ArrayList<Integer> data,int bitsControl){
        int lastBit = 0; 
        int contando = 0; 
        
        for (int i = 0; i < bitsControl; i++) {
            if(data.get((int)(Math.pow(2, i))-1) == 1){
                contando += 1;
            }
        }

        lastBit = (contando%2);
        
        return lastBit;
    }

    public static ArrayList<Integer> rellenarConCeros(ArrayList<Integer> arreglo,int tope){
        for (int i = arreglo.size(); i < tope; i++) {
            arreglo.add(0);
        }
        return arreglo;
    }
 
        public static ArrayList<Integer> aplicarHamming(ArrayList<Integer>array,int r, boolean error){ //r es la cantidad de bits de control
    
            ArrayList<Integer> resto;
            resto = new ArrayList<Integer>();
            ArrayList<Integer> hamming;
            hamming = new ArrayList<Integer>();
            int desde = array.size()-r-1; //26
            int j=0;
            int k=0;
            int tope = array.size();
      
            //Guardo los ultimos bits que van a sobrar y luego los elimino del arreglo con info     
            for (int i = 0; i < r+1 ; i++) {     
                resto.add(i,array.get(desde+i));
            }
    
            for (int n = desde; n < tope; n++) {
                array.remove(array.size()-1);
            }
            System.out.println("Guarde las sobras");

            //Distribuir data y bits de control en el modulo
            for(int i = 1; i <= tope; i++) {  
                if(Math.pow(2, j) == i) {  
                   hamming.add((i - 1),2); //Las posiciones donde sea bit de control tendra 2        
                   j++;  
                }  
                else {
                    hamming.add((k+j), array.get(k++));  
                }  
            }

         System.out.println("Distribui la data");
         System.out.println("Size es: "+hamming.size());
         System.out.println("El vector es: "+hamming);
         System.out.println("CHECK");

            //Dar valor a los bits de paridad
            /* 
            for(int i = 0; i < r; i++) {  
                System.out.println("Le doy valor al bit: "+((int) Math.pow(2, i)));
                hamming.set(((int) Math.pow(2, i))-1, getParityBit(hamming, i));
                System.out.println("Ya le di valor");
            }    
            */
            hamming = control(hamming, r);
            System.out.println("Di valor a los bits de paridad");
           
            //Bit 32
             hamming.set(hamming.size()-1, ultimoBit(hamming, r));
            

             int numero=0;

             //Introducir error
             if(error){             //Error viene por parametro.Va a ser True si se selecciono "proteger con errores"
              if((int)(Math. random()*2+1) == 1){ //Devuelve 1 o 2.Si devuelve 1 meto error
                    numero = (int)(Math. random()*hamming.size()+1);    //Posicion donde se va a introducir el error
                    if(hamming.get(numero-1) == 0){
                        hamming.set(numero-1,1);
                    }else{
                        hamming.set(numero-1,0);
                    }
                }
             }
             
            //System.out.println("Mando a imprimir,mi size es: "+hamming.size());
            //System.out.println(hamming);
            //Ahora el array "hamming" esta listo para ser guardado en txt
            //System.out.println("Llamando a imprimir");
            
            if(error){//SI viene con errores
                if(hamming.size() == 32){
                    Archivo.escribir("HA32-E", hamming);
                }
                else if(hamming.size() == 2048){
                    Archivo.escribir("HA2048-E", hamming);
                }
                else{//bloque de 65536
                    Archivo.escribir("HA65536-E", hamming);
                }
            }
            else{//No tiene errores
                if(hamming.size() == 32){
                    Archivo.escribir("HA32", hamming);
                }
                else if(hamming.size() == 2048){
                    Archivo.escribir("HA2048", hamming);
                }
                else{//bloque de 65536
                    Archivo.escribir("HA65536", hamming);
                }
            }

            return resto;
        }

     
        //Aplica un Haming
    public static BitSet generateCode(BitSet viejo, int M, int r)  
    {
        BitSet nuevo = new BitSet(32);     //Crea el arreglo con su tamaño final correspondiente
        int j = 1;
        for (int i = 1; i < viejo.length(); i++) {
            if ((Math.ceil(Math.log(i) / Math.log(2))
                 - Math.floor(Math.log(i) / Math.log(2)))
                == 0) {
  
                // si i == 2^n para n in (0, 1, 2, .....)
                // entonces ar[i]=0
                // Los bits de control son inicializados con valor 0
                nuevo.set(i,0);
            }
            else {
                // codeword[i] = dataword[j]
                nuevo.set(i, viejo.get(j));
                j++;
            }
        }

        return nuevo;
    }

    public static ArrayList<Integer> decodificar(ArrayList<Integer> arreglo){
    
        //int cantidad = (int)(Math.log(arreglo.size())/Math.log(2));
        ArrayList<Integer> nuevo = new ArrayList<>();
        int control=0;

        for(int i=0; i < arreglo.size() ; i++){//Si es distinto de un bit de control, que lo agregue al nuevo arreglo
            
            if( i != (Math.pow(2, control)-1) ){
                nuevo.add(arreglo.get(i));
            }
            else{
                control+=1;
            }
            
        }

        return nuevo;
    }

    static int obtenerPosError(ArrayList<Integer>sindrome){
        int posicion=0;
       
        for (int i = 0; i < sindrome.size(); i++) {
            if(sindrome.get(i) == 1){
            posicion += Math.pow(2, i); 
            }
        }

        return posicion;
    }

    static ArrayList<Integer> bloqueCorregido(ArrayList<Integer>revisar,int bitsControl){
        ArrayList<Integer> nuevo = new ArrayList<Integer>();
        ArrayList<Integer> sindrome = new ArrayList<Integer>();

        int posError;
        //
        for (int i = 0; i < bitsControl; i++) {
            sindrome.add(i, getParityBit(revisar, i));
        } 
        posError = obtenerPosError(sindrome);
        
        if (posError != 0) {
            if (revisar.get(posError-1) == 1) {
                revisar.set(posError-1, 0);
            }else{
                revisar.set(posError-1, 1);
            }
        } 
        //
        return revisar;
    }
}

