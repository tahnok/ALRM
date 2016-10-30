package me.tahnok.wesclock;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends Activity {

    @BindView(R.id.port) protected TextView portView;
    @BindView(R.id.ip_address) protected TextView ipAddressView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings));

        ButterKnife.bind(this);
        render();
    }

    private void render() {
        Settings settings = Settings.getInstance(getApplicationContext());
        portView.setText(settings.getPort());
        ipAddressView.setText(settings.getIpdAddress());

    }

    @OnClick(R.id.ip_address_row)
    protected void setIpAddress() {

    }

    @OnClick(R.id.port_row)
    protected void setPortAddress() {
        
    }

    @OnClick(R.id.source_code)
    protected void launchSourceCode() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tahnok/WesClock"));
        startActivity(browserIntent);
    }
}
