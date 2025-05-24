import java.util.List;
import java.util.Comparator;

public class AnaliseDados {

    /**
     * Encontra a nacionalidade com o maior número de alunos na árvore.
     * @param arvore A árvore (BST ou AVL) contendo NacionalidadeInfo.
     * @return A NacionalidadeInfo com o maior número de alunos, ou null se a árvore estiver vazia.
     */
    public static NacionalidadeInfo encontrarNacionalidadeComMaisAlunos(BinaryTree<NacionalidadeInfo> arvore) {
        if (arvore == null || arvore.isEmpty()) {
            return null;
        }

        List<NacionalidadeInfo> todasNacionalidades = arvore.inOrderTraversal();
        
        // Usa Stream API para encontrar o máximo baseado no total de alunos
        return todasNacionalidades.stream()
                                  .max(Comparator.comparingInt(NacionalidadeInfo::getTotalAlunos))
                                  .orElse(null);
    }

    /**
     * Lista todas as nacionalidades e seus respectivos totais de alunos em ordem alfabética.
     * @param arvore A árvore (BST ou AVL) contendo NacionalidadeInfo.
     * @return Uma lista de Strings formatadas com "Nacionalidade: [Nome], Total de Alunos: [Total]".
     */
    public static List<String> listarNacionalidadesOrdenadas(BinaryTree<NacionalidadeInfo> arvore) {
        if (arvore == null || arvore.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        List<NacionalidadeInfo> todasNacionalidades = arvore.inOrderTraversal();
        
        // Mapeia cada NacionalidadeInfo para sua representação em String
        return todasNacionalidades.stream()
                                  .map(NacionalidadeInfo::toString)
                                  .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Conta o número total de nacionalidades distintas presentes na árvore.
     * @param arvore A árvore (BST ou AVL) contendo NacionalidadeInfo.
     * @return O número total de nacionalidades únicas.
     */
    public static int contarNacionalidadesUnicas(BinaryTree<NacionalidadeInfo> arvore) {
        if (arvore == null || arvore.isEmpty()) {
            return 0;
        }
        // O tamanho da árvore (número de nós) já representa as nacionalidades únicas
        return arvore.size(); 
    }

    // Outros métodos de análise podem ser adicionados aqui conforme necessário.
    // Por exemplo, buscar nacionalidades dentro de um intervalo de contagem de alunos,
    // ou analisar distribuição por região (se essa informação fosse agregada).

}

