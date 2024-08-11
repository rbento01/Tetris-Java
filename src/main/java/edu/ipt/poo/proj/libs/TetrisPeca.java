/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.libs;

import edu.ipt.poo.proj.blocks.BlocoVazio;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Classe onde se controla os movimentos da peça
 * @author bento
 */
public class TetrisPeca extends JComponent {

    //posição dentro do jogo
    //coordenada y
    int posY = 0; 
    //coordenada x
    int posX = 5; 
    
    
    //conjunto dos blocos da peça
    Peca[][] matriz;

    /**
     *
     * @param matriz recebe o conjunto de blocos
     */
    public TetrisPeca(Peca[][] matriz) {
        //construtor do conjunto dos blocos da peça
        this.matriz = matriz;
    }
    
      
    /**
     *  a aumentar o valor de y quer dizer que vai tar a aumentar de linha no tabuleiro querendo dizer que que a peça vai para baixo
     */
    public void ParaBaixo() {
        posY++;
    }

    /**
     *  a aumentar o valor do x quer dizer que vai aumentar a coluna do tabuleiro querendo dizer que que a peça vai para a direita
     */
    public void ParaDireita() {
        posX++;
    }

    /**
     *  a subtrair o valor do x quer dizer que vai diminuir a coluna do tabuleiro querendo dizer que a peça vai para a esquerda
     */
    public void ParaEsquerda() {
        posX--;
    }
    
    
    /**
     * esta função faz com que o conjunto das peças pertença ao tabuleiro
     * @param jogo manda o tabuleiro
     */
    public void freezePeca(Peca[][] jogo) {
        //percorre todo os blocos da peça
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                //percorre o conjunto dos blocos da peça, até encontrar um bloco vazio
                //se a expressão for verdadeira quer dizer que a peça passa para o tabuleiro, fazendo parte deste
                if( !(matriz[y][x] instanceof BlocoVazio)){
                    jogo[posY + y][posX + x] = matriz[y][x];
                }
            }
        }
    }
    
    /**
     *  esta função é que faz com que seja possivel rodar a peça
     */
    private void transpose() {
        //cria uns blocos da peça com as coord invertidas
        Peca[][] tmp = new Peca[matriz[0].length][matriz.length];
        //percorre todo os blocos da peça
        for(int y=0; y<matriz.length; y++){
           for(int x=0; x<matriz[0].length; x++){
               //passa os conjuntos dos blocos da peça invertidos para a peça normal
               tmp[x][y] = matriz[y][x];
           }
        }
        //faz com que a peça não fique só invertida
        matriz = tmp;
        //repinta o tabuleiro com as mudanças feitas
        repaint();
    }
    
    /**
     *  esta função vai fazer com que a peça rode espelho horizontal
     * (o que muda nesta função é que na atribuição da peça invertida passa para a linha - 1)
     */
    private void mirrorHorizontal() {
        //cria uns blocos da peça com as coord invertidas
        Peca[][] tmp = new Peca[matriz.length][matriz[0].length];
        //percorre todo os blocos da peça
        for(int y=0; y<matriz.length; y++)
           for(int x=0; x<matriz[0].length; x++)
               //passa os conjuntos dos blocos da peça invertidos para a peça normal
               tmp[y][x] = matriz[matriz.length-y-1][x];
        //faz com que peça fique invertida não só pela esquerda
        matriz = tmp;   
    }
    
    /**
     *  esta função vai fazer com que a peça rode num espelho vertical
     * (o que muda nesta função é que na atribuição da peça invertida passa para a coluna - 1)
     */
    private void mirrorVertical() {
        //cria uns blocos da peça com as coord invertidas
        Peca[][] tmp = new Peca[matriz.length][matriz[0].length];
        //percorre todo os blocos da peça
        for(int y=0; y<matriz.length; y++)
           for(int x=0; x<matriz[0].length; x++)
               //passa os conjuntos dos blocos da peça invertidos para a peça normal
               tmp[y][x] = matriz[y][matriz[0].length-x-1];
        //faz com que peça fique invertida não só pela direita
        matriz = tmp;
        
    }
    
    /**
     *  roda as peças para no sentido horário
     */
    public void rotateRight() {
        //permite rodar a peça
        transpose();
        //roda na vertical
        mirrorVertical();
    }
    
    /**
     *  roda as peças para no sentido anti-horário
     */
    public void rotateLeft() {
        //permite rodar a peça
        transpose();
        //roda na vertical
        mirrorHorizontal();
    }
    
    /**
     * Verifica se a peça se pode mover para baixo
     *
     * @param jogo tabuleiro do jogo
     * @return retorna true se se puder continuar a mover para baixo
     */
    public boolean VerificaMove(Peca[][] jogo) {
        //verificação para ver se a peça está para ultrapassar o tabuleiro
        if (posY + matriz.length >= jogo.length) {
            return false;
        }
        //verificar se existem posições livres para cair
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                //os blocos de baixo estão ocupados
                if (!(jogo[posY + y + 1][posX + x] instanceof BlocoVazio) 
                        //verifica se é uma peça
                        && !(matriz[y][x] instanceof BlocoVazio)) { 
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *  Verifica se a peça se pode mover para a direta
     * @param jogo  tabuleiro do jogo
     * @return true se se puder movimentar para a direita
     */
    public boolean VerificaDireita(Peca[][] jogo) {
        //chegou ao limite direito da matriz
        if (posX + matriz[0].length >= jogo[0].length) {
            return false;
        }
        //verifica se existe posições para a peça se mover
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                //os blocos a baixo estão ocupados
                if (!(jogo[posY + y][posX + x + 1] instanceof BlocoVazio)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     *  Verifica se a peça se pode mover para a esquerda
     * @param jogo tabuleiro do jogo
     * @return true se se puder movimentar para a esquerda
     */
    public boolean VerificaEsquerda(Peca[][] jogo) {
        //chegou ao limite esquerdo do tabuleiro
        if (posX <= 0) {
            return false;
        }
        //verifica se existe posições para a peça se mover
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                //os blocos a baixo estão ocupados
                if (!(jogo[posY + y][posX + x - 1] instanceof BlocoVazio)) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     *  Verifica se é possivel rodar 
     * @param jogo é enviado o tabuleiro do jogo
     * @return retorna false se é possivel rodar numa determinada posição
     */
    public boolean VerificaRotate(Peca[][] jogo){
        if(posX + matriz.length > jogo[0].length){
            return false;
        }else{
            return true;
        }
    }
    
    /**
     * desenha as peças no tabuleiro
     * @param gr: contexto gráfico
     * @param px: coordenada X do canto superior esquerdo
     * @param py: coordenada Y do canto superior esquerdo
     * @param dx: largura dos blocos
     * @param dy: altura dos blocos
     * 
     */
    public void draw(Graphics gr, int px, int py, int dx, int dy) {
        //vai percorrer o conjunto dos blocos das peças
        for (int y = 0; y < matriz.length; y++) {
            for (int x = 0; x < matriz[y].length; x++) {
                //desenhar as peças no tabuleiro
                matriz[y][x].draw(gr, px + x * dx, py + y * dy, dx, dy);
            }
        }
    }
}
