import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DataLoader {

    // Índice da coluna DS_PAIS (Nacionalidade) no CSV
    private static final int INDEX_NACIONALIDADE = 7;
    // Índice da coluna Nº ALUNOS no CSV
    private static final int INDEX_NUM_ALUNOS = 8;
    // Delimitador do CSV
    private static final String CSV_DELIMITER = ";";

    /**
     * Lê o arquivo CSV e popula as árvores BST e AVL com dados agregados por nacionalidade.
     * @param csvFilePath Caminho para o arquivo CSV.
     * @param bst Árvore BST a ser populada.
     * @param avl Árvore AVL a ser populada.
     * @throws IOException Se ocorrer um erro de leitura do arquivo.
     */
    public static void popularArvoresComCSV(String csvFilePath, BST<NacionalidadeInfo> bst, AVL<NacionalidadeInfo> avl) throws IOException {
        // Usar try-with-resources para garantir que o reader seja fechado
        // Especificar UTF-8 para lidar com possíveis caracteres especiais e BOM
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            // Remove BOM (Byte Order Mark) se presente na primeira linha
            br.mark(1);
            if (br.read() != 0xFEFF) {
                br.reset();
            }

            while ((line = br.readLine()) != null) {
                // Pular a linha do cabeçalho
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Dividir a linha pelo delimitador
                String[] values = line.split(CSV_DELIMITER);

                // Verificar se a linha tem o número esperado de colunas
                if (values.length > Math.max(INDEX_NACIONALIDADE, INDEX_NUM_ALUNOS)) {
                    try {
                        String nacionalidade = values[INDEX_NACIONALIDADE].trim();
                        // Remover espaços extras e tentar converter número de alunos
                        String numAlunosStr = values[INDEX_NUM_ALUNOS].trim();
                        int numAlunos = Integer.parseInt(numAlunosStr);

                        // Ignorar entradas com nacionalidade vazia ou número de alunos inválido
                        if (!nacionalidade.isEmpty() && numAlunos >= 0) {
                            NacionalidadeInfo info = new NacionalidadeInfo(nacionalidade, numAlunos);
                            // A lógica de agregação está no método insert da BST/AVL
                            bst.insert(info);
                            avl.insert(info);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter número de alunos na linha: " + line + " -> " + e.getMessage());
                        // Continuar para a próxima linha
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Erro de índice fora dos limites na linha: " + line + " -> " + e.getMessage());
                        // Continuar para a próxima linha
                    }
                } else {
                    System.err.println("Linha ignorada por ter formato inesperado: " + line);
                }
            }
        }
    }

    // Método main para teste rápido (opcional)
    /*
    public static void main(String[] args) {
        BST<NacionalidadeInfo> bst = new BST<>();
        AVL<NacionalidadeInfo> avl = new AVL<>();
        String csvPath = "/path/to/your/Quantidade de alunos estrangeiros por nacionalidade_2° Semestre 2023.csv"; // Substitua pelo caminho real

        try {
            popularArvoresComCSV(csvPath, bst, avl);
            System.out.println("Árvores populadas com sucesso!");
            System.out.println("BST - Altura: " + bst.getHeight() + ", Tamanho: " + bst.size());
            System.out.println("AVL - Altura: " + avl.getHeight() + ", Tamanho: " + avl.size());

            // Exemplo de busca
            NacionalidadeInfo busca = bst.search(new NacionalidadeInfo("Venezuela", 0));
            if (busca != null) {
                System.out.println("Encontrado na BST: " + busca);
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
    */
}

