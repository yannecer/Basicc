package com.necer.basic.view.picker;

import android.content.Context;
import android.view.View;
import com.necer.basic.R;
import com.necer.basic.bean.Area;
import com.necer.basic.bean.City;
import com.necer.basic.bean.Districts;
import com.necer.basic.bean.Province;

import java.util.List;

/**
 * Created by necer on 2018/9/18.
 */
public class AddressPicker extends BasePicker {


    private WheelPicker wl_prov;
    private WheelPicker wl_city;
    private WheelPicker wl_dist;




    private Area area;




    public AddressPicker(Context context) {
        super(context);
    }

    @Override
    protected int getPickerView() {
        return R.layout.picker_address;
    }

    @Override
    protected void initPickerView(View pickView) {

        wl_prov = pickView.findViewById(R.id.wl_prov);
        wl_city = pickView.findViewById(R.id.wl_city);
        wl_dist = pickView.findViewById(R.id.wl_dist);



        wl_prov.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<Province>() {
            @Override
            public void onWheelSelected(WheelPicker wheelPicker, Province province, int position) {

                wl_city.replaceData(province.children);
                wl_city.setCurrentPosition(0);

            }
        });
        wl_city.setOnWheelChangeListener(new WheelPicker.OnWheelChangeListener<City>() {
            @Override
            public void onWheelSelected(WheelPicker wheelPicker, City city, int position) {
                wl_dist.replaceData(city.children);
                wl_dist.setCurrentPosition(0);
            }
        });

    }


    public AddressPicker setDistVisibility(boolean isVisibility) {
        wl_dist.setVisibility(isVisibility ? View.VISIBLE : View.GONE);
        return this;
    }
    public AddressPicker setArea(Area area) {
        this.area = area;

        List<Province> provinceList = area.data;

        wl_prov.replaceData(provinceList);


        Province province = provinceList.get(0);
        wl_city.replaceData(province.children);

        City city = province.children.get(0);
        wl_dist.replaceData(city.children);

        return this;
    }

    @Override
    protected void onSure() {


        Province province = (Province) wl_prov.getCurrentItem();
        City city = (City) wl_city.getCurrentItem();
        Districts districts = (Districts) wl_dist.getCurrentItem();

        if (onAddressSelectListener != null) {
            onAddressSelectListener.onAddressSelect(province, city, districts);
        }
    }



    public interface OnAddressSelectListener{
        void onAddressSelect(Province province, City city, Districts districts);
    }


    private OnAddressSelectListener onAddressSelectListener;
    public AddressPicker setOnAddressSelectListener(OnAddressSelectListener onAddressSelectListener) {
        this.onAddressSelectListener = onAddressSelectListener;
        return this;
    }
}
