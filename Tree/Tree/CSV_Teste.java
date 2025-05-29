package Tree;

import java.io.IOException;
import java.util.List;

// Só serve pra testar o arquivo CSV
public class CSV_Teste {
    public static void main(String[] args) {
        String csvFilePath = "Quantidade de alunos estrangeiros por nacionalidade_2° Semestre 2023.csv";
        
        System.out.println("Iniciando demonstração com o arquivo: " + csvFilePath);
        
        try {
            // Criar as árvores
            BSTTree<NacionalidadeInfo> bst = new BSTTree<>();
            AVLTree<NacionalidadeInfo> avl = new AVLTree<>();
            
            // Carregar dados do CSV para as árvores
            System.out.println("\n--- Carregando dados do CSV ---");
            CSVLoader.carregarParaBST(csvFilePath, bst);
            CSVLoader.carregarParaAVL(csvFilePath, avl);
            
            // Análise estatística básica
            System.out.println("\n--- Análise Estatística ---");
            
            // Total de nacionalidades
            List<NacionalidadeInfo> nacionalidadesBST = CSVLoader.percorrerBST(bst);
            System.out.println("Total de nacionalidades únicas: " + nacionalidadesBST.size());
            
            // Total de alunos estrangeiros
            int totalAlunosBST = CSVLoader.calcularTotalAlunosBST(bst);
            System.out.println("Total de alunos estrangeiros: " + totalAlunosBST);
            
            // Nacionalidade com mais alunos
            NacionalidadeInfo maisAlunosBST = CSVLoader.encontrarNacionalidadeComMaisAlunosBST(bst);
            System.out.println("Nacionalidade com mais alunos: " + maisAlunosBST);
            
            // Listar as 10 primeiras nacionalidades em ordem alfabética
            System.out.println("\n--- 10 primeiras nacionalidades (ordem alfabética) ---");
            List<NacionalidadeInfo> listaOrdenada = CSVLoader.percorrerBST(bst);
            for (int i = 0; i < Math.min(10, listaOrdenada.size()); i++) {
                System.out.println(listaOrdenada.get(i));
            }
            
            // Demonstração de busca
            System.out.println("\n--- Demonstração de Busca ---");
            if (!nacionalidadesBST.isEmpty()) {
                // Buscar uma nacionalidade existente (a primeira da lista)
                NacionalidadeInfo buscar = new NacionalidadeInfo(nacionalidadesBST.get(0).getNomePais(), 0);
                boolean encontradoBST = bst.searchInBST(bst.getRoot(), buscar);
                boolean encontradoAVL = avl.searchInAVL(avl.getRoot(), buscar);
                System.out.println("Busca por '" + buscar.getNomePais() + "': BST=" + encontradoBST + ", AVL=" + encontradoAVL);
                
                // Buscar uma nacionalidade inexistente
                NacionalidadeInfo buscarInexistente = new NacionalidadeInfo("País Inexistente", 0);
                boolean encontradoInexistenteBST = bst.searchInBST(bst.getRoot(), buscarInexistente);
                boolean encontradoInexistenteAVL = avl.searchInAVL(avl.getRoot(), buscarInexistente);
                System.out.println("Busca por '" + buscarInexistente.getNomePais() + "': BST=" + encontradoInexistenteBST + ", AVL=" + encontradoInexistenteAVL);
            }
            
            // Demonstração de benchmark
            System.out.println("\n--- Benchmark de Operações ---");
            
            // Converter para arrays para usar com os métodos de benchmark existentes
            NacionalidadeInfo[] arrayNacionalidades = CSVLoader.bstParaArray(bst);
            
            // Criar novas árvores para o benchmark
            BSTTree<NacionalidadeInfo> bstBenchmark = new BSTTree<>();
            AVLTree<NacionalidadeInfo> avlBenchmark = new AVLTree<>();
            
            // Medir tempo de inserção
            double tempoBST = BenchmarkTree.measureInsert(bstBenchmark, arrayNacionalidades);
            double tempoAVL = BenchmarkTree.measureInsert(avlBenchmark, arrayNacionalidades);
            
            System.out.println("Tempo de inserção (BST): " + tempoBST + " ms");
            System.out.println("Tempo de inserção (AVL): " + tempoAVL + " ms");
            
            // Medir tempo de busca (usando os primeiros 5 elementos como amostra)
            NacionalidadeInfo[] amostraBusca = new NacionalidadeInfo[Math.min(5, arrayNacionalidades.length)];
            System.arraycopy(arrayNacionalidades, 0, amostraBusca, 0, amostraBusca.length);
            
            double tempoBuscaBST = BenchmarkTree.measureSearch(bstBenchmark, amostraBusca);
            double tempoBuscaAVL = BenchmarkTree.measureSearch(avlBenchmark, amostraBusca);
            
            System.out.println("Tempo de busca (BST): " + tempoBuscaBST + " ms");
            System.out.println("Tempo de busca (AVL): " + tempoBuscaAVL + " ms");
            
            // Visualização das árvores
            System.out.println("\n--- Visualização das Árvores ---");
            System.out.println("BST (primeiros elementos em largura):");
            bst.breadthFirstSearch();
            System.out.println("\nAVL (primeiros elementos em largura):");
            avl.breadthFirstSearch();
            
        } catch (IOException e) {
            System.err.println("Erro ao carregar ou processar o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nDemonstração concluída.");
    }
}
