package tiroparabolico;


import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Base {

    private double posX;    //posicion en x.       
    private double posY;    //posicion en y.
    
    private Animacion animacion;  //animacion del objeto

    /**
     * Metodo constructor usado para crear el objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param image es la <code>imagen</code> del objeto.
     */
    public Base(int posX, int posY, Animacion animacion) {
        this.posX = (double)posX;
        this.posY = (double)posY;
        this.animacion = animacion;
    }

    /**
     * Metodo modificador usado para cambiar la posicion entera en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion en x del objeto
     *
     * @param posX es la <code>posicion en x</code> del objeto.
     */
    public void setDoublePosX(double posX) {
        this.posX = posX;
    }
    
    /**
     * Metodo de acceso que regresa la posicion en x del objeto como entero
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public int getPosX() {
        return (int)posX;
    }
    
    /**
     * Metodo de acceso que regresa la posicion en x del objeto
     *
     * @return posX es la <code>posicion en x</code> del objeto.
     */
    public double getDoublePosX() {
        return posX;
    }

    /**
     * Metodo modificador usado para cambiar la posicion entera en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    /**
     * Metodo modificador usado para cambiar la posicion en y del objeto
     *
     * @param posY es la <code>posicion en y</code> del objeto.
     */
    public void setDoublePosY(double posY) {
        this.posY = posY;
    }
    
    

    /**
     * Metodo de acceso que regresa la posicion en y del objeto
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public int getPosY() {
        return (int)posY;
    }
    
    /**
     * Metodo de acceso que regresa la posicion en y del objeto como entero
     *
     * @return posY es la <code>posicion en y</code> del objeto.
     */
    public double getDoublePosY() {
        return posY;
    }

    /**
     * Metodo de acceso que regresa el icono del objeto
     *
     * @return icono es el <code>icono</code> del objeto.
     */
    public ImageIcon getImageIcon() {
        return animacion.getImagen();
    }

    /**
     * Metodo de acceso que regresa el ancho del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del
     * icono.
     */
    public int getAncho() {
        return animacion.getImagen().getIconWidth();
    }

    /**
     * Metodo de acceso que regresa el alto del icono
     *
     * @return un objeto de la clase <code>ImageIcon</code> que es el alto del
     * icono.
     */
    public int getAlto() {
        return animacion.getImagen().getIconHeight();
    }

    /**
     * Metodo de acceso que regresa la imagen del icono
     *
     * @return un objeto de la clase <code>Image</code> que es la imagen del
     * icono.
     */
    public Image getImagenI() {
        return animacion.getImagen().getImage();
    }

    /**
     * Metodo de acceso que regresa un nuevo rectangulo
     *
     * @return un objeto de la clase <code>Rectangle</code> que es el perimetro
     * del rectangulo
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getPosX(), getPosY(), getAncho(), getAlto());
    }

    /**
     * Checa si el objeto <code>Base</code> intersecta a otro
     * <code>Base</code>
     *
     * @return un valor boleano <code>true</code> si lo intersecta
     * <code>false</code> en caso contrario
     */
    public boolean intersecta(Base obj) {
        return getPerimetro().intersects(obj.getPerimetro());
    }

    public boolean intersecta(int x, int y) {
        return getPerimetro().contains(new Point(x, y));
    }

    public void actualiza(long tiempo) {
        animacion.actualiza(tiempo);
    }


}
