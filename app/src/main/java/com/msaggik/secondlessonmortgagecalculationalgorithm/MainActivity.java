package com.msaggik.secondlessonmortgagecalculationalgorithm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // задание полей
    float apartmentPrice = 1_000_000; // стоимость квартиры
    int account = 250_000; // счёт пользователя
    float wage = 100_000; // заработная плата в месяц
    int percentFree = 50; // доля заработной платы на любые траты
    float percentBank = 5; // годовая процентная ставка за ипотеку
    float[] monthlyPayments = new float[120]; // создание массива ежемесячных платежей на 10 лет

    // метод подсчёта стоимости квартиры с учётом первоначального взноса
    private float apartmentPriceWithContribution() {
        return apartmentPrice - account; // возврат подсчитанного значения
    }

    // метод подсчёта ежемесячных трат на ипотеку (зар.плата, процент своб.трат)
    public float mortgageCosts(float amount, int percent) {
        return (amount*percent)/100;
    }

    // метод подсчёта времени выплаты ипотеки (сумма долга, сумма платежа, годовой процент)
    // и заполнение массива monthlyPayments[] ежемесячными платежами
    public int countMonth(float total, float mortgageCosts, float percentBankYear) {

        float percentBankMonth = percentBankYear / 12; // подсчёт ежемесячного процента банка за ипотеку
        int count = 0; // счётчик месяцев выплаты ипотеки

        // алгоритм расчёта ипотеки
        while (total>0) {
            count++; // добавление нового месяца платежа
            total = (total + (total*percentBankMonth)/100) - mortgageCosts; // вычисление долга с учётом выплаты и процента
            // заполнение массива ежемесячными платежами за ипотеку
            if(total > mortgageCosts) { // если сумма долга больше ежемесячного платежа, то
                monthlyPayments[count-1] = mortgageCosts; // в массив добавляется целый платёж
            } else { // иначе
                monthlyPayments[count-1] = total; // в массив добавляется платёж равный остатку долга
            }
        }

        return count;
    }

    // создание дополнительных полей для вывода на экран полученных значений
    private TextView countOut; // поле вывода количества месяцев выплаты ипотеки
    private TextView manyMonthOut; // поле выписки по ежемесячным платежам

    // вывод на экран полученных значений
    @Override
    protected void onCreate(Bundle savedInstanceState) { // создание жизненного цикла активности
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // присваивание жизненному циклу активити представления activity_main

        // присваивание переменным активити элементов представления activity_main
        countOut = findViewById(R.id.countOut); // вывод информации количества месяцев выплаты ипотеки
        manyMonthOut = findViewById(R.id.manyMonthOut); // вывод информации выписки по ежемесячным платежам

        // запонение экрана
        // 1) вывод количества месяцев выплаты ипотеки
        countOut.setText("Ипотека будет выплачиваться " + countMonth(apartmentPriceWithContribution(), mortgageCosts(wage, percentFree),percentBank) + " месяцев");
        // 2) подготовка выписки
        String monthlyPaymentsList = "";
        for(float list : monthlyPayments) {
            if (list > 0) {
                monthlyPaymentsList = monthlyPaymentsList + Float.toString(list) + " монет ";
            } else {
                break;
            }
        }
        // 3) вывод выписки ежемесячных выплат по ипотеке
        manyMonthOut.setText("Первоначальный взнос " + account + " монет, ежемесячные выплаты: " + monthlyPaymentsList);
    }
}