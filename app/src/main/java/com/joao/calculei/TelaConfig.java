package com.joao.calculei;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TelaConfig extends AppCompatActivity {

    TextView txtDificuldade;
    TextView txtTempoNova;
    TextView txtTempoResolve;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tela_config);

        txtDificuldade = (TextView) findViewById(R.id.txtDificuldade);
        txtTempoNova = (TextView) findViewById(R.id.txtTempoNova);
        txtTempoResolve = (TextView) findViewById(R.id.txtTempoResolve);



        btnSalvar = (Button) findViewById(R.id.btnSalvarConfig);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar();
            }
        });
        CarregarDoBanco();
    }

    private void CarregarDoBanco(){
        Cursor curRank = Banco.BDAdapter.executaConsultaSQL(TelaConfig.this,"select * from config");
        if (curRank.moveToFirst()) {
            int colunaDificuldade = curRank.getInt(curRank.getColumnIndex("Dificuldade"));
            int colunaTemponova = curRank.getInt(curRank.getColumnIndex("TempoNova"))/1000;
            int colunaTempoResolve = curRank.getInt(curRank.getColumnIndex("TempoResolve"))/1000;

            txtDificuldade.setText(""+colunaDificuldade);
            txtTempoResolve.setText(""+colunaTempoResolve);
            txtTempoNova.setText(""+colunaTemponova);
        }
    }

    private void Salvar() {
        Banco.BDAdapter.executarComandoSQL(TelaConfig.this,"delete from config");
        int Dificuldade = 0;
        int TempoNova = 0;
        int TempoResolve = 0;
        try {
            Dificuldade = Integer.parseInt(txtDificuldade.getText().toString());
            TempoNova = Integer.parseInt(txtTempoNova.getText().toString())*1000;
            TempoResolve = Integer.parseInt(txtTempoResolve.getText().toString())*1000;
        } catch (Exception e) {
            finish();
        }
        Banco.BDAdapter.executarComandoSQL(TelaConfig.this,"insert into config(dificuldade, temponova, temporesolve) values("+ Dificuldade+","+ TempoNova+","+ TempoResolve+");");
        finish();
    }

}
