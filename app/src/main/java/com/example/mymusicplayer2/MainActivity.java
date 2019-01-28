package com.example.mymusicplayer2;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekbar;
    private TextView Tv_end,Tv_start;
    private ImageButton Btn_forward,Btn_play, Btn_backward, Btn_next,Btn_previous;
    private MediaPlayer mediaPlayer;
    private ProgressWheel progress_wheel;
    private Timer timer;
    private String file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Cast();
        setUpMusicPlayer();

    }

    void Cast(){

        seekbar=findViewById(R.id.seekbar);
        Tv_end =findViewById(R.id.Tv_endtime);
        Tv_start=findViewById(R.id.Tv_start);
        Btn_forward =findViewById(R.id.Btn_forward);
        Btn_play=findViewById(R.id.Btn_play);
        Btn_backward =findViewById(R.id.Btn_backward);
        Btn_next=findViewById(R.id.Btn_next);
        Btn_previous=findViewById(R.id.Btn_previous);
        progress_wheel=findViewById(R.id.progress_wheel);

    }


    void setUpMusicPlayer()
    {
        mediaPlayer=new MediaPlayer();

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.
                        EXTERNAL_CONTENT_URI,
                null,
                null, null, null);

        if (null != cursor) {
            cursor.moveToFirst();}
            cursor.moveToNext();

            file=  cursor.getString (cursor.getColumnIndex(MediaStore.Audio.Media.DATA));





        try {
//            mediaPlayer.setDataSource("http://dl.pop-music.ir/music/1397/Khordad/Karang%20-%20Oriental%20Rhythm%20(128).mp3");
            mediaPlayer.setDataSource(this, Uri.parse(file) );
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mediaPlayer) {
                    progress_wheel.setVisibility(View.GONE);
                    playMusic();
                    nextback();
                    setup_seekbar();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }



    }



    void playMusic()
    {
        Btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer!=null && mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
//                    Btn_play.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.play_music));
//                    Btn_play.setImageResource(R.drawable.play_music);
                }
                else
                {
                    mediaPlayer.start();
//                    Btn_play.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_pause));
//                    Btn_play.setImageResource(R.drawable.ic_pause);

                }

            }
        });
        seekbar.setProgress(0);
        mediaPlayer.seekTo(0);
        Tv_start.setText(set_time(0));
        Tv_end.setText(set_time(mediaPlayer.getDuration()));

    }

    void nextback()
    {
        Btn_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
            }
        });

        Btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+ 5000);
            }
        });
    }

    void setup_seekbar()
    {
        seekbar.setMax(mediaPlayer.getDuration());
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                    Tv_start.setText(set_time(mediaPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        timer=new Timer();
        timer.schedule(new timertask(),0,1000);

    }

    private String set_time(long progress)
    {
        int sec=(int) progress / 1000;
        int min=sec / 60;
        sec %= 60;
        return String.format(Locale.ENGLISH,"%02d",min) + ":" + String.format(Locale.ENGLISH,"%02d",sec);
    }




    public class  timertask extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    seekbar.setProgress(mediaPlayer.getCurrentPosition());
                    Tv_start.setText(set_time(mediaPlayer.getCurrentPosition()));
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null){
            try{
                timer.cancel();
                mediaPlayer.release();
            }catch (Exception e){

            }

        }
        super.onDestroy();
    }
}




