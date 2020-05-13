package adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.mymoneyapp.GetData;

import java.util.List;

public class DataAdapter extends ArrayAdapter <GetData>{
    private int resourceId;
    DataAdapter(Context context, int viewResourceId, List<GetData>objects){
        super(context,viewResourceId,objects);
        resourceId=viewResourceId;
    }


}