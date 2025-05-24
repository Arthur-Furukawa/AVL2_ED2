import java.io.IOException;
import java.util.List;

public class ProjetoApl2 {

    public static void main(String[] args) {
        // Caminho para o arquivo CSV - **IMPORTANTE: Ajuste este caminho!**
        // O arquivo CSV fornecido pelo usuário está em /home/ubuntu/upload/
        String csvFilePath = "/home/ubuntu/upload/Quantidade de alunos estrangeiros por nacionalidade_2° Semestre 2023.csv";

        // Cria as árvores BST e AVL
        BST<NacionalidadeInfo> bst = new BST<>();
        AVL<NacionalidadeInfo> avl = new AVL<>();

        System.out.println("Iniciando carregamento dos dados do CSV: " + csvFilePath);

        // Medir desempenho do carregamento (inserção)
        Benchmark.Result bstLoadResult = null;
        Benchmark.Result avlLoadResult = null;

        try {
            // --- Medição para BST ---
            long startTimeBst = System.nanoTime();
            bst.resetComparisonCount();
            DataLoader.popularArvoresComCSV(csvFilePath, bst, new AVL<>()); // Passa dummy AVL para popular só a BST
            long endTimeBst = System.nanoTime();
            bstLoadResult = new Benchmark.Result((endTimeBst - startTimeBst) / 1_000_000.0, bst.getComparisonCount());
            System.out.println("BST populada com sucesso.");

            // --- Medição para AVL ---
            long startTimeAvl = System.nanoTime();
            avl.resetComparisonCount();
            DataLoader.popularArvoresComCSV(csvFilePath, new BST<>(), avl); // Passa dummy BST para popular só a AVL
            long endTimeAvl = System.nanoTime();
            avlLoadResult = new Benchmark.Result((endTimeAvl - startTimeAvl) / 1_000_000.0, avl.getComparisonCount());
            System.out.println("AVL populada com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao ler ou processar o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
            return; // Termina a execução em caso de erro
        }

        System.out.println("\n--- Resultados do Carregamento (Inserção) ---");
        if (bstLoadResult != null) {
            System.out.println("BST -> " + bstLoadResult);
            System.out.println("BST -> Altura: " + bst.getHeight() + ", Tamanho (Nacionalidades Únicas): " + bst.size());
        }
        if (avlLoadResult != null) {
            System.out.println("AVL -> " + avlLoadResult);
            System.out.println("AVL -> Altura: " + avl.getHeight() + ", Tamanho (Nacionalidades Únicas): " + avl.size());
        }

        System.out.println("\n--- Análise Estatística (usando BST como exemplo) ---");
        
        // 1. Contar nacionalidades únicas
        int numNacionalidades = AnaliseDados.contarNacionalidadesUnicas(bst);
        System.out.println("1. Número total de nacionalidades únicas: " + numNacionalidades);

        // 2. Encontrar nacionalidade com mais alunos
        NacionalidadeInfo maisAlunos = AnaliseDados.encontrarNacionalidadeComMaisAlunos(bst);
        if (maisAlunos != null) {
            System.out.println("2. Nacionalidade com maior número de alunos: " + maisAlunos);
        }

        // 3. Listar algumas nacionalidades (ex: as 10 primeiras em ordem alfabética)
        System.out.println("3. Lista das 10 primeiras nacionalidades (ordem alfabética):");
        List<String> todasNacionalidades = AnaliseDados.listarNacionalidadesOrdenadas(bst);
        todasNacionalidades.stream().limit(10).forEach(System.out::println);

        // --- Exemplo de Busca ---
        System.out.println("\n--- Exemplo de Busca (por 'Venezuela') ---");
        NacionalidadeInfo chaveBusca = new NacionalidadeInfo("Venezuela", 0); // Total de alunos não importa para busca
        
        bst.resetComparisonCount();
        long startTimeBuscaBst = System.nanoTime();
        BinaryNode<NacionalidadeInfo> encontradoBst = bst.search(chaveBusca);
        long endTimeBuscaBst = System.nanoTime();
        Benchmark.Result bstSearchResult = new Benchmark.Result((endTimeBuscaBst - startTimeBuscaBst) / 1_000_000.0, bst.getComparisonCount());

        avl.resetComparisonCount();
        long startTimeBuscaAvl = System.nanoTime();
        BinaryNode<NacionalidadeInfo> encontradoAvl = avl.search(chaveBusca);
        long endTimeBuscaAvl = System.nanoTime();
        Benchmark.Result avlSearchResult = new Benchmark.Result((endTimeBuscaAvl - startTimeBuscaAvl) / 1_000_000.0, avl.getComparisonCount());

        System.out.println("Busca BST -> " + (encontradoBst != null ? encontradoBst.getData() : "Não encontrado") + " -> " + bstSearchResult);
        System.out.println("Busca AVL -> " + (encontradoAvl != null ? encontradoAvl.getData() : "Não encontrado") + " -> " + avlSearchResult);
        
        // --- Exemplo de Remoção ---
        // Para remoção, precisaríamos de uma lista de chaves a remover, similar à busca.
        // System.out.println("\n--- Exemplo de Remoção (Implementação Pendente no Benchmark) ---");

        System.out.println("\nExecução concluída.");
    }
}

