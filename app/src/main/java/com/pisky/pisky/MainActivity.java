package com.pisky.pisky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pisky.pisky.models.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE = 1;
    private String[] PERMISOS = {
            Manifest.permission.INTERNET
    };
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    public ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private TextInputLayout txt_inputemail, txt_inputpassword;
    private TextView btnPolitica, txt_accionUser;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private AppCompatButton btn_ingresar;
    private String accionUser, txtAccionUser, btnAccionUser;
    private static final int SIGN_IN_GOOGLE_CODE = 1;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        permisos();
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTheme(R.style.HomeTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        vistas();
        initialize();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                 if (user != null) {
                        //String email = user.getEmail();
                        //String userName = user.getDisplayName();
                        //Picasso.get().load(user.getPhotoUrl()).into(imgProfile);
                        //loginButton.setVisibility(View.GONE);
                    } else {
                        //loginButton.setVisibility(View.VISIBLE);
                    }
            }
        };
    }
    private void permisos() {
        int leer2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        if (leer2 == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, PERMISOS, REQUEST_CODE);
        }
    }
    public void vistas() {
        FirebaseAuth.getInstance().signOut();
        firebaseAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        txt_inputemail = (TextInputLayout) findViewById(R.id.inputemail);
        txt_inputpassword = (TextInputLayout) findViewById(R.id.inputpassword);
        firebaseAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        btnPolitica = (TextView) findViewById(R.id.politica);
        txt_accionUser = (TextView) findViewById(R.id.txt_accionuser);
        txt_accionUser.setText(txtAccionUser);
        btn_ingresar = (AppCompatButton) findViewById(R.id.btn_registrar);
        setButtonListeners();
    }
    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    Toast.makeText(MainActivity.this,
                            "Bienvenido: ",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainUsuario.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //finish();
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);

                } else {
                    hideProgressDialog();
                    Toast.makeText(MainActivity.this, "Sesion Cerrada", Toast.LENGTH_LONG);

                }


            }
        };
        //Inicia Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setButtonListeners() {
        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegistrationLogin();
            }
        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_GOOGLE_CODE) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            signInGoogleFirebase(googleSignInResult);
        }
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }
    private void signInGoogleFirebase(final GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInResult.getSignInAccount().getIdToken(), null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        addGoogleToCollection(googleSignInResult);
                        Toast.makeText(MainActivity.this, "Ingresocorrecto Google " + googleSignInResult.getSignInAccount().getEmail(), Toast.LENGTH_LONG);
                        Intent intent = new Intent(MainActivity.this, MainUsuario.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        hideProgressDialog();
                        startActivity(intent);
                        //finish();
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);

                    } else {
                        hideProgressDialog();
                        Toast.makeText(MainActivity.this, "Algo sucedió Google!! ", Toast.LENGTH_LONG);
                    }

                }
            });

        } else {

        }

    }
    private void addDocumentToCollection() {
        String userId = firebaseAuth.getUid();
        String userDi = "";
        String userNacimiento = "";
        String userGenero = "";
        String userDireccion = "";
        String emailFacebook = firebaseAuth.getCurrentUser().getEmail();
        String userNameFacebook = firebaseAuth.getCurrentUser().getDisplayName();
        String userPhotoFacebook = "";
        String userPhoneFacebook = "";
        final Usuario user = new Usuario(userId, userDi, userNacimiento, userGenero, emailFacebook, userNameFacebook, userPhotoFacebook, userPhoneFacebook, userDireccion);

        //User sharedData = User.getInstance();
        //sharedData.setValue(userId);
        DatabaseReference usrID = myRef.child("users").child(userId);
        usrID.child("perfil").setValue(user);
        usrID.child("cursos_inscritos").setValue(user);
        usrID.child("cursos_finalizados").setValue(user);
        usrID.child("bolsa_empleo").setValue(user);

    }
    private void addGoogleToCollection(GoogleSignInResult googleSignInResult) {
        String userId = firebaseAuth.getUid();
        String userDi = "";
        String userNacimiento = "";
        String userGenero = "";
        String userDireccion = "";
        String emailGoogle = googleSignInResult.getSignInAccount().getEmail();
        String userNameGoogle = googleSignInResult.getSignInAccount().getDisplayName();
        String userPhotoGoogle = String.valueOf(googleSignInResult.getSignInAccount().getPhotoUrl());
        String userPhoneGoogle = "";
        final Usuario user = new Usuario(userId, userDi, userNacimiento, userGenero, emailGoogle, userNameGoogle, userPhotoGoogle, userPhoneGoogle, userDireccion);

        //User sharedData = User.getInstance();
        //sharedData.setValue(userId);
        DatabaseReference usrID = myRef.child("users").child(userId);
        usrID.child("perfil").setValue(user);
        usrID.child("cursos_inscritos").setValue(user);
        usrID.child("cursos_finalizados").setValue(user);
        usrID.child("bolsa_empleo").setValue(user);

    }
    private void handleRegistrationLogin() {
        final String email = txt_inputemail.getEditText().getText().toString();
        final String password = txt_inputpassword.getEditText().getText().toString();

        if (!validateEmailPass(email, password)) {
            return;
        }

        //show progress dialog
        showProgressDialog();

        //perform login and account creation depending on existence of email in firebase
        performLoginOrAccountCreation(email, password);
    }
    private void performLoginOrAccountCreation(final String email, final String password) {
        firebaseAuth.fetchProvidersForEmail(email).addOnCompleteListener(
                this, new OnCompleteListener<ProviderQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "checking to see if user exists in firebase or not");
                            ProviderQueryResult result = task.getResult();

                            if (result != null && result.getProviders() != null
                                    && result.getProviders().size() > 0) {
                                //Toast.makeText(ActividadIngreso.this, "El usuario ya existe!!", Toast.LENGTH_LONG).show();
                                performLogin(email, password);
                            } else {
                                //Log.d(TAG, "User doesn't exist, creating account");
                                registerAccount(email, password);
                            }
                        } else {
                            //Log.w(TAG, "User check failed", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Hay un problema, por favor intente de nuevo más tarde.",
                                    Toast.LENGTH_SHORT).show();

                        }
                        //hide progress dialog
                        hideProgressDialog();
                        //enable and disable login, logout buttons depending on signin status
                        //showAppropriateOptions();
                    }
                });
    }
    private void registerAccount(final String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "account created");
                            ingresarUsuario(email);
                            Toast.makeText(MainActivity.this,
                                    "Cuenta Registrada: " + firebaseAuth.getCurrentUser().getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                            Intent intent = new Intent(MainActivity.this, MainUsuario.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //finish();
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        } else {
                            //Log.d(TAG, "register account failed", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "El registro de la cuenta falló, vuelva a Intentar!! ",
                                    Toast.LENGTH_SHORT).show();
                            hideProgressDialog();
                        }
                        //hide progress dialog
                        hideProgressDialog();
                        //enable and disable login, logout buttons depending on signin status
                        //showAppropriateOptions();
                    }
                });
    }
    private void ingresarUsuario(String emailUser){
        String userId = firebaseAuth.getUid();
        String userDi = "";
        String userNacimiento = "";
        String userGenero = "";
        String userDireccion = "";
        String email = emailUser;
        String userName = "";
        String userPhoto = "";
        String userPhone = "";
        final Usuario user = new Usuario(userId, userDi, userNacimiento, userGenero, email, userName, userPhoto, userPhone, userDireccion);

        DatabaseReference usrID = myRef.child("users").child(userId);
        usrID.child("perfil").setValue(user);
        usrID.child("cursos_inscritos").setValue(user);
        usrID.child("cursos_finalizados").setValue(user);
        usrID.child("bolsa_empleo").setValue(user);
    }

    private void performLogin(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "login success");
                            Toast.makeText(MainActivity.this,
                                    "Bienvenido: ",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, MainUsuario.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //finish();
                            overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        } else {
                            //Log.e(TAG, "Login fail", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "La autenticación falló",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //hide progress dialog
                        //hideProgressDialog();
                        //enable and disable login, logout buttons depending on signin status
                        // showAppropriateOptions();
                    }
                });
    }

    //non-singed in user reset password email
    private void sendResetPasswordEmail() {
        final String email = txt_inputemail.getEditText().getText().toString();

        if (!validateEmail(email)) {
            return;
        }
        //show progress dialog
        showProgressDialog();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Se ha enviado un mensaje a tu correo: "
                                            + email,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            //Log.e(TAG, "Error in sending reset password code",
                            //    task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Hay un problema con restablecer la contraseña, intente más tarde.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean validateEmailPass(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            txt_inputemail.setError("Necesario.");
            valid = false;
        } else if (!email.contains("@")) {
            txt_inputemail.setError("No es una Dirección de correo electrónico.");
            valid = false;
        } else {
            txt_inputemail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            txt_inputpassword.setError("Necesario.");
            valid = false;
        } else if (password.length() < 6) {
            txt_inputpassword.setError("Min. 6 caracteres.");
            valid = false;
        } else {
            txt_inputpassword.setError(null);
        }

        return valid;
    }

    private boolean validateEmail(String email) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            txt_inputemail.setError("Necesario.");
            valid = false;
        } else if (!email.contains("@")) {
            txt_inputemail.setError("No es una Dirección de correo electrónico.");
            valid = false;
        } else {
            txt_inputemail.setError(null);
        }

        return valid;
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progressdialog);
    }
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        firebaseAuth.addAuthStateListener(mAuthListener);
        super.onStart();
        //showAppropriateOptions();

    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
        super.onStop();
        hideProgressDialog();

    }

    @Override
    protected void onDestroy() {
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
        super.onDestroy();

    }
}
