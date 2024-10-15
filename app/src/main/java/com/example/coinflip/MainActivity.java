package com.example.coinflip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView coin;
    private TextView allText;
    private TextView wonText;
    private TextView lostText;

    private int won = 0;
    private int lost = 0;
    private int all = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.coin = findViewById(R.id.coin);
        this.wonText = findViewById(R.id.won);
        this.lostText = findViewById(R.id.lost);
        this.allText = findViewById(R.id.all);
        this.coin.setOnClickListener(view -> createDialog());
    }
    private void createDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choice");
        builder.setMessage("Choose between the followings");
        builder.setPositiveButton("Head", (dialogInterface, i) -> FlipCoin(0));
        builder.setNegativeButton("Tail", (dialogInterface, i) -> FlipCoin(1));
        builder.show();
    }
    @SuppressLint("SetTextI18n")
    private void FlipCoin(int choice)
    {
        Random rand = new Random();
        int random = rand.nextInt(2);
        String result = "";
        if (choice == random)
        {
            this.won += 1;
        }
        else
        {
            this.lost += 1;
        }
        switch (random)
        {
            case 0:
                this.coin.setImageResource(R.drawable.heads);
                result = "Head " + random;
                break;
            case 1:
                this.coin.setImageResource(R.drawable.tails);
                result = "Tail " + random;
                break;
            default:
                break;
        }
        Toast.makeText(this, "Result: " + result, Toast.LENGTH_SHORT).show();
        this.all += 1;
        this.wonText.setText("Won: " + this.won);
        this.lostText.setText("Lost: " + this.lost);
        this.allText.setText("All: " + this.all);
        if (this.won >= 3 || (this.lost == 1 && this.won == 2))
        {
            createEndDialog(true);
        }
        else if (this.lost >= 3 || (this.won == 1 && this.lost == 2))
        {
            createEndDialog(false);
        }
    }
    private void createEndDialog(boolean wonOrLost)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(wonOrLost ? "You've won" : "You've lost");
        builder.setMessage("Would you like to start a new game?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> ResetCounter());
        builder.setNegativeButton("No", (dialogInterface, i) -> finish());
        builder.show();
    }

    private void ResetCounter()
    {
        this.coin.setImageResource(R.drawable.heads);
        this.won = 0;
        this.lost = 0;
        this.all = 0;
        this.wonText.setText(R.string.won_0);
        this.lostText.setText(R.string.lost_0);
        this.allText.setText(R.string.all_0);
    }
}