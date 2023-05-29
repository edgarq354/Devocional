package com.elisoft.devocional;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elisoft.devocional.databinding.ActivityPrincipalBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Principal extends AppCompatActivity {

    FloatingActionButton fb_cliente,fb_perfil;

    private static final int Date_id = 0;
    TextView tv_fecha,tv_tipo_usuario,tv_comision;
    int dia,mes,anio;

    AlertDialog alert2 = null;

    ProgressDialog pDialog;

    @Override
    protected void onStart() {
        verificar_sesion();
        super.onStart();
    }

    Suceso suceso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        fb_cliente=findViewById(R.id.fb_cliente);
        fb_perfil=findViewById(R.id.fb_perfil);
        tv_tipo_usuario=findViewById(R.id.tv_tipo_usuario);
        tv_comision=findViewById(R.id.tv_comision);


        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.it_empresas:
                        //servicio_lista_cliente_por_fecha();
                        break;

                    case R.id.it_historial:
                        //startActivity(new Intent(Menu_usuario.this, Historial_pedido_usuario.class));
                        break;
                    case R.id.it_buscar:
                        //formultario_buscar_cliente_por_codigo();
                        break;
                }
                return true;
            }
        });


//fecha
        Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH)+1;
        dia = c.get(Calendar.DAY_OF_MONTH);
        tv_fecha= (TextView) findViewById(R.id.tv_fecha);
        tv_fecha.setText(dia+" de "+mes_numero_a_letra(mes)+" del "+anio);

        tv_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("menu","Click");  // DO SOMETHING HERE
                showDialog(Date_id);
            }
        });

    }


    public void  verificar_sesion()
    {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);

        tv_tipo_usuario.setText(usuario.getString("tipo_usuario",""));


        if(usuario.getString("login_usuario","0").equals("0"))
        {
            eliminar_sesion();
            Intent intent=new Intent(this, Animacion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }
    private void eliminar_sesion( ) {
        SharedPreferences usuario=getSharedPreferences("perfil",MODE_PRIVATE);
        SharedPreferences.Editor editar=usuario.edit();
        editar.putString("nombre","");
        editar.putString("sexo","");
        editar.putString("email","");
        editar.putString("telefono","");
        editar.putString("id_usuario","");
        editar.putString("tipo_usuario","");
        editar.putString("login_usuario","0");
        editar.commit();
    }



    public String mes_numero_a_letra(int i)
    {
        String mes="";
        switch (i)
        {
            case 1: mes="Enero"; break;
            case 2: mes="Febrero"; break;
            case 3: mes="Marzo"; break;
            case 4: mes="Abril"; break;
            case 5: mes="Mayo"; break;
            case 6: mes="Junio"; break;
            case 7: mes="Julio"; break;
            case 8: mes="Agosto"; break;
            case 9: mes="Septiembre"; break;
            case 10: mes="Octubre"; break;
            case 11: mes="Noviembre"; break;
            case 12: mes="Diciembre"; break;
        }

        return  mes;
    }



    public void mensaje(String mensaje)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create();
        builder.show();
    }

}