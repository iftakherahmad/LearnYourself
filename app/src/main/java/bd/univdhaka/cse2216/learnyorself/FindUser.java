package bd.univdhaka.cse2216.learnyorself;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindUser extends Activity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    ArrayList<User> users=new ArrayList<>();
    private FindUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_find_user);
            recyclerView = findViewById(R.id.recyclerView);
            progressBar = findViewById(R.id.progressbar);
            progressBar.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            adapter = new FindUserAdapter(users, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(R.menu.find_user_menu, menu);
            MenuItem item = menu.findItem(R.id.search);
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    users.clear();
                    progressBar.setVisibility(View.VISIBLE);
                    adapter.notifyDataSetChanged();
                    updateRecyclerView(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void updateRecyclerView(String query) {
        DatabaseReference userTable= FirebaseDatabase.getInstance().getReference("users");
        userTable.orderByChild("name").startAt(query).endAt(query+"\uf8ff").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("--5");
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    System.out.println("--6");
                    String id=dataSnapshot1.getKey();
                    System.out.println("id"+id);
                    String name=dataSnapshot1.child("name").getValue().toString();
                    String profilePic=dataSnapshot1.child("profilePicUrl").getValue().toString();
                    System.out.println("--7");
                    String registrationDate=dataSnapshot1.child("signupDate").getValue().toString();
                    User user=new User(name,"","",profilePic,registrationDate,"",id,0,0,0);
                    users.add(user);
                    System.out.println("--9");
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
