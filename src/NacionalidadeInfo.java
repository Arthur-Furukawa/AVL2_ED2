package src;

public class NacionalidadeInfo implements Comparable<NacionalidadeInfo> {
    private String nomePais;
    private int totalAlunos;

    public NacionalidadeInfo(String nomePais, int totalAlunos) {
        this.nomePais = nomePais;
        this.totalAlunos = totalAlunos;
    }

    public String getNomePais() {
        return nomePais;
    }

    public int getTotalAlunos() {
        return totalAlunos;
    }

    public void adicionarAlunos(int quantidade) {
        this.totalAlunos += quantidade;
    }

    @Override
    public int compareTo(NacionalidadeInfo outra) {
        // Comparação case-insensitive para consistência
        return this.nomePais.compareToIgnoreCase(outra.nomePais);
    }

    @Override
    public String toString() {
        return "Nacionalidade: " + nomePais + ", Total de Alunos: " + totalAlunos;
    }

    // Sobrescrever equals e hashCode para consistência com compareTo
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NacionalidadeInfo that = (NacionalidadeInfo) obj;
        return nomePais.equalsIgnoreCase(that.nomePais);
    }

    @Override
    public int hashCode() {
        return nomePais.toLowerCase().hashCode();
    }
}

