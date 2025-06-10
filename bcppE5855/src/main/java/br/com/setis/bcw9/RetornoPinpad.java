/*     */
package br.com.setis.bcw9;
/*     */
/*     */

import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class RetornoPinpad
        /*     */ {
    /*     */
    public static CodigosRetorno parseCodigoRetorno(Integer retorno) {
        /*  15 */
        Retornos r = Retornos.getByInt(retorno.intValue());
        /*     */
        /*  17 */
        CodigosRetorno codigosRetorno = null;
        /*  18 */
        switch (r)
            /*     */ {
            case PP_OK:
                /*     */
            case PP_ALREADYOPEN:
                /*  21 */
                codigosRetorno = CodigosRetorno.OK;
                /* 155 */
                return codigosRetorno;
            case PP_INVCALL:
                codigosRetorno = CodigosRetorno.CHAMADA_INVALIDA;
                return codigosRetorno;
            case PP_INVPARM:
                codigosRetorno = CodigosRetorno.PARAMETRO_INVALIDO;
                return codigosRetorno;
            case PP_TIMEOUT:
                codigosRetorno = CodigosRetorno.TIMEOUT;
                return codigosRetorno;
            case PP_CANCEL:
                codigosRetorno = CodigosRetorno.OPERACAO_CANCELADA;
                return codigosRetorno;
            case PP_NOTOPEN:
                codigosRetorno = CodigosRetorno.PINPAD_NAO_INICIALIZADO;
                return codigosRetorno;
            case PP_EXECERR:
                codigosRetorno = CodigosRetorno.ERRO_INTERNO;
                return codigosRetorno;
            case PP_INVMODEL:
                codigosRetorno = CodigosRetorno.MODELO_INVALIDO;
                return codigosRetorno;
            case PP_NOFUNC:
                codigosRetorno = CodigosRetorno.OPERACAO_NAO_SUPORTADA;
                return codigosRetorno;
            case PP_TABEXP:
                codigosRetorno = CodigosRetorno.TABELAS_EXPIRADAS;
                return codigosRetorno;
            case PP_TABERR:
                codigosRetorno = CodigosRetorno.ERRO_GRAVACAO_TABELAS;
                return codigosRetorno;
            case PP_PORTERR:
            case PP_COMMERR:
            case PP_UNKNOWNSTAT:
            case PP_RSPERR:
            case PP_COMMTOUT:
            case PP_INTERR:
                codigosRetorno = CodigosRetorno.ERRO_INTERNO;
                return codigosRetorno;
            case PP_MCDATAERR:
                codigosRetorno = CodigosRetorno.ERRO_LEITURA_CARTAO_MAG;
                return codigosRetorno;
            case PP_ERRPIN:
                codigosRetorno = CodigosRetorno.CHAVE_PIN_AUSENTE;
                return codigosRetorno;
            case PP_NOCARD:
                codigosRetorno = CodigosRetorno.CARTAO_AUSENTE;
                return codigosRetorno;
            case PP_PINBUSY:
                codigosRetorno = CodigosRetorno.PINPAD_OCUPADO;
                return codigosRetorno;
            case PP_SAMINV:
                codigosRetorno = CodigosRetorno.SAM_INVALIDO;
                return codigosRetorno;
            case PP_NOSAM:
                codigosRetorno = CodigosRetorno.SAM_AUSENTE;
                return codigosRetorno;
            case PP_SAMERR:
                codigosRetorno = CodigosRetorno.ERRO_MODULO_SAM;
                return codigosRetorno;
            case PP_DUMBCARD:
                codigosRetorno = CodigosRetorno.CARTAO_MUDO;
                return codigosRetorno;
            case PP_ERRCARD:
                codigosRetorno = CodigosRetorno.ERRO_COMUNICACAO_CARTAO;
                return codigosRetorno;
            case PP_CARDERRSTRUCT:
            case PP_CARDINVDATA:
                codigosRetorno = CodigosRetorno.CARTAO_COM_DADOS_INVALIDOS;
                return codigosRetorno;
            case PP_CARDINVALIDAT:
                codigosRetorno = CodigosRetorno.CARTAO_INVALIDADO;
                return codigosRetorno;
            case PP_CARDPROBLEMS:
                codigosRetorno = CodigosRetorno.CARTAO_COM_PROBLEMAS;
                return codigosRetorno;
            case PP_CARDAPPNAV:
                codigosRetorno = CodigosRetorno.CARTAO_SEM_APLICACAO;
                return codigosRetorno;
            case PP_CARDAPPNAUT:
                codigosRetorno = CodigosRetorno.APLICACAO_NAO_UTILIZADA;
                return codigosRetorno;
            case PP_CARDBLOCKED:
            case PP_CARDAPPBLOCKED:
                codigosRetorno = CodigosRetorno.CARTAO_BLOQUEADO;
                return codigosRetorno;
            case PP_ERRFALLBACK:
                codigosRetorno = CodigosRetorno.ERRO_FALLBACK;
                return codigosRetorno;
            case PP_CTLSSMULTIPLE:
                codigosRetorno = CodigosRetorno.MULTIPLOS_CTLSS;
                return codigosRetorno;
            case PP_CTLSSCOMMERR:
                codigosRetorno = CodigosRetorno.ERRO_COMUNICACAO_CTLSS;
                return codigosRetorno;
            case PP_CTLSSINVALIDAT:
                codigosRetorno = CodigosRetorno.CTLSS_INVALIDADO;
                return codigosRetorno;
            case PP_CTLSSPROBLEMS:
                codigosRetorno = CodigosRetorno.CTLSS_COM_PROBLEMAS;
                return codigosRetorno;
            case PP_CTLSSAPPNAV:
                codigosRetorno = CodigosRetorno.CTLSS_SEM_APLICACAO;
                return codigosRetorno;
            case PP_CTLSSAPPNAUT:
                codigosRetorno = CodigosRetorno.CTLSS_APLICACAO_NAO_SUPORTADA;
                return codigosRetorno;
            case PP_CTLSSONDEVICE:
                codigosRetorno = CodigosRetorno.CTLSS_DISPOSITIVO_EXTERNO;
                return codigosRetorno;
            case PP_CTLSSIFCHG:
                codigosRetorno = CodigosRetorno.CTLSS_MUDA_INTERFACE;
                return codigosRetorno;
            case UNKNOWN:
                codigosRetorno = CodigosRetorno.ERRO_INTERNO;
                return codigosRetorno;
        }
        codigosRetorno = CodigosRetorno.ERRO_INTERNO;
        return codigosRetorno;
        /*     */
    }
    /*     */
}


/* Location:              D:\安装软件工具包\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\BCPP_E5855-v001.45_debug\classes.jar!\br\com\setis\bcw9\RetornoPinpad.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */