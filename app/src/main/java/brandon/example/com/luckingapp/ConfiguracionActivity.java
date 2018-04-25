package brandon.example.com.luckingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ConfiguracionActivity extends AppCompatActivity {

    private SeekBar seekBar, seekBar2;
    private TextView tv13, tv14, tv15, tv16, tv17, tv18, tv19, tv20, tv21;
    private Button button;
    private String costo = "1";
    private String distancia = "1000";
    private DatosDestino datosDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        datosDestino = (DatosDestino) getIntent().getSerializableExtra("datos");
        button = (Button) findViewById(R.id.takecityButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatosDestino datos = new DatosDestino();
                datos.categorias = datosDestino.categorias;
                datos.price = costo;
                datos.radio = distancia;
                Intent intent = new Intent(ConfiguracionActivity.this, GoActivity.class);
                intent.putExtra("datos",datos);
                //intent.putExtra("arreglo", lista);
                //intent.putExtra("costo",costo);
                //intent.putExtra("distancia",distancia);
                startActivity(intent);
                //Toast.makeText(ConfiguracionActivity.this, costo.toString() + "--"+distancia.toString(), Toast.LENGTH_SHORT).show();

            }
        });




        tv13 = (TextView) findViewById(R.id.textView13);
        tv14 = (TextView) findViewById(R.id.textView14);
        tv15 = (TextView) findViewById(R.id.textView15);
        tv16 = (TextView) findViewById(R.id.textView16);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int seekBarProgress = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                seekBarProgress = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv13.setTextColor(Color.GRAY);
                tv14.setTextColor(Color.GRAY);
                tv15.setTextColor(Color.GRAY);
                tv16.setTextColor(Color.GRAY);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                switch (seekBarProgress){
                    case 0:
                        tv13.setTextColor(Color.GREEN);
                        costo = "1";
                        break;
                    case 1:
                        tv14.setTextColor(Color.GREEN);
                        costo = "2";
                        break;
                    case 2:
                        tv15.setTextColor(Color.GREEN);
                        costo = "3";
                        break;
                    case 3:
                        tv16.setTextColor(Color.GREEN);
                        costo = "4";
                        break;
                    default:

                        break;
                }
            }

        });



        //Segundo SeekBar

        tv17 = (TextView) findViewById(R.id.textView17);
        tv18 = (TextView) findViewById(R.id.textView18);
        tv19 = (TextView) findViewById(R.id.textView19);
        tv20 = (TextView) findViewById(R.id.textView20);
        tv21 = (TextView) findViewById(R.id.textView21);

        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            int seekBarProgress = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                seekBarProgress = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv17.setTextColor(Color.GRAY);
                tv18.setTextColor(Color.GRAY);
                tv19.setTextColor(Color.GRAY);
                tv20.setTextColor(Color.GRAY);
                tv21.setTextColor(Color.GRAY);



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                switch (seekBarProgress){
                    case 0:
                        tv17.setTextColor(Color.YELLOW);
                        distancia = "1000";
                        break;
                    case 1:
                        tv18.setTextColor(Color.YELLOW);
                        distancia = "2000";
                        break;
                    case 2:
                        tv19.setTextColor(Color.YELLOW);
                        distancia = "3000";
                        break;
                    case 3:
                        tv20.setTextColor(Color.YELLOW);
                        distancia = "4000";
                        break;
                    case 4:
                        tv21.setTextColor(Color.YELLOW);
                        distancia = "5000";
                        break;
                    default:

                        break;
                }
            }

        });



    }
}
