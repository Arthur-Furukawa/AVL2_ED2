package Tree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DataLoader {

    // Índice da coluna DS_PAIS (Nacionalidade) no CSV
    private static final int INDEX_NACIONALIDADE = 7;
    // Índice da coluna Nº ALUNOS no CSV
    private static final int INDEX_NUM_ALUNOS = 8;
    // Delimitador do CSV
    private static final String CSV_DELIMITER = ";";

    // Lê o arquivo CSV, agrega os dados por nacionalidade e popula as árvores.
    public static void popularArvoresComCSV(String csvFilePath, BSTTree<NacionalidadeInfo> bst, AVLTree<NacionalidadeInfo> avl) throws IOException {
        // Usar um mapa para agregar os dados antes de inserir nas árvores
        Map<String, Integer> agregadorNacionalidades = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            // Remove BOM (Byte Order Mark) se presente na primeira linha
            br.mark(1);
            if (br.read() != 0xFEFF) {
                br.reset();
            }

            int linhaAtual = 0;
            while ((line = br.readLine()) != null) {
                linhaAtual++;
                // Pular a linha do cabeçalho
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // Dividir a linha pelo delimitador
                String[] values = line.split(CSV_DELIMITER, -1); // -1 para incluir campos vazios no final

                // Verificar se a linha tem o número esperado de colunas
                if (values.length > Math.max(INDEX_NACIONALIDADE, INDEX_NUM_ALUNOS)) {
                    try {
                        String nacionalidade = values[INDEX_NACIONALIDADE].trim();
                        String numAlunosStr = values[INDEX_NUM_ALUNOS].trim();

                        // Ignorar linhas com nacionalidade vazia ou número de alunos inválido/vazio
                        if (!nacionalidade.isEmpty() && !numAlunosStr.isEmpty()) {
                            int numAlunos = Integer.parseInt(numAlunosStr);
                            if (numAlunos >= 0) {
                                // Agrega no mapa (chave é o nome do país em minúsculas para consistência)
                                String chaveMapa = nacionalidade.toLowerCase();
                                agregadorNacionalidades.put(chaveMapa, 
                                    agregadorNacionalidades.getOrDefault(chaveMapa, 0) + numAlunos);
                            }
                        } else {
                            System.err.println("Linha " + linhaAtual + " ignorada: Nacionalidade ou Nº Alunos vazio.");
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter número de alunos na linha " + linhaAtual + ": " + line + " -> " + e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Erro de índice fora dos limites na linha " + linhaAtual + ": " + line + " -> " + e.getMessage());
                    }
                } else {
                    System.err.println("Linha " + linhaAtual + " ignorada por ter formato inesperado (colunas insuficientes): " + line);
                }
            }
        }

        // Inserir os dados agregados nas árvores
        System.out.println("Inserindo dados agregados nas árvores...");
        for (Map.Entry<String, Integer> entry : agregadorNacionalidades.entrySet()) {
            // Recria o nome original (primeira letra maiúscula) para exibição, se necessário
            // Ou usa a chave do mapa diretamente se case-insensitivity for mantida
            String nomePaisOriginal = entry.getKey(); // Usando a chave lowercase por enquanto
            // Poderia buscar o nome original no CSV se fosse necessário, mas para a árvore basta a chave única
            NacionalidadeInfo info = new NacionalidadeInfo(nomePaisOriginal, entry.getValue());
            bst.addNode(info);
            avl.addNode(info);
        }
        System.out.println("Inserção concluída. Total de nacionalidades únicas: " + agregadorNacionalidades.size());
    }
}

