package com.example.user.simpleui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity implements DrinkOrderDalog.OnDrinkOrderListener  {

    TextView totalTextView;
    ListView drinkMenuListView;

    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶","珍珠紅茶拿鐵","紅茶拿鐵"};
    int[] mPrice = {25,35,45,35};
    int[] lPrice = {35,45,55,45};
    int[] imageId={R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};

    List<Drink> drinks = new ArrayList<>();
    List<DrinkOrder> orders = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
        Log.d("Debug", "DrinkMenuActivity OnCreate");
        setData();

        totalTextView = (TextView)findViewById(R.id.totalTextView);
        drinkMenuListView = (ListView)findViewById(R.id.drinkMenuListView);

        setupDrinkMenuListView();

    }

    private void setData()
    {
        for(int i = 0;i< names.length; i++)
        {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.mPrice = mPrice[i];
            drink.lPrice = lPrice[i];
            drink.imageId=imageId[i];
            drinks.add(drink);
        }
    }

    private void setupDrinkMenuListView()
    {
        DrinkAdapter adapter = new DrinkAdapter(this, drinks);
        drinkMenuListView.setAdapter(adapter);

        drinkMenuListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrinkAdapter drinkAdapter = (DrinkAdapter) parent.getAdapter();
                Drink drink = (Drink) drinkAdapter.getItem(position);


                showDrinkDialog(drink);

            }
        });
    }

    public void showDrinkDialog(Drink drink)
    {
        DrinkOrder drinkOrder = new DrinkOrder(drink);
        for (DrinkOrder Order : orders)
        {
            if(Order.drink.name.equals(drink.name))
            {
                drinkOrder = Order;
                break;
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        DrinkOrderDalog dialog = DrinkOrderDalog.newInstance(drinkOrder);
        Fragment prev = getFragmentManager().findFragmentByTag("DrinkOrderDalog");
        if(prev !=null)
        {
            ft.remove(prev);

        }

        ft.addToBackStack(null);


        dialog.show(ft,"DrinkOrderDalog");
    }

    public  void  updateTotal()
    {
        int total = 0;
        for(DrinkOrder order: orders)
        {
            total += order.mNumber * order.drink.mPrice + order.lNumber * order.drink.lPrice;
        }

        totalTextView.setText(String.valueOf(total));
    }

    public void done (View view) {
        Intent intent = new Intent();
        JSONArray jsonArray = new JSONArray();

        for (DrinkOrder order : orders)
        {
            String data = order.toData();
            jsonArray.put(data);
        }

        intent.putExtra("results",jsonArray.toString());

        setResult(RESULT_OK,intent);
        finish();
   }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("Debug", "DrinkMenuActivity OnStart");

    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d("Debug","DrinkMenuActivity OnResume");
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.d("Debug", "DrinkMenuActivity OnPause");

    }
    @Override
    protected  void onStop()
    {
        super.onStop();
        Log.d("Debug", "DrinkMenuActivity OnStop");
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.d("Debug", "DrinkMenuActivity OnDestroy");
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.d("Debug", "DrinkMenuActivity OnRestart");
    }

    @Override
    public void onDrinkOrderFinished(DrinkOrder drinkOrder) {
        Boolean flag = false;
        for (int index = 0 ; index < orders.size() ; index ++)
        {
            if(orders.get(index).drink.name.equals(drinkOrder.drink.name))
            {
                orders.set(index, drinkOrder);
                flag = true;
                break;
            }
        }
        if(!flag)
        orders.add(drinkOrder);

        updateTotal();

    }
}
