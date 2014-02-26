package tiroparabolico;

import java.awt.Toolkit;

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
    private static double aceleracion = .5;
    
    public Pelota(int posX, int posY) {
        super(posX, posY, crearAnimacionPelota());
    }
    
    public void empezar() {
        setPosX(200);
        setPosY(200);
        dy = -Math.random()*2;
        dx = Math.random()*3;
    }
    
    public void actualiza() {
        setDoublePosX(getDoublePosX() + dx);
        setDoublePosY(getDoublePosY() + dy);
        dy += aceleracion;
    }
    
    public static void setAceleracion(double a) {
        aceleracion = a;
    }
    
    public static double getAceleracion() {
        return aceleracion;
    }
    
    private static Animacion crearAnimacionPelota() {
        Animacion anim = new Animacion();
        for (int i = 0; i <= 20; i++) {
            anim.sumaCuadro (Toolkit.getDefaultToolkit ().getImage (Pelota.class.getResource ("tiroparabolico/images/ball/basketball" + i + ".png")), 200);
        }
        return anim;
    }
}
