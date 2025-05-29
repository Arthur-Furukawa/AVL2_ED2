package Tree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe utilizada para carregar dados do CSV e popular as árvores.
public class CSVLoader {

    // Índices das colunas relevantes no CSV
    private static final int INDEX_NACIONALIDADE = 7; // Coluna DS_PAIS
    private static final int INDEX_NUM_ALUNOS = 8;    // Coluna Nº ALUNOS
    private static final String CSV_DELIMITER = ";";  // Delimitador do CSV

    // Carrega dados do CSV e popula uma árvore BST.
    public static void carregarParaBST(String csvFilePath, BSTTree<NacionalidadeInfo> bst) throws IOException {
        List<NacionalidadeInfo> dados = carregarDadosDoCSV(csvFilePath);
        for (NacionalidadeInfo info : dados) {
            bst.addNode(info);
        }
        System.out.println("BST populada com " + dados.size() + " nacionalidades.");
    }

    // Carrega dados do CSV e popula uma árvore AVL.
    public static void carregarParaAVL(String csvFilePath, AVLTree<NacionalidadeInfo> avl) throws IOException {
        List<NacionalidadeInfo> dados = carregarDadosDoCSV(csvFilePath);
        for (NacionalidadeInfo info : dados) {
            avl.addNode(info);
        }
        System.out.println("AVL populada com " + dados.size() + " nacionalidades.");
    }

    // Carrega dados do CSV, agrega por nacionalidade e retorna uma lista.
    public static List<NacionalidadeInfo> carregarDadosDoCSV(String csvFilePath) throws IOException {
        Map<String, Integer> agregadorNacionalidades = new HashMap<>();
        
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(csvFilePath), StandardCharsets.UTF_8))) {
            
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
                String[] values = line.split(CSV_DELIMITER, -1); // -1 para incluir campos vazios
                
                // Verificar se a linha tem o número esperado de colunas
                if (values.length > Math.max(INDEX_NACIONALIDADE, INDEX_NUM_ALUNOS)) {
                    try {
                        String nacionalidade = values[INDEX_NACIONALIDADE].trim();
                        String numAlunosStr = values[INDEX_NUM_ALUNOS].trim();
                        
                        // Ignorar linhas com nacionalidade vazia ou número de alunos inválido
                        if (!nacionalidade.isEmpty() && !numAlunosStr.isEmpty()) {
                            int numAlunos = Integer.parseInt(numAlunosStr);
                            if (numAlunos >= 0) {
                                // Agrega no mapa (chave é o nome do país)
                                String chaveMapa = nacionalidade.toLowerCase();
                                agregadorNacionalidades.put(chaveMapa, 
                                    agregadorNacionalidades.getOrDefault(chaveMapa, 0) + numAlunos);
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter número de alunos na linha " + linhaAtual + ": " + e.getMessage());
                    }
                }
            }
        }
        
        // Converter o mapa para uma lista de NacionalidadeInfo
        List<NacionalidadeInfo> resultado = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : agregadorNacionalidades.entrySet()) {
            resultado.add(new NacionalidadeInfo(entry.getKey(), entry.getValue()));
        }
        
        return resultado;
    }
    
    // Converte uma árvore BST para um array de NacionalidadeInfo.
    public static NacionalidadeInfo[] bstParaArray(BSTTree<NacionalidadeInfo> bst) {
        List<NacionalidadeInfo> lista = percorrerBST(bst);
        return lista.toArray(new NacionalidadeInfo[0]);
    }
    
    //Converte uma árvore AVL para um array de NacionalidadeInfo.
    public static NacionalidadeInfo[] avlParaArray(AVLTree<NacionalidadeInfo> avl) {
        List<NacionalidadeInfo> lista = percorrerAVL(avl);
        return lista.toArray(new NacionalidadeInfo[0]);
    }
    
    // Percorre uma árvore BST em ordem e retorna uma lista dos elementos.
    public static List<NacionalidadeInfo> percorrerBST(BSTTree<NacionalidadeInfo> bst) {
        List<NacionalidadeInfo> resultado = new ArrayList<>();
        percorrerBSTEmOrdem(bst.getRoot(), resultado);
        return resultado;
    }
    
    // Método auxiliar recursivo para percorrer BST em ordem.
    private static void percorrerBSTEmOrdem(Node<NacionalidadeInfo> node, List<NacionalidadeInfo> lista) {
        if (node != null) {
            percorrerBSTEmOrdem(node.getLeft(), lista);
            lista.add(node.getData());
            percorrerBSTEmOrdem(node.getRight(), lista);
        }
    }
    
    // Percorre uma árvore AVL em ordem e retorna uma lista dos elementos.
    public static List<NacionalidadeInfo> percorrerAVL(AVLTree<NacionalidadeInfo> avl) {
        List<NacionalidadeInfo> resultado = new ArrayList<>();
        percorrerAVLEmOrdem(avl.getRoot(), resultado);
        return resultado;
    }
    
    // Método auxiliar recursivo para percorrer AVL em ordem.
    private static void percorrerAVLEmOrdem(AVLTreeNode<NacionalidadeInfo> node, List<NacionalidadeInfo> lista) {
        if (node != null) {
            percorrerAVLEmOrdem(node.getLeft(), lista);
            lista.add(node.getData());
            percorrerAVLEmOrdem(node.getRight(), lista);
        }
    }
    
    // Encontra a nacionalidade com maior número de alunos na árvore BST.
    public static NacionalidadeInfo encontrarNacionalidadeComMaisAlunosBST(BSTTree<NacionalidadeInfo> bst) {
        List<NacionalidadeInfo> lista = percorrerBST(bst);
        return encontrarNacionalidadeComMaisAlunos(lista);
    }
    
    // Encontra a nacionalidade com maior número de alunos na árvore AVL.
    public static NacionalidadeInfo encontrarNacionalidadeComMaisAlunosAVL(AVLTree<NacionalidadeInfo> avl) {
        List<NacionalidadeInfo> lista = percorrerAVL(avl);
        return encontrarNacionalidadeComMaisAlunos(lista);
    }
    
    // Método auxiliar para encontrar a nacionalidade com mais alunos em uma lista.
    private static NacionalidadeInfo encontrarNacionalidadeComMaisAlunos(List<NacionalidadeInfo> lista) {
        if (lista.isEmpty()) {
            return null;
        }
        
        NacionalidadeInfo maxInfo = lista.get(0);
        for (NacionalidadeInfo info : lista) {
            if (info.getTotalAlunos() > maxInfo.getTotalAlunos()) {
                maxInfo = info;
            }
        }
        return maxInfo;
    }
    
    // Calcula o total de alunos estrangeiros na árvore BST.
    public static int calcularTotalAlunosBST(BSTTree<NacionalidadeInfo> bst) {
        List<NacionalidadeInfo> lista = percorrerBST(bst);
        return calcularTotalAlunos(lista);
    }
    
    // Calcula o total de alunos estrangeiros na árvore AVL.
    public static int calcularTotalAlunosAVL(AVLTree<NacionalidadeInfo> avl) {
        List<NacionalidadeInfo> lista = percorrerAVL(avl);
        return calcularTotalAlunos(lista);
    }
    
    // Método auxiliar para calcular o total de alunos em uma lista.
    private static int calcularTotalAlunos(List<NacionalidadeInfo> lista) {
        int total = 0;
        for (NacionalidadeInfo info : lista) {
            total += info.getTotalAlunos();
        }
        return total;
    }
}
