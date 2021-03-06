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
    private static double aceleracion = 200;
    
    /**
     * Metodo constructor.
     * @param posX coordenada x inicial.
     * @param posY coordenada y inicial.
     */
    public Pelota(int posX, int posY) {
        super(posX, posY, crearAnimacionPelota());
        x = posX;
        y = posY;
        freezeTime = System.currentTimeMillis();
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
        double maxVy = .94*getMaxVy();
        double minVy = .3*maxVy;
        vy = Math.random()*(maxVy - minVy) + minVy;
        double maxVx = .97*getVx(getW() - getAncho() + 10, getH() - getAlto());
        double minVx = getVx(getW()/2 + 20, getH() - 2*getAlto());
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
        }
    }
    /**
     * La pelota se mueve de acuerdo al tiempo, velocidad en X y Y, y gravedad.
     */
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
    
    /**
     * Dice si el punto (posX, posY) esta dentro de la pelota, no solo del
     * cuadrado que la contiene
     * @param posX coordenada X del punto
     * @param posY coordenada Y del punto
     * @return <code>true</code> si está en el interior, sino <code>false</code>.
     */
    public boolean contiene(int posX, int posY) {
        int distCentroX = (getPosX() + getAncho()/2) - posX;
        int distCentroY = (getPosY() + getAlto()/2) - posY;
        
        return (distCentroX*distCentroX + distCentroY*distCentroY <= getAncho()*getAncho()/4);
    }
    
    /**
     * Indica si la pelota está en movimiento o no
     * @param m valor que tomará <code>mov</code>
     */
    public void setMov(boolean m) {
        mov = m;
    }
    
    /**
     * Regresa si <code>Pelota</code> esta en movimiento o no
     * @return true o false
     */
    public boolean getMov() {
        return mov;
    }
    
    /**
     * Establece la posición inicial en X
     * @param X coordenada X
     */
    public void setX(int X) {
        x = X;
        setPosX(X);
    }
    
    /**
     * Establece la posición inicial en Y
     * @param Y coordenada Y
     */
    public void setY(int Y) {
        y = Y;
        setPosY(Y);
    }
    
    /**
     * Establece el valor de vx
     * @param v un <code>double</code>
     */
    public void setVx(double v) {
        vx = v;
    }
    
    /**
     * Establece el valor de la gravedad
     * @param a un valor de tipo <code>double</code>
     */
    public static void setAceleracion(double a) {
        aceleracion = a;
    }
    
    /**
     * Regresa el valor de la gravedad
     * @return <code>aceleracion</code>
     */
    public static double getAceleracion() {
        return aceleracion;
    }
    
    /**
     * Crea la animación de la pelota para el constructor
     * @return un objeto de tipo <code>Animacion</code>
     */
    private static Animacion crearAnimacionPelota() {
        Animacion anim = new Animacion();
        for (int i = 0; i <= 20; i++) {
            anim.sumaCuadro (Toolkit.getDefaultToolkit ().getImage (Pelota.class.getResource ("Images/ball/basketball" + i + ".png")), 60);
        }
        return anim;
    }
    
    public String getData() {
        long dif1 = System.currentTimeMillis() - startTime;
        long dif2 = System.currentTimeMillis() - freezeTime;
        String salida = String.valueOf(dif1)+","+String.valueOf(dif2)+","+ String.valueOf(vx) + ",";
        salida += String.valueOf (vy) + "," + String.valueOf (x)+ ","+ String.valueOf (y)+",";
        salida += String.valueOf(aceleracion)+ ","+String.valueOf(mov) ;
        return salida;
        
    }
    public void assingData(String[] arr) {
        
        long dif1 = Long.parseLong(arr[5]);
        startTime = System.currentTimeMillis() - dif1;
        long dif2 = Long.parseLong(arr[6]);
        freezeTime = System.currentTimeMillis() - dif2;
        vx = Double.parseDouble(arr[7]);
        vy = Double.parseDouble(arr[8]);
        x = Double.parseDouble(arr[9]);
        y = Double.parseDouble(arr[10]);

        
        aceleracion = Double.parseDouble(arr[11]);
        mov = Boolean.parseBoolean(arr[12]);
        avanza();
 

    }

}
