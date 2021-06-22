package br.com.zup.nossocartao.servicosExternos.analiseFinanceira;

import br.com.zup.nossocartao.propostas.utils.StatusProposta;

public enum PropostaRetorno {
    COM_RESTRICAO{

        StatusProposta retorno(){
            return StatusProposta.NAO_ELEGIVEL;
        }

    },
    SEM_RESTRICAO{

        StatusProposta retorno(){
            return StatusProposta.ELEGIVEL;
        }
    };

    abstract StatusProposta retorno();
}
