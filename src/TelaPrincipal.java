
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;


public class TelaPrincipal extends JFrame {
    PainelDesenhavel pd;
    JLabel rodape = new JLabel("Imagem Original...");

    // menu
    JMenuBar menuBar;
    JMenu menuAjuda;
    JMenuItem itemSobre;
    JMenuItem itemSair;
    JMenuItem itemSalvar;
    JMenu menuArquivo;

    // seletor de cor
    JPanel seletorFlow;

    MouseAdapter ma = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e){
            super.mouseClicked(e);
            System.out.println(e.getX());
        }
        @Override
        public void mouseDragged(MouseEvent e){
            super.mouseDragged(e);
            System.out.println(e.getX()+ " "+ e.getY());
            pd.desenhar(e.getX() - 10, e.getY() - 10);
            rodape.setText("Imagem Modificada");
        }
    };


    public void montarTela(){
        setTitle("Paint PROG II");
        setSize(1080, 720);

        criarPainelDesenhavel(getWidth(), getHeight());
        criarMenu();
        criarRodape();
        criarSeletorDeCores();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void criarPainelDesenhavel(int x, int y) {
        pd = new PainelDesenhavel(x, y);
        pd.addMouseListener(ma);
        pd.addMouseMotionListener(ma);
        // aumenta o tamanho do traço
        pd.addMouseWheelListener((e) -> {
            if(e.getWheelRotation() < 0){
                pd.setTamanho(pd.getTamanho() + 10);
            } else {
                if(pd.getTamanho() - 10 > 0){
                    pd.setTamanho(pd.getTamanho() - 10);
                }
            }
            System.out.println("Rodou o Scroll do mouse!");
        });
        getContentPane().add(pd, BorderLayout.CENTER);
    }

    private void criarMenu() {
        // menu arquivo
        menuArquivo = new JMenu("Arquivo");
        itemSalvar = new JMenuItem("Salvar");
        itemSair = new JMenuItem("Sair");
        menuArquivo.add(itemSalvar);
        menuArquivo.add(itemSair);

        // action atualizar o rodape e salvar um jpg
        itemSalvar.addActionListener((e) -> salvarImagem());

        itemSair.addActionListener((e) -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        // menu ajuda
        menuAjuda = new JMenu("Ajuda");
        itemSobre = new JMenuItem("Sobre");
        menuAjuda.add(itemSobre);
        itemSobre.addActionListener((e) -> JOptionPane.showMessageDialog(null, "Atividade De Prog II"));

        // add o menu
        menuBar = new JMenuBar();
        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);
    }

    private void salvarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
            rodape.setText("Imagem Original");
            pd.salvarPainel(fileChooser);
        } else {
            JOptionPane.showMessageDialog(null, "Imagem não foi salva...");
        }
    }

    private void criarRodape() {
        rodape.setBackground(Color.lightGray);
        getContentPane().add(rodape, BorderLayout.SOUTH);
    }

    private void criarSeletorDeCores() {

        // lista de cores
        DefaultListModel<Color> model = new DefaultListModel<>();
        model.addElement(Color.BLACK);
        model.addElement(Color.BLUE);
        model.addElement(Color.GRAY);
        model.addElement(Color.GREEN);
        model.addElement(Color.PINK);
        model.addElement(Color.ORANGE);
        model.addElement(Color.RED);
        model.addElement(Color.YELLOW);
        model.addElement(Color.WHITE);

        JList<Color> listaDeCores = new JList<>(model);

        // cria botoes de acordo com as cores na lista anterior
        DefaultListModel<JButton> botoes = new DefaultListModel<>();
        for (int i = 0; i < listaDeCores.getModel().getSize(); i++) {
            JButton bt = new JButton("...");
            bt.setBackground(listaDeCores.getModel().getElementAt(i));
            bt.addActionListener(e -> {
                pd.setCor(bt.getBackground());
                System.out.println("Cor alterada para: " + bt.getBackground());
            });
            botoes.addElement(bt);
        }
        JList<JButton> listaDeBotoes = new JList<>(botoes);

        // add botoes ao Jpanel
        seletorFlow = new JPanel(new FlowLayout());
        JPanel seletorDeCores = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < listaDeBotoes.getModel().getSize(); i++) {
            seletorDeCores.add(listaDeBotoes.getModel().getElementAt(i));
        }

        seletorFlow.add(seletorDeCores);
        getContentPane().add(seletorFlow, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        TelaPrincipal tp = new TelaPrincipal();
        tp.montarTela();
    }
}
