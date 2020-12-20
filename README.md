# NotePad
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/login.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/loginfailed.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/loginsuccess.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/search.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/setpassword.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/setpasswordsuccess.png)

##一：时间戳的添加
**1.在project添加修改时间**

```
 private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID,
            NotePad.Notes.COLUMN_NAME_TITLE, 
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
    };

```
**2.在dataColumns,viewIDs添加修改时间,同时在notelist_item中添加一个textview显示时间戳**
```
String[] dataColumns = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE} ;
int[] viewIDs = { android.R.id.text1,R.id.text2};
```



**3.在NoteEditor中updateNote()中转换时间格式，并在编辑保存后设置时间戳的值**
```
ContentValues values = new ContentValues();
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, System.currentTimeMillis());
        Long now = Long.valueOf(System.currentTimeMillis());
        SimpleDateFormat sf = new SimpleDateFormat("yy/MM/dd HH:mm");
        Date d = new Date(now);
        String format = sf.format(d);
        values.put(NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE, format);
```


##二：搜索功能的实现

**1.添加搜索框按钮，我把他安排在菜单栏上，在list_options_menu.xml中添加search选项**
```
	<item
        android:id="@+id/menu_search"
        android:showAsAction="always"
        android:title="@string/menu_search">

    </item>
```
**2.添加搜索的xml项目**
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    <SearchView
        android:id="@+id/search_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:queryHint="请输入搜索内容"
        >
    </SearchView>

    <ListView
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:drawSelectorOnTop="false" />
    <TextView
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:id="@android:id/empty"
        android:gravity="center"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:text="无搜索结果" />
</LinearLayout>
```
**2.这里是search类的核心代码，主要功能是搜索项目，并且监听用户点击项目，点击后进入NoteEditor**
```
public boolean onQueryTextChange(String string) {
        String selection1 = NotePad.Notes.COLUMN_NAME_TITLE+" like ? or "+NotePad.Notes.COLUMN_NAME_NOTE+" like ?";
        String[] selection2 = {"%"+string+"%","%"+string+"%"};
        Cursor cursor = sqLiteDatabase.query(
                NotePad.Notes.TABLE_NAME,
                PROJECTION, // The columns to return from the query
                selection1, // The columns for the where clause
                selection2, // The values for the where clause
                null,          // don't group the rows
                null,          // don't filter by row groups
                NotePad.Notes.DEFAULT_SORT_ORDER // The sort order
        );
        // The names of the cursor columns to display in the view, initialized to the title column
        String[] dataColumns = {
                NotePad.Notes.COLUMN_NAME_TITLE,
                NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
        } ;
        // The view IDs that will display the cursor columns, initialized to the TextView in
        // noteslist_item.xml
        int[] viewIDs = {
                android.R.id.text1,
                android.R.id.text2
        };
        // Creates the backing adapter for the ListView.
        SimpleCursorAdapter adapter
                = new SimpleCursorAdapter(
                this,                             // The Context for the ListView
                R.layout.noteslist_item,         // Points to the XML for a list item
                cursor,// The cursor to get items from
                dataColumns,
                viewIDs
        );
        // Sets the ListView's adapter to be the cursor adapter that was just created.
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onListItemClick(listView,view,id);
            }
        });


        return true;
    }


    protected void onListItemClick(ListView l, View v, long id) {

        // Constructs a new URI from the incoming URI and the row ID
        Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);

        // Gets the action from the incoming Intent
        String action = getIntent().getAction();

        // Handles requests for note data
        if (Intent.ACTION_PICK.equals(action) || Intent.ACTION_GET_CONTENT.equals(action)) {

            // Sets the result to return to the component that called this Activity. The
            // result contains the new URI
            setResult(RESULT_OK, new Intent().setData(uri));
        } else {

            // Sends out an Intent to start an Activity that can handle ACTION_EDIT. The
            // Intent's data is the note ID URI. The effect is to call NoteEdit.
            startActivity(new Intent(Intent.ACTION_EDIT, uri));
        }
    }
```
##三：登录功能和设置密码
**1.写login类
这里也很简单，无非是做一个登录界面，然后设置初始账号密码123，设置按钮监听，比对输入(我做的很简单)**
```
public class Login extends Activity {

    private EditText userName, password;
    private Button btn_login;
    private String userNameValue,passwordValue;
    private SharedPreferences sp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
        userName = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_mima);
        btn_login = (Button) findViewById(R.id.btn_login);




        btn_login.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                userNameValue = userName.getText().toString();
                passwordValue = password.getText().toString();

                if(userNameValue.equals("123")&&passwordValue.equals("123"))
                {
                    Toast.makeText(Login.this,"登录成功", Toast.LENGTH_SHORT).show();
                    //跳转界面
                    Intent intent = new Intent(Login.this,NotesList.class);
                    Login.this.startActivity(intent);
                    finish();

                }else{

                    Toast.makeText(Login.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
```
2.在清单文件AndroidManifest.xml里加过滤器让登录页面先运行，登录成功就跳转Notelist
```
<activity android:name="Login"
        android:theme="@android:style/Theme.Holo.Wallpaper">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
</activity>

```

3.在菜单栏添加设置账号选项,在Notelist中编写选项的功能，我这里只是做了个对话框，然后简单写了下设置账号方法，毕竟功能没有完全实现，就随便写了个方法...
```
        case R.id.setpassword:
            LayoutInflater layoutInflater=LayoutInflater.from(NotesList.this);
            View loginView =layoutInflater.inflate(R.layout.setaccount,null);
            final EditText etUsername = (EditText) loginView.findViewById(R.id.et_user_name);
            final EditText etPwd = (EditText) loginView.findViewById(R.id.et_password);
            AlertDialog.Builder dialog = new AlertDialog.Builder(NotesList.this);
            dialog.setTitle("设置账号密码");
            dialog.setView(loginView);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String password = etPwd.getText().toString().trim();
                            String username = etUsername.getText().toString().trim();
                            setAccount(username, password);
                            String text = "账号:" + username + "\n" + "密码:" + password;
                            Toast.makeText(NotesList.this, text, Toast.LENGTH_SHORT).show();
                        }
                    }

            );
            dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });
            dialog.create().show();
            return true;
```

```
    private void setAccount(String account,String passwor){
        ContentValues values = new ContentValues();
        values.put("USERNAME",account);
        values.put("PASSWORD",passwor);
```
--------**最后耍了个小聪明，给我的项目加了个透明的Ui，看起来像优化过了一样**------
也很简单，在AndroidManifest中修改主题就行
```
<activity android:name="NotesList" android:label="@string/title_notes_list"
            android:theme="@android:style/Theme.Holo.Wallpaper">
```
本来想写夜间模式切换的，看了网上大部分的教程实在学不会，代码的格式也不太熟悉，想在Notelist中加个按钮，结果每条笔记后面都跟了个按钮，后来改在菜单栏代替按钮，无奈刷新ui那边似乎没我想象中的那么简单，希望日后有机会实现。
