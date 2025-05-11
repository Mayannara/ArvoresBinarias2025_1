package app;
import lib.ArvoreBinaria;

public class AppArvore {
    public static void main(String[] args) {
        // Criando a árvore com comparador por matrícula
        ArvoreBinaria<Aluno> arvore = new ArvoreBinaria<Aluno>(new ComparadorAlunoPorMatricula());

        // Adicionando alunos para criar uma árvore bem estruturada
        // O nó raiz terá matrícula 100
        arvore.adicionar(new Aluno(100, "Carlos"));

        // Subárvore esquerda (matrículas < 100)
        arvore.adicionar(new Aluno(50, "Ana"));
        arvore.adicionar(new Aluno(25, "Pedro"));
        arvore.adicionar(new Aluno(75, "Maria"));
        arvore.adicionar(new Aluno(15, "João"));
        arvore.adicionar(new Aluno(35, "Beatriz"));
        arvore.adicionar(new Aluno(60, "Lucas"));
        arvore.adicionar(new Aluno(85, "Julia"));

        // Subárvore direita (matrículas > 100)
        arvore.adicionar(new Aluno(150, "Mariana"));
        arvore.adicionar(new Aluno(125, "Miguel"));
        arvore.adicionar(new Aluno(175, "Gabriela"));
        arvore.adicionar(new Aluno(110, "Thiago"));
        arvore.adicionar(new Aluno(130, "Laura"));
        arvore.adicionar(new Aluno(160, "Bruno"));
        arvore.adicionar(new Aluno(190, "Camila"));

        System.out.println("========== INFORMAÇÕES DA ÁRVORE ==========");
        System.out.println("Altura da árvore: " + arvore.altura());
        System.out.println("Quantidade de nós: " + arvore.quantidadeNos());

        System.out.println("\n========== PESQUISAS ==========");
        System.out.println("Pesquisando matrícula 100: " + arvore.pesquisar(new Aluno(100, "")));
        System.out.println("Pesquisando matrícula 75: " + arvore.pesquisar(new Aluno(75, "")));
        System.out.println("Pesquisando matrícula 190: " + arvore.pesquisar(new Aluno(190, "")));
        System.out.println("Pesquisando matrícula 200 (não existe): " + arvore.pesquisar(new Aluno(200, "")));

        System.out.println("\n========== CAMINHAMENTOS ==========");
        System.out.println("CAMINHAMENTO EM ORDEM (CRESCENTE POR MATRÍCULA):");
        System.out.println(arvore.caminharEmOrdem());

        System.out.println("\nCAMINHAMENTO EM NÍVEL (POR LARGURA):");
        System.out.println(arvore.caminharEmNivel());

        System.out.println("\n========== REMOÇÕES ==========");
        // Testando remoção de folha
        System.out.println("Removendo aluno com matrícula 15 (folha): " + arvore.remover(new Aluno(15, "")));
        System.out.println("Quantidade de nós após remoção: " + arvore.quantidadeNos());

        // Testando remoção de nó com um filho
        System.out.println("Removendo aluno com matrícula 25 (tem filho à direita): " + arvore.remover(new Aluno(25, "")));
        System.out.println("Quantidade de nós após remoção: " + arvore.quantidadeNos());

        // Testando remoção de nó com dois filhos
        System.out.println("Removendo aluno com matrícula 150 (tem dois filhos): " + arvore.remover(new Aluno(150, "")));
        System.out.println("Quantidade de nós após remoção: " + arvore.quantidadeNos());

        System.out.println("\nCAMINHAMENTO EM ORDEM APÓS REMOÇÕES:");
        System.out.println(arvore.caminharEmOrdem());
    }
}