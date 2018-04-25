package brandon.example.com.luckingapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class InteresesActivity extends AppCompatActivity {

    GridLayout mainGrid;
    String[] intereses;
    ArrayList<String> seleccionado;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intereses);

        button = (Button) findViewById(R.id.button3);
        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        intereses = new String[]{"cerveza", "cafe", "romantic", "coktail", "mariscos", "wine", "hamburguesa", "sushi", "tacos", "ensaladas"};
        seleccionado = new ArrayList<>();
        //setSingleEvent(mainGrid);
        setToogleEvent(mainGrid);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seleccionado.size()>1){
                    Toast.makeText(InteresesActivity.this, "Selecciona solo una categoría", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    DatosDestino datosDestino = new DatosDestino();
                    datosDestino.categorias = seleccionado;
                    Log.e("MYLOG", String.valueOf(datosDestino.categorias));
                    Intent intent = new Intent(InteresesActivity.this, ConfiguracionActivity.class);
                    intent.putExtra("datos", datosDestino);
                    startActivity(intent);
                }

            }
        });

    }

    private void setToogleEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getChildCount();i++){
            final CardView cardView = (CardView)mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cardView.getCardBackgroundColor().getDefaultColor() == -1){
                        //Change background
                        cardView.setCardBackgroundColor(Color.parseColor("#FF6F00"));
                        //Toast.makeText(InteresesActivity.this, "Seleccionaste "+intereses[finalI], Toast.LENGTH_SHORT).show();
                        seleccionado.add(intereses[finalI]);
                    }else{
                        cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                        seleccionado.remove(intereses[finalI]);
                    }
                }
            });
        }
    }

  /*  private void setSingleEvent(GridLayout mainGrid) {
        for(int i=0;i<mainGrid.getColumnCount();i++){
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(InteresesActivity.this, "CLicked at index: "+ finalI +"", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }*/
}
