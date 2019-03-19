/*SPACE INVADERS: Kill them all*/
package codigo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

/*
 * @author Pablo Serrano Manzarbeitia
 */


public class VentanaJuego extends javax.swing.JFrame {

    //Declaro dos constantes, dos valores que no van a cambiar durante el juego
    
    static int ANCHOPANTALLA = 600;
    static int ALTOPANTALLA = 450;
    
    //Declaro el numero de marcianos que van a aparecer
    int filas = 5;
    int columnas = 10;
    
      /*El buffer lo que hace es ALMACENAR temporalmente la pantalla del juego, 
    para posteriormente plasmarla*/
    
    BufferedImage buffer = null;
    
    Nave miNave = new Nave();
    Disparo miDisparo = new Disparo();
    //Marciano miMarciano = new Marciano();
    /*Declaramos un array de dos dimensiones para los marcianos (TABLA)*/
    Marciano [][] listaMarcianos = new Marciano [filas] [columnas];
    boolean direccionMarcianos = false;
    //El contador sirve para decidir que imagen del marciano toca poner
    int contador; //Si no indicamos que valor, automaticamente el int se iguala a cero
    
    
    
    
      /*Ponemos un temporizador con un import que cada 10 segundos va a efectuar un
    nuevo actionlistener, el cual va a invocar a un nuevo método--> bucleDelJuego*/
    
    Timer temporizador = new Timer (10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            bucleDelJuego();
            }
    });
    
  
    
    public VentanaJuego() {
        initComponents();
        setSize (ANCHOPANTALLA, ALTOPANTALLA);
        buffer = (BufferedImage) jPanel1.createImage(ANCHOPANTALLA, ALTOPANTALLA);
        buffer.createGraphics();
        //Iniciamos el temporizador
        temporizador.start();
        
        //Inicializo la posición inical de la nave
        miNave.x = ANCHOPANTALLA /2 - miNave.imagen.getWidth(this) /2;
        miNave.y = ALTOPANTALLA - miNave.imagen.getHeight(this) -40;
        
        //Inicializo el array de marcianos
        for (int i=0; i < filas; i++){
            for (int j=0; j < columnas; j++){
                listaMarcianos[i][j] = new Marciano();
                listaMarcianos[i][j].x = j*(15 + listaMarcianos[i][j].imagen1.getWidth(null));
                listaMarcianos[i][j].y = i*(10 + listaMarcianos[i][j].imagen1.getHeight(null));
            }
        }
        
    }
    
    
    /*Controla el redibujado de los objetos del JPanel1(pantalla)*/
    
    private void bucleDelJuego() {
        contador++;
        /*PRIMERO: BORRO TODO LO QUE HAY EN EL BUFFER*/
           //Aqui "apunto" a donde quiero borrar, AL BUFFER
        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setColor(Color.BLACK);
            //Aquí borro, resetea, lo pinta de negro
        g2.fillRect(0, 0, ANCHOPANTALLA, ALTOPANTALLA);
       
        ////////////////////////////////////////////////
        /*SEGUNDO: REDIBUJAMOS CADA ELEMENTO EN SU POSICION*/
        g2.drawImage(miNave.imagen, miNave.x, miNave.y, null);
        g2.drawImage(miDisparo.imagen, miDisparo.x, miDisparo.y, null);
       // g2.drawImage(miMarciano.imagen1, miMarciano.x, miMarciano.y, null);
        pintaMarcianos(g2);
        chequeaColision();
        miNave.mueve();
        miDisparo.mueve();
        //miMarciano.mueve();
        
        //////////////////////////////////////////////
        /*TERCERO: SE DIBUJA DE GOLPE SOBRE EL JPANEL*/
        
        g2 = (Graphics2D) jPanel1.getGraphics();
        g2.drawImage(buffer, 0, 0, null);
        
    }
       
    private void chequeaColision(){
        Rectangle2D.Double rectanguloMarciano = new Rectangle2D.Double();
        Rectangle2D.Double rectanguloDisparo = new Rectangle2D.Double();
        //Este comando sirve para ajustar las dimensiones del rectangulo:
        rectanguloDisparo.setFrame(miDisparo.x, miDisparo.y, miDisparo.imagen.getWidth(null),
                                    miDisparo.imagen.getHeight(null));
        
        for (int i=0; i < filas; i++) {
            for (int j=0; j < columnas; j++) {
                rectanguloMarciano.setFrame(listaMarcianos[i][j].x, listaMarcianos[i][j].y, 
                                            listaMarcianos[i][j].imagen1.getWidth(null),
                                            listaMarcianos[i][j].imagen1.getHeight(null)
                                            );
                if (rectanguloDisparo.intersects(rectanguloMarciano)) {
                    listaMarcianos[i][j].y = 2000;
                    miDisparo.y = 30000;
                }
            }
        }
    }
    
    private void cambiaDireccionMarcianos() {
        for (int i=0; i < filas; i++) {
            for (int j=0; j < columnas; j++) {
                listaMarcianos[i][j].setvX(listaMarcianos[i][j].getvX()*-1);
            }
        }
    } 
    
    private void pintaMarcianos(Graphics2D _g2) {
        //Declaro un int de Marciano por no tener que escribir todo esto tol rato
        int anchoMarciano = listaMarcianos[0][0].imagen1.getWidth(null);
            
        for (int i=0; i < filas; i++){
            for (int j=0; j < columnas; j++){
                listaMarcianos[i][j].mueve();
                /*Chequeo si el marciano se ha chocado con la pared para cambiar de direccion*/
                if (listaMarcianos[i][j].x + anchoMarciano == ANCHOPANTALLA){
                    cambiaDireccionMarcianos();
                }
                if (listaMarcianos[i][j].x == 0) {
                    cambiaDireccionMarcianos();
                }
                //El contador nos indica que imagen poner
                if (contador < 50) {
                    _g2.drawImage(listaMarcianos[i][j].imagen1, listaMarcianos[i][j].x,
                                    listaMarcianos[i][j].y, null);
                }
                else if (contador < 100) {
                    _g2.drawImage(listaMarcianos[i][j].imagen2, listaMarcianos[i][j].x,
                                    listaMarcianos[i][j].y, null);
                    }
                    else contador = 0;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 450, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        /*Especificamos que botón queremos que active el evento*/
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT: miNave.setPulsadoIzquierda(true);
            break; //Break significa que no haga la siguiente linea
            case KeyEvent.VK_RIGHT: miNave.setPulsadoDerecha(true);
            break;
            case KeyEvent.VK_SPACE: miDisparo.x = miNave.x;
                                    miDisparo.y = miNave.y;
            break;
        }
    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        /*Desactiva la accion cuando dejamos de pulsar el boton que queremos*/ 
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_LEFT: miNave.setPulsadoIzquierda(false);
            break; 
            case KeyEvent.VK_RIGHT: miNave.setPulsadoDerecha(false);
            break;
        }
    }//GEN-LAST:event_formKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaJuego().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
