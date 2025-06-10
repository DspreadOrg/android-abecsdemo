package com.dspread.ppcomlibrary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.setis.bcw9.DeviceSerial;
import br.com.setis.bcw9.PPCompAndroid;
import br.com.setis.bibliotecapinpad.AcessoFuncoesPinpad;
import br.com.setis.bibliotecapinpad.GestaoBibliotecaPinpad;
import br.com.setis.bibliotecapinpad.conversoresEntradaSaida.util.Utils;
import br.com.setis.bibliotecapinpad.definicoes.BibliotecaPinpadNaoEncontradaExcecao;
import br.com.setis.bibliotecapinpad.definicoes.CodigosRetorno;
import br.com.setis.bibliotecapinpad.definicoes.ModoCriptografia;
import br.com.setis.bibliotecapinpad.definicoes.TabelaAID;
import br.com.setis.bibliotecapinpad.definicoes.TabelaCAPK;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoCheckEvent;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoChipDirect;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoClose;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoEncryptBuffer;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoFinishChip;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetCard;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetInfo;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGetPin;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoGoOnChip;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoOpen;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoRemoveCard;
import br.com.setis.bibliotecapinpad.entradas.EntradaComandoTableLoad;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoCheckEvent;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoChipDirect;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoEncryptBuffer;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoFinishChip;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetCard;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetInfo;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGetPin;
import br.com.setis.bibliotecapinpad.saidas.SaidaComandoGoOnChip;

import static com.dspread.ppcomlibrary.Tables.aidTablesValues;
import static com.dspread.ppcomlibrary.Tables.capkTablesValues;

//import com.dspread.dsplibrary.DeviceAbecsConstant;


