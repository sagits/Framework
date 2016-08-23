package atrativaweb.com.br.framework;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.gc.materialdesign.views.ButtonRectangle;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rockapps.com.library.local.LocalDbImplement;
import rockapps.com.library.model.UserModel;
import rockapps.com.library.volley.CallSingleListener;
import rockapps.com.library.volley.OnDialogButtonClick;

public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.username)
    EditText userName;
    @Bind(R.id.password)
    EditText password;
    private UserModel object;
    @Bind(R.id.enter)
    ButtonRectangle enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.enter)
    void onLogin() {

        object = new UserModel();
        object.setEmail(userName.getText().toString());
        object.setPassword(password.getText().toString());

        startActivity(new Intent(this, MenuActivity.class));
        finish();

    }


    private void login(final UserModel object) {

        OnDialogButtonClick onDialogButtonClick = new OnDialogButtonClick() {
            @Override
            public void onPositiveClick() {
                login(object);
            }

            @Override
            public void onNegativeClick() {
            }
        };

        CallSingleListener<UserModel> callListener = new CallSingleListener<UserModel>(this, UserModel.class, onDialogButtonClick) {


            @Override
            public void onErrorResponse(VolleyError error) {
                super.onErrorResponse(error);
                showErrorInDialog();
            }

            @Override
            public void onResponse(JSONObject response) {
                super.onResponse(response);
                try {

                    if (this.success()) {
                        new LocalDbImplement<UserModel>(LoginActivity.this).save(this.jsonMessage.getObject(), UserModel.class, UserModel.class.getSimpleName());
           //             startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        finish();
                    } else {
                        showErrorInDialog();
                    }

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };

     //   new UserDao(this).loginWaiter(callListener, object).doRequest();

    }

}