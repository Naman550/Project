package jacobrawlinson.assignmentapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FoodDiaryFragment extends Fragment {

    DatabaseHelper myDB;

    public FoodDiaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_food_diary, container, false);
   }

}
