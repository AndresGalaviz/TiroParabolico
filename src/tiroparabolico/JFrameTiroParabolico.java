package tiroparabolico;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JFrame;

public class JFrameTiroParabolico extends JFrame implements Runnable, KeyListener, MouseListener {

    private static final long serialVersionUID = 1L;

    private Pelota pelota;
    private Canasta canasta;
    private boolean pausa;
    private int num;
    private int direccion;
    private long tiempo;
    private long tMensaje;
    private Image dbImage;
    private Graphics dbg;
    private SoundClip shoot;
    private SoundClip bang;

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
        malos = new LinkedList();
        bueno = new Bueno(getWidth() / 2, getHeight() / 2);
        bueno.setPosX(bueno.getPosX() - bueno.getAncho() / 2);
        bueno.setPosY(bueno.getPosY() - bueno.getAlto() / 2);
        pausa = false;
        tMensaje = 500;
        tiempo = System.currentTimeMillis() - tMensaje - 1;
        Random r = new Random();
        // Al azar, elige la cantidad de malos que aparecerán
        int[] opNum = {6, 10, 12};
        num = opNum[r.nextInt(3)];
        direccion = 0;
        for (int i = 0; i < num; i++) {
            int velocidad = r.nextInt(4) + 3;
            Malo malo = new Malo(0, 0, i, (int) (2 * (i % 2 - 0.5)), velocidad);
            int posY = r.nextInt((getHeight() - malo.getAlto()) * 3 / 4) + getHeight() / 8;
            malo.reaparece(getWidth(), getHeight());
            malos.add(malo);
        }
        //Pinta el fondo del Applet de color blanco
        setBackground(Color.white);
        bang = new SoundClip("sounds/shotgun.wav");
        shoot = new SoundClip("sounds/skorpion.wav");
        
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

        //Se mueve malo de acuerdo a su direccion y velocidad
        for (Malo malo : malos) {
            malo.setPosX(malo.getPosX() + malo.getDireccion() * malo.getVelocidad());
        }

        //Se mueve bueno de acuerdo a la direccion actual
        switch (direccion) {
            case 1: { // arriba
                bueno.setPosY(bueno.getPosY() - 3);
                break;
            }
            case 2: { // derecha
                bueno.setPosX(bueno.getPosX() + 3);
                break;
            }
            case 3: { // abajo
                bueno.setPosY(bueno.getPosY() + 3);
                break;
            }
            case 4: { // izquierda
                bueno.setPosX(bueno.getPosX() - 3);
                break;
            }
        }

        //Actualiza la animación en base al tiempo transcurrido
        bueno.actualiza(tiempoTranscurrido);
        for (Malo malo : malos) {
            malo.actualiza(tiempoTranscurrido);
        }
    }

    /**
     * Metodo usado para checar las colisiones de los personajes con las orillas
     * del <code>Applet</code> y entre si.
     */
    public void checaColision() {
        if (bueno.getPosX() < 0) {
            bueno.setPosX(0);
        }
        if (bueno.getPosX() + bueno.getAncho() > getWidth()) {
            bueno.setPosX(getWidth() - bueno.getAncho());
        }
        if (bueno.getPosY() < 0) {
            bueno.setPosY(0);
        }
        if (bueno.getPosY() + bueno.getAlto() > getHeight()) {
            bueno.setPosY(getHeight() - bueno.getAlto());
        }
        Random r = new Random();
        for (Malo malo : malos) {
            if (malo.intersecta(bueno)) {
                bang.play();
                Malo.setScore(Malo.getScore() + 1);
                tiempo = System.currentTimeMillis();
                malo.reaparece(getWidth(), getHeight());
            }

            // Checa dependiendo de la direccion por que lado salio de la pantalla
            if (malo.getDireccion() == 1 && malo.getPosX() > getWidth()) {
                shoot.play();
                malo.reaparece(getWidth(), getHeight());
            } else if (malo.getDireccion() == -1 && malo.getPosX() + malo.getAncho() < 0) {
                shoot.play();
                malo.reaparece(getWidth(), getHeight());
            }
        }

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
        //g.fillRect(0, 0, WIDTH, HEIGHT);
        // Muestra en pantalla el cuadro actual de la animación
        if (bueno != null && bueno.getImagenI() != null) {
            g.drawImage(bueno.getImagenI(), bueno.getPosX(), bueno.getPosY(), this);
        }

        if (malos != null) {
            for (Malo malo : malos) {
                // Muestra en pantalla el cuadro actual de la animación
                if (malo.getImagenI() != null) {
                    g.drawImage(malo.getImagenI(), malo.getPosX(), malo.getPosY(), this);
                }
            }
        }

        g.setFont(new Font("default", Font.BOLD, 16));
        if (pausa) { // mensaje de pausa
            g.setColor(Color.blue);
            g.drawString(bueno.getPausa(), bueno.getPosX() - 13, bueno.getPosY() + bueno.getAlto() / 2);
        } else if (System.currentTimeMillis() - tiempo < tMensaje) { // mensaje de desaparece
            g.setColor(Color.green);
            g.drawString(bueno.getDesaparece(), bueno.getPosX() - 26, bueno.getPosY() + bueno.getAlto() / 2);
        }

        g.setColor(Color.red);
        //g.drawString("10", 20, 50);
        g.drawString(String.valueOf(Malo.getScore()), 20, 53);
        //System.out.println(String.valueOf(Malo.getScore()));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Cambia la direccion de bueno, segun el cuadrante seleccionado
     *
     * @param e evento del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int centroX = bueno.getPosX() + bueno.getAncho() / 2;
        int centroY = bueno.getPosY() + bueno.getAlto() / 2;
        if (e.getX() > centroX) {
            if (e.getY() < centroY) {
                direccion = 1;
            } else {
                direccion = 2;
            }
        } else {
            if (e.getY() > centroY) {
                direccion = 3;
            } else {
                direccion = 4;
            }
        }
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
    
    /*public static void main(String[] args) {
        JFrameExamen examen = new JFrameExamen();
        examen.setVisible(true);
    }*/
}
