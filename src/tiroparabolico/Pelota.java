package tiroparabolico;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AndresG
 */
public class Pelota extends Base {
    
    private double dx;
    private double dy;
    private static double aceleracion = 9;
    
    public Pelota(int posX, int posY, Animacion animacion) {
        super(posX, posY, animacion);
    }
    
    public void actualiza() {
        dy += aceleracion;
        setDoublePosX(getDoublePosX() + dx);
        setDoublePosY(getDoublePosY() + dy);
    }
    
    public static void setAceleracion(double a) {
        aceleracion = a;
    }
    
    public static double getAceleracion() {
        return aceleracion;
    }
}
