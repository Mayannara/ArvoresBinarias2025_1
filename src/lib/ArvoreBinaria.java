/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.*;


public class ArvoreBinaria<T> implements IArvoreBinaria<T> {
    
    protected No<T> raiz = null;
    protected Comparator<T> comparador;


    public ArvoreBinaria(Comparator<T> comp) {
        comparador = comp;
    }

    @Override
    public void adicionar(T novoValor) {
        if (raiz == null) {
            this.raiz = new No<>(novoValor);
            return;
        }

        if (this.comparador == null) {
            throw new IllegalStateException("Comparador não inicializado");
        }

        No<T> atual = this.raiz;
        No<T> novoNo = new No<>(novoValor);

        while (true) {
            T valorAtual = atual.getValor();
            int comparacao = this.comparador.compare(novoValor, valorAtual);

            if (comparacao < 0) {
                if (atual.getFilhoEsquerda() == null) {
                    atual.setFilhoEsquerda(novoNo);
                    break;
                }
                atual = atual.getFilhoEsquerda();
            } else if (comparacao > 0) {
                if (atual.getFilhoDireita() == null) {
                    atual.setFilhoDireita(novoNo);
                    break;
                }
                atual = atual.getFilhoDireita();
            } else {
                // Valor já existe, não adiciona
                break;
            }
        }
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisarReq(this.raiz, valor);
    }

    private T pesquisarReq(No<T> atual, T valor){
        if(atual == null){
            return null;
        }else if(this.comparador.compare(valor, atual.getValor()) == 0){
            return atual.getValor();
        }else if(this.comparador.compare(valor, atual.getValor()) < 0){
           return pesquisarReq(atual.getFilhoEsquerda(), valor);
        }else{
           return pesquisarReq(atual.getFilhoDireita(), valor);
        }
    }

    @Override
    public T pesquisar(T valor, Comparator<T> comparador) {
        // Se o comparador for do mesmo tipo do que foi usado para indexar a árvore, podemos usar a função de busca anterior (pesquisarReq)
        if (comparador.getClass().equals(this.comparador.getClass())) {
            return pesquisarReq(this.raiz, valor);
        } else {
            // Caso contrário, precisamos fazer uma busca completa na árvore
            return pesquisarCompleto(this.raiz, valor, comparador);
        }
    }

    // Método para busca completa (percorrendo toda a árvore) quando o comparador é de tipo diferente
    private T pesquisarCompleto(No<T> atual, T valor, Comparator<T> comparador) {
        if (atual == null) {
            return null;
        }

        // Verifica o nó atual
        if (comparador.compare(valor, atual.getValor()) == 0) {
            return atual.getValor();
        }

        // Procura na filha esquerda
        T resultadoEsquerda = pesquisarCompleto(atual.getFilhoEsquerda(), valor, comparador);
        if (resultadoEsquerda != null) {
            return resultadoEsquerda;
        }

        // Procura na filha direita
        return pesquisarCompleto(atual.getFilhoDireita(), valor, comparador);
    }

    @Override
    public No<T> remover(T valor) {
        No<T> noRemovido = new No<>(null); // Auxiliar para guardar o valor removido
        raiz = removerRecursivo(raiz, valor, noRemovido);
        return noRemovido;
    }

    private No<T> removerRecursivo(No<T> atual, T valor, No<T> noRemovido) {
        if (atual == null) {
            return null;
        }

        int comparacao = comparador.compare(valor, atual.getValor());

        if (comparacao < 0) {
            atual.setFilhoEsquerda(removerRecursivo(atual.getFilhoEsquerda(), valor, noRemovido));
        } else if (comparacao > 0) {
            atual.setFilhoDireita(removerRecursivo(atual.getFilhoDireita(), valor, noRemovido));
        } else {
            // Nó encontrado - vamos removê-lo
            noRemovido.setValor(atual.getValor());

            // Caso 1: Nó é folha (sem filhos)
            if (atual.getFilhoEsquerda() == null && atual.getFilhoDireita() == null) {
                return null;
            }

            // Caso 2: Nó tem apenas um filho
            if (atual.getFilhoEsquerda() == null) {
                return atual.getFilhoDireita();
            }
            else if (atual.getFilhoDireita() == null) {
                return atual.getFilhoEsquerda();
            }
            else{
                // Caso 3: nó com 2 filhos
                //Encontra o valor minimo
                No<T> sucessor = encontrarMinimo(atual.getFilhoDireita());
                //Acha o valor que deve ser posto no lugar do que vai sar
                atual.setValor(sucessor.getValor());
                //Remove o valor
                atual.getFilhoDireita().setValor(removerMinimo(atual.getFilhoDireita()).getValor());
            }

        }

        return atual;
    }