public class WelcomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1000;
    private final String TAG = "WelcomeActivity";
    private String terminal = "SETIS";
    private List<Parametro> parametros;

    private static TextView textView;

    AcessoFuncoesPinpad acessoFuncoes;
    ManageFilesPermissionHelper.OnManageAllFilesPermissionResult resultCallback = new ManageFilesPermissionHelper.OnManageAllFilesPermissionResult() {
        @Override
        public void onGranted() {
            Log.e(this.getClass().getName(), "already get permission");
            PPCompAndroid.getInstance().initBindService("D30M");
        }

        @Override
        public void onDenied() {
            Log.e(this.getClass().getName(), "no permission granted");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button openButton = findViewById(R.id.open_button);
        Button finishChipButton = findViewById(R.id.finishchip_button);
        Button getPinButton = findViewById(R.id.getpin_button);
        Button goonchipButton = findViewById(R.id.goonchip_button);
        Button getcardButton = findViewById(R.id.getcard_button);
        Button checkEvents = findViewById(R.id.events_button);
        Button closeButton = findViewById(R.id.close_button);
        Button tablesload = findViewById(R.id.tables_button);
        Button removeCardButton = findViewById(R.id.removecard_button);
        Button chipDirectButton = findViewById(R.id.chipdirect_button);
        Button encryptBuffer = findViewById(R.id.encryptBuffer_button);
        Button abortButton = findViewById(R.id.abort_button);
        Button newTestButton = findViewById(R.id.newtest_button);
        textView = findViewById(R.id.display_textview);

        ManageFilesPermissionHelper.requestPermission(this, resultCallback);
        //  new  DeviceAbecs()
        try {
            acessoFuncoes = GestaoBibliotecaPinpad.obtemInstanciaAcessoFuncoesPinpad();
        } catch (BibliotecaPinpadNaoEncontradaExcecao bibliotecaPinpadNaoEncontradaExcecao) {
            Log.e(TAG, bibliotecaPinpadNaoEncontradaExcecao.getMessage());
        }


        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("OPN ENd", " command opn strat");
                parametros = new ArrayList<>();
                //Toast.makeText(WelcomeActivity.this, "Click OPEN", Toast.LENGTH_SHORT).show();

                EntradaComandoOpen entrada = new EntradaComandoOpen(new InterfaceUsuario(WelcomeActivity.this, textView));
                acessoFuncoes.open(entrada, new EntradaComandoOpen.OpenCallback() {
                    @Override
                    public void comandoOpenEncerrado(CodigosRetorno codigosRetorno) {
                        acessoFuncoes.getInfo(new EntradaComandoGetInfo() {
                            @Override
                            public void comandoGetInfoEncerrado(SaidaComandoGetInfo saidaComandoGetInfo) {
                                if (saidaComandoGetInfo.obtemResultadoOperacao() == CodigosRetorno.OK) {
                                    terminal = saidaComandoGetInfo.obtemFabricantePinpad().replace(" ", "");
                                    parametros.add(new Parametro("Fabricante", Tipo.OUT, saidaComandoGetInfo.obtemFabricantePinpad()));
                                    parametros.add(new Parametro("Modelo", Tipo.OUT, saidaComandoGetInfo.obtemModeloPinpad()));
                                    parametros.add(new Parametro("VersaoSO", Tipo.OUT, saidaComandoGetInfo.obtemVersaoSistemaOperacionalPinpad()));
                                    parametros.add(new Parametro("VersaoEspec", Tipo.OUT, saidaComandoGetInfo.obtemVersaoEspecificacao()));
                                    parametros.add(new Parametro("VersaoApp", Tipo.OUT, saidaComandoGetInfo.obtemVersaoAplicacaoGerenciadora()));
                                    parametros.add(new Parametro("NumeroSerie", Tipo.OUT, saidaComandoGetInfo.obtemNumeroSeriePinpad()));
                                } else {
                                    parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoGetInfo.obtemResultadoOperacao().name()));
                                }

                                Log.e("OPN ENd", " command opn end");

                                alertDialog(parametros);
                            }
                        });
                    }
                });

            }
        });
        finishChipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                EntradaComandoFinishChip entradaComandoFinishChip = new EntradaComandoFinishChip(EntradaComandoFinishChip.ResultadoComunicacaoAdquirente.TRANSACAO_APROVADA, "00");
                entradaComandoFinishChip.informaDadosEMVRecebidos("9108A8B7DA7800800000".getBytes());
                entradaComandoFinishChip.informaListaTagsEMV("959B9F029F039F1A5F2A9A9C9F379F279F26829F369F109F34".getBytes());
                acessoFuncoes.finishChip(entradaComandoFinishChip, new EntradaComandoFinishChip.FinishChipCallback() {
                    @Override
                    public void comandoFinishChipEncerrado(SaidaComandoFinishChip saidaComandoFinishChip) {
                        Log.d(TAG, "FinishChip Ok");
                        if (saidaComandoFinishChip.obtemResultadoOperacao() == CodigosRetorno.OK) {
                            try {
                                parametros.add(new Parametro("DadosEMV", Tipo.OUT, new String(saidaComandoFinishChip.obtemDadosEMV(), "UTF-8")));
                                parametros.add(new Parametro("IssuerScriptsResults", Tipo.OUT, saidaComandoFinishChip.obtemIssuerScriptsResults() == null ? "" : new String(saidaComandoFinishChip.obtemIssuerScriptsResults(), "UTF-8")));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else {
                            parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoFinishChip.obtemResultadoOperacao().name()));
                        }
                        alertDialog(parametros);
                    }
                });

            }
        });


        getcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("WelcomeActivity:", "StartGCX");
                parametros = new ArrayList<>();
                List<Integer> tipoAplicacao = new ArrayList<>();
                tipoAplicacao.add(1);
                int valor = 100;
                EntradaComandoGetCard entradaComandoGetCard = new EntradaComandoGetCard
                        .Builder(new Date(System.currentTimeMillis()))
                        .informaIndiceAdquirente(Geral.REDECREDENCIADORA)
                        .informaTipoAplicacao(tipoAplicacao)
                        .informaValorTotal(valor)
                        .informaTimestamp(Geral.TIMESTAMP)
                        //.informaTimestamp("01234567800")
                        .informaPermiteCtls(true)
                        .build();

                acessoFuncoes.getCard(entradaComandoGetCard, new EntradaComandoGetCard.GetCardCallback() {
                    @Override
                    public void comandoGetCardEncerrado(SaidaComandoGetCard saidaComandoGetCard) {
                        if (saidaComandoGetCard.obtemResultadoOperacao() == CodigosRetorno.OK) {
                            DateFormat df = new SimpleDateFormat("MM/yy");
                            parametros.add(new Parametro("IssuerCountryCode", Tipo.OUT, Integer.toString(saidaComandoGetCard.obtemDadosCartao().obtemIssuerCountryCode())));
                            parametros.add(new Parametro("NomeAplicacao", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemNomeAplicacao()));
                            parametros.add(new Parametro("NomePortador", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemNomePortador()));
                            parametros.add(new Parametro("Pan", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemPan()));
                            if (saidaComandoGetCard.obtemDadosCartao().obtemDataVencimento() != null)
                                parametros.add(new Parametro("DataVencimento", Tipo.OUT, df.format(saidaComandoGetCard.obtemDadosCartao().obtemDataVencimento())));
                            parametros.add(new Parametro("PanSequenceNumber", Tipo.OUT, Integer.toString(saidaComandoGetCard.obtemDadosCartao().obtemPanSequenceNumber())));
                            parametros.add(new Parametro("Trilha1", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemTrilha1()));
                            parametros.add(new Parametro("Trilha2", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemTrilha2()));
                            parametros.add(new Parametro("Trilha3", Tipo.OUT, saidaComandoGetCard.obtemDadosCartao().obtemTrilha3()));
                        } else {
                            parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoGetCard.obtemResultadoOperacao().name()));
                        }
                        Log.i("WelcomeActivity:", "EndGCX");
                        alertDialog(parametros);
                    }
                });


            }
        });
        goonchipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();

                EntradaComandoGoOnChip.ParametrosTerminalRiskManagement parametrosTerminalRiskManagement = new EntradaComandoGoOnChip.ParametrosTerminalRiskManagement();
                parametrosTerminalRiskManagement.informaMaxTargetPercentage(Byte.valueOf("75"));
                parametrosTerminalRiskManagement.informaThresholdValue("00001388".getBytes());
                parametrosTerminalRiskManagement.informaTerminalFloorLimit("00002710".getBytes());
                parametrosTerminalRiskManagement.informaMaxTargetPercentage(Byte.valueOf("20"));

                EntradaComandoGoOnChip entradaComandoGoOnChip = new EntradaComandoGoOnChip.Builder(4, ModoCriptografia.MK_WK_3DES, 1)
                        .informaForcaTransacaoOnline(false)
                        .informaPanNaListaExcecao(false)
                        .informaWorkingKey("000000000000000000000000".getBytes())
                        .informaValorTotal(100)
                        .informaValorTroco(0)
                        .informaPermiteBypass(false)
                        .informaParametrosTerminalRiskManagement(parametrosTerminalRiskManagement)
                        .informaListaTagsEMV("5A9F029F1A955F2A9A9C9F37829F369F109F269F275F349F039F34".getBytes())
                        .build();
                acessoFuncoes.goOnChip(entradaComandoGoOnChip, new EntradaComandoGoOnChip.GoOnChipCallback() {
                    @Override
                    public void comandoGoOnChipEncerrado(SaidaComandoGoOnChip saidaComandoGoOnChip) {
                        if (saidaComandoGoOnChip.obtemResultadoOperacao() == CodigosRetorno.OK) {
                            parametros.add(new Parametro("ResultadoProcessamentoEMV", Tipo.OUT, saidaComandoGoOnChip.obtemResultadoProcessamentoEMV() != null ? saidaComandoGoOnChip.obtemResultadoProcessamentoEMV().name() : ""));
                            parametros.add(new Parametro("DadosEMV", Tipo.OUT, saidaComandoGoOnChip.obtemDadosEMV() != null ? new String(saidaComandoGoOnChip.obtemDadosEMV(), StandardCharsets.ISO_8859_1) : ""));
                            parametros.add(new Parametro("Ksn", Tipo.OUT, saidaComandoGoOnChip.obtemKsn() != null ? new String(saidaComandoGoOnChip.obtemKsn(), StandardCharsets.ISO_8859_1) : ""));
                            parametros.add(new Parametro("PinCriptografado", Tipo.OUT, saidaComandoGoOnChip.obtemPinCriptografado() != null ? new String(saidaComandoGoOnChip.obtemPinCriptografado(), StandardCharsets.ISO_8859_1) : ""));
                        } else {
                            parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoGoOnChip.obtemResultadoOperacao().name()));
                        }
                        alertDialog(parametros);
                    }
                });

            }
        });
        getPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                EntradaComandoGetPin entradaComandoGetPin = new EntradaComandoGetPin(
                        ModoCriptografia.MK_WK_3DES,
                        1,
                        "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".getBytes(),
                        "0000000000000000",
                        "LINHA 01########ENTRE COM o PIN:");
                acessoFuncoes.getPin(entradaComandoGetPin, new EntradaComandoGetPin.GetPinCallback() {
                    @Override
                    public void comandoGetPinEncerrado(SaidaComandoGetPin saidaComandoGetPin) {
                        if (saidaComandoGetPin.obtemResultadoOperacao() == CodigosRetorno.OK) {
                            parametros.add(new Parametro("PinBlk", Tipo.OUT, new String(saidaComandoGetPin.obtemPinCriptografado(), StandardCharsets.ISO_8859_1)));
                            parametros.add(new Parametro("Ksn", Tipo.OUT, new String(saidaComandoGetPin.obtemKSN(), StandardCharsets.ISO_8859_1)));
                        } else {
                            parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoGetPin.obtemResultadoOperacao().name()));
                        }
                        alertDialog(parametros);
                    }
                });


            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                acessoFuncoes.close(new EntradaComandoClose() {
                    @Override
                    public void comandoCloseEncerrado() {
                        parametros.add(new Parametro("Retorno", Tipo.RET, "OK"));
                        alertDialog(parametros);
                        showMessage("SETIS");
                    }
                });

            }
        });
        checkEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                showMessage(" APROXIME, INSIRA\r\n OU PASSE O CARTAO");
                List<EntradaComandoCheckEvent.Eventos> eventos = new ArrayList<>();
                eventos.add(EntradaComandoCheckEvent.Eventos.VERIFICA_INSERCAO_ICC);
                eventos.add(EntradaComandoCheckEvent.Eventos.VERIFICA_PASSAGEM_CARTAO_MAGNETICO);
                eventos.add(EntradaComandoCheckEvent.Eventos.VERIFICA_APROXIMACAO_CTLS);
                EntradaComandoCheckEvent entradaComandoChecEvents = new EntradaComandoCheckEvent(eventos);
                acessoFuncoes.checkEvent(entradaComandoChecEvents, new EntradaComandoCheckEvent.CheckEventCallback() {
                    @Override
                    public void eventoRecebido(SaidaComandoCheckEvent saidaComandoCheckEvent) {
                        if (saidaComandoCheckEvent.obtemResultadoOperacao() == CodigosRetorno.OK) {
                            parametros.add(new Parametro("EVENTO", Tipo.RET, saidaComandoCheckEvent.obtemEventoOcorrido().name()));
                            if (saidaComandoCheckEvent.obtemEventoOcorrido() == SaidaComandoCheckEvent.EventoOcorrido.CARTAO_MAG_LIDO) {
                                parametros.add(new Parametro("TRILHA 1", Tipo.RET, saidaComandoCheckEvent.obtemDadosCartao().obtemTrilha1()));
                                parametros.add(new Parametro("TRILHA 2", Tipo.RET, saidaComandoCheckEvent.obtemDadosCartao().obtemTrilha2()));
                                parametros.add(new Parametro("TRILHA 3", Tipo.RET, saidaComandoCheckEvent.obtemDadosCartao().obtemTrilha3()));
                            }
                        } else {
                            parametros.add(new Parametro("Retorno", Tipo.RET, saidaComandoCheckEvent.obtemResultadoOperacao().name()));
                        }
                        alertDialog(parametros);
                    }
                });


            }
        });
        tablesload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage("Loading...");
                parametros = new ArrayList<>();
                List<TabelaAID> aidTables = aidTablesValues();
                List<TabelaCAPK> capkTables = capkTablesValues();
                EntradaComandoTableLoad entradaComandoTableLoad = new EntradaComandoTableLoad(
                        Geral.REDECREDENCIADORA, Geral.TIMESTAMP, aidTables, capkTables, null);
                acessoFuncoes.tableLoad(entradaComandoTableLoad, new EntradaComandoTableLoad.TableLoadCallback() {
                    @Override
                    public void comandoTableLoadEncerrado(CodigosRetorno codigosRetorno) {
                        parametros.add(new Parametro("Retorno", Tipo.RET, codigosRetorno.name()));
                        alertDialog(parametros);
                    }
                });

            }
        });
        encryptBuffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                EntradaComandoEncryptBuffer entradaComandoEncryptBuffer = new EntradaComandoEncryptBuffer
                        .Builder("4C45455045415254".getBytes(), ModoCriptografia.MK_WK_3DES, 1)
                        .informaWorkingKey("FE4B136446329FE60000000000000000".getBytes())
                        .build();//md by zhanghb
                acessoFuncoes.encryptBuffer(entradaComandoEncryptBuffer, new EntradaComandoEncryptBuffer.EncryptBufferCallback() {
                    @Override
                    public void comandoEncryptBufferEncerrado(SaidaComandoEncryptBuffer saidaComandoEncryptBuffer) {
                        try {
                            if (saidaComandoEncryptBuffer.obtemDadosCriptografados() != null) {

                                String encryData = Utils.bytesToHex(saidaComandoEncryptBuffer.obtemDadosCriptografados());
                                //new String(saidaComandoEncryptBuffer.obtemDadosCriptografados(), "UTF-8");

                                parametros.add(new Parametro("DadosCriptografados", Tipo.OUT, encryData));
                            } else {
                                parametros.add(new Parametro("DadosCriptografados", Tipo.OUT, ""));
                            }

                            if (saidaComandoEncryptBuffer.obtemKSN() != null) {
                                parametros.add(new Parametro("Ksn", Tipo.OUT, new String(saidaComandoEncryptBuffer.obtemKSN(), "UTF-8")));
                            } else {
                                parametros.add(new Parametro("Ksn", Tipo.OUT, ""));
                            }
                            alertDialog(parametros);

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
        removeCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                acessoFuncoes.removeCard(new EntradaComandoRemoveCard("RETIRE O CARTAO"), new EntradaComandoRemoveCard.RemoveCardCallback() {
                    @Override
                    public void cartaoRemovido(CodigosRetorno codigosRetorno) {
                        parametros.add(new Parametro("Retorno", Tipo.RET, codigosRetorno.toString()));
                        alertDialog(parametros);
                    }
                });

            }
        });


        chipDirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
//                EntradaComandoChipDirect entradaComandoChipDirect =
//                        new EntradaComandoChipDirect(
//                                EntradaComandoChipDirect.IdentificadorSlotCartao.ICC_ACOPLADOR_PRINCIPAL,
//                                EntradaComandoChipDirect.Operacao.DESLIGAR_CARTAO
////                        );
//                byte dadosCartao[] = {
//                        0x00, (byte) 0xA4, 0x04, 0x00,
//                        0x07, (byte) 0xA0, 0x00, 0x00,
//                        0x00, 0x04, 0x10, 0x10};

                EntradaComandoChipDirect entradaComandoChipDirect =
                        new EntradaComandoChipDirect(
                                EntradaComandoChipDirect.IdentificadorSlotCartao.CONTACTLESS,
                                EntradaComandoChipDirect.Operacao.LIGAR_CARTAO
                        );
                byte dadosCartao[] = {
                        0x00, (byte) 0xA4, 0x04, 0x00,
                        0x07, (byte) 0xA0, 0x00, 0x00,
                        0x00, 0x04, 0x10, 0x10};


                entradaComandoChipDirect.informaComandoCartao(dadosCartao);
                acessoFuncoes.chipDirect(entradaComandoChipDirect,
                        new EntradaComandoChipDirect.ChipDirectCallback() {
                            @Override
                            public void comandoChipDirectEncerrado(SaidaComandoChipDirect saidaComandoChipDirect) {
                                parametros.add(new Parametro("Retorno", Tipo.RET,
                                        saidaComandoChipDirect.obtemResultadoOperacao().toString()));
                                if (saidaComandoChipDirect.obtemResultadoOperacao() == CodigosRetorno.OK) {
                                    try {
                                        if (saidaComandoChipDirect.obtemRespostaCartao() != null) {
                                            parametros.add(new Parametro("Resposta do Cartao", Tipo.OUT,
                                                    new String(saidaComandoChipDirect.obtemRespostaCartao(), "UTF-8")));
                                        }
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                                alertDialog(parametros);
                            }
                        });

            }
        });


        abortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros = new ArrayList<>();
                acessoFuncoes.abort();
            }
        });

        newTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(WelcomeActivity.this, NewTestActivity.class);
                startActivity(intent);
            }
        });
    }


    private void alertDialog(List<Parametro> parametros) {
        showMessage(terminal);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        StringBuilder message = new StringBuilder();
        for (Parametro p : parametros) {
            message.append(p.getNome());
            message.append(":\t");
            message.append(p.getValor());
            message.append(":\n");
        }
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static String mapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append("&");
            }
            String value = map.get(key);
            try {
                stringBuilder.append((key != null ? URLEncoder.encode(key, "UTF-8") : ""));
                stringBuilder.append("=");
                stringBuilder.append(value != null ? URLEncoder.encode(value, "UTF-8") : "");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("This method requires UTF-8 encoding support", e);
            }
        }

        return stringBuilder.toString();
    }


    public static void showMessage(String message) {
        Log.e("WelcomeActivity", message);
        textView.setText(message);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    protected void onResume() {
        super.onResume();
        ManageFilesPermissionHelper.onResumeCheck();
    }

    protected void onDestroy() {
        super.onDestroy();

    }
}