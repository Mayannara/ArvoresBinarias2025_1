package lib;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparator) {
        super(comparator);
    }

    // Método para calcular a altura de um nó
    private int altura(No<T> no) {
        if (no == null) {
            return -1;
        }
        return Math.max(altura(no.getFilhoEsquerda()), altura(no.getFilhoDireita())) + 1;
    }

    // Método para calcular o fator de balanceamento
    private int fatorBalanceamento(No<T> no) {
        if (no == null) {
            return 0;
        }
        return altura(no.getFilhoEsquerda()) - altura(no.getFilhoDireita());
    }

    // Rotação simples à esquerda
    private No<T> rotacaoEsquerda(No<T> no) {
        No<T> filhoDireita = no.getFilhoDireita();
        no.setFilhoDireita(filhoDireita.getFilhoEsquerda());
        filhoDireita.setFilhoEsquerda(no);
        return filhoDireita;
    }

    // Rotação simples à direita
    private No<T> rotacaoDireita(No<T> no) {
        No<T> filhoEsquerda = no.getFilhoEsquerda();
        no.setFilhoEsquerda(filhoEsquerda.getFilhoDireita());
        filhoEsquerda.setFilhoDireita(no);
        return filhoEsquerda;
    }

    // Rotação dupla direita-esquerda
    private No<T> rotacaoDireitaEsquerda(No<T> no) {
        no.setFilhoDireita(rotacaoDireita(no.getFilhoDireita()));
        return rotacaoEsquerda(no);
    }

    // Rotação dupla esquerda-direita
    private No<T> rotacaoEsquerdaDireita(No<T> no) {
        no.setFilhoEsquerda(rotacaoEsquerda(no.getFilhoEsquerda()));
        return rotacaoDireita(no);
    }

    // Método para balancear um nó
    private No<T> balancear(No<T> no) {
        if (no == null) {
            return null;
        }

        int fatorBalanceamento = fatorBalanceamento(no);

        if (fatorBalanceamento > 1) {
            if (fatorBalanceamento(no.getFilhoEsquerda()) >= 0) {
                no = rotacaoDireita(no);
            } else {
                no = rotacaoEsquerdaDireita(no);
            }
        }
        else if (fatorBalanceamento < -1) {
            if (fatorBalanceamento(no.getFilhoDireita()) <= 0) {
                no = rotacaoEsquerda(no);
            } else {
                no = rotacaoDireitaEsquerda(no);
            }
        }

        return no;
    }

    // Sobrescreve o método adicionar para manter a árvore balanceada
    @Override
    public void adicionar(T valor) {
        raiz = adicionarRecursivo(raiz, valor);
    }

    private No<T> adicionarRecursivo(No<T> atual, T valor) {
        if (atual == null) {
            return new No<>(valor);
        }

        int comparacao = comparador.compare(valor, atual.getValor());

        if (comparacao < 0) {
            atual.setFilhoEsquerda(adicionarRecursivo(atual.getFilhoEsquerda(), valor));
        } else if (comparacao > 0) {
            atual.setFilhoDireita(adicionarRecursivo(atual.getFilhoDireita(), valor));
        } else {
            // Valor já existe, não adiciona
            return atual;
        }

        // Balanceia a árvore após a inserção
        return balancear(atual);
    }

    // Sobrescreve o método remover para manter a árvore balanceada
    @Override
    public No<T> remover(T valor) {
        No<T> noRemovido = new No<>(null);
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

            // Caso 3: nó com 2 filhos
            // Encontra o sucessor (menor valor na subárvore direita)
            No<T> sucessor = encontrarMinimo(atual.getFilhoDireita());
            atual.setValor(sucessor.getValor());

            // Remove o sucessor
            atual.setFilhoDireita(removerRecursivo(atual.getFilhoDireita(), sucessor.getValor(), new No<>(null)));
        }

        // Balanceia a árvore após a remoção
        return balancear(atual);
    }

    private No<T> encontrarMinimo(No<T> no) {
        if (no == null) {
            return null;
        }

        while (no.getFilhoEsquerda() != null) {
            no = no.getFilhoEsquerda();
        }

        return no;
    }
}