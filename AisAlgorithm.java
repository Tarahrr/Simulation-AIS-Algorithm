import java.util.*;
import java.math.*;

public class AisAlgorithm {
    //***********************************************Variable*****************************************************************
    final static int pop_size = 50 ;
    final static int Iter = 50;
    final static double Mut_rate = 0.05;
    final static double Clone_rate = 0.1;
    static double [][] population = new double[pop_size][4];    // X-Y-Fittness
    final static double max = 1.0;
    final static double min = 0.0;
    //************************************************Initialize****************************************************************
    public static void initializePopulation(){           // meghdar dehi avaliye be sorate random
        double n=0.0,r=0.0;
        for(int i=0;i<pop_size;i++)
            for(int j=0;j<2;j++){
                population[i][j] =(0.4+ (double) (Math.random() * ((0.6 - (0.4)))));
                        //(double)Math.round(Math.random() * 10000) / 10000;
            }
    }
    //*************************************************cloneRateNum*****************************************************************
    public static double cloneRateNum(double [][] arr,int popNum){
        // Clone Rate Calculate
        double r=0;
            r = (Clone_rate*pop_size*arr[popNum][2])+1;
               r=Math.round(r);
        return r;
    }
    //*************************************************Fitness*****************************************************************
    public static double fitnessFunction(double x,double y){

        double c = 15*x*y*(1-x)*(1-y)*Math.sin(9*Math.PI*x)*Math.sin(9*Math.PI*y) ;
        c =(double)Math.round(Math.pow(c,2) * 10000)/10000;
        return c;

    }

    //****************************************************Mutation**************************************************************
    public static double [][] mutationFunction(double[][] arr,int popNum){

        double delta = 0;
        double rand =0;
        if(arr[popNum][2] != 0){
            delta = arr[popNum][2]/1000;
            delta = 1/delta;
            rand =(-0.0002+ (double) (Math.random() * ((0.0002 - (-0.0002)))));
            rand = (double)Math.round(rand * 10000) / 10000;
            delta = delta * rand;
            delta = delta/1000;
        }
        else{
            rand = (-0.0002+ (double) (Math.random() * ((0.0002 - (-0.0002)))));
            rand = (double)Math.round(rand * 10000) / 10000;
            delta =  rand/100;
        }
        delta =(double)Math.round(delta * 10000)/10000;
      //  System.out.println(delta);
      //  System.out.println(arr[popNum][0]+"     "+arr[popNum][1]+"      "+arr[popNum][2]+"      "+delta);
        if( arr[popNum][0] +delta <=1 && arr[popNum][0] +delta>=0 ){
            arr[popNum][0] = arr[popNum][0] +delta;
            arr[popNum][0]=(double)Math.round(arr[popNum][0] * 10000)/10000;
        }
        else if(arr[popNum][0] -delta <=1 && arr[popNum][0] -delta>=0) {
            arr[popNum][0] = arr[popNum][0] -delta;
            arr[popNum][0]=(double)Math.round(arr[popNum][0] * 10000)/10000;
        }
        if( arr[popNum][1] +delta <=1 && arr[popNum][1] +delta>=0 ){
            arr[popNum][1] = arr[popNum][1] +delta;
            arr[popNum][1]=(double)Math.round(arr[popNum][1] * 10000)/10000;
        }
        else if(arr[popNum][1] -delta <=1 && arr[popNum][1] -delta>=0) {
            arr[popNum][1] = arr[popNum][1] -delta;
            arr[popNum][1]=(double)Math.round(arr[popNum][1] * 10000)/10000;
        }

      //  System.out.println(arr[popNum][0]+"     "+arr[popNum][1]+"      "+arr[popNum][2]);
      //  System.out.println("------------------------------------------------------------");
        return  arr;

    }
    //****************************************************Sort**************************************************************
    public static double[][] sort(double [][] arr ,int n){
        double [] temp = new double [4];
        for(int i = 0; i < n; i++){
            for(int j = 1; j < (n-i); j++){
                if(arr[j-1][2] < arr[j][2]){
                    temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    //****************************************************Clone**************************************************************
    public static double[][] Clone(double [][] clone,int n ,double [][] pop , int m){
        int k=0 ;
        for (int i =0;i<m;i++)           {
            for(int j=0;j<pop[i][3];j++){
                clone[k] = pop[i];
                k++;
            }
        }

        return clone;
    }
    //**************************************************Selection************************************************************
    public static double [][] selection(double [][] clone,int sizeClone,double  [][]oldPopulation){
        int k=0;
        double [][] newPopulation = new double[pop_size][4];

        // 2 ta az jamiate ghabli ke behtarin fitness ro darad negah midarim
       // newPopulation[0] = oldPopulation[0];
        //newPopulation[1] = oldPopulation[1];
        // behtarin fitness haro entekhab mikonim

        for(int i =0; i<pop_size && k<sizeClone;i++){
            newPopulation[i] =clone[k];
            k++;
        }

        return newPopulation;

    }
    //****************************************************print**************************************************************
    public static void print(double [][]arr,int n){
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<4;j++)
                System.out.print(arr[i][j]+"     ");
            System.out.println();
        }
    }
    //*****************************************************Main*****************************************************************
    public static void main(String[] args) {
        int count=0;
        double sum =0;
        initializePopulation();
      while(count<=Iter){//Condition
          sum=0;
        count++;
        //FITNESS
        for (int i =0;i<pop_size;i++)  {
            population[i][2] = fitnessFunction(population[i][0],population[i][1]);
            population[i][3]=cloneRateNum(population,i);
            sum = sum + population[i][3];
        }
        //SORT
        population = sort(population,pop_size);

        double  [][] populationClone = new double[(int)sum][4];
        //CLONE
        populationClone = Clone(populationClone,(int)sum,population,pop_size);
        //MUTATION
        //print(populationClone,(int)sum);
        for (int i =0;i<(int)sum;i++)  {
              populationClone=mutationFunction(populationClone,i);
        }
        //System.out.println("----------------------------------------------------------------");
       // print(populationClone,(int)sum);
        //FITNESS
        for (int i =0;i<(int)sum;i++)  {
            populationClone[i][2] = fitnessFunction(populationClone[i][0], populationClone[i][1]);
        }
        //SORT
        populationClone = sort(populationClone,(int)sum);
        //SELECTION
        population = selection(populationClone,(int)sum,population);
         }
        System.out.println(population[0][0]+"   "+population[0][1]);
    }
}
//*/