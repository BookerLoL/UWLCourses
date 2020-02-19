/*
 * A program that represents a version of a mandelbrot set using *
 *
 * Author: Ethan Booker
 * Date: 2/1/2018
 */
#include <stdlib.h>
#include <stdio.h>
#include <math.h> 

//Global constants
const int MAX_MAGNITUDE = 2;
const double VERTICAL_DIMENSION = 2.0/20.0;
const double HORITZONAL_DIMENSION = 3.0 / 79.0;

int main(int argc, char **argv) {

// Variables declarations for looping
int i;
double X, Y;

// Variables declarations for complex numbers
double zr, zi, cr, ci; 

//Vertical parameters
for(Y=-1; Y<=1; Y += VERTICAL_DIMENSION )
{
    //Horitzonal parameters
    for(X=-2; X<=1; X += HORITZONAL_DIMENSION)
    {
        //Base complex number initalization
        zr=0;
        zi=0;

        for(i = 1; i <=100; i++)
        {
            //Setting Z(k) 
            cr = zr; 
            ci = zi;

            zr = pow(cr, 2) - pow(ci,2);
            zi = 2*cr*ci; 

            zr += X;
            zi += Y; 

            //Checking for early divergence
            if(sqrt((zr*zr) + (zi*zi)) > MAX_MAGNITUDE)
            {
                break;
            }
               
        }
        
        //Checks for divergence
        if(i==101)
        {
            printf("%s","*");
        }
        else
        {
            printf("%s"," ");
        }
    }
    printf("\n");
   
}
	return 0;
}