    private No<T> encontrarMinimo(No<T> no) {
        while (no.getFilhoEsquerda() != null) {
            no = no.getFilhoEsquerda();
        }
        return no;
    }

    private No<T> removerMinimo(No<T> no) {
        if (no.getFilhoEsquerda() == null) {
            return no.getFilhoDireita();
        }
        no.setFilhoEsquerda(removerMinimo(no.getFilhoEsquerda()));
        return no;
    }


    private int alturaRecursiva(No<T> noAtual) {
       if (noAtual == null) {
          return -1; // altura de árvore vazia//
       }

       int alturaEsquerda = alturaRecursiva(noAtual.getFilhoEsquerda());
       int alturaDireita = alturaRecursiva(noAtual.getFilhoDireita());

       return 1 + Math.max(alturaEsquerda, alturaDireita);
    }

    @Override
    public int altura() {
        if (raiz == null) return -1;

        Queue<No<T>> fila = new LinkedList<>();
        fila.add(raiz);
        int altura = -1;

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();
            for (int i = 0; i < tamanhoNivel; i++) {
                No<T> atual = fila.poll();
                assert atual != null;
                if (atual.getFilhoEsquerda() != null) {
                    fila.add(atual.getFilhoEsquerda());
                }
                if (atual.getFilhoDireita() != null) {
                    fila.add(atual.getFilhoDireita());
                }
            }
            altura++; // cada nível completo aumenta a altura
        }

        return altura;
    }

    @Override
    public int quantidadeNos() {
        return tamanhoRecursivo(this.raiz);
    }

    private int tamanhoRecursivo(No<T> noAtual) {
        if (noAtual == null) {
            return 0;
        } else {
            int tamanhoEsquerda = tamanhoRecursivo(noAtual.getFilhoEsquerda());
            int tamanhoDireita = tamanhoRecursivo(noAtual.getFilhoDireita());
            // Soma 1, devido ao comportamento representando a raiz
            return 1 + tamanhoEsquerda + tamanhoDireita;
        }
    }

    @Override
    public String caminharEmNivel() {
        ArrayList<No<T>> fila = new ArrayList<>();
        String filafinal = "[";
        fila.add(raiz);

        while (!fila.isEmpty()) {
            No<T> atual = fila.removeFirst();
            filafinal = filafinal + "\n" + atual.getValor().toString();
            if (atual.getFilhoEsquerda() != null) {fila.add(atual.getFilhoEsquerda());}
            if (atual.getFilhoDireita() != null) {fila.add(atual.getFilhoDireita());}
        }
        filafinal = filafinal + " ";
        return filafinal;
    }

    @Override
    public String caminharEmOrdem() {
        StringBuilder filafinal = new StringBuilder("[");

        if (raiz !=null) {
            caminharEmOrdem(raiz, filafinal);
        }

        return filafinal.append("]").toString();
    }

    private void caminharEmOrdem(No<T> no, StringBuilder filafinal) {
        if (no != null) {
            // Primeiro vai para a esquerda
            caminharEmOrdem(no.getFilhoEsquerda(), filafinal);

            // Nó atual
            if (filafinal.length() > 1) {
                filafinal.append(",\n");
            }

            filafinal.append(no.getValor().toString());

            // Vai para a direita
            caminharEmOrdem(no.getFilhoDireita(), filafinal);
        }
    }
}
