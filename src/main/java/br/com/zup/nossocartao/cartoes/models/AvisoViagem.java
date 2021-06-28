package br.com.zup.nossocartao.cartoes.models;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    @NotBlank
    private String destino;

    @Future
    private LocalDate validoAte;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    private LocalDateTime avisadoEm = LocalDateTime.now();

    public AvisoViagem() {
    }

    public AvisoViagem(Cartao cartao, String destino, LocalDate validoAte, String ip, String userAgent) {

        this.cartao = cartao;
        this.destino = destino;
        this.validoAte = validoAte;
        this.ip = ip;
        this.userAgent = userAgent;
    }
}
