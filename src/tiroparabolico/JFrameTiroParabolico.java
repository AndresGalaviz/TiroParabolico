package tiroparabolico;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class JFrameTiroParabolico extends JFrame implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private Pelota pelota;
    private Canasta canasta;
    private boolean pausa;
    private int vidas;
    private int score;
    private long tiempo;
    private long tMensaje;
    private Image dbImage;
    private Graphics dbg;
    private SoundClip bang;
    private SoundClip shoot;

    //Variables de control de tiempo de la animacion
    private long tiempoActual;

    /**
     * Método constructor de la clase <code>JFrameExamen</code>.
     */
    public JFrameTiroParabolico() {
        setTitle("Examen");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();
        start();
    }
    
    /**
     * Metodo <I>init</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se inizializan las variables o se crean los objetos a
     * usarse en el <code>Applet</code> y se definen funcionalidades.
     */
    public void init(){
        addKeyListener(this);
        addMouseListener(this);
        Base.setW(getWidth());
        Base.setH(getHeight());
        pelota = new Pelota(0,0);
        pelota.setX(getWidth()/5 - pelota.getAncho());
        pelota.setY(getHeight()/2 - pelota.getAlto());
        canasta = new Canasta(0,0);
        canasta.setPosX((int)(Math.random()*(getWidth()/2 - canasta.getAncho())) + getWidth()/2);
        canasta.setPosY(getHeight() - 3*canasta.getAlto()/2);

        pausa = false;
        tMensaje = 500;
        tiempo = System.currentTimeMillis() - tMensaje - 1;
        vidas = 5;
        score = 0;

        //Pinta el fondo del Applet de color blanco
        setBackground(Color.white);
        shoot = new SoundClip();
        bang = new SoundClip();
        
    }
    
    /**
     * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
     * En este metodo se crea e inicializa el hilo para la animacion este metodo
     * es llamado despues del init o cuando el usuario visita otra pagina y
     * luego regresa a la pagina en donde esta este <code>Applet</code>
     *
     */
    public void start() {

        //Crea el thread
        Thread th = new Thread(this);
        //Inicializa el thread
        th.start();
    }

    /**
     * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se
     * incrementa la posicion en x o y dependiendo de la direccion, finalmente
     * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
     *
     */
    public void run() {
        //Guarda el tiempo actual del sistema
        tiempoActual = System.currentTimeMillis();
        //Ciclo principal del Applet. Actualiza y despliega en pantalla la 
        //animacion hasta que el Applet sea cerrado
        while (true) {
            if (!pausa) {
                checaColision();
                //Actualiza la animacion
                actualiza();
            }
            //Manda a llamar al metodo paint() para mostrar en pantalla la animación
            repaint();
            //Hace una pausa de 20 milisegundos
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }

    }

    /**
     * El método actualiza() actualiza la animación
     */
    public void actualiza() {

        //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;

        //Guarda el tiempo actual
        tiempoActual += tiempoTranscurrido;

        pelota.avanza();
        
        if (canasta.getMoveLeft()) {
            canasta.setPosX(canasta.getPosX() - 3);
        }
        if (canasta.getMoveRight()) {
            canasta.setPosX(canasta.getPosX() + 3);
        }

        //Actualiza la animación en base al tiempo transcurrido
        pelota.actualiza(tiempoTranscurrido);
        canasta.actualiza(tiempoTranscurrido);
    }

    /**
     * Metodo usado para checar las colisiones de los personajes con las orillas
     * del <code>Applet</code> y entre si.
     */
    public void checaColision() {
        if (canasta.getPosX() < getWidth()/2) {
            canasta.setPosX(getWidth()/2);
        }
        if (canasta.getPosX() + canasta.getAncho() > getWidth()) {
            canasta.setPosX(getWidth() - canasta.getAncho());
        }

        
        // Colision pelota-canasta

    }

    /**
     * Metodo <I>update</I> sobrescrito de la clase <code>Applet</code>,
     * heredado de la clase Container.<P>
     * En este metodo lo que hace es actualizar el contenedor
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);

        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>, heredado
     * de la clase Container.<P>
     * En este metodo se dibuja la imagen con la posicion actualizada, ademas
     * que cuando la imagen es cargada te despliega una advertencia.
     *
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    public void paint1(Graphics g) {
        //g.setColor(Color.RED);
        //g.fillRect(0, 0, getWidth(), getHeight());
        // Muestra en pantalla el cuadro actual de la animación
        if (pelota != null && pelota.getImagenI() != null) {
            g.drawImage(pelota.getImagenI(), pelota.getPosX(), pelota.getPosY(), this);
        }

        if (canasta != null && canasta.getImagenI() != null) {
            g.drawImage(canasta.getImagenI(), canasta.getPosX(), canasta.getPosY(), this);
        }

        g.setFont(new Font("default", Font.BOLD, 16));
        if (pausa) { // mensaje de pausa
            g.setColor(Color.blue);
            g.drawString("PAUSA", canasta.getPosX() - 13, canasta.getPosY() + canasta.getAlto() / 2);
        }

        g.setColor(Color.red);
        g.drawString(String.valueOf(score), 20, 53);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * Define el sentido del movimiento de <code>canasta</code>
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            canasta.setMoveLeft(true);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            canasta.setMoveRight(true);
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        }
    }

    /**
     * Define el sentido del movimiento de <code>canasta</code>
     * @param e 
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            canasta.setMoveLeft(false);
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            canasta.setMoveRight(false);
        }
    }

    /**
     * Inicia el movimiento de <code>pelota</code>.
     * @param e 
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!pelota.getMov() && pelota.contiene(e.getX(), e.getY())) {
            pelota.lanzar();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
