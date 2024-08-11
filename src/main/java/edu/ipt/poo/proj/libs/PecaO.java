/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.libs;

import java.awt.Color;

/**
 *
 * @author bento
 */
public class PecaO extends TetrisPeca{
    
    /**
     *  cria-se um array de Peca querendo dizer que vão ter uma cor e que vão ser pintadas no tabuleiro
     */
    static Peca[][] T = {
        {new Peca(Color.YELLOW), new Peca(Color.YELLOW)},
        {new Peca(Color.YELLOW), new Peca(Color.YELLOW)}    
    };
    
    /**
     *  envia-se o array para o construtor da classe TetrisPeca que transforma o array na var matriz dessa mesma classe
     */
    public PecaO(){
        super(T);
    }
    
}
