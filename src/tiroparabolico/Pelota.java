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
    
    private double vx;
    private double vy;
    private double x;
    private double y;
    private boolean mov;
    private long startTime;
    private long freezeTime;
    private static double aceleracion = 100;
    
    /**
     * Metodo constructor.
     * @param posX coordenada x inicial.
     * @param posY coordenada y inicial.
     */
    public Pelota(int posX, int posY) {
        super(posX, posY, crearAnimacionPelota());
        x = posX;
        y = posY;
        reaparecer();
    }

    /**
     * <code>Pelota</code> reaparece en su posicion original.
     */
    public void reaparecer() {
        setDoublePosX(x);
        setDoublePosY(y);
        mov = false;
    }
    
    /**
     * Inicia el movimiento de <code>Pelota<code>.
     */
    public void lanzar() {
        mov = true;
        startTime = System.currentTimeMillis();
        double maxVy = getMaxVy();
        double minVy = .3*maxVy;
        vy = Math.random()*(maxVy - minVy) + minVy;
        double maxVx = getVx(getW() - getAncho(), getH() - getAlto());
        double minVx = getVx(getW()/2, getH() - getAlto());
        vx = Math.random()*(maxVx - minVx) + minVx;
    }
    
    /**
     * La pelota se mueve de acuerdo al tiempo, velocidad en X y Y, y gravedad.
     */
    public void avanza() {
        if (mov) {
            double time = (double)(System.currentTimeMillis() - startTime)/1000;
            setDoublePosX(x + vx * time);
            setDoublePosY(y - (vy*time - 0.5*aceleracion*time*time));
            //System.out.println("(" + getDoublePosX() + ", " + getDoublePosY() + ")");
        }
    }
    
    public void freeze() {
        freezeTime = System.currentTimeMillis();
    }
    
    public void unfreeze() {
        startTime += System.currentTimeMillis() - freezeTime;
    }
    
    /**
     * Regresa el mayor valor de vy para que el objeto no se salga por la
     * parte superior del <code>JFrame</code>
     * @return un <code>double</code>.
     */
    private double getMaxVy() {
        return Math.sqrt(2*y*aceleracion);
    }
    
    /**
     * Regresa el valor de vx necesario para que la pelota llegue al punto
     * (posX, posY) dado el valor actual de vy y aceleracion.
     * @param posX coordenada x del punto deseado
     * @param posY coordenada y del punto deseado
     * @return 
     */
    private double getVx(double posX, double posY) {
        double t = (vy + Math.sqrt(vy*vy - 2*aceleracion*(y - posY)))/aceleracion;
        return (posX-x)/t;
    }
    
    public boolean contiene(int posX, int posY) {
        int distCentroX = (getPosX() + getAncho()/2) - posX;
        int distCentroY = (getPosY() + getAlto()/2) - posY;
        
        return (distCentroX*distCentroX + distCentroY*distCentroY <= getAncho()*getAncho()/4);
    }
    
    public void setMov(boolean m) {
        mov = m;
    }
    
    public boolean getMov() {
        return mov;
    }
    
    public void setX(int X) {
        x = X;
        setPosX(X);
    }
    
    public void setY(int Y) {
        y = Y;
        setPosY(Y);
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
            anim.sumaCuadro (Toolkit.getDefaultToolkit ().getImage (Pelota.class.getResource ("Images/ball/basketball" + i + ".png")), 100);
        }
        return anim;
    }
    
    public String getData() {
        
        String salida = String.valueOf(vx) + "," + String.valueOf (vy) + String.valueOf (x);
        salida += String.valueOf (y) + String.valueOf(mov) + String.valueOf(startTime);
        salida += String.valueOf(aceleracion);
        return salida;
        
    }

}
