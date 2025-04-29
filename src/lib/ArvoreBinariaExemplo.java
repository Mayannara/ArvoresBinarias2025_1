/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author victoriocarvalho
 */
public class ArvoreBinariaExemplo<T> implements IArvoreBinaria<T> {
    
    protected NoExemplo<T> raiz = null;
    protected Comparator<T> comparador; 
  
    public ArvoreBinariaExemplo(Comparator<T> comp) {
        comparador = comp;
    }
    
    @Override
    public void adicionar(T novoValor) {
        if(raiz == null) {
            this.raiz = new NoExemplo<>(novoValor);
        }else{
            adicionarRecursivo(this.raiz, new NoExemplo<>(novoValor));
        }
    }

    private void adicionarRecursivo(NoExemplo<T> atual, NoExemplo<T> novoValor){
        // Verificando se o comparador foi inicializado corretamente
        if (this.comparador == null) {
            throw new IllegalStateException("Comparador não inicializado");
        }
        // Assumindo que o valor está dentro do nó
        T valorAtual = atual.getValor();
        T novoValorNode = novoValor.getValor();

        if (this.comparador.compare(novoValorNode, valorAtual) < 0) {
            if (atual.getFilhoEsquerda() == null) {
                atual.setFilhoEsquerda(novoValor);
            } else {
                adicionarRecursivo(atual.getFilhoEsquerda(), novoValor);
            }
        } else if (this.comparador.compare(novoValorNode, valorAtual) > 0) {
            if (atual.getFilhoDireita() == null) {
                atual.setFilhoDireita(novoValor);
            } else {
                adicionarRecursivo(atual.getFilhoDireita(), novoValor);
            }
        }
        // perguntar ao professor sobre duplicidade
    }

    @Override
    public T pesquisar(T valor) {
        return pesuisarReq(this.raiz, valor);
    }

    // perguntar ao professor o que ele espera que retorne

    private T pesuisarReq(NoExemplo<T> atual, T valor){
        if(atual == null){
            return null;
        }else if(this.comparador.compare(valor, atual.getValor()) == 0){
            return valor;
        }else if(this.comparador.compare(valor, atual.getValor()) < 0){
           return pesuisarReq(atual.getFilhoEsquerda(), valor);
        }else{
           return pesuisarReq(atual.getFilhoDireita(), valor);
        }
    }



    @Override
    public T pesquisar(T valor, Comparator comparador) {
        // Se o comparador for do mesmo tipo do que foi usado para indexar a árvore
        // podemos usar a busca binária normal
        if (comparador.getClass().equals(this.comparador.getClass())) {
            return pesquisarReqComp(this.raiz, valor, comparador);
        } else {
            // Caso contrário, precisamos fazer uma busca completa na árvore
            return pesquisarCompleto(this.raiz, valor, comparador);
        }
    }

    private T pesquisarReqComp(NoExemplo<T> atual, T valor, Comparator comparador) {
        if (atual == null) {
            return null;
        } else if (comparador.compare(valor, atual.getValor()) == 0) {
            return atual.getValor();
        } else if (comparador.compare(valor, atual.getValor()) < 0) {
            return pesquisarReqComp(atual.getFilhoEsquerda(), valor, comparador);
        } else {
            return pesquisarReqComp(atual.getFilhoDireita(), valor, comparador);
        }
    }

    // Método para busca completa (percorrendo toda a árvore) quando o comparador é de tipo diferente
    private T pesquisarCompleto(NoExemplo<T> atual, T valor, Comparator comparador) {
        if (atual == null) {
            return null;
        }

        // Verifica o nó atual
        if (comparador.compare(valor, atual.getValor()) == 0) {
            return atual.getValor();
        }

        // Procura na subárvore esquerda
        T resultadoEsquerda = pesquisarCompleto(atual.getFilhoEsquerda(), valor, comparador);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        // Procura na subárvore direita
        return pesquisarCompleto(atual.getFilhoDireita(), valor, comparador);
    }

    @Override
    public T remover(T valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int altura() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       
    
    @Override
    public int quantidadeNos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String caminharEmNivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
    
    @Override
    public String caminharEmOrdem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
        
}
