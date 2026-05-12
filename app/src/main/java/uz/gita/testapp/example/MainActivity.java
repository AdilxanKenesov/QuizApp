package uz.gita.testapp.example;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import uz.gita.testapp.R;

public class MainActivity extends AppCompatActivity {
    //private final List<CategoryData> categorys = new ArrayList<>();
    private LinearLayout container;
    private ImageView imgProduct, logoProduct;
    private ShimmerFrameLayout shimmerFrameLayout;
    private View dataLayout;
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

//        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
//        dataLayout = findViewById(R.id.data_layout);
//
//        imgProduct = dataLayout.findViewById(R.id.img_product);
//        logoProduct = dataLayout.findViewById(R.id.logo_product);
//        container = dataLayout.findViewById(R.id.category_container);
//
//        dataLayout.setVisibility(View.GONE);
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        shimmerFrameLayout.startShimmer();
//
//        new Handler().postDelayed(() -> {
//            loadCategoryData();
//            loadView();
//
//            shimmerFrameLayout.stopShimmer();
//            shimmerFrameLayout.setVisibility(View.GONE);
//            dataLayout.setVisibility(View.VISIBLE);
//        }, 3000);
    }

//    private void loadView() {
//        container.removeAllViews();
//        for (CategoryData category : categorys) {
//            View itemView = getLayoutInflater().inflate(R.layout.item_category_card, container, false);
//            TextView textView = itemView.findViewById(R.id.txt_category_name);
//            MaterialCardView cardView = itemView.findViewById(R.id.card_category);
//
//            textView.setText(category.getName());
//
//            updateCardUI(cardView, textView, category);
//
//            itemView.setOnClickListener(v -> {
//                for (CategoryData data : categorys) {
//                    data.setSelected(data.getId() == category.getId());
//                }
//                loadView();
//            });
//            container.addView(itemView);
//        }
//    }
//
//    private void updateCardUI(MaterialCardView cardView, TextView textView, CategoryData category) {
//        if (category.isSelected()) {
//            cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#EF2A39")));
//            cardView.setCardElevation(22f);
//            cardView.setTranslationZ(20f);
//            textView.setTextColor(Color.WHITE);
//
//            Glide.with(this).load(category.getProductImg()).into(imgProduct);
//            Glide.with(this).load(category.getProductLogo()).into(logoProduct);
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
//                cardView.setOutlineSpotShadowColor(Color.BLACK);
//                cardView.setOutlineAmbientShadowColor(Color.parseColor("#4D000000"));
//            }
//        } else {
//            cardView.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#F3F4F6")));
//            cardView.setCardElevation(0f);
//            cardView.setTranslationZ(0f);
//            textView.setTextColor(Color.parseColor("#6A6A6A"));
//        }
//    }
//
////    private void loadCategoryData() {
////        categorys.clear();
////        categorys.add(new CategoryData(1, "Oq Tepa", true, "https://png.pngtree.com/png-vector/20240710/ourmid/pngtree-burger-with-floating-ingredient-png-image_13054386.png", "https://api.logobank.uz/media/logos_png/oqtepa_logo-01.png"));
////        categorys.add(new CategoryData(2, "Grand Lavash", false, "https://cc.oqtepalavash.uz/api/image/9196c5d0-3449-43ec-b6b4-349846788392_small.png", "https://image.winudf.com/v2/image1/Y29tLmdyYW5kbGF2YXNocHJvX2ljb25fMTcxNDYyMTQwMV8wMzg/icon.png?w=312&fakeurl=1"));
////        categorys.add(new CategoryData(3, "Burgers", false, "https://png.pngtree.com/png-vector/20240710/ourmid/pngtree-burger-with-floating-ingredient-png-image_13054386.png", "https://halaldamu.kz/wp-content/uploads/2026/01/5f709d048001a342707213-1.png"));
////        categorys.add(new CategoryData(4, "Pizza", false, "https://www.panarottis.com/_ipx/w_3072&f_png/img/kenya/home-page-pizza-2.png", "https://iconape.com/wp-content/png_logo_vector/the-pizza-company-logo.png"));
////        categorys.add(new CategoryData(5, "Fries", false, "https://png.pngtree.com/png-vector/20250424/ourmid/pngtree-hot-and-crispy-french-fries-in-red-box-png-image_16097148.png", "https://logos-world.net/wp-content/uploads/2020/06/McDonalds-Logo.png"));
////    }
}