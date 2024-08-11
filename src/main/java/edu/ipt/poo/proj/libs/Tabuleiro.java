/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.libs;

import edu.ipt.poo.proj.blocks.BlocoVazio;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Inicia o jogo
 * @author bento
 */
public class Tabuleiro extends JPanel implements ActionListener, MouseListener, KeyListener {

    //blocos que formam o tabuleiro
    Peca[][] jogo;

    //peça que se move
    TetrisPeca pecas;

    //timer que controla a que velocidade o jogo tem 
    Timer timer;

    //variável que controla a pontuação, aumentando cada vez que o jogador meter uma peça no tabuleiro ou fazer uma linha
    public int score = 0;

    //varíavel que controla a quantidade de linhas feitas pelo jogador, aumentando cada vez que a pessoa faça uma linha horizontal 
    public int linhas = 0;

    //label que mostra no GUI o valor da pontuação do jogador
    JLabel labelScore;

    //label que mostra no GUI o valor da quantidade de linhas feitas
    JLabel labelLinhas;
    
    /**
     * Função que inicia o jogo todo
     */
    public Tabuleiro() {
        //inicia o jogo
        initGame();
        
        //inicializa o timer
        timer = new Timer(1000, this);

        //componente que lê o botão do rato que a pessoa pressiona 
        addMouseListener(this);

        //componente que lê as teclas que a pessoa pressiona 
        addKeyListener(this);

        //a aplicação fica sempre à frente
        this.requestFocus();
    }

    /**
     *
     * @return esta função devolve o valor do Timer, no valor de milisegundos
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     *  esta função para o timer, querendo dizer que a app para 
     */
    public void Pause() {
        timer.stop();
    }

    /**
     *  esta função faz com que o timer volte a dar depois de o pararmos
     */
    public void Start() {
        timer.restart();
    }

    /**
     *  esta função cria um novo jogo
     *  score fica a 0
     *  linhas fica a 0
     *  initGame() vai criar um novo jogo completo
     */
    public void NovoJogo() {
        score = 0;
        labelLinhas.setText(String.valueOf(0));
        initGame();
    }
    
    /**
     *  esta função escolhe aleatoreamente uma peça e coloca esta no tabuleiro, aleatora usando o NextInt 
     */
    private void CriaNovasPecas() {
        Random r = new Random();
        //r vai ter um valor entre 0 e 6, consoante for esse número, vai escolher uma imagem diferente
        switch (r.nextInt(7)) {
            case 0:
                pecas = new PecaI();
                break;
            case 1:
                pecas = new PecaJ();
                break;
            case 2:
                pecas = new PecaL();
                break;
            case 3:
                pecas = new PecaO();
                break;
            case 4:
                pecas = new PecaS();
                break;
            case 5:
                pecas = new PecaT();
                break;
            case 6:
                pecas = new PecaZ();
                break;
        }
    }
        
    /**
     *  cria o tabuleiro
     */
    public void initGame() {
        
        //contruir o tabuleiro dado a altura e o comprimento
        jogo = new Peca[20][11];

        //cria os blocos vazios
        for (int y = 0; y < jogo.length; y++) {
            for (int x = 0; x < jogo[y].length; x++) {
                jogo[y][x] = new BlocoVazio();
            }
        }
        CriaNovasPecas();
    }



    /**
     *
     * @param gr desenha o tabuleiro e as peças no tabuleiro
     */
    @Override
    public void paintComponent(Graphics gr) {
        //escolhe a cor do background do tabuleiro
        gr.setColor(Color.BLACK);
        //pinta o tabuleiro com a cor previamente escolhida
        gr.fillRect(0, 0, getWidth(), getHeight());
        //divide-se a largura com com o número de colunas
        int dx = getWidth() / jogo[0].length;
        //divide-se a largura com com o número de linhas
        int dy = getHeight() / jogo.length;
        
        //percorre o previamente construido tabuleiro
        for (int y = 0; y < jogo.length; y++) {
            for (int x = 0; x < jogo[y].length; x++) {
                //desenha todos os blocos do tabuleiro
                jogo[y][x].draw(gr, x * dx, y * dy, dx, dy);
            }
        }
        //desenha as peças no tabuleiro
        pecas.draw(gr, dx * pecas.posX, dy * pecas.posY, dx, dy);
    }

