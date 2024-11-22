package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;

    private final String claveCorrecta = "123456";
    private int intentosRestantes = 3;
    private ArrayList<JLabel> circulosClave = new ArrayList<>();
    private ArrayList<JButton> botones = new ArrayList<>();
    private StringBuilder claveActual = new StringBuilder();
    private JPanel panelTeclado;

    public Login() {
        setTitle("Yape Login");
        setSize(400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(97, 32, 123));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setOpaque(false);

        // ComponentesPrincipales
        panelPrincipal.add(crearPanelSuperior());
        panelPrincipal.add(crearPanelInferior());

        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearPanelSuperior() {
        // panelSuperior
        JPanel panelSuperior = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // gradienteSuperior
                    Color moradoClaro = new Color(140, 61, 153);
                    Color moradoOscuro = new Color(75, 25, 113);
                    LinearGradientPaint degradado = new LinearGradientPaint(
                            0, 0, 0, getHeight(),
                            new float[]{0.0f, 1.0f}, new Color[]{moradoClaro, moradoOscuro}
                    );

                    g2.setPaint(degradado);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };

        panelSuperior.setPreferredSize(new Dimension(400, 350));
        panelSuperior.setLayout(null);

        // agregarBotonAyuda
        panelSuperior.add(crearBotonAyuda());

        // agregarQrDecorativo
        panelSuperior.add(crearEtiquetaQr());

        return panelSuperior;
    }

    private JButton crearBotonAyuda() {
        // botonAyuda
        JButton botonAyuda = new JButton("Ayuda") {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(140, 51, 168));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
                super.paintComponent(g);
            }
        };

        botonAyuda.setFont(new Font("Arial", Font.BOLD, 12));
        botonAyuda.setForeground(Color.WHITE);
        botonAyuda.setBounds(260, 10, 100, 30);
        botonAyuda.setFocusPainted(false);
        botonAyuda.setBorderPainted(false);
        botonAyuda.setContentAreaFilled(false);

        // IconoAyuda
        ImageIcon iconoOriginal = new ImageIcon("src/ui/img/ayuda.jpg");
        Image iconoEscalado = iconoOriginal.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        botonAyuda.setIcon(new ImageIcon(iconoEscalado));

        botonAyuda.setIconTextGap(5);
        botonAyuda.setHorizontalAlignment(SwingConstants.LEFT);
        botonAyuda.setHorizontalTextPosition(SwingConstants.RIGHT);
        botonAyuda.setVerticalTextPosition(SwingConstants.CENTER);

        botonAyuda.addActionListener(e -> JOptionPane.showMessageDialog(this, "Contacta al soporte para ayuda."));
        return botonAyuda;
    }
    private JLabel crearEtiquetaQr() {
        // etiquetaQr
        ImageIcon iconoQr = new ImageIcon("src/ui/img/Qr.jpg");
        Image imagenEscalada = iconoQr.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

        JLabel etiquetaQr = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int radio = 15; // esquinasRedondeadas
                    g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radio, radio));
                    g2.drawImage(imagenEscalada, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        etiquetaQr.setBounds(125, 65, 130, 130);
        return etiquetaQr;
    }

    private JPanel crearPanelInferior() {
        // panelInferior, PantallaIngresoyTeclado
        JPanel panelInferior = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // gradienteInferior
                    Color moradoOscuro = new Color(75, 25, 113);
                    g2.setPaint(moradoOscuro);
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    // fondoBlanco
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 10, getWidth(), getHeight(), 30, 25);
                }
            }
        };

        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelInferior.add(crearEtiquetaClave());
        panelInferior.add(Box.createVerticalStrut(10));

        panelInferior.add(crearPanelCirculosClave());
        panelInferior.add(Box.createHorizontalStrut(20));

        panelTeclado = new JPanel(new GridLayout(4, 3, 10, 10));
        panelTeclado.setBackground(Color.WHITE);
        generarTecladoAleatorio();
        panelInferior.add(panelTeclado);

        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(crearEtiquetaOlvidoClave());
        return panelInferior;
    }
    private JLabel crearEtiquetaClave(){
        JLabel etiquetaClave = new JLabel("Ingresa tu clave",SwingConstants.CENTER);
        etiquetaClave.setForeground(new Color(114, 24, 138));
        etiquetaClave.setFont(new Font("Arial", Font.BOLD, 18));
        etiquetaClave.setAlignmentX(Component.CENTER_ALIGNMENT);
        return etiquetaClave;
    }

    private JPanel crearPanelCirculosClave(){
        JPanel panelCirculos = new JPanel();
        panelCirculos.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelCirculos.setBackground(Color.WHITE);

        for (int i = 0; i<6; i++){
            JLabel circulo = new JLabel("○", SwingConstants.CENTER);
            circulo.setFont(new Font("Arial", Font.BOLD, 24));
            circulo.setForeground(Color.LIGHT_GRAY);
            circulosClave.add(circulo);
            panelCirculos.add(circulo);
        }
        return panelCirculos;
    }

    private JLabel crearEtiquetaOlvidoClave(){
        JLabel etiquetaOlvido = new JLabel("¿OLVIDASTE TU CLAVE?", SwingConstants.CENTER);
        etiquetaOlvido.setForeground(new Color(0, 150, 136));
        etiquetaOlvido.setFont(new Font("Arial", Font.PLAIN, 12));
        etiquetaOlvido.setAlignmentX(Component.CENTER_ALIGNMENT);
        return etiquetaOlvido;
    }

    private void generarTecladoAleatorio(){
        panelTeclado.removeAll();
        botones.clear();

        ArrayList<String> numeros = new ArrayList<>();
        for (int i = 1; i <= 9 ; i++){
            numeros.add(String.valueOf(i));
        }

        numeros.add("0");
        Collections.shuffle(numeros);

        for(int i = 0; i<9; i++ ){
            String numero = numeros.get(i);
            JButton boton = crearBotonRedondeado(numero, new Color(237, 235, 243), null);
            boton.addActionListener(e -> manejarClickBoton(numero));
            botones.add(boton);
            panelTeclado.add(boton);
        }

        JButton botonHuella = crearBotonBlanco("");
        ImageIcon iconoHuella = new ImageIcon("src/ui/img/Botonhuella.png");
        Image huellaEscalada = iconoHuella.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        botonHuella.setIcon(new ImageIcon(huellaEscalada));
        botonHuella.addActionListener(e -> JOptionPane.showMessageDialog(this, "Acceso por huella dactilar"));
        panelTeclado.add(botonHuella);

        // BotónCero
        String cero = numeros.get(9);
        JButton botonCero = crearBotonRedondeado(cero, new Color(237, 235, 243), null);
        botonCero.addActionListener(e -> manejarClickBoton(cero));
        botones.add(botonCero);
        panelTeclado.add(botonCero);

        // BotónBorrar
        JButton botonBorrar = crearBotonBlanco("");
        ImageIcon iconoBorrar = new ImageIcon("src/ui/img/Botonborrar.png");
        Image borrarEscalado = iconoBorrar.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
        botonBorrar.setIcon(new ImageIcon(borrarEscalado));
        botonBorrar.addActionListener(e -> borrarUltimoDigito());
        panelTeclado.add(botonBorrar);

        panelTeclado.setBackground(Color.WHITE);
        panelTeclado.revalidate();
        panelTeclado.repaint();

    }

    private JButton crearBotonRedondeado(String texto, Color colorFondo, Color colorBorde) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(colorFondo != null ? colorFondo : new Color(237, 235, 243));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                }
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                if (colorBorde != null && g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(colorBorde);
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                }
            }
        };

        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(Color.BLACK);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(false);
        boton.setPreferredSize(new Dimension(60, 60));
        return boton;

    }

    private JButton crearBotonBlanco(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(true);
        boton.setOpaque(true);
        boton.setBorder(null);
        boton.setPreferredSize(new Dimension(60, 60));
        return boton;
    }

    private void manejarClickBoton(String digito){
        if (claveActual.length()<6){
            circulosClave.get(claveActual.length()).setText("●");
            circulosClave.get(claveActual.length()).setForeground(Color.BLACK);
            claveActual.append(digito);
        }

        if (claveActual.length()==6){
            verificarClave();
        }

    }

    private void verificarClave(){
        if(claveActual.toString().equals(claveCorrecta)){
            JOptionPane.showMessageDialog(this, "         1 SOL DE SALDO");
            System.exit(0);
        }else{
            intentosRestantes--;
            if(intentosRestantes > 0) {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta. Intentos restantes: " + intentosRestantes);
                reiniciarClave();
                generarTecladoAleatorio();
            }else{
                JOptionPane.showMessageDialog(this, "App bloqueada");
                botones.forEach(boton -> boton.setEnabled(false));
            }
        }
    }

    private void reiniciarClave(){
        circulosClave.forEach(circulo ->{
            circulo.setText("○");
            circulo.setForeground(Color.LIGHT_GRAY);
        });
        claveActual.setLength(0);
    }

    private void borrarUltimoDigito(){
        if(claveActual.length()>0){
            claveActual.deleteCharAt(claveActual.length() - 1);

            int longitud = claveActual.length();
            for(int i = 0; i< circulosClave.size(); i++){
                if(i<longitud){
                    circulosClave.get(i).setText("●"); 
                }else{
                    circulosClave.get(i).setText("○"); 
                }
            }
        }
        
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}