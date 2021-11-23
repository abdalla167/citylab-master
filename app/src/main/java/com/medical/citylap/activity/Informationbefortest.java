package com.medical.citylap.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.medical.citylap.Adapter.AdapterInformatinTest;
import com.medical.citylap.R;
import com.medical.citylap.modles.InformationTest;

import java.util.ArrayList;

public class Informationbefortest extends AppCompatActivity {
ArrayList<InformationTest>informationbefortests=new ArrayList<>();
AdapterInformatinTest informatinTest;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informationbefortest);

        recyclerView=findViewById(R.id.recyclerViewforinformationtest);
        informationbefortests.add(new InformationTest (false,"السكر الصائم ","اشتراط الصيام من 6 -8 ساعات ويسمح بشرب الماء فقط اثناء فتره الصيام"));



        informationbefortests.add(new InformationTest (false,"الدهون بالدم",
                "يشترط الصيام من ساعه ويسمح بشرب الماء فقط خلال فتره الصيام"));



        informationbefortests.add(new InformationTest (false,"اختبار الحمل في البول","يفضل اول عينه في الصباح اذا كانت النتيجه سلبيه\n" +
                "يقترح اجراء اختبار الحمل في الدم لانه اكثر حساسيه"));


        informationbefortests.add(new InformationTest (false,"الدم الخفي في البراز","يمتنع المريض لمده ٤٨ ساعه قبل اجراء التحليل عن تناول الاطعمه الاتيه\n" +
                "اللحوم - الفجل - ادويه الروماتزيم - الاسبرين او اي ادويه اخري تحتوي علي فيتامين س او حديد او كورتيزون\n"));



        informationbefortests.add(new InformationTest (false,"الادويه العلاجيه (الربو - الصرع -  القلب)","يؤخذ الدواء بانتظام لمد خمسة ايام علي الاقل قبل اجراء التحليل ويشترط عدم القئ او الاسهال لمدة ٤٨ ساعه قبل اجراء التحليل"));


        informationbefortests.add(new InformationTest (false,"تحليل المزرعه(بول- براز-دم-...)","يشترط الامتناع عن المضاد الحيوي لمده لا تقل عن 48 ساعه قبل اجراء التحليل"));


        informationbefortests.add(new InformationTest (false,"تحاليل البول والبراز","باستثناء الحالات العاجله يكون دائما من الافضل فحص عينات البول والبراز الصباحيه الاولي ومن الافضل ان تكون في عبوات معقمه يقوم المعمل بتسليمها لصاحب التحاليل حرصا علي دقه النتائج"));


        informationbefortests.add(new InformationTest (false,"تحليل انيميا الفول ","يشترط عدم اجراء التحليل بعد نقل الدم او عند حدوث انتكاسه"));


        informationbefortests.add(new InformationTest (false,"سكر بعد الاكل بساعتين","يشترط احتساب الساعتين من بدايه الاكل ويشترط تناول الطعام في خلال اول عشر دقائق من بداية الساعتين"));



        informationbefortests.add(new InformationTest (false,"تحاليل السائل المنوي","يشترط الامنتناع عن الجماع او الاحتلام لمده اربع ايام متتاليه ويفضل اعطاء العينه بالمعمل "));

        informatinTest=new AdapterInformatinTest(this,informationbefortests);
        recyclerView.setAdapter(informatinTest);




    }
}