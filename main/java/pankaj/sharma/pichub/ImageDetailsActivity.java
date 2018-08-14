package pankaj.sharma.pichub;

import android.Manifest;
import android.app.DownloadManager;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import static com.bumptech.glide.load.engine.DiskCacheStrategy.ALL;

public class ImageDetailsActivity extends AppCompatActivity {

    String largeImageURL;
    int likes;
    long views;
    String tags;
    long downloads;
    ImageView img;
    private long reid;

    DownloadManager downloadManager;
    private static int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //registerReceiver(onEnterAnimationComplete(), )
        largeImageURL = getIntent().getStringExtra("LARGEIMAGEURL");
        likes = getIntent().getIntExtra("LIKES",0);
        views = getIntent().getLongExtra("VIEWS",0);
        tags = getIntent().getStringExtra("TAGS");
        downloads = getIntent().getIntExtra("DOWNLOADS",0);


        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        },REQUEST_CODE);


        TextView tr = findViewById(R.id.tags);
        tr.setText(""+tags);

        TextView vi = findViewById(R.id.viewsa);
        vi.setText(""+views);
        TextView lik = findViewById(R.id.likesa);
        lik.setText(""+likes);
        TextView down = findViewById(R.id.download);

        img = findViewById(R.id.imgs);
        img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Glide.with(this)
                .load(largeImageURL)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_placeholder)
                .dontAnimate()
                .approximate()
                .error(R.drawable.ic_error)
                .into(img);

        findViewById(R.id.don).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(ImageDetailsActivity.this)
                        .load(largeImageURL)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.drawable.ic_placeholder)
                        .dontAnimate()
                        .approximate()
                        .error(R.drawable.ic_error)
                        .into(img);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri Download_uri = Uri.parse(largeImageURL);
                DownloadManager.Request request = new DownloadManager.Request(Download_uri);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI| DownloadManager.Request.NETWORK_MOBILE);
                request.setAllowedOverRoaming(false);
                request.setVisibleInDownloadsUi(true);
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String filename = URLUtil.guessFileName(largeImageURL, null, MimeTypeMap.getFileExtensionFromUrl(largeImageURL));
                request.setTitle(""+filename);
                request.setDescription("1 : "+filename);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,filename);
                reid = downloadManager.enqueue(request);
            }
        });
    }
}
