package com.example.neverforget;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    /** アプリケーションバーパターンと対話するNavigationUIメソッドの構成オプション */
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //定型文
        setContentView(R.layout.activity_main); // 定型文

        // ツールバーの設定(androidx.appcompat.widget.ToolBar)
        Toolbar toolbar = findViewById(R.id.toolbar); // app_bar_main.xml内,(XMLファイルはアクティビティのレイアウトへインクルードされる)
        setSupportActionBar(toolbar); // アクティビティのアクションツールバーとして関連づける。ナビゲーションメニューとオプションメニューが使用可能になる。

        // フローティングアクションボタンの設定、マテリアルデザインコンポーネント
        FloatingActionButton fab = findViewById(R.id.fab); // app_bar_main.xml内,fabって略し過ぎでは？
        fab.setOnClickListener(new View.OnClickListener() { // フローティングボタンのクリック
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG) // スナックバー構築,TOastみたいに画面下部にメッセージを表示する
                        .setAction("Action", null).show(); // setActionの第二引数で任意のメッセージクリック時のアクションを設定できる
            }
        });

        // 以下ドロワーメニューの設定
        DrawerLayout drawer = findViewById(R.id.drawer_layout); // AndroidXライブラリのドロワーレイアウト
        NavigationView navigationView = findViewById(R.id.nav_view); //マテリアルデザインコンポーネントのナビゲーションビュー


        // アプリケーションバーを構築する
        // メニューのトップレベルの宛先となる項目のメニューIDを渡す
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        // AndroidXライブラリのナビゲーションコントローラオブジェクトを取得する。第二引数はナビゲーションホストのID
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // ナビゲーションコントローラにアクションバーを設定する。画面の切り替わりとアクションバーのタイトルが連動するようになる。
        // AppCompatActivity.getSupportActionBar()で取得できるアクションバーをセットアップし、NavControllerです要する。
        // AppBarConfigurationはナビゲーションボタンの表示方法を制御する。
        // navigateUp(NavController, AppBarConfiguration)を呼び出し"アップ"ボタンを処理する。
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // ナビゲーションコントローラで使用するナビゲーションビューを設定する。
        // メニュー項目が選択されるとonNavDestinationSelected(MenuItem, NavController)が呼び出される。
        // NavigationViewで選択した項目は、宛先が変更されると自動的に更新される。
        // NavigationViewがDrawerLayoutに含まれている場合、メニュー項目の選択と同時にドロワーが閉じる。
        // NavigationViewにBottomSheetBehaviorが関連づけられている場合、メニュー項目を選択すると、下のシートは非表示になる。
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    /**
     * アクティビティの標準オプションメニューの内容を初期化する。
     * オーバーライドで独自のオプションメニューを設定できる。
     * メニュー項目をMenuに配置する必要がある。
     * これは、オプションメニューが初めて表示される場合に呼び出される。
     * メニューが表示されるたびに更新するには{@link #onPrepareOptionsMenu(Menu)}を参照すること。
     * @param menu アイテムを配置するオプションメニュー
     * @return true メニューを表示 / false メニューを表示しない
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューをインフレート。アクションバーが存在すれば項目が追加される。
        getMenuInflater().inflate(R.menu.main, menu);
        // メニューを表示
        return true;
    }

    /**
     * ユーザーがアクションバーから"アップ"ナビゲーションを選択するたびに呼び出される。
     * このアクティビティの親が設定(マニフェストまたはエイリアスで)されている場合、"アップ"は自動的に処理される。
     * 多分、サポート処理の実装有無を返すのが目的ではなく、アップ処理そのものだと思う。
     * 親を指定するには{@link #getSupportParentActivityIntent()}を参照すること。
     * @return true アップナビゲーションを処理 / false それ以外
     */
    @Override
    public boolean onSupportNavigateUp() {
        // AndroidXライブラリのナビゲーションコントローラオブジェクトを取得する。第二引数はナビゲーションホストのID
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // 多分、アップ処理し、成否を返す
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) // NavControllerにアップ処理をさせる。アップ処理が行われたらtrueが返る
                || super.onSupportNavigateUp(); //不明。規程のアップ処理?
    }
}