//
//
//
////????????????????????????
////
//
//shayanxm🃏, [28.01.19 20:40]
//        package com.example.shayanmoradi.xplayer.Models;
//
//        import java.util.UUID;
//
//public class Song {
//    private String mSongPath;
//    private String mSongArtWorkPath;
//    private String mAlbumArtWorkPath;
//    private String mSongName;
//    private String mAlbumName;
//    private String mArtistName;
//    private String mAlbumId;
//    private String mArtistId;
//    private int mNumbpointer;
//    private UUID songId= UUID.randomUUID();
//    public String getmAlbumName() {
//        return mAlbumName;
//    }
//
//    public int getmNumbpointer() {
//        return mNumbpointer;
//    }
//
//    public void setmNumbpointer(int mNumbpointer) {
//        this.mNumbpointer = mNumbpointer;
//    }
//
//
//
//    public UUID getSongId() {
//        return songId;
//    }
////                    song.setmAlbumArtWorkPath(getAlbumart(mContext, Long.valueOf(albumIdSeted)));
////                    song.setmSongArtWorkPath(getsongArtWork(data));
//
//
//
//    public String getmSongName() {
//        return mSongName;
//    }
//
//    public String getmArtistName() {
//        return mArtistName;
//    }
//
//    public String getmAlbumId() {
//        return mAlbumId;
//    }
//
//    public Song(String mSongPath) {
//        this.mSongPath = mSongPath;
//
//    }
//    public String getmSongPath() {
//        return mSongPath;
//    }
//    public void setmSongArtWorkPath(String  mSongArtWorkPath) {
//        this.mSongArtWorkPath = mSongArtWorkPath;
//    }
//
//    public void setmAlbumArtWorkPath(String mAlbumArtWorkPath) {
//        this.mAlbumArtWorkPath = mAlbumArtWorkPath;
//    }
//
//    public void setmSongName(String mSongName) {
//        this.mSongName = mSongName;
//    }
//
//    public void setmAlbumName(String mAlbumName) {
//        this.mAlbumName = mAlbumName;
//    }
//
//    public void setmArtistName(String mArtistName) {
//        this.mArtistName = mArtistName;
//    }
//
//    public void setmAlbumId(String mAlbumId) {
//        this.mAlbumId = mAlbumId;
//    }
//
//    public void setmArtistId(String mArtistId) {
//        this.mArtistId = mArtistId;
//    }
//
//
//
//}
//
//shayanxm🃏, [28.01.19 20:40]
//        package com.example.shayanmoradi.xplayer.Models;
//
//        import android.content.ContentResolver;
//        import android.content.ContentUris;
//        import android.content.Context;
//        import android.database.Cursor;
//        import android.graphics.Bitmap;
//        import android.graphics.BitmapFactory;
//        import android.media.MediaMetadataRetriever;
//        import android.net.Uri;
//        import android.os.ParcelFileDescriptor;
//        import android.provider.BaseColumns;
//        import android.provider.MediaStore;
//
//        import java.io.FileDescriptor;
//        import java.util.ArrayList;
//        import java.util.List;
//
//public class SongLab {
//    private static SongLab instance;
//    List<Song> mAllSongs;
//    private Context mContext;
//
//    private SongLab(Context context) {
//        mAllSongs = new ArrayList<>();
//        this.mContext = context;
//    }
//
//
//    public static SongLab getInstance(Context context) {
//        if (instance == null)
//            instance = new SongLab(context);
//        return instance;
//    }
//
//    //when get some thing dont want context
//    private SongLab() {
//        mAllSongs = new ArrayList<>();
//
//
//    }
//
//    public static SongLab getInstance() {
//        if (instance == null)
//            instance = new SongLab();
//        return instance;
//    }
//
//    //when get some thing dont want context
//    public List<Song> getAllSongs() {
//        setAllSongsList();
//        List<Song> songList = mAllSongs;
//        return songList;
//    }
//
//    private void setAllSongsList() {
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
//        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
//        ContentResolver cr = mContext.getContentResolver();
//        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
//        int count = 0;
//        List<String> names = new ArrayList<>();
//        List<String> namesz = new ArrayList<>();
//        List<String> albumId = new ArrayList<>();
//        List<Song> allSongs = new ArrayList<>();
//        String thisArtist = "";
//        String thisTitle = "";
//        String album = "";
//        String albumTestId;
//        int i = 0;
//        if (cur != null) {
//            count = cur.getCount();
//
//            if (count > 0) {
//                while (cur.moveToNext()) {
//
//                    int titleColumn = cur.getColumnIndex(MediaStore.MediaColumns.TITLE);
//                    int idColumn = cur.getColumnIndex(BaseColumns._ID);
//                    int albumIdH = cur.getColumnIndex(MediaStore.Audio.Albums._ID);
//                    //   int albumIdH = cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID);
//                    //   int albumIdH = cur.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID);
//                    int artistColumn = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST);
//                    int artistId = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST_ID);
//                    int artistColumns = cur.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
//                    int column_index = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
//                    thisTitle = cur.getString(titleColumn);
//                    String albumIdSeted = cur.getString(albumIdH);
//                    thisArtist = cur.getString(artistColumn);
//                    album = cur.getString(artistColumns);
//                    String artistIdSeted = cur.getString(artistId);
//                    namesz.add(album);
//                    //     albumId.add(test);
//                    // Add code to get more column here
//                    names.add(data);
//                    // Save to your list here
//
//
//                    /////////////
//                    Song song = new Song(data);
//                    song.setmAlbumName(album);
//                    song.setmAlbumId(albumIdSeted);
//
//                    shayanxm🃏, [28.01.19 20:40]
//                    song.setmArtistId(artistIdSeted);
//                    song.setmSongName(thisTitle);
//                    song.setmArtistName(thisArtist);
//                    song.setmAlbumArtWorkPath(albumIdSeted);
//                    song.setmSongArtWorkPath(data);
//                    song.setmNumbpointer(i);
////                    song.setmAlbumArtWorkPath(getAlbumart(mContext, Long.valueOf(albumIdSeted)));
////                    song.setmSongArtWorkPath(getsongArtWork(data));
////
////
//
//                    allSongs.add(song);
//                    i++;
//                }
//
//            }
//        }
//        mAllSongs = allSongs;
//    }
//
//    private Bitmap getsongArtWork(String path) {
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        mmr.setDataSource(path);
//        byte[] data = mmr.getEmbeddedPicture();
//        if (data != null) return BitmapFactory.decodeByteArray(data, 0, data.length);
//        return null;
//    }
//
//    public Bitmap getAlbumart(Context context, Long album_id) {
//        Bitmap albumArtBitMap = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        try {
//
//            final Uri sArtworkUri = Uri
//                    .parse("content://media/external/audio/albumart");
//
//            Uri uri = ContentUris.withAppendedId(sArtworkUri, album_id);
//
//            ParcelFileDescriptor pfd = context.getContentResolver()
//                    .openFileDescriptor(uri, "r");
//
//            if (pfd != null) {
//                FileDescriptor fd = pfd.getFileDescriptor();
//                albumArtBitMap = BitmapFactory.decodeFileDescriptor(fd, null,
//                        options);
//                pfd = null;
//                fd = null;
//            }
//        } catch (Error ee) {
//        } catch (Exception e) {
//        }
//
//        if (null != albumArtBitMap) {
//            return albumArtBitMap;
//        }
//        return null;
//    }
//
//    public Song getSong(String mSongPath) {
//        //search for song
//        List<Song> songs = getAllSongs();
//        for (int i = 0; i < songs.size(); i++) {
//            if (songs.get(i).getmSongPath().equals(mSongPath))
//                return songs.get(i);
//        }
//        return null;
//    }
//
//    public int getNurmberPointer(Song song) {
//        List<Song> songs = getAllSongs();
//        for (int i = 0; i < songs.size(); i++) {
//            if (songs.get(i).getmSongPath().equals(song.getmSongPath()))
//                return i;
//        }
//        return -1;
//    }
//}