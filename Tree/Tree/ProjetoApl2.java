package Tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProjetoApl2 {
        public static void main(String[] args) {
        String csvFilePath = "Quantidade de alunos estrangeiros por nacionalidade_2° Semestre 2023.csv";
        System.out.println("Iniciando aplicação com o arquivo: " + csvFilePath);

        BSTTree<NacionalidadeInfo> bst = new BSTTree<>();
        AVLTree<NacionalidadeInfo> avl = new AVLTree<>();
        Scanner scanner = new Scanner(System.in);
        boolean dadosCarregados = false;

        try {
            System.out.println("\n--- Carregando dados do CSV ---");
            // Carrega os dados uma vez para evitar recarregar a cada operação
            List<NacionalidadeInfo> dadosCarregadosLista = CSVLoader.carregarDadosDoCSV(csvFilePath);
            if (dadosCarregadosLista.isEmpty()) {
                System.err.println("Nenhum dado válido encontrado no arquivo CSV.");
            } else {
                // Popula as árvores
                for (NacionalidadeInfo info : dadosCarregadosLista) {
                    bst.addNode(info);
                    avl.addNode(info);
                }
                dadosCarregados = true;
                System.out.println("Dados carregados com sucesso em ambas as árvores.");
            }
        } catch (IOException e) {
            System.err.println("Erro CRÍTICO ao carregar o arquivo CSV: " + e.getMessage());
            System.err.println("Verifique se o arquivo está na pasta correta.");
        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado durante o carregamento: " + e.getMessage());
        }

        if (dadosCarregados) {
            int escolha = -1;
            while (escolha != 0) {
                exibirMenuPerguntas();
                try {
                    escolha = Integer.parseInt(scanner.nextLine());

                    switch (escolha) {
                        case 1:
                            responderPergunta1(bst, avl);
                            break;
                        case 2:
                            responderPergunta2(bst, avl);
                            break;
                        case 3:
                            responderPergunta3(bst, avl, scanner);
                            break;
                        case 4:
                            responderPergunta4(bst, avl);
                            break;
                        case 0:
                            System.out.println("Encerrando o programa...");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número.");
                    escolha = -1; 
                } catch (Exception e) {
                    System.err.println("Ocorreu um erro inesperado durante a operação: " + e.getMessage());
                    e.printStackTrace(); // Para depuração
                }
                
                if (escolha != 0) {
                    System.out.println("\nPressione Enter para continuar...");
                    scanner.nextLine(); 
                }
            }
        } else {
             System.out.println("\nNão foi possível carregar os dados. O programa será encerrado.");
        }

        scanner.close();
        System.out.println("Programa encerrado.");
    }

    private static void exibirMenuPerguntas() {
        System.out.println("\n--- Menu de Perguntas --- (Tempo medido para obter dados da árvore + processar)");
        System.out.println("1. Quais são os 10 países com maior número de alunos?");
        System.out.println("2. Quantos e quais países possuem apenas um aluno?");
        System.out.println("3. Quais nacionalidades possuem entre X e Y alunos? (Digite os valores desejados)");
        System.out.println("4. Qual país com mais alunos e quantos são?");
        System.out.println("0. Sair");
        System.out.print("Escolha uma pergunta: ");
    }

    // Pergunta 1: Top 10 países com mais alunos
    private static void responderPergunta1(BSTTree<NacionalidadeInfo> bst, AVLTree<NacionalidadeInfo> avl) {
        System.out.println("\n--- Top 10 Países com Mais Alunos ---");
        
        long startTimeBST = System.nanoTime();
        List<NacionalidadeInfo> listaBST = CSVLoader.percorrerBST(bst);
        
        // Ordenar a lista pelo número de alunos
        Collections.sort(listaBST, new Comparator<NacionalidadeInfo>() {
            @Override
            public int compare(NacionalidadeInfo o1, NacionalidadeInfo o2) {
                return Integer.compare(o2.getTotalAlunos(), o1.getTotalAlunos()); // Ordem decrescente
            }
        });
        
        // Pegar os 10 primeiros (ou menos, se a lista for menor)
        List<NacionalidadeInfo> top10BST = new ArrayList<>();
        for (int i = 0; i < Math.min(10, listaBST.size()); i++) {
            top10BST.add(listaBST.get(i));
        }
        long endTimeBST = System.nanoTime();
        
        // AVL
        long startTimeAVL = System.nanoTime();
        List<NacionalidadeInfo> listaAVL = CSVLoader.percorrerAVL(avl);
        
        // Ordenar a lista pelo número de alunos (decrescente)
        Collections.sort(listaAVL, new Comparator<NacionalidadeInfo>() {
            @Override
            public int compare(NacionalidadeInfo o1, NacionalidadeInfo o2) {
                return Integer.compare(o2.getTotalAlunos(), o1.getTotalAlunos()); // Ordem decrescente
            }
        });
        
        // Pegar os 10 primeiros (ou menos, se a lista for menor)
        List<NacionalidadeInfo> top10AVL = new ArrayList<>();
        for (int i = 0; i < Math.min(10, listaAVL.size()); i++) {
            top10AVL.add(listaAVL.get(i));
        }
        long endTimeAVL = System.nanoTime();

        System.out.println("Resultado:");
        for (int i = 0; i < top10BST.size(); i++) {
            System.out.println((i + 1) + ". " + top10BST.get(i));
        }
        
        System.out.println("\nTempo BST: " + BenchmarkTree.formatNanos(endTimeBST - startTimeBST));
        System.out.println("Tempo AVL: " + BenchmarkTree.formatNanos(endTimeAVL - startTimeAVL));
    }

    // Pergunta 2: Países com apenas um aluno
    private static void responderPergunta2(BSTTree<NacionalidadeInfo> bst, AVLTree<NacionalidadeInfo> avl) {
        System.out.println("\n--- Países com Apenas Um Aluno ---");

        // BST
        long startTimeBST = System.nanoTime();
        List<NacionalidadeInfo> listaBST = CSVLoader.percorrerBST(bst);
        
        // Filtrar países com apenas 1 aluno
        List<NacionalidadeInfo> umAlunoBST = new ArrayList<>();
        for (NacionalidadeInfo info : listaBST) {
            if (info.getTotalAlunos() == 1) {
                umAlunoBST.add(info);
            }
        }
        long endTimeBST = System.nanoTime();

        // AVL
        long startTimeAVL = System.nanoTime();
        List<NacionalidadeInfo> listaAVL = CSVLoader.percorrerAVL(avl);
        
        // Filtrar países com apenas 1 aluno
        List<NacionalidadeInfo> umAlunoAVL = new ArrayList<>();
        for (NacionalidadeInfo info : listaAVL) {
            if (info.getTotalAlunos() == 1) {
                umAlunoAVL.add(info);
            }
        }
        long endTimeAVL = System.nanoTime();

        System.out.println("Resultado:");
        System.out.println("Total de países com 1 aluno: " + umAlunoBST.size());
        if (!umAlunoBST.isEmpty()) {
             System.out.println("Países:");
             for (NacionalidadeInfo info : umAlunoBST) {
                 System.out.println("- " + info.getNomePais());
             }
        }

        System.out.println("\nTempo BST: " + BenchmarkTree.formatNanos(endTimeBST - startTimeBST));
        System.out.println("Tempo AVL: " + BenchmarkTree.formatNanos(endTimeAVL - startTimeAVL));
    }

    // Pergunta 3: Nacionalidades com 10 a 50 alunos
    private static void responderPergunta3(BSTTree<NacionalidadeInfo> bst, AVLTree<NacionalidadeInfo> avl, Scanner scanner) {
        System.out.println("\n--- Pergunta 3: Nacionalidades com X a Y Alunos ---");
        
        // Solicitar valores mínimo e máximo ao usuário
        int minAlunos = 0;
        int maxAlunos = 0;
        boolean valoresValidos = false;
        
        while (!valoresValidos) {
            try {
                System.out.print("Digite o valor mínimo de alunos: ");
                minAlunos = Integer.parseInt(scanner.nextLine().trim());
                
                System.out.print("Digite o valor máximo de alunos: ");
                maxAlunos = Integer.parseInt(scanner.nextLine().trim());
                
                if (minAlunos <= 0) {
                    System.out.println("O valor mínimo deve ser maior que zero. Tente novamente.");
                } else if (maxAlunos < minAlunos) {
                    System.out.println("O valor máximo deve ser maior ou igual ao valor mínimo. Tente novamente.");
                } else {
                    valoresValidos = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite apenas números inteiros. Tente novamente.");
            }
        }
        
        System.out.println("\nBuscando nacionalidades com " + minAlunos + " a " + maxAlunos + " alunos...");

        // BST - usando loops tradicionais
        long startTimeBST = System.nanoTime();
        List<NacionalidadeInfo> listaBST = CSVLoader.percorrerBST(bst);
        
        // Filtrar países com X a Y alunos
        List<NacionalidadeInfo> entreXeYBST = new ArrayList<>();
        for (NacionalidadeInfo info : listaBST) {
            if (info.getTotalAlunos() >= minAlunos && info.getTotalAlunos() <= maxAlunos) {
                entreXeYBST.add(info);
            }
        }
        
        // Ordenar alfabeticamente pelo nome do país
        Collections.sort(entreXeYBST, new Comparator<NacionalidadeInfo>() {
            @Override
            public int compare(NacionalidadeInfo o1, NacionalidadeInfo o2) {
                return o1.getNomePais().compareTo(o2.getNomePais());
            }
        });
        long endTimeBST = System.nanoTime();

        // AVL - usando loops tradicionais
        long startTimeAVL = System.nanoTime();
        List<NacionalidadeInfo> listaAVL = CSVLoader.percorrerAVL(avl);
        
        // Filtrar países com X a Y alunos
        List<NacionalidadeInfo> entreXeYAVL = new ArrayList<>();
        for (NacionalidadeInfo info : listaAVL) {
            if (info.getTotalAlunos() >= minAlunos && info.getTotalAlunos() <= maxAlunos) {
                entreXeYAVL.add(info);
            }
        }
        
        // Ordenar alfabeticamente pelo nome do país
        Collections.sort(entreXeYAVL, new Comparator<NacionalidadeInfo>() {
            @Override
            public int compare(NacionalidadeInfo o1, NacionalidadeInfo o2) {
                return o1.getNomePais().compareTo(o2.getNomePais());
            }
        });
        long endTimeAVL = System.nanoTime();

        System.out.println("Resultado:");
        System.out.println("Total de países com " + minAlunos + " a " + maxAlunos + " alunos: " + entreXeYBST.size());
        if (!entreXeYBST.isEmpty()) {
            System.out.println("Nacionalidades:");
            for (NacionalidadeInfo info : entreXeYBST) {
                System.out.println(info);
            }
        } else {
            System.out.println("Nenhuma nacionalidade encontrada com essa faixa de alunos.");
        }

        System.out.println("\nTempo BST: " + BenchmarkTree.formatNanos(endTimeBST - startTimeBST));
        System.out.println("Tempo AVL: " + BenchmarkTree.formatNanos(endTimeAVL - startTimeAVL));
    }

    // Pergunta 4: País com mais alunos
    private static void responderPergunta4(BSTTree<NacionalidadeInfo> bst, AVLTree<NacionalidadeInfo> avl) {
        System.out.println("\n--- País com Maior Número de Alunos ---");

        // BST
        long startTimeBST = System.nanoTime();
        NacionalidadeInfo maisAlunosBST = CSVLoader.encontrarNacionalidadeComMaisAlunosBST(bst);
        long endTimeBST = System.nanoTime();

        // AVL
        long startTimeAVL = System.nanoTime();
        NacionalidadeInfo maisAlunosAVL = CSVLoader.encontrarNacionalidadeComMaisAlunosAVL(avl);
        long endTimeAVL = System.nanoTime();

        System.out.println("Resultado:");
        if (maisAlunosBST != null) {
            System.out.println(maisAlunosBST);
        } else {
            System.out.println("Não foi possível determinar.");
        }

        System.out.println("\nTempo BST: " + BenchmarkTree.formatNanos(endTimeBST - startTimeBST));
        System.out.println("Tempo AVL: " + BenchmarkTree.formatNanos(endTimeAVL - startTimeAVL));
    }
    }