    /**
     *  A cada pulsação do Timer executa esta função
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //faz com que a peça se mova para baixo
        if (pecas.VerificaMove(jogo)) {
            //força a peça a ir para baixo até não puder mais
            pecas.ParaBaixo();
        }
        //faz com que reconheça o final do tabuleiro como uma parede, querendo dizer que não passa do fim
        else {
            //faz com que a peça pare
            pecas.freezePeca(jogo);
            //adiciona 10 pontos no score
            score = score + 10;
            if(youLose()){
                Pause();
                JOptionPane.showMessageDialog(this, "Perdeu :(", "UR A LOSER", 0);
                NovoJogo();
            }
            //faz com que caiam mais peças
            CriaNovasPecas();
        }
        //faz com que o texto da label passe a ser o valor do score na forma de string
        labelScore.setText(toString());
        //requer o foco da aplicaçao
        this.requestFocus();
        
        //verifica todas as linhas na possibilidade de eliminar algumas dessas
        verLinha();
        
        //faz com que vejamos a peça a ir para baixo em tempo real
        repaint();
    }

    /**
     *
     * @param e evento onde o jogador clica nos botões do rato
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //verifica qual o botão pressionado
        if (e.getButton() == MouseEvent.BUTTON1) {
            //se conseguir fazer a rotação faz essa mesma rotação
            if (pecas.VerificaRotate(jogo)) {
                pecas.rotateLeft();
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            //se conseguir fazer a rotação faz essa mesma rotação
            if (pecas.VerificaRotate(jogo)) {
                pecas.rotateRight();
            }
        }
        //pinta de novo o tabuleiro de forma que consigamos ver as peças rodaddas
        repaint();
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

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    /**
     *
     * @param ke evento onde o jogador clica nas teclas
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        //verifica qual a tecla pressionada
        switch (key) {
            case KeyEvent.VK_RIGHT:
                //se conseguir fazer a rotação faz essa mesma rotação
                if (pecas.VerificaDireita(jogo)) {
                    pecas.ParaDireita();
                }
                break;
            case KeyEvent.VK_LEFT:
                //se conseguir fazer a rotação faz essa mesma rotação
                if (pecas.VerificaEsquerda(jogo)) {
                    pecas.ParaEsquerda();

                }
                break;
            case KeyEvent.VK_DOWN:
                //se conseguir fazer a rotação faz essa mesma rotação
                if (pecas.VerificaMove(jogo)) {
                    pecas.ParaBaixo();
                }
                break;
            case KeyEvent.VK_UP:
                //se conseguir fazer a rotação faz essa mesma rotação
                while (pecas.VerificaMove(jogo) == true) {
                    pecas.ParaBaixo();
                }
                break;
            default:
                break;
        }
        //pinta de novo o tabuleiro de forma que consigamos ver as peças a movimentarem-se
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    /**
     *  verifica todas as linhas na possibilidade de eliminar algumas dessas no caso de ter uma linha cheia de peças
     */
    public void verLinha() {
        //vai percorrer todo o tabuleiro
        for (int y = 0; y < jogo.length; y++) {
            //criação de uma flag para ver se ela muda de valor
            boolean apagaLinha = true;
            for (int x = 0; x < jogo[y].length; x++) {
                if ((jogo[y][x] instanceof BlocoVazio)) {
                    //como encontrou um bloco vazio nessa coluna, não vai poder eliminar essa mesma linha
                    //a flag "levanta" e diz que é impossivel apagar essa linha
                    apagaLinha = false;
                    break;
                }
            }
            if (apagaLinha) {
                //caso a flag continuasse com o valor de true quer dizer que não "levantou"
                //o que quer dizer que encontrou uma linha que vai ser eliminada
                eliminaLinha(y);
            }
        }
    }

    /**
     *
     * @param linha: vai ser enviada para esta função a linha a ser eliminada
     */
    public void eliminaLinha(int linha) {
        //vai percorrer da linha que foi enviada, até ao inicio do tabuleiro no caso de haver mais linhas a ser eliminadas
        for (int y = linha; y > 0; y--) {
            //percorre as colunas de as suas respetivas linhas
            for (int x = 0; x < jogo[y].length; x++) {
                //quer dizer que a linha a ser eliminada vai passar a ter as peças da linha anterior
                jogo[y][x] = jogo[y - 1][x];
            }
        }
        
        //soma à pontuação 100 valores
        score = score + 100;
        //como eliminou uma linha vai contar mais uma linha
        linhas = linhas + 1;
        
        //o valor do score vai passar para String e passa para o valor da Label
        labelScore.setText(toString());
        
        //o valor da quantidade de linhas feitas pelo jogador vai passar para String e passa para o valor da Label
        labelLinhas.setText(linhasString());
        //vai pintar de novo, para vermos a linha eliminada
        repaint();

    }

    /**
     *
     * @return o valor do score (int) na forma de String
     */
    @Override
    public String toString() {
        return " " + score;
    }

    /**
     *
     * @param labelScore: vai conter o valor do score na forma de texto numa Label
     */
    public void setLabelScore(JLabel labelScore) {
        this.labelScore = labelScore;
    }

    /**
     *
     * @return passa o valor da quantidade de linhas (int) para a string
     */
    public String linhasString() {
        return " " + linhas;
    }

    /**
     *
     * @param labelLinhas: vai conter o valor da quantidade de linhas na forma de texto numa Label
     */
    public void setLabelLinhas(JLabel labelLinhas) {
        this.labelLinhas = labelLinhas;
    }
    
    /**
     * Esta função vai verificar se o jogador perdeu
     * @return vai retornar true se o jogador perdeu
     */
    public boolean youLose(){
        //vai verificar na primeira linha, em todas as colunas se há algum bloco vazio
        for (int i = 0; i < jogo[1].length; i++) {
            if(!(jogo[0][i] instanceof BlocoVazio)){
                return true;
            }
        }
        return false;
    }
}
