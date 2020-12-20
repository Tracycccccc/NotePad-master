# NotePad
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/login.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/loginfailed.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/loginsuccess.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/search.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/setpassword.png)
![image](https://github.com/Tracycccccc/NotePad-master/blob/master/img-folder/setpasswordsuccess.png)

##一：时间戳的添加
(```)
 private static final String[] PROJECTION = new String[] {
            NotePad.Notes._ID,
            NotePad.Notes.COLUMN_NAME_TITLE, 
            NotePad.Notes.COLUMN_NAME_MODIFICATION_DATE
    };
(```)
