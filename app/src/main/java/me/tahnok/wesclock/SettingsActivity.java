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

    protected Settings settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.settings));

        ButterKnife.bind(this);
        settings = Settings.getInstance(getApplicationContext());
        render();
    }

    private void render() {
        portView.setText(String.valueOf(settings.getPort()));
        ipAddressView.setText(settings.getIpdAddress());

    }

    @OnClick(R.id.ip_address_row)
    protected void setIpAddress() {
        new StringPickerDialog().show(
            R.string.ip_address,
            settings.getIpdAddress(),
            getFragmentManager(),
            new StringPickerDialog.Delegate() {
                @Override
                public void onValueSelected(String string) {
                    settings.setIpAddress(string);
                    render();
                }
        });
    }

    @OnClick(R.id.port_row)
    protected void setPortAddress() {
        new StringPickerDialog().show(
            R.string.port,
            String.valueOf(settings.getPort()),
            getFragmentManager(), new StringPickerDialog.Delegate() {
                @Override
                public void onValueSelected(String string) {
                    settings.setPort(Integer.valueOf(string));
                    render();
                }
            });
    }

    @OnClick(R.id.source_code)
    protected void launchSourceCode() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tahnok/WesClock"));
        startActivity(browserIntent);
    }
}
