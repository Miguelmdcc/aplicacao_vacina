package poov.programa14javafx.modelo;

import java.time.LocalDate;

public class Aplicacao {
    private Long codigo;
    private Long pessoa;
    private Long vacina;
    private LocalDate atual;
    private Situacao situacao;

    public Aplicacao() {
        codigo = null;
        pessoa = null;
        vacina = null;
        atual = null;
        situacao = Situacao.ATIVO;
    }

    public Aplicacao(Long pessoa, Long vacina, LocalDate atual) {
        this.pessoa = pessoa;
        this.vacina = vacina;
        this.atual = atual;
        situacao = Situacao.ATIVO;
    }

    public Long getPessoa() {
        return pessoa;
    }

    public void setPessoa(Long pessoa) {
        this.pessoa = pessoa;
    }

    public Long getVacina() {
        return vacina;
    }

    public void setVacina(Long vacina) {
        this.vacina = vacina;
    }

    public LocalDate getAtual() {
        return atual;
    }

    public void setAtual(LocalDate atual) {
        this.atual = atual;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
        result = prime * result + ((vacina == null) ? 0 : vacina.hashCode());
        result = prime * result + ((atual == null) ? 0 : atual.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Aplicacao other = (Aplicacao) obj;
        if (pessoa == null) {
            if (other.pessoa != null)
                return false;
        } else if (!pessoa.equals(other.pessoa))
            return false;
        if (vacina == null) {
            if (other.vacina != null)
                return false;
        } else if (!vacina.equals(other.vacina))
            return false;
        if (atual == null) {
            if (other.atual != null)
                return false;
        } else if (!atual.equals(other.atual))
            return false;
        return true;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Aplicacao [codigo=" + codigo + ", pessoa=" + pessoa + ", vacina=" + vacina + ", atual=" + atual
                + ", situacao=" + situacao + "]";
    }

}